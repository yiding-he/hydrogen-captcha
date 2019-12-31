package com.hyd.captcha;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class CaptchaGeneratorTest extends JFrame {

    public static void main(String[] args) {
        CaptchaGenerator captchaGenerator = new CaptchaGenerator();
        BufferedImage bufferedImage = captchaGenerator.generate(100, 40, "Styles");
        List<BufferedImage> charImages = CaptchaGenerator.getCharImages();

        CaptchaGeneratorTest frame = new CaptchaGeneratorTest();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(imageLabel(bufferedImage), BorderLayout.CENTER);

        JPanel charImagePanel = new JPanel();
        charImagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        charImages.forEach(charImage -> charImagePanel.add(imageLabel(charImage)));
        frame.getContentPane().add(charImagePanel, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    private static JLabel imageLabel(BufferedImage bufferedImage) {
        return new JLabel(new ImageIcon(bufferedImage));
    }
}