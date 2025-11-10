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
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;



public class MenuPrincipal extends JFrame{
    private LogicaUsuarios logica;
    private Usuario jugadorIniciado;
    
    public MenuPrincipal(LogicaUsuarios logica, Usuario jugadorIniciado){
        this.logica=logica;
        this.jugadorIniciado=jugadorIniciado;
       
        setTitle("Vampire Wargame");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        ImageIcon fondoImg = new ImageIcon("src/imagenes/fondo2.2.jpg");
        JLabel fondo = new JLabel();
        fondo.setIcon(new ImageIcon(fondoImg.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 500);
        fondo.setLayout(null);
        add(fondo);
        
        ImageIcon logo = new ImageIcon("src/imagenes/logo.jpg"); 
        setIconImage(logo.getImage());
        
        JLabel titulo = new JLabel("VAMPIRE WARGAME",SwingConstants.CENTER);
        titulo.setForeground(new Color(224, 220, 222));
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 32));
        titulo.setBounds(0, 80, 800, 50);
        fondo.add(titulo);
        
        JLabel info = new JLabel("Menu Principal",SwingConstants.CENTER);
        info.setForeground(new Color(224,220,222));
        info.setFont(new Font("serif", Font.ITALIC,15));
        info.setBounds(0, 80, 800, 100);
        fondo.add(info);
        
        int anchoBoton = 150;
        int altoBoton = 40;
        int xCentro = (800 - anchoBoton) / 2; // posición centrada

        JButton btnJugar = crearBoton("Jugar", xCentro, 180, anchoBoton, altoBoton);
        JButton btnCuenta = crearBoton("Mi cuenta", xCentro, 230, anchoBoton, altoBoton);
        JButton btnReportes = crearBoton("Reportes", xCentro, 280, anchoBoton, altoBoton);
        JButton btnLogOut = crearBoton("Log Out", xCentro, 330, anchoBoton, altoBoton);
        
        btnJugar.setBackground(new Color(80, 0, 0)); 
        btnJugar.setForeground(Color.white);
        btnJugar.setFont(new Font("Serif", Font.PLAIN, 14));
        btnJugar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnJugar.addActionListener(e -> {
    if (logica.usuarios.size() <= 1) {
        JOptionPane.showMessageDialog(this, "No existen más jugadores registrados para jugar contra.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }


    String[] nombres = logica.usuarios.stream().filter(u -> !u.getUsuario().equals(jugadorIniciado.getUsuario())).map(Usuario::getNombre).toArray(String[]::new);
    String seleccionado = (String) JOptionPane.showInputDialog(
            this,
            "Seleccione un oponente:",
            "Elegir jugador",
            JOptionPane.QUESTION_MESSAGE,
            null,
            nombres,
            nombres[0]
    );

    if (seleccionado != null) {
        Usuario oponente = logica.usuarios.stream()
                .filter(u -> u.getNombre().equals(seleccionado))
                .findFirst()
                .orElse(null);

        if (oponente != null) {
    TableroGUI juego = new TableroGUI(jugadorIniciado, oponente, logica);
    juego.setVisible(true);
    dispose();
}

    }
});
        
        btnCuenta.setForeground(Color.white);
        btnCuenta.setBackground(new Color(80, 0, 0)); 
        btnCuenta.setFont(new Font("Serif", Font.PLAIN, 14));
        btnCuenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCuenta.addActionListener(e -> {
        new miCuenta(logica, jugadorIniciado).setVisible(true);
        dispose();
        });
        
        btnReportes.setForeground(Color.white);
        btnReportes.setBackground(new Color(80, 0, 0));
        btnReportes.setFont(new Font("Serif", Font.PLAIN, 14));
        btnReportes.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReportes.addActionListener(e -> {
        new Reportes(logica, jugadorIniciado).setVisible(true);
        dispose();
        });
        
        btnLogOut.setBackground(new Color(80, 0, 0)); 
        btnLogOut.setForeground(Color.white);
        btnLogOut.setFont(new Font("Serif", Font.PLAIN, 14));
        btnLogOut.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogOut.addActionListener(e -> {
          MenuInicial menu = new MenuInicial(logica);
          menu.setVisible(true);
          dispose();
        });
 
        fondo.add(btnJugar);
        fondo.add(btnCuenta);
        fondo.add(btnReportes);
        fondo.add(btnLogOut);
        setContentPane(fondo);
        
    }

    private static JButton crearBoton(String texto, int x, int y, int ancho, int alto) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, ancho, alto);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);

        
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
       
    public static void main (String[]args){
        LogicaUsuarios logica = new LogicaUsuarios();
        new MenuPrincipal(logica, null).setVisible(true);
        
    }
}
