import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ListadoJugadores {

    public boolean comprobarJugador(ArrayList <Jugador> players, String nom){
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getNombre().equals(nom)){
                return true;
            }
        }
        return false;
    }

    //METODO QUE AÑADE UN NUEVO JUGADOR AL FICHERO JUGADORES
    public void nuevoJugador(Scanner entrada) throws IOException {
        ArrayList<Jugador> players = registroJugadores();
        FileReader fic = new FileReader("./Ficheros/Jugadores.txt");
        BufferedReader BR = new BufferedReader(fic);
        System.out.println("Estos son los jugadores:  ");
        System.out.println();
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Nombre: "+players.get(i).getNombre()+"---Victorias: "+players.get(i).getPartidasGanadas()+"---Derrotas: "+players.get(i).getPartidasPerdidas()+"---Puntos: "+players.get(i).getPuntos());
        }
        String nombre;
        System.out.println();
        System.out.print("Introduce el nombre del nuevo jugador:   ");nombre = entrada.next();
        System.out.println();
        while(comprobarJugador(players,nombre)){
            System.out.println("Introduce el nombre de un jugador que no este registrado");
            nombre = entrada.next();
            comprobarJugador(players,nombre);
        }
        Jugador jug = new Jugador(nombre,0,0,0);
        String jugador=nombre+";"+0+";"+0+";"+0;
        FileWriter fichero = new FileWriter("./Ficheros/Jugadores.txt",true);
        fichero.write(System.lineSeparator()+jugador);
        players.add(jug);

        System.out.println("Estos son los jugadores: ");
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Nombre: "+players.get(i).getNombre()+"---Victorias: "+players.get(i).getPartidasGanadas()+"---Derrotas: "+players.get(i).getPartidasPerdidas()+"---Puntos: "+players.get(i).getPuntos());
        }


    }
    public static ArrayList<Jugador> registroJugadores() throws IOException {
        ArrayList<Jugador> players = new ArrayList<>();
        FileReader fic = new FileReader("./Ficheros/Jugadores.txt");
        BufferedReader BR = new BufferedReader(fic);
        String contenido;
        while((contenido=BR.readLine())!=null) {
            String [] content = contenido.split(";");
            String nombre=content[0];
            int vic= Integer.parseInt(content[1]);
            int der= Integer.parseInt(content[2]);
            int punt= Integer.parseInt(content[3]);
            Jugador jug = new Jugador(nombre,vic,der,punt);
            players.add(jug);
        }
        return players;
    }

    //METODO QUE MUESTRA EL RANKING DE JUGADORES
    public void mostrarRanking() throws IOException {
        ArrayList<Jugador> players = registroJugadores();
        ArrayList<Jugador> reales = registroJugadores();
        ArrayList<Jugador> ranking = new ArrayList<>();
        int longitud=players.size();
        int max=-1;
        int contador=0;
        String nommax = null;
        while(contador<longitud) {
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getPuntos() > max) {
                    max = players.get(i).getPuntos();
                }
            }
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getPuntos()==max) {
                    nommax = players.get(i).getNombre();
                    players.remove(players.get(i));
                }
            }
            for (int i = 0; i < reales.size(); i++) {
                if (reales.get(i).getNombre().equals(nommax)) {
                    ranking.add(reales.get(i));
                    contador=contador+1;
                }
            }
            max=-1;
        }
        for (int i = 0; i < ranking.size(); i++) {
            System.out.println("Top-"+(i+1)+" Nombre: "+ranking.get(i).getNombre()+"   Victorias: "+ranking.get(i).getPartidasGanadas()+"   Derrotas: "+ranking.get(i).getPartidasPerdidas()+"   Puntos: "+ranking.get(i).getPuntos());
        }
        System.out.println();
    }

    public void listaralfabeticamenteJugadores() throws IOException {
        ArrayList<Jugador> players = registroJugadores();
        ArrayList<String> nombres = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            nombres.add(players.get(i).getNombre());
        }
        //Aqui utilizo la clase Collection que me ordena alfabeticamente por nombres
        Collections.sort(nombres);
        for (String nombre : nombres) {
            System.out.println("- "+nombre);
        }

    }
    public static void mostrarEstadisticas() throws IOException {
        ArrayList<Jugador> players = registroJugadores();
        for (int i = 0; i < players.size(); i++) {
            System.out.println("Nombre: "+players.get(i).getNombre()+"   Victorias: "+players.get(i).getPartidasGanadas()+"   Derrotas: "+players.get(i).getPartidasPerdidas()+"   Puntos: "+players.get(i).getPuntos());

        }
        System.out.println();
    }


}

class  Jugador{
    private String nombre;
    private int partidasGanadas;
    private int partidasPerdidas;
    private int puntos;

    public Jugador(String nombre, int partidasGanadas, int partidasPerdidas, int puntos) {
        this.nombre = nombre;
        this.partidasGanadas = partidasGanadas;
        this.partidasPerdidas = partidasPerdidas;
        this.puntos = puntos;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getPartidasGanadas() {
        return partidasGanadas;
    }
    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas += partidasGanadas;
    }
    public int getPartidasPerdidas() {
        return partidasPerdidas;
    }
    public void setPartidasPerdidas(int partidasPerdidas) {
        this.partidasPerdidas += partidasPerdidas;
    }
    public int getPuntos() {
        return puntos;
    }
    public void setPuntos(int puntos) {
        this.puntos += puntos;
    }
}
