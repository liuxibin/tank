package com.smile.tank;

import com.smile.config.ProConfig;
import com.smile.enums.DirectionEnum;
import com.smile.resource.ResourceLoad;

import java.awt.*;

public class Bullet {
    private final int w = ResourceLoad.bulletU.getHeight();
    private final int h = ResourceLoad.bulletU.getHeight();;
    // 坐标
    private int x, y;
    // 方向
    private DirectionEnum direction;
    // 移动步长
    private final int speed = Integer.parseInt(ProConfig.getProperties("BulletSpeed"));
    // 移动指令时间间隔
    private final long intervalMillis = 25;
    // 移动指令时间
    private long lastMillis = System.currentTimeMillis();
    // 归属
    private Tank source;

    private boolean live = true;

    private final Rectangle rect;

    public Bullet(int x, int y, DirectionEnum direction, Tank source) {
        this.x = x - (w/2);
        this.y = y- (h/2);
        this.direction = direction;
        this.source = source;
        this.rect = new Rectangle(x, y, w, h);
    }

    public void paint(Graphics g) {
        if (live) {
            this.move();
            switch (this.direction) {
                case LEFT:
                    g.drawImage(ResourceLoad.bulletL, x, y, null);
                    break;
                case UP:
                    g.drawImage(ResourceLoad.bulletU, x, y, null);
                    break;
                case RIGHT:
                    g.drawImage(ResourceLoad.bulletR, x, y, null);
                    break;
                case DOWN:
                    g.drawImage(ResourceLoad.bulletD, x, y, null);
                    break;
            }
        }

    }

    private void move() {
        if (this.intervalMillis > System.currentTimeMillis() - this.lastMillis) {
            // 指令时间间隔过短忽略
            return;
        }
        this.lastMillis = System.currentTimeMillis();
        switch (this.direction) {
            case LEFT:
                this.x -= this.speed;
                break;
            case UP:
                this.y -= this.speed;
                break;
            case RIGHT:
                this.x += this.speed;
                break;
            case DOWN:
                this.y += this.speed;
                break;
        }
        // update rect
        rect.x = this.x;
        rect.y = this.y;
    }

    public void die() {
        this.live = false;
    }

    public Tank getSource() {
        return source;
    }

    public Rectangle getRect() {
        return rect;
    }

    public boolean isLive() {
        return live;
    }
}
