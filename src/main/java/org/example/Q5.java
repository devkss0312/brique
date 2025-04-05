package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Q5 {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://codingtest.brique.kr:8080/random"))
                .GET()
                .build();

        Map<String, Integer> frequencyMap = new HashMap<>();
        int totalCalls = 0;

        // 100번 호출
        for (int i = 0; i < 100; i++) {
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    String body = response.body().trim();
                    frequencyMap.put(body, frequencyMap.getOrDefault(body, 0) + 1);
                    totalCalls++;
                } else {
                    System.out.println("Error: 응답 코드 " + response.statusCode());
                }
            } catch (IOException | InterruptedException e) {
                System.err.println("요청 중 예외 발생: " + e.getMessage());
            }
        }

        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(frequencyMap.entrySet());
        sortedEntries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        for (Map.Entry<String, Integer> entry : sortedEntries) {
            System.out.println("count: " + entry.getValue() + " " + entry.getKey());
        }

        System.out.println("\nTotal Count: " + totalCalls);
    }
}
