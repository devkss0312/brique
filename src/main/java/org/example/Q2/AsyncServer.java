package org.example.Q2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncServer {
    public static void main(String[] args) {
        int port = 5000;  // 서버 포트
        ExecutorService executor = Executors.newCachedThreadPool();
        AtomicInteger messageCount = new AtomicInteger(0);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected by " + clientSocket.getRemoteSocketAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String line;
            while ((line = in.readLine()) != null) {
                int count = messageCount.incrementAndGet();
                System.out.println("Received(" + count + "): " + line);

                final String finalLine = line;
                final int msgNumber = count;

                executor.submit(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    String response = finalLine.equals("Ping") ? "Pong" : finalLine;
                    out.println(response + " (" + msgNumber + ")");
                    System.out.println("Send: " + response + " (" + msgNumber + ")");
                });


                if (line.equals("foobar")) {
                    break;
                }
            }

            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);
            clientSocket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
