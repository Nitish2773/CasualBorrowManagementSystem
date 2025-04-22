package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.DBConnection;
import models.User;
import storage.UserStorage;

public class UserService {

    public static void register(String username, String password) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);

        boolean success = UserStorage.registerUser(newUser);

        if (success) {
            System.out.println("User Registered Successfully");
        } else {
            System.out.println("Registration failed. Try a different username.");
        }
    }

    public static Integer loggedInUserId = null;

    public static void login(String username, String password) throws Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            loggedInUserId = rs.getInt("user_id"); // Save logged in user's ID
            System.out.println("Login successful.");
        } else {
            System.out.println("Login failed.");
        }
    }

    public static void logout() {
        loggedInUserId = null;
        System.out.println("Logged out.");
    }
}
