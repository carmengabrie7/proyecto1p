package proyecto1p;

import javax.swing.ImageIcon;


public class Vampiro extends AbstractPiezas {
    public Vampiro(int x, int y,String color){
        super(4,3,5,x,y,color, new ImageIcon("src/imagenes/vampiro.png")); //vida=4, ataque=3, escudo=5
    }
    
    @Override
    public void habilidadEspecial() {
        System.out.println("Vampiro usa 'Chupar sangre': quita 1 de vida y se cura 1.");
    }

    //habilidad en si
    public void chuparSangre(AbstractPiezas enemigo) {
        if (enemigo != null && enemigo.estaViva()) {
            enemigo.recibirDanio(1);
            setVida(getVida() + 1);
            if (getVida() > 4) setVida(4); // vida máxima
        }
    }
}
