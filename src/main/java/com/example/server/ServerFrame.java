package com.example.server;

import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class ServerFrame extends JFrame{


    // СОЗДАНИЕ ФОРМЫ

    private JLabel labelNow = new JLabel("Time now:");
    private JLabel timeNow = new JLabel("00:00:00");

    private JLabel portLabel = new JLabel("Port");
    private JTextArea port = new JTextArea("3123");
    private JLabel ipLabel = new JLabel("Ip-address");

    InetAddress host;
    {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    String hostName = host.getHostName();
    InetAddress [] ip_local;
    {
        try {
            ip_local = InetAddress.getAllByName(hostName);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    InetAddress ip;


    private JComboBox ipBox = new JComboBox(ip_local);

    private JButton buttonRunServer = new JButton("Run server");
    private JButton buttonDisconnectServer = new JButton("Disconnect");
    private JButton buttonStart = new JButton ("Start");
    private JButton buttonStop = new JButton("Stop");

    private JLabel labelInfo = new JLabel("Information: ");
    private JTextArea textInformation= new JTextArea();

    private JLabel labelMessages = new JLabel("All messages:");
    private JTextArea labelEvent = new JTextArea("-");

    Server server = new Server();

    public ServerFrame(String title) {
        super(title);
        this.setBounds(100, 100, 400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Container container = this.getContentPane(); // это наша форма, куда можем добавлять элементы
        container.setLayout(new GridLayout(12, 2, 2, 2)); // создаем табличку для элементов
        container.add(labelNow);
        container.add(timeNow);

        container.add(portLabel);
        container.add(port);
        container.add(ipLabel);

        ipBox.addActionListener(e -> ip = (InetAddress)ipBox.getSelectedItem());
        container.add(ipBox);



        buttonRunServer.addActionListener(e -> runServer());
        container.add(buttonRunServer);

        buttonDisconnectServer.addActionListener(e -> disconnectServer());
        container.add(buttonDisconnectServer);

        buttonStart.addActionListener(e -> server.start());
        container.add(buttonStart);


        buttonStop.addActionListener(e -> server.stop());
        container.add(buttonStop);

        container.add(labelInfo);
        container.add(textInformation);

        container.add(labelMessages);
        container.add(labelEvent);


    }

//    // РАБОТА С БУДИЛЬНИКОМ
//    private Thread t;
//    String str = "";
//    int time = 0;
//    int timer = 0;
//    Map<Integer,String> messages = new HashMap<>();

//    public void addTime(){
//        time ++;
//        int hours, minutes, seconds;
//        seconds = time % 60;
//        minutes = time / 60;
//        hours = minutes / 60;
//        minutes %= 60;
//
//        timeNow.setText(hours + ":" + minutes + ":" + seconds);
//        repaint();
//    }

//    boolean run;
//    public void start() {
//        if(t!=null)
//            if (t.isAlive())
//                return;
//        t = new Thread(
//                ()->{
//                    run = true;
//                    try {
//                        while (run) {
//                            addTime();
//                            Thread.sleep(1000);
//                            for(Map.Entry<Integer, String> item : messages.entrySet()){
//                                if (time == item.getKey()) {
//                                    JOptionPane.showMessageDialog(this, item.getValue());
//                                    repaint();
//                                }
//                            }
//
//                        }
//                    }
//                    catch (InterruptedException e) {
//                        e.printStackTrace();
//                        JOptionPane.showMessageDialog(this, "Stop message");
//                        return;
//                    }
//
//                }
//        );
//        t.start();
//
//    }

//    String s = "";
//    int hours, minutes, seconds;
//    boolean runAdd = true;
//    public void add() {
//
//        //Integer t = 3600 * (int) spinnerHours.getValue() + 60 * (int) spinnerMinutes.getValue() + (int) spinnerSeconds.getValue();
//        //messages.put(t, message.getText());
//        s = "";
//        for(Map.Entry<Integer, String> item : messages.entrySet()){
//
//            seconds = item.getKey() % 60;
//            minutes = item.getKey() / 60;
//            hours = minutes / 60;
//            minutes %= 60;
//
//            s+=hours + ":" + minutes + ":" + seconds + "  " + item.getValue() + "\n";
//
//        }
//        labelEvent.setText(s);
//        repaint();
//    }

//    public void stop() {
//        if (t == null) return;  // если ещё не создавали поток, то ничего не надо делать
//        run = false;
//        t.interrupt();
//        //t.stop();
//
//    }



    // РАБОТА СЕРВЕРА
    Thread t;
    boolean run;
    public void runServer() {
        server.connect(Integer.valueOf(port.getText()), ip);
        textInformation.setText(server.getStatus());
        t = new Thread(
                ()-> {
                    run = true;
                    while(run) {
                        textInformation.setText(server.getStatus());
                        labelEvent.setText(server.getAllMsg());
                        //messages = server.getMessages();

                        // изменяем время
                        int hours, minutes, seconds;
                        seconds = server.timer % 60;
                        minutes = server.timer / 60;
                        hours = minutes / 60;
                        minutes %= 60;

                        timeNow.setText(hours + ":" + minutes + ":" + seconds);


                        repaint();
                    }
                }
        );
        t.start();
    }

    public void disconnectServer () {
            server.disconnect();
            textInformation.setText(server.getStatus());
            repaint();
            run = false;
            t.interrupt();
    }


    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerFrame("Timer").setVisible(true);
            }
        });

    }

}

