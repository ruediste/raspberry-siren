package com.github.ruediste.raspberrySiren;

public class StateController {

    private static boolean isAlarm;

    public synchronized static boolean isAlarm() {
        return isAlarm;
    }

    public synchronized static void setAlarm(boolean alarm) {
        if (alarm && !isAlarm) {
            turnAlarmOn();
            isAlarm = true;
        } else if (!alarm && isAlarm) {
            turnAlarmOff();
            isAlarm = false;
        }
    }

    private static void turnAlarmOff() {
        Playmusic.turnOff();
    }

    private static void turnAlarmOn() {
        Playmusic.turnOn();
    }
}
