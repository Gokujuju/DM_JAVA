package com.julien.dmJava;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class calculCurrency {

    public static void executeConversion(int first, int second, double convertValue) throws Exception {
        Connection bdd = BDD.getConnectionFromDataSource();
        String nameFirst = BDD.getMoneyName(first);
        String nameSecond = BDD.getMoneyName(second);

        double resultat = 0;

        if(second == 0){
            resultat = convertInEuro(bdd,first,convertValue);
        }else if(first == 0){
            resultat = convertFromEuro(bdd,second,convertValue);
        }else{
            resultat = convertOtherMoney(bdd,first,second,convertValue);
        }

        resultat = Math.round(resultat*100.0)/100.0;
        System.out.println(convertValue+nameFirst+" est égale à "+resultat+nameSecond);
    }



    private static double convertInEuro(Connection bdd, int first, double convertValue) throws SQLException {
        double f = 0;
        String requeteFirst = "SELECT value FROM money WHERE idMoney="+(first-1);
        ResultSet resultatFirst = BDD.executerRequete(bdd, requeteFirst);
        while (resultatFirst.next())
        {
            f = resultatFirst.getDouble("value");
        }
        return convertValue*(1/f);
    }

    private static double convertFromEuro(Connection bdd, int second, double convertValue) throws SQLException {
        double s = 0;
        String requeteSecond = "SELECT value FROM money WHERE idMoney="+(second-1);
        ResultSet resultatSecond = BDD.executerRequete(bdd, requeteSecond);
        while (resultatSecond.next())
        {
            s = resultatSecond.getDouble("value");
        }
        return s*convertValue;
    }

    private static double convertOtherMoney(Connection bdd, int first, int second, double convertValue) throws SQLException {
        double f = 0;
        double s = 0;

        String requeteFirst = "SELECT value FROM money WHERE idMoney="+(first-1);
        String requeteSecond = "SELECT value FROM money WHERE idMoney="+(second-1);

        ResultSet resultatFirst = BDD.executerRequete(bdd, requeteFirst);
        ResultSet resultatSecond = BDD.executerRequete(bdd, requeteSecond);

        while (resultatFirst.next())
        {
            f = resultatFirst.getDouble("value");
        }

        while (resultatSecond.next())
        {
            s = resultatSecond.getDouble("value");
        }

        return (1/f)*convertValue*s;
    }
}
