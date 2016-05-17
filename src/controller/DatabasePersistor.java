package controller;

import controller.interfaces.IPersistor;
import model.DataModel;
import model.Inductee;


import java.sql.*;
import java.util.ArrayList;

public class DatabasePersistor implements IPersistor {

    private Connection dbConnection;


    public DatabasePersistor() {

//        //Start the driver for MYSQL
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            dbConnection = DriverManager.getConnection(
//                    "jdbc:mysql://localhost:3306/oop2?" + "user=root&password=");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


    }


    public void writeInductee(ArrayList<Inductee> inductees) {
//        for (Inductee currInductee: inductees) {
//            PreparedStatement prepStmt = null;
//            try {
//                prepStmt = dbConnection.prepareStatement("INSERT into oop2.players VALUES (?,?,?)");
//                prepStmt.setString(1, currInductee.getName());
//                prepStmt.setInt(2, currInductee.getAge());
//                prepStmt.setInt(3, currInductee.getNoOfGoals());
//
//                prepStmt.executeUpdate();
//                prepStmt.close();
//
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }


        }
        // TODO Auto-generated method stub



    public void writeInductee(Inductee i) {

    }

    @Override
    public DataModel read() {

        //Create an empty list
        //ArrayList<Inductee> inducteesList = new ArrayList<Inductee>();
        DataModel dataModel = new DataModel();
        // Test code
//        Inductee i = new Inductee("Feidhlim", "Jamie", "Dorkheads Inc", "Dogsbody", "being a dork", System.currentTimeMillis());
//        inducteesList.add(i);
//        try {
//            Statement getAllPlayersStatement = dbConnection.createStatement();
//
//            ResultSet rs = getAllPlayersStatement.executeQuery("SELECT * from oop2.players");
//
//            while (rs.next()) {
//                // Give me the data in column "name" at the row at which the ResultSet is
//                // Currently pointing at
//                String currentName = rs.getString("name");
//                int currAge = rs.getInt("age");
//                int goalsScored = rs.getInt("goalsScored");
//                // Re-create a Player object and initialise it with the raw data we have just extracted
//                // from the row in the database.
//                Inductee p = new Inductee(currentName, currAge, 0, goalsScored);
//                playersList.add(p);
//                //Close the connection to the database
//
//            }
//            getAllPlayersStatement.close();
//            rs.close();
//
//        } catch (SQLException sqlEx) {
//            System.out.println(sqlEx.getMessage());
//        }
        return dataModel;

    }

    @Override
    public void write(DataModel dm) {

    }

}