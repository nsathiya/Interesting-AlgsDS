/*
  author: @nsathiya

  Custom implementation of Map interface using only primitives and nodes.
  Hash collisions resolved by chaining.
  Simple hash function -> Math.abs(hashCode % hashtable_size).

  Map Implementations-
    V get(K key)
    void put(K key, V value)
    int getSize()
    void remove(K key)
    void printHashTable()

  Ex. Final Hash Table-
    0: (Hello,8)->(World,10)->(For,3)->
    1: (Test,5)->
    2: (Strings,2)->
    3: (More,1)->(Now,6)->
    4: (Some,0)->(Collisions,4)->

*/

//Node to keep track of key, value, and adjacent node
class EntryRef <K,V>{
  private K key;
  private V value;
  private EntryRef next;

  public EntryRef(K key, V value){
    this.key = key;
    this.value = value;
    this.next = null;
  }

  public void setNext(EntryRef e){
    this.next = e;
  }

  public void setValue(V value){
    this.value = value;
  }

  public void setKey(K key){
    this.key = key;
  }

  public EntryRef getNext(){
    return this.next;
  }

  public V getValue(){
    return this.value;
  }

  public K getKey(){
    return this.key;
  }

}

//Main implementation
class HashMapRef <K,V> {

  private EntryRef [] entries;
  private int currentHashSize;
  private int currentCount;
  private final int MAP_INITIAL_SIZE = 5;

  public HashMapRef(){
    this.entries = new EntryRef[MAP_INITIAL_SIZE];
    this.currentHashSize = MAP_INITIAL_SIZE;
    this.currentCount = 0;
  }

  public V get(K key){

    //get start index of hashtable
    int location = computeLoc(key);
    EntryRef current = entries[location];

    //go through list and return value if key is found
    while(current != null){
      if (current.getKey() == key)
        return (V)current.getValue();
      current = current.getNext();
    }

    //if no key found, return null
    return null;

  }

  public void	put(K key, V value){

    //get start index of hashtable
    int location = computeLoc(key);
    EntryRef current = this.entries[location];

    //if no existing entry, create new one. increment count.
    if (current == null){
      this.entries[location] = new EntryRef(key, value);
      this.currentCount++;
    } else {
      boolean inserted = false;
      //check if existing entries have similar keys. if so update value.
      while(current.getNext() != null){
        if (current.getKey() == key){
          current.setValue(value);
          inserted = true;
          break;
        }
        current = current.getNext();
      }

      //check last entry. Refactor this to be elegant.
      if (current.getKey() == key && !inserted){
        current.setValue(value);
        inserted = true;
      }

      //if no existing entries found, create new one and append to end of list.
      //increment count.
      if (!inserted){
        current.setNext(new EntryRef(key, value));
        this.currentCount++;
      }
    }
  }

  //return size
  public int getSize(){
    return this.currentCount;
  }

  public void remove(K key){

    //get start index of hashtable
    int location = computeLoc(key);
    EntryRef current = entries[location];

    //if first entry, point index to adj. decrement counter
    if (current.getKey() == key){
      entries[location] = current.getNext();
      this.currentCount--;
    } else {
      //remove using double pointers. simple delete node from LL technique.
      EntryRef toRemove = current.getNext();
      //decrement counter if key matches and delete node.
      while(toRemove != null){
        if (toRemove.getKey() == key){
          current = toRemove.getNext();
          this.currentCount--;
          break;
        }
        current = current.getNext();
        toRemove = toRemove.getNext();
      }
    }
  }

  //hash function. hashcode can be negative if overflow.
  private int computeLoc(K key){
    return Math.abs(key.hashCode() % this.currentHashSize);
  }

  //print hash table key-value for testing purposes.
  public void printHashTable(){
    for (int i=0; i<this.entries.length; i++){
      EntryRef current = entries[i];
      String toPrint = i+": ";
      while(current != null){
        toPrint += "(" + current.getKey() + "," + current.getValue() + ")->";
        current = current.getNext();
      }
      System.out.println(toPrint);
    }
  }

}

class Solution{

  private static final int MAP_INITIAL_SIZE = 5;

  public static void main(String[] args){

    //initialize hashmap
    HashMapRef<String, Integer> map = new HashMapRef<String, Integer>();

    //test put(), get(), getSize()
    System.out.println("Inserting three key-value pairs...");
    map.put("Hello", 8);
    map.put("World", 12);
    map.put("Now", 4);
    System.out.println("Map current size: " + map.getSize());
    System.out.println("Get key 'Hello': " + map.get("Hello"));

    //test put() on exisiting key
    System.out.println("Changing existing key-value 'World': ");
    map.put("World", 10);
    System.out.println("Get key 'World': " + map.get("World"));

    //test remove
    System.out.println("Removing 'Now'");
    map.remove("Now");
    System.out.println("Map current size: " + map.getSize());

    //test collisions. map is printed to help.
    System.out.println("Testing Collision...");
    String[] keys = {"Some", "More", "Strings", "For", "Collisions", "Test", "Now"};
    for (int i=0; i<keys.length; i++)
      map.put(keys[i], i);
    System.out.println("Map current size: " + map.getSize());
    map.printHashTable();
    System.out.println("Get key 'Collisions': " + map.get("Collisions"));
  }
}
