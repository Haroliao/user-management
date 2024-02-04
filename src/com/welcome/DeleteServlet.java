package com.welcome;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String phoneLocation = request.getParameter("phoneLocation");
        String registrationDate = request.getParameter("registrationDate");
        String organization = request.getParameter("organization");
        String role = request.getParameter("role");


        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UserLists", "root", "123456");
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE Username=? AND Name=? AND PhoneNum=? AND PhoneNumLocation=? AND RegistrationDATE=? AND Organization=? AND Role=?")) {

            Class.forName("com.mysql.cj.jdbc.Driver");
            stmt.setString(1, username);
            stmt.setString(2, name);
            stmt.setString(3, phone);
            stmt.setString(4, phoneLocation);
            stmt.setString(5, registrationDate);
            stmt.setString(6, organization);
            stmt.setString(7, role);

            int deleted = stmt.executeUpdate();
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");

           if(deleted == 0) {
                response.getWriter().write("Error: No user deleted.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
}

