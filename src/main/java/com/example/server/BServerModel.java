package com.example.server;

public class BServerModel {
    static ServerModel m = new ServerModel();

    static public ServerModel build() {
        return m;
    }
}
