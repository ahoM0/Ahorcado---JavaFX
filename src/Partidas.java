import com.sun.source.tree.WhileLoopTree;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Partidas {

    private int numpartida;
    private String jugadorpropone;
    private String jugadoradivina;
    private String palabraadivinar;
    private int puntospartida=0;
    private String ganador;


    private String perdedor;

    private String letra;
    private int contador=0;
    private int letrasadivinadas=0;
    Partidas() {}
    public Partidas(int numpartida, String jugadorpropone, String jugadoradivina, String palabraadivinar, int puntospartida, String ganador) {
        this.numpartida = numpartida;
        this.jugadorpropone = jugadorpropone;
        this.jugadoradivina = jugadoradivina;
        this.palabraadivinar = palabraadivinar;
        this.puntospartida = puntospartida;
        this.ganador = ganador;
    }

    //Metodo que se ejecuta si el usuario quiere añadir una palabra al fichero palabra
    public void cambiarficheropalabras(Scanner entrada) throws IOException {
        ArrayList<String> palabras = listapalabras();
        FileReader fic = new FileReader("./Ficheros/Palabras.txt");
        BufferedReader BR = new BufferedReader(fic);
        String contenido;
        System.out.println("Las palabras actuales son: ");
        while ((contenido=BR.readLine())!=null){
            System.out.println(contenido);
            palabras.add(contenido);
        }
        System.out.println("Escribe la palabra que desees añadir: ");
        String palabra;
        palabra = entrada.next();
        while(comprobarpalabra(palabra, palabras)){
            System.out.println("Introduce una palabra que no este registrada");
            palabra = entrada.next();
            comprobarpalabra(palabra,palabras);
        }
        FileWriter fichero = new FileWriter("./Ficheros/Palabras.txt",true);
        fichero.write(System.lineSeparator()+palabra);
        palabras.add(palabra);
        System.out.println("¿Deseas añadir mas palabras? Elige SI o NO");
        String opt = entrada.next();
        while(opt.equals("SI") || opt.equals("si") || opt.equals("Si")) {
            System.out.println("Escribe la palabra que quieras añadir");
            palabra = entrada.next();
            while(comprobarpalabra(palabra, palabras)){
                System.out.println("Introduce una palabra que no este registrada");
                palabra = entrada.next();
                comprobarpalabra(palabra,palabras);
            }
            fichero.write(System.lineSeparator()+palabra);
            palabras.add(palabra);
            System.out.println("¿Deseas añadir mas palabras? Elige SI o NO");
            opt = entrada.next();
        }
        fichero.close();
        System.out.println();
        System.out.println("Las palabras que hay actualmente son: ");
        for (int i = 0; i < palabras.size(); i++) {
            System.out.println(palabras.get(i));
        }

    }
    //METODO QUE GUARDA EN UNA LISTA LAS PALABRAS QUE HAY EN EL FICHERO PALABRAS
    public ArrayList<String> listapalabras() throws IOException {
        ArrayList<String> palabras = new ArrayList<>();
        FileReader fic = new FileReader("./Ficheros/Palabras.txt");
        BufferedReader BR = new BufferedReader(fic);
        String contenido;
        while ((contenido=BR.readLine())!=null){
            palabras.add(contenido);
        }
        return palabras;
    }
    //METODO QUE GUARDA EN UNA LISTA LAS PARTIDAS QUE HAY EN EL DFICHERO PARTIDAS
    public ArrayList<Partidas> listapartidas() throws IOException {
        ArrayList<Partidas> partys = new ArrayList<>();
        FileReader fic = new FileReader("./Ficheros/Partidas.txt");
        BufferedReader BR = new BufferedReader(fic);
        String contenido;
        while ((contenido=BR.readLine())!=null){
            String [] content = contenido.split(";");
            int numpartida= Integer.parseInt(content[0]);
            String jugpropone=content[1];
            String jugadivina=content[2];
            String palabra=content[3];
            int puntos = Integer.parseInt(content[4]);
            String ganador=content[5];
            Partidas party = new Partidas(numpartida,jugpropone,jugadivina,palabra,puntos,ganador);
            partys.add(party);
        }
        return partys;
    }
    //METODO QUE ESTABLECE LOS PUNTOS DE LA PARTIDA
    private void establecerpuntos(Scanner entrada){
        System.out.println("Introduce los puntos que recibe por ganar la partida como max 10.000");
        int puntos = entrada.nextInt();
        while(puntos<0){
            System.out.println("Introduce puntos mayores de 0");
            puntos=entrada.nextInt();
        }
        setPuntos(puntos);
    }

    //
    public void jugar(Scanner entrada) throws IOException {
        ListadoJugadores test = new ListadoJugadores();
        ArrayList<Partidas> partys =listapartidas();
        int opcion = 0;
        System.out.println("MENU DE LA PARTIDA:");
        System.out.println();
        while (opcion!=3) {
            System.out.println("1 - Comenzar partida");
            System.out.println("2 - Añadir una palabra al fichero");
            System.out.println("3 - Volver al MENU");
            System.out.print("Selecciona una opcion:   ");
            opcion = entrada.nextInt();
            espaciarcontenido(entrada);
            switch (opcion) {
                case 2:
                    cambiarficheropalabras(entrada);
                    break;
                default:
            }
            if (opcion==1){
                partida(entrada);
                System.out.println("Termino la partida");
                System.out.println();
                entrada.nextLine();
            }
        }

    }

    /**                <h3>METODO PRINCIPAL, ES DONCE EJECUTA LA PARTIDA</h3>
     *                 El metodo primero pide al usuario que elija los jugadores que participan,
     *                 segundo pide que elijas una palabra y por tercero que introduzcas los puntos,
     *                 luego muestra la palabra en forma de guiones y la horca, cuando introduzcas una
     *                 letra mal sumara 1 fallo cuando llegue a 6 fallos el juego terminara y perdera el
     *                 jugador que adivina y basicamente es eso un bucle que ejecuta lo mismo hasta que
     *                 el jugador adivine la palabra o llega a 6 fallos.
     *                 Cuando termine la partida muestra los resultados.
     * @see Partidas#partida(Scanner)
     */
    public void partida(Scanner entrada) throws IOException {
        while(getJugadoradivina()==null|| getJugadorpropone()==null){
            System.out.println();
            System.out.println("DEBES ELEGIR LOS DOS JUGADORES QUE PARTICIPARAN");
            System.out.println();
            seleccionaJugador(entrada);
        }
        espaciarcontenido(entrada);
        while(getPalabraadivinar()==null){
            System.out.println("DEBES ELEGIR UNA PALABRA PARA PODER JUGAR");
            System.out.println();
            System.out.println("1 - Proponer una palabra(NO SE AÑADIRA LA PALABRA AL FICHERO)");
            System.out.println("2 - Elegir una palabra del fichero");
            System.out.println();
            System.out.print("Elige cual opcion prefieres:   ");
            int opcion;
            opcion=entrada.nextInt();
            switch (opcion) {
                case 1:
                    proponerPalabra(entrada);
                    break;
                case 2:
                    elegirpalabra(entrada);
                    break;
                default:
            }

        }
        espaciarcontenido(entrada);
        while(getPuntos()==0){
            System.out.println();
            System.out.println("DEBES INTRODUCIR LOS PUNTOS");
            System.out.println();
            establecerpuntos(entrada);
            System.out.println();
            System.out.println();
        }
        espaciarcontenido(entrada);
        int fallos=0;
        ArrayList<String> letrasUtilizadas=new ArrayList<>();
        String [] word = palabraguiones();
        System.out.println();
        mostrarahorcado(fallos);
        System.out.print("La palabra tiene "+getPalabraadivinar().length()+" letras:    ");mostrarpalabra(word);
        System.out.println();
        while(fallos!=6 && letrasadivinadas<getPalabraadivinar().length()){
            System.out.print("Introduce una letra en minuscula:  ");
            this.letra=entrada.next();
            boolean comprobar = compruebaletra(this.letra);
            if (!comprobar){
                fallos+=1;
                if (!comprobarrepetidaletra(letrasUtilizadas)){
                    letrasUtilizadas.add(this.letra);
                }
            }else{
                if (!comprobarrepetidaletra(letrasUtilizadas)){
                    letrasUtilizadas.add(this.letra);
                }
            }
            String [] pal = sustituyeguionporletra(word);
            System.out.println();
            mostrarahorcado(fallos);
            System.out.print("La palabra tiene "+getPalabraadivinar().length()+" letras:    ");mostrarpalabra(pal);
            System.out.println();
            System.out.print("La letras escritas son: ");mostrarletrasutilizadas(letrasUtilizadas);
            System.out.println();
        }
        if (fallos==6){
            mostrarahorcado(fallos);
            setGanador(getJugadorpropone());
            setPerdedor(getJugadoradivina());
            System.out.println("No se ha resuelto la palabra VICTORIA para : "+getGanador());
        }else if (letrasadivinadas>=getPalabraadivinar().length()){
            setGanador(getJugadoradivina());
            setPerdedor(getJugadorpropone());
            System.out.println();
            System.out.println("Se ha conseguido resolver la palabra VICTORIA para: "+getGanador());
        }
        añadirPartida();
        actualizarficherojugadores();
    }

    /**                <h3>METODO PARA SELECCIONAR LOS JUGADORES QUE PARTICIPAN</h3>
     *                 Este metodo muestra primero los jugadores que estan inscritos en el fichero ,
     *                 despues pide que eligas al que propone la palabra y al que la tiene que adivinar.
     *                 Y cuando se eligen guardamos quien es el que propone y adivina para luego actualizar sus
     *                 datos dependiendo quien gane.
     * @see Partidas#seleccionaJugador(Scanner) ()
     */
    public void seleccionaJugador(Scanner entrada) throws IOException {
        ArrayList<Jugador> players = ListadoJugadores.registroJugadores();
        FileReader fic = new FileReader("./Ficheros/Jugadores.txt");
        BufferedReader BR = new BufferedReader(fic);
        String contenido;
        System.out.println("Estos son los jugadores:");

        while((contenido=BR.readLine())!=null) {
            String [] content = contenido.split(";");
            String nombre=content[0];
            int vic= Integer.parseInt(content[1]);
            int der= Integer.parseInt(content[2]);
            int punt= Integer.parseInt(content[3]);
            Jugador jug = new Jugador(nombre,vic,der,punt);
            players.add(jug);
        }
        ListadoJugadores.mostrarEstadisticas();
        String nompropone;
        System.out.println("Selecciona el jugador que propone la palabra");
        nompropone=entrada.next();
        while(!comprobarJugador(players, nompropone)){
            System.out.println("Escriba el nombre de un jugador existente");
            nompropone=entrada.next();
            comprobarJugador(players, nompropone);
        }
        setJugadorpropone(nompropone);
        System.out.println("Ahora selecciona el jugador que adivina la palabra");
        String nomadivina;
        nomadivina=entrada.next();
        while(nomadivina!=nompropone && !comprobarJugador(players, nomadivina)){
            System.out.println("Escriba el nombre de un jugador existente");
            nomadivina=entrada.next();
            comprobarJugador(players, nomadivina);
        }
        setJugadoradivina(nomadivina);
        System.out.println();
        System.out.println();
        System.out.println();
    }
    public boolean comprobarJugador(ArrayList <Jugador> players, String nom){
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getNombre().equals(nom)){
                return true;
            }
        }
        return false;
    }
    //METODO PARA ACTUALIZAR LOS DATOS DEL GANADOR Y EL PERDEDOR
    private ArrayList<Jugador> darpuntosganador() throws IOException {
        ArrayList<Jugador> players = ListadoJugadores.registroJugadores();
        int puntos = getPuntos();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getNombre().equals(getGanador())){
                players.get(i).setPuntos(puntos);
                players.get(i).setPartidasGanadas(1);
            }
            if (players.get(i).getNombre().equals(getPerdedor())){
                players.get(i).setPartidasPerdidas(1);
            }
        }

        return players;
    }
    /**                <h3>METODO PARA ACTUALIZAR LOS DATOS DEL FICHERO DE LOS JUGADORES</h3>
     *                 Este metodo crea un archivo temporal con los datos actualizados de los jugadores y todos los demas
     *                 y lo sustituye por el que tenemos.
     * @see Partidas#actualizarficherojugadores() ()
     */
    private void actualizarficherojugadores() throws IOException {
        ArrayList<Jugador> players = darpuntosganador();

        FileWriter ficher = new FileWriter("./Ficheros/Jugadores.txt.tmp",true);
        for (int i = 0; i < players.size(); i++) {
            String jugador=players.get(i).getNombre()+";"+players.get(i).getPartidasGanadas()+";"+players.get(i).getPartidasPerdidas()+";"+players.get(i).getPuntos();
            ficher.write(jugador+System.lineSeparator());
        }
        ficher.close();

        File file = new File("./Ficheros/Jugadores.txt");
        file.delete();
        File file2 = new File("./Ficheros/Jugadores.txt.tmp");
        file2.renameTo(file);
    }
    /**                <h3>METODO DEVUELVE LISTA DE PARTIDAS</h3>
     * @return  Nos devuelve una lista de las partidas que estan escritas en el fichero partidas
     * @see Partidas#listarpartidas()
     */
    public ArrayList<Partidas> listarpartidas() throws IOException {
        ArrayList<Partidas> partys = new ArrayList<>();
        FileReader fic = new FileReader("./Ficheros/Partidas.txt");
        BufferedReader BR = new BufferedReader(fic);
        String contenido;
        while((contenido=BR.readLine())!=null) {
            String [] content = contenido.split(";");
            int numparty= Integer.parseInt(content[0]);
            String propone = content[1];
            String adivina = content[2];
            String palabra = content[3];
            int points=Integer.parseInt(content[4]);
            String winner=content[5];
            Partidas party = new Partidas(numparty,propone,adivina,palabra,points,winner);
            partys.add(party);
        }

        return partys;
    }

    /**                <h3>METODO PARA AÑADIR UNA NUEVA PARTIDA AL FICHERO DE PARTIDAS</h3>
     *                 Este metodo lee el fichero de Partidas y calcula el numero de partidas, para asi saber
     *                 con que numero se identificara la partida que estan jugando que tambien se añadirá a ese fichero.
     * @see Partidas#añadirPartida()
     */
    public void añadirPartida() throws IOException {
        String numpartidita = String.valueOf(numpartida());
        String puntitos = String.valueOf(getPuntos());
        String change=numpartidita+";"+getJugadorpropone()+";"+getJugadoradivina()+";"+getPalabraadivinar()+";"+puntitos+";"+getGanador();
        FileWriter fichero = new FileWriter("./Ficheros/Partidas.txt",true);
        fichero.write(System.lineSeparator()+change);
        fichero.close();
    }
    /**                <h3>METODO Comprueba si la letra esta repetida en el array de letras utilizadas</h3>
     *                 Si la letra ya ha sido utilizada pues devuelve true y siginifica que no la añadiremos de nuevo
     * @see Partidas#comprobarrepetidaletra(ArrayList)
     */
    private boolean comprobarrepetidaletra(ArrayList<String> letrasutilizadas){
        for (int i = 0; i < letrasutilizadas.size(); i++) {
            if (this.letra.equals(letrasutilizadas.get(i))){
                return true;
            }
        }
        return false;
    }

    /**                <h3>METODO QUE COMPRUEBA SI LA LETRA ESTA EN LA PALABRA</h3>
     *                 Comprueba si la letra estqa en la palabra, el contador cuenta cuantas veces
     *                 esta la misma letra en la palabra, si la letra esta en la palabra el metodo devuelve true
     *
     * @see Partidas#compruebaletra(String)
     */
    private boolean compruebaletra(String letra){
        contador=0;
        String palabrareal=getPalabraadivinar();
        String [] comprobarword = palabrareal.split("");
        for (int i = 0; i < comprobarword.length; i++) {
            if (comprobarword[i].equals(letra)){
                this.contador+=1;
            }
        }
        if (this.contador>0){
            return true;
        }
        return false;
    }

    /**                <h3>METODO SUSTITUIR GUIONES POR LA LETRA QUE ELIGIERON</h3>
     *                 Tenemos una palabra que es la palabraOriginal y luego la palabra en guiones(___) que es sobre la
     *                 que vamos a actuar, la palabra original no se toca.
     *                 Basicamente el primer bucle comrpobamos si la letra esta en la palabra original,
     *                 si está lo que hace es guardar la posicion para luego en otro bucle donde buscamos la posicion que sacamos
     *                 del bucle anterior y si esta en la posicion un guion significa que esta libre, si esta libre pues sustituimos
     *                 el guion por la letra y ya terminaria el metodo devolviendo la palabra actualizada.
     *
     * @return  Devuelve la palabra que deben adivinar pero cambiando el guion por la letra que eligieron.
     * @see Partidas#sustituyeguionporletra(String[])
     */
    private String[] sustituyeguionporletra(String [] word){
        String palabraOriginal=getPalabraadivinar();
        ArrayList<Integer> posiciones=new ArrayList<>();
        String [] comprobarword = palabraOriginal.split("");
        int pos=0;

        for (int i = 0; i < comprobarword.length; i++) {
            if (comprobarword[i].equals(this.letra)){
                pos=i;
                posiciones.add(pos);
            }else{
                posiciones.add(999);
            }
        }

        for (int i = 0; i < word.length; i++) {
            if (word[i].equals("_") && i==posiciones.get(i)){
                word[i]=this.letra;
                this.letrasadivinadas+=1;
            }
        }

        return word;

    }

    /**                <h3>METODO PARA EL NUMERO DE PARTIDAS GUARDADAS EN UN FICHERO</h3>
     *                 Este metodo lee el fichero de Partidas y calcula el numero de partidas, para asi saber
     *                 con que numero se identificara la partida que estan jugando que tambien se añadirá a ese fichero.
     *
     * @return  Devuelve un numero que será el identificador de las partidas que se añadirán
     * @see Partidas#numpartida()
     */
    public int numpartida() throws IOException {
        int num=0;
        FileReader fic = new FileReader("./Ficheros/Partidas.txt");
        BufferedReader BR = new BufferedReader(fic);
        String contenido = null;
        while((contenido=BR.readLine())!=null){
            num +=1;
        }
        return num;
    }

    /**                <h3>METODO CREAR LA PALABRA EN FORMA DE GUIONES</h3>
     *                 Este metodo crea una nueva palabra pero con la longitud de la palabra que deben adivinar pero sin contenido,
     *                 luego atraves de un bucle le damos contenido a la palabra escribiendo guiones en todas las posiciones.Ejemplo-(palabra[]="_,_,_,_")
     * @return Devuelve la palabra que deben adivinar en forma de guiones, como por ejemplo (palabra[]="t,r,e,n") pues la palabra que devolvera sera como (palabra[]="_,_,_,_")
     * @see Partidas#palabraguiones()
     */
    private String[] palabraguiones(){
        String [] word =new String[getPalabraadivinar().length()];
        for (int i = 0; i < word.length; i++) {
            word[i]="_";
        }

        return word;
    }

    /**                <h3>METODO PARA MOSTRAR LA HORCA</h3>
     *                  Este metodo le pasamos los fallos desde el metodo de partida y dependido de la cantidad de fallos
     *                  nos mostrara la situacion 1,2,3,4,5 o 6 de la horca.
     * @see Partidas#mostrarahorcado(int)
     */
    private void mostrarahorcado(int fallos) {
        switch (fallos) {
            case 0:
                Art.ahorcado0();
                break;
            case 1:
                Art.ahorcado1();
                break;
            case 2:
                Art.ahorcado2();
                break;
            case 3:
                Art.ahorcado3();
                break;
            case 4:
                Art.ahorcado4();
                break;
            case 5:
                Art.ahorcado5();
                break;
            case 6:
                Art.ahorcado6();
                break;
            default:
        }
    }

    /**                <h3>METODO PARA MOSTRAR LAS LETRAS QUE HAN SIDO ESCRITAS POR EL USUARIO DURANTE LA PARTIDA</h3>
     *                 Ha este metodo le pasamos un Arraylist que tiene alamacenadas la letras que se han utilizado en la partida.
     *                 Lo unico que hace es mostrar la partida
     * @see Partidas#mostrarletrasutilizadas(ArrayList)
     */
    private void mostrarletrasutilizadas(ArrayList<String> letrasutilizadas){
        for (int i = 0; i < letrasutilizadas.size(); i++) {
            System.out.print(letrasutilizadas.get(i)+", ");
        }
    }

    /**                <h3>METODO PARA MOSTRAR LA PALABRA PERO CON GUIONES</h3>
     *                  Este metodo le pasamos un String[] que es la palabra con guiones pero que se irá modificando, entonces
     *                  he creado este metodo que tu le pasas la palabra ya modificada mediante otro metodo y lo unico que hace es mostrarla.
     * @see Partidas#mostrarpalabra(String[])
     */
    private void mostrarpalabra(String [] word){
        for (int i = 0; i < word.length; i++) {
            System.out.print(word[i]);
        }
    }

    /**                 <h3>METODO PARA PROPONER UNA PALABRA</h3>
     *                  EL metodo pide al usuario que introduzca una palabra que será la que tendra que adivinar el segundo jugador,
     *                  la palabra se guarda con el setPalabraadivinar para tener almacenada la palabra en una variable global y asi poder usarla
     *                  en otros metodos.
     * @return El metodo devuelve la palabra que ha propuesto el usuario.
     * @see Partidas#proponerPalabra(Scanner)
     */
    private String proponerPalabra(Scanner entrada){
        String palabra;
        System.out.println("Introduce una palabra");
        palabra=entrada.next();
        palabra=palabra.toLowerCase();
        setPalabraadivinar(palabra);
        return palabra;
    }

    /**                <h3>METODO PARA ELIGIR UNA PALABRA</h3>
     *                 Es un metodo un metodo que lee el fichero de palabras, lo muestra y pide al usuario que elija una de las palabras
     *                 que se han mostrado, el metododo comprueba que la palabra que elijas sea correcta atraves de la funcion comprobarpalabra()
     *                 que basicamente lo que hace es comprobar que la palabra sea igual a alguna del fichero.
     *                 Este metodo al haber comprobado que la palabra introducida este en el fichero lo siguiente que hace es guardar la palabra
     *                 elegida en una variable global para utilizarla mas tarde en otros metodos.
     * @see Partidas#elegirpalabra(Scanner)
     */
    public void elegirpalabra(Scanner entrada) throws IOException {
        ArrayList<String> palabras = listapalabras();
        FileReader fic = new FileReader("./Ficheros/Palabras.txt");
        BufferedReader BR = new BufferedReader(fic);
        String contenido, palabra = null;

        System.out.println();
        System.out.println("Las palabras son: ");
        while ((contenido=BR.readLine())!=null){
            System.out.println(contenido);
            palabras.add(contenido);
        }
        System.out.println("Elige una palabra con la que desees jugar");
        palabra=entrada.next();
        //Comprueba que la palabra sea correcta
        while (!comprobarpalabra(palabra,palabras)){
            System.out.println("Introduce una palabra que este registrada");
            palabra=entrada.next();
            comprobarpalabra(palabra,palabras);

        }
        System.out.println("La palabra elegida es "+palabra);
        //Guarda la palabra elegida en una variable global para utilizarla mas tarde
        setPalabraadivinar(palabra);
        System.out.println();
    }
    private boolean comprobarpalabra(String palabra, ArrayList <String> palabras){
        for (int i = 0; i < palabras.size(); i++) {
            if (palabras.get(i).equals(palabra)){
                return true;
            }
        }
        return false;
    }

    //El metodo espaciarcontenido hace un bucle de soutprintln(); con el fin de conseguir espacio a la hora de ejecutar las funciones en el terminal
    private void espaciarcontenido(Scanner entrada){
        for (int i = 0; i < 15; i++) {
            System.out.println();
        }
    }

    //GETTERS Y SETTERS DE LAS VARIABLES QUE SE VAN A USAR
    public String getPerdedor() {
        return perdedor;
    }

    public void setPerdedor(String perdedor) {
        this.perdedor = perdedor;
    }

    public String getJugadorpropone() {
        return jugadorpropone;
    }

    public void setJugadorpropone(String jugadorpropone) {
        this.jugadorpropone = jugadorpropone;
    }

    public String getJugadoradivina() {
        return jugadoradivina;
    }

    public void setJugadoradivina(String jugadoradivina) {
        this.jugadoradivina = jugadoradivina;
    }

    public String getPalabraadivinar() {
        return palabraadivinar;
    }

    public void setPalabraadivinar(String palabraadivinar) {
        this.palabraadivinar = palabraadivinar;
    }
    public int getPuntos() {
        return puntospartida;
    }

    public void setPuntos(int puntos) {
        this.puntospartida += puntos;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }
}
