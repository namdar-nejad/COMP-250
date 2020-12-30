package FinalProject;

import java.util.ArrayList;

public class Twitter {

	//ADD YOUR CODE BELOW HERE
	
	//MyHashTable<String, Tweet> tDate;
	MyHashTable<String, String> sWord;
	MyHashTable<String, Tweet> tAuthor;
	ArrayList<ArrayList<Tweet>> dateList;
	ArrayList<ArrayList<String>> strArr;
	int size;
	//ADD CODE ABOVE HERE 
	
	// O(n+m) where n is the number of tweets, and m the number of stopWords
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		//ADD YOUR CODE BELOW HERE
		this.size = tweets.size()*2;	
		
		strArr =  new ArrayList<ArrayList<String>>(tweets.size());
		tAuthor = new MyHashTable<String, Tweet>(tweets.size());
		dateList = new ArrayList<ArrayList<Tweet>>(this.size);
		sWord = new MyHashTable<String, String>(10);
				
		
		for(int i = 0; i<this.size;i++) {
			ArrayList<Tweet> a = new ArrayList<Tweet>();
			ArrayList<String> b = new ArrayList<String>();
			dateList.add(a);
			strArr.add(b);
		}
		
		for(Tweet t: tweets) {
			//tDate.put(t.getDateAndTime(), t);			
			//	Adding Tweets to the author HashTable only if its date is bigger than the last one			
			{
				if(tAuthor.get(t.getAuthor())==null 
						||	(tAuthor.get(t.getAuthor()).getDateAndTime()).compareTo(t.getDateAndTime()) < 0) {
					tAuthor.put(t.getAuthor(), t);
				}
			}			
			putIn(t, dateList);
			strArr.add(getWords(t.getMessage()));		// Adding the parsed massages to the array
		}
		
