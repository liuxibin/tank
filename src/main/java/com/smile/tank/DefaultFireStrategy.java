package com.smile.tank;

import java.util.Collections;

public class DefaultFireStrategy implements FireStrategy{

    @Override
    public Bullet fire(Tank tank) {
        return new Bullet(getX(tank), getY(tank), tank.getDirection(), tank);
    }

}
