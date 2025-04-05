package org.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q1 {
    public static void main(String[] args) {

        ClassLoader classLoader = Q1.class.getClassLoader();


        // long startTime = System.currentTimeMillis();

        try (InputStream inputStream = classLoader.getResourceAsStream("sample.csv")) {
            if (inputStream == null) {
                System.err.println("Resource file 'sample.csv' not found.");
                return;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            int totalLines = 0;
            int calcLines = 0;
            List<String> calcResults = new ArrayList<>();
            List<String[]> errorRows = new ArrayList<>();

            final int LEN = br.readLine().split(",").length;
            String line;
            while ((line = br.readLine()) != null) {
                totalLines++;
                String[] fields = line.split(",");

                if (fields.length != LEN) {
                    errorRows.add(fields);
                    continue;
                }

                boolean validRow = true;
                double[] numbers = new double[LEN];
                double min = Double.MAX_VALUE;
                double max = Double.MIN_VALUE;
                double sum = 0.0;

                for (int i = 0; i < LEN; i++) {
                    String field = fields[i];
                    try {
                        double num = Double.parseDouble(field);
                        numbers[i] = num;
                        min = Double.min(min, num);
                        max = Double.max(max, num);
                        sum += num;
                    } catch (NumberFormatException e) {
                        validRow = false;
                        break;
                    }
                }

                if (validRow) {
                    calcLines++;

                    double avg = sum / LEN;
                    double mean = 0.0;
                    double M2 = 0.0;
                    for (int i = 0; i < LEN; i++) {
                        double x = numbers[i];
                        double delta = x - mean;
                        mean += delta / (i + 1);
                        double delta2 = x - mean;
                        M2 += delta * delta2;
                    }
                    double variance = (LEN > 1) ? M2 / (LEN - 1) : 0.0;
                    double std = Math.sqrt(variance);

                    Arrays.sort(numbers);
                    double median = (numbers[LEN / 2 - 1] + numbers[LEN / 2]) / 2.0;

                    String resultLine = String.format("%.1f %.1f %.1f %.1f %.15f %.1f",
                            min, max, sum, avg, std, median);
                    calcResults.add(resultLine);
                } else {
                    errorRows.add(fields);
                }
            }
            br.close();

            // 계산 결과 출력
            for (String result : calcResults) {
                System.out.println(result);
            }
            System.out.println("---------------------------------------------");
            System.out.println("The total number of lines: " + totalLines);
            System.out.println("The calculated lines: " + calcLines);
            System.out.println("Error rows:");
            for (String[] errorRow : errorRows) {
                System.out.println(Arrays.toString(errorRow));
            }
        } catch (IOException e) {
            System.err.println("Error reading resource file: " + e.getMessage());
            e.printStackTrace();
        }


//        long endTime = System.currentTimeMillis();
//        System.out.println("Execution time: " + (endTime - startTime) + " ms");


    }
}