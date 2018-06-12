/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.controller;

import java.awt.Frame;
import rs.ac.bg.fion.silab.gui.general.FormState;
import rs.ac.bg.fion.silab.gui.general.GeneralGUI;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCKorisnik;
import rs.ac.bg.fon.silab.jpa.example1.domain.GeneralDObject;
import rs.ac.bg.fon.silab.server.form.FUserRegistration;
import rs.ac.bg.fon.silab.server.listener.user.CancelListener;
import rs.ac.bg.fon.silab.server.listener.user.FindListener;
import rs.ac.bg.fon.silab.server.listener.user.SaveListener;
import rs.ac.bg.fon.silab.server.listener.user.UpdateListener;

/**
 *
 * @author MARINA
 */
public class GUIControllerUserRegistration extends GeneralControllerNew{
    
    FUserRegistration fUserRegistration;
    DCKorisnik korisnik;
    

    public GUIControllerUserRegistration(Frame parent,FormState formState) {
        super(parent);
        fUserRegistration = new FUserRegistration(parent,true);
        createObject();
        setListeners();
        prepareFormFor(formState);
        fUserRegistration.setVisible(true);
    }

    @Override
    public GeneralDObject getGdo() {
        return korisnik;
    }

    @Override
    public void emptyGraphicalObject() {
        fUserRegistration.getjTxtIme().setText("");
        fUserRegistration.getjTxtKorisnickoIme().setText("");
        fUserRegistration.getjTxtPrezime().setText("");
        fUserRegistration.getjTxtSifra().setText("");
    }

    @Override
    public GeneralGUI getGeneralGUI() {
        return fUserRegistration;
    }

    @Override
    public void showMessage(String signal) {
        fUserRegistration.setMessage(signal);
    }

    @Override
    public void closeForm() {
        fUserRegistration.close();
    }

    @Override
    public void convertGraphicalIntoDomainObject() {
        korisnik.setIme(fUserRegistration.getjTxtIme().getText());
        korisnik.setPrezime(fUserRegistration.getjTxtPrezime().getText());
        korisnik.setUsername(fUserRegistration.getjTxtKorisnickoIme().getText());
        korisnik.setPassword(fUserRegistration.getjTxtSifra().getText().toCharArray());
    }

    @Override
    public void convertDomainIntoGraphicalObject() {
        fUserRegistration.getjTxtIme().setText(korisnik.getIme());
        fUserRegistration.getjTxtKorisnickoIme().setText(korisnik.getUsername());
        fUserRegistration.getjTxtPrezime().setText(korisnik.getPrezime());
        fUserRegistration.getjTxtSifra().setText(new String(korisnik.getPassword()));
    }

    @Override
    public void createObject() {
        korisnik = new DCKorisnik();
    }

    @Override
    public void setListeners() {
        fUserRegistration.getjBtnCancel().addActionListener(new CancelListener(this));
        fUserRegistration.getjBtnSave().addActionListener(new SaveListener(this));
        fUserRegistration.getjBtnUpdate().addActionListener(new UpdateListener(this));
        fUserRegistration.getjBtnFind().addActionListener(new FindListener(this));
    }

    @Override
    public void prepareFormFor(FormState formState) {
        switch(formState){
            case INSERT:
                fUserRegistration.getjBtnUpdate().setVisible(false);
                fUserRegistration.getjBtnFind().setVisible(false);
                break;
            case EDIT:
                fUserRegistration.getjBtnSave().setVisible(false);
                break;
        }
    }

    @Override
    public void setGdo(GeneralDObject gdo) {
        this.korisnik = (DCKorisnik) gdo;
    }
    
    
    
}
