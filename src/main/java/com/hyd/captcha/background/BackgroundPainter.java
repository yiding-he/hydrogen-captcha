package com.hyd.captcha.background;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public interface BackgroundPainter {

    void paint(BufferedImage bufferedImage, Graphics2D graphics2D);
}
