package FinalProject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
    // num of entries to the table
    private int numEntries;
    // num of buckets 
    private int numBuckets;
    // load factor needed to check for rehashing 
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K, V>>> buckets; 
    
    // constructor
    public MyHashTable(int initialCapacity) {
        // ADD YOUR CODE BELOW THIS        
    	this.numBuckets = initialCapacity;
    	this.numEntries = 0;
    	this.buckets = new ArrayList<LinkedList<HashPair<K, V>>>(initialCapacity);    	
    	
    	for(int i = 0; i<initialCapacity;i++) {
    		LinkedList<HashPair<K, V>> a = new LinkedList<HashPair<K, V>>();
    		this.buckets.add(i, a);
    	}    	
    	//System.out.println("At Initilization:"+"	numBuckets:"+this.numBuckets+"		length:" + this.buckets.size());
        //ADD YOUR CODE ABOVE THIS
    }
    
    public int size() {
        return this.numEntries;
    }
    
    public boolean isEmpty() {
        return this.numEntries == 0;
    }
    
    public int numBuckets() {
        return this.numBuckets;
    }
    
    /**
     * Returns the buckets variable. Useful for testing  purposes.
     */
    public ArrayList<LinkedList<HashPair<K, V>>> getBuckets(){
        return this.buckets;
    }
    
    /**
     * Given a key, return the bucket position for the key. 
     */
    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }
    
    private int hashFunctionValue(V value, int num) {
        int hashValue = Math.abs(value.hashCode())%num;
        return hashValue;
    }
    
    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
        //  ADD YOUR CODE BELOW HERE
    	boolean rehash = false;
    	HashPair<K, V> newPair = new HashPair<K, V>(key, value);		// HashPair to add
    	int hash;
    	    	
    	if(key == null)	return null;
    	hash = hashFunction(key);
    	    	
    	if(((this.numEntries+1.0)/this.numBuckets) > MAX_LOAD_FACTOR) rehash = true;		// Checking if rehashing is needed 
    	
    	LinkedList<HashPair<K, V>> LList = buckets.get(hash);
    	
    	/*
    	System.out.println("\n--------------------------------------------------------------------------");
    	System.out.println("Key: " + key + "		hash code: "+key.hashCode() + "		index: "+ hash + "\nValue: 	" + value);
    	System.out.println("List: "+LList+"		Size of list:"+this.size()+"		isEmpty:"+LList.isEmpty());    	
    	*/
    	    	
    	for(HashPair<K, V> e : LList) {
    		if(e.getKey().equals(key)) {
    			V rtn = (V) e.getValue();
    			e.setValue(value);
    			return rtn;
    		}
    	}
    	
    	LList.add(newPair);    	
		if(rehash) rehash();		
		
		numEntries++;
		return null;
    }
    
    private void putAt(K key, V value, MyHashTable<K, V> table, int hash) {
        //  ADD YOUR CODE BELOW HERE
    	boolean replaced = false;
    	HashPair<K, V> newPair = new HashPair<K, V>(key, value);
    	int size;
    	
    	LinkedList<HashPair<K, V>> LList = table.getBuckets().get(hash);
    	
    	size = LList.size();
    	
    	if(size == 0) {
			LList.add(newPair);
			numEntries++;
    	}
    	else {
        	for(int i = 0; i<size; i++) {
        		HashPair<K, V> e = LList.get(i);
        		if(e.getValue().equals(value)) {
        			e.setValue(value);
        			replaced = true;
        		}
        	}
        	if(!replaced) {
        		LList.add(newPair);
    			numEntries++;
        		}    			
    		}
        }
    
    /**
     * Get the value corresponding to key. Expected average runtime O(1)
     */    
    public V get(K key) {
        //ADD YOUR CODE BELOW HERE
    	if(key == null) return null;    	
    	int hash = hashFunction(key);    	
    	
    	//System.out.println("Key: " + key + "	hash code: "+key.hashCode() + "		index: "+ hash + "\nValue: " + value);
    	
    	LinkedList<HashPair<K, V>> LList = buckets.get(hash);
    	
    	for(HashPair<K, V> e : LList) {
    		if(e.getKey().equals(key)) {
    			return (V) e.getValue();
    		}
    	}
    	
    	return null;    	
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Remove the HashPair corresponding to key . Expected average runtime O(1) 
     */
    		
    public V remove(K key) {
        //ADD YOUR CODE BELOW HERE
    	if(key == null) return null;    	
    	int hash = hashFunction(key); 
    	
    	//System.out.println("Key: " + key + "	hash code: "+key.hashCode() + "		index: "+ hash + "\nValue: " + value);
    	
    	LinkedList<HashPair<K, V>> LList = buckets.get(hash);
    	
    	for(HashPair<K,V> e : LList) {
    		if(e.getKey().equals(key)) {
    			V rtn = (V) e.getValue();
    			LList.remove(e);
    			numEntries--;
    			return rtn;
    		}
    	}
    	return null;    	
        //ADD YOUR CODE ABOVE HERE
    }
    
    
    /** 
     * Method to double the size of the hashtable if load factor increases
     * beyond MAX_LOAD_FACTOR.
     * Made public for ease of testing.
     * Expected average runtime is O(m), where m is the number of buckets
     */    
    public void rehash() {
        //ADD YOUR CODE BELOW HERE
    	//System.out.println("rehashing");
    	MyHashTable<K,V> newTable = new MyHashTable<K,V>(this.numBuckets*2);
    	
        transfer(this, newTable);
        this.numBuckets *= 2;
        this.buckets = newTable.getBuckets();        
        //ADD YOUR CODE ABOVE HERE
    }
    
    private void transfer(MyHashTable<K,V> oldB, MyHashTable<K,V> newB) {
    	for(HashPair<K,V> p: oldB) {
    			newB.put((K) p.getKey(),(V) p.getValue());
    	}
    }
    
    
    /**
     * Return a list of all the keys present in this hashtable.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    public ArrayList<K> keys() {
        //ADD YOUR CODE BELOW HERE
    	ArrayList<K> rtn = new ArrayList<K>(numBuckets);
    	
    	
    	for(HashPair<K,V> p : this) {
    			rtn.add((K) p.getKey());    		
    	}
    	
    	return rtn;    	
        //ADD YOUR CODE ABOVE HERE
    }    
    
    
    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(m) where m is the number of buckets
     */
    public ArrayList<V> values() {
        //ADD CODE BELOW HERE
    	int hash;
    	MyHashTable<K,V> table = new MyHashTable<K,V>(this.numEntries);        
    	ArrayList<V> rtn = new ArrayList<V>(numEntries);
    	
    	for(HashPair<K,V> p : this) {
    			hash = hashFunctionValue(p.getValue(), table.numBuckets());    			    			
    			if(!exists(hash, table, p.getValue())) {
        			rtn.add((V) p.getValue());	
    			}
    			table.putAt(p.getKey(), p.getValue(), table, hash);
    	}
    	return rtn;    	
        //ADD CODE ABOVE HERE
    }
    
    private boolean exists(int key, MyHashTable<K,V> table, V value) {
    	boolean rtn = false;
    	for(HashPair<K,V> p: table.buckets.get(key)) {
    		if(value.equals(p.getValue())) {
    			rtn = true;
    			return rtn;
    		}
    	}
    	return rtn;
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort (MyHashTable<K, V> results) {
        ArrayList<K> sortedResults = new ArrayList<>();
        for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
        	while (i >= 0) {
        		toCompare = results.get(sortedResults.get(i));
        		if (element.compareTo(toCompare) <= 0 )
        			break;
        		i--;
        	}
        	sortedResults.add(i+1, toAdd);
        }
        return sortedResults;
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to.
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */    
    
    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
        //ADD CODE BELOW HERE    	
    	ArrayList<HashPair<K, V>> arrList = new ArrayList<HashPair<K,V>>(results.size());
    	for(HashPair<K,V> p : results) {
    		arrList.add(p);
    	}    	
    	// Performing merge sort on the array of HashPairs:
    	quickSort(arrList, 0, arrList.size()-1);  
 		 		
 		return getKeys(arrList);
        //ADD CODE ABOVE HERE
    }
    
    private static <K, V extends Comparable<V>> ArrayList<K> getKeys(ArrayList<HashPair<K, V>> arrList) {
    	ArrayList<K> rtn = new ArrayList<K>(arrList.size());
    	for(int i = 0; i<arrList.size(); i++) {
 			rtn.add(arrList.get(arrList.size()-1-i).getKey());
 		}
    	return rtn;
    }
    
    private static <K, V extends Comparable<V>> void quickSort(ArrayList<HashPair<K, V>> list, int start, int end){
    	if(start >= end) return;
    	else {
    		int part = partition(list, start, end);
    		
    		quickSort(list, start, part-1);    		
    		quickSort(list, part+1, end);
    	}
    }
    
    private static <K, V extends Comparable<V>> int partition(ArrayList<HashPair<K, V>> list, int start, int end) {
		HashPair<K, V> pivot = list.get(end);
		int wall = start-1;
		
		
		for(int i = start; i<end; i++) {
			if((list.get(i).getValue()).compareTo(pivot.getValue()) <= 0) {
				wall++;
				swap(i, wall, list);
			}
		}
		swap(end, wall+1, list);    	
    	return wall+1;
    }
    
    private static <K, V extends Comparable<V>> void swap(int a, int b, ArrayList<HashPair<K, V>> list) {
    	HashPair<K, V> temp = list.get(a);
    	list.set(a, list.get(b));
    	list.set(b, temp);    	
    }
    
    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }   
    
    
    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        //ADD YOUR CODE BELOW HERE
    	LinkedList<HashPair<K, V>> list;
    	ArrayList<LinkedList<HashPair<K, V>>> buks;
    	
    	//ADD YOUR CODE ABOVE HERE
    	
    	/**
    	 * Expected average runtime is O(m) where m is the number of buckets
    	 */
        private MyHashIterator() {
            //ADD YOUR CODE BELOW HERE
        	buks = buckets;
        	list = new LinkedList<HashPair<K, V>>();
        	if(buks != null) populate(list);
        	//ADD YOUR CODE ABOVE HERE
        }
        
        private void populate(LinkedList<HashPair<K, V>> list) {
        	for(int i = 0; i<numBuckets; i++) {
        			list.addAll(buks.get(i));
        	}
        }
        
		@Override
        /**
         * Expected average runtime is O(1)
         */
        public boolean hasNext() {
            //ADD YOUR CODE BELOW HERE
        	if(list.size()!=0) return true;			
			return false;        	
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public HashPair<K,V> next() {
            //ADD YOUR CODE BELOW HERE
        	if(hasNext()) {
        		HashPair<K, V> rtn = list.getFirst();
            	list.removeFirst();
        		return(rtn);
        	}
        	return null;
            //ADD YOUR CODE ABOVE HERE
        }        
    }
}
