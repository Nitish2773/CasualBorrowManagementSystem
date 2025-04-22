package storage;

import models.User;
import db.DBConnection;
import java.sql.*;

public class UserStorage {

	// Register new user
	public static boolean registerUser(User user) {
		Connection con = null;
		PreparedStatement pmst;
		try {
			con = DBConnection.getConnection();
			String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
			pmst = con.prepareStatement(sql);
			pmst.setString(1, user.getUsername());
			pmst.setString(2, user.getPassword());
			int result = pmst.executeUpdate();

			return result > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Login user by checking UserName and password
	public static User loginUser(String username, String password) {
		Connection con = null;
		PreparedStatement pstmt;
		ResultSet rs;
		try {
			con = DBConnection.getConnection();
			String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return new User(
					rs.getInt("user_id"),
					rs.getString("username"),
					rs.getString("password")
				);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
	}
}
