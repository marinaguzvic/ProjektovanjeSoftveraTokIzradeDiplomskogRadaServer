/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.silab.server.form.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bgfon.silab.server.thread.ClientThread;

/**
 *
 * @author MARINA
 */
public class ServerTableModel extends AbstractTableModel {

    List<ClientThread> clients;

    public ServerTableModel(List<ClientThread> clients) {
        this.clients = clients;
    }

    @Override
    public int getRowCount() {
        return clients.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClientThread client = clients.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return client.getKorisnik() == null? "Unknown user" : client.getKorisnik().getIme() + "_" + client.getKorisnik().getPrezime();
            case 1:
                return client.getTimeConnected();
            case 2:
                return client.getSocket().getInetAddress();
            case 3: 
                return client.getSocket().getPort();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return new String[]{"Klijent","Vreme konektovanja na server","Address","Port"}[column];
    }



    public void setData(List<ClientThread> clients) {
        this.clients = clients;
        fireTableDataChanged();
    }

    
}
