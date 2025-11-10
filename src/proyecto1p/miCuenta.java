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
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class miCuenta extends JFrame {
    private LogicaUsuarios logica;
    private Usuario jugadorIniciado;
    
    public miCuenta(LogicaUsuarios logica, Usuario jugadorIniciado){
        this.logica=logica;
        this.jugadorIniciado=jugadorIniciado;
        
        //**** default settings ****
        setTitle("Vampire Wargame");
        setSize(800,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        ImageIcon fondoImg = new ImageIcon ("src/imagenes/fondo2.2.jpg");
        JLabel fondo = new JLabel();
        fondo.setIcon(new ImageIcon(fondoImg.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 500);
        fondo.setLayout(null);
        add(fondo);
        
        ImageIcon logo = new ImageIcon("src/imagenes/logo vw.jpg");
        setIconImage(logo.getImage());
        
        JLabel titulo = new JLabel("Mi Cuenta",SwingConstants.CENTER);
        titulo.setForeground(new Color(224,220,222));
        titulo.setFont((new Font("Times New Roman",Font.BOLD,32)));
        titulo.setBounds(0,50,800,50);
        fondo.add(titulo);
        
        JLabel subtitulo = new JLabel ("Informacion de tu perfil",SwingConstants.CENTER);
        subtitulo.setForeground(new Color(224,220,222));
        subtitulo.setFont(new Font("Serif",Font.ITALIC,15));
        subtitulo.setBounds(0,90,800,30);
        fondo.add(subtitulo);
        
        //informacion del usuario
        
        /*JPanel minifondo = new JPanel();
        minifondo.setBounds(180, 150, 470, 280);
        fondo.add(minifondo);*/
        
        JLabel lblNombre = new JLabel("Nombre: "+ jugadorIniciado.getNombre());
        lblNombre.setForeground(Color.white);
        lblNombre.setFont(new Font("Serif", Font.PLAIN, 16));
        lblNombre.setBounds(250, 150, 400, 30);
        fondo.add(lblNombre);
        
        JLabel lblUsuario = new JLabel("Usuario: " + jugadorIniciado.getUsuario());
        lblUsuario.setForeground(Color.white);
        lblUsuario.setFont(new Font("Serif", Font.PLAIN, 16));
        lblUsuario.setBounds(250, 190, 400, 30);
        fondo.add(lblUsuario);

        JLabel lblPuntos = new JLabel("Puntos: " + jugadorIniciado.getPuntos());
        lblPuntos.setForeground(Color.white);
        lblPuntos.setFont(new Font("Serif", Font.PLAIN, 16));
        lblPuntos.setBounds(250, 230, 400, 30);
        fondo.add(lblPuntos);
        
        //botones
        JButton btnCambiar = new JButton("Cambiar Password");
        btnCambiar.setBounds(270, 300, 250, 35);
        btnCambiar.setBackground(new Color(87, 0, 0));
        btnCambiar.setForeground(Color.WHITE);
        btnCambiar.setFont(new Font("Serif", Font.PLAIN, 14));
        btnCambiar.setFocusPainted(false);
        btnCambiar.setBorderPainted(false);
        btnCambiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fondo.add(btnCambiar);

        JButton btnEliminar = new JButton("Eliminar mi Cuenta");
        btnEliminar.setBounds(270, 350, 250, 35);
        btnEliminar.setBackground(new Color(87, 0, 0));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFont(new Font("Serif", Font.PLAIN, 14));
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fondo.add(btnEliminar);

        JButton volver = new JButton("Volver");
        volver.setBounds(40, 30, 100, 30);
        volver.setBackground(new Color(87, 0, 0));
        volver.setForeground(Color.WHITE);
        volver.setFont(new Font("Serif", Font.PLAIN, 14));
        volver.setFocusPainted(false);
        volver.setBorderPainted(false);
        volver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fondo.add(volver);

        JButton cerrar = new JButton("Cerrar");
        cerrar.setBounds(650, 30, 100, 30);
        cerrar.setBackground(new Color(87, 0, 0));
        cerrar.setForeground(Color.WHITE);
        cerrar.setFont(new Font("Serif", Font.PLAIN, 14));
        cerrar.setFocusPainted(false);
        cerrar.setBorderPainted(false);
        cerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fondo.add(cerrar);
        
        //acciones de los botones
        volver.addActionListener(e -> {
            new MenuPrincipal(logica, jugadorIniciado).setVisible(true);
            dispose();
        });

        cerrar.addActionListener(e -> System.exit(0));
        
        btnCambiar.addActionListener(e -> {
            String actual = JOptionPane.showInputDialog(this, "Ingrese su contraseña actual:");
            if (actual == null) return; // cancelado

            if (!actual.equals(jugadorIniciado.getContra())) {
                JOptionPane.showMessageDialog(this, "Contraseña incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nueva = JOptionPane.showInputDialog(this, "Ingrese su nueva contraseña (5 caracteres, con símbolo):");
            if (nueva == null) return;

            if (nueva.length() != 5 || !nueva.matches(".*[!@#$%^&*].*")) {
                JOptionPane.showMessageDialog(this, "Contraseña inválida. Debe tener 5 caracteres y un símbolo especial.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            jugadorIniciado.setContra(nueva);
            JOptionPane.showMessageDialog(this, "Contraseña cambiada con éxito.", "", JOptionPane.INFORMATION_MESSAGE);
        });

        btnEliminar.addActionListener(e -> {
            String confirm = JOptionPane.showInputDialog(this, "Confirme su contraseña actual para eliminar la cuenta:");
            if (confirm == null) return;

            if (confirm.equals(jugadorIniciado.getContra())) {
                logica.usuarios.remove(jugadorIniciado);
                JOptionPane.showMessageDialog(this, "Cuenta eliminada exitosamente.", "", JOptionPane.INFORMATION_MESSAGE);
                new MenuInicial(logica).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Contraseña incorrecta. No se eliminó la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
    }
      public static void main (String[]args){
        LogicaUsuarios logica = new LogicaUsuarios();
        Usuario user = new Usuario("Carmen", "carmen", "123!@", 10);
        logica.usuarios.add(user);
        new miCuenta(logica, user).setVisible(true);
    }  
    }
    
