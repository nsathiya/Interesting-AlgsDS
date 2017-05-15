/*
Given an N-ary tree where every node has at-most N children.
Write functions to serialize and deserialize it. Nodes can
have duplicate values.

Got this question in an interview. This is the approach I took,
though DFS might be cleaner.

Ex.-
    5
    |
 |~~|~~|
 3 11 10
 |     |
|~|   |~
3 2   5

Serialized Code:
Remember parent position with value.
return "5/0-3/1-11/1-10/1-3/2-2/2-5/4-"

Deserialized Tree:
Use hashmap to keep track of node by position.
Add child accordingly.
Final Tree-
return 5,3,3,2,11,10,5
*/

import java.util.*;

class TreeNode{
  public int value;
  public ArrayList<TreeNode> children;
  public int parentPosition;

  public TreeNode(int value){
    this.value = value;
    this.children = new ArrayList<TreeNode>();
  }

  public void addChild(TreeNode child){
    this.children.add(child);
  }
}

class Solution {

  public static String Serialize(TreeNode root){

    //Queue to keep track of nodes
    Queue<TreeNode> q = new LinkedList<TreeNode>();
    q.add(root);
    int parentPos = 0;
    root.parentPosition = parentPos;
    String result = "";

    while (!q.isEmpty()){
      //increase position for each node
      parentPos++;
      //remove current node and loop through its children
      TreeNode current = q.remove();
      //current component = value+parentPos
      result += current.value + "/" + current.parentPosition + "-";

      for (int i=0; i < current.children.size(); i++){
        current.children.get(i).parentPosition = parentPos;
        q.add(current.children.get(i));
      }

    }

    //return final serialized result
    return result;
  }

  public static TreeNode Deserialize(String str){

    //Initial parsing to get node components
    String[] splitStr = str.split("-");
    //Create map to keep track of positions and nodes
    HashMap<Integer, TreeNode> mmp = new HashMap<Integer, TreeNode>();

    for (int i=0; i<splitStr.length; i++){
      //Parse nodes to get value and parent positions
      String[] component = splitStr[i].split("/");
      int value = Integer.parseInt(component[0]);
      int parent = Integer.parseInt(component[1]);
      TreeNode curr = new TreeNode(value);
      //Put current node in its position
      mmp.put(i+1, curr);
      //If not root, associate child to parent
      if (i>0)
        mmp.get(parent).addChild(curr);
    }

    //Return root
    return mmp.get(1);

  }

  //Print tree recursively, in a DFS manner
  public static void printTree(TreeNode root){
    System.out.print(root.value + ",");
    for (int i=0; i<root.children.size(); i++){
      printTree(root.children.get(i));
    }
  }

  public static void main(String[] args){

    //Create our initial tree
    TreeNode root = new TreeNode(5);
    root.addChild(new TreeNode(3));
    root.addChild(new TreeNode(11));
    root.addChild(new TreeNode(10));
    root.children.get(0).addChild(new TreeNode(3));
    root.children.get(0).addChild(new TreeNode(2));
    root.children.get(2).addChild(new TreeNode(5));

    //Serialize our tree and print
    String serialized = Serialize(root);
    System.out.println("Serialized: " + serialized);

    //Deserialize our code and print
    TreeNode rootDeserialized = Deserialize(serialized);
    System.out.print("Deserialized: ");
    printTree(rootDeserialized);
    System.out.println("");
  }

}
