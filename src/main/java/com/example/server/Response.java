package com.example.server;

import com.example.Message;

import java.util.ArrayList;
import java.util.List;

public class Response {
    // Массив сообщений
    Message msg = new Message();
    ArrayList <Message> msgs = new ArrayList<>();
    Action action;

    // конструктор принимает список сообщений, которые
    // мы хотим отправить
    public Response(List<Message> msgs, Action action) {
        this.msgs.addAll(msgs);
        this.action = action;
    }

    public Response (Message msg, Action action) {
        this.msg = msg;
        this.action = action;
    }

    public Response(Action action) {
        this.action = action;
    }

    public ArrayList <Message> getMsgs() {
        return msgs;
    }

    public void setMsgs(ArrayList <Message> msgs) {
        this.msgs = msgs;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Message getMsg() {
        return msg;
    }

    public void setMsg(Message msg) {
        this.msg = msg;
    }
}

