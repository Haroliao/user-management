
package com.welcome;
/**
 * Servlet implementation class Upload
 */
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.welcome.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;

@WebServlet("/Upload")

public class Upload extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
      
        // Parse JSON data from the request
        String jsonData = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

        // Convert JSON data to an array of User objects
        User[] users = parseJsonToUserArray(jsonData);

        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/UserLists", "root", "123456");

           
            for (User user : users) {
            	 
            	String formattedDate = convertDate(user.getRegistrationDate());
                // Check if the user already exists
                PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM Users WHERE Username = ?");
                selectStatement.setString(1, user.getUsername());

                ResultSet rs = selectStatement.executeQuery();
                if (rs.next()) {
                    // User exists, perform an update
                    PreparedStatement updateStatement = connection.prepareStatement(
                        "UPDATE Users SET Name=?, PhoneNum=?, PhoneNumLocation=?, RegistrationDATE=?, Organization=?, Role=? WHERE Username=?");
                    updateStatement.setString(1, user.getName());
                    updateStatement.setString(2, user.getPhoneNum());
                    updateStatement.setString(3, user.getPhoneNumLocation());
                    updateStatement.setString(4, formattedDate);
                    updateStatement.setString(5, user.getOrganization());
                    updateStatement.setString(6, user.getRole());
                    updateStatement.setString(7, user.getUsername());
                    updateStatement.executeUpdate();
                    updateStatement.close();
                } else {
                    // User doesn't exist, perform an insert
                    PreparedStatement insertStatement = connection.prepareStatement(
                        "INSERT INTO Users (Username, Name, PhoneNum, PhoneNumLocation, RegistrationDATE, Organization, Role) VALUES (?, ?, ?, ?, ?, ?, ?)");
                    insertStatement.setString(1, user.getUsername());
                    insertStatement.setString(2, user.getName());
                    insertStatement.setString(3, user.getPhoneNum());
                    insertStatement.setString(4, user.getPhoneNumLocation());
                    insertStatement.setString(5, formattedDate);
                    insertStatement.setString(6, user.getOrganization());
                    insertStatement.setString(7, user.getRole());
                    insertStatement.executeUpdate();
                    insertStatement.close();
                }
                selectStatement.close();
            }
            connection.close();
            out.println("{\"message\": \"Users processed successfully.\"}");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("{\"error\": \"An error occurred while processing the request.\"}");
        }
    }

    // Implement a method to parse JSON data into an array of User objects
    private User[] parseJsonToUserArray(String jsonData) {
        Gson gson = new Gson();
        return gson.fromJson(jsonData, User[].class);
    }
    private String convertDate(String registrationDate) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(registrationDate, inputFormatter);
            return outputFormatter.format(date);
        } catch (DateTimeParseException e) {
            
            e.printStackTrace();
            return null;
        }
    }
    
}
