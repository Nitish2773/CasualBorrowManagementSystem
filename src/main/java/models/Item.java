package models;

import java.util.Date;

public class Item {
    private int itemId;
    private String itemName;
    private int borrowerId;
    private String description;
    private boolean available;
    private Date dueDate;
    private Date dateBorrowed;
    private Date dateReturned;
    
	public Item() {
		super();
	}
	public Item(int itemId, String itemName, int borrowerId, String description, boolean available, Date dueDate,
			Date dateBorrowed, Date dateReturned) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.borrowerId = borrowerId;
		this.description = description;
		this.available = available;
		this.dueDate = dueDate;
		this.dateBorrowed = dateBorrowed;
		this.dateReturned = dateReturned;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getBorrowerId() {
		return borrowerId;
	}
	public void setBorrowerId(int borrowerId) {
		this.borrowerId = borrowerId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Date getDateBorrowed() {
		return dateBorrowed;
	}
	public void setDateBorrowed(Date dateBorrowed) {
		this.dateBorrowed = dateBorrowed;
	}
	public Date getDateReturned() {
		return dateReturned;
	}
	public void setDateReturned(Date dateReturned) {
		this.dateReturned = dateReturned;
	}
    
}
