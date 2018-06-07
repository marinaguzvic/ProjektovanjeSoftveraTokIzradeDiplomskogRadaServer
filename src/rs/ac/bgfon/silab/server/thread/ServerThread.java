/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bgfon.silab.server.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import rs.ac.bg.fon.silab.jpa.example1.domain.DCKorisnik;
import rs.ac.bg.fon.silab.server.form.model.Observable;
import rs.ac.bg.fon.silab.server.form.model.Observer;

/**
 *
 * @author MARINA
 */
public class ServerThread extends Thread implements Observable{

    ServerSocket serverSocket;
    List<ClientThread> clients;
    JTextArea jTextAreaStatus;
    Observer observer;

    public ServerThread(int port,JTextArea jTextAreaStatus, Observer observer) throws IOException {
        this.observer = observer;
        this.jTextAreaStatus = jTextAreaStatus;
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<>();
        jTextAreaStatus.append("\nServer started on port: " + port);
    }
    

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                System.out.println("Waiting for a client");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                ClientThread client = new ClientThread(socket,jTextAreaStatus, this);
                client.start();
                clients.add((ClientThread) client);
                jTextAreaStatus.append("\nNew client added");
                notifyObserves();
            }
        } catch (IOException ex) {
            jTextAreaStatus.append("\nServer stopped: " + ex.getMessage());
        }
    }

    public void stopSocket() throws IOException {
        serverSocket.close();
        for (ClientThread client : clients) {
            client.interrupt();
            client.closeSocket();
            
        }
        clients = null;
    }

    public List<ClientThread> getClients() {
        return clients;
    }

    @Override
    public void notifyObserves() {
        updateClients();
        observer.updateData();
    }
    
    public void updateClients(){
        for (int i = 0; i < clients.size();i++) {
            if(clients.get(i).isInterrupted()){
                jTextAreaStatus.append("<" + clients.get(i).getKorisnik() + ">: " + "Disconnected");
                clients.remove(i);
            }
        }
    }

    boolean isConnected(DCKorisnik korisnikFound) {
        for (ClientThread client : clients) {
            if(client.getKorisnik() != null && client.getKorisnik().equals(korisnikFound)){
                return true;
            }
        }
        return false;
    }
}
