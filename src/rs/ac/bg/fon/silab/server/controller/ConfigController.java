/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.controller;

import java.util.Properties;

/**
 *
 * @author MARINA
 */
public interface ConfigController {

    void createConfigFile();

    void readProperties();

    void setListeners();
    Properties getProperties();
}
