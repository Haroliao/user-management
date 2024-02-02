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
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String PhoneLocation= request.getParameter("phoneLocation");
        String registrationDate=request.getParameter("registrationDate");
        String organization=request.getParameter("organization");
        String role=request.getParameter("role");
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UserLists", "root", "123456");
            String sql = "INSERT INTO Users (Username, Name, PhoneNum, PhoneNumLocation, RegistrationDATE, Organization, Role) VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE Name = ?, PhoneNum = ?, PhoneNumLocation = ?, RegistrationDATE = ?, Organization = ?, Role = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, name);
            stmt.setString(3, phone);
            stmt.setString(4, PhoneLocation);
            stmt.setString(5, registrationDate);
            stmt.setString(6, organization);
            stmt.setString(7, role);
            // For the UPDATE part
            stmt.setString(8, name);
            stmt.setString(9, phone);
            stmt.setString(10, PhoneLocation);
            stmt.setString(11, registrationDate);
            stmt.setString(12, organization);
            stmt.setString(13, role);
            stmt.executeUpdate();
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Success");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
