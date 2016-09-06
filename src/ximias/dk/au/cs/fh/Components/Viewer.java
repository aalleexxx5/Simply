package ximias.dk.au.cs.fh.Components;

import ximias.dk.au.cs.fh.Commands.Run;
import ximias.dk.au.cs.fh.Commands.Window;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Alex on 05/01/2016.
 * Contains all user-interaction.<br/>
 * Starts and restarts the application, contains the window opened during runtime, and all its components.
 */
public class Viewer extends JFrame{
    private String filename;
    private static Thread exeThread;
    public Viewer(String[] args){
        this.filename=args[0];
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
    private Lookup lookup;

    /**
     * Creates the the user-interface, layout and sets action listeners for the buttons.
     */
    private void createUI(){
        win = this;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        runButton = new JButton("Start Program");
        //runButton.setPreferredSize(new Dimension(380, 60));
        commandButton = new JToggleButton();
        //commandButton.setPreferredSize(new Dimension(10,60));
        input = new JTextField();
        submit = new JButton ("submit");
        //submit.setPreferredSize(new Dimension(100, 20));
        input.setEditable(false);
        //input.setPreferredSize(new Dimension(300, 20));
        submit.setEnabled(false);
        output = new JTextArea();
        output.setEditable(false);
        JScrollPane outputContainer = new JScrollPane(output);
        outputContainer.setPreferredSize(new Dimension(400, 180));
        outputContainer.setAutoscrolls(true);

        SpringLayout topContainerLayout = new SpringLayout();
        JPanel topContainer  = new JPanel(topContainerLayout);
        topContainerLayout.putConstraint(SpringLayout.SOUTH,topContainer,60,SpringLayout.NORTH,topContainer);

        topContainerLayout.putConstraint(SpringLayout.WEST,runButton,0,SpringLayout.WEST,topContainer);
        topContainerLayout.putConstraint(SpringLayout.NORTH,runButton,0,SpringLayout.NORTH,topContainer);
        topContainerLayout.putConstraint(SpringLayout.SOUTH,runButton,0,SpringLayout.SOUTH,topContainer);
        topContainerLayout.putConstraint(SpringLayout.EAST,runButton,-1,SpringLayout.WEST,commandButton);

        topContainerLayout.putConstraint(SpringLayout.WEST,commandButton,-15,SpringLayout.EAST,topContainer);
        topContainerLayout.putConstraint(SpringLayout.EAST,commandButton,0,SpringLayout.EAST,topContainer);
        topContainerLayout.putConstraint(SpringLayout.NORTH,commandButton,0,SpringLayout.NORTH,topContainer);
        topContainerLayout.putConstraint(SpringLayout.SOUTH,commandButton,0,SpringLayout.SOUTH,topContainer);

        topContainer.add(runButton);
        topContainer.add(commandButton);


        SpringLayout inputsContainerLayout = new SpringLayout();
        JPanel inputsContainer = new JPanel(inputsContainerLayout);
        inputsContainerLayout.putConstraint(SpringLayout.SOUTH,inputsContainer,20,SpringLayout.NORTH,inputsContainer);

        inputsContainerLayout.putConstraint(SpringLayout.WEST,input,0,SpringLayout.WEST,inputsContainer);
        inputsContainerLayout.putConstraint(SpringLayout.EAST,input,1,SpringLayout.WEST,submit);
        inputsContainerLayout.putConstraint(SpringLayout.NORTH,input,0,SpringLayout.NORTH,inputsContainer);
        inputsContainerLayout.putConstraint(SpringLayout.SOUTH,input,0,SpringLayout.SOUTH,inputsContainer);

        inputsContainerLayout.putConstraint(SpringLayout.WEST,submit,-100,SpringLayout.EAST,inputsContainer);
        inputsContainerLayout.putConstraint(SpringLayout.EAST,submit,0,SpringLayout.EAST,inputsContainer);
        inputsContainerLayout.putConstraint(SpringLayout.NORTH,submit,0,SpringLayout.NORTH,inputsContainer);
        inputsContainerLayout.putConstraint(SpringLayout.SOUTH,submit,0,SpringLayout.SOUTH,inputsContainer);

        inputsContainer.add(input);
        inputsContainer.add(submit);

        add(topContainer,"North");
        add(inputsContainer,"South");
        add(outputContainer, "Center");
        pack();

        runButton.addActionListener(e -> {
            lookup=new Lookup(FileManager.readFileList(filename));
            Mem.removeAll();
            Window.removeAll();
            output.setText("");
            if (runtimeFrame != null) {
                runtimeFrame.getLayeredPane().removeAll();
            }
            exeThread = new Thread(new Exechuter(lookup));
            exeThread.start();
            runButton.setEnabled(false);
        });

        submit.addActionListener(e -> {
            synchronized (lock){
                lock.notifyAll();
            }
        });

        commandButton.addActionListener(e -> {
            if(commandButton.isSelected()){//button is pressed
                if (lookup == null) {
                    lookup = new Lookup(new ArrayList<String>());
                }
                SwingUtilities.invokeLater(this::createCommandList);
            }else{//button is released
                commandFrame.dispose();
            }
        });
    }

    /**
     * Opens a list of commands in a separate popup window.
     */
    private void createCommandList(){
        commandFrame = new JFrame();
        commandFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int commandLength=0;
        int descriptionLength =0;
        for(int i=0;i<lookup.getNumCommands();i++){
            commandLength = Math.max(commandLength,Lookup.getCommandName(i).length());
            descriptionLength = Math.max(descriptionLength,Lookup.getCommandDescription(i).length());
        }
        TableModel commandData = new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return Lookup.getNumCommands()+1;
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                if (rowIndex<Lookup.getNumCommands()) {
                    if (columnIndex == 0) {
                        return Lookup.getCommandName(rowIndex);
                    } else {
                        return Lookup.getCommandDescription(rowIndex);
                    }
                }else{
                    if (columnIndex == 0) {
                        return "Comment";
                    } else {
                        return "To put comments in code, surround it with comment and endcomment like a subroutine";
                    }
                }
            }
        };
        JTable commandTable = new JTable(commandData);
        commandTable.getColumnModel().getColumn(0).setMinWidth((commandLength*7)+2);
        commandTable.getColumnModel().getColumn(0).setMaxWidth((commandLength*7)+4);
        commandTable.getColumnModel().getColumn(1).setMinWidth(descriptionLength*6);
        commandTable.setAutoCreateRowSorter(true);
        commandTable.getRowSorter().toggleSortOrder(0);
        JScrollPane scrollpane = new JScrollPane(commandTable);
        commandTable.getSelectionModel().addListSelectionListener(e -> {
            if (commandTable.getSelectedRow()>=0){
                JOptionPane.showMessageDialog(null,Lookup.getCommandUse(commandTable.getSelectedRow()),"Usage",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        commandFrame.setPreferredSize(new Dimension(commandTable.getMinimumSize().width,commandTable.getRowHeight()*(commandTable.getRowCount())+70));
        commandFrame.add(scrollpane);
        commandFrame.setVisible(true);
        commandFrame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent winEvt){
                commandButton.setSelected(false);
            }
        });
        commandFrame.pack();
    }

