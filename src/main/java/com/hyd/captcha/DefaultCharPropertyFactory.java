package com.hyd.captcha;

import static com.hyd.captcha.CaptchaGenerator.random;

public class DefaultCharPropertyFactory implements CharPropertyFactory {

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
