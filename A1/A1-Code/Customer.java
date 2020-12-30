package Assigment_1;

public class Customer {
	
	// Fields //
	private String name;
	private int balance;
	private Basket basket;
	
	// Constructors //
	/**
	 * Customer Class Constructor
	 * A constructor that takes as input a String indicating the name of the customer,
		and an int representing their initial balance.
	 * @param name name of customer
	 * @param balance initial balance
	 */
	public Customer(String name, int balance) {
		this.name = name;
		this.balance = balance;
		this.basket = new Basket();
	}
	
	/**
	 * getName Method
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * getBalance Method
	 * @return balance
	 */
	public int getBalance() {
		return this.balance;
	}
	
	private void setBalance(int amount) {
		this.balance = amount;
	}
	
	/**
	 * addFund Method
	 * addFunds method which takes an int as input representing the amount of cents
		to be added to the balance of the customer. If the input received is negative, the
		method should throw an IllegalArgumentException with an appropriate message.
		Otherwise, the method will simply update the balance and return the new balance
		in cents.
	 * @param amount to add to balance in cents
	 */
	public int addFunds(int amount) {
		if(amount < 0) throw new IllegalArgumentException("Nagatibe Number");
		else setBalance(this.getBalance()+amount);
		return this.getBalance();
	}

	/** 
	 * addToBasket Method
	 * addToBasket method which takes a Reservation as input and adds it to the
		basket of the customer if the name on the reservation matches the name of the customer.
		If the method is successful it returns the number of reservations in the basket of the customer.
	    Otherwise, the method throws an IllegalArgumentException.
	 * @param resv Reservation to add
	 * @return number of reservation in Basket
	 */
	
	public int addToBasket(Reservation resv) {
		if(resv.reservationName().equals(this.name)) {
			this.basket.add(resv);
			return this.basket.getNumOfReservations();
		}
		else throw new IllegalArgumentException("Wrong Reservation");
	}
	public Basket getBasket() {
		return basket;
	}
	
	/**
	 * addToBasket Method
	 * addToBasket method which takes a Hotel, a String representing a room type,
		an int representing the number of nights, and a boolean representing whether or not
		the customer wants breakfast to be included. The method adds the corresponding
		reservation to the basket of the customer and returns the number of reservations
		that are now in the basket of this customer.		
	 * @param hotel
	 * @param room
	 * @param nights
	 * @param breakfast
	 */
	public int addToBasket(Hotel hotel, String room, int nights, boolean breakfast) {
		try {
		if (breakfast) {
			this.basket.add(new BnBReservation(this.name, hotel, room, nights));
		}
		else {
			this.basket.add(new HotelReservation(this.name, hotel, room, nights));
			}
		}
		catch (IllegalArgumentException e) {
		}
		finally {
			return this.basket.getNumOfReservations();
		}
	}

	/**
	 * addToBasketMethod
	 * addToBasket method which takes two Airports as input. The method adds the
		corresponding reservation to the basket of the customer and returns the number of
		reservations that are now in their basket, whether or not the flight reservation was
		created successfully.
	 * @param a Departure Airport
	 * @param b Arrival Airport
	 */
	public int addToBasket(Airport a, Airport b) {
		try {
			this.basket.add(new FlightReservation(this.name, a,b));
		}
		catch(IllegalArgumentException e) {}
		finally {
			return this.basket.getNumOfReservations();
		}	
	}
	
	/**
	 * removeFromBasket Method
	 * A removeFromBasket method which takes a Reservation as input and removes it
		from the basket of the customer. The method returns a boolean indicating whether
		of not the operation was successful.
	 * @param resv Reservation to remove
	 * @return true on success
	 */
	public boolean removeFromBasket(Reservation resv) {
		return this.basket.remove(resv);
	}
	
	/**
	 * checkOut method
	  	which takes no input. If the customer’s balance is not enough to
		cover the total cost of their basket, then the method throws an IllegalStateException.
		Otherwise, the customer is charged the total cost of the basket, the basket is
		cleared, and balance left is returned.
	 * @return new customer balance
	 */
	public int checkOut() {
		int total_cost = basket.getTotalCost();
		if(this.balance < total_cost) {
			throw new IllegalStateException("Low balance");
		}
		else {
			this.basket.clear();
			setBalance(balance-total_cost);
			return this.getBalance();
		}
	}
	
}