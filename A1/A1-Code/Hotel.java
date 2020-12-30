package Assigment_1;

public class Hotel {
	// Fields //
	private String name;			// Hotel name
	private Room[] rooms;			// Array of Rooms in Hotel
	
	// Constructor //
	
	/**
	 * Constructor for Hotel class
	 * takes a String and an array of Rooms respectively.
	 * The constructor uses the inputs to initialize the corresponding fields.
	 * @param rooms An array of rooms
	 * @param name The name of the Hotel
	 */
	public Hotel(String name, Room[] rooms) {
		// Making a deep copy of the room_array
		this.rooms = new Room[rooms.length];			// Initializing the hotel rooms array
		for(int i=0; i<rooms.length; i++) {				// Looping through given array of rooms
			this.rooms[i] = new Room(rooms[i]);			// Making a deep copy using a Room constructor
		}
		this.name = name;
	}
	
	// Methods //

	/**
	 * reserveRoom Method
	 * The method changes the availability of the first available room of the specified type in the hotel.
	 * If successful, the method returns the price of the room. Otherwise, an IllegalArgumentException is thrown.
	 * @param type room type we are looking for
	 * @return the room price if room found
	 * @throw  IllegalArgumentException if room not found
	 */
	public int reserveRoom(String type) {		
		Room available_room = Room.findAvailableRoom(this.rooms, type);
		if (available_room!=null) {
			available_room.changeAvailability();
			return available_room.getPrice();
		}
		else {
			throw new IllegalArgumentException ("Such a room is not available.");
		}
	}
	
	/**
	 * cancelRoom Method
	 * The method makes a room of that type available again.
	 * It returns true if it operation was possible, false otherwise.
	 * @param type Room type we want to make available
	 * @return True on success
	 */
	public boolean cancelRoom(String type) {	
		return Room.makeRoomAvailable(this.rooms, type);		
	}
}