package com.smile.resource;

import com.smile.utils.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceLoad {

    public static BufferedImage tankL,tankU,tankR,tankD;
    public static BufferedImage bulletL,bulletU,bulletR,bulletD;
    public static BufferedImage explodes[] = new BufferedImage[16];

    static {
        try {
            tankU = ImageIO.read(ResourceLoad.class.getClassLoader().getResourceAsStream("images/GoodTank2.png"));
            tankL = ImageUtil.rotateImage(tankU, -90);
            tankR = ImageUtil.rotateImage(tankU, 90);
            tankD = ImageUtil.rotateImage(tankU, 180);

            bulletU = ImageIO.read(ResourceLoad.class.getClassLoader().getResourceAsStream("images/bulletU.png"));
            bulletL = ImageUtil.rotateImage(bulletU, -90);
            bulletR = ImageUtil.rotateImage(bulletU, 90);
            bulletD = ImageUtil.rotateImage(bulletU, 180);

            for(int i=0; i<16; i++)
                explodes[i] = ImageIO.read(ResourceLoad.class.getClassLoader().getResourceAsStream("images/e" + (i+1) + ".gif"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
