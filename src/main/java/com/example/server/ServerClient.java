package com.example.server;

import com.example.Message;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerClient {
    int id; // модификатор клиента

    Socket cs;
    DataInputStream dis;
    DataOutputStream dos;

    Gson convert = new Gson();

    // Ссылка на модель, чтобы можно было с ней взаимодействовать
    ServerModel m = BServerModel.build();

    Server server;// делаем связь между основным классом и клиентом, который у нас есть

    Thread t;

    public ServerClient(int id, Socket cs, Server server) {
        this.id = id;
        this.cs = cs;
        this.server = server;

        server.setStatus("Client № " + id + " is connected \n");

        //System.out.println("Client № " + id + " is connected \n");
        try {
            dos = new DataOutputStream(cs.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        sendUser_id();

        t = new Thread(
                ()->{
                    try {
                        dis = new DataInputStream(cs.getInputStream());
                        while(true) {
                            String obj;
                            obj = dis.readUTF();

                            Message msg = convert.fromJson(obj, Message.class);
                            System.out.println("Get " + msg);

                            m.save(msg);
                            //server.addMsg(msg.getMessages());
                            sendTime();
                            sendMsgs();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        t.start();

    }

    void sendUser_id() {
        Message msg = new Message();
        msg.setUser_id(id);
        Response resp = new Response(msg, Action.ID);
        String sendStr = convert.toJson(resp);
        try {
            dos.writeUTF(sendStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendTime() {

        Message msg = new Message();
        //msg.setType("TIME");
        msg.setTime(server.timer);
        Response resp = new Response(msg, Action.TIME);
        String sendStr = convert.toJson(resp);
        try {
            dos.writeUTF(sendStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendMsgs();
    }

    void sendEvent(Message msg) {
        Response resp = new Response(msg, Action.EVENT);
        //msg.setType("EVENT");
        //msg.getMessages().put(time, event);
        String sendStr = convert.toJson(resp);
        try {
            dos.writeUTF(sendStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMsgs() {
        // Из модели получаем список всех сообщений, которые
        // хранятся внутри неё, конвертируем в строчку и
        // отправляем клиенту
        Response resp = new Response(m.get(), Action.MESSAGE);
        String s = convert.toJson(resp);

        if (dos != null) {
            try {
                dos.writeUTF(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
