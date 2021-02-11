package com.julien.dmJava;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;

import static com.julien.dmJava.BDD.executerRequete;
import static com.julien.dmJava.BDD.getConnectionFromDataSource;

public class allCurrencies {

    public static void getAllCurrenciesFromTable() throws Exception {
        String allCurrencies = "\t";
        Connection bdd = BDD.getConnectionFromDataSource();
        String requeteBase = "SELECT base FROM base";
        ResultSet resultatBase = BDD.executerRequete(bdd, requeteBase);

        while (resultatBase.next()) {
            allCurrencies = "0: " + resultatBase.getString("base") + "\n";
        }

        String requeteMonnaie = "SELECT idMoney, moneyName FROM money";
        ResultSet resultatMonnaie = BDD.executerRequete(bdd, requeteMonnaie);

        while (resultatMonnaie.next())
        {
            int idVal = resultatMonnaie.getInt("idMoney")+1;
            String id = String.valueOf(idVal);
            String name = resultatMonnaie.getString("moneyName");
            if(idVal%6 == 0){
                allCurrencies += id+": "+name+"\t\t\n";
            }else{
                allCurrencies += id+": "+name+"\t\t";
            }


        }
        System.out.println(allCurrencies);
    }
}
