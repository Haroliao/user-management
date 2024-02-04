package com.welcome;

import java.sql.Connection;


import java.io.IOException;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddServlet
 */
@WebServlet("/AddServlet")
public class AddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operationType = request.getParameter("operationType"); // This parameter should be sent from the frontend
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String phoneLocation = request.getParameter("phoneLocation");
        String registrationDate = request.getParameter("registrationDate");
        String organization = request.getParameter("organization");
        String role = request.getParameter("role");
        if (operationType == null || username == null || name == null || phone == null || 
                phoneLocation == null || registrationDate == null || organization == null || role == null ||
                operationType.isEmpty() || username.isEmpty() || name.isEmpty() || phone.isEmpty() || 
                phoneLocation.isEmpty() || registrationDate.isEmpty() || organization.isEmpty() || role.isEmpty()) {
                
                response.getWriter().write("Error: Information incomplete.");
                return;
            }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UserLists", "root", "123456");

            if ("insert".equals(operationType)) {
                // Check if the username already exists before inserting
                String checkSql = "SELECT COUNT(*) FROM Users WHERE Username = ?";
                stmt = conn.prepareStatement(checkSql);
                stmt.setString(1, username);
                rs = stmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    // Username exists, return an error
                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("Error: Username already exists.");
                    return;
                }

                // Username does not exist, insert a new record
                String sql = "INSERT INTO Users (Username, Name, PhoneNum, PhoneNumLocation, RegistrationDATE, Organization, Role) VALUES (?, ?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, name);
                stmt.setString(3, phone);
                stmt.setString(4, phoneLocation);
                stmt.setString(5, registrationDate);
                stmt.setString(6, organization);
                stmt.setString(7, role);
            } else if ("update".equals(operationType)) {
                // Update the existing user's information
                String sql = "UPDATE Users SET Name = ?, PhoneNum = ?, PhoneNumLocation = ?, RegistrationDATE = ?, Organization = ?, Role = ? WHERE Username = ?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, phone);
                stmt.setString(3, phoneLocation);
                stmt.setString(4, registrationDate);
                stmt.setString(5, organization);
                stmt.setString(6, role);
                stmt.setString(7, username);
            } else {
                // Invalid operation type
                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("Error: Invalid operation type.");
                return;
            }

            stmt.executeUpdate();
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Success");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
