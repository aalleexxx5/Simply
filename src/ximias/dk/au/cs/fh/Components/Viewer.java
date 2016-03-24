package ximias.dk.au.cs.fh.Components;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Alex on 05/01/2016.
 */
public class Viewer extends JFrame{
    String[] args;
    FileManager reader = new FileManager();
    private static Thread exeThread;
    public Viewer(String[] args){
        this.args = args;
        createUI();
    }

    private static JTextArea output;
    private static JTextField input;
    private static final Object lock=" ";
    private static JButton submit;
    private static JButton runButton;
    private static JFrame win;

    void createUI(){
        win = this;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        runButton = new JButton("Start Program");
        runButton.setPreferredSize(new Dimension(400, 60));
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


        add(runButton, "North");
        inputsContainer.add(input);
        inputsContainer.add(submit);
        add(inputsContainer,"South");
        add(outputContainer, "Center");
        pack();


        runButton.addActionListener(e -> {
            exeThread = new Thread(new Exechuter(reader.readFileList(args[0])));
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
    }

    public static void doneExecution(){
        if (exeThread.equals(Thread.currentThread()))
            runButton.setEnabled(true);
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
    public static void resizeApp(int w, int h){
        win.setSize(w,h);
    }

    public static void print(String line){
        System.out.println(line);
        output.append("\n" + line);
            output.setCaretPosition(output.getDocument().getLength());
    }

    public static void clear(){
        System.out.println("screen was cleared");
        output.setText("");
    }
}
