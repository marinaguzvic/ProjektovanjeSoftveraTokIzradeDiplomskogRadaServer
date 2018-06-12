/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.controller;

import java.awt.Frame;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fion.silab.gui.general.GeneralGUINew;
import rs.ac.bg.fon.silab.constants.Constants;
import rs.ac.bg.fon.silab.constants.Constants.Korisnik;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCKorisnik;
import rs.ac.bg.fon.silab.jpa.example1.domain.GeneralDObject;
import rs.ac.bg.fon.silab.server.logic.AbstractGenericSO;
import rs.ac.bg.fon.silab.server.logic.SOFindById;
import rs.ac.bg.fon.silab.server.logic.SOSaveRecord;
import rs.ac.bg.fon.silab.server.logic.SOUpdateRecord;

/**
 *
 * @author MARINA
 */
public abstract class GeneralControllerNew extends GUIControllerDialog {

    public GeneralControllerNew(Frame parent) {
        super(parent);
    }

    public abstract GeneralDObject getGdo();
    public abstract void setGdo(GeneralDObject gdo);

    public void SOSave(GeneralDObject gdo) {
        try {
            AbstractGenericSO so = new SOSaveRecord();
            so.templateExecute(gdo);
        } catch (Exception ex) {
            showMessage("Error saving record: " + gdo + "\n" + ex.getMessage());
        }
    }

    public void SOUpdate(GeneralDObject gdo) throws Exception {
        try {
            AbstractGenericSO so = new SOUpdateRecord();
            so.templateExecute(gdo);
        } catch (Exception ex) {
            showMessage("Error updating record: " + gdo + "\n" + ex.getMessage());
            throw new Exception();
        }
    }

    public GeneralDObject SOFind(GeneralDObject gdo) throws Exception {
         try {
            AbstractGenericSO so = new SOFindById();
             ResultSet rs = so.templateExecute(gdo);
             if(rs.next()){
             return new DCKorisnik(rs.getString(Korisnik.USERNAME), rs.getString(Korisnik.PASSWORD).toCharArray(), rs.getString(Korisnik.IME), rs.getString(Korisnik.PREZIME));
             }else{
                 throw new Exception("No record for : " + gdo.toString() + " has been found");
             }
        } catch (Exception ex) {
            showMessage("Error finding record: " + gdo + "\n" + ex.getMessage());
            throw new Exception();
        }
    }





}
