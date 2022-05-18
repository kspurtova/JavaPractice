//package com.example.client;
//
//// В клиентском классе соединяемся с сервером
//
//import java.io.DataInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.InetAddress;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//public class Client {
//
//    int port = 3124;
//    InetAddress ip = null;
//
//    public Client () {
//
//        try {
//            ip = InetAddress.getLocalHost();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//
//        // с помощью сокета подключаемся к серверу
//        try {
//            Socket socket = new Socket(ip, port);
//
//            InputStream is = socket.getInputStream();
//            DataInputStream dis = new DataInputStream(is);
//
//            String ans = dis.readUTF();
//            System.out.println("Server's answer: " + ans);
//            socket.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//
//        public static void main(String[] args) {
//        new Client();
//    }
//}
