package com.github.ruediste.raspberrySiren;

public class Main {

    public static void main(String[] args) {
        MailPoller.start();
        synchronized (Main.class) {
            try {
                Main.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
