package com.johnnking.csrf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

	public final static String DB_DRIVER= "org.hsqldb.jdbcDriver";
	public final static String DB_CONN = "jdbc:hsqldb:mem:csrf-jee-example-db";
	public final static String DB_USER = "SA";
	public final static String DB_PASS = "";
	
	/*
	 * Create the User table
	 */
	public static void init() throws SQLException, ClassNotFoundException {
		
		Class.forName(DB_DRIVER);
		Connection conn = DriverManager.getConnection(DB_CONN, DB_USER, DB_PASS);

		try {
			PreparedStatement stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS users (name varchar(255))");						
			
			try {
				stmt.execute();
				
				if (UserManager.getUsers().size() == 0) {
					UserManager.addUser(new User("John"));
				}
			    
			} catch (SQLException e ) {
				throw e;
				
			} finally {
				stmt.close();
			}
			
		} catch (SQLException e ) {
			throw e;
			
		} finally {
		    conn.close();
		}
	}
	
	/*
	 * Get a list of all users
	 */
	public static List<User> getUsers() throws SQLException, ClassNotFoundException {
		List<User> result = new ArrayList<User>();
		
		Class.forName(DB_DRIVER);
		Connection conn = DriverManager.getConnection(DB_CONN, DB_USER, DB_PASS);

		try {
			PreparedStatement stmt = conn.prepareStatement("select * from users");
			
			try {
				ResultSet rs = stmt.executeQuery();
			
				try {
				    while (rs.next()) {
				    	result.add(new User(rs.getString("name")));
			    	}
				    
				} catch (SQLException e ) {
					throw e;
					
				} finally {
					rs.close();
				} 
			    
			} catch (SQLException e ) {
				throw e;
				
			} finally {
				stmt.close();
			}
			
		} catch (SQLException e ) {
			throw e;
			
		} finally {
		    conn.close();
		}
			
		return result;
	}
	
	
	/*
	 * Get a particular user
	 */
	public static User getUser(String name) throws SQLException, ClassNotFoundException {
		User result = null;
		
		Class.forName(DB_DRIVER);
		Connection conn = DriverManager.getConnection(DB_CONN, DB_USER, DB_PASS);

		try {
			PreparedStatement stmt = conn.prepareStatement("select * from users where name = ?");
			
			try {
				stmt.setString(1, name);
				ResultSet rs = stmt.executeQuery();
			
				try {
					if (rs.next()) {
				    	result = new User(rs.getString("name"));
					}
				    
				} catch (SQLException e ) {
					throw e;
					
				} finally {
					rs.close();
				} 
			    
			} catch (SQLException e ) {
				throw e;
				
			} finally {
				stmt.close();
			}
			
		} catch (SQLException e ) {
			throw e;
			
		} finally {
		    conn.close();
		}
			
		return result;
	}	

	/*
	 * Add a user
	 */
	public static void addUser(User user) throws SQLException, ClassNotFoundException {
			
		Class.forName(DB_DRIVER);
		Connection conn = DriverManager.getConnection(DB_CONN, DB_USER, DB_PASS);

		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO users VALUES ?");
			
			try {
				stmt.setString(1, user.getName());
				stmt.execute();
			    
			} catch (SQLException e ) {
				throw e;
				
			} finally {
				stmt.close();
			}
			
		} catch (SQLException e ) {
			throw e;
			
		} finally {
		    conn.close();
		}
	}
}
