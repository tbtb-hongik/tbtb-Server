package io.psol.tbtb.tbtb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

@RestController
public class TBController implements Runnable {
    public static final int ServerPort = 5555;
    String str;

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(ServerPort);
            System.out.println("S: Connecting...");

            System.out.println(InetAddress.getLocalHost().getHostAddress());
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("S: Receiving...");
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    str = in.readLine();

                    System.out.println("S: Received: '" + str + "'");

                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    out.println("Server Received " + str);
                } catch (Exception e) {
                    System.out.println("S: Error");
                    e.printStackTrace();
                } finally {
                    socket.close();
                    System.out.println("S: Done.");
                }
            }
        } catch (Exception e) {
            System.out.println("S: Error");
            e.printStackTrace();
        }
    }

    @RequestMapping("/home")
    public String home() {
        return str;
    }
}
