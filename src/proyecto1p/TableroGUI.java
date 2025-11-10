package proyecto1p;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class TableroGUI extends JFrame {
    private Usuario jugadorBlanco;
    private Usuario jugadorNegro;
    private LogicaUsuarios logicaUsuarios;
    
    private JButton[][] celdas = new JButton[6][6];
    private JLabel lblTurno, lblInfo;
    private JButton btnRuleta, btnRetirarse;
    
    private LogicaJuego logica = new LogicaJuego();
    private int seleccionX = -1, seleccionY = -1;
    private boolean modoHabilidad = false;
    
    private RuletaPanel ruleta;
    private String piezaPermitida = null;
    private boolean ruletaGirada =false;
    
    public TableroGUI(Usuario jugadorBlanco, Usuario jugadorNegro, LogicaUsuarios logicaUsuarios){
        this.jugadorBlanco=jugadorBlanco;
        this.jugadorNegro=jugadorNegro;
        this.logicaUsuarios=logicaUsuarios;
        
        //default settings
        setTitle("Vampire Wargame");
        setSize(800,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        
        ImageIcon fondoImg = new ImageIcon("src/imagenes/fondo juego.jpg");
        JLabel fondo = new JLabel();
        fondo.setIcon(new ImageIcon(fondoImg.getImage().getScaledInstance(800, 500, Image.SCALE_SMOOTH)));
        fondo.setBounds(0, 0, 800, 500);
        fondo.setLayout(null);
        add(fondo);
        setContentPane(fondo);
        
        ImageIcon logo = new ImageIcon("src/imagenes/logo vw.jpg"); 
        setIconImage(logo.getImage());
        
        ruleta = new RuletaPanel();
        ruleta.setBounds(530, 100, 200, 200);
        fondo.add(ruleta);
        
        //tablero 6x6
        JPanel tablero = new JPanel(new GridLayout(6,6));
        tablero.setBounds(80,60,384,384);
        tablero.setOpaque(false);
        fondo.add(tablero);
        
        logica.inicializarTablero();
        
        for (int f=0; f<6; f++){
            for (int c =0; c<6; c++){
                JButton casilla = new JButton();
                casilla.setFocusable(false);
                casilla.setBorderPainted(false);
                casilla.setOpaque(true);
                casilla.setBackground(((f+c) % 2 ==0)
                        ? new Color (90,96,110)
                        : new Color(170,175,190));
                tablero.add(casilla);
                celdas[f][c] =casilla;
                
                int x =f, y=c;
                casilla.addActionListener(e -> manejarClick(x,y));
            }
        }
        
        lblInfo = new JLabel ("Gira la ruleta para comenzar!", SwingConstants.CENTER);
        lblInfo.setForeground(Color.WHITE);
        lblInfo.setFont(new Font("Serif",Font.ITALIC,14));
        lblInfo.setBounds(70,30,400,25);
        fondo.add(lblInfo);
        
        lblTurno = new JLabel ("Turno: "+jugadorBlanco.getNombre(), SwingConstants.LEFT);
        lblTurno.setForeground(Color.WHITE);
        lblTurno.setFont(new Font("Times New Roman",Font.BOLD,15));
        lblTurno.setBounds(550, 30, 200, 25);
        fondo.add(lblTurno);
        
        btnRuleta = new JButton("Girar Ruleta");
        btnRuleta.setBounds(550, 350,160, 35);
        btnRuleta.setBackground(new Color(80,0,0));
        btnRuleta.setForeground(Color.WHITE);
        fondo.add(btnRuleta);
        
        btnRuleta.addActionListener(e-> {
            lblInfo.setText("Girando la ruleta...");
            btnRuleta.setEnabled(false);
            ruletaGirada = false;
            
            ruleta.girar(() -> {
                String resultado = ruleta.getResultado();
                piezaPermitida = resultado;
                ruletaGirada = true;
                btnRuleta.setEnabled(true);
                lblInfo.setText("Te toco mover: "+resultado+"!");
            });
            
        });
        
        btnRetirarse = new JButton("Retirarse");
        btnRetirarse.setBounds(550, 420,160, 35);
        btnRetirarse.setBackground(new Color(120,0,0));
        btnRetirarse.setForeground(Color.WHITE);
        fondo.add(btnRetirarse);
        
        btnRetirarse.addActionListener(e -> {
    int confirm = JOptionPane.showConfirmDialog(
        this,
        "¿Seguro que deseas retirarte?\nEsto contará como una derrota.",
        "Confirmar rendición",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
    );

    if (confirm == JOptionPane.YES_OPTION) {
        String jugadorRendido = logica.getTurno();
        String ganador = jugadorRendido.equals("Blanco") ? "Negro" : "Blanco";

        Usuario ganadorJugador = ganador.equals("Blanco") ? jugadorBlanco : jugadorNegro;
        ganadorJugador.setPuntos(ganadorJugador.getPuntos() + 1);

        logicaUsuarios.registrarPartida(
            jugadorBlanco.getNombre(),
            jugadorNegro.getNombre(),
            "Ganó " + ganadorJugador.getNombre() + " (por retiro del oponente)",
            1
        );

        JOptionPane.showMessageDialog(this,
            "El jugador " + (jugadorRendido.equals("Blanco") ? jugadorBlanco.getNombre() : jugadorNegro.getNombre()) +
            " se ha retirado.\n" +
            "🏆 El ganador es " + ganadorJugador.getNombre() + "!",
            "Partida terminada",
            JOptionPane.INFORMATION_MESSAGE
        );

        new MenuPrincipal(logicaUsuarios, jugadorBlanco).setVisible(true);
        dispose();
    }
});


        
        refrescarTablero();
        setVisible(true);
        
    }
    
    //metodos
    private void manejarClick(int x, int y) {
    AbstractPiezas[][] tablero = logica.getTablero();

    if (!ruletaGirada) {
        lblInfo.setText("Primero debes girar la ruleta antes de mover.");
        return;
    }

    if (seleccionX == -1) {
        if (tablero[x][y] == null) {
            lblInfo.setText("Selecciona una pieza válida para mover o usar habilidad.");
            return;
        }

        if (!tablero[x][y].getColor().equals(logica.getTurno())) {
            lblInfo.setText("No es tu turno. Espera al jugador " + logica.getTurno() + ".");
            return;
        }

        String tipo = tablero[x][y].getClass().getSimpleName();

       
        if (piezaPermitida == null || !tipo.equalsIgnoreCase(piezaPermitida.replace(" ", ""))) {
            lblInfo.setText("Solo puedes mover: " + piezaPermitida + " este turno.");
            return;
        }

        // Selecciona la pieza
        seleccionX = x;
        seleccionY = y;
        lblInfo.setText("Seleccionaste: " + tipo);
        resaltarMovimientos(tablero[x][y]);

        // Pregunta la acción
        String[] opciones = {"Mover", "Usar habilidad especial", "Cancelar"};
        int eleccion = JOptionPane.showOptionDialog(
                this,
                "¿Qué deseas hacer con " + tipo + "?",
                "Acción de " + tipo,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (eleccion == 0) {
            lblInfo.setText("Selecciona una casilla a donde mover la pieza.");
            modoHabilidad = false;

        } else if (eleccion == 1) {
            AbstractPiezas pieza = tablero[x][y];
            if (pieza instanceof Muerte) {
                String[] habilidades = {"Lanzar lanza", "Conjurar zombie", "Cancelar"};
                int op = JOptionPane.showOptionDialog(
                        this,
                        "Selecciona la habilidad especial:",
                        "Habilidad de la Muerte",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        habilidades,
                        habilidades[0]
                );

                if (op == 0) {
                    lblInfo.setText("Selecciona un enemigo a 2 casillas para lanzar la lanza.");
                    modoHabilidad = true;
                } else if (op == 1) {
                    lblInfo.setText("Selecciona una casilla vacía para conjurar un zombie.");
                    modoHabilidad = true;
                } else {
                    limpiarSeleccion();
                    return;
                }

            } else if (pieza instanceof Vampiro) {
                lblInfo.setText("Selecciona un enemigo cercano para chupar sangre.");
                modoHabilidad = true;
            } else {
                JOptionPane.showMessageDialog(this, "Esta pieza no tiene habilidad especial.");
                limpiarSeleccion();
                return;
            }

        } else {
            limpiarSeleccion();
            return;
        }

        return;
    }

    if (modoHabilidad) {
        String resultado = logica.ataqueEspecial(seleccionX, seleccionY, x, y);
        lblInfo.setText(resultado);
        modoHabilidad = false;
    } else {
        String resultado = logica.mover(seleccionX, seleccionY, x, y);
        lblInfo.setText(resultado);
    }

    //limpieza de seleccion
    seleccionX = -1;
    seleccionY = -1;
    refrescarTablero();
    limpiarResaltado();

    if (logica.verificarFin()) {
    String ganador = logica.getGanador();
    String nombreGanador = ganador.equals("Blanco") ? jugadorBlanco.getNombre() : jugadorNegro.getNombre();
    lblInfo.setText("¡Juego terminado! Ganó " + nombreGanador);

    // Actualizar puntos
    if (ganador.equals("Blanco")) {
        jugadorBlanco.setPuntos(jugadorBlanco.getPuntos() + 1);
    } else if (ganador.equals("Negro")) {
        jugadorNegro.setPuntos(jugadorNegro.getPuntos() + 1);
    }
    
logicaUsuarios.registrarPartida(
    jugadorBlanco.getNombre(),
    jugadorNegro.getNombre(),
    "Ganó " + (ganador.equals("Blanco") ? jugadorBlanco.getNombre() : jugadorNegro.getNombre()),
    1
);


    int opcion = JOptionPane.showOptionDialog(
        this,
        "🏆 Ganador: " + nombreGanador +
        "\n\nPuntaje actualizado:\n" +
        jugadorBlanco.getNombre() + ": " + jugadorBlanco.getPuntos() + " pts\n" +
        jugadorNegro.getNombre() + ": " + jugadorNegro.getPuntos() + " pts\n\n" +
        "¿Deseas volver al menú principal?",
        "Resultado final",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.INFORMATION_MESSAGE,
        null,
        new Object[]{"Sí, volver al menú", "Cerrar juego"},
        "Sí, volver al menú"
    );

    if (opcion == JOptionPane.YES_OPTION) {
        new MenuPrincipal(logicaUsuarios, jugadorBlanco).setVisible(true);
        dispose();
    } else {
        System.exit(0);
    }
}
    else {
        logica.cambiarTurno();
        lblTurno.setText("Turno: " + 
    (logica.getTurno().equals("Blanco") ? jugadorBlanco.getNombre() + " (Blanco)"
                                       : jugadorNegro.getNombre() + " (Negro)")
);

        ruletaGirada = false;
        piezaPermitida = null;
        lblInfo.setText("Jugador siguiente: gira la ruleta para jugar.");
    }
}


    
    private void limpiarSeleccion(){
        seleccionX = -1;
        seleccionY = -1;
        limpiarResaltado();
        lblInfo.setText("Accion cancelada");
    }
    
    private void refrescarTablero(){
        AbstractPiezas[][] tablero = logica.getTablero();
        for (int f =0; f<6; f++){
            for (int c =0; c<6; c++){
                celdas[f][c].setIcon(null);
                if (tablero[f][c] != null){ 
                    celdas[f][c].setIcon(tablero[f][c].getIcon());
                }
            }
        }
    }
    
    private void resaltarMovimientos(AbstractPiezas pieza) {
    limpiarResaltado();

    int x = pieza.getX();
    int y = pieza.getY();

    // alcance según la pieza
    int maxStep = (pieza instanceof hombreLobo) ? 2 : 1;

    // 8 direcciones (adyacentes + diagonales)
    int[] df = {-1,-1,-1, 0, 0, 1, 1, 1};
    int[] dc = {-1, 0, 1,-1, 1,-1, 0, 1};

    AbstractPiezas[][] t = logica.getTablero();

    for (int d = 0; d < 8; d++) {
        for (int step = 1; step <= maxStep; step++) {
            int nf = x + df[d] * step;
            int nc = y + dc[d] * step;

            // fuera del tablero
            if (nf < 0 || nf >= 6 || nc < 0 || nc >= 6) break;

            AbstractPiezas celda = t[nf][nc];

            if (celda == null) {
                // casilla libre → movimiento posible (verde)
                celdas[nf][nc].setBackground(new Color(100, 220, 120));
                celdas[nf][nc].setBorderPainted(true);
                celdas[nf][nc].setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0,0,0,80), 1));
                // si es libre, podemos seguir extendiendo en esta dirección (solo lobo)
                continue;
            } else {
                // hay una pieza
                if (!celda.getColor().equals(pieza.getColor())) {
                    // enemigo → ataque posible (rojo) y se detiene en esta dirección
                    celdas[nf][nc].setBackground(new Color(220, 80, 80));
                    celdas[nf][nc].setBorderPainted(true);
                    celdas[nf][nc].setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0,0,0,80), 1));
                }
                // pieza propia o enemigo: detener en esta dirección
                break;
            }
        }
    }

    // celda seleccionada
    celdas[x][y].setBackground(Color.YELLOW);
    celdas[x][y].setBorderPainted(true);
    celdas[x][y].setBorder(javax.swing.BorderFactory.createLineBorder(new Color(0,0,0,120), 2));
}

    
    private void limpiarResaltado() {
    for (int f = 0; f < 6; f++) {
        for (int c = 0; c < 6; c++) {
            celdas[f][c].setBackground(((f + c) % 2 == 0)
                    ? new Color(90, 96, 110)
                    : new Color(170, 175, 190));
            celdas[f][c].setBorderPainted(false);
            celdas[f][c].setBorder(null);
        }
    }
}

    
    
    
       public static void main(String[] args) {
    LogicaUsuarios logica = new LogicaUsuarios();

    Usuario jugador1 = new Usuario("Jugador Blanco", "blanco", "clave!", 0);
    Usuario jugador2 = new Usuario("Jugador Negro", "negro", "clave@", 0);

    TableroGUI juego = new TableroGUI(jugador1, jugador2, logica);
    juego.setVisible(true);
}

    }

