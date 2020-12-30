package Assigment_1;

public class HotelReservation extends Reservation {
	
	// Fields //
	private Hotel hotel;
	private String room_type;
	private int duration;
	private int price_night;			// the price (in cents) for one night in room of hotel

	// Constructors //
	
	/**
	 * HotelReservation Constructor
	 * A constructor that takes as input a String with the name on the reservation, a
		Hotel, a String with the room type, and an int indicating the number of nights.
		The constructor uses the inputs to create a Reservation and initialize the corresponding fields.		
		If such reservation is not possible, then an IllegalArgumentException is raised.
	 * @param name Name on reservation 
	 * @param hotel hotel we are reserving in
	 * @param room room type
	 * @param nights duration of stay
	 * @throws IllegalArgumentException if hotel reservation is not possible
	 */
	public HotelReservation(String name, Hotel hotel, String room, int nights) {
		super(name);
		try {
			price_night = hotel.reserveRoom(room);			
		}
		catch(IllegalArgumentException e){
			throw new IllegalArgumentException("Such a room is not avalible at " + hotel);
		}
		this.hotel = hotel;
		this.room_type = room;
		this.duration = nights;		
	}
	
	// Methods //
	/** getNumOfNights Method 
	 * @return duration Number of nights staying in hotel
	 */
	public int getNumOfNights() {
		return this.duration;
	}

	/**
	 * getCost Method
	 * A getCost method that takes no input and returns the cost of the reservation (an
		int) in cents. The cost represents the price to pay for the specified type of room
		given the number of nights indicated in the reservation.
	 *@returns room cost for the number of nights   	
	 */
	public int getCost() {
		int cost = price_night*this.getNumOfNights();
		return cost;
	}

	/**
	 * equals Method from HotelReservation Class
	 * An equals method which takes as input an Object and return true if input matches
		this in type, name, hotel, room type, number of nights, and total cost.		
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof HotelReservation)) return false;
		else {
			HotelReservation hRes = (HotelReservation) obj;
			if(this.getCost() == hRes.getCost()
					&& this.getNumOfNights() == hRes.getNumOfNights()
						&& this.room_type.equals(hRes.room_type)
							&& this.hotel.equals(hRes.hotel)
								&& this.reservationName().equals(hRes.reservationName())) {
				return true;
			}
			else	return false;
		}
	}
}