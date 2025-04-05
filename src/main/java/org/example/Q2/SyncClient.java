package org.example.Q2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SyncClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int port = 5000;

        try (Socket socket = new Socket(serverAddress, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // 순차적으로 메시지 전송 후 응답 대기
            out.println("Ping");
            System.out.println("Send: Ping");
            System.out.println("Received: " + in.readLine());

            out.println("Ping");
            System.out.println("Send: Ping");
            System.out.println("Received: " + in.readLine());

            out.println("foobar");
            System.out.println("Send: foobar");
            System.out.println("Received: " + in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
