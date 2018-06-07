/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fion.silab.gui.general.FormState;
import rs.ac.bg.fon.silab.server.controller.Controller;

/**
 *
 * @author MARINA
 */
public class StoptListener implements ActionListener{

    Controller controller;

    public StoptListener(Controller controller) {
        this.controller = controller;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            controller.stopServer();
            controller.prepareFormFor(FormState.STOP_SERVER);
        } catch (IOException ex) {
            Logger.getLogger(StoptListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
