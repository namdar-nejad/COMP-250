package Assigment_1;

public class FlightReservation extends Reservation{
	// Fields //
	private Airport depAirport;
	private Airport arrAirport;
		
	// Constructors //
	/**
	 * FlightReservation Class Constructor	 * 
	 * A constructor that takes as input a String with the name on the reservation, and two
		Airports indicating the place of departure and arrival respectively.
	 * @param name Name on Reservation
	 * @param a Departure Airport
	 * @param b Arrival Airport
	 * @throw IllegalArgumentException when arrival and departure airports are the same
	 */
	public FlightReservation(String name, Airport a, Airport b) {
		super(name);
		if (a.equals(b)) throw new IllegalArgumentException("The arrival and departure airports are the same.");
		else {			
			this.depAirport = a;
			this.arrAirport = b;
		}
		// TODO is the equal method right + where to put the Exception statement?			
	}	
	
	// Methods //
	
	/**
	 * getCost Method from the FlightReservation Class
	 * A getCost method that takes no input and returns the cost of the reservation (an
		int) in cents. The cost is computed adding together the fuels cost, the airport fees,
		and $53.75 (which include costs related to the plane plus taxes). We assume that:
 		1- Plnes pay $1.24 per gallon of fuel.
 		2- Planes can fly 167.52 kilometer per gallon of fuel.
		The cost is rounded up to the nearest cent.
	 * @return cost of the flight rounded to the nearest cent
	 */
	public int getCost() {		
		double fuelCost = (Airport.getDistance(depAirport, arrAirport)/167.52)*(1.24*100);			// Cost of fuel for traveling the distance in cents 
		double cost = fuelCost + arrAirport.getFees() + depAirport.getFees()+ (53.75*100); 
		return (int) Math.ceil(cost);
	}
	
	/**
	 * equals Method for the FlightReservation Class
	 * An equals method which takes as input an Object and return true if input matches
		this in type, name, and airports. Otherwise the method returns false.
	 */
	public boolean equals(Object obj) {		
		if(!(obj instanceof FlightReservation)) return false;
		else {
			FlightReservation flightObj = (FlightReservation) obj;
			if(this.arrAirport.equals(flightObj.arrAirport) 								// Comparing arrival Airports
					&& this.depAirport.equals(flightObj.depAirport)							// Comparing departure Airports
						&& this.reservationName().equals(flightObj.reservationName())) {	// Comparing reservationNames
				return true;
			}
			else	return false;
		}
	}
}