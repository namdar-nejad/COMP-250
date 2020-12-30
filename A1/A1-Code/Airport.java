package Assigment_1;

public class Airport {
	
	// Fields //	
	private int x_coord;		// The x-coordinate of the airport on a world map with a scale to 1 km
	private int y_coord;		// The y-coordinate of the airport on a world map with a scale to 1 km
	private int fee;			// The airport fee in cents
	
	// Constructors //
	/**
	 * Constructor for Airport class
	 * @param x The x-coordinate of the airport on a world map with a scale to 1 km
	 * @param y The y-coordinate of the airport on a world map with a scale to 1 km
	 * @param fee The airport fee in cents
	 */	
	public Airport(int x, int y, int fee) {
		x_coord = x;
		y_coord = y;
		this.fee = fee;		
	}
	
	// Methods //	
	/**
	 * getFee method
	 * @return The airport fee
	 */
	public int getFees() {
		return fee;
	}
	
	/**
	 * getDistance method
	 * @param a The first Airport
	 * @param b The second Airport
	 * @return The distance in kilometer between the two airports, rounded up to the closes integer
	 */
	public static int getDistance(Airport a, Airport b) {	
		double distance = 
				 Math.sqrt(Math.pow((a.x_coord - b.x_coord),2)
							+ Math.pow((a.y_coord - b.y_coord),2));		// Finding distance
		return (int) Math.ceil(distance);		
	}
}