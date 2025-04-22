package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class DBConnection {
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "Nitish@123";

	private static Connection con;
	private static PreparedStatement pmst;
	
	public static void createDatabase() {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter Database Name to Create: ");
			String dbName = sc.nextLine().trim();

			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			String sql = "CREATE DATABASE IF NOT EXISTS " + dbName;

			pmst = con.prepareStatement(sql);
			int i = pmst.executeUpdate();
			if (i > 0) {
				System.out.println("Database '" + dbName + "' created successfully.");
			} else {
				System.out.println("Database '" + dbName + "' already exists or couldn't be created.");
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection(String dbName) {
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL + dbName, USERNAME, PASSWORD);
			System.out.println("Connected to database: " + dbName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public static Connection getConnection() {
		if (con == null) {
			System.err.println("No DB connection. Call getConnection(dbName) first.");
			throw new IllegalStateException("No database selected.");
		}
		return con;
	}
}
