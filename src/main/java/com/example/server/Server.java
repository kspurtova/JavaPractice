package com.example.server;

import com.example.Message;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {
    int port;
    InetAddress ip;
    ServerSocket ss;


    int count = 0;

    ServerModel m = BServerModel.build();

    //Map<Integer,String> allMsg = new HashMap<>();
    ArrayList<ServerClient> allClient = new ArrayList<>();

    String status = "";

    int timer = 0;
    Thread thread;
    boolean state = true;

    public Server () {

    }

    public void connect(Integer portName, InetAddress ipAd) {
        if(thread!=null)
            if (thread.isAlive())
                return;
        port = portName;
        // InetAddress.getAllByName() - все доступные IP адреса

        // Получаем IP адрес
        try {
            ip = ipAd;
            // Создадим сервер
            ss = new ServerSocket(port, 0, ip);
            System.out.println("Server is running");
            status = "Server is running";


            // Создали сервер
            // Переходим в состояние ожидания подключения клиента
            thread = new Thread(
                    ()->{
                        Socket cs = null;
                        state = true;
                        while(state) {
                            try {
                                cs = ss.accept();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            count++;

                            assert cs != null;
                            ServerClient sc = new ServerClient(count, cs, this );
                            allClient.add(sc);

                        }
                        setStatus("Server is disconnected");
                        // Завершаем соединение через сокет
                        try {
                            cs.close();
                            ss.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // завершаем соединение с базой данных


                    }
            );
            thread.start();


        } catch (IOException e) {
            System.out.println("Can't create server");
            status = "Can't create server";
        }

    }

    public void disconnect() {
        if (thread == null) return;  // если ещё не создавали поток, то ничего не надо делать
        state = false;
        thread.interrupt();
        stop();
    }

//
//    public void addMsg(Map<Integer, String> messages) {
//        for(Map.Entry<Integer, String> item : messages.entrySet()){
//            allMsg.put(item.getKey(), item.getValue()); // добавляем во все сообщения, которые хранятся на сервере
//        }
//
//        // разослать сообщение по всем пользователям
//        for (ServerClient serverClient : allClient) {
//            serverClient.sendMsg();
//        }
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAllMsg() {
        int seconds, minutes, hours;
        String s = "";
        for (Message msg: m.get()) {
            seconds = msg.getTime() % 60;
            minutes = msg.getTime() / 60;
            hours = minutes / 60;
            minutes %= 60;

            s += hours + ":" + minutes + ":" + seconds + "  " + msg.getMessage() + "\n";
        }
        return s;
    }

//    public  getMessages() {
//        return allMsg;
//    }


    // TIMER
    Thread t;
    boolean run;
    public void start() {
        if(t!=null)
            if (t.isAlive())
                return;
        t = new Thread(
                ()->{
                    run = true;
                    try {
                        while (run) {
                            timer ++;
                            for (ServerClient serverClient : allClient) {
                                serverClient.sendTime();
                                serverClient.sendMsgs();
                            }
                            Thread.sleep(1000);
                            for(Message msg : m.get()){
                                if (timer == msg.getTime()) {
                                    for (ServerClient serverClient : allClient) {
                                        serverClient.sendEvent(msg);
                                    }
                                    m.remove(msg);
                                    //JOptionPane.showMessageDialog(this, item.getValue());
                                    // отправить сообщение клиентам
                                }
                            }

                        }
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                        //JOptionPane.showMessageDialog(this, "Stop message");
                        //корректное завершение работы клиентов
                        //return;
                    }

                }
        );
        t.start();

    }

    public void stop() {
        if (t == null) return;  // если ещё не создавали поток, то ничего не надо делать
        run = false;
        t.interrupt();
        //t.stop();
    }

//    public String timeToString() {
//        int seconds, minutes, hours;
//        String s = "";
//        seconds = timer % 60;
//        minutes = timer / 60;
//        hours = minutes / 60;
//        minutes %= 60;
//
//        s += hours + ":" + minutes + ":" + seconds + "  " + "\n";
//        return s;
//    }

    public static void main(String[] args) {
        new Server();
    }

}
