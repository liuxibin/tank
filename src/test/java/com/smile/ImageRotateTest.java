package com.smile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageRotateTest {

    @Test
    public void test() {

        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("images/GoodTank1.png");
        try {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            Assertions.assertNotNull(bufferedImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
