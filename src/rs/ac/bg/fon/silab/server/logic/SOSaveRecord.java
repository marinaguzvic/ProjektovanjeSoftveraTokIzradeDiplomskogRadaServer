/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.logic;

import java.sql.ResultSet;
import rs.ac.bg.fon.silab.jpa.example1.domain.GeneralDObject;

/**
 *
 * @author MARINA
 */
public class SOSaveRecord extends AbstractGenericSO {

    @Override
    public ResultSet execute(GeneralDObject gdo) throws Exception {
        db.insertRecordCompound(gdo);
        return null;
    }
}
