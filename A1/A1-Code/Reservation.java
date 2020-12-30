package Assigment_1;

public abstract class Reservation {
	// Fields //
	private String name;				// name of the client 
	
	// Constructors //
	/**
	 * Constructor for the Reservation Class
	 * @param name Client's name
	 */
	public Reservation(String name) {
		this.name = name;
	}
	
	// Methods //
	/**
	 * reservationName Method
	 * @return The name on the reservation
	 */
	public final String reservationName() {
		return this.name;
	}
	
	/**
	 * Abstract method getCost
	 * @return cost
	 */	
	public abstract int getCost();
	
	/**
	 * Abstract method equal
	 * @param obj some object
	 * @return if the object are equal
	 */
	public abstract boolean equals(Object obj);	
}