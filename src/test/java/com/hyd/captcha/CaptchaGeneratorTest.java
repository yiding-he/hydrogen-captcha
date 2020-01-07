package com.hyd.captcha;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.*;

public class CaptchaGeneratorTest extends JFrame {

    public static void main(String[] args) throws Exception {

        FontRepository.loadFonts("./sample-fonts/");

        CaptchaGenerator captchaGenerator = new CaptchaGenerator();
        JLabel imageLabel = new JLabel();
        JPanel charImagePanel = new JPanel();

        Runnable displayImage = () -> {
            BufferedImage bufferedImage = captchaGenerator.generate(250, 70, "captcha12345");
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