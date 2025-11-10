package proyecto1p;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Reportes extends JFrame {
    private LogicaUsuarios logica;
    private Usuario jugadorIniciado;

    public Reportes(LogicaUsuarios logica, Usuario jugadorIniciado) {
        this.logica = logica;
        this.jugadorIniciado = jugadorIniciado;

        //default settings
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

        JLabel titulo = new JLabel("Reportes", SwingConstants.CENTER);
        titulo.setForeground(new Color(224, 220, 222));
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 32));
        titulo.setBounds(0, 50, 800, 50);
        fondo.add(titulo);

        JLabel subtitulo = new JLabel("Ranking de Jugadores y registro de partidas", SwingConstants.CENTER);
        subtitulo.setForeground(new Color(224, 220, 222));
        subtitulo.setFont(new Font("Serif", Font.ITALIC, 15));
        subtitulo.setBounds(0, 90, 800, 30);
        fondo.add(subtitulo);

        // botones
        JButton btnRanking = new JButton("Ranking de Jugadores");
        btnRanking.setBounds(150, 150, 200, 40);
        btnRanking.setBackground(new Color(87, 0, 0));
        btnRanking.setForeground(Color.WHITE);
        btnRanking.setFont(new Font("Serif", Font.PLAIN, 14));
        btnRanking.setFocusPainted(false);
        btnRanking.setBorderPainted(false);
        btnRanking.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fondo.add(btnRanking);

        JButton btnLogs = new JButton("Mis Últimos Partidos");
        btnLogs.setBounds(450, 150, 200, 40);
        btnLogs.setBackground(new Color(87, 0, 0));
        btnLogs.setForeground(Color.WHITE);
        btnLogs.setFont(new Font("Serif", Font.PLAIN, 14));
        btnLogs.setFocusPainted(false);
        btnLogs.setBorderPainted(false);
        btnLogs.setCursor(new Cursor(Cursor.HAND_CURSOR));
        fondo.add(btnLogs);

        // tabla
        String[] columnas = {"Posición", "Usuario", "Puntos"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        tabla.setEnabled(false);
        tabla.setBackground(new Color(240, 240, 240));
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(130, 210, 540, 200);
        fondo.add(scroll);

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

        volver.addActionListener(e -> {
            new MenuPrincipal(logica, jugadorIniciado).setVisible(true);
            dispose();
        });

        cerrar.addActionListener(e -> System.exit(0));

        btnRanking.addActionListener(e -> {
            modelo.setRowCount(0);
            modelo.setColumnIdentifiers(new Object[]{"Posición", "Usuario", "Puntos"});

            
            Set<String> usuariosUnicos = new HashSet<>();
            List<Usuario> lista = new ArrayList<>(logica.usuarios);

            
            lista.sort((u1, u2) -> Integer.compare(u2.getPuntos(), u1.getPuntos()));

            int pos = 1;
            for (Usuario u : lista) {
                if (usuariosUnicos.add(u.getUsuario())) { // solo agrega si no estaba
                    modelo.addRow(new Object[]{pos++, u.getNombre(), u.getPuntos()});
                }
            }

            if (modelo.getRowCount() == 0) {
                modelo.addRow(new Object[]{"—", "No hay jugadores registrados", "—"});
            }
        });

        btnLogs.addActionListener(e -> {
            modelo.setRowCount(0);
            modelo.setColumnIdentifiers(new Object[]{"Fecha", "Resultado", "Puntos Obtenidos"});

            if (logica.partidas == null || logica.partidas.isEmpty()) {
                modelo.addRow(new Object[]{"—", "No hay partidas registradas", "—"});
                return;
            }

            for (RegistrarPartidas p : logica.partidas) {
                if (p.getJugador1().equals(jugadorIniciado.getNombre()) ||
                    p.getJugador2().equals(jugadorIniciado.getNombre())) {
                    modelo.addRow(new Object[]{
                        p.getFecha(),
                        p.getResultado(),
                        p.getPuntosGanados()
                    });
                }
            }

            if (modelo.getRowCount() == 0) {
                modelo.addRow(new Object[]{"—", "No has jugado partidas aún", "—"});
            }
        });
    }

    public static void main(String[] args) {
        LogicaUsuarios logica = new LogicaUsuarios();
        new Reportes(logica, null).setVisible(true);
    }
}
