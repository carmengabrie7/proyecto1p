package proyecto1p;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class LogicaUsuarios {
    ArrayList<Usuario> usuarios = new ArrayList();
    
    public boolean registrar(String usuario, String nombre, String contra){
        if (usuario.isEmpty() || contra.isEmpty()){
            JOptionPane.showMessageDialog(null,"Ningun campo puede estar vacio.","ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        for (Usuario lista : usuarios){
            if (lista.getUsuario().equals(usuario))
                JOptionPane.showMessageDialog(null, "Usuario ya existe.", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
        }
        
        if (contra.length()!=5){
            JOptionPane.showMessageDialog(null, "La clave debe llevar 5 caracteres exactos.", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (!contra.contains(".*[!@#$%^&*].*\"")){
            JOptionPane.showMessageDialog(null, "La contraseña debe incluir al menos un carácter especial (!,@,#,$,%,^,&,*).", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            return false;
        }
           
        usuarios.add(new Usuario(nombre,usuario,contra));
        JOptionPane.showMessageDialog(null, "Usuario registrado con éxito.", "ÉXITO", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }
    
    public Usuario login(String usuario, String contra) {
        for (Usuario lista : usuarios) {
            if (lista.getUsuario().equals(usuario) && lista.getContra().equals(contra)) {
                return lista;
            }
        }
        return null;
    }
}
