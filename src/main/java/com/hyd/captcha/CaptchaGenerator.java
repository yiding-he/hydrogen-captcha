package com.hyd.captcha;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import sun.swing.SwingUtilities2;

public class CaptchaGenerator {

    public BufferedImage generate(int width, int height, String content) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        char[] chars = content.toCharArray();

        int cellWidth = width / chars.length;
        int charWidth = cellWidth * 2;
        Font font = new Font("DialogInput", Font.ITALIC, charWidth);

        FontRenderContext frc = new FontRenderContext(null, false, false);
        Graphics g = bufferedImage.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);

        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];

            BufferedImage charImage = generateCharImage(aChar, font, frc);

            int x = cellWidth * i;
            int y = height - ((height - charImage.getHeight()) / 2);
            g.drawImage(charImage, x, y, null);
        }

        return bufferedImage;
    }

    private BufferedImage generateCharImage(char aChar, Font font, FontRenderContext frc) {
        char[] aCharArr = {aChar};
        Rectangle2D bounds = font.getStringBounds(aCharArr, 0, 1, frc);
        BufferedImage image = new BufferedImage(
            (int) bounds.getWidth(), (int)bounds.getHeight(), BufferedImage.TYPE_INT_RGB
        );
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawChars(aCharArr, 0, 1, 0, image.getHeight());
        return image;
    }
}
