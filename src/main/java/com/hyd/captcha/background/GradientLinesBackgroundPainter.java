package com.hyd.captcha.background;

import static com.hyd.captcha.CaptchaGenerator.random;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GradientLinesBackgroundPainter implements BackgroundPainter {

    @Override
    public void paint(BufferedImage bufferedImage, Graphics2D g) {
        int stroke = 10;
        int xmin = -stroke / 2;
        int xmax = bufferedImage.getWidth() + stroke / 2;
        int y1 = -stroke, y2 = bufferedImage.getHeight() + stroke;
        int x1 = random(xmin, xmax), x2 = random(xmin, xmax);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g.setStroke(new BasicStroke(stroke));

        for (int i = 0; i < 200; i++) {
            g.setPaint(new GradientPaint(
                x1, y1, new Color(random(0, 255), random(0, 255), random(0, 255)),
                x2, y2, new Color(random(0, 255), random(0, 255), random(0, 255))
            ));
            g.drawLine(x1, y1, x2, y2);
            x1 = random(xmin, xmax);
            x2 = random(xmin, xmax);
        }

    }
}
