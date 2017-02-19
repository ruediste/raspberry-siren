package com.github.ruediste.raspberrySiren;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;

public class MailPoller {

    public static void main(String[] args) {
        poll();
    }

    public static void start() {
        Thread thread = new Thread(MailPoller::pollLoop, "mailPoller");
        thread.setDaemon(true);
        thread.start();
    }

    public static void pollLoop() {
        while (true) {
            try {
                poll();
            } catch (Throwable t) {
                t.printStackTrace();
            }

            try {
                Thread.sleep(StateController.isAlarm() ? 5 * 1000 : 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void poll() {
        Properties props = new Properties();
        try {
            // props.load(new FileInputStream(new File("C:\\smtp.properties")));
            Session session = Session.getDefaultInstance(props, null);

            Store store = session.getStore("imaps");
            System.out.println(System.getProperty("MAIL_SERVER"));
            System.out.println(System.getProperty("MAIL_USER"));
            System.out.println(System.getProperty("MAIL_PASSWORD"));
            store.connect(System.getProperty("MAIL_SERVER"), 993, System.getProperty("MAIL_USER"),
                    System.getProperty("MAIL_PASSWORD"));

            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            int messageCount = inbox.getMessageCount();
            int unreadMessageCount = inbox.getUnreadMessageCount();

            // System.out.println("Total Messages:- " + messageCount);
            // System.out.println("Unread Messages:- " + unreadMessageCount);

            StateController.setAlarm(unreadMessageCount > 0);

            // Message[] messages = inbox.getMessages();
            // System.out.println("------------------------------");
            // for (int i = 0; i < 10; i++) {
            // System.out.println("Mail Subject:- " + messages[i].getSubject());
            // }
            inbox.close(true);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}