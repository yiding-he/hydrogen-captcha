package com.hyd.captcha;

import java.awt.Paint;

public class CharProperty {

    private Paint fillPaint;

    private Paint strikePaint;

    private double[] shear;

    private double rotate;

    public Paint getFillPaint() {
        return fillPaint;
    }

    public void setFillPaint(Paint fillPaint) {
        this.fillPaint = fillPaint;
    }

    public Paint getStrikePaint() {
        return strikePaint;
    }

    public void setStrikePaint(Paint strikePaint) {
        this.strikePaint = strikePaint;
    }

    public double getRotate() {
        return rotate;
    }

    public void setRotate(double rotate) {
        this.rotate = rotate;
    }

    public double[] getShear() {
        return shear;
    }

    public void setShear(double[] shear) {
        this.shear = shear;
    }
}
