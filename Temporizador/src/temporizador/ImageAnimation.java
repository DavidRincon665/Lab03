package temporizador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ImageAnimation {

    private JFrame frame;
    private JLabel imageLabel;
    private Timer animationTimer;
    private JSlider speedSlider;
    private List<ImageIcon> images;
    private int currentImageIndex = 0;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ImageAnimation().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Animación de Imágenes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Cargar imágenes
        loadImages();

        // Crear y agregar el JLabel para mostrar las imágenes
        imageLabel = new JLabel();
        // Establecer un tamaño preferido para el JLabel
        imageLabel.setPreferredSize(new Dimension(600, 400));
        frame.add(imageLabel, BorderLayout.CENTER);

        // Crear y agregar el control deslizante para ajustar la velocidad
        speedSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 50);
        speedSlider.setMajorTickSpacing(10);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.addChangeListener(e -> updateTimerInterval());

        JPanel sliderPanel = new JPanel();
        sliderPanel.add(new JLabel("Velocidad de Animación:"));
        sliderPanel.add(speedSlider);
        frame.add(sliderPanel, BorderLayout.SOUTH);

        // Crear el temporizador para la animación
        animationTimer = new Timer(getTimerInterval(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextImage();
            }
        });
        animationTimer.start();

        // Mostrar la primera imagen
        showNextImage();

        frame.setVisible(true);
    }

    private void loadImages() {
        images = new ArrayList<>();
        // Cargar las imágenes desde archivos
        String[] imageFiles = {"man1.png", "man2.png", "man3.png", "man4.png", "man5.png", "man6.png", "man7.png", "man8.png"};
        for (String file : imageFiles) {
            ImageIcon imageIcon = new ImageIcon(file);
            if (imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.out.println("Error al cargar " + file);
            } else {
                images.add(imageIcon);
            }
        }
        if (images.isEmpty()) {
            System.out.println("No se cargaron imágenes.");
        }
    }

    private void showNextImage() {
        if (images.isEmpty()) return;
        ImageIcon currentImageIcon = images.get(currentImageIndex);
        Image scaledImage = currentImageIcon.getImage().getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        currentImageIndex = (currentImageIndex + 1) % images.size();
        System.out.println("Mostrando imagen: " + currentImageIndex);
    }

    private int getTimerInterval() {
        // Convertir el valor del slider (1-100) a un intervalo de temporizador en milisegundos (100-2000 ms)
        int sliderValue = speedSlider.getValue();
        int interval = 2100 - sliderValue * 20;
        System.out.println("Intervalo del temporizador: " + interval + " ms");
        return interval;
    }

    private void updateTimerInterval() {
        // Detener el temporizador actual y reiniciarlo con el nuevo intervalo
        animationTimer.setDelay(getTimerInterval());
    }
}