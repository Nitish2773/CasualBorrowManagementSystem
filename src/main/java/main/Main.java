package main;

import java.util.Scanner;

import services.BorrowerService;
import services.ItemService;
import services.UserService;
import db.DBConnection;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome to Casual Borrower Management System");

		System.out.print("Enter database name to connect: ");
		String dbName = sc.nextLine().trim();

		DBConnection.getConnection(dbName);

		int choice;
		do {
			System.out.println("\n--- Main Menu ---");
			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("3. Add Borrower");
			System.out.println("4. Lend Item");
			System.out.println("5. Mark Item as Returned");
			System.out.println("6. View Overdue Items");
			System.out.println("7. View Trust Scores");
			System.out.println("8. Search Borrowers by Name");
			System.out.println("9. Search Items by Name");
			System.out.println("10. View All Borrowed Items");
			System.out.println("11. Delete Borrower");
			System.out.println("12. Delete Item");
			System.out.println("13. View All Borrowers");
			System.out.println("14. Exit");
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1:
				System.out.print("Enter username: ");
				String regUsername = sc.nextLine();
				System.out.print("Enter password: ");
				String regPassword = sc.nextLine();
				UserService.register(regUsername, regPassword);
				break;

			case 2:
				System.out.print("Enter username: ");
				String loginUsername = sc.nextLine();
				System.out.print("Enter password: ");
				String loginPassword = sc.nextLine();
				UserService.login(loginUsername, loginPassword);
				break;

			case 3:

				System.out.print("Enter borrower's first name: ");
				String firstName = sc.nextLine();
				System.out.print("Enter borrower's last name: ");
				String lastName = sc.nextLine();
				System.out.print("Enter borrower's contact number: ");
				String contactNumber = sc.nextLine();
				BorrowerService.addBorrower(firstName, lastName, contactNumber);
				break;

			case 4:

				System.out.print("Enter item name: ");
				String itemName = sc.nextLine();
				System.out.print("Enter item description: ");
				String description = sc.nextLine();
				System.out.print("Enter borrower ID: ");
				int borrowerId = sc.nextInt();
				sc.nextLine();
				System.out.print("Enter due date (YYYY-MM-DD): ");
				String dueDate = sc.nextLine();
				ItemService.addBorrowedItem(itemName, description, borrowerId, dueDate);
				break;

			case 5:

				System.out.print("Enter item ID to mark as returned: ");
				int itemId = sc.nextInt();
				sc.nextLine();
				ItemService.markAsItemReturned(itemId);
				break;

			case 6:

				ItemService.viewOverdueItems();
				break;

			case 7:

				ItemService.showTrustScores();
				break;
			case 8:
				System.out.print("Enter search term for borrower (first or last name): ");
				String borrowerSearchTerm = sc.nextLine();
				BorrowerService.searchBorrowersByName(borrowerSearchTerm);
				break;

			case 9:
				System.out.print("Enter search term for item name: ");
				String itemSearchTerm = sc.nextLine();
				ItemService.searchItemsByName(itemSearchTerm);
				break;
			case 10:

				ItemService.viewAllBorrowedItems();
				break;
			case 11:
				System.out.print("Enter Borrower ID to delete: ");
				int deleteBorrowerId = sc.nextInt();
				sc.nextLine();
				BorrowerService.deleteBorrower(deleteBorrowerId);
				break;

			case 12:
				System.out.print("Enter Item ID to delete: ");
				int deleteItemId = sc.nextInt();
				sc.nextLine();
				ItemService.deleteItem(deleteItemId);
				break;
			case 13:
				BorrowerService.listBorrowers();
				break;

			case 14:
				System.out.println("Exiting. Have a great day!");
				break;

			default:
				System.out.println("Invalid choice. Please select 1-14.");
			}

		} while (choice != 14);

		sc.close();
	}
}
