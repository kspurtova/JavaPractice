package com.example.client;

import com.example.Message;
import com.example.server.Action;
import com.example.server.Response;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientFrame extends JFrame{

    int port = 3123;
    InetAddress ip;

    int id;

    Socket cs;
    DataInputStream dis;
    DataOutputStream dos;

    Gson convert = new Gson();


    private JLabel labelNow = new JLabel("Time now:");
    private JLabel timeNow = new JLabel("00:00:00");

    private JLabel labelHours = new JLabel("Hours: ");
    private JLabel labelMinutes = new JLabel("Minutes: ");
    private JLabel labelSeconds = new JLabel("Seconds: ");


    private JSpinner spinnerHours = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
    private JSpinner spinnerMinutes = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
    private  JSpinner spinnerSeconds = new JSpinner(new SpinnerNumberModel(10, 0, 59, 1));

    private JLabel labelText = new JLabel("Message:");
    private JTextField message = new JTextField("", 255);

    private JButton buttonConnect = new JButton("Connect");
    private JLabel labelInfo = new JLabel("Information");

    private JButton buttonAdd = new JButton("Add");

    private JTextArea textMessages= new JTextArea();


    public ClientFrame() {
        this.setBounds(100, 100, 400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Container container = this.getContentPane(); // это наша форма, куда можем добавлять элементы
        container.setLayout(new GridLayout(12, 2, 2, 2)); // создаем табличку для элементов
        container.add(labelNow);
        container.add(timeNow);
        container.add(labelHours);
        container.add(spinnerHours);
        container.add(labelMinutes);
        container.add(spinnerMinutes);
        container.add(labelSeconds);
        container.add(spinnerSeconds);
        container.add(labelText);
        container.add(message);



        buttonConnect.addActionListener(e -> connect());
        container.add(buttonConnect);


        container.add(labelInfo);

        buttonAdd.addActionListener(e -> add());

        container.add(buttonAdd);
        container.add(textMessages);

    }

    Thread t;
    public void connect() {
        if(t!=null)
            if (t.isAlive())
                return;
            try {
                ip = InetAddress.getLocalHost();

                // с помощью сокета подключаемся к серверу
                cs = new Socket(ip, port);

                dos = new DataOutputStream(cs.getOutputStream());

                t = new Thread(
                        () -> {
                            try {
                                dis = new DataInputStream(cs.getInputStream());
                                String obj;

                                Response resp;
                                //Message msg;
                                //obj = dis.readUTF();
                                //resp = convert.fromJson(obj, Response.class);
                                //id = msg.getId();
                                //labelInfo.setText("You are " + id + " connected to the server!");

                                while (true) {
                                    obj = dis.readUTF();
                                    resp = convert.fromJson(obj, Response.class);

                                    if (resp.getAction() == Action.ID) {
                                        id = resp.getMsg().getUser_id();
                                        labelInfo.setText("You are " + id + " connected to the server!");

                                    }
                                    else if(resp.getAction() == Action.MESSAGE) {
                                        textMessages.setText(resp.getMsgs().toString());
                                        repaint();
                                    }
                                    else if (resp.getAction() == Action.EVENT) {

                                        String s = "Coming event: " + resp.getMsg().toString();
                                        labelInfo.setText(s);
                                        repaint();
                                    }
                                    else if(resp.getAction() == Action.TIME) {

                                        // ???
                                        int timer = resp.getMsg().getTime();

                                        int seconds, minutes, hours;
                                        String s = "";
                                        seconds = timer % 60;
                                        minutes = timer / 60;
                                        hours = minutes / 60;
                                        minutes %= 60;

                                        s+=hours + ":" + minutes + ":" + seconds  + "\n";

                                        timeNow.setText(s);
                                        repaint();
                                    }
                                    else {
                                        labelInfo.setText("Error");
                                        repaint();
                                    }
                                    //repaint();
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                );
                t.start();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

    }


    public void add() {
        if(dos != null) { // тогда отправляем данные
            Message msg = new Message();
            msg.setUser_id(id);
            int t = 3600 * (int) spinnerHours.getValue() + 60 * (int) spinnerMinutes.getValue() + (int) spinnerSeconds.getValue();
            msg.setTime(t);
            msg.setMessage(message.getText());
            String str = convert.toJson(msg);
            System.out.println("Get " + msg);
            try {
                dos.writeUTF(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new ClientFrame().setVisible(true));
    }

}



