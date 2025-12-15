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
        System.out.println("1. Reservar una habitació");
        System.out.println("2. Alliberar una habitació");
        System.out.println("3. Consultar disponibilitat");
        System.out.println("4. Llistar reserves per tipus");
        System.out.println("5. Obtindre una reserva");
        System.out.println("6. Ixir");
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
                obtindreReserva();
                break;
            case 5:
                obtindreReservaPerTipus();
                break;
            case 6:
                System.out.println("Eixint del sistema de reserves..");
                break;
            default:
                System.out.println("Opció no valida. Introdueix una opció valida ");
                break;
        }
    }

    /**
     * Gestiona tot el procés de reserva: selecció del tipus d'habitació,
     * serveis addicionals, càlcul del preu total i generació del codi de reserva.
     */
    public static void reservarHabitacio() {
        System.out.println("\n===== RESERVAR HABITACIÓ =====");
        // Seleccionamos el tipo de habitación disponible.
        // String tipusHabitacio = seleccionarTipusHabitacioDisponible();
        // Seleccionamos los servicios adicionales.
        // ArrayList<String> serveisSeleccionats = seleccionarServeis();

    }

    /**
     * Pregunta a l'usuari un tipus d'habitació en format numèric i
     * retorna el nom del tipus.
     */
    public static String seleccionarTipusHabitacio() {
        String tipusHabitacio = null;

        while (tipusHabitacio == null) {

            System.out.print("Seleccione el tipus d'habitació; ");
            String entrada = scanner.nextLine();

            if (entrada.equals("1") || entrada.equals("2") || entrada.equals("3")) {
                int opcio = Integer.parseInt(entrada);
                switch (opcio) {
                    case 1:
                        tipusHabitacio = TIPUS_ESTANDARD;
                        break;
                    case 2:
                        tipusHabitacio = TIPUS_SUITE;
                        break;
                    case 3:
                        tipusHabitacio = TIPUS_DELUXE;
                        break;
                    default:
                        System.out.println("Opció no vàlida. Si us plau, seleccioneu una opció vàlida.");
                        break;
                }
                // Comprobar la disponibilidad
                int disponibles = disponibilitatHabitacions.getOrDefault(tipusHabitacio, 0);
                if (disponibles <= 0) {
                    System.out.println(
                            "No hi ha habitacions disponibles per al tipus seleccionat (" + tipusHabitacio + ").");
                    tipusHabitacio = null; // Reinicia el tipo seleccionado si no hay disponibilidad
                }

            } else {
                // Si la entrada no es una opción válida (ni 1, 2 o 3)
                System.out.println(" =====> ERROR. Opció no vàlida.");
            }
        }
        return tipusHabitacio;
    }

    /**
     * Mostra la disponibilitat i el preu de cada tipus d'habitació,
     * demana a l'usuari un tipus i només el retorna si encara hi ha
     * habitacions disponibles. En cas contrari, retorna null.
     */
    public static String seleccionarTipusHabitacioDisponible() {
        System.out.println("\nTipus d'habitació disponibles:");
        System.out.println("1. Estàndard - " + disponibilitatHabitacions.get(TIPUS_ESTANDARD)
                + " habitacions disponibles, " + preusHabitacions.get(TIPUS_ESTANDARD) + "€");
        System.out.println("2. Suite - " + disponibilitatHabitacions.get(TIPUS_SUITE) + " habitacions disponibles, "
                + preusHabitacions.get(TIPUS_SUITE) + "€");
        System.out.println("3. Deluxe - " + disponibilitatHabitacions.get(TIPUS_DELUXE) + " habitacions disponibles, "
                + preusHabitacions.get(TIPUS_DELUXE) + "€");
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
        // Creamos una arrayList para almacenar los servicios seleccionados.
        ArrayList<String> serveisSeleccionats = new ArrayList<>();

        // Definimos los servicios disponibles.
        System.out.println("Serveis addicionals (0-4):");
        System.out.println("0. Finalitzar.");
        System.out.println("1. Esmorzar (" + preusServeis.get(SERVEI_ESMORZAR) + "€)");
        System.out.println("2. Gimnàs (" + preusServeis.get(SERVEI_GIMNAS) + "€");
        System.out.println("3. Spa (" + preusServeis.get(SERVEI_SPA) + "€)");
        System.out.println("4. Piscina (" + preusServeis.get(SERVEI_PISCINA) + "€)");

        // Leemos la opción seleccionada por el usuario.
        int opcio = -1;
        while (opcio != 0) {
            // Solicitamos al usuario que seleccione un servicio.
            System.out.print("Vols afegir un servei?(s/n)");
            System.out.print("Seleccione servei:");
            // Leemos la opcion del servicio.
            opcio = scanner.nextInt();

            String servei = "";

            switch (opcio) {
                case 1:
                    servei = SERVEI_ESMORZAR;
                    break;
                case 2:
                    servei = SERVEI_GIMNAS;
                    break;
                case 3:
                    servei = SERVEI_SPA;
                    break;
                case 4:
                    servei = SERVEI_PISCINA;
                    break;
                case 0:
                    System.out.println("Finalitzant selecció de serveis.");
                    break;
                default:
                    System.out.println("Opció no vàlida. Si us plau, seleccioneu una opció vàlida.");
                    continue;
            }
            if (!servei.isEmpty()) {
                // Comprobar si el servicio ya ha sido seleccionado
                if (serveisSeleccionats.contains(servei)) {
                    System.out.println("Ja has afegit " + servei + ". ");
                } else {
                    // Añadir el servicio a la lista de seleccionados
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
        // TODO:
        return 0;
    }

    /**
     * Genera i retorna un codi de reserva únic de tres xifres
     * (entre 100 i 999) que no estiga repetit.
     */
    public static int generarCodiReserva() {
        // Generamos el codigo de reserva aleatorio.
        int codiReserva = (int) (Math.random() * 1000);
        // Formateamos el codigo para que tenga 3 digitos.
        String strCodiReserva = String.format("%03d", codiReserva);
        // Imprimimos el codigo por pantalla.
        System.out.println("Codi de reserva: " + strCodiReserva);

        return 0;
    }

    /**
     * Permet alliberar una habitació utilitzant el codi de reserva
     * i actualitza la disponibilitat.
     */
    public static void alliberarHabitacio() {
        System.out.println("\n===== ALLIBERAR HABITACIÓ =====");
        // TODO: Demanar codi, tornar habitació i eliminar reserva
    }

    /**
     * Mostra la disponibilitat actual de les habitacions (lliures i ocupades).
     */
    public static void consultarDisponibilitat() {
        // TODO: Mostrar lliures i ocupades
    }

    /**
     * Funció recursiva. Mostra les dades de totes les reserves
     * associades a un tipus d'habitació.
     */
    public static void llistarReservesPerTipus(int[] codis, String tipus) {
        // TODO: Implementar recursivitat
    }

    /**
     * Permet consultar els detalls d'una reserva introduint el codi.
     */
    public static void obtindreReserva() {
        System.out.println("\n===== CONSULTAR RESERVA =====");
        // TODO: Mostrar dades d'una reserva concreta

    }

    /**
     * Mostra totes les reserves existents per a un tipus d'habitació
     * específic.
     */
    public static void obtindreReservaPerTipus() {
        System.out.println("\n===== CONSULTAR RESERVES PER TIPUS =====");
        // TODO: Llistar reserves per tipus
    }

    /**
     * Consulta i mostra en detall la informació d'una reserva.
     */
    public static void mostrarDadesReserva(int codi) {
        // TODO: Imprimir tota la informació d'una reserva
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
