package Assigment_1;

public class Basket {
	// Fields //
	Reservation[] reservationArr;
	
	// Constructors //
	public Basket() {
		reservationArr = new Reservation[0];
	}
	
	// Methods //
	/** 
	 * A getProducts method which takes no inputs and returns a shallow copy of the
		array of Reservations of the basket. This array should contain all the reservations in
		the basket in the same order in which they were added.
	 */	
	public Reservation[] getProducts() {
		Reservation[] copy = reservationArr;
		return copy;
	}
	
	/**
	 * add Method
	 * An add method which takes as input a Reservation. The method adds the reservation
		at the end of the list of reservation of the basket and returns how many reservations there are.
	 * This method calls on findLast to find the first null space in the array.
	 */	
	public int add(Reservation res) {
		Reservation[] newArray = fullCopy(reservationArr, 1);
		newArray[newArray.length-1] = res;
		this.reservationArr = newArray;
		return this.reservationArr.length;
	}
	
	/**
	 * remove Method
	 * A remove method which takes as input a Reservation and returns a boolean. The
		method removes the first occurrence of the specified element from the array of reservation of the basket.
		If no such reservation exists, then the method returns false, otherwise, after removing it, the method returns true.
		Note that this method removes a reservation from the list if and only if such reservation is equal to the input received.
		After the reservation has been removed from the array, the subsequent elements is shifted down by one position.
	 * This method calls on the finsFirst method to find the first occurrence of a Reservation.
	 * @param resv Reservation to remove
	 * @return True on success
	 */
	public boolean remove(Reservation resv) {
			int first = findFirst(this.reservationArr, resv);
			if(first != -1) {
			this.reservationArr[first] = null;			
			this.reservationArr = reOrder(this.reservationArr);			
			return true;
		}
		else return false;		
	}
	
	/**
	 * clear Methods
	 * clears all the reservation array slots
	 * calls on wipe method
	 */
	public void clear() {
		this.reservationArr = new Reservation[0];
	}
	
	/**
	 * getNumOfReservation Method 
	 * @return number of reservations in Basket
	 */
	public int getNumOfReservations() {
		int count = 0;
		for (int i = 0; i<this.reservationArr.length; i++) {
			if (reservationArr[i] != null) count++;
		}
		return count;
	}
	
	/**
	 * getTotalCost Method
	 * @return total cost of Reservations in Basket
	 */
	public int getTotalCost() {
		int total = 0;
		for(int i = 0; i<reservationArr.length; i++) {
			if(reservationArr[i] != null) total += reservationArr[i].getCost();
		}
		return total;
	}	
	
	// Helper Methods //
	private Reservation[] fullCopy(Reservation[] arr, int extra) {
		int newLen = arr.length+extra;
		Reservation[] newArr = new Reservation[newLen]; 
		for (int i = 0; i<arr.length; i++) {
			newArr[i] = arr[i];
		}
		return newArr;		
	}	
	private int findFirst(Reservation[] arr, Reservation resv) {		
		for(int i = 0; i < arr.length; i++) {
			if(arr[i].equals(resv) && arr[i] != null) return i;
		}
		return -1;
	}	
	private Reservation[] reOrder(Reservation[] arr) {
		int a = 0;
		Reservation[] newArr = new Reservation[arr.length-1];
		for(int x = 0; x<arr.length-1; x++) {			
			if(arr[x] != null) {
				if(x+a == arr.length) break;
				newArr[x] = arr[x+a];				
			}
			else {
				if(x+1 == arr.length) break;
				a = 1;
				newArr[x] = arr[x+1];
			}
		}
		return newArr;
	}
}