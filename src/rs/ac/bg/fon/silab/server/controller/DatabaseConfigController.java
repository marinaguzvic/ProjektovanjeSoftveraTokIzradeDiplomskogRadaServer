/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.controller;

import rs.ac.bg.fion.silab.gui.controller.general.ConfigController;
import java.awt.Frame;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.silab.constants.Constants;
import rs.ac.bg.fon.silab.server.form.FDatabaseConfig;
import rs.ac.bg.fon.silab.server.listener.config.SaveConfigListener;
import rs.ac.bg.fon.silab.server.listener.config.TestConfigListener;
import rs.ac.bgfon.silab.server.db.DatabaseConnection;
import rs.ac.bgfon.silab.server.db.DatabaseResources;

/**
 *
 * @author MARINA
 */
public class DatabaseConfigController implements ConfigController {

    FDatabaseConfig fDatabaseConfig;

    public DatabaseConfigController(Frame parent) {
        fDatabaseConfig = new FDatabaseConfig(parent, true);
        readProperties();
        populateComboProvider();
        setListeners();
        fDatabaseConfig.setVisible(true);
    }

    @Override
    public void setListeners() {
        fDatabaseConfig.getjBtnSave().addActionListener(new SaveConfigListener(this));
        fDatabaseConfig.getjBtnTestConnectivity().addActionListener(new TestConfigListener(this));
    }

    @Override
    public Properties getProperties() {
        FileInputStream file = null;
        try {
            file = new FileInputStream(Constants.PATH_TO_CONFIG_FILE);
            Properties prop = new Properties();
            prop.load(file);
            file.close();
            prop.setProperty(Constants.PROVIDER, (String) fDatabaseConfig.getjComboBoxProvider().getSelectedItem());
            prop.setProperty(Constants.ADDRESS, fDatabaseConfig.getjTxtAddress().getText().trim());
            prop.setProperty((String) fDatabaseConfig.getjComboBoxProvider().getSelectedItem() + "_" + Constants.PORT, fDatabaseConfig.getjTxtPort().getText().trim());
            prop.setProperty(Constants.DATABASE, fDatabaseConfig.getjTxtDatabase().getText().trim());
            prop.setProperty((String) fDatabaseConfig.getjComboBoxProvider().getSelectedItem() + "_" + Constants.USERNAME, fDatabaseConfig.getjTxtUsername().getText().trim());
            prop.setProperty((String) fDatabaseConfig.getjComboBoxProvider().getSelectedItem() + "_" + Constants.PASSWORD, fDatabaseConfig.getjTxtPassword().getText().trim());
            return prop;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DatabaseConfigController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DatabaseConfigController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(DatabaseConfigController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public void createConfigFile() {
        try {

            OutputStream out = new FileOutputStream(Constants.PATH_TO_CONFIG_FILE);
            getProperties().store(out, null);
            out.close();
        } catch (FileNotFoundException ex) {
            fDatabaseConfig.setMessage("File not found");

            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(DatabaseConfigController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @Override
    public void readProperties() {
        try (FileInputStream file = new FileInputStream(Constants.PATH_TO_CONFIG_FILE)) {
            Properties prop = new Properties();
            prop.load(file);
            fDatabaseConfig.getjComboBoxProvider().setSelectedItem(prop.getProperty(Constants.PROVIDER));
            fDatabaseConfig.getjTxtAddress().setText(prop.getProperty(Constants.ADDRESS));
            fDatabaseConfig.getjTxtPort().setText(prop.getProperty(prop.getProperty(Constants.PROVIDER) + "_" + Constants.PORT));
            fDatabaseConfig.getjTxtDatabase().setText(prop.getProperty(Constants.DATABASE));
            fDatabaseConfig.getjTxtUsername().setText(prop.getProperty(prop.getProperty(Constants.PROVIDER) + "_" + Constants.USERNAME));
            fDatabaseConfig.getjTxtPassword().setText(prop.getProperty(prop.getProperty(Constants.PROVIDER) + "_" + Constants.PASSWORD));
        } catch (FileNotFoundException ex) {
            fDatabaseConfig.setMessage("File not found");

            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(DatabaseConfigController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private void populateComboProvider() {
        try (FileInputStream file = new FileInputStream(Constants.PATH_TO_CONFIG_FILE)) {
            Properties props = new Properties();
            props.load(file);
            int propsNo = Integer.parseInt(props.getProperty(Constants.PROVIDER + "_" + Constants.PROVIDER_NO));
            for (int i = 1; i <= propsNo; i++) {
                fDatabaseConfig.getjComboBoxProvider().addItem(props.getProperty(Constants.PROVIDER + i));
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void testConnection(){
        try {
            DatabaseResources dbr = new DatabaseResources(getProperties());
            Connection con = DriverManager.getConnection(dbr.getUrl(), dbr.getUsername(), dbr.getPassword());

            if (con != null) {
                fDatabaseConfig.setMessage("Connection succesful");
            } else {
                fDatabaseConfig.setMessage("Connection unsuccesful");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fDatabaseConfig.setMessage("Connection unsuccesful: " + e.getMessage());
        }
    }

}
