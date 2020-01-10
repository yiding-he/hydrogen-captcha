package com.hyd.captcha;

import com.hyd.captcha.background.CrossBackgroundFilter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import javax.swing.*;

public class CaptchaGeneratorTest extends JFrame {

    public static void main(String[] args) throws Exception {

        FontRepository.loadFont("./sample-fonts/RobotoSlab.ttf");
        // FontRepository.loadFonts("./sample-fonts/");

        CaptchaGenerator captchaGenerator = new CaptchaGenerator();
        captchaGenerator.addBackgroundPainter(new CrossBackgroundFilter());

        JLabel imageLabel = new JLabel();
        JPanel charImagePanel = new JPanel();

        Function<Integer, String> randStrGenerator = size -> {
            Random random = new SecureRandom();
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < size; i++) {
                sb.append(chars.charAt(random.nextInt(chars.length())));
            }
            return sb.toString();
        };

        Runnable displayImage = () -> {
            String str = randStrGenerator.apply(6);
            BufferedImage bufferedImage = captchaGenerator.generate(250, 70, str);
            imageLabel.setIcon(new ImageIcon(bufferedImage));

            List<BufferedImage> charImages = CaptchaGenerator.getCharImages();
            charImagePanel.removeAll();
            charImages.forEach(charImage -> charImagePanel.add(imageLabel(charImage)));
            charImagePanel.revalidate();
            charImagePanel.repaint();
        };

        CaptchaGeneratorTest frame = new CaptchaGeneratorTest();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(imageLabel, BorderLayout.CENTER);

        charImagePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        frame.getContentPane().add(charImagePanel, BorderLayout.NORTH);

        JButton button = new JButton("刷新");
        button.addActionListener(e -> displayImage.run());
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonsPanel.add(button);
        frame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

        frame.setLocation(500, 300);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                displayImage.run();
            }
        });
        frame.setVisible(true);
    }

    private static JLabel imageLabel(BufferedImage bufferedImage) {
        return new JLabel(new ImageIcon(bufferedImage));
    }
}