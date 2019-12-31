package com.hyd.captcha;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CaptchaGenerator {

    private static final ThreadLocal<java.util.List<BufferedImage>> CHAR_IMAGE_LIST = new ThreadLocal<>();

    public static java.util.List<BufferedImage> getCharImages() {
        return CHAR_IMAGE_LIST.get();
    }

    public BufferedImage generate(int width, int height, String content) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        char[] chars = content.toCharArray();

        int cellWidth = width / chars.length;
        int charWidth = cellWidth * 3;
        Font font = new Font("Impact", Font.PLAIN, charWidth);

        FontRenderContext frc = new FontRenderContext(null, false, false);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0, 0, width, height);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        g.setColor(Color.BLACK);

        java.util.List<BufferedImage> charImages = new ArrayList<>();

        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];

            BufferedImage charImage = generateCharImage(aChar, font, frc);

            int x = cellWidth * i;
            int y = (height - charImage.getHeight()) / 2;
            g.drawImage(charImage, x, y, charImage.getWidth(), charImage.getHeight(), null);

            charImages.add(charImage);
        }

        CHAR_IMAGE_LIST.set(charImages);
        return bufferedImage;
    }

    private BufferedImage generateCharImage(char aChar, Font font, FontRenderContext frc) {
        char[] aCharArr = {aChar};

        Rectangle2D bounds = font.getStringBounds(aCharArr, 0, 1, frc);
        BufferedImage image = new BufferedImage(
                (int) bounds.getWidth(), (int)bounds.getHeight(), BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = (Graphics2D) image.getGraphics();
        FontMetrics fm   = g.getFontMetrics(font);
        int y = fm.getAscent();

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setComposite(AlphaComposite.Src);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawChars(aCharArr, 0, 1, 0, y);
        g.drawRect(0, 0, image.getWidth() - 1, image.getHeight() - 1);
        return image;
    }
}
