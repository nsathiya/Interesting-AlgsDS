/*
  author: @nsathiya

  Leetcode Problem-
  Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and put in O(1) time.

  get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
  put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.

  Takeaways =>
  1. Be careful when dealing with double-linked lists. prev and next of every node should be set.
  2. Node also needs to keep track of key because it needs to be removed from hashmap once limit reached.

*/

import java.util.*;

//Main class
class LRUCacheRef<K,V>{

    //map to store key-value and q to keep track of replacement priorities
    private HashMap<K, Node> map;
    private Q q;
    private int capacity;

    //node with next and prev pointers
    class Node <K, V>{
      private V value;
      private K key;
      private Node next;
      private Node prev;

      public Node(K key, V val){
        this.value = val;
        this.key = key;
        this.next = null;
        this.prev = null;
      }

      public Node getNext(){
        return this.next;
      }

      public Node getPrev(){
        return this.prev;
      }

      public V getValue(){
        return this.value;
      }
      public K getKey(){
        return this.key;
      }

      public void setNext(Node n){
        this.next = n;
      }

      public void setPrev(Node p){
        this.prev = p;
      }

    }

    //doubly linkedlist based queue.
    class Q<K,V> {
      private Node<K,V> head;
      private Node<K,V> tail;
      private int count;

      public Q(){
        this.head = new Node(null, null);
        this.tail = new Node(null, null);
        this.count = 0;
      }

      //add node to end of queue
      public void enqueue(Node<K,V> n){
        //if no nodes, set head and tails to node
        if (this.count == 0){
          this.head.setNext(n);
          this.tail.setPrev(n);
          //be sure to keep track of next and prev
          n.setNext(this.tail);
          n.setPrev(this.head);
        } else {
          //else set node to current first element
          n.setNext(this.head.getNext());
          this.head.getNext().setPrev(n);
          this.head.setNext(n);
          n.setPrev(this.head);
        }
        this.count++;
      }

      //remove node from front of queue
      public Node<K,V> dequeue(){
        if (count>0){
          //remove last element and reset tail to second last element
          Node<K,V> toRemove = this.tail.getPrev();
          Node<K,V> nextFront = toRemove.getPrev();
          nextFront.setNext(this.tail);
          this.tail.setPrev(nextFront);
          //remove pointers to removed element
          toRemove.setNext(null);
          toRemove.setPrev(null);
          this.count--;
          //return removed element
          return toRemove;
        }
        return null;
      }

      //remove particular node, given reference
      public void remove(Node n){
        if (n != null){
          //set prev and next nodes to ref each other.
          Node<K,V> next = n.getNext();
          Node<K,V> prev = n.getPrev();
          next.setPrev(prev);
          prev.setNext(next);
          this.count--;
        }
      }

      //return current size
      public int getSize(){
        return this.count;
      }

      //print queue
      public void printQ(){
        Node<K,V> n = this.head.getNext();
        String toPrint = "";
        while (n != this.tail){
          toPrint += n.getValue() + "->";
          n = n.getNext();
        }
        System.out.println(toPrint);
      }

    }

    public LRUCacheRef(int capacity){
      //initialize hashmap and queue
      this.map = new HashMap<K, Node>();
      this.q = new Q();
      this.capacity = capacity;
    }

    public V get(K key){
      Node<K, V> n = this.map.get(key);
      //if key is present, move node to end of queue
      if (n != null){
        this.q.remove(n);
        this.q.enqueue(n);
        return n.getValue();
      }
      return null;
    }

    public void put(K key, V value){
      Node<K, V> n;
      //if key is present, move node to end of queue
      if (map.containsKey(key)){
        n = map.get(key);
        this.q.remove(n);
      } else {
        //create new node. if capacity is reached, removed first node from queue
        n = new Node(key, value);
        if (q.getSize() == this.capacity){
          Node<K,V> remove = this.q.dequeue();
          map.remove(remove.getKey());
        }
      }
      //add current key-value to front of queue
      this.q.enqueue(n);
      map.put(key, n);
    }

    public void printQ(){
      this.q.printQ();
    }

}

class Solution{
  public static void main(String[] args){

    LRUCacheRef cache = new LRUCacheRef(2);

    cache.put(1, 1);
    cache.put(2, 2);
    System.out.println(cache.get(1));       // returns 1
    cache.put(3, 3);                        // evicts key 2
    System.out.println(cache.get(2));       // returns -1 (not found)
    cache.put(4, 4);                        // evicts key 1
    System.out.println(cache.get(1));       // returns -1 (not found)
    System.out.println(cache.get(3));       // returns 3
    System.out.println(cache.get(4));       // returns 4
    cache.printQ();

  }
}
