package proyecto1p;


public class Usuario {
   private String nombre;
    private String usuario;
    private String contra;
    private int puntos;
    
    //constructor 
    public Usuario(String nombre, String usuario, String contra, int puntos){
        this.nombre=nombre;
        this.contra=contra;
        this.usuario=usuario;
        this.puntos=puntos;
    }
    
    //constructor #2 para evitar errores en logicausuarios
    public Usuario(String nombre, String usuario, String contra) {
        this(nombre, usuario, contra, 0);
    }
    
    //getters y setters
    public void setNombre(String nombre){
        this.nombre= nombre;
    }
    public String getNombre(){
        return nombre;
    } 
   
    public void setContra(String contra){
        this.contra=contra;
    }
    public String getContra(){
        return contra;
    }
    
    public void setUsuario(String usuario){
        this.usuario=usuario;
    }
    public String getUsuario(){
        return usuario;
    }
    
    public void setPuntos(int puntos){
        this.puntos=puntos;
    }
    public int getPuntos(){
        return puntos;
    }
} 

