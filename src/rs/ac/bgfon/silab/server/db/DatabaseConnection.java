/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bgfon.silab.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author MARINA
 */
public class DatabaseConnection {
    private final Connection connection;
    private static DatabaseConnection instance;

    private DatabaseConnection() throws Exception {
        
        try {
            
            DatabaseResources dbr = new DatabaseResources();
            connection = DriverManager.getConnection(dbr.getUrl(), dbr.getUsername(), dbr.getPassword());
            connection.setAutoCommit(false);
        } catch (Exception ex) {
            throw new Exception("Error occured making connection!\n" + ex.getMessage());
        }
        
    }

    public Connection getConnection() {
        return connection;
    }
    
    public static DatabaseConnection getInstance() throws Exception{
        if(instance == null){
            instance = new DatabaseConnection();
        }
        return instance;
    }


    
    
}
