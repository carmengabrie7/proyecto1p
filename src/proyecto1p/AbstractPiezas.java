package proyecto1p;

import javax.swing.ImageIcon;

public abstract class AbstractPiezas {
    private int vida;
    private int ataque;
    private int escudo;
    private int x,y;
    private String color; //esto es para el turno
    private ImageIcon icon; 
    
    //constructor
    public AbstractPiezas(int vida, int ataque,int escudo, int x, int y,String color, ImageIcon icon){
        this.vida=vida;
        this.ataque=ataque;
        this.escudo=escudo;
        this.x=x;
        this.y=y;
        this.color=color;
        this.icon=icon;
    }
    
    //getters y setters
    public int getVida (){
        return vida;
    }
    public void setVida(int vida){
        this.vida=vida;
    }
    public int getAtaque(){
        return ataque;
    }
    public void setAtaque(int ataque){
        this.ataque=ataque;
    }
    public int getEscudo(){
        return escudo;
    }
    public void setEscudo(int escudo){
        this.escudo=escudo;
    }
    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x=x;
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y=y;
    }
    public String getColor(){
        return color;
    }
    public ImageIcon getIcon(){
        return icon;
    }
    
    //metodos abstractos
    public abstract void habilidadEspecial();
    
    //metodos generales
    public void mover(int x, int y){
        this.x=x;
        this.y=y;
    }
    
     public void recibirDanio(int cantidad) {
        if (escudo > 0) {
            int danoEscudo = Math.min(escudo, cantidad);
            escudo -= danoEscudo;
            cantidad -= danoEscudo;
        }
        vida -= cantidad;
        if (vida < 0) vida = 0;
    }
     
     public boolean estaViva() {
        return vida > 0;
    }
     
     public void atacar(AbstractPiezas enemigo) {
        if (enemigo != null && enemigo.estaViva()) {
            enemigo.recibirDanio(this.ataque);
        }
    }
     
      public boolean puedeMoverse(int xDestino, int yDestino) {
        int dx = Math.abs(this.x - xDestino);
        int dy = Math.abs(this.y - yDestino);
        return dx <= 1 && dy <= 1;
    }
    
    
}
