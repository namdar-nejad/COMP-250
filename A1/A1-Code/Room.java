package Assigment_1;

public class Room {
	
	// Fields //
	
	private int room_price;					// The price (in cents) of the room
	private String room_type;				// The type of the room
	private boolean available;				// Whether or not the room is available

	
	// Constructors //
	
	/**
	 * Constructor_1 for the Room class
	 * It Takes in the type of the room and initializes the price and availability based on the type of the room.
	 * The only acceptable types are: double, queen, and king. They type is not case sensitive.   
	 * @param type The room type
	 * @exception IllegalArgumentException If room type is not acceptable
	 */
	public Room(String type) {
		switch (type.toLowerCase())
		{
		case "double":
			room_type = "Double";
			room_price = 9000;
			available = true;
			break;
		case "queen":
			room_type = "Queen";
			room_price = 11000;
			available = true;
			break;
		case "king":
			room_type = "King";
			room_price = 15000;
			available = true;
			break;
		default:			
			throw new IllegalArgumentException(type + " room type can't be created.");		
		}		
	}
	
	/**
	 * Constructor_2 for the Room class
	 * It takes a Room Object as and input and constructs another Room Object with the exact same values.
	 * @param room A Room Object
	 */	
	public Room(Room room) {
		this.room_type = room.room_type;
		this.room_price = room.room_price;
		this.available = room.available;
	}
		
	// Methods //	
	/**
	 * getPrice Method
	 * @return room_price
	 */
	public int getPrice() {
		return room_price;
	}
	
	/**
	 * getType Method
	 * @return room_type
	 */
	public String getType() {
		return room_type;
	}
	
	/**
	 * changeAvailability Method
	 *  It sets the value stored in the available field to be the opposite of the one currently there
	 */	
	public void  changeAvailability() {
		available = !available;
	}
	
	/**
	 *  findAvailableRoom Method
	 *  The method returns the first available room in the array of the indicated type.
	 *  If no such room exists the method returns null.  
	 * @param room_array An array of Rooms
	 * @param target_room The room type we are looking for
	 * @return The first available room in the array 
	 */	
	public static Room findAvailableRoom(Room[] room_array,  String target_room) {
		Room available_room = null;		
		for(int i=0; i<room_array.length; i++) {												// Looping trough the Room array
			Room current_room = room_array[i];													// current_room is a place holder
			if ((current_room!=null)&&(current_room.getType().equalsIgnoreCase(target_room))){	// If the Room type matches && The Room is not null
				if (current_room.available) {													// If the Room is available --> saving the room in avalible_room and breaking
					available_room = current_room;
					break;
				}
			}
		}		
		return available_room;
	}

	
	/**
	 * The method makes the first unavailable room in the array of the indicated type available again.
	 * If successful, the method should return true, otherwise the method should return false.
	 * @param room_array An array of Rooms
	 * @param target_room The room type that we are looking for
	 * @return True on success
	 */
	public static boolean makeRoomAvailable(Room[] room_array, String target_room) {
		boolean successful = false;		
		for(int i=0; i<room_array.length; i++) {													// Looping trough the Room array
			Room current_room = room_array[i];														// current_room is a place holder
			if ((current_room!=null)&&(current_room.getType().equalsIgnoreCase(target_room))){		// If the Room type matches && The Room is not null
				if (current_room.available == false) {												// If the Room is not available
					current_room.changeAvailability();
					successful = true;
					break;					
				}
			}
		}		
		return successful;
	}
}