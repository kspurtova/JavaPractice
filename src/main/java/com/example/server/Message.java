//package server;
//
//import javax.persistence.*;
//import java.util.HashMap;
//import java.util.Map;
//
////@Entity
////@Table (name = "msg")
//public class com.example.Message {
////    @Id
////    @GeneratedValue(strategy = GenerationType.AUTO)
////    private int id;
////
////    @Column (name = "time")
////    private int time;
////
////    @Column (name = "message")
////    private String message;
////
////    @Column (name = "user_id")
////    private int user_id;
////
////    // 0 - server's id
//
//    Map<Integer, String> messages = new HashMap<>();
//    int id = -1;
//    private String type = "";
//    int timer = 0;
//
//    public Map<Integer, String> getMessages() {
//        return messages;
//    }
//
//    public void setMessages(Map<Integer, String> messages) {
//        this.messages = messages;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public int getTimer() {
//        return timer;
//    }
//
//    public void setTimer(int timer) {
//        this.timer = timer;
//    }
//
//    @Override
//    public String toString() {
//        int seconds, minutes, hours;
//        String s = "";
//        for(Map.Entry<Integer, String> item : messages.entrySet()){
//        seconds = item.getKey() % 60;
//        minutes = item.getKey() / 60;
//        hours = minutes / 60;
//        minutes %= 60;
//
//        s+=hours + ":" + minutes + ":" + seconds + "  " + item.getValue() + "\n";
//
//    }
//        return s;
//    }
//
//    public String timerToString() {
//        int seconds, minutes, hours;
//        String s = "";
//        seconds = timer % 60;
//        minutes = timer / 60;
//        hours = minutes / 60;
//        minutes %= 60;
//
//        s+=hours + ":" + minutes + ":" + seconds  + "\n";
//
//        return s;
//    }
//}
