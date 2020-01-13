package com.hyd.captcha;

import java.security.SecureRandom;
import java.util.Random;

public class DefaultCharPropertyFactory implements CharPropertyFactory {

    private static final Random RANDOM = new SecureRandom();

    private static double random(double min, double max) {
        return RANDOM.nextDouble() * (max - min) + min;
    }

    private static int random(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    @Override
    public CharProperty getCharProperty(char ch) {
        CharProperty charProperty = new CharProperty();
        charProperty.setFillPaint(null);
        charProperty.setStrikePaint(ImageUtil.randomColor(180, 255));
        charProperty.setFillPaint(ImageUtil.randomColor(0, 120));
        charProperty.setRotate(random(-Math.PI / 6, Math.PI / 6));
        charProperty.setShear(new double[]{0, random(-0.25, 0.25)});
        charProperty.setFont(FontRepository.pickRandomFont());
        return charProperty;
    }
}
