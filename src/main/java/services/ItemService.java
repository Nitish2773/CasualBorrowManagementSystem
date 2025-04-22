package services;

import java.sql.*;
import db.DBConnection;

public class ItemService {

	public static void addBorrowedItem(String itemName, String description, int borrowerId, String dueDate)
			throws Exception {
		Connection conn = DBConnection.getConnection();

		if (borrowerId == -1) {
			System.out.println(">> Borrower not found. Add them first.");
			return;
		}
		PreparedStatement stmt = conn.prepareStatement(
				"INSERT INTO items (item_name, borrower_id, description, available, date_borrowed, due_date) "
						+ "VALUES (?, ?, ?, FALSE, CURDATE(), ?)");
		stmt.setString(1, itemName);
		stmt.setInt(2, borrowerId);
		stmt.setString(3, description);
		stmt.setString(4, dueDate);
		stmt.executeUpdate();

		PreparedStatement updateBorrowed = conn
				.prepareStatement("UPDATE borrowers SET total_borrowed = total_borrowed + 1 WHERE borrower_id = ?");
		updateBorrowed.setInt(1, borrowerId);
		updateBorrowed.executeUpdate();

		System.out.println(">> Item lent: " + itemName + " to borrower ID " + borrowerId);
	}

	public static void markAsItemReturned(int itemId) throws Exception {
		Connection conn = DBConnection.getConnection();

		PreparedStatement getBorrower = conn.prepareStatement("SELECT borrower_id FROM items WHERE item_id = ?");
		getBorrower.setInt(1, itemId);
		ResultSet rs = getBorrower.executeQuery();

		if (!rs.next()) {
			System.out.println(">> Item not found.");
			return;
		}

		int borrowerId = rs.getInt("borrower_id");

		PreparedStatement updateItem = conn
				.prepareStatement("UPDATE items SET date_returned = CURDATE(), available = TRUE WHERE item_id = ?");
		updateItem.setInt(1, itemId);
		updateItem.executeUpdate();

		PreparedStatement checkBorrowed = conn
				.prepareStatement("SELECT total_borrowed, total_returned FROM borrowers WHERE borrower_id = ?");
		checkBorrowed.setInt(1, borrowerId);
		ResultSet rsBorrowed = checkBorrowed.executeQuery();

		if (rsBorrowed.next()) {
			int totalBorrowed = rsBorrowed.getInt("total_borrowed");
			int totalReturned = rsBorrowed.getInt("total_returned");

			if (totalReturned < totalBorrowed) {
				PreparedStatement updateReturned = conn.prepareStatement(
						"UPDATE borrowers SET total_returned = total_returned + 1 WHERE borrower_id = ?");
				updateReturned.setInt(1, borrowerId);
				updateReturned.executeUpdate();
			} else {
				System.out.println(">> Note: total_returned already equals total_borrowed. Skipping increment.");
			}
		}

		System.out.println(">> Item ID " + itemId + " marked as returned.");
	}

	public static void viewOverdueItems() throws Exception {
		Connection conn = DBConnection.getConnection();
		PreparedStatement stmt = conn
				.prepareStatement("SELECT item_id, item_name, first_name, last_name, date_borrowed, due_date, "
						+ "DATEDIFF(CURDATE(), due_date) AS days_overdue " + "FROM items "
						+ "JOIN borrowers ON items.borrower_id = borrowers.borrower_id "
						+ "WHERE date_returned IS NULL AND due_date IS NOT NULL AND due_date < CURDATE()");

		ResultSet rs = stmt.executeQuery();

		System.out.println("\n>> Overdue Items (Past Due Date):\n");
		boolean found = false;

		while (rs.next()) {
			found = true;
			System.out.println("Item ID: " + rs.getInt("item_id") + "\n   Item: " + rs.getString("item_name")
					+ "\n   Borrower: " + rs.getString("first_name") + " " + rs.getString("last_name")
					+ "\n   Borrowed On: " + rs.getDate("date_borrowed") + "\n   Due Date: " + rs.getDate("due_date")
					+ "\n   Days Overdue: " + rs.getInt("days_overdue")
					+ "\n--------------------------------------------");
		}

		if (!found) {
			System.out.println("No overdue items!");
		}
	}

	public static void showTrustScores() throws Exception {
		Connection conn = DBConnection.getConnection();

		PreparedStatement stmt = conn.prepareStatement("SELECT first_name, last_name, total_borrowed, total_returned, "
				+ "LEAST(ROUND((total_returned / total_borrowed) * 100, 2), 100) AS trust_score "
				+ "FROM borrowers WHERE total_borrowed > 0");

		ResultSet rs = stmt.executeQuery();

		System.out.println("\n>> Borrower Trust Scores:");
		while (rs.next()) {
			System.out.println("Name: " + rs.getString("first_name") + " " + rs.getString("last_name") + " | Borrowed: "
					+ rs.getInt("total_borrowed") + " | Returned: " + rs.getInt("total_returned") + " | Trust Score: "
					+ rs.getDouble("trust_score") + "%");
		}
	}

	public static void searchItemsByName(String searchTerm) throws SQLException {
		Connection conn = DBConnection.getConnection();
		String query = "SELECT * FROM items WHERE item_name LIKE ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, "%" + searchTerm + "%");
		ResultSet rs = stmt.executeQuery();

		System.out.println("\n>> Search Results (Items):");
		while (rs.next()) {
			System.out.println("Item ID: " + rs.getInt("item_id") + " | Item: " + rs.getString("item_name")
					+ " | Borrower ID: " + rs.getInt("borrower_id"));
		}
	}

	public static void viewAllBorrowedItems() throws Exception {
		Connection conn = DBConnection.getConnection();

		PreparedStatement stmt = conn
				.prepareStatement("SELECT borrowers.first_name, borrowers.last_name, items.item_name " + "FROM items "
						+ "JOIN borrowers ON items.borrower_id = borrowers.borrower_id "
						+ "WHERE items.available = FALSE");
		ResultSet rs = stmt.executeQuery();

		System.out.println("\n>> All Borrowed Items:");
		while (rs.next()) {
			String borrowerName = rs.getString("first_name") + " " + rs.getString("last_name");
			String itemName = rs.getString("item_name");
			System.out.println("Borrower: " + borrowerName + " | Item: " + itemName);
		}
	}

	public static void deleteItem(int itemId) throws Exception {
		Connection conn = DBConnection.getConnection();

		PreparedStatement checkStmt = conn
				.prepareStatement("SELECT * FROM items WHERE item_id = ? AND date_returned IS NULL");
		checkStmt.setInt(1, itemId);
		ResultSet rs = checkStmt.executeQuery();
		if (rs.next()) {
			System.out.println("Warning: This item has not been returned yet. Proceed with caution.");
		}

		PreparedStatement stmt = conn.prepareStatement("DELETE FROM items WHERE item_id = ?");
		stmt.setInt(1, itemId);
		int rowsAffected = stmt.executeUpdate();

		if (rowsAffected > 0) {
			System.out.println("Item deleted successfully.");
		} else {
			System.out.println("Item not found.");
		}
	}

}
