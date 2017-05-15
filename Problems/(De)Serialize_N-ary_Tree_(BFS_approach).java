/*
Given an N-ary tree where every node has at-most N children.
Write functions to serialize and deserialize it

To Be Completed.
*/

/*

     5
 3 11 10
3 2  5

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

Class TreeNode{
    int value
    Tree [] children;
    int parentValue;
}


String Serialize(TreeNode root){

    int i=0;
    Queue<Tree> q = new Queue<Integer>();

    q.enqueue(root)
    int parent = 0;
    String result = "";
    while (!q.isEmpty()){

        Tree current = q.dequeue();
        result += current.value + "/" + parent + "-";
        for (int i =0; i < current.children.length(); i++){
            current.children[i].parent = parent;
            q.enqueue(current.children[i]);
        }
        parent++;

    }

    return result;

}

"5/0-3/1-11/1-10/1-3/2-2-5"
Tree Deserialize(String tree)
