/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bgfon.silab.server.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import rs.ac.bg.fon.silab.server.form.model.ServerTableModel;
import transfer.request.RequestObject;

/**
 *
 * @author MARINA
 */
public class ServerThread extends Thread {

    ServerSocket serverSocket;
    List<ClientThread> clients;
    JTextArea jTextAreaStatus;
    ServerTableModel stm;

    public ServerThread(int port,JTextArea jTextAreaStatus) throws IOException {
        this.jTextAreaStatus = jTextAreaStatus;
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<>();
        jTextAreaStatus.append("\nServer started on port: " + port);
    }

    public void setStm(ServerTableModel stm) {
        this.stm = stm;
    }

    public void setStmData(){
        stm.setData(clients);
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
}
