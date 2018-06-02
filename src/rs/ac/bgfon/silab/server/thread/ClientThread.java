/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bgfon.silab.server.thread;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.ir.RuntimeNode;
import rs.ac.bg.fon.silab.constants.Constants;
import rs.ac.bg.fon.silab.jpa.example1.domain.CompundDObject;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCClanKomisije;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCDiplomskiRad;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCKomisija;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCNastavnik;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCPredmet;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCStudent;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCTemaDiplomskogRada;
import rs.ac.bg.fon.silab.jpa.example1.domain.EStatus;
import rs.ac.bg.fon.silab.jpa.example1.domain.ETitula;
import rs.ac.bg.fon.silab.jpa.example1.domain.EUlogaClanaKomisije;
import rs.ac.bg.fon.silab.jpa.example1.domain.EZvanje;
import rs.ac.bg.fon.silab.jpa.example1.domain.GeneralDObject;
import rs.ac.bg.fon.silab.server.controller.Controller;
import rs.ac.bg.fon.silab.server.logic.AbstractGenericSO;
import rs.ac.bg.fon.silab.server.logic.SOFindAllRecords;
import rs.ac.bg.fon.silab.server.logic.SOFindById;
import rs.ac.bg.fon.silab.server.logic.SOFindChildren;
import rs.ac.bg.fon.silab.server.logic.SOGenerateBrojIndeksa;
import rs.ac.bg.fon.silab.server.logic.SOSaveRecord;
import rs.ac.bg.fon.silab.server.logic.SOUpdateRecord;
import transfer.request.RequestObject;
import transfer.response.ResponseObject;
import transfer.util.IOperation;
import transfer.util.IStatus;

/**
 *
 * @author MARINA
 */
public class ClientThread extends Thread {

    Socket socket;
    ObjectInputStream input;
    ObjectOutputStream output;
    RequestObject request;
    ResponseObject response;
    AbstractGenericSO so;

