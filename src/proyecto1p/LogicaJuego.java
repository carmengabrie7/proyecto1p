package proyecto1p;


public class LogicaJuego {
    private final int N=6;
    private AbstractPiezas[][] tablero = new AbstractPiezas[N][N];
    private String turno = "Blanco";
    
    public void inicializarTablero(){
        //posiciones por default!
        tablero[0][0] = new hombreLobo(0,0,"Blanco");
        tablero[0][1] = new Vampiro(0,1,"Blanco");
        tablero[0][2] = new Muerte (0,2,"Blanco");
        tablero[0][3]= new Muerte (0,3,"Blanco");
        tablero[0][4] = new Vampiro (0,4,"Blanco");
        tablero[0][5] = new hombreLobo (0,5,"Blanco");
        
        tablero[5][0] = new hombreLobo(5,0,"Negro");
        tablero[5][1] = new Vampiro(5,1,"Negro");
        tablero[5][2] = new Muerte (5,2,"Negro");
        tablero[5][3]= new Muerte (5,3,"Negro");
        tablero[5][4] = new Vampiro (5,4,"Negro");
        tablero[5][5] = new hombreLobo (5,5,"Negro");
    }
    
    //getters
    public AbstractPiezas[][] getTablero(){
        return tablero;
    }
    public String getTurno(){
        return turno;
    }
    
    public void cambiarTurno(){
        turno = turno.equals("Blanco") ?  "Negro" : "Blanco";
    }
    
    public String getGanador() {
    int blancos = 0, negros = 0;
    for (AbstractPiezas[] fila : tablero) {
        for (AbstractPiezas pieza : fila) {
            if (pieza != null) {
                if (pieza.getColor().equals("Blanco")) blancos++;
                else negros++;
            }
        }
    }
    if (blancos == 0 && negros > 0) return "Negro";
    if (negros == 0 && blancos > 0) return "Blanco";
    return "Empate";
}
    
    public String mover(int x1, int y1, int x2, int y2){
        //x1,y1 - posicion actual; x2,y2 - posicion destino
        AbstractPiezas pieza = tablero[x1][y1];
        if (pieza == null) return "No hay pieza en esa casilla.";
        if (!pieza.getColor().equals(turno)) return "No es tu turno.";
        
        AbstractPiezas objetivo = tablero[x2][y2];
        if (objetivo == null){
            tablero[x2][y2] =pieza;
            tablero[x1][y1] =null;
            pieza.setX(x2);
            pieza.setY(y2);
            return pieza.getClass().getSimpleName() + " se movio a (" + x2 + "," + y2 + ")";
        }
        
        if (!objetivo.getColor().equals(pieza.getColor())) {
    int daño = pieza.getAtaque();
    int vidaAntes = objetivo.getVida();
    int escudoAntes = objetivo.getEscudo();

    objetivo.recibirDanio(daño);

    int vidaDespues = Math.max(0, objetivo.getVida());
    int escudoDespues = Math.max(0, objetivo.getEscudo());
    
    javax.swing.JOptionPane.showMessageDialog(null,
        pieza.getClass().getSimpleName() + " ataca a " + objetivo.getClass().getSimpleName() + "!\n\n"
        + "Ataque: " + daño + "\n"
        + "Vida: " + vidaAntes + " → " + vidaDespues + "\n"
        + "Escudo: " + escudoAntes + " → " + escudoDespues,
        "⚔️ Ataque ejecutado",
        javax.swing.JOptionPane.INFORMATION_MESSAGE
    );

    if (vidaDespues <= 0) {
        tablero[x2][y2] = pieza;
        tablero[x1][y1] = null;
        pieza.setX(x2);
        pieza.setY(y2);
        return pieza.getClass().getSimpleName() + " destruyó al enemigo y ocupó su lugar!";
    } else {
        return "Ataque exitoso: enemigo con " + vidaDespues + " de vida restante.";
    }
}
        return "No puedes sobre una pieza de tu equipo.";
    }
    
    public String ataqueEspecial(int x1, int y1, int x2, int y2){
        AbstractPiezas atacante = tablero[x1][y1];
        if (atacante == null) return "No hay pieza en esa casilla.";
        if (!atacante.getColor().equals(turno)) return "No es tu turno.";
        
        AbstractPiezas objetivo = tablero[x2][y2];
        
    if (atacante instanceof Vampiro) {
        if (objetivo == null || objetivo.getColor().equals(atacante.getColor()))
            return "El vampiro solo puede drenar enemigos cercanos.";

        int dx = Math.abs(x1 - x2);
        int dy = Math.abs(y1 - y2);
        if (dx > 1 || dy > 1)
            return "El enemigo está demasiado lejos para chupar sangre.";

        ((Vampiro) atacante).chuparSangre(objetivo);
        if (!objetivo.estaViva()) tablero[x2][y2] = null;
        return "El vampiro chupó sangre y recuperó vida.";
    }

    
    if (atacante instanceof Muerte) {
        
        Object[] opciones = {"Lanzar lanza", "Conjurar zombie", "Cancelar"};
        int op = javax.swing.JOptionPane.showOptionDialog(
                null,
                "Elegí habilidad especial de la Muerte:",
                "Habilidad de la Muerte",
                javax.swing.JOptionPane.DEFAULT_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (op == 0) { // lanzar lanza
            if (objetivo == null || objetivo.getColor().equals(atacante.getColor()))
                return "Debés seleccionar un enemigo válido para lanzar la lanza.";

            int dx = Math.abs(x1 - x2);
            int dy = Math.abs(y1 - y2);
            boolean esLineaRecta2 = (dx == 2 && dy == 0) || (dx == 0 && dy == 2) || (dx == 2 && dy == 2);
            if (!esLineaRecta2)
                return "La lanza solo puede lanzarse a 2 casillas en línea recta o diagonal.";

            int mx = (x1 + x2) / 2;
            int my = (y1 + y2) / 2;
            if (tablero[mx][my] != null)
                return "La lanza no puede atravesar piezas.";

            ((Muerte) atacante).lanzarLanza(tablero,x2,y2);
            if (!objetivo.estaViva()) tablero[x2][y2] = null;
            return "La Muerte lanzó su lanza. El enemigo sufrió daño directo.";
        }

        if (op == 1) { // conjurar zombie
            if (objetivo != null)
                return "No podés conjurar sobre otra pieza.";
            return ((Muerte) atacante).conjurarZombie(tablero, x2, y2);
        }

        return "Habilidad cancelada.";
    }

    if (atacante instanceof hombreLobo)
        return "El Hombre Lobo no tiene habilidad especial activa (puede moverse 2 casillas).";

    
    if (atacante instanceof Zombie)
        return "El Zombie no tiene habilidades especiales.";

    return "Esa pieza no tiene habilidad especial.";
        
    }
    
    public boolean verificarFin(){
        boolean quedanBlancos=false;
        boolean quedanNegros=false;
        
        for(int i=0; i<N;i++){
            for (int j=0; j<N; j++){
                if (tablero[i][j] != null){
                    if (tablero[i][j].getColor().equals("Blanco")) quedanBlancos =true;
                    if (tablero[i][j].getColor().equals("Negro")) quedanNegros=true;
                }
            }
        }
        return !(quedanBlancos && quedanNegros);
    }
}
