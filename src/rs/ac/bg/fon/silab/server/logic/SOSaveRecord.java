/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.logic;


import rs.ac.bgfon.silab.server.db.DatabaseRepository;
import rs.ac.bg.fon.silab.jpa.example1.domain.GeneralDObject;

/**
 *
 * @author MARINA
 */
public class SOSaveRecord {
    public void execute(GeneralDObject gdo) throws Exception{
        try {
            GeneralDObject gdoReturn = DatabaseRepository.insertRecordCompound(gdo);
            DatabaseRepository.getInstance().commitTransaction();
        } catch (Exception ex) {
            DatabaseRepository.getInstance().rollbackTransaction();
            ex.printStackTrace();
            throw new Exception("Error saving record " + gdo + "\n" + ex.getMessage());
        }
    }
}
