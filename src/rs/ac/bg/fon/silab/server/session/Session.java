/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.session;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCKorisnik;

/**
 *
 * @author user
 */
public class Session {

    private static Session instance;
    private final Map<String, Object> map;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private DCKorisnik korisnik;

    private Session() {
        map = new HashMap<>();
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
    }

    public ObjectInputStream getInput() throws Exception {
        try {
            if (input == null) {
                input = new ObjectInputStream(socket.getInputStream());
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("Error opening input stream");
        }
        return input;
    }

    public ObjectOutputStream getOutput() throws Exception {
        try {
            if (output == null) {
                output = new ObjectOutputStream(socket.getOutputStream());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("Error opening output stream");
        }
        return output;
    }

    public DCKorisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(DCKorisnik korisnik) {
        this.korisnik = korisnik;
    }

    public void closeSocket() throws IOException {
        try {
            socket.close();
            socket = null;
            output = null;
            input = null;
        } catch (Exception ex) {
        }
    }

}
