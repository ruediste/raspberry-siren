package com.github.ruediste.raspberrySiren;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Playmusic {

    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
    }

    private static Clip clip;

    static {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (InputStream in = Playmusic.class.getClassLoader().getResourceAsStream("truck_horn.wav")) {
                copy(in, baos);
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new ByteArrayInputStream(baos.toByteArray()));
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, AudioSystem.NOT_SPECIFIED, 8, 1, 4,
                    AudioSystem.NOT_SPECIFIED, true);
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioIn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void turnOn() {
        clip.setLoopPoints(0, -1);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    public static void turnOff() {
        clip.stop();
    }

}