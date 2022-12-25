package com.smile.tank;

public interface FireStrategy {

    Bullet fire(Tank tank);

    default int getX(Tank tank) {
        return tank.getRect().x + ((int)tank.getRect().getWidth()/2);
    }

    default int getY(Tank tank) {
        return tank.getRect().y + ((int)tank.getRect().getHeight()/2);
    }

}
