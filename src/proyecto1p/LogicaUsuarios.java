package proyecto1p;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class LogicaUsuarios {
    ArrayList<Usuario> usuarios = new ArrayList<>();
    ArrayList<RegistrarPartidas> partidas = new ArrayList<>();
    private AbstractPiezas[][] tablero;
    private String turno = "Blanco";
    
    public boolean registrar(String usuario, String nombre, String contra){
        usuario = usuario.trim().toLowerCase();
        nombre= nombre.trim();
        
        if (usuario.isEmpty() || contra.isEmpty()){
            JOptionPane.showMessageDialog(null,"Ningun campo puede estar vacio.","", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    for (Usuario lista : usuarios){
    if (lista.getUsuario().equalsIgnoreCase(usuario)) {
        JOptionPane.showMessageDialog(
            null,
            "Usuario ya existe.",
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
         return false;
    }
    }
        
        if (contra.length()!=5){
            JOptionPane.showMessageDialog(null, "La clave debe llevar 5 caracteres exactos.", "", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!contra.matches(".*[!@#$%^&*].*")) {
            JOptionPane.showMessageDialog(null,
                "La contraseña debe incluir al menos un carácter especial (!,@,#,$,%,^,&,*).",
                " ", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        //el 0 es para que por default el jugador inicialice con 0 puntos
        usuarios.add(new Usuario(nombre,usuario,contra,0));
        JOptionPane.showMessageDialog(null, "Usuario registrado con éxito.", " ", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    
    public void registrarPartida(String jugador1, String jugador2, String resultado, int puntosGanados) {
    partidas.add(new RegistrarPartidas(jugador1, jugador2, resultado, puntosGanados));
}

    public boolean hayAlgunaPiezaMovibleDelTurno() {
    return hayMovRec(0, 0);
}

private boolean hayMovRec(int i, int j) {
    if (i >= tablero.length) return false;

    int nextI = (j == tablero[i].length - 1) ? i + 1 : i;
    int nextJ = (j == tablero[i].length - 1) ? 0 : j + 1;

    AbstractPiezas p = tablero[i][j];
    if (p != null && turno.equals(p.getColor())) {
        // prueba 1-2 casillas alrededor por si hay un destino válido (muy ligero)
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                int x2 = i + dx, y2 = j + dy;
                if (x2 < 0 || x2 >= tablero.length || y2 < 0 || y2 >= tablero[0].length) continue;
                if (p.puedeMoverse(x2, y2)) return true;
            }
        }
    }

    return hayMovRec(nextI, nextJ);
}


public int contarPiezasColor(String color) {
    return contarPiezasColorRec(tablero, color, 0, 0);
}
private int contarPiezasColorRec(AbstractPiezas[][] t, String color, int i, int j) {
    if (i >= t.length) return 0;
    int nextI = j == t[i].length - 1 ? i + 1 : i;
    int nextJ = j == t[i].length - 1 ? 0 : j + 1;

    int add = (t[i][j] != null && color.equals(t[i][j].getColor())) ? 1 : 0;
    return add + contarPiezasColorRec(t, color, nextI, nextJ);
}


    
    public Usuario login(String usuario, String contra) {
        usuario = usuario.trim().toLowerCase();
        
        for (Usuario lista : usuarios) {
            if (lista.getUsuario().equalsIgnoreCase(usuario) && lista.getContra().equals(contra)) {
                return lista;
            }
        }
        return null;
    }
}
