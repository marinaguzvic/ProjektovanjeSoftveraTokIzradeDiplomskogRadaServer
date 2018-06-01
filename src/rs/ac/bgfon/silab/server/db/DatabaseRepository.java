/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bgfon.silab.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.silab.jpa.example1.domain.CompundDObject;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCClanKomisije;
import rs.ac.bg.fon.silab.jpa.example1.domain.GeneralDObject;

/**
 *
 * @author MARINA
 */
public class DatabaseRepository {

    private static DatabaseRepository instance;

    public GeneralDObject updateRecordCompound(GeneralDObject gdo) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(gdo.getClassName()).append(" SET ").append(gdo.setAtrValue()).append(" WHERE " ).append(gdo.getWhereCondition());
        String query = sb.toString();
        System.out.println(query);
        try (Statement statement = connection.createStatement()) {
            if (statement.executeUpdate(query) > 0) {
                try {
                    CompundDObject cdo = (CompundDObject) gdo;
                    for (String className : cdo.classNames()) {
                        List<GeneralDObject> items = cdo.getItemsFor(className);
                        for (GeneralDObject item : items) {
                            updateRecordCompound(item);
                        }
                    }
                } catch (ClassCastException cce) {
                }
                return gdo;
            } else {
                throw new Exception("No rows have been affected, update failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    public ResultSet findRecordsByWhereCondition(GeneralDObject gdo, String where) throws Exception {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(gdo.getClassName()).append(" WHERE ").append(where);
            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (Exception ex) {
            Logger.getLogger(DatabaseRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error reading list from database!");
        }
    }

    public ResultSet findAllRecords(GeneralDObject gdo) throws Exception {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(gdo.getClassName());
            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (Exception ex) {
            Logger.getLogger(DatabaseRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error reading list from database!");
        }
    }

    public void commitTransaction() throws Exception {
        try {
            DatabaseConnection.getInstance().getConnection().commit();
        } catch (Exception ex) {
            Logger.getLogger(DatabaseRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error in commiting transaction!");
        }
    }

    public void rollbackTransaction() throws Exception {
        try {
            DatabaseConnection.getInstance().getConnection().rollback();
        } catch (Exception ex) {
            Logger.getLogger(DatabaseRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Error in rolling back transaction!");
        }
    }

    private DatabaseRepository() {

    }

    public static DatabaseRepository getInstance() {
        if (instance == null) {
            instance = new DatabaseRepository();
        }
        return instance;
    }

    public ResultSet findRecordByPrimaryKey(GeneralDObject gdo) throws Exception {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ").append(gdo.getClassName()).append(" WHERE ").append(gdo.getWhereCondition());
            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            return rs;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("DatabaseRepository::findRecordByPrimaryKey::" + ex.getMessage());
        }

    }

    public static GeneralDObject insertRecord(GeneralDObject gdo) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        try (Statement statement = connection.createStatement()) {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(gdo.getClassName()).append("(").append(gdo.getColumnNames()).append(")").append(" VALUES (").append(gdo.getAtrValue()).append(")");
            String query = sb.toString();
            System.out.println(query);
            if (statement.executeUpdate(query) > 0) {
                return gdo;
            } else {
                throw new Exception("No rows have been affected, insert failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }

    public GeneralDObject updateRecord(GeneralDObject gdo) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        try (Statement statement = connection.createStatement()) {
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ").append(gdo.getClassName()).append(" SET ").append(gdo.getAtrValue()).append(" WHERE ").append(gdo.getWhereCondition());
            String query = sb.toString();
            if (statement.executeUpdate(query) > 0) {
                return gdo;
            } else {
                throw new Exception("No rows have been affected, update failed");
            }
        } catch (Exception ex) {
            Logger.getLogger(DatabaseRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
    }

    public static GeneralDObject deleteRecord(GeneralDObject gdo) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        try (Statement statement = connection.createStatement()) {
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ").append(gdo.getClassName()).append(" WHERE ").append(gdo.getWhereCondition());
            String query = sb.toString();
            if (statement.executeUpdate(query) > 0) {

                return gdo;
            } else {
                throw new Exception("No rows have been affected, delete failed");
            }
        } catch (Exception ex) {
            Logger.getLogger(DatabaseRepository.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
    }

    public static GeneralDObject insertRecordCompound(GeneralDObject gdo) throws Exception {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        System.out.println(gdo);
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(gdo.getClassName()).append("(").append(gdo.getColumnNames()).append(")").append(" VALUES (").append(gdo.getAtrValue()).append(")");
        String query = sb.toString();
        System.out.println(query);
        try (Statement statement = connection.createStatement()) {
            if (statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS) > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                gdo.setKey(rs);
                try {
                    CompundDObject cdo = (CompundDObject) gdo;
                    for (String className : cdo.classNames()) {
                        List<GeneralDObject> items = cdo.getItemsFor(className);
                        for (GeneralDObject item : items) {
                            insertRecordCompound(item);
                        }
                    }
                } catch (ClassCastException cce) {
                }
                return gdo;
            } else {
                throw new Exception("No rows have been affected, insert failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }
    }
}
