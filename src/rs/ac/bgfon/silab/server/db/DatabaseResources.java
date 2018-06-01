/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bgfon.silab.server.db;

import java.io.FileInputStream;
import java.util.Properties;
import rs.ac.bg.fon.silab.constants.Constants;

/**
 *
 * @author MARINA
 */
public class DatabaseResources {

    Properties properties;

    public DatabaseResources() throws Exception {
        try (FileInputStream fis = new FileInputStream("./resources/database.config")) {
            properties = new Properties();
            properties.load(fis);
        }catch(Exception ex){
            throw new Exception("Error reading connection parameters!\n" + ex.getMessage());
        }

    }
    
    public String getUrl(){
        return properties.getProperty(Constants.URL);
    }
    
    public String getUsername(){
        return properties.getProperty(Constants.USERNAME);
    }
    
    public String getPassword(){
        return properties.getProperty(Constants.PASSWORD);
    }

}
