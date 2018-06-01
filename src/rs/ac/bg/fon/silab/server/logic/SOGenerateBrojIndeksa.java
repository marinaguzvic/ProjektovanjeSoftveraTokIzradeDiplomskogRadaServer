/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.logic;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import rs.ac.bg.fon.silab.constants.Constants;
import rs.ac.bgfon.silab.server.db.DatabaseRepository;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCSkolskaGodina;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCStudent;
import rs.ac.bg.fon.silab.jpa.example1.domain.GeneralDObject;

/**
 *
 * @author MARINA
 */
public class SOGenerateBrojIndeksa extends AbstractGenericSO {

    public ResultSet execute(GeneralDObject gdo) throws Exception {
        String indexNo = null;
        ResultSet rs = db.findRecordsByWhereCondition(new DCSkolskaGodina(), Constants.SkolskaGodina.AKTIVNA + "=" + true);
        DCSkolskaGodina skolskaGodina = null;
        if (rs.next()) {
            skolskaGodina = new DCSkolskaGodina(rs.getString(Constants.SkolskaGodina.SKOLSKA_GODINA), true);
            rs.close();
        } else {
            throw new Exception("Result set for Skolska godina is empty");
        }
        String[] years = skolskaGodina.getSkolskaGodina().split("/");
        ResultSet rsStudents = DatabaseRepository.getInstance().findRecordsByWhereCondition(new DCStudent(), Constants.Student.BROJ_INDEKSA + " LIKE " + "'" + years[0] + "%'");
        String lastIndexNo;
        if (rsStudents.last()) {
            lastIndexNo = rsStudents.getString(Constants.Student.BROJ_INDEKSA);
            String no = lastIndexNo.substring(4);
            Integer noInt = Integer.parseInt(no);
            noInt++;
            String formattedNo = String.format("%04d", noInt);
            indexNo = years[0] + formattedNo;
            ((DCStudent) gdo).setBrojIndeksa(indexNo);
        } else {
            indexNo = years[0] + String.format("%04d", 1);
            ((DCStudent) gdo).setBrojIndeksa(indexNo);
        }
        db.insertRecordCompound(gdo);
        return null;

    }
}
