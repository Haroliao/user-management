package com.welcome;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import org.json.JSONObject;
import org.json.JSONArray;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetData
 */
@WebServlet("/GetData")
public class GetData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	        JSONArray usersArray = new JSONArray();
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/UserLists", "root", "123456");
	             PreparedStatement stmt = conn.prepareStatement("SELECT Username, Name, PhoneNum, PhoneNumLocation, RegistrationDATE, Organization, Role FROM Users");
	             ResultSet rs = stmt.executeQuery()) {
	        	 while (rs.next()) {
	                 JSONObject userObj = new JSONObject();
	                 userObj.put("username", rs.getString("Username"));
	                 userObj.put("name", rs.getString("Name"));
	                 userObj.put("phone", rs.getString("PhoneNum"));
	                 userObj.put("phoneLocation", rs.getString("PhoneNumLocation"));
	                 userObj.put("registrationDate", rs.getString("RegistrationDATE"));
	                 userObj.put("organization", rs.getString("Organization"));
	                 userObj.put("role", rs.getString("Role"));
	       
	                 usersArray.put(userObj);
	             }
	        	 response.getWriter().write(usersArray.toString());
	        }
	        	
	        }catch (Exception e) {
	            e.printStackTrace();
	            response.getWriter().write("[]");
	        }
	    }
}

		// TODO Auto-generated method stub
		
