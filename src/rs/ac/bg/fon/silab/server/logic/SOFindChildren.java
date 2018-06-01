/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.logic;

import java.sql.ResultSet;
import rs.ac.bg.fon.silab.jpa.example1.domain.CompundDObject;
import rs.ac.bg.fon.silab.jpa.example1.domain.GeneralDObject;
import rs.ac.bgfon.silab.server.db.DatabaseRepository;

/**
 *
 * @author MARINA
 */
public class SOFindChildren extends AbstractGenericSO{
    @Override
    public ResultSet execute(GeneralDObject gdo) throws Exception {
        return db.findRecordsByWhereCondition(gdo,((CompundDObject)gdo).getWhere(gdo.getClassName()));
    }
}
