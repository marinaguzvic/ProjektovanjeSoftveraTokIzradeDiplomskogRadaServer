/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.logic;

import java.sql.ResultSet;
import rs.ac.bg.fon.silab.jpa.example1.domain.GeneralDObject;
import rs.ac.bgfon.silab.server.db.DatabaseRepository;

/**
 *
 * @author MARINA
 */
public abstract class AbstractGenericSO {

    protected DatabaseRepository db;

    public AbstractGenericSO() {
        db = DatabaseRepository.getInstance();
    }

    public void templateExecute(GeneralDObject gdo) throws Exception {
        try {
            validate(gdo);
            try {
                execute(gdo);
                commitTransaction();
            } catch (Exception e) {
                rollbackTransaction();
                throw new Exception(e.getMessage());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void validate(GeneralDObject gdo) throws Exception {
        //ako je select, redefinisemo validate, jer za select se validate ne radi
        gdo.checkConstraints();
        checkUnique(gdo);
    }

    protected abstract ResultSet execute(GeneralDObject gdo) throws Exception;

    private void commitTransaction() throws Exception {
        db.commitTransaction();
    }

    private void rollbackTransaction() throws Exception {
        db.rollbackTransaction();
    }

    protected void checkUnique(GeneralDObject gdo) {
        //gdo implementira metodu vrati kolone koje su unique
        //iz database repo uzimamo sve recorde 
        //proveravamo da li je unique vrednost
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
