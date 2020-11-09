
public class HashTableTesting {
	public static void main(String[] args) {
		HashTable<String, Integer> hash = new HashTable<>();
		hash.put("dog", 10);
		hash.put("dog", 5);
		hash.put("dog", 4);
		hash.put("dog", 6);
		hash.put("dog", 7);
		hash.put("dog", 8);
		hash.put("dog", 9);
		hash.put("dog", 11);
		hash.put("dog", 12);
		hash.put("dog", 13);
		hash.put("cat", 14);
		System.out.println(hash.get("dog"));
		
		
	}
}
