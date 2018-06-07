/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.controller;

import rs.ac.bg.fon.silab.server.listener.menu.PortListener;
import java.awt.Frame;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import rs.ac.bg.fion.silab.gui.general.FormState;
import rs.ac.bg.fon.silab.constants.Constants;
import rs.ac.bg.fon.silab.server.form.FServer;
import rs.ac.bg.fon.silab.server.form.model.ServerTableModel;
import rs.ac.bg.fon.silab.server.listener.StartListener;
import rs.ac.bg.fon.silab.server.listener.StoptListener;
import rs.ac.bg.fon.silab.server.listener.menu.DatabaseConfigListener;
import rs.ac.bg.fon.silab.server.logic.AbstractGenericSO;
import rs.ac.bgfon.silab.server.thread.ServerThread;

/**
 *
 * @author MARINA
 */
public class Controller implements rs.ac.bg.fon.silab.server.form.model.Observer{

    FServer fServer;
    ServerThread serverThread;

    public Controller() {
        fServer = new FServer();
        setLiseners();
        prepareFormFor(FormState.STOP_SERVER);
        fServer.setVisible(true);
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
    }

    //nisam sigurna ni da mi ovo treba.. mozda server da bude ovde
    public Frame getForm() {
        return fServer;
    }

    private void setLiseners() {
        fServer.getjMenuItemDatabase().addActionListener(new DatabaseConfigListener(this));
        fServer.getjMenuItemPort().addActionListener(new PortListener(this));
        fServer.getjBtnStart().addActionListener(new StartListener(this));
        fServer.getjBtnStop().addActionListener(new StoptListener(this));
    }

    public void startServer() throws Exception {
        Properties prop = readProperties();
        serverThread = new ServerThread(Integer.parseInt(prop.getProperty(Constants.SERVER + "_" + Constants.PORT)), fServer.getjTextAreaStatus(),  this);
        prepareFormFor(FormState.START_SERVER);
        serverThread.start();
    }

    public void stopServer() throws IOException {
        serverThread.interrupt();
        serverThread.stopSocket();
        prepareFormFor(FormState.STOP_SERVER);
    }

    public Properties readProperties() throws Exception {
        try (FileInputStream file = new FileInputStream(Constants.PATH_TO_CONFIG_FILE)) {
            Properties prop = new Properties();
            prop.load(file);
            return prop;

        } catch (FileNotFoundException ex) {

            ex.printStackTrace();
            throw new Exception("File not found");
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("Error reading file");

        }
    }

    public void prepareFormFor(FormState formState) {
        switch (formState) {
            case START_SERVER:
                fServer.getjBtnStart().setEnabled(false);
                fServer.getjBtnStop().setEnabled(true);
                populateTableUsers();
                fServer.getjPanelUsers().setVisible(true);
                break;
            case STOP_SERVER:
                fServer.getjBtnStart().setEnabled(true);
                fServer.getjBtnStop().setEnabled(false);
                fServer.getjPanelUsers().setVisible(false);
                break;
        }
    }

    private void populateTableUsers() {
        fServer.getjTableUsers().setModel(new ServerTableModel(serverThread.getClients()));
    }

    @Override
    public void updateData() {
        ((ServerTableModel)fServer.getjTableUsers().getModel()).setData(serverThread.getClients());
    }


}
