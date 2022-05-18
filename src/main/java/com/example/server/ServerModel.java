package com.example.server;

import com.example.Message;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ServerModel {
//    ArrayList<IObserver> all_observers = new ArrayList<>();
//
//    // метод проходит по всем наблюдателям и оповещает об изменениях
//    // данных на сервере
//    void update() {
//        for (IObserver iObserver : all_observers) {
//            iObserver.update();
//        }
//    }

    public ServerModel () {
        // один раз создаем и удаляем сессию, чтобы проверить
        // что всё нормально с базой данных
        Session session = BHibernate.getSessionFactory().openSession();
        session.close();
    }

    // сохранить сообщение
    public void save (Message m) {
        Session session = BHibernate.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(m);
        tx1.commit();
        session.close();
        //update();
    }

    // удалить сообщение
    public void remove (Message m) {
        Session session = BHibernate.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.remove(m);
        tx1.commit();
        session.close();
        //update();
    }

    // получить список всех сообщений, что есть на сервере
    List<Message> get() {
        List<Message> msg = (List<Message>)BHibernate.
                getSessionFactory().openSession().createQuery("From Message").list();
        return msg;
    }

    // На модель мы можем подписать наблюдателя
    // Как только наблюдатель подписывается с моделью, он
    // добавляется в список наблюдателей
//    public void addObserver(IObserver o) {
//        all_observers.add(o);
//        update(); // подключился новый наблюдатель,
//        // значит, нужно разослать данные
//    }
}

