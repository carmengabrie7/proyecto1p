package proyecto1p;

public class Main {
    public static void main(String[] args) {
        LogicaUsuarios logica = new LogicaUsuarios();
        MenuInicial menu = new MenuInicial(logica);
        menu.setVisible(true);
    }
}

