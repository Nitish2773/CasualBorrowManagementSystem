package services;

import java.sql.*;
import db.DBConnection;

public class BorrowerService {
	
	public static void addBorrower(String firstName, String lastName, String contactNumber) throws Exception {
        if (UserService.loggedInUserId == null) {
            System.out.println("Please login first to add a borrower.");
            return;
        }

        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO borrowers (user_id, first_name, last_name, contact_number, total_borrowed, total_returned) VALUES (?, ?, ?, ?, 0, 0)"
        );

        stmt.setInt(1, UserService.loggedInUserId);
        stmt.setString(2, firstName);
        stmt.setString(3, lastName);
        stmt.setString(4, contactNumber);

        stmt.executeUpdate();
        System.out.println("Borrower added successfully!");
    }

    public static int getBorrowerIdByName(String firstName, String lastName) throws Exception {
        Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT borrower_id FROM borrowers WHERE first_name = ? AND last_name = ?");
        stmt.setString(1, firstName);
        stmt.setString(2, lastName);
        ResultSet rs = null;
        
        try {
            rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("borrower_id");
                return id;
            }
            System.out.println(">> Borrower not found.");
            return -1;
        } catch (SQLException e) {
            System.out.println(">> Error while retrieving borrower ID: " + e.getMessage());
            throw new Exception("Error while retrieving borrower ID", e);
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    public static void searchBorrowersByName(String searchTerm) throws SQLException {
        Connection conn = DBConnection.getConnection();
        String query = "SELECT * FROM borrowers WHERE first_name LIKE ? OR last_name LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, "%" + searchTerm + "%");
        stmt.setString(2, "%" + searchTerm + "%");
        ResultSet rs = stmt.executeQuery();

        System.out.println("\n>> Search Results (Borrowers):");
        while (rs.next()) {
            System.out.println("Borrower ID: " + rs.getInt("borrower_id") +
                    " | Name: " + rs.getString("first_name") + " " + rs.getString("last_name") +
                    " | Contact: " + rs.getString("contact_number"));
        }
    }
    public static void deleteBorrower(int borrowerId) throws Exception {
        Connection conn = DBConnection.getConnection();

        PreparedStatement checkStmt = conn.prepareStatement(
            "SELECT COUNT(*) AS cnt FROM items WHERE borrower_id = ? AND date_returned IS NULL"
        );
        checkStmt.setInt(1, borrowerId);
        ResultSet rs = checkStmt.executeQuery();
        if (rs.next() && rs.getInt("cnt") > 0) {
            System.out.println("Cannot delete borrower: They still have unreturned items.");
            return;
        }

        PreparedStatement stmt = conn.prepareStatement("DELETE FROM borrowers WHERE borrower_id = ?");
        stmt.setInt(1, borrowerId);
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Borrower deleted successfully.");
        } else {
            System.out.println("Borrower not found.");
        }
    }
    
    public static void listBorrowers() {
        try (Connection conn = DBConnection.getConnection(); 
             Statement stmt = conn.createStatement()) {

            String query = "SELECT * FROM borrowers";
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("\n--- Borrower List ---");
            while (rs.next()) {
                int id = rs.getInt("borrower_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String contact = rs.getString("contact_number");
                int totalBorrowed = rs.getInt("total_borrowed");
                int totalReturned = rs.getInt("total_returned");

                System.out.printf("ID: %d | Name: %s %s | Contact: %s | Borrowed: %d | Returned: %d\n",
                                  id, firstName, lastName, contact, totalBorrowed, totalReturned);
            }

        } catch (SQLException e) {
            System.out.println("Error listing borrowers: " + e.getMessage());
        }
    }


}
