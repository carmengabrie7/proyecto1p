package proyecto1p;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



public class CrearPlayer extends JFrame{
    private Usuario usuario;
    private LogicaUsuarios logica;

    public CrearPlayer(LogicaUsuarios logica){
        this.logica=logica;
        
    setTitle("Vampire Wargame");
    setSize(800,500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    setLocationRelativeTo(null);
        
        //fondo
        ImageIcon fondoImg = new ImageIcon("src/imagenes/fondo.jpg");
        JLabel fondo = new JLabel();
        fondo.setIcon(new ImageIcon(fondoImg.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 500);
        fondo.setLayout(null);
        add(fondo);
        
        //logo
        ImageIcon logo = new ImageIcon("src/imagenes/logo.jpg"); 
        setIconImage(logo.getImage());
        
        JLabel titulo = new JLabel("Crear Player", SwingConstants.CENTER);
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
        volver.addActionListener(e ->{
            MenuInicial menu = new MenuInicial(this.logica);
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
        fondo.add(cerrar);
        cerrar.addActionListener(e -> System.exit(0));

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(new Font("Serif", Font.PLAIN, 16));
        lblNombre.setBounds(250, 160, 100, 25);
        fondo.add(lblNombre);
         
        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(320, 160, 200, 25);
        fondo.add(txtNombre);
   
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Serif", Font.PLAIN, 16));
        lblUsuario.setBounds(250, 200, 100, 25);
        fondo.add(lblUsuario);

        JTextField txtUsuario = new JTextField();
        txtUsuario.setBounds(320, 200, 200, 25);
        fondo.add(txtUsuario);

        JLabel lblClave = new JLabel("Clave:");
        lblClave.setForeground(Color.WHITE);
        lblClave.setFont(new Font("Serif", Font.PLAIN, 16));
        lblClave.setBounds(250, 240, 100, 25);
        fondo.add(lblClave);

        JPasswordField txtClave = new JPasswordField();
        txtClave.setBounds(320, 240, 200, 25);
        fondo.add(txtClave);

        JLabel info = new JLabel("*Clave debe ser 5 caracteres exactos y llevar caracteres especiales*");
        info.setForeground(new Color(200, 200, 200));
        info.setFont(new Font("Times New Roman", Font.ITALIC, 12));
        info.setBounds(220, 270, 400, 25);
        fondo.add(info);

        JButton confirmar = new JButton("Confirmar");
        confirmar.setBounds(340, 320, 120, 35);
        confirmar.setBackground(new Color(87, 0, 0));
        confirmar.setForeground(Color.WHITE);
        confirmar.setFont(new Font("Times New Roman", Font.BOLD, 16));
        confirmar.setFocusPainted(false);
        confirmar.setBorderPainted(false);
        fondo.add(confirmar);
        
        confirmar.addActionListener(e ->{
            String nombre = txtNombre.getText();
            String usuario = txtUsuario.getText();
            String clave = new String(txtClave.getPassword());

            boolean registrado = logica.registrar(usuario, nombre, clave);

            if (registrado) {
                txtNombre.setText("");
                txtUsuario.setText("");
                txtClave.setText("");
                
                Usuario nuevo = new Usuario(nombre, usuario, clave, 0);
                logica.usuarios.add(nuevo);
                 new MenuPrincipal(logica, nuevo).setVisible(true);
                dispose();
            }
        });
    }
    public static void main (String[]args){
        LogicaUsuarios logica = new LogicaUsuarios();
        CrearPlayer registro = new CrearPlayer(logica);
        registro.setVisible(true);
    }
}
