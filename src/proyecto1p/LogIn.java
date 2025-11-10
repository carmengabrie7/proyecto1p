package proyecto1p;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class LogIn extends JFrame{
    private LogicaUsuarios logica;
    private Usuario usuario;

public LogIn(LogicaUsuarios logica) {
    this.logica = logica;  

    setTitle("Vampire Wargame");
    setSize(800,500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(null);

        ImageIcon fondoImg = new ImageIcon("src/imagenes/fondo.jpg");
        JLabel fondo = new JLabel();
        fondo.setIcon(new ImageIcon(fondoImg.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 500);
        fondo.setLayout(null);
        add(fondo);
        
        ImageIcon logo = new ImageIcon("src/imagenes/logo vw.jpg"); 
        setIconImage(logo.getImage());
        
        JLabel titulo = new JLabel("Log In", SwingConstants.CENTER);
        titulo.setForeground(new Color(224, 220, 222));
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 32));
        titulo.setBounds(0, 80, 800, 50);
        fondo.add(titulo);
        
        JButton volver = new JButton("Volver");
        volver.setBounds(40, 30, 100, 30);
        volver.setBackground(new Color(87, 0, 0));
        volver.setForeground(Color.WHITE);
        volver.setFont(new Font("Serif", Font.PLAIN, 14));
        volver.setFocusPainted(false);
        volver.setBorderPainted(false);
        fondo.add(volver);
        volver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        volver.addActionListener(e ->{
            MenuInicial menu = new MenuInicial(logica);
            menu.setVisible(true);
            dispose();
        });

        JButton cerrar = new JButton("Cerrar");
        cerrar.setBackground(new Color(87,0,0));
        cerrar.setForeground(Color.white);
        cerrar.setFont(new Font("Serif",Font.PLAIN,14));
        cerrar.setBounds(650, 30, 100, 30);
        cerrar.setFocusPainted(false);
        cerrar.setBorderPainted(false);
        cerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fondo.add(cerrar);
        cerrar.addActionListener(e -> System.exit(0));
        
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Serif", Font.PLAIN, 16));
        lblUsuario.setBounds(250, 160, 100, 25);
        fondo.add(lblUsuario);
         
        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(320, 160, 200, 25); // y, x, largo, ancho
        fondo.add(txtUsuario);
        
        JLabel lblClave = new JLabel("Clave:");
        lblClave.setForeground(Color.WHITE);
        lblClave.setFont(new Font("Serif", Font.PLAIN, 16));
        lblClave.setBounds(250, 200, 100, 25);
        fondo.add(lblClave);
        
        JPasswordField txtClave = new JPasswordField();
         txtClave.setBounds(320, 200, 200, 25);
        fondo.add(txtClave);
        
        JButton confirmar = new JButton("Confirmar");
        confirmar.setBounds(340, 253, 120, 35);
        confirmar.setBackground(new Color(87, 0, 0));
        confirmar.setForeground(Color.WHITE);
        confirmar.setFont(new Font("Times New Roman", Font.BOLD, 16));
        confirmar.setFocusPainted(false);
        confirmar.setBorderPainted(false);
        confirmar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fondo.add(confirmar);
        
        confirmar.addActionListener(e -> { 
         String usuario = txtUsuario.getText();
        String clave = new String(txtClave.getPassword());

        Usuario u = logica.login(usuario, clave);

        if (u != null) {
            JOptionPane.showMessageDialog(null,
                "Bienvenido, " + u.getNombre() + "!",
                "", JOptionPane.INFORMATION_MESSAGE);
            
            MenuPrincipal menup = new MenuPrincipal(logica,u);
            menup.setVisible(true);
            dispose();

        } else {
            JOptionPane.showMessageDialog(null,
                "Usuario o contraseña incorrectos.",
                "Error de inicio de sesión", JOptionPane.ERROR_MESSAGE);
        }
        });
 
    }
    
    public static void main (String[]args){
   LogicaUsuarios logica = new LogicaUsuarios();
        new LogIn(logica).setVisible(true);
    }
    
}
