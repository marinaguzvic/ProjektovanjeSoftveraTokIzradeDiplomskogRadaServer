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
        try (FileInputStream fis = new FileInputStream(Constants.PATH_TO_CONFIG_FILE)) {
            properties = new Properties();
            properties.load(fis);
        }catch(Exception ex){
            throw new Exception("Error reading connection parameters!\n" + ex.getMessage());
        }

    }

    public DatabaseResources(Properties properties) {
        this.properties = properties;
    }
    
    public String getUrl(){
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:").append(properties.getProperty(Constants.PROVIDER)).append("://").append(properties.getProperty(Constants.ADDRESS)).append(":").append(properties.getProperty(properties.getProperty(Constants.PROVIDER) + "_" + Constants.PORT)).append("/").append(properties.getProperty(Constants.DATABASE));
        System.out.println(sb.toString());
        return sb.toString();
    }
    
    public String getUsername(){
        return properties.getProperty(properties.getProperty(Constants.PROVIDER) + "_" + Constants.USERNAME);
    }
    
    public String getPassword(){
        return properties.getProperty(properties.getProperty(Constants.PROVIDER) + "_" + Constants.PASSWORD);
    }



}
