package com.hyd.captcha;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class CaptchaGenerator {

    private static final ThreadLocal<java.util.List<BufferedImage>> CHAR_IMAGE_LIST = new ThreadLocal<>();

    private static final Random RANDOM = new SecureRandom();

    public static java.util.List<BufferedImage> getCharImages() {
        return CHAR_IMAGE_LIST.get();
    }

    public BufferedImage generate(int width, int height, String content) {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        char[] chars = content.toCharArray();

        int cellWidth = width / (chars.length + 1);
        int charWidth = (int) (height * 0.5);
        Font font = new Font("Impact", Font.PLAIN, charWidth);

        FontRenderContext frc = new FontRenderContext(null, false, false);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

        java.util.List<BufferedImage> charImages = new ArrayList<>();
        int lastX = -4;

        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];

            BufferedImage charImage = generateCharImage(aChar, font, frc);

            int x = (int) (cellWidth * (i + 0.5));
            int y = (height - charImage.getHeight()) / 2;

            x += RANDOM.nextInt(cellWidth) - (cellWidth / 2);
            y += random(-y + 1, height - charImage.getHeight() - 1 - y);

            x = Math.max(x, lastX + (cellWidth * 2 / 3));
            lastX = x;

            g.drawImage(charImage, x, y, charImage.getWidth(), charImage.getHeight(), null);

            charImages.add(charImage);
        }

        CHAR_IMAGE_LIST.set(charImages);
        return bufferedImage;
    }

    private int random(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    private BufferedImage generateCharImage(char aChar, Font font, FontRenderContext frc) {
        char[] aCharArr = {aChar};

        TextLayout textLayout = new TextLayout(new String(aCharArr), font, frc);
        Shape outline = textLayout.getOutline(null);

        Rectangle2D bounds = outline.getBounds();
        BufferedImage image = new BufferedImage(
                (int) bounds.getWidth() + 2, (int)bounds.getHeight() + 2, BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = (Graphics2D) image.getGraphics();
        g.translate(-bounds.getX() + 1, -bounds.getY() + 1);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setComposite(AlphaComposite.Src);

        g.setColor(Color.BLACK);
        g.draw(outline);

        return image;
    }
}
