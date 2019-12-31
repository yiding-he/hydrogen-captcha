package com.hyd.captcha;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class CaptchaGeneratorTest extends JFrame {

    public static void main(String[] args) {
        CaptchaGenerator captchaGenerator = new CaptchaGenerator();
        BufferedImage bufferedImage = captchaGenerator.generate(100, 40, "Styles");

        CaptchaGeneratorTest frame = new CaptchaGeneratorTest();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(bufferedImage)), BorderLayout.CENTER);

        frame.setVisible(true);
    }
}