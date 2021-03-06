package com.johnnking.csrf;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if ((username != null) && (! username.equals("")) && (password != null) && (! password.equals(""))) {
			
			try {
				if (UserManager.getUser(username) != null) {
					if (username.equals(password)) {
						request.getSession(true).setAttribute("username", username);
						
						response.sendRedirect("userForm.jsp");
						return;
					}
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		response.sendRedirect("login.jsp?error");
	}
}
