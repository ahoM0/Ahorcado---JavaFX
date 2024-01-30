import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner entrada = new Scanner(System.in);
        ListadoJugadores test = new ListadoJugadores();
        Partidas tasr = new Partidas();
        int opcion;
        System.out.println();
        do {
            System.out.println("1 - Jugar");
            System.out.println("2 - Mostrar Ranking(10 primeros) con mas puntos");
            System.out.println("3 - Crear nuevo Jugador");
            System.out.println("4 - Listar Jugadores alfabéticamente");
            System.out.println("5 - Mostrar estadísticas de los jugadores");
            System.out.println("6 - Finalizar juego");
            System.out.println();
            System.out.print("Selecciona una opcion:   ");
            opcion = entrada.nextInt();

            System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();
            System.out.println();System.out.println();System.out.println();System.out.println();System.out.println();
            System.out.println();System.out.println();System.out.println();System.out.println();
            switch (opcion) {
                case 1:
                    tasr.jugar(entrada);
                    break;
                case 2:
                    test.mostrarRanking();
                    break;
                case 3:
                    test.nuevoJugador(entrada);
                    break;
                case 4:
                    test.listaralfabeticamenteJugadores();
                    break;
                case 5:
                    ListadoJugadores.mostrarEstadisticas();
                    break;
                default:
            }
        }while(opcion!=6);
        System.out.println("Gracias por su pasarte");



    }
}