package proyecto1p;

import javax.swing.ImageIcon;


public class Zombie extends AbstractPiezas{
    public Zombie(int x, int y,String color){
        super(1,1,0,x,y,color,new ImageIcon("src/imagenes/zombie.png")); //vida =1, ataque=1, escudo=0
    }

    @Override
    public void habilidadEspecial() {
        System.out.println("Solo se mueve si la muerte se lo ordena.");
    }
    
    @Override
    public boolean puedeMoverse(int xDestino, int yDestino) {
        return false; 
    }
}
