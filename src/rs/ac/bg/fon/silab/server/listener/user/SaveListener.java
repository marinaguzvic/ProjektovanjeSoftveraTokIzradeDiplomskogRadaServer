/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.listener.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import rs.ac.bg.fon.silab.server.controller.GeneralControllerNew;

/**
 *
 * @author MARINA
 */
public class SaveListener implements ActionListener {

        GeneralControllerNew ggc;

        public SaveListener(GeneralControllerNew ggc) {
            this.ggc = ggc;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ggc.convertGraphicalIntoDomainObject();
            ggc.SOSave(ggc.getGdo());
            ggc.closeForm();
        }
}