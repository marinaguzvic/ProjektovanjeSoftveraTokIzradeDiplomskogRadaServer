/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.controller;

import java.io.ObjectInputStream;
import rs.ac.bg.fon.silab.server.logic.SOFindById;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.silab.constants.Constants;
import rs.ac.bg.fon.silab.jpa.example1.domain.CompundDObject;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCClanKomisije;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCDiplomskiRad;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCKomisija;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCNastavnik;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCPredmet;
import rs.ac.bg.fon.silab.server.logic.SOSaveRecord;
import rs.ac.bg.fon.silab.server.logic.SOFindAllRecords;
import rs.ac.bg.fon.silab.server.logic.SOGenerateBrojIndeksa;
import rs.ac.bg.fon.silab.server.logic.SOUpdateRecord;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCStudent;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCTemaDiplomskogRada;
import rs.ac.bg.fon.silab.jpa.example1.domain.GeneralDObject;
import rs.ac.bg.fon.silab.jpa.example1.domain.EStatus;
import rs.ac.bg.fon.silab.jpa.example1.domain.ETitula;
import rs.ac.bg.fon.silab.jpa.example1.domain.EUlogaClanaKomisije;
import rs.ac.bg.fon.silab.jpa.example1.domain.EZvanje;
import rs.ac.bg.fon.silab.server.logic.AbstractGenericSO;
import rs.ac.bg.fon.silab.server.logic.SOFindChildren;

/**
 *
 * @author MARINA
 */
public class Controller {

    private static Controller instance;
    private AbstractGenericSO abstractSo;

    private Controller() {

    }

//    public void findAll(GeneralDObject gdo,List<GeneralDObject> list) throws Exception {
//
//        ResultSet rs = new SOFindAllRecords().execute(gdo);
//        GeneralDObject gdoFound;
//        while((gdoFound = convertResultSetToObject(rs,gdo.getClassName())) != null){
//            list.add(gdoFound);
//        }
//    }
//
//    public GeneralDObject save(GeneralDObject generalDObject) throws Exception {
//        try {
//            generalDObject.checkConstraints();
//            new SOSaveRecord().execute(generalDObject);
//            return generalDObject;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            //send a message about an error
//            return null;
//        }
//    }
//
//    public static Controller getInstance() {
//        if (instance == null) {
//            instance = new Controller();
//        }
//        return instance;
//
//    }
//
//    public GeneralDObject update(GeneralDObject gdo) throws Exception {
//        return new SOUpdateRecord().execute(gdo);
//    }
//
//    public GeneralDObject generateNewBrojIndeksa(GeneralDObject gdo) throws Exception {
//        return new SOGenerateBrojIndeksa().execute(gdo);
//
//    }
//
//    public GeneralDObject findByID(GeneralDObject gdo) throws Exception {
//        ResultSet rs = new SOFindById().execute(gdo);
//        return convertResultSetToObject(rs, gdo.getClassName());
//    }
//
////    public void findByWhere(GeneralDObject gdo, String where, List<GeneralDObject> list) throws Exception {
////        ResultSet rs =  new SOFindByWhere().execute(gdo, where);
////        while((gdo = convertResultSetToObject(rs, gdo.getClassName()))!= null){
////            list.add(gdo);
////        }
////    }
    public void findChildren(CompundDObject cdo, List<GeneralDObject> list) throws Exception {
        for (String className : cdo.classNames()) {
            ResultSet rs = new SOFindChildren().execute(cdo.createChild(className));
            GeneralDObject gdo;
            while ((gdo = convertResultSetToObject(rs, cdo, className)) != null) {
                list.add(gdo);
            }

        }

    }

