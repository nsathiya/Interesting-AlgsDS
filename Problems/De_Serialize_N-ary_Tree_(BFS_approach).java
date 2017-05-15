/*
Given an N-ary tree where every node has at-most N children.
Write functions to serialize and deserialize it

To Be Completed.
*/

/*

    5
    |
 |~~|~~|
 3 11 10
 |     |
|~|   |~
3 2   5

Node
    int v


hashmap<int, node>

3/5

mmp.get(5).insertChildren(3);


"5/0-3/1-11/1-10/1--3/2-2-5"

i j
mmp.put(1, node(5))

mmp.put(2, node(3));
mmp.get(1).insertChildren(mmp.get(2));

Java.stringify( 5 : [3:[1:[], 2], 11:[5]]

"3-5-10"
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

    Queue<TreeNode> q = new LinkedList<TreeNode>();

    q.add(root);
    int parentPos = 0;
    root.parentPosition = parentPos;
    String result = "";
    while (!q.isEmpty()){

      parentPos++;
      TreeNode current = q.remove();
      result += current.value + "/" + current.parentPosition + "-";
      for (int i=0; i < current.children.size(); i++){
        current.children.get(i).parentPosition = parentPos;
        q.add(current.children.get(i));
      }

    }

    return result;
  }

  public static TreeNode Deserialize(String str){

    String[] splitStr = str.split("-");
    HashMap<Integer, TreeNode> mmp = new HashMap<Integer, TreeNode>();

    for (int i=0; i<splitStr.length; i++){
      String[] component = splitStr[i].split("/");
      int value = Integer.parseInt(component[0]);
      int parent = Integer.parseInt(component[1]);
      TreeNode curr = new TreeNode(value);
      mmp.put(i+1, curr);
      if (i>0)
        mmp.get(parent).addChild(curr);
    }

    return mmp.get(1);

  }

  public static void printTree(TreeNode root){
    System.out.print(root.value + ",");
    for (int i=0; i<root.children.size(); i++){
      printTree(root.children.get(i));
    }
  }

  public static void main(String[] args){

    TreeNode root = new TreeNode(5);
    root.addChild(new TreeNode(3));
    root.addChild(new TreeNode(11));
    root.addChild(new TreeNode(10));
    root.children.get(0).addChild(new TreeNode(3));
    root.children.get(0).addChild(new TreeNode(2));
    root.children.get(2).addChild(new TreeNode(5));

    String serialized = Serialize(root);
    System.out.println("Serialized: " + serialized);

    TreeNode rootDeserialized = Deserialize(serialized);
    System.out.print("Deserialized: ");
    printTree(rootDeserialized);
    System.out.println("");
  }

}
