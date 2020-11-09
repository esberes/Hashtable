/**
 * Filename:   Profile.java
 * Project:    p3
 * Authors:    Ellie Beres 
 */
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * This class is used to compare our own implementation of the HashTable class and
 *  Java's TreeMap class. The comparison is done through the use of a Profile object 
 *  and Java's built-in Flight Recorder. 
 *  
 *  @param K 
 *  @param V
 */
public class Profile<K extends Comparable<K>, V> {

	HashTableADT<K, V> hashtable;
	TreeMap<K, V> treemap;
	
	/**
	 * Constructor for the profile class that initializes a HashTable and TreeMap object.
	 */
	public Profile() {
		hashtable = new HashTable<>();
		treemap = new TreeMap<>();
	}
	
	/**
	 * This method inserts a key, value pair into our own implementation of HashTable and 
	 * Java's built in version of TreeMap.
	 * 
	 * @param K key
	 * @param V value
	 */
	public void insert(K key, V value) {
		hashtable.put(key, value);
		treemap.put(key, value);
	}
	
	/**
	 * This method retrieves a value from the correct key in our own implementation of
	 * HashTable and Java's built in version of TreeMap.
	 * 
	 * @param K key 
	 */
	public void retrieve(K key) {
		hashtable.get(key);
		treemap.get(key);
	}
	
	/**
	 * This method creates a profile object, then performs the insert and retrieve method
	 * on each object as many times as specified by the variable, numElements. 
	 * 
	 * @param args 
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Expected 1 argument: <num_elements>");
			System.exit(1);
		}
		int numElements = Integer.parseInt(args[0]);
		 ArrayList<Long> elements = new ArrayList<Long>();
		 Profile<Integer, Integer> profile = new Profile<Integer, Integer>();

	        for (long i = 0; i < numElements; i++) {
	            elements.add(i);    
	        }
	        for (int i = 0; i < numElements; i++) {
	        	profile.insert(i, i);
	        }
	        for (int i = 0; i < numElements; i++) {
	        	profile.retrieve(i);
	        }
		
		String msg = String.format("Successfully inserted and retreived %d elements into the hash table and treemap", numElements);
		System.out.println(msg);
	}
}
