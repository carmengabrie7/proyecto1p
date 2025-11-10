package proyecto1p;

import javax.swing.ImageIcon;


public class hombreLobo extends AbstractPiezas{
    public hombreLobo(int x, int y,String color){
        super(5,5,2,x,y,color, new ImageIcon ("src/imagenes/hombrelobo.png")); //vida=5,ataque=5,escudo=2
    }
    
    @Override
    public void habilidadEspecial() {
        System.out.println("El Hombre Lobo puede moverse hasta 2 casillas.");
    }

    @Override
    public boolean puedeMoverse(int xDestino, int yDestino) {
        int dx = Math.abs(getX() - xDestino); //aca resta la columna destino de la columna actual
        int dy = Math.abs(getY() - yDestino); //resta la fila destino de la fila actual
        return (dx <= 2 && dy <= 2) && !(dx == 0 && dy == 0);// hasta 2 casillas
    }
}
