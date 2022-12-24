package com.smile.tank;


import com.smile.enums.DirectionEnum;
import com.smile.frame.GameFrame;
import com.smile.resource.ResourceLoad;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Tank {
    private final int w = ResourceLoad.tankU.getWidth();
    private final int h = ResourceLoad.tankU.getHeight();;
    // 坐标
    private int x, y;
    // 方向
    private DirectionEnum direction;
    // 移动步长
    private final int speed = 5;
    // 碰撞区域
    private final Rectangle rect;

    private boolean live = true;

    private boolean moving = false;


    public Tank(int x, int y, DirectionEnum direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.rect = new Rectangle(x, y, w, h);
    }

    public void paint(Graphics g) {
        if (this.live) {
            BufferedImage tank = null;
            switch(direction) {
                case LEFT:
                    tank = ResourceLoad.tankL;
                    this.x = this.moving ? this.x - this.speed : this.x;
                    break;
                case UP:
                    tank = ResourceLoad.tankU;
                    this.y = this.moving ? this.y - this.speed : this.y;
                    break;
                case RIGHT:
                    tank = ResourceLoad.tankR;
                    this.x = this.moving ? this.x + this.speed : this.x;
                    break;
                case DOWN:
                    tank = ResourceLoad.tankD;
                    this.y = this.moving ? this.y + this.speed : this.y;
                    break;
            }
            boundsCheck();
            // update rect
            rect.x = this.x;
            rect.y = this.y;
            g.drawImage(tank, x, y, null);
        }
    }

    public void setDirection(DirectionEnum direction) {
        this.direction = direction;
    }

    public void move() {
        this.moving = true;
    }

    public void stop() {
        this.moving = false;
    }

    private void boundsCheck() {
        if (this.x < 2) x = 2;
        if (this.y < 28) y = 28;
        if (this.x > GameFrame.GAME_WIDTH- this.w -2) x = GameFrame.GAME_WIDTH - this.w -2;
        if (this.y > GameFrame.GAME_HEIGHT - this.h -2 ) y = GameFrame.GAME_HEIGHT -this.h -2;
    }

    public void die() {
        this.live = false;
    }

    public Bullet fire() {
        return new Bullet(this.x + (this.w/2), this.y + (this.h/2), direction, this);
    }

    public Rectangle getRect() {
        return rect;
    }

    public boolean isLive() {
        return live;
    }
}
