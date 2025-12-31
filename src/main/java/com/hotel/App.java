package com.hotel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Gestió de reserves d'un hotel.
 */
public class App {

    // --------- CONSTANTS I VARIABLES GLOBALS ---------

    // Tipus d'habitació
    public static final String TIPUS_ESTANDARD = "Estàndard";
    public static final String TIPUS_SUITE = "Suite";
    public static final String TIPUS_DELUXE = "Deluxe";

    // Serveis addicionals
    public static final String SERVEI_ESMORZAR = "Esmorzar";
    public static final String SERVEI_GIMNAS = "Gimnàs";
    public static final String SERVEI_SPA = "Spa";
    public static final String SERVEI_PISCINA = "Piscina";

    // Capacitat inicial
    public static final int CAPACITAT_ESTANDARD = 30;
    public static final int CAPACITAT_SUITE = 20;
    public static final int CAPACITAT_DELUXE = 10;

    // IVA
    public static final float IVA = 0.21f;

    // Scanner únic
    public static Scanner sc = new Scanner(System.in);

    // HashMaps de consulta
    public static HashMap<String, Float> preusHabitacions = new HashMap<String, Float>();
    public static HashMap<String, Integer> capacitatInicial = new HashMap<String, Integer>();
    public static HashMap<String, Float> preusServeis = new HashMap<String, Float>();

    // HashMaps dinàmics
    public static HashMap<String, Integer> disponibilitatHabitacions = new HashMap<String, Integer>();
    public static HashMap<Integer, ArrayList<String>> reserves = new HashMap<Integer, ArrayList<String>>();

    // Generador de nombres aleatoris per als codis de reserva
    public static Random random = new Random();

    // --------- MÈTODE MAIN ---------

    /**
     * Mètode principal. Mostra el menú en un bucle i gestiona l'opció triada
     * fins que l'usuari decideix eixir.
     */
    public static void main(String[] args) {
        inicialitzarPreus();

        int opcio = 0;
        do {
            mostrarMenu();
            opcio = llegirEnter("Seleccione una opció: ");
            gestionarOpcio(opcio);
        } while (opcio != 6);

        System.out.println("Eixint del sistema... Gràcies per utilitzar el gestor de reserves!");
    }

    // --------- MÈTODES DEMANATS ---------

    /**
     * Configura els preus de les habitacions, serveis addicionals i
     * les capacitats inicials en els HashMaps corresponents.
     */
    public static void inicialitzarPreus() {
        // Preus habitacions
        preusHabitacions.put(TIPUS_ESTANDARD, 50f);
        preusHabitacions.put(TIPUS_SUITE, 100f);
        preusHabitacions.put(TIPUS_DELUXE, 150f);

        // Capacitats inicials
        capacitatInicial.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        capacitatInicial.put(TIPUS_SUITE, CAPACITAT_SUITE);
        capacitatInicial.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Disponibilitat inicial (comença igual que la capacitat)
        disponibilitatHabitacions.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        disponibilitatHabitacions.put(TIPUS_SUITE, CAPACITAT_SUITE);
        disponibilitatHabitacions.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Preus serveis
        preusServeis.put(SERVEI_ESMORZAR, 10f);
        preusServeis.put(SERVEI_GIMNAS, 15f);
        preusServeis.put(SERVEI_SPA, 20f);
        preusServeis.put(SERVEI_PISCINA, 25f);
    }

