package me.spider.db;

import java.sql.*;
import java.util.HashMap;

public class JDBCManager {
    //server | user | personal | personal max | peripheral | peripheral max | other | other max | limit | willpower

    private Statement statement;
    private Connection connection;
    public JDBCManager(){
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:exalted.db");
            Statement statement = connection.createStatement();
            this.connection = connection;
            this.statement = statement;
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS characters" +
                    " (serverID string, userID string, personalMotes integer default 0, personalMax integer default 0, " +
                    "peripheralMotes integer default 0, peripheralMax integer default 0, otherMotes integer default 0, otherMax integer default 0, limitbreak integer default 0, willpower integer default 5, PRIMARY KEY (serverID, userID))"
            );
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Statement getJDBCStatement() {
        return statement;
    }

    public void setEssence(String serverID, String userID, String name, int value) throws SQLException {
        setInt(serverID, userID, name, value);
    }



    public int getLimit(String serverID, String userID) throws SQLException {
        return getInt(serverID, userID, "limitbreak");
    }

    public int getWillpower(String serverID, String userID) throws SQLException {
        return getInt(serverID, userID, "willpower");
    }

    public HashMap<String, Integer> getAllEssences(String serverID, String userID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT personalMotes, personalMax, " +
                "peripheralMotes, peripheralMax, otherMotes, otherMax from characters " +
                "where serverID = ? AND userID = ?");
        preparedStatement.setString(1, serverID);
        preparedStatement.setString(2, userID);
        ResultSet resultSet = preparedStatement.executeQuery();
        HashMap<String, Integer> motes = new HashMap<>();
        if(resultSet.next()){
            motes.put("personalMotes", resultSet.getInt("personalMotes"));
            motes.put("personalMax", resultSet.getInt("personalMax"));
            motes.put("peripheralMotes", resultSet.getInt("peripheralMotes"));
            motes.put("peripheralMax", resultSet.getInt("peripheralMax"));
            motes.put("otherMotes", resultSet.getInt("otherMotes"));
            motes.put("otherMax", resultSet.getInt("otherMax"));
        }
        return motes;
    }
    public HashMap<String, Integer> getEssenseMoteAndMax(String serverID, String userID, String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT ? , ? FROM characters WHERE serverID = ? AND userID = ?");
        preparedStatement.setString(1, name + "Motes");
        preparedStatement.setString(2, name + "Max");
        preparedStatement.setString(3, serverID);
        preparedStatement.setString(4, userID);
        ResultSet resultSet = preparedStatement.executeQuery();
        HashMap<String, Integer> motes = new HashMap<>();
        if(resultSet.next()){
            motes.put(name + "Motes", resultSet.getInt(name + "Motes"));
            motes.put(name + "Max", resultSet.getInt(name + "Max"));
        }
        return motes;
    }

    public int getEssenceValue(String serverID, String userID, String name) throws SQLException {
        return getInt(serverID, userID, name);
    }
    public void setLimit(String serverID, String userID, int limitbreak) throws SQLException {
        setInt(serverID, userID, "limitbreak", limitbreak);

    }
    public void setWillpower(String serverID, String userID, int willpower) throws SQLException {
        setInt(serverID, userID, "willpower", willpower);
    }

    public void setInt(String serverID, String userID, String name, int value) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO characters (serverID,userID, " + name + ") VALUES (?,?,?) ON CONFLICT(serverID,userID) DO UPDATE SET " + name + " = ?");
        preparedStatement.setString(1, serverID);
        preparedStatement.setString(2, userID);
        preparedStatement.setInt(3, value);
        preparedStatement.setInt(4, value);
        preparedStatement.executeUpdate();
    }

    public int getInt(String serverID, String userID, String name) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT " + name + " FROM characters WHERE serverID = ? AND userID = ?");
        preparedStatement.setString(1, serverID);
        preparedStatement.setString(2, userID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
        return resultSet.getInt(name);
        }
        return -1;
    }





}
