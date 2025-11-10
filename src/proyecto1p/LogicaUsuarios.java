package proyecto1p;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class LogicaUsuarios {
    ArrayList<Usuario> usuarios = new ArrayList<>();
    ArrayList<RegistrarPartidas> partidas = new ArrayList<>();
    
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
