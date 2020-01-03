package com.hyd.captcha;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
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

    //////////////////////////////////////////////////////////////

    private CharPropertyFactory charPropertyFactory = new DefaultCharPropertyFactory();

    public void setCharPropertyFactory(CharPropertyFactory charPropertyFactory) {
        this.charPropertyFactory = charPropertyFactory;
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

        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            BufferedImage charImage = generateCharImage(aChar, font, frc);

            int[] pos = adjustCharPosition(height, cellWidth, i, charImage);

            g.drawImage(charImage, pos[0], pos[1], charImage.getWidth(), charImage.getHeight(), null);
            charImages.add(charImage);
        }

        CHAR_IMAGE_LIST.set(charImages);
        return bufferedImage;
    }

    private int[] adjustCharPosition(int height, int cellWidth, int i, BufferedImage charImage) {
        int hCenter = cellWidth * (i + 1);
        hCenter = random(hCenter - cellWidth / 4, hCenter + cellWidth / 4);

        int vCenter = random(charImage.getHeight() * 2 / 3, height - (charImage.getHeight() * 2 / 3));

        return new int[]{
            hCenter - charImage.getWidth() / 2,
            vCenter - charImage.getHeight() / 2
        };
    }

    private int random(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    private static AffineTransform translate(double x, double y) {
        AffineTransform transform = new AffineTransform();
        transform.translate(x, y);
        return transform;
    }

    private Shape resetPosition(Shape shape) {
        AffineTransform transform = translate(-shape.getBounds().x, -shape.getBounds().y);
        return transform.createTransformedShape(shape);
    }

    private Shape apply(Shape shape, AffineTransform transform) {
        return resetPosition(transform.createTransformedShape(shape));
    }

    private BufferedImage generateCharImage(char aChar, Font font, FontRenderContext frc) {
        char[] aCharArr = {aChar};

        CharProperty charProperty = charPropertyFactory.getCharProperty(aChar);

        Shape charShape = new TextLayout(new String(aCharArr), font, frc).getOutline(null);
        charShape = resetPosition(charShape);

        //////////////////////////////////////////////////////////////

        if (charProperty.getRotate() != 0) {
            AffineTransform transform = new AffineTransform();
            Rectangle bounds = charShape.getBounds();
            if (charProperty.getRotate() > 0) {
                transform.translate(bounds.getHeight() * Math.abs(Math.sin(charProperty.getRotate())), 0);
            } else {
                transform.translate(0, bounds.getWidth() * Math.abs(Math.sin(charProperty.getRotate())));
            }
            transform.rotate(charProperty.getRotate());
            charShape = apply(charShape, transform);
        }

        if (charProperty.getShear() != null) {
            AffineTransform transform = new AffineTransform();
            transform.shear(charProperty.getShear()[0], charProperty.getShear()[1]);
            charShape = apply(charShape, transform);
        }

        //////////////////////////////////////////////////////////////

        BufferedImage image = new BufferedImage(
            charShape.getBounds().width + 2, charShape.getBounds().height + 2, BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setComposite(AlphaComposite.Src);

        if (charProperty.getStrikePaint() != null) {
            g.setPaint(charProperty.getStrikePaint());
            g.draw(charShape);
        }

        if (charProperty.getFillPaint() != null) {
            g.setPaint(charProperty.getFillPaint());
            g.fill(charShape);
        }

        return image;
    }
}
