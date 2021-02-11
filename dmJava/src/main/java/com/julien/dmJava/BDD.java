package com.julien.dmJava;

import org.hsqldb.jdbc.JDBCDataSource;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BDD {
    /** mode mémoire. */
    private static final String database = "jdbc:hsqldb:mem:database";

    /** utilisateur qui se connecte à la base de données. */
    private static final String userDB = "Utilisateur1";

    /** mot de passe pour se connecter à la base de données. */
    private static final String passwordDB = "mdpUser";

    public static void executerUpdate(Connection connexion, String requete) throws SQLException
    {
        // executerUpdate permet d’effectuer une requête SQL de type UPDATE sur la base
        Statement statement;
        statement = connexion.createStatement();
        statement.executeUpdate(requete);
    }

    private static void initTable(Connection connexion) throws SQLException
    {
        // On créer une table users
        // On commence par créer la requête SQL
        String baseTable = "CREATE TABLE base ( id INTEGER IDENTITY PRIMARY KEY NOT NULL, base VARCHAR(256) NOT NULL, date DATE NOT NULL)";
        String createExchangeTable = "CREATE TABLE money ( idMoney INTEGER IDENTITY PRIMARY KEY NOT NULL, moneyName VARCHAR(256) NOT NULL, value DOUBLE NOT NULL)";
        executerUpdate(connexion, baseTable);
        executerUpdate(connexion,createExchangeTable);

        String includeBase = "INSERT INTO base (base, date) VALUES ('" + API_Exchange.getAPI().getBase() + "', '" + API_Exchange.getAPI().getDate() + "')";
        executerUpdate(connexion, includeBase);

        HashMap<String,Double> rates = API_Exchange.getAPI().getRates();
        for (Map.Entry<String, Double> entry : rates.entrySet()) {
            String includeRates = "INSERT INTO money (moneyName, value) VALUES ('" + entry.getKey() + "', '" + entry.getValue() + "')";
            try {
                executerUpdate(connexion, includeRates);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static ResultSet executerRequete(Connection connexion,String requete) throws SQLException
    {
        // executerRequete execute n’importe quelle requête, mais renvoie le résultat, ce qui sert par exemple
        // dans le cas d’une requête de type SELECT
        Statement statement;
        statement = connexion.createStatement();
        ResultSet resultat = statement.executeQuery(requete);
        return resultat;
    }

    public static void main(String[] args) throws Exception
    {
        Connection connexion = null;
        try {
            // Puis on se connecte à la base de données en mode mémoire
            connexion = getConnectionFromDataSource();
            // Old School
            //connexion = getConnectionFromDriverManager();
            initTable(connexion);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        showTables(connexion);
    }

    private static void showTables(Connection connexion) throws SQLException {

        //Show Base Table
        String requestbase = "SELECT * FROM base";
        ResultSet resultatBase = executerRequete(connexion,requestbase);
        System.out.println("============================");
        System.out.println("id\t\tbase\t\tdate\t|");
        while (resultatBase.next())
        {
            String id = resultatBase.getString("id");
            String base = resultatBase.getString("base");
            String date = resultatBase.getString("date");
            System.out.println( id + "\t\t" + base + "\t\t" + date +"\t|");
        }
        System.out.println("============================");

        //Show Rates Table
        String requestRates = "SELECT * FROM money";
        ResultSet resultatRates = executerRequete(connexion,requestRates);
        System.out.println("=====================================");
        System.out.println("idMoney\t\tnameMoney\t\tvalue\t");
        while (resultatRates.next())
        {
            String idMoney = resultatRates.getString("idMoney");
            String moneyName = resultatRates.getString("moneyName");
            String value = resultatRates.getString("value");
            System.out.println( idMoney + "\t\t\t" + moneyName + "\t\t\t\t" + value +"\t");
        }
        System.out.println("=====================================");
    }

    public static Connection getConnectionFromDriverManager() throws Exception
    {
        Class.forName("org.hsqldb.jdbcDriver");
        return DriverManager.getConnection(database, userDB, passwordDB);
    }

    public static Connection getConnectionFromDataSource() throws Exception
    {
        JDBCDataSource dataSource = new JDBCDataSource();
        dataSource.setURL(database);
        return dataSource.getConnection(userDB,passwordDB);
    }

    public static String getMoneyName(int id) throws Exception {
        if(id == 0){
            return "EUR";
        }
        String name = "";
        Connection bdd = BDD.getConnectionFromDataSource();

        String requestMoneyName = "SELECT moneyName FROM money WHERE idMoney="+(id-1);
        ResultSet resultatMoneyName = executerRequete(bdd,requestMoneyName);

        while (resultatMoneyName.next())
        {
            name = resultatMoneyName.getString("moneyName");
        }

        return name;
    }
}
