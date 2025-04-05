package org.example;

import java.sql.*;

public class Q3 {
    public static void main(String[] args) {
        // DB 접속 정보
        String url = "jdbc:mariadb://codingtest.brique.kr:3306/employees";
        String username = "codingtest";
        String password = "12brique!@";


        String query = "SELECT e.emp_no, e.first_name, e.last_name, e.gender, e.hire_date, " +
                "       d.dept_name, t.title, MAX(s.salary) AS max_salary " +
                "FROM employees e " +
                "JOIN salaries s ON e.emp_no = s.emp_no " +
                "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                "JOIN departments d ON de.dept_no = d.dept_no " +
                "JOIN titles t ON e.emp_no = t.emp_no " +
                "WHERE e.hire_date >= '2000-01-01' " +
                "GROUP BY e.emp_no, e.first_name, e.last_name, e.gender, e.hire_date, d.dept_name, t.title " +
                "ORDER BY e.first_name";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // 결과 출력 헤더 (총 8개 열)
            System.out.printf("%-10s %-15s %-15s %-8s %-12s %-15s %-20s %-10s\n",
                    "emp_no", "first_name", "last_name", "gender", "hire_date", "dept_name", "title", "max_salary");
            System.out.println("-------------------------------------------------------------------------------------------");

            // 결과 행 출력
            while (rs.next()) {
                int empNo = rs.getInt("emp_no");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String gender = rs.getString("gender");
                Date hireDate = rs.getDate("hire_date");
                String deptName = rs.getString("dept_name");
                String title = rs.getString("title");
                int maxSalary = rs.getInt("max_salary");

                System.out.printf("%-10d %-15s %-15s %-8s %-12s %-15s %-20s %-10d\n",
                        empNo, firstName, lastName, gender, hireDate, deptName, title, maxSalary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
