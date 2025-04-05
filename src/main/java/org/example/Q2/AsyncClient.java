package org.example.Q2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;

public class AsyncClient {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1";
        int port = 5000;

        try (Socket socket = new Socket(serverAddress, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // 서버 응답을 읽기 위한 별도의 스레드 시작
            Thread readerThread = new Thread(() -> {
                String line;
                try {
                    while ((line = in.readLine()) != null) {
                        System.out.println("Received: " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            readerThread.start();

            // 클라이언트는 연속적으로 메시지 전송 (마지막 메시지가 foobar면 종료)
            out.println("Ping");
            System.out.println("Send(1): Ping");
            out.println("Ping");
            System.out.println("Send(2): Ping");
            out.println("foobar");
            System.out.println("Send(3): foobar");

            readerThread.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
