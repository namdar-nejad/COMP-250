import java.util.Arrays;
import java.util.Random;


public class TrainLine {

	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	public static Random rand;
	
	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
	/*
	 * Constructor for TrainStation input: stationList - An array of TrainStation
	 * containing the stations to be placed in the line name - Name of the line
	 * goingRight - boolean indicating the direction of travel
	 */
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 1; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]);
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name,
			boolean goingRight) {/*
									 * Constructor for TrainStation. input: stationNames - An array of String
									 * containing the name of the stations to be placed in the line name - Name of
									 * the line goingRight - boolean indicating the direction of travel
									 */
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i]));
		}
 		this.lineMap = this.getLineArray();
 	}

	// adds a station at the last position before the right terminus
	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft();
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}	
	
	public void sortLine() {
		
			TrainStation[] a;
			TrainStation current;
			TrainStation next;
			
			int i,j;
			int n = this.getSize();
			
			current = this.getLeftTerminus();
			next = current.getRight();
			
			for(i=0; i<n-1; i++) {
				//System.out.println("\nouter loop -- "+ i);
				for(j=0; j<n-i-1; j++) {
					//System.out.println("inner loop -- " + j + " -- " + i);
					//System.out.println("		current = " + current.getName() + "		next = " + next.getName());
					 if(current.getName().compareTo(next.getName())>0) {
						 //System.out.println("swaping -- " + current.getName() + " -- with -- "+ next.getName());
						 swap(current, next);
						 //a= this.getLineArray();
						 //printArr(a);
						 
						if(current.getRight() == null) {
							//System.out.println("current.getRight() =="+current.getRight() + "    breaking");
							break;
						}
						next = current.getRight();
						//System.out.println("swaped -- new current = "+ current.getName() + "   new next  =  " + next.getName());
					 }
					 else {
						 //System.out.println("no swap");
						 //a = this.getLineArray();
						 //printArr(a);
						 current = next;
						
						 if(next.getRight() == null) break;
						// System.out.println("breaking iner loop");
						 
						 next = next.getRight();
						 
						 //System.out.println(" new current =  " +current.getName()+ " new next = " + next.getName());
					}	 
				}
				 //a = this.getLineArray();
				 //printArr(a);
				 
				current = this.getLeftTerminus();
				next = current.getRight();
				//System.out.println(" new current =  " +current.getName()+ " new next = " + next.getName());
			}
			this.lineMap = getLineArray();
		}
	
	private void printArr(TrainStation[] arr) {
		String str = "";
		for (int i=0; i<arr.length;i++) {
			str += (arr[i].getName()+" , ");
		}
		System.out.println(str);
	}
	
	
	private void swap(TrainStation a, TrainStation b) {
		
		boolean bRT=false, aLT=false; 				// booleans to hold left and right terminals
		//TrainStation bS=null, aS=null;			// to hold transfer station values
		//TrainLine bL= null, aL=null;
		
		// checking if either are end terminals
		
		if(a.isLeftTerminal() || a.getLeft() == null) {
			aLT = true;
		}
		
		if(b.isRightTerminal() || b.getRight() == null) {
			bRT = true;
		}
		
		// checking if either are transfer stations and updating vars
		/*
		if(a.hasConnection) {
			aS = a.getTransferStation();
			aL = a.getTransferLine();
		}
		if(b.hasConnection) {
			bS = b.getTransferStation();
			bL = b.getTransferLine();
		}*/

		// swapping scenarios:(x4)
		
		// 1- both terminal stations
		if(bRT && aLT) {
			a.setRight(null);
			b.setLeft(null);
			a.setLeft(b);
			b.setRight(a);
			a.setRightTerminal();
			b.setLeftTerminal();
			this.leftTerminus = b;
			this.rightTerminus = a;
		}		
		
		// 2- neither are terminals
		else if(!bRT && !aLT) {
			TrainStation left = a.getLeft();
			TrainStation right = b.getRight();
			
			b.setLeft(left);
			b.setRight(a);
			
			a.setRight(right);
			a.setLeft(b);
			
			left.setRight(b);
			right.setLeft(a);
			
			a.setNonTerminal();
			b.setNonTerminal();
		}
		
		
		// 3- a is left terminal and b is not a terminal
		else if(aLT && !bRT) {
			TrainStation right = b.getRight();	
			
			b.setLeft(null);
			a.setRight(right);
			a.setLeft(b);
			b.setRight(a);
			right.setLeft(a);
			
			b.setLeftTerminal();
			a.setNonTerminal();
			this.leftTerminus = b;
		}
		
		// 4- a is a not a terminal and b is the right terminal
		else if(bRT && !aLT) {
			TrainStation left = a.getLeft();			
			a.setLeft(b);
			b.setLeft(left);
			b.setRight(a);
			a.setRight(null);
			left.setRight(b);
			
			b.setNonTerminal();
			a.setRightTerminal();			
			this.rightTerminus = a;
		    }
		
		/*
		// setting connections
		// only a has connections
		if(a.hasConnection && !b.hasConnection) {
			b.setConnection(aL, aS);
			a.hasConnection = false;
		}
		//only b has connections
		else if(b.hasConnection && !a.hasConnection) {
			a.setConnection(bL, bS);
			b.hasConnection = false;
		}
		// they both have connections
		else if(a.hasConnection && b.hasConnection) {
			a.setConnection(bL, bS);
			b.setConnection(aL, aS);
			}*/
		}
	
	public int getSize() {
		TrainStation current = this.leftTerminus; 		// Starting at the most left end
		int size = 0;
		while(!current.isRightTerminal()) {				// Incrementing through the line
			if(current != null)	size++;
			current = current.getRight();
			}
		if(current.isRightTerminal() && current != null) {
			return size+1;
			}
		else return size;
		}
	
	public TrainStation findStation(String name) throws StationNotFoundException{
		TrainStation current = this.leftTerminus; 			// Starting at the most left end
		for(int i=0; i<this.getSize(); i++){				// Incrementing through the line
			if ((name.toLowerCase()).equals(current.getName().toLowerCase())) return current;
			else {
					current = current.getRight();
				}
			}
			throw new StationNotFoundException("No Station found with such a name.");

		}
	
	public TrainStation getNext(TrainStation station) throws StationNotFoundException{
			TrainStation str = findStation(station.getName());

			if(str.isRightTerminal()) {
				this.goingRight = false;
				return str.getLeft();				
			}
			else if(str.isLeftTerminal()) {
				this.goingRight = true;
				return str.getRight();
			}
			else if(this.goingRight) {
				return str.getRight();
			}
			else if(!this.goingRight) {
				return str.getLeft();
			}
			else {
				
				throw new StationNotFoundException(" Station Not Found ");
			}
	}	
	
	public TrainStation travelOneStation(TrainStation current, TrainStation previous) throws StationNotFoundException {	
			TrainStation curStation = findStation(current.getName());
			if(curStation.hasConnection) {
				if(previous!=null) {
					if(previous.equals(curStation.getTransferStation())) {
						return getNext(curStation);
					}
					else {
						return curStation.getTransferStation();
					}
				}
				else {
					return curStation.getTransferStation();
				}
			}
			else if(!curStation.hasConnection) {
				return getNext(curStation);
			}
			else {			
			throw new StationNotFoundException("Current Station not on this line.");
			}
	}	

	public TrainStation[] getLineArray() {		
		TrainStation[] arr = new TrainStation[this.getSize()];		
		TrainStation current = this.leftTerminus;
		
		for(int i=0; i< this.getSize(); i++) {
			if(current != null) arr[i] = current;
			if(current.isRightTerminal()) {
				arr[i] = current;
				break;
			}
			else current = current.getRight();
		}
		return arr;
	}

	public void reverseDirection() {
		this.goingRight = !this.goingRight;
	}

	private TrainStation[] shuffleArray(TrainStation[] array) {
		Random rand = new Random();
		rand.setSeed(11);
		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}

	public void shuffleLine() {
		TrainStation[] lineArray = this.getLineArray();
		
		this.leftTerminus.setNonTerminal();
		this.rightTerminus.setNonTerminal();
		
		TrainStation[] shuffledArray = shuffleArray(lineArray);
		this.lineMap = shuffledArray;
		
		int n = lineMap.length;
		
		this.leftTerminus = lineMap[0];
		lineMap[0].setLeftTerminal();
		if(lineMap[1] != null) {
			lineMap[0].setRight(lineMap[1]);
			lineMap[1].setLeft(lineMap[0]);
		}
		
		this.rightTerminus = lineMap[n-1];
		lineMap[n-1].setRightTerminal();		
		lineMap[n-2].setRight(lineMap[n-1]);
		lineMap[n-1].setLeft(lineMap[n-2]);
		
		for(int i=1; i<lineMap.length-2; i++) {
			resetStations(lineMap[i-1], lineMap[i], lineMap[i+1]);
		}
		this.lineMap = this.getLineArray();
	}	
	
	// connecting stations to each other from an array -- called in shuffle line
	public void resetStations(TrainStation before, TrainStation current, TrainStation after) {
		TrainStation left = before;
		TrainStation right = after;
		
		current.setLeft(left);
		current.setRight(right);
		left.setRight(current);
		right.setLeft(current);
		current.setNonTerminal();
	}
	
	public String getName() {
		return this.lineName;
	}

	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2))
					return false;
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus;
	}
}

//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}
}