		for(String s : stopWords) {
			ArrayList<String> arr = getWords(s);
			//System.out.println("Stop Word Array: "+ arr);
			for(String st: arr) {
				//System.out.println(arr.get(0).toLowerCase());
				String str = st.toLowerCase();
				sWord.put(str, str);
			}			
		}
		//ADD CODE ABOVE HERE 
	}
	
	private void putIn(Tweet t, ArrayList<ArrayList<Tweet>> list) {
		String date;
		
		if(t.getDateAndTime().length() >= 10)	date = t.getDateAndTime().substring(0,10);
		else date = t.getDateAndTime();		
		
		int hash = hashFunctionDate(date, this.size);
		
		//System.out.println("-----------------------------------------");		
		//System.out.println("subString: " + date +"\t Tweet: "+ t +"\thash: "+hash);
		//System.out.println("List before: "+list);
		ArrayList<Tweet> inner = list.get(hash);
		//System.out.println("at index " + hash + " list is " + inner);
		
		inner.add(t);
		list.set(hash, inner);
		//System.out.println("List after: "+list);
		//System.out.println("at index " + hash + " list is " + inner);
	}
	
    private int hashFunctionDate(String key, int num) {
        int hashValue = Math.abs(key.hashCode())%num;
        return hashValue;
    }
	// TODO: consider having a rehash method for the date list.
    /**
     * Add Tweet t to this Twitter
     * O(1)
     */
	public void addTweet(Tweet t) {
		//ADD CODE BELOW HERE
		tAuthor.put(t.getAuthor(), t);
		putIn(t, dateList);
		//tDate.put(t.getDateAndTime(), t);
		//ADD CODE ABOVE HERE 
	}	

    /**
     * Search this Twitter for the latest Tweet of a given author.
     * If there are no tweets from the given author, then the 
     * method returns null. 
     * O(1)  
     */
    public Tweet latestTweetByAuthor(String author) {
        //ADD CODE BELOW HERE
    	Tweet a = tAuthor.get(author);
    	return(a);
        //ADD CODE ABOVE HERE 
    }

    /**
     * Search this Twitter for Tweets by `date' and return an 
     * ArrayList of all such Tweets. If there are no tweets on 
     * the given date, then the method returns null.
     * O(1)
     */
    public ArrayList<Tweet> tweetsByDate(String date) {
        //ADD CODE BELOW HERE
    	int hash = hashFunctionDate(date.substring(0, 10), this.size);    	
    	//System.out.println("\ngot hash to be:" + hash);
    	ArrayList<Tweet> rtn = new ArrayList<Tweet>(dateList.get(hash).size());
    	    	
    	for(Tweet t:dateList.get(hash)) {
    		if(date.equals(t.getDateAndTime().substring(0, 10))) rtn.add(t);
    	}
    	
    	return dateList.get(hash);    	
        //ADD CODE ABOVE HERE
    }
    
	/**
	 * Returns an ArrayList of words (that are not stop words!) that
	 * appear in the tweets. The words should be ordered from most 
	 * frequent to least frequent by counting in how many tweet messages
	 * the words appear. Note that if a word appears more than once
	 * in the same tweet, it should be counted only once. 
	 */
    public ArrayList<String> trendingTopics() {
        //ADD CODE BELOW HERE
    	//System.out.println("Intered treding topics:");
    	
    	
    	MyHashTable<String, Integer> rtn = new MyHashTable<String, Integer>(strArr.size());    	
    	MyHashTable<String, String> contains;
    	Integer number;
    	// get the array of parsed words and init a HashTable
    	// for each section start one temp ArrayList
    	// Check if you have the work in the array list and check with the stop word table
    	// add word to HachTable and the array list
    	//System.out.println("The array of passed strings: "+ strArr);
    	for(ArrayList<String> strA: strArr) {
    		contains = new MyHashTable<String, String>(33);
    		//System.out.println("Current String Array: " + strA);
    		//int size = strA.size();
    		for(String str : strA) {
    			//System.out.println("Current String: "+str);
    			
    			str = str.toLowerCase();
    			
    			//int hash = hashFunctionDate(str, rtn.numBuckets());
    			
    			//System.out.println("Word: "+ str);
    			//System.out.println("Hash: "+ hash);
    			
    			if(sWord != null) {						// Checking if the sWord arra is empty or not
    				//System.out.println("sWord not empty");
        			if(sWord.get(str) == null) {		// not a stop word
        				
        				if(rtn.get(str) == null) {		// First time adding this to the table
        					
        					//System.out.println("didn't exicts in the HashTable before."); 
        					
        					if(contains.get(str) == null) {
        						number = 1;
            					rtn.put(str, number);
            					//System.out.println("Current number: " + number); 
            					contains.put(str, str);
        					}        					
        				}
        				else {
        					//System.out.println("did exicts in the HashTable before.");  
        					if(contains.get(str) == null) {
        						int num = rtn.get(str) + 1;
        						number = num;
        						//System.out.println("Current number: " + (num));
        						contains.put(str, str);
            					rtn.put(str, number);
        					}        					
        				}    				
        			}
        		}    			
    			else {    				
    			//System.out.println("sWord not empty");
    			
    			if(sWord.get(str) == null) {		// not a stop word
    				
    				if(rtn.get(str) == null) {		// First time adding this to the table
    					
    					//System.out.println("didn't exicts in the HashTable before."); 
    					
    					if(contains.get(str) == null) {
    						number = 1;
        					rtn.put(str, number);
        					//System.out.println("Current number: " + number); 
        					contains.put(str, str);
    					}        					
    				}
    				else {
    					//System.out.println("did exicts in the HashTable before.");
    					if(contains.get(str) == null) {
    						int num = rtn.get(str) + 1;
    						number = num;
    						//System.out.println("Current number: " + (num));
    						contains.put(str, str);
        					rtn.put(str, number);
    						}        					
    					}    				
    				}
    			}
    			//System.out.println("Table: " + rtn);
    		}
    	}
    	return MyHashTable.fastSort(rtn);    	
        //ADD CODE ABOVE HERE    	
    }
    
    /**
     * An helper method you can use to obtain an ArrayList of words from a 
     * String, separating them based on apostrophes and space characters. 
     * All character that are not letters from the English alphabet are ignored. 
     */
    private static ArrayList<String> getWords(String msg) {
    	msg = msg.replace('\'', ' ');
    	String[] words = msg.split(" ");
    	ArrayList<String> wordsList = new ArrayList<String>(words.length);
    	for (int i=0; i<words.length; i++) {
    		String w = "";
    		for (int j=0; j< words[i].length(); j++) {
    			char c = words[i].charAt(j);
    			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
    				w += c;    			
    		}
    		wordsList.add(w);
    	}
    	return wordsList;
    }
}