    private static JFrame runtimeFrame;
    private static int elementindex = 1;
    private static JLabel canvas;

    /**
     * adds an element to a frame opened during runtime
     * @param element the element to be added, can be any component.
     */
    public static void addElement(Component element){
        runtimeFrame.getLayeredPane().add(element,elementindex);
        elementindex++;
    }

    /**
     * removed an element from a frame opened during runtime
     * @param element the element to be removed, can be any component.
     */
    public static void removeElement(Component element){
        runtimeFrame.getLayeredPane().remove(element);
        elementindex--;
    }
    public static void removeAllElements(){
        while (runtimeFrame.getLayeredPane().getComponents().length>0){
            runtimeFrame.getLayeredPane().remove(0);
            elementindex--;
        }
        Window.removeAll();
        runtimeFrame.repaint();
    }
    public static boolean drawElement(BufferedImage image) throws InterruptedException {
        if (!runtimeFrame.isVisible()) {
            throw new InterruptedException("frame closed");
        }
        if (canvas == null) {
            if (image==null)return false;
            canvas=new JLabel(new ImageIcon(image));
            canvas.setHorizontalAlignment(JLabel.LEFT);
            canvas.setVerticalAlignment(JLabel.TOP);
            canvas.setBounds(0,0,runtimeFrame.getWidth(),runtimeFrame.getHeight());
            runtimeFrame.getLayeredPane().add(canvas,-1);
        }else{
            canvas.setIcon(new ImageIcon(image));
        }
        return true;
    }

    public static void updateWindow(int x, int y, int width, int height){
        if (runtimeFrame==null) {
            runtimeFrame = new JFrame();
            runtimeFrame.setLayout(null);
            runtimeFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            runtimeFrame.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent winEvt) {
                    runtimeFrame.setVisible(false);
                    canvas=null;
                    doneExecution();
                    if (!runButton.isEnabled()){
                        try {
                            Thread.sleep(300);
                            doneExecution();
                            if (!runButton.isEnabled()){
                                print("The application is still running");
                            }
                        } catch (InterruptedException ignored) {}
                    }
                }
            });
        }
        runtimeFrame.setBounds(x,y,width,height);
        runtimeFrame.setVisible((width!=0&&height!=0));
    }
    public static void addKeyboard(KeyEventDispatcher e){
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e);
    }

    public static void removeKeyboard(KeyEventDispatcher e) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(e);
    }

    static void doneExecution(){
        if (runtimeFrame!=null&&runtimeFrame.isVisible()){
            return;
        }
        if (Run.isDone()&&!exeThread.isAlive())
            runButton.setEnabled(true);
        else if(exeThread.equals(Thread.currentThread())&&Run.noThreads()){
            runButton.setEnabled(true);
        }
        //exeThread.interrupt();
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
        if (type==null||type.isEmpty()){
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
            output.append(line+"\n");
            output.setCaretPosition(output.getDocument().getLength());
    }

    public static void clear(){
        System.out.println("screen was cleared \n \n");
        output.setText("");
    }
}