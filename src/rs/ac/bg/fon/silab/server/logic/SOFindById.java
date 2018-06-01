/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.logic;

import java.sql.ResultSet;
import rs.ac.bgfon.silab.server.db.DatabaseRepository;
import rs.ac.bg.fon.silab.jpa.example1.domain.GeneralDObject;

/**
 *
 * @author MARINA
 */
public class SOFindById {


    

    public ResultSet execute(GeneralDObject gdo) throws Exception {
        try {
            ResultSet rs = DatabaseRepository.getInstance().findRecordByPrimaryKey(gdo);
            DatabaseRepository.getInstance().commitTransaction();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            DatabaseRepository.getInstance().rollbackTransaction();
            throw new Exception(e.getMessage());
        }
    }
}
