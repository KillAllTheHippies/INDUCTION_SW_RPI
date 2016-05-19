package controller;


import view.QuizFrame;

import javax.swing.*;

public class VideoPlayer {

    public VideoPlayer() {
        runCommand("omxplayer -r -o hdmi InductionVideo.mp4");

        //
        QuizFrame qf = new QuizFrame("Induction Assessment");
        qf.setSize(640,480);
        qf.setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
    }


    public static void runCommand(String command)
    {

        Process p;
        try {
            System.out.println("run command " + command);
            p = Runtime.getRuntime().exec(new String[]{"bash", "-c",command});

//            MyInputStreamReader reader1 = new MyInputStreamReader(p.getInputStream());
//            reader1.setTag("in");
//            reader1.start();
//
//            MyInputStreamReader reader2 = new MyInputStreamReader(p.getErrorStream());
//            reader2.setTag("in");
//            reader2.start();


            p.waitFor();
            System.out.println ("exit: " + p.exitValue());
            p.destroy();


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}

