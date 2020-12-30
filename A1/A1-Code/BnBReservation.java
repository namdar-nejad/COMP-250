package Assigment_1;

public class BnBReservation extends HotelReservation{
	
	// Constructors //
	/**
	 * BnBReservation Reservation Constructor
	 * A constructor that takes as input a String with the name on the reservation, a
		Hotel, a String with the room type, and an int indicating the number of nights.
		The constructor calls the HotelReservation constructor.
	 * @param name
	 * @param hotel
	 * @param room
	 * @param nights
	 */
	public BnBReservation(String name, Hotel hotel, String room, int nights) {
		super(name, hotel, room, nights);
	}
	
	// Methods //
	/**
	 * getCost Method for BnBReservation Class
	 * A getCost method that takes no input and returns the cost of the reservation + 10$ (an int) in cents.	    
	 */
	public int getCost() {
		 return super.getCost() + 1000*this.getNumOfNights();
	}
}