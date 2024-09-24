package temporizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageAnimation extends JFrame {
    private JLabel imageLabel;
    private Timer timer;
    private JSlider speedSlider;
    private int currentImageIndex = 0;
    private int delay = 500; 
    private final String[] imageNames = {"man1.png", "man2.png", "man3.png", "man4.png", "man5.png", "man6.png", "man7.png", "man8.png"};
    private ImageIcon[] images;

    public ImageAnimation() {
        // Cargar imágenes
        images = new ImageIcon[imageNames.length];
        for (int i = 0; i < imageNames.length; i++) {
            images[i] = new ImageIcon(getClass().getResource(imageNames[i]));
        }

        setTitle("Animación de Imágenes");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setIcon(images[0]); 
        add(imageLabel, BorderLayout.CENTER);

        speedSlider = new JSlider(JSlider.HORIZONTAL, 100, 2000, delay);
        speedSlider.setMajorTickSpacing(500);
        speedSlider.setMinorTickSpacing(100);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                delay = speedSlider.getValue();
                timer.setDelay(delay); // Cambiar el delay del temporizador
            }
        });
        add(speedSlider, BorderLayout.SOUTH);

        // Temporizador para cambiar las imágenes
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentImageIndex = (currentImageIndex + 1) % images.length;
                imageLabel.setIcon(images[currentImageIndex]);
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ImageAnimation().setVisible(true);
            }
        });
    }
}

