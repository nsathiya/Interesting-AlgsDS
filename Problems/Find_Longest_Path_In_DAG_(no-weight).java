/*

Given a directed acyclic graph, return longest path of nodes visited.
No weighted edges.

Ex.
0 -> 5 -> 6
     ^
3 -> 4 <- 2
^
1

*/

import java.util.*;

class Graph {

  LinkedList<Integer>[] adjList;
  int size;

  public Graph(int n) {

    //mitigate this check
    this.adjList = new LinkedList[n];
    this.size = n;

    //Initiate our adjacency list
    for (int i=0; i<n; i++){
      this.adjList[i] = new LinkedList<Integer>();
    }
  }

  //add directed edge
  public void addEdge(int src,int dest){
    this.adjList[src].add(dest);
  }

  //DFS recurvive sort and add topological ordering to stack
  public void sort(int curr, Stack<Integer> st, boolean[] visited){

    visited[curr] = true;

    for (int i=0; i<this.adjList[curr].size(); i++)
      if (!visited[this.adjList[curr].get(i)])
        sort(this.adjList[curr].get(i), st, visited);

    st.push(curr);

  }

  //Create topological ordering
  public Stack<Integer> topologicalSort(){

    Stack<Integer> st = new Stack<Integer>();
    //keep track of visited nodes
    boolean[] visited = new boolean[this.size];

    for (int i=0; i<this.size; i++){
      if (!visited[i])
        sort(i, st, visited);
    }

    return st;
  }
}

class Solution {
  public static void main(String[] args){

    int NUM = 7;
    //Make graph object
    Graph g = new Graph(NUM);
    //Add our edges
    g.addEdge(0, 5);
    g.addEdge(5, 6);
    g.addEdge(4, 5);
    g.addEdge(3, 4);
    g.addEdge(2, 4);
    g.addEdge(1, 3);

    int[] dist = new int[NUM];
    //Get our topological order
    Stack<Integer> results = g.topologicalSort();
    Stack<Integer> resultsRev = new Stack<Integer>();

    //Reverse our order so we start from node with no out-degrees
    while(!results.isEmpty()){
      Integer current = results.pop();
      resultsRev.push(current);
    }

    //Go through topological order backwards. Instantiate 1 for
    //no out-degree nodes, then keep update thier adjacent nodes
    //with max node visited.
    while(!resultsRev.isEmpty()){
      int curr = resultsRev.pop();
      if (g.adjList[curr].size() == 0){
        dist[curr] = 1;
      } else {
        for (int i=0; i<g.adjList[curr].size(); i++){
          if (dist[curr] < dist[g.adjList[curr].get(i)] + 1)
            dist[curr] = dist[g.adjList[curr].get(i)] + 1;
        }
      }
    }

    //Find max distance
    int maxDist = 0;
    for (int i=0; i<dist.length; i++)
      maxDist = Math.max(dist[i], maxDist);

    //Print result
    System.out.println("Max number of nodes covered: " + maxDist);

  }
}
