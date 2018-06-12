/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.controller;

import java.awt.Frame;
import rs.ac.bg.fion.silab.gui.general.GeneralGUI;
import rs.ac.bg.fion.silab.gui.general.FormState;

/**
 *
 * @author MARINA
 */
public abstract class GUIControllerDialog{


    protected Frame parent;

    public GUIControllerDialog(Frame parent) {
        this.parent = parent;
    }

    
    public Frame getfParent() {
        return parent;
    }

    public abstract void emptyGraphicalObject();

    public abstract GeneralGUI getGeneralGUI();

    public abstract void showMessage(String signal);

    public abstract void closeForm();

    public abstract void convertGraphicalIntoDomainObject();

    public abstract void convertDomainIntoGraphicalObject();

    public abstract void createObject();

    public abstract void setListeners();

    public abstract void prepareFormFor(FormState formState);

  
}
