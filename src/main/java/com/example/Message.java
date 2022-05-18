package com.example;

import javax.persistence.*;

@Entity
@Table (name = "msg")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "time")
    private int time;

    @Column(name = "message")
    private String message;

    @Column(name = "user_id")
    private int user_id;

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {

        int seconds, minutes, hours;
        String s = "";
        seconds = time % 60;
        minutes = time / 60;
        hours = minutes / 60;
        minutes %= 60;

        s+=hours + ":" + minutes + ":" + seconds + "  " + "'" + message + "'" + "from user " + user_id + "\n";
        return s;
    }
}

