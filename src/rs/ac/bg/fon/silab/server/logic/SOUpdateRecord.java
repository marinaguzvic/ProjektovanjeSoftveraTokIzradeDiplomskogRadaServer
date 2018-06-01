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
public class SOUpdateRecord {
    public GeneralDObject execute(GeneralDObject gdo) throws Exception{
        try {
            GeneralDObject gdoReturn = DatabaseRepository.getInstance().updateRecordCompound(gdo);
            DatabaseRepository.getInstance().commitTransaction();
            return gdoReturn;
        } catch (Exception ex) {
            DatabaseRepository.getInstance().rollbackTransaction();
            throw new Exception("Error saving record " + gdo + "\n" + ex.getMessage());
        }
    }
}