    protected GeneralDObject SOCall(String SOName, GeneralDObject gdo) {
        
        try {
            switch (SOName) {
                case Constants.SOCall.SO_SAVE:
                    abstractSo = new SOSaveRecord();
                    
                case Constants.SOCall.SO_UPDATE:
                    return Controller.getInstance().update(gdo);
                case Constants.SOCall.SO_GENERATE_BROJ_INDEKSA:
                    return Controller.getInstance().generateNewBrojIndeksa(gdo);
                case Constants.SOCall.SO_FIND_BY_ID:
                    return Controller.getInstance().findByID(gdo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showMessage(e.getMessage());
        }
        return null;
    }

    protected void SOCall(String SOName, List<GeneralDObject> list, GeneralDObject gdo) {
        try {
            switch (SOName) {

                case Constants.SOCall.SO_FIND_ALL:
                    Controller.getInstance().findAll(gdo, list);
                    break;

            }
        } catch (Exception ex) {
            showMessage(ex.getMessage());
        }
    }

    protected void SOCall(String SOName, List<GeneralDObject> list, GeneralDObject gdo, String where) {
        try {
            switch (SOName) {
                case Constants.SOCall.SO_FIND_WHERE:
                    Controller.getInstance().findByWhere(gdo, where, list);
                default:
            }
        } catch (Exception ex) {
            showMessage(ex.getMessage());
        }
    }

    public GeneralDObject convertResultSetToObject(ResultSet rs, String className) throws Exception {
        if (rs.next()) {
            switch (className) {
                case Constants.Komisija.CLASS_NAME:
                    DCKomisija komisija = new DCKomisija(rs.getLong(Constants.Komisija.KOMISIJA_ID));
                    List<GeneralDObject> gdos = new ArrayList<>();
                    List<DCClanKomisije> clanovi = new ArrayList<>();
                    findChildren(komisija, gdos);
                    for (GeneralDObject gdo : gdos) {
                        clanovi.add((DCClanKomisije) gdo);
                    }
                    if (clanovi.isEmpty()) {
                        return null;
                    }
                    komisija.setClanovi(clanovi);
                    return komisija;
                case Constants.DiplomskiRad.CLASS_NAME:
                    return new DCDiplomskiRad((DCTemaDiplomskogRada) findByID(new DCTemaDiplomskogRada(rs.getLong(Constants.DiplomskiRad.TEMA_ID_FK))), (DCStudent) findByID(new DCStudent(rs.getString(Constants.DiplomskiRad.BROJ_INDEKSA_FK))), (DCKomisija) findByID(new DCKomisija(rs.getLong(Constants.DiplomskiRad.KOMISIJA_ID_FK))), rs.getDate(Constants.DiplomskiRad.DATUM_PRIJAVE).toLocalDate(), rs.getDate(Constants.DiplomskiRad.DATUM_KAD_JE_ODOBREN) == null ? null : rs.getDate(Constants.DiplomskiRad.DATUM_KAD_JE_ODOBREN).toLocalDate(), rs.getDate(Constants.DiplomskiRad.DATUM_ODBRANE) == null ? null : rs.getDate(Constants.DiplomskiRad.DATUM_ODBRANE).toLocalDate(), EStatus.valueOf(rs.getString(Constants.DiplomskiRad.STATUS)), rs.getInt(Constants.DiplomskiRad.OCENA));
                case Constants.Student.CLASS_NAME:
                    return new DCStudent(rs.getString(Constants.Student.BROJ_INDEKSA), rs.getString(Constants.Student.JMBG), rs.getString(Constants.Student.IME), rs.getString(Constants.Student.PREZIME), rs.getBoolean(Constants.Student.BUDZET), rs.getInt(Constants.Student.GODINA_STUDIJA), rs.getDate(Constants.Student.DATUM_RODJENJA).toLocalDate(), rs.getBoolean(Constants.Student.PRVI_PUT_UPISAO));
                case Constants.Nastavnik.CLASS_NAME:
                    return new DCNastavnik(rs.getLong(Constants.Nastavnik.NASTAVNIK_ID), rs.getString(Constants.Nastavnik.IME_NASTAVNIKA), rs.getString(Constants.Nastavnik.PREZIME_NASTAVNIK), rs.getString(Constants.Nastavnik.BROJ_RADNE_KNJIZICE), rs.getString(Constants.Nastavnik.JMBG), EZvanje.valueOf(rs.getString(Constants.Nastavnik.ZVANJE)), ETitula.valueOf(rs.getString(Constants.Nastavnik.TITULA)));
                case Constants.Predmet.CLASS_NAME:
                    return new DCPredmet(rs.getLong(Constants.Predmet.PREDMET_ID), rs.getString(Constants.Predmet.NAZIV_PREDMETA));
                case Constants.TemaDiplomskogRada.CLASS_NAME:
                    return new DCTemaDiplomskogRada(rs.getLong(Constants.TemaDiplomskogRada.TEMA_ID), rs.getString(Constants.TemaDiplomskogRada.NAZIV_TEME), rs.getString(Constants.TemaDiplomskogRada.OPIS_TEME), (DCPredmet) findByID(new DCPredmet(rs.getLong(Constants.TemaDiplomskogRada.PREDMET_ID_FK))));
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    public GeneralDObject convertResultSetToObject(ResultSet rs, CompundDObject parent, String className) throws Exception {
        if (rs.next()) {
            switch (className) {
                case Constants.ClanKomisije.CLASS_NAME:
                    return new DCClanKomisije((DCKomisija) parent, rs.getInt(Constants.ClanKomisije.CLAN_KOMISIJE_RB), (DCNastavnik) findByID(new DCNastavnik(rs.getLong(Constants.ClanKomisije.NASTAVNIK_ID_FK))), EUlogaClanaKomisije.valueOf(rs.getString(Constants.ClanKomisije.ULOGA_CLANA_KOMISIJE)));
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

}
