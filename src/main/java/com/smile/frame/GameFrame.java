package com.smile.frame;

import com.smile.enums.DirectionEnum;
import com.smile.tank.Bullet;
import com.smile.tank.Explode;
import com.smile.tank.Tank;


import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameFrame extends Frame {

    public static final int GAME_WIDTH = 1080, GAME_HEIGHT = 720;

    private Tank myTank;

    private final List<Tank> otherTankList = new ArrayList<>();;

    private final List<Bullet> bulletList = new ArrayList<>();

    private final List<Explode> explodeList = new ArrayList<>();

    private final Random random = new Random();

    public GameFrame() throws HeadlessException {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setTitle("坦克大战");
        setResizable(false);
        setVisible(true);

        addKeyListener(new MyKeyListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    public void start() {
        this.myTank = new Tank(200, 200, DirectionEnum.UP);
        for (int i = 0; i < 6; i++) {
            Tank tank = new Tank(100 + (i * 80), 100, DirectionEnum.DOWN);
            tank.move();
            otherTankList.add(tank);
        }
        // 自动移动与随机开火
        new Thread(()-> {
            while(true) {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < otherTankList.size(); i++) {
                    Tank tank = otherTankList.get(i);
                    if(random.nextInt(100) > 95) {
                        bulletList.add(tank.fire());
                    }
                    if(random.nextInt(100) > 95) {
                        randomDir(tank);
                    }
                }
                this.collisionDetection();
            }
        }).start();
        // 碰撞检测
        new Thread(()-> {
            while(true) {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.collisionDetection();
            }
        }).start();
    }

    private void randomDir(Tank tank) {
        tank.setDirection(DirectionEnum.values()[random.nextInt(4)]);
    }

    private Image offScreenImage = null;
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        // 自己的坦克
        if (myTank != null) {
            myTank.paint(g);
        }
        // 其它坦克
        for (int i = 0; i < otherTankList.size(); i++) {
            Tank tank = otherTankList.get(i);
            if (tank.isLive()) {
                tank.paint(g);
            } else {
                otherTankList.remove(tank);
            }
        }
        // 已发出的子弹
        for (int i = 0; i < bulletList.size(); i++) {
            Bullet bullet = bulletList.get(i);
            if (bullet.isLive()) {
                bullet.paint(g);
            } else {
                bulletList.remove(bullet);
            }
        }
        // 爆炸效果
        for (int i = 0; i < explodeList.size(); i++) {
            Explode explode = explodeList.get(i);
            if (explode.isLive()) {
                explode.paint(g);
            } else {
                explodeList.remove(explode);
            }
        }
    }

    public void collisionDetection() {
        for (int i = 0; i < bulletList.size(); i++) {
            Bullet bullet = bulletList.get(i);
            if (bullet.getSource() == this.myTank) {
                for (int j = 0; j < otherTankList.size(); j++) {
                    Tank tank = otherTankList.get(j);
                    if (bullet.getRect().intersects(tank.getRect())) {
                        bullet.die();
                        tank.die();
                        explodeList.add(new Explode(tank.getRect().x + (tank.getRect().width / 2), tank.getRect().y + (tank.getRect().height / 2)));
                    }
                }
            }/* else {
                if (bullet.getRect().intersects(this.myTank.getRect())) {
                    bullet.die();
                    this.myTank.die();
                    explodeList.add(new Explode(this.myTank.getRect().x + (this.myTank.getRect().width / 2), this.myTank.getRect().y + (this.myTank.getRect().height / 2)));
                }
            }*/
        }
    }

    class MyKeyListener extends KeyAdapter {

        private boolean left = false;
        private boolean up = false;
        private boolean right = false;
        private boolean down = false;

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    left = true;
                    myTank.setDirection(DirectionEnum.LEFT);
                    myTank.move();
                    break;
                case KeyEvent.VK_UP:
                    up = true;
                    myTank.setDirection(DirectionEnum.UP);
                    myTank.move();
                    break;
                case KeyEvent.VK_RIGHT:
                    right = true;
                    myTank.setDirection(DirectionEnum.RIGHT);
                    myTank.move();
                    break;
                case KeyEvent.VK_DOWN:
                    down = true;
                    myTank.setDirection(DirectionEnum.DOWN);
                    myTank.move();
                    break;
                case KeyEvent.VK_CONTROL:
                    bulletList.add(myTank.fire());
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    left = false;
                    break;
                case KeyEvent.VK_UP:
                    up = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    right = false;
                    break;
                case KeyEvent.VK_DOWN:
                    down = false;
                    break;
            }
            if (!left && !up && !right && !down) {
                myTank.stop();
            }
        }
    }
}
