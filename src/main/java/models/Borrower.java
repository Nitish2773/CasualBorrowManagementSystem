package models;

public class Borrower {
	private int borrowerId;
	private int userId;
	private String firstName;
	private String lastName;
	private String contactNumber;
	private int totalBorrowed;
	private int totalReturned;

	public Borrower(int borrowerId, int userId, String firstName, String lastName, String contactNumber,
			int totalBorrowed, int totalReturned) {
		super();
		this.borrowerId = borrowerId;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNumber = contactNumber;
		this.totalBorrowed = totalBorrowed;
		this.totalReturned = totalReturned;
	}

	public Borrower() {
		super();
	}

	public int getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(int borrowerId) {
		this.borrowerId = borrowerId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public int getTotalBorrowed() {
		return totalBorrowed;
	}

	public void setTotalBorrowed(int totalBorrowed) {
		this.totalBorrowed = totalBorrowed;
	}

	public int getTotalReturned() {
		return totalReturned;
	}

	public void setTotalReturned(int totalReturned) {
		this.totalReturned = totalReturned;
	}

}