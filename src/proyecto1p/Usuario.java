package proyecto1p;


public class Usuario {
   private String nombre;
    private String usuario;
    private String contra;
    
    //constructor 
    public Usuario(String nombre, String usuario, String contra){
        this.nombre=nombre;
        this.contra=contra;
        this.usuario=usuario;
    }
    
    //getters y setters
    public void setNombre(){
        this.nombre= nombre;
    }
    public String getNombre(){
        return nombre;
    } 
   
    public void setContra(){
        this.contra=contra;
    }
    public String getContra(){
        return contra;
    }
    
    public void setUsuario(){
        this.usuario=usuario;
    }
    public String getUsuario(){
        return usuario;
    }
} 
}
