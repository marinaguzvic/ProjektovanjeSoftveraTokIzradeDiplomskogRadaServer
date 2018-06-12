/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.listener.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import rs.ac.bg.fon.silab.server.controller.GUIControllerDialog;

/**
 *
 * @author MARINA
 */
    public class CancelListener implements ActionListener{
        GUIControllerDialog ggc;

        public CancelListener(GUIControllerDialog ggc) {
            this.ggc = ggc;
        }
        
        
        @Override
        public void actionPerformed(ActionEvent e) {
            ggc.getGeneralGUI().close();
            
        }
        
    }