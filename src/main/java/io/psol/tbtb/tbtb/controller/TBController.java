package io.psol.tbtb.tbtb.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

@RestController
public class TBController implements Runnable {
    public static final int androidServerPort = 5555;
    public static final int iosServerPort = 6666;
    String str, str2;

    @Override
    public void run() {
        try {
            ServerSocket androidServerSocket = new ServerSocket(androidServerPort);
            ServerSocket iosServerSocket = new ServerSocket(iosServerPort);
            System.out.println("S: Connecting...");

            System.out.println(InetAddress.getLocalHost().getHostAddress());
            while (true) {
                Socket androidSocket = androidServerSocket.accept();
                System.out.println("Android S: Receiving...");
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(androidSocket.getInputStream()));
                    str = in.readLine();
                    if (str != null){
                        str2 = str;
                    }
                    System.out.println("Android S: Received: '" + str + "'");

                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(androidSocket.getOutputStream())), true);
                    out.println("Android Server Received " + str);
                } catch (Exception e) {
                    System.out.println("Android S: Error");
                    e.printStackTrace();
                } finally {
                    androidSocket.close();
                    System.out.println("Android S: Done.");
                }

                Socket iosSocket = iosServerSocket.accept();
                System.out.println("iOS S: Receiving...");
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(iosSocket.getInputStream()));
                    str = in.readLine();

                    System.out.println("iOS S: Received: '" + str + "'");

                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(iosSocket.getOutputStream())), true);
                    out.println("iOS Server Received " + str);
                } catch (Exception e) {
                    System.out.println("iOS S: Error");
                    e.printStackTrace();
                } finally {
                    iosSocket.close();
                    System.out.println("iOS S: Done.");
                }
            }
        } catch (Exception e) {
            System.out.println("S: Error");
            e.printStackTrace();
        }
    }

    @RequestMapping("/home")
    public String home() {
        System.out.println("str : " + str + "\nstr2 : " + str2);
        return str2;
    }
}
