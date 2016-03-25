package ximias.dk.au.cs.fh.Components;

import ximias.dk.au.cs.fh.Commands.Run;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Alex on 05/01/2016.
 * Houses the UI. Prints the text. Changes the UI.
 */
public class Viewer extends JFrame{
    private static Thread exeThread;
    public Viewer(String[] args){
        lookup = new Lookup(FileManager.readFileList(args[0]));
        createUI();
    }

    private static JTextArea output;
    private static JTextField input;
    private static final Object lock=" ";
    private static JButton submit;
    private static JButton runButton;
    private static JFrame win;
    private static JToggleButton commandButton;
    private static JFrame commandFrame;
    private final Lookup lookup;

    private void createUI(){
        win = this;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        runButton = new JButton("Start Program");
        runButton.setPreferredSize(new Dimension(380, 60));
        commandButton = new JToggleButton();
        commandButton.setPreferredSize(new Dimension(10,60));
        input = new JTextField();
        submit = new JButton ("submit");
        submit.setPreferredSize(new Dimension(100, 20));
        input.setEditable(false);
        input.setPreferredSize(new Dimension(300, 20));
        submit.setEnabled(false);
        output = new JTextArea();
        output.setEditable(false);
        JScrollPane outputContainer = new JScrollPane(output);
        outputContainer.setPreferredSize(new Dimension(400, 180));
        outputContainer.setAutoscrolls(true);
        JPanel inputsContainer = new JPanel(new FlowLayout());
        JPanel topContainer  = new JPanel(new FlowLayout());


        topContainer.add(runButton);
        topContainer.add(commandButton);
        inputsContainer.add(input);
        inputsContainer.add(submit);
        add(topContainer,"North");
        add(inputsContainer,"South");
        add(outputContainer, "Center");
        pack();


        runButton.addActionListener(e -> {
            exeThread = new Thread(new Exechuter(lookup));
            exeThread.start();
            runButton.setEnabled(false);
        });

        submit.addActionListener(e -> {
            synchronized (lock){
                lock.notifyAll();/*
                try {
                    Thread.sleep(1050);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }*/
            }
        });

        commandButton.addActionListener(e -> {
            if(commandButton.isSelected()){//button is pressed
                SwingUtilities.invokeLater(this::createCommandList);
            }else{//button is released
                commandFrame.dispose();
            }
        });
    }

    private void createCommandList(){
        commandFrame = new JFrame();
        commandFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int commandLength=0;
        int descriptionLength =0;
        for(int i=0;i<Lookup.getNumCommands();i++){
            commandLength = Math.max(commandLength,Lookup.getCommandName(i).length());
            descriptionLength = Math.max(descriptionLength,Lookup.getCommandDescription(i).length());
        }
        TableModel commandData = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return Lookup.getNumCommands();
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                if (columnIndex==0){
                    return Lookup.getCommandName(rowIndex);
                }else{
                    return Lookup.getCommandDescription(rowIndex);
                }
            }
        };
        JTable commandTable = new JTable(commandData);
        commandTable.getColumnModel().getColumn(0).setMinWidth((commandLength*7)+2);
        commandTable.getColumnModel().getColumn(0).setMaxWidth((commandLength*7)+4);
        commandTable.getColumnModel().getColumn(1).setMinWidth(descriptionLength*6);
        JScrollPane scrollpane = new JScrollPane(commandTable);
        commandTable.getSelectionModel().addListSelectionListener(e -> {
            if (commandTable.getSelectedRow()>=0){
                JOptionPane.showMessageDialog(null,Lookup.getCommandUse(commandTable.getSelectedRow()),"Usage",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        commandFrame.setPreferredSize(new Dimension(commandTable.getMinimumSize().width,commandTable.getRowHeight()*(commandTable.getRowCount()+5)));
        commandFrame.add(scrollpane);
        commandFrame.setVisible(true);
        commandFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent winEvt){
                commandButton.setSelected(false);
            }
        });
        commandFrame.pack();
    }

    public static void doneExecution(){
        if (Run.isDone()&&!exeThread.isAlive())
            runButton.setEnabled(true);
        else if(exeThread.equals(Thread.currentThread())&&Run.noThreads()){
            runButton.setEnabled(true);
        }
    }

    public static String getInput(){
        input.setEditable(true);
        submit.setEnabled(true);
        try {
            synchronized (lock){
                lock.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        input.setEditable(false);
        submit.setEnabled(false);
        String ret = input.getText();
        input.setText("");
        return ret;
    }

    public static void setFont(String type ,int size){
        if (type.isEmpty()){
            output.setFont(new Font(output.getFont().getName(),Font.PLAIN,size));
        }
        if (size<1){
            output.setFont(new Font(type,Font.PLAIN,output.getFont().getSize()));
        }
    }
    public static void setTextColour(int value){
            output.setForeground(new Color(value));
    }
    @SuppressWarnings("unused")
    public static void resizeApp(int w, int h){
        win.setSize(w,h);
    }

    public static void print(String line){
        System.out.println(line);
        output.append("\n" + line);
            output.setCaretPosition(output.getDocument().getLength());
    }

    public static void clear(){
        System.out.println("screen was cleared \n \n");
        output.setText("");
    }
}
