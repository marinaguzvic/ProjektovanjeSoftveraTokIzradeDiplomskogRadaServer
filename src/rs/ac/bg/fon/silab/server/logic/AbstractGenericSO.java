/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.logic;

import jdk.nashorn.internal.runtime.regexp.RegExpFactory;
import rs.ac.bg.fon.silab.jpa.example1.domain.GeneralDObject;
import rs.ac.bgfon.silab.server.db.DatabaseRepository;

/**
 *
 * @author MARINA
 */
public class AbstractGenericSO {
    protected DatabaseRepository db;

    public AbstractGenericSO() {
        db = DatabaseRepository.getInstance();
    }
    
    public void templateExecute(GeneralDObject gdo) throws Exception{
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

    private void validate(GeneralDObject gdo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    private void execute(GeneralDObject gdo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void commitTransaction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void rollbackTransaction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
