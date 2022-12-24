package com.smile.run;

import com.smile.frame.GameFrame;

public class Run {

    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame();
        gameFrame.start();
        // 屏幕刷新
        new Thread(()-> {
            while(true) {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                gameFrame.repaint();
            }
        }).start();
    }
}
