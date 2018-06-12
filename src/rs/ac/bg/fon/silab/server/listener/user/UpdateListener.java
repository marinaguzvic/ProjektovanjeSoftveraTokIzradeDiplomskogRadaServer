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
import rs.ac.bg.fion.silab.gui.general.FormState;
import rs.ac.bg.fon.silab.server.controller.GeneralControllerNew;

/**
 *
 * @author MARINA
 */
public class UpdateListener implements ActionListener {

        GeneralControllerNew ggc;

        public UpdateListener(GeneralControllerNew ggc) {
            this.ggc = ggc;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ggc.convertGraphicalIntoDomainObject();
                ggc.SOUpdate(ggc.getGdo());
                ggc.showMessage("Update succesful");
                ggc.closeForm();
            } catch (Exception ex) {
                ggc.emptyGraphicalObject();
            }
            
        }

    }