/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bgfon.silab.server.db.DatabaseRepository;
import rs.ac.bg.fon.silab.jpa.example1.domain.GeneralDObject;

/**
 *
 * @author MARINA
 */
public class SOUpdateRecord extends AbstractGenericSO {

    @Override
    public ResultSet execute(GeneralDObject gdo) throws Exception {
        db.updateRecordCompound(gdo);
        return null;
    }

    @Override
    protected boolean sameRecord(ResultSet rs, GeneralDObject gdo) {
        boolean same = true;
        for (String primaryKeyColumn : gdo.getPrimaryKeyColumns()) {
            try {
                same = same && gdo.getValue(primaryKeyColumn).equals(rs.getObject(primaryKeyColumn));
            } catch (SQLException ex) {
                System.out.println("Exception in sameRecord");
                ex.printStackTrace();
            }
        }
        return same;
    }

}
