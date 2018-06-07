/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.listener.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import rs.ac.bg.fon.silab.server.controller.DatabaseConfigController;

/**
 *
 * @author MARINA
 */
public class TestConfigListener implements ActionListener{
    DatabaseConfigController controller;

    public TestConfigListener(DatabaseConfigController controller) {
        this.controller = controller;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        controller.testConnection();
    }
    
    
}
