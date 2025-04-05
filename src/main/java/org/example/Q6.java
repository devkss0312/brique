package org.example;

import java.util.Arrays;
import java.util.Stack;

public class Q6 {
    public static void main(String[] args) {
        int[] towers1 = {6, 9, 5, 7, 4};
        testExample(towers1);

        int[] towers2 = {3, 9, 9, 3, 5, 7, 2};
        testExample(towers2);

        int[] towers3 = {1, 2, 3, 4, 5};
        testExample(towers3);

        int[] towers4 = {5, 4, 3, 2, 1};
        testExample(towers4);

        int[] towers5 = {10, 3, 8, 4, 6, 2, 9};
        testExample(towers5);
    }

    public static void testExample(int[] towers) {
        int[] result = solution(towers);
        System.out.println("입력 : " + Arrays.toString(towers));
        System.out.println("출력 : " + Arrays.toString(result));
        System.out.println("---------------------------------");
    }

    public static int[] solution(int[] towers) {
        int n = towers.length;
        int[] answer = new int[n];
        Stack<Node> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            // 스택이 비어있지 않고, 현재 탑의 높이보다 낮은 탑은 pop
            while (!stack.isEmpty() && stack.peek().h < towers[i]) {
                stack.pop();
            }

            if (stack.isEmpty()) {
                answer[i] = 0;
            } else {
                answer[i] = stack.peek().index + 1;
            }

            stack.push(new Node(i, towers[i]));
        }
        return answer;
    }
    static class Node {
        int index;
        int h;
        Node(int index, int h) {
            this.index = index;
            this.h = h;
        }
    }
}