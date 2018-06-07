/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.listener.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import rs.ac.bg.fon.silab.server.controller.Controller;
import rs.ac.bg.fon.silab.server.controller.ServerPortController;

/**
 *
 * @author MARINA
 */
public class PortListener implements ActionListener {
    
    Controller controller;
    public PortListener(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ServerPortController portController = new ServerPortController(controller.getForm());
    }
    
}
