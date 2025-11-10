 package proyecto1p;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class MenuInicial extends JFrame {
LogicaUsuarios logica = new LogicaUsuarios();
    public MenuInicial(LogicaUsuarios logica){
        this.logica = logica;
       
        setTitle("Vampire Wargame");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        // fondo
        ImageIcon fondoImg = new ImageIcon("src/imagenes/fondo.jpg");
        JLabel fondo = new JLabel();
        fondo.setIcon(new ImageIcon(fondoImg.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 500);
        fondo.setLayout(null);
        
        ImageIcon logo = new ImageIcon("src/imagenes/logo.jpg"); 
        setIconImage(logo.getImage()); 

        // titulo
        JLabel titulo = new JLabel("VAMPIRE WARGAME", SwingConstants.CENTER);
        titulo.setForeground(new Color(224, 220, 222));
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 32));
        titulo.setBounds(0, 80, 800, 50);
        fondo.add(titulo);
        
        JLabel info = new JLabel("Menu Inicial",SwingConstants.CENTER);
        info.setForeground(new Color(224,220,222));
        info.setFont(new Font("serif", Font.ITALIC,15));
        info.setBounds(0, 80, 800, 100);
        fondo.add(info);

        // botones medidas
        int anchoBoton = 150;
        int altoBoton = 40;
        int xCentro = (800 - anchoBoton) / 2; // posición centrada

        JButton btnLogin = crearBoton("Log In", xCentro, 200, anchoBoton, altoBoton);
        JButton btnCrear = crearBoton("Crear player", xCentro, 270, anchoBoton, altoBoton);
        JButton btnSalir = crearBoton("Salir", xCentro, 340, anchoBoton, altoBoton);
        
        
        btnLogin.setBackground(new Color(80, 0, 0)); 
        btnLogin.setForeground(Color.white);
        btnLogin.setFont(new Font("Serif", Font.PLAIN, 14));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(e -> {
           new LogIn(logica).setVisible(true);
            dispose();
        });
        
        btnCrear.setForeground(Color.white);
        btnCrear.setBackground(new Color(80, 0, 0)); 
        btnCrear.setFont(new Font("Serif", Font.PLAIN, 14));
        btnCrear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCrear.addActionListener(e -> {
            new CrearPlayer(logica).setVisible(true);
            dispose();
        });
        
        btnSalir.setBackground(new Color(80, 0, 0)); 
        btnSalir.setForeground(Color.white);
        btnSalir.setFont(new Font("Serif", Font.PLAIN, 14));
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalir.addActionListener(e -> System.exit(0));
 
        fondo.add(btnLogin);
        fondo.add(btnCrear);
        fondo.add(btnSalir);
        setContentPane(fondo);
        
    }

    private static JButton crearBoton(String texto, int x, int y, int ancho, int alto) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, ancho, alto);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);

        // esto hace que cuando presionen el mouse sobre el boton cambie de color
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(120, 0, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(80, 0, 0));
            }
        });

        return boton;
    }
    
    public static void main(String[] args) {
        LogicaUsuarios logica = new LogicaUsuarios();
    MenuInicial menu = new MenuInicial(logica);
    menu.setVisible(true);
    }
} 
    
   
