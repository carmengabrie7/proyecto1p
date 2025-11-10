package proyecto1p;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class RuletaPanel extends JPanel {
    
    private final String[] opciones = {"Vampiro","Hombre Lobo", "Muerte"};
    private final Color[] colores = {
        new Color(150, 0, 0),
        new Color(60, 60, 60),
        new Color(40, 0, 60)
    };
    private double anguloActual = 0;
    private double velocidad = 0;
    private Timer timer;
    private String resultado = "";

    public RuletaPanel() {
        setPreferredSize(new Dimension(200, 200));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centroX = getWidth() / 2;
        int centroY = getHeight() / 2;
        int radio = Math.min(getWidth(), getHeight()) / 2 - 10;
        double anguloSeccion = 360.0 / opciones.length;

        // Dibujar las secciones
        for (int i = 0; i < opciones.length; i++) {
            g2.setColor(colores[i]);
            g2.fillArc(10, 10, radio * 2, radio * 2,
                    (int) (anguloActual + i * anguloSeccion), (int) anguloSeccion);
        }

        // Borde y puntero
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(10, 10, radio * 2, radio * 2);

        int[] xP = {centroX, centroX - 10, centroX + 10};
        int[] yP = {centroY - radio - 5, centroY - radio + 20, centroY - radio + 20};
        g2.setColor(Color.RED);
        g2.fillPolygon(xP, yP, 3);

        // Texto
        for (int i = 0; i < opciones.length; i++) {
            double ang = Math.toRadians(anguloActual + anguloSeccion * (i + 0.5));
            int x = (int) (centroX + Math.cos(ang) * radio * 0.6);
            int y = (int) (centroY + Math.sin(ang) * radio * 0.6);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Serif", Font.BOLD, 13));
            g2.drawString(opciones[i], x - 30, y);
        }
    }

    // Girar ruleta
    public void girar(Runnable callback) {
    if (timer != null && timer.isRunning()) return;

    Random rand = new Random();
    final double[] destino = {360 * (3 + rand.nextInt(4)) + rand.nextInt(360)};
    velocidad = 20;

    timer = new Timer(20, null);

    timer.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            anguloActual += velocidad;
            destino[0] -= velocidad;

            if (destino[0] <= 0) {
                timer.stop();
                velocidad = 0;
                resultado = determinarResultado();
                if (callback != null) callback.run();
            }

            repaint();
        }
    });

    timer.start();
}


    private String determinarResultado() {
        double angulo = (anguloActual % 360 + 360) % 360;
        double seccion = 360.0 / opciones.length;
        int indice = (int) ((360 - angulo + seccion / 2) % 360 / seccion);
        return opciones[indice];
    }

    public String getResultado() {
        return resultado;
    }
}

