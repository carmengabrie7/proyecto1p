package proyecto1p;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistrarPartidas {
    private String jugador1;
    private String jugador2;
    private String resultado;
    private int puntosGanados;
    private String fecha;

    public RegistrarPartidas(String jugador1, String jugador2, String resultado, int puntosGanados) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.resultado = resultado;
        this.puntosGanados = puntosGanados;
        this.fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public String getJugador1() { 
        return jugador1; }
    public String getJugador2() { 
        return jugador2; }
    public String getResultado() { 
        return resultado; }
    public int getPuntosGanados() { 
        return puntosGanados; }
    public String getFecha() { return fecha; }
}

