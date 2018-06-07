/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.listener.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import rs.ac.bg.fon.silab.server.controller.Controller;
import rs.ac.bg.fon.silab.server.controller.DatabaseConfigController;
import rs.ac.bg.fon.silab.server.form.FServer;

/**
 *
 * @author MARINA
 */
public class DatabaseConfigListener implements ActionListener{
    
    Controller conttroller;

    public DatabaseConfigListener(Controller conttroller) {
        this.conttroller = conttroller;
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        DatabaseConfigController configController = new DatabaseConfigController(conttroller.getForm());
    }
    
}
