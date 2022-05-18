package timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class GUI extends JFrame{

    private JLabel labelNow = new JLabel("Time now:");
    private JLabel timeNow = new JLabel("00:00:00");
    private JButton buttonStart = new JButton ("Start");
    private JButton buttonStop = new JButton("Stop");
    private JButton buttonAdd = new JButton("Add");
    private JLabel labelHours = new JLabel("Hours: ");
    private JLabel labelMinutes = new JLabel("Minutes: ");
    private JLabel labelSeconds = new JLabel("Seconds: ");


    private JSpinner spinnerHours = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));
    private JSpinner spinnerMinutes = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
    private  JSpinner spinnerSeconds = new JSpinner(new SpinnerNumberModel(10, 0, 59, 1));

    private JLabel labelText = new JLabel("com.example.Message:");
    private JTextField message = new JTextField("", 255);

    private JLabel labelMessages = new JLabel("All messages:");
    private JLabel labelEvent = new JLabel("-");

    public GUI(String title) {
        super(title);
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


        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // start action

                //String text = "";
                //text += "Start\n";
                //JOptionPane.showMessageDialog(null, text, "Start message", JOptionPane.PLAIN_MESSAGE);

                start();
            }
        });
        container.add(buttonStart);

        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //add action
                add();
            }
        });

        container.add(buttonAdd);


        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // stop action
                //String text = "";
                //text += "Stop\n";
                //JOptionPane.showMessageDialog(null, text, "Stop message", JOptionPane.PLAIN_MESSAGE);
                stop();
            }
        });
        container.add(buttonStop);

        container.add(labelMessages);
        container.add(labelEvent);

    }


    private Thread t, threadAdd;
    String str = "";
    int time = 0;
    int timer = 0;
    Map<Integer,String> messages = new HashMap<>();

    public void addTime(){
        time ++;
        int hours, minutes, seconds;
        seconds = time % 60;
        minutes = time / 60;
        hours = minutes / 60;
        minutes %= 60;

        timeNow.setText(hours + ":" + minutes + ":" + seconds);
        repaint();
    }

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
                            addTime();
                            Thread.sleep(1000);
                            for(Map.Entry<Integer, String> item : messages.entrySet()){
                                if (time == item.getKey()) {
                                    JOptionPane.showMessageDialog(this, item.getValue());
                                    repaint();
                                }
                            }

                        }
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Stop message");
                        return;
                    }

                }
        );
        t.start();

    }

    String s = "";
    int hours, minutes, seconds;
    boolean runAdd = true;
    public void add() {
        Integer t = 3600 * (int) spinnerHours.getValue() + 60 * (int) spinnerMinutes.getValue() + (int) spinnerSeconds.getValue();
        messages.put(t, message.getText());
        s = "";
        for(Map.Entry<Integer, String> item : messages.entrySet()){

            seconds = item.getKey() % 60;
            minutes = item.getKey() / 60;
            hours = minutes / 60;
            minutes %= 60;

            s+=hours + ":" + minutes + ":" + seconds + "  " + item.getValue() + "\n";

        }
        labelEvent.setText(s);
        repaint();
    }

    public void stop() {
        if (t == null) return;  // если ещё не создавали поток, то ничего не надо делать
        run = false;
        t.interrupt();
        //t.stop();

    }

}
