package proyecto1p;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class CrearPlayer {
    
    public static void main(String[]args){
    JFrame ventanaRegistro = new JFrame("Crear Player");
    
    ventanaRegistro.setSize(800,500);
    ventanaRegistro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaRegistro.setResizable(false);
        ventanaRegistro.setLocationRelativeTo(null);

        ImageIcon fondoImg = new ImageIcon("src/proyecto1p/fondo.jpg");
        JLabel fondo = new JLabel();
        fondo.setIcon(new ImageIcon(fondoImg.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 500);
        fondo.setLayout(null);
        
        
        
        ventanaRegistro.setVisible(true);
    }   
}
