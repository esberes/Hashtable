/**
 * Filename:   HashTable.java
 * Project:    p3
 * Authors:    Ellie Beres
 */

import java.util.ArrayList;
import java.util.NoSuchElementException;

//
// Collision Scheme: Buckets
// 		The Hash Table is created using an ArrayList containing members of the HashNode class.
//		To account for collisions, each HashNode member contains the data of its next node. 
//		Therefore, the bucket scheme uses a singly linked list data structure of HashNodes 
//		with the head of the bucket list being the accessible member at the bucket index.
//
// Hashing Algorithm: hashCode provided by the <K key> object
// 		To find the hash index, the hashCode provided by the <K key> object is used.
//		That provided value is stored as a variable and is then modulo-ed with the capacity
//		of the Hash Table to ensure the index is bounded.
//    

/**
 * 
 *         The HashTable.java class implements a hash table data structure of
 *         type @class HashNode. The Hash Table is implemented using an
 *         ArrayList and collisions are handled using a singly linked list. This
 *         class provides efficient data storage and accessibility.
 *
 * @param <K>
 * @param <V>
 */
public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {

	/**
	 * The HashNode<KK, VV> class contains node object data, including a node's key,
	 * value, and next node. The class creates a chain of nodes.
	 * 
	 * @param <KK, VV>
	 */
	static class HashNode<KK, VV> {
		KK key;
		VV value;

		// Reference to next node allows for singly linked list bucket system
		HashNode<KK, VV> next;

		// Constructor
		public HashNode(KK key, VV value) {
			this.key = key;
			this.value = value;
		}

	}

	// hashTable is used to store array of chains
	private ArrayList<HashNode<K, V>> hashTable;

	// Current capacity of array list and default capacity for no-arg constructor
	private int capacity;
	private final static int DEFAULT_CAPACITY = 10;

	// Current size of array list
	private int size;

	// Current load factor and default load factor for no-arg constructor
	private double loadFactor;
	private final static double DEFAULT_LOAD_FACTOR = 0.75;

	// No-arg Constructor
	public HashTable() {
		this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);

	}

	// Constructor that accepts initial capacity and load factor
	public HashTable(int initialCapacity, double loadFactor) {
		hashTable = new ArrayList<>();
		size = 0;
		this.capacity = initialCapacity;
		this.loadFactor = loadFactor;

		// Create empty chains for Hash Table
		for (int i = 0; i < capacity; i++)
			hashTable.add(null);

	}

	/**
	 * This helper method implements hash function to find index for a key in the
	 * Hash Table.
	 * 
	 * @returns Hash Table index
	 */
	private int getHashIndex(K key) {
		int hashCode = key.hashCode(); // Leverage hashCode provided by the <K key> object
		int index = (int) Math.abs(hashCode % capacity); // Ensure index is bounded
		return index;
	}

	/**
	 * This helper method increases the capacity of the Hash Table and the amount of
	 * buckets when the size to capacity ratio becomes greater than the load factor.
	 */
	private void updateTableSize() {
		ArrayList<HashNode<K, V>> temp = hashTable; // Store current hash table values in ArrayList temp as to not lose
													// them.
		hashTable = new ArrayList<>(); // Re-create hashTable that will be of new capacity.
		capacity = (2 * capacity) + 1; // Increase capacity accordingly
		size = 0; // Re-initialize size to 0, because size will be incremented in the following
					// loop
		for (int i = 0; i < capacity; i++)
			hashTable.add(null);

		for (HashNode<K, V> head : temp) {
			while (head != null) {
				put(head.key, head.value); // Add all values back into the newly sized hash table
				head = head.next;
			}
		}
	}

	/**
	 * Inserts a <key,value> pair entry into the hash table if the key already
	 * exists in the table, replace existing value for that key with the value
	 * specified in this call to put.
	 * 
	 * permits null values but not null keys and permits the same value to be paired
	 * with different key
	 * 
	 * @throw IllegalArgumentException when key is null
	 */

	@Override
	public void put(K key, V value) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException("Cannot insert a null key!");
		}

		// Find head of chain for given key
		int bucketIndex = getHashIndex(key);
		HashNode<K, V> head = hashTable.get(bucketIndex);

		// Check if key is already present
		while (head != null) {
			if (head.key.equals(key)) {
				head.value = value;
				return;
			}
			head = head.next;
		}
		// Insert key in chain
		size++;
		head = hashTable.get(bucketIndex);
		HashNode<K, V> newNode = new HashNode<K, V>(key, value);
		newNode.next = head; // Set the newNode.next value to head so that newNode becomes the new head at
								// the index
		hashTable.set(bucketIndex, newNode);

		// If load factor goes beyond threshold, then increase the hash table size
		if ((1.0 * size) / capacity >= loadFactor) {
			updateTableSize();
		}
	}

	/**
	 * This method returns the value associated with the given key.
	 * 
	 * @throw IllegalArgumentException if key is null
	 * @throw NoSuchElementException if key does not exist
	 */
	@Override
	public V get(K key) throws IllegalArgumentException, NoSuchElementException {
		if (key == null) {
			throw new IllegalArgumentException("Cannot get a null key!");
		}

		// Find head of chain for given key
		int hashIndex = getHashIndex(key);
		HashNode<K, V> head = hashTable.get(hashIndex);

		// Search key in chain
		while (head != null) {
			if (head.key.equals(key))
				return head.value;
			head = head.next;
		}

		// If key not found
		throw new NoSuchElementException("Element with key <" + key.toString() + "> not found!");
	}

	/**
	 * remove the (key,value) entry for the specified key throw
	 * IllegalArgumentException if key is null throw NoSuchElementException if key
	 * does not exist in the tree
	 */
	@Override
	public void remove(K key) throws IllegalArgumentException, NoSuchElementException {
		if (key == null) {
			throw new IllegalArgumentException("Cannot delete a null key!");
		}
		// Apply hash function to find index for given key
		int hashIndex = getHashIndex(key);

		// Get head of chain
		HashNode<K, V> head = hashTable.get(hashIndex);

		// Search for key in its chain
		/*
		 * prev keeps track of the previous node throughout the iteration, which will be
		 * used to remove the specified node when found.
		 */
		HashNode<K, V> prev = null;
		while (head != null) {
			// If Key found
			if (head.key.equals(key))
				break;

			// Else continue iterating through the chain
			prev = head;
			head = head.next;
		}

		// If key was not there
		if (head == null) {
			throw new NoSuchElementException(
					"Element with key <" + key.toString() + "> cannot" + "be deleted, because it cannot be found!");
		}

		// Reduce size
		size--;

		// Remove key
		if (prev != null)
			prev.next = head.next;
		else
			hashTable.set(hashIndex, head.next);
	}

	/** @return the number of keys in the hash table */
	@Override
	public int size() {
		return size;
	}

}
