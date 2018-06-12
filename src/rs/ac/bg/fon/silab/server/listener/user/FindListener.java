/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.listener.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.silab.server.controller.GeneralControllerNew;

/**
 *
 * @author MARINA
 */
public class FindListener implements ActionListener{
    GeneralControllerNew controllerNew;

    public FindListener(GeneralControllerNew controllerNew) {
        this.controllerNew = controllerNew;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            controllerNew.convertGraphicalIntoDomainObject();
            controllerNew.setGdo(controllerNew.SOFind(controllerNew.getGdo()));
            controllerNew.convertDomainIntoGraphicalObject();
        } catch (Exception ex) {
           controllerNew.emptyGraphicalObject();
        }
    }
    
    
}