    public ClientThread(Socket socket) throws IOException {
        this.socket = socket;
        input = new ObjectInputStream(socket.getInputStream());
        output = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                request = (RequestObject) input.readObject();
                response = new ResponseObject();
                processRequest(request);
                output.writeObject(response);
                output.flush();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

        }
    }

    private void processRequest(RequestObject request) {
        try {
            switch (request.getOperation()) {
                case IOperation.SO_SAVE:
                    so = new SOSaveRecord();
                    so.templateExecute((GeneralDObject) request.getData());
                    response.setData(request.getData());
                    response.setMessage("Save succesfull: " + request.getData());
                    break;
                case IOperation.SO_UPDATE:
                    so = new SOUpdateRecord();
                    so.templateExecute((GeneralDObject) request.getData());
                    response.setData(request.getData());
                    response.setMessage("Update succesfull: " + request.getData());
                    break;
                case IOperation.SO_GENERATE_BROJ_INDEKSA:
                    so = new SOGenerateBrojIndeksa();
                    so.templateExecute((GeneralDObject) request.getData());
                    response.setData(request.getData());
                    response.setMessage("Generate broj indeksa and insert succesfull");
                    break;
                case IOperation.SO_FIND_BY_ID: {
                    so = new SOFindById();
                    ResultSet rs = so.templateExecute((GeneralDObject) request.getData());
                    GeneralDObject gdo = convertResultSetToObject(rs, (String) request.getData());
                    response.setData(gdo);
                    break;
                }
                case IOperation.SO_FIND_ALL: {
                    so = new SOFindAllRecords();
                    ResultSet rs = so.templateExecute((GeneralDObject) request.getData());
                    List<GeneralDObject> list = new ArrayList<>();
                    GeneralDObject gdoFound;
                    while ((gdoFound = convertResultSetToObject(rs, ((GeneralDObject) request.getData()).getClassName())) != null) {
                        list.add(gdoFound);
                    }
                    response.setData(list);
                    break;
                }
                
            }
            response.setCode(IStatus.OK);
        } catch (Exception e) {
            response.setCode(IStatus.ERROR);
            response.setMessage(e.getMessage());
        }

    }

    public GeneralDObject convertResultSetToObject(ResultSet rs, String className) throws Exception {
        if (rs.next()) {
            switch (className) {
                case Constants.Komisija.CLASS_NAME: {
                    DCKomisija komisija = new DCKomisija(rs.getLong(Constants.DiplomskiRad.KOMISIJA_ID_FK));
                    List<GeneralDObject> gdos = new ArrayList<>();
                    so = new SOFindChildren();
                    findChildren(so.templateExecute(komisija), komisija, gdos);
                    for (GeneralDObject gdo1 : gdos) {
                        komisija.getClanovi().add((DCClanKomisije) gdo1);
                    }
                    return komisija;
                }
                case Constants.DiplomskiRad.CLASS_NAME: {
                    so = new SOFindById();
                    GeneralDObject gdo = new DCTemaDiplomskogRada(rs.getLong(Constants.DiplomskiRad.TEMA_ID_FK));
                    DCTemaDiplomskogRada tema = (DCTemaDiplomskogRada) convertResultSetToObject(so.templateExecute(gdo), gdo.getClassName());
                    gdo = new DCStudent(rs.getString(Constants.DiplomskiRad.BROJ_INDEKSA_FK));
                    DCStudent student = (DCStudent) convertResultSetToObject(so.templateExecute(gdo), gdo.getClassName());
                    DCKomisija komisija = new DCKomisija(rs.getLong(Constants.DiplomskiRad.KOMISIJA_ID_FK));
                    List<GeneralDObject> gdos = new ArrayList<>();
                    so = new SOFindChildren();
                    findChildren(so.templateExecute(komisija), komisija, gdos);
                    for (GeneralDObject gdo1 : gdos) {
                        komisija.getClanovi().add((DCClanKomisije) gdo1);
                    }
                    return new DCDiplomskiRad(tema, student, komisija, rs.getDate(Constants.DiplomskiRad.DATUM_PRIJAVE).toLocalDate(), rs.getDate(Constants.DiplomskiRad.DATUM_KAD_JE_ODOBREN) == null ? null : rs.getDate(Constants.DiplomskiRad.DATUM_KAD_JE_ODOBREN).toLocalDate(), rs.getDate(Constants.DiplomskiRad.DATUM_ODBRANE) == null ? null : rs.getDate(Constants.DiplomskiRad.DATUM_ODBRANE).toLocalDate(), EStatus.valueOf(rs.getString(Constants.DiplomskiRad.STATUS)), rs.getInt(Constants.DiplomskiRad.OCENA));
                }
                case Constants.Student.CLASS_NAME:
                    return new DCStudent(rs.getString(Constants.Student.BROJ_INDEKSA), rs.getString(Constants.Student.JMBG), rs.getString(Constants.Student.IME), rs.getString(Constants.Student.PREZIME), rs.getBoolean(Constants.Student.BUDZET), rs.getInt(Constants.Student.GODINA_STUDIJA), rs.getDate(Constants.Student.DATUM_RODJENJA).toLocalDate(), rs.getBoolean(Constants.Student.PRVI_PUT_UPISAO));
                case Constants.Nastavnik.CLASS_NAME:
                    return new DCNastavnik(rs.getLong(Constants.Nastavnik.NASTAVNIK_ID), rs.getString(Constants.Nastavnik.IME_NASTAVNIKA), rs.getString(Constants.Nastavnik.PREZIME_NASTAVNIK), rs.getString(Constants.Nastavnik.BROJ_RADNE_KNJIZICE), rs.getString(Constants.Nastavnik.JMBG), EZvanje.valueOf(rs.getString(Constants.Nastavnik.ZVANJE)), ETitula.valueOf(rs.getString(Constants.Nastavnik.TITULA)));
                case Constants.Predmet.CLASS_NAME:
                    return new DCPredmet(rs.getLong(Constants.Predmet.PREDMET_ID), rs.getString(Constants.Predmet.NAZIV_PREDMETA));
                case Constants.TemaDiplomskogRada.CLASS_NAME:
                    GeneralDObject gdo = new DCPredmet(rs.getLong(Constants.TemaDiplomskogRada.PREDMET_ID_FK));
                    so = new SOFindById();
                    DCPredmet predmet = (DCPredmet) convertResultSetToObject(so.templateExecute(gdo), gdo.getClassName());
                    return new DCTemaDiplomskogRada(rs.getLong(Constants.TemaDiplomskogRada.TEMA_ID), rs.getString(Constants.TemaDiplomskogRada.NAZIV_TEME), rs.getString(Constants.TemaDiplomskogRada.OPIS_TEME), predmet);
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
                    GeneralDObject gdo = new DCNastavnik(rs.getLong(Constants.ClanKomisije.NASTAVNIK_ID_FK));
                    so = new SOFindById();
                    DCNastavnik nastavnik = (DCNastavnik) convertResultSetToObject(so.templateExecute(gdo), gdo.getClassName());
                    return new DCClanKomisije((DCKomisija) parent, rs.getInt(Constants.ClanKomisije.CLAN_KOMISIJE_RB), nastavnik, EUlogaClanaKomisije.valueOf(rs.getString(Constants.ClanKomisije.ULOGA_CLANA_KOMISIJE)));
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    public void findChildren(ResultSet rs, CompundDObject cdo, List<GeneralDObject> list) throws Exception {
        for (String className : cdo.classNames()) {
            GeneralDObject gdo;
            while ((gdo = convertResultSetToObject(rs, cdo, className)) != null) {
                list.add(gdo);
            }

        }

    }
}