    /**
     * Mostra el menú principal amb les opcions disponibles per a l'usuari.
     */
    public static void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("\n1. Reservar una habitació");
        System.out.println("2. Alliberar una habitació");
        System.out.println("3. Consultar disponibilitat");
        System.out.println("4. Llistar reserves per tipus");
        System.out.println("5. Obtindre una reserva");
        System.out.println("6. Ixir\n");
    }

    /**
     * Processa l'opció seleccionada per l'usuari i crida el mètode corresponent.
     */
    public static void gestionarOpcio(int opcio) {
        switch (opcio) {
            case 1:
                reservarHabitacio();
                break;
            case 2:
                alliberarHabitacio();
                break;
            case 3:
                consultarDisponibilitat();
                break;
            case 4:
                obtindreReservaPerTipus();
                break;
            case 5:
                obtindreReserva();
                break;
            case 6:
                System.out.println("Eixint del sistema de reserves..");
                break;
            default:
                System.out.println("Opció no vàlida ");
                break;
        }
    }

    /**
     * Gestiona tot el procés de reserva: selecció del tipus d'habitació,
     * serveis addicionals, càlcul del preu total i generació del codi de reserva.
     */
    public static void reservarHabitacio() {
        System.out.println("\n===== RESERVAR HABITACIÓ =====");

        // Selecciona tipo de habitación disponible
        String tipusHabitacio = seleccionarTipusHabitacioDisponible();
        // Selecciona servicios adicionales
        ArrayList<String> serveisSeleccionats = seleccionarServeis();
        // Calcula subtotal y total con IVA
        float subtotal = calcularPreuTotal(tipusHabitacio, serveisSeleccionats); // sin IVA
        float iva = subtotal * IVA;
        float total = subtotal + iva;
        // Genera código de reserva
        int codiReserva = generarCodiReserva();

        // Formatea los servicios para mostrar con precios
        String serveisFormatejats = "";
        if (serveisSeleccionats.isEmpty()) {
            serveisFormatejats = "Ninguno";
        } else {
            for (int i = 0; i < serveisSeleccionats.size(); i++) {
                String servei = serveisSeleccionats.get(i);
                serveisFormatejats += servei + " (" + preusServeis.get(servei) + "€)";
                if (i < serveisSeleccionats.size() - 1) {
                    serveisFormatejats += ", ";
                }
            }
        }

        // Guarda la reserva en el HashMap
        ArrayList<String> reserva = new ArrayList<>();

        reserva.add(tipusHabitacio); // posición 0 = tipus
        reserva.add(serveisFormatejats); // posición 1 = serveis
        reserva.add(String.valueOf(subtotal)); // posición 2 = subtotal
        reserva.add(String.valueOf(iva)); // posición 3 = IVA
        reserva.add(String.valueOf(total)); // posición 4 = TOTAL

        reserves.put(codiReserva, reserva);

        // Mostrar datos de la reserva
        System.out.println("\nCalculem el total...");
        System.out.println("Preu habitació: " + preusHabitacions.get(tipusHabitacio) + " euros");
        System.out.println("Serveis: " + serveisFormatejats + " euros");
        System.out.println("Subtotal: " + subtotal + " euros");
        System.out.println("IVA (21%): " + iva + " euros");
        System.out.println("TOTAL: " + total + " euros");
        System.out.println("Reserva creada amb èxit.");
        System.out.println("Codi de reserva: " + codiReserva);

    }

    /**
     * Pregunta a l'usuari un tipus d'habitació en format numèric i
     * retorna el nom del tipus.
     */
    public static String seleccionarTipusHabitacio() {
        // Variable que almacena el tipo de habitacion seleccionado. Empieza con null
        // para poder usarla en el bucle while.
        String tipusHabitacio = null;
        // sc.nextLine(); // Consume el salto de línea pendiente ERROR!!!
        // Bucle que se repite hasta que se seleccione un tipo válido y haya
        // disponibilidad.
        while (tipusHabitacio == null) {

            System.out.print("Seleccione tipus d'habitació: ");
            if (!sc.hasNextLine())
                break;
            // Lee la entrada del usuario como texto completo, eliminando espacios.
            String entrada = sc.nextLine().trim();
            // Asignamos el tipo de habitación según la opción seleccionada
            switch (entrada) {
                case "1":
                    tipusHabitacio = TIPUS_ESTANDARD;
                    break;
                case "2":
                    tipusHabitacio = TIPUS_SUITE;
                    break;
                case "3":
                    tipusHabitacio = TIPUS_DELUXE;
                    break;
                default:
                    System.out.println("Opció no vàlida");
                    continue; // Reinicia el bucle si la opción no es válida
            }
            // Comprobamos la disponibilidad del tipo seleccionado.

            int disponibles = disponibilitatHabitacions.getOrDefault(tipusHabitacio, 0);
            if (disponibles <= 0) {
                System.out.println(
                        "No hi ha habitacions disponibles per al tipus seleccionat (" + tipusHabitacio + ").");
                tipusHabitacio = null; // Vuelve a null y reinicia el tipo seleccionado si no hay disponibilidad
            }

        }
        return tipusHabitacio; // Devuelve el tipo de habitación seleccionado valido y disponible.

    }

    /**
     * Mostra la disponibilitat i el preu de cada tipus d'habitació,
     * demana a l'usuari un tipus i només el retorna si encara hi ha
     * habitacions disponibles. En cas contrari, retorna null.
     */
    public static String seleccionarTipusHabitacioDisponible() {
        System.out.println("\nTipus d'habitació disponibles:");
        System.out.println("\n1. Estàndard - " + disponibilitatHabitacions.get(TIPUS_ESTANDARD)
                + " disponibles - " + preusHabitacions.get(TIPUS_ESTANDARD) + " euros");
        System.out.println("2. Suite - " + disponibilitatHabitacions.get(TIPUS_SUITE) + " disponibles - "
                + preusHabitacions.get(TIPUS_SUITE) + " euros");
        System.out.println("3. Deluxe - " + disponibilitatHabitacions.get(TIPUS_DELUXE) + " disponibles - "
                + preusHabitacions.get(TIPUS_DELUXE) + " euros\n");
        // Llamamos al metodo para seleccionar el tipo de habitacion.
        String tipusSeleccionat = seleccionarTipusHabitacio();

        if (tipusSeleccionat != null && disponibilitatHabitacions.get(tipusSeleccionat) > 0) {
            // Actualizamos la disponibilidad de habitaciones
            int disponibilidadActual = disponibilitatHabitacions.get(tipusSeleccionat);
            // Disminuimos en 1 la disponibilidad
            disponibilitatHabitacions.put(tipusSeleccionat, disponibilidadActual - 1);

            return tipusSeleccionat;
        } else {
            System.out.println("No hi ha habitacions disponibles d'aquest tipus.");
            return null;
        }
    }

    /**
     * Permet triar serveis addicionals (entre 0 i 4, sense repetir) i
     * els retorna en un ArrayList de String.
     */
    public static ArrayList<String> seleccionarServeis() {
        // ArrayList para almacenar los servicios seleccionados.
        ArrayList<String> serveisSeleccionats = new ArrayList<>();

        // Mostramos los servicios disponibles y sus precios.
        System.out.println("\nServeis addicionals (0-4):");
        System.out.println("\n0. Finalitzar.");
        System.out.println("1. Esmorzar (" + preusServeis.get(SERVEI_ESMORZAR) + " euros)");
        System.out.println("2. Gimnàs (" + preusServeis.get(SERVEI_GIMNAS) + " euros");
        System.out.println("3. Spa (" + preusServeis.get(SERVEI_SPA) + " euros)");
        System.out.println("4. Piscina (" + preusServeis.get(SERVEI_PISCINA) + " euros)\n");

        // Bucle infinito para seleccionar servicios.
        while (true) {
            // Solicitamos al usuario que seleccione un servicio.
            System.out.print("Vols afegir un servei?(s/n): ");
            String resposta = sc.nextLine().trim().toLowerCase();
            // Si la respuesta no es "s", salimos del bucle.
            if (!resposta.equals("s")) {
                break;
            }

            System.out.print("Seleccione servei:");
            // Leemos la opcion del servicio.
            String opcio = sc.nextLine().trim();
            // Variable para almacenar el servicio seleccionado.
            String servei = null;
            // Asignamos el servicio según la opción seleccionada.
            switch (opcio) {
                case "1":
                    servei = SERVEI_ESMORZAR;
                    break;
                case "2":
                    servei = SERVEI_GIMNAS;
                    break;
                case "3":
                    servei = SERVEI_SPA;
                    break;
                case "4":
                    servei = SERVEI_PISCINA;
                    break;
                case "0":
                    // En este caso, finalizamos la selección de servicios.
                    System.out.println("Finalitzant selecció de serveis.");
                    return serveisSeleccionats;
                default:
                    System.out.println("Opció no vàlida. Si us plau, seleccioneu una opció vàlida.");
                    continue;
            }
            // Si seleccionamos un servicio válido.
            if (servei != null) {
                // Comprobamos si el servicio ya ha sido seleccionado
                if (serveisSeleccionats.contains(servei)) {
                    System.out.println("Ja has afegit " + servei + ". ");
                } else {
                    // Si no, añadimos el servicio a la lista de seleccionados
                    serveisSeleccionats.add(servei);
                    System.out.println("Servei afegit:" + servei);
                }
            }
        }
        // Devuelve la lista de servicios seleccionados.
        return serveisSeleccionats;
    }

    /**
     * Calcula i retorna el cost total de la reserva, incloent l'habitació,
     * els serveis seleccionats i l'IVA.
     */
    public static float calcularPreuTotal(String tipusHabitacio, ArrayList<String> serveisSeleccionats) {
        // Obtenemos el precio base de la habitación según el tipo seleccionado.
        float preuBase = preusHabitacions.get(tipusHabitacio);
        // Inicializamos el precio de los servicios a 0.
        float preuServeis = 0.0f;

        // Comprobamos si serveisSeleccionats no es nulo o vacío
        if (serveisSeleccionats != null && !serveisSeleccionats.isEmpty()) {
            // Recorremos los servicios seleccionados para sumar su precio.
            for (String servei : serveisSeleccionats) {
                preuServeis += preusServeis.get(servei);
            }
        }
        // Devuelve la suma del precio base y el precio de los servicios con IVA
        // incluido
        float subtotal = preuBase + preuServeis;
        return subtotal + (subtotal * IVA);
    }

    /**
     * Genera i retorna un codi de reserva únic de tres xifres
     * (entre 100 i 999) que no estiga repetit.
     */
    public static int generarCodiReserva() {
        // Generamos el codigo de reserva aleatorio para que sea unico
        int codiReserva;
        // Bucle para asegurar que el código generado no esté repetido
        do {
            // Genera un número aleatori entre 100 i 999
            codiReserva = 100 + random.nextInt(900); // 100 + [0-899] = 100-999
            // Repetimos hasta que el código no esté en el HashMap de reserves
        } while (reserves.containsKey(codiReserva));

        // Formateamos el codigo para que tenga 3 digitos.
        // String strCodiReserva = String.format("%03d", codiReserva);
        // Imprimimos el codigo por pantalla.
        // System.out.println("Codi de reserva: " + strCodiReserva);
        // Devolvemos el codigo de reserva unico
        return codiReserva;
    }

    /**
     * Permet alliberar una habitació utilitzant el codi de reserva
     * i actualitza la disponibilitat.
     */
    public static void alliberarHabitacio() {
        System.out.println("\n===== ALLIBERAR HABITACIÓ =====");
        // Solicitamos al usuario que introduzca el codigo de reserva
        System.out.print("Introdueix el codi de reserva: ");
        int codiReserva = sc.nextInt();
        sc.nextLine(); // Consumimos el salto de línea pendiente
        // Comprobamos si existe la reserva con el codigo dado
        if (reserves.containsKey(codiReserva)) {
            // Obtenemos los datos de la reserva
            ArrayList<String> reserva = reserves.get(codiReserva);
            // Obtenemos el tipo de habitacion de la reserva
            String infoTipus = reserva.get(0); // El primer dato es el tipo de habitacion, el indice 0
            // Definimos el tipo de habitacion extrayendolo del String
            String tipusHabitacio = infoTipus.replace("Tipus habitació: ", "");
            // Actualizamos la disponibilidad de habitaciones
            int disponibilitatActual = disponibilitatHabitacions.get(tipusHabitacio);
            // Aumentamos en 1 la disponibilidad
            disponibilitatHabitacions.put(tipusHabitacio, disponibilitatActual + 1);
            // Eliminamos la reserva del HashMap
            reserves.remove(codiReserva);
            // Mostramos mensaje de salida en consola con exito
            System.out.println("Habitació alliberada amb èxit.");
            System.out.println("Disponibilitat actualitzada.");
        } else {
            System.out.println("Codi no trobat");
        }

    }

    /**
     * Mostra la disponibilitat actual de les habitacions (lliures i ocupades).
     */
    public static void consultarDisponibilitat() {
        // Linea vacia para separar la salida visualmente
        System.out.println("");
        System.out.println("Disponibilitat d'habitacions:");
        System.out.println("Tipus\tLliures\tOcupades");
        mostrarDisponibilitatTipus(TIPUS_ESTANDARD);
        mostrarDisponibilitatTipus(TIPUS_SUITE);
        mostrarDisponibilitatTipus(TIPUS_DELUXE);
    }

    /**
     * Funció recursiva. Mostra les dades de totes les reserves
     * associades a un tipus d'habitació.
     */
    public static void llistarReservesPerTipus(int[] codis, String tipus) {
        // Verificamos que el array de códigos no esté vacío
        if (codis.length == 0) {
            return; // Si el array está vacío, salimos de la función
        }
        // Verificamos la primera reserva (en el índice 0)
        int codiActual = codis[0];
        // Obtenemos la reserva asociada al código actual
        ArrayList<String> reserva = reserves.get(codiActual);

        // Si el tipo de la reserva coincide con el tipo solicitado, mostramos los datos
        if (reserva != null && reserva.get(0).contains(tipus)) {
            mostrarDadesReserva(codiActual);
        }
        // Creamos un nuevo array excluyendo el primer elemento
        int[] nuevosCodis = new int[codis.length - 1];
        System.arraycopy(codis, 1, nuevosCodis, 0, nuevosCodis.length);

        // Llamamos recursivamente a llistarReservesPerTipus con el nuevo array de
        // códigos
        llistarReservesPerTipus(nuevosCodis, tipus);
    }

    /**
     * Permet consultar els detalls d'una reserva introduint el codi.
     */
    public static void obtindreReserva() {
        System.out.println("\n===== CONSULTAR RESERVA =====");
        System.out.print("Introdueix el codi de reserva: ");
        int codi = sc.nextInt();
        sc.nextLine();
        // Verificamos si existe el código en el HashMap
        if (reserves.containsKey(codi)) {
            // Si existe, mostramos la reserva
            mostrarDadesReserva(codi);
        } else {
            System.out.println("No s'ha trobat cap reserva amb aquest codi.");
        }

    }

    /**
     * Mostra totes les reserves existents per a un tipus d'habitació
     * específic.
     */
    public static void obtindreReservaPerTipus() {
        System.out.println("\n===== CONSULTAR RESERVES PER TIPUS =====");

        // Solicitamos al usuario que introduzca el tipo de habitacion
        System.out.print("Seleccione tipus:");
        System.out.println("    Estàndard");
        System.out.println("    Suite");
        System.out.println("    Deluxe\n");

        System.out.print("Opció: ");
        String opcio = sc.nextLine();

        String tipus = null;
        switch (opcio) {
            case "1":
                tipus = TIPUS_ESTANDARD;
                break;
            case "2":
                tipus = TIPUS_SUITE;
                break;
            case "3":
                tipus = TIPUS_DELUXE;
                break;
            default:
                System.out.println("Opció no vàlida");
                return;
        }
        System.out.println("\nReserves del tipus \"" + tipus + "\":\n");

        boolean hiHaReserves = false;

        for (int codi : reserves.keySet()) {

            ArrayList<String> reserva = reserves.get(codi);
            String tipusReserva = reserva.get(0);
            if (tipusReserva.equals(tipus)) {
                System.out.println("Codi: " + codi);
                System.out.println("    Tipus d'habitació: " + reserva.get(0));
                System.out.println("    Cost total: " + reserva.get(4) + "€");
                System.out.println("    Serveis: " + reserva.get(1));

                hiHaReserves = true;
            }
        }

        if (!hiHaReserves) {
            System.out.println("(No hi ha més reserves d’aquest tipus.)");
        }

    }

    /**
     * Consulta i mostra en detall la informació d'una reserva.
     */
    public static void mostrarDadesReserva(int codi) {
        if (reserves.containsKey(codi)) {
            ArrayList<String> reserva = reserves.get(codi);
            System.out.println("\n de la reserva:");

            if (reserva.size() > 0)
                System.out.println("-> " + reserva.get(0));
            if (reserva.size() > 1)
                System.out.println("-> " + reserva.get(1));
            if (reserva.size() > 4)
                System.out.println("-> TOTAL: " + reserva.get(4) + " euros");

        } else {
            System.out.println("Codi no trobat");
        }
    }

    // --------- MÈTODES AUXILIARS (PER MILLORAR LEGIBILITAT) ---------

    /**
     * Llig un enter per teclat mostrant un missatge i gestiona possibles
     * errors d'entrada.
     */
    static int llegirEnter(String missatge) {
        int valor = 0;
        boolean correcte = false;
        while (!correcte) {
            System.out.print(missatge);
            valor = sc.nextInt();
            correcte = true;
        }
        return valor;
    }

    /**
     * Mostra per pantalla informació d'un tipus d'habitació: preu i
     * habitacions disponibles.
     */
    static void mostrarInfoTipus(String tipus) {
        int disponibles = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        float preu = preusHabitacions.get(tipus);
        System.out.println("- " + tipus + " (" + disponibles + " disponibles de " + capacitat + ") - " + preu + "€");
    }

    /**
     * Mostra la disponibilitat (lliures i ocupades) d'un tipus d'habitació.
     */
    static void mostrarDisponibilitatTipus(String tipus) {
        int lliures = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        int ocupades = capacitat - lliures;

        String etiqueta = tipus;
        if (etiqueta.length() < 8) {
            etiqueta = etiqueta + "\t"; // per a quadrar la taula
        }

        System.out.println(etiqueta + "\t" + lliures + "\t" + ocupades);
    }
}
