package com.hyd.captcha.background;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class CrossBackgroundFilter implements BackgroundPainter {

    private int width = 15;

    @Override
    public void paint(BufferedImage bufferedImage, Graphics2D g) {
        int x = 0, offset = 1;
        int height = bufferedImage.getHeight();

        List<BufferedImage> subImages = new ArrayList<>();

        while (x < bufferedImage.getWidth()) {
            subImages.add(bufferedImage.getSubimage(
                x, 0, Math.min(bufferedImage.getWidth() - x, width), height
            ));
            x += width;
        }

        x = 0;
        for (BufferedImage subImage : subImages) {
            g.drawImage(subImage, x, offset, null);
            x += width;
            offset = -offset;
        }
    }
}
