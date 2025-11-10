package proyecto1p;

import javax.swing.ImageIcon;


public class Muerte extends AbstractPiezas{
     public Muerte(int x, int y,String color) {
        super(3, 4, 1, x, y,color, new ImageIcon("src/imagenes/muerte.png")); // vida=3, ataque=4, escudo=1
    }

     
    @Override
    public void habilidadEspecial() {
        System.out.println("La Muerte puede lanzar su lanza o conjurar un zombie.");
    }

    // lanza a distancia
    public String lanzarLanza(AbstractPiezas[][] tablero, int x2, int y2) {
        AbstractPiezas objetivo = tablero[x2][y2];

        if (objetivo == null || objetivo.getColor().equals(this.getColor())) {
            return "Debes elegir un enemigo para la lanza.";
        }

        int dx = Math.abs(getX() - x2);
        int dy = Math.abs(getY() - y2);
        boolean esLinea = (dx == 2 && dy == 0) || (dx == 0 && dy == 2) || (dx == 2 && dy == 2);

        if (!esLinea) return "La lanza solo puede lanzarse a 2 casillas en línea o diagonal.";

        // ignora escudo: -2 de vida directo
        objetivo.setVida(objetivo.getVida() - 2);
        if (objetivo.getVida() < 0) objetivo.setVida(0);

        if (!objetivo.estaViva()) {
            tablero[x2][y2] = null;
            return "Lanza mortal: pieza destruida.";
        } else {
            return "Lanza mortal: -2 vidas (ignora escudo). Vidas restantes: " + objetivo.getVida();
        }
    }

    // crea zombie
    public String conjurarZombie(AbstractPiezas[][] tablero, int x2, int y2) {
    if (tablero[x2][y2] != null)
        return "No podés conjurar sobre otra pieza.";

    tablero[x2][y2] = new Zombie(x2, y2, this.getColor());
    return "La Muerte ha conjurado un Zombie en (" + x2 + ", " + y2 + ").";
}

}
