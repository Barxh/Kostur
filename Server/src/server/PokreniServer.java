/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import controller.Controller;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PokreniServer extends Thread {

    private boolean kraj = false;
    private ServerSocket serverskiSoket;

    @Override
    public void run() {
        try {
            Properties properties = new Properties();
            try (FileInputStream fis = new FileInputStream("app.config")) {
                properties.load(fis);
                String key = properties.getProperty("max_broj_klijenata");
                int max = Integer.parseInt(key);
            } catch (IOException e) {

            }
            serverskiSoket = new ServerSocket(9000);
            System.out.println("SOKET OTVOREN");
            while (!kraj) {
                Socket s = serverskiSoket.accept();

                ObradaKlijentskihZahteva nit = new ObradaKlijentskihZahteva(s);

                nit.start();
            }

        } catch (IOException ex) {
            Logger.getLogger(PokreniServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void zaustaviServer() {
        System.out.println("SOKET ZATVOREN");
        kraj = true;
        try {
            serverskiSoket.close();
        } catch (IOException ex) {
            Logger.getLogger(PokreniServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
