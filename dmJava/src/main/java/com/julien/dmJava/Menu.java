package com.julien.dmJava;

import javax.sound.midi.SysexMessage;
import java.util.Scanner;

public class Menu {

    public static void menuChoice() throws Exception {
        System.out.println("\t=================================\n" +
                "\t|\tConvertisseur de monnaies\t|\n" +
                "\t|\t\tMenu Principal\t\t\t|\n" +
                "\t=================================\n");

        System.out.println("\t\t 1: Convertir une monnaie");
        System.out.println("\t\t 2: Fermer l'application");

        Scanner scan = new Scanner(System.in);
        int choice = scan.nextInt();
        switch (choice){
            case 1:
                convertMenu();
                break;
            case 2:
                System.exit(1);
                break;
            default:
                System.out.println("Choix incorrecte veuillez recommencer !");
                menuChoice();
        }
        System.out.println("Comment êtes vous arrivé ici ? Fin du programme !");
        System.exit(1);
    }

    private static void convertMenu() throws Exception {
        System.out.println("Entrez la somme à convertir (mettre une virgule si décimal):\n");
        Scanner scan = new Scanner(System.in);

        if(!scan.hasNextDouble()){
            System.out.println("Mauvaise valeur entrée ! On recommence !");
            convertMenu();
        }
        double value = scan.nextDouble();

        System.out.println("\t\tChoisissez sa monnaie :\n");
        allCurrencies.getAllCurrenciesFromTable();

        int firstCurrency = checkItems();
        if(firstCurrency == -1){
            System.out.println("Erreur, mauvaise valeur entrée !");
            convertMenu();
        }

        System.out.println("\t\tChoisissez en quelle monnaie elle sera convertie :\n");
        allCurrencies.getAllCurrenciesFromTable();
        int secondCurrency = checkItems();
        if(secondCurrency == -1){
            System.out.println("Erreur, mauvaise valeur entrée !");
            convertMenu();
        }

        calculCurrency.executeConversion(firstCurrency, secondCurrency, value);
        restartMenu();
    }

    private static int checkItems(){
        Scanner scan = new Scanner(System.in);
        int item = scan.nextInt();
        if(item >= 0 && item <= 32){
            return item;
        }else{
            return -1;
        }
    }

    private static void restartMenu() throws Exception {
        System.out.println("\nVoulez vous faire une autre conversion ? ");
        System.out.println("0: Non\t\t\t1: Oui");
        Scanner scan = new Scanner(System.in);
        if(!scan.hasNextInt()){
            System.out.println("Erreur ! Saisie incorrect ! Aurevoir !");
            System.exit(1);
        }
        int reponse = scan.nextInt();

        if(reponse == 1){
            menuChoice();
        }else if(reponse == 0){
            System.exit(1);
        }else{
            System.out.println("Erreur ! Saisie incorrect ! Aurevoir !");
            System.exit(1);
        }
    }
}
