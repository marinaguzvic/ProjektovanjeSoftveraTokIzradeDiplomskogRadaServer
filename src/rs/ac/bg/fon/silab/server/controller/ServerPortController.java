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
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.silab.constants.Constants;
import rs.ac.bg.fon.silab.server.form.FDatabaseConfig;
import rs.ac.bg.fon.silab.server.form.FPort;
import rs.ac.bg.fon.silab.server.listener.config.SaveConfigListener;
import rs.ac.bg.fon.silab.server.listener.config.TestConfigListener;

/**
 *
 * @author MARINA
 */
public class ServerPortController implements ConfigController{
    FPort fPort;

    public ServerPortController(Frame parent) {
        fPort = new FPort(parent, true);
        readProperties();
        setListeners();
        fPort.setVisible(true);
    }

    @Override
    public void setListeners() {
        fPort.getjBtnSave().addActionListener(new SaveConfigListener(this));
    }

    @Override
    public Properties getProperties() {
        FileInputStream file = null;
        try {
            file = new FileInputStream(Constants.PATH_TO_CONFIG_FILE);
            Properties prop = new Properties();
            prop.load(file);
            file.close();
            prop.setProperty(Constants.SERVER + "_" + Constants.PORT, (String) fPort.getjTxtPort().getText().trim());
            return prop;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(DatabaseConfigController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void createConfigFile() {
        try {

            OutputStream out = new FileOutputStream(Constants.PATH_TO_CONFIG_FILE);
            getProperties().store(out, null);
            out.close();
        } catch (FileNotFoundException ex) {
            fPort.setMessage("File not found");

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
            fPort.getjTxtPort().setText(prop.getProperty(Constants.SERVER + "_" + Constants.PORT));

        } catch (FileNotFoundException ex) {
            fPort.setMessage("File not found");

            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(DatabaseConfigController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
