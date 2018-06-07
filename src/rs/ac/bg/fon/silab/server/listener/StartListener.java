/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fion.silab.gui.general.FormState;
import rs.ac.bg.fon.silab.server.controller.Controller;

/**
 *
 * @author MARINA
 */
public class StartListener implements ActionListener{
    Controller controller;

    public StartListener(Controller controller) {
        this.controller = controller;
    }
    
    
            

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            controller.startServer();
            controller.prepareFormFor(FormState.START_SERVER);
        } catch (Exception ex) {
            Logger.getLogger(StartListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
