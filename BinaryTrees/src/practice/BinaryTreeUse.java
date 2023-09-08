package practice;
import java.util.*;

class Pair<T,V> {
	public T first;
	public V second;
}

public class BinaryTreeUse {
	
// Get root to node path.
	
	public static ArrayList<Integer> getRootToNodePath(BinaryTreeNode<Integer> root, int data){
		
		if(root==null) {
			return null;
		}
		
		if(root.data==data) {
			ArrayList<Integer> output= new ArrayList<>();
			output.add(root.data);
			return output;
		}
		
		ArrayList<Integer> leftOutput= getRootToNodePath(root.left, data);
		
		if(leftOutput!=null) {
			leftOutput.add(root.data);
			return leftOutput;
		}
		
		ArrayList<Integer> rightOutput= getRootToNodePath(root.right, data);
		
		if(rightOutput!=null) {
			rightOutput.add(root.data);
			return rightOutput;
		}
		
		return null;
	}
	
// Checking whether a tree is BST or not. Again a good solution to use.   O( n )
	
	public static boolean isBST3( BinaryTreeNode<Integer> root, int min, int max) {
		
		if(root==null) {
			return true;
		}
		
		if(root.data<min || root.data>max) {
			return false;
		}
		
		boolean isLeftOkay=isBST3(root.left, min, root.data-1);
		boolean isRightOkay=isBST3(root.right, root.data, max);
		
		return isLeftOkay && isRightOkay;
	}
	
// Checking whether a tree is BST or not ( Better one ). 
	
	public static Pair<Boolean, Pair<Integer, Integer>> isBST2( BinaryTreeNode<Integer> root){
		
		if(root==null) {
			Pair <Boolean, Pair<Integer, Integer>> output= new Pair<Boolean, Pair<Integer, Integer>>();
			output.first=true;
			output.second= new Pair<Integer, Integer>();
			output.second.first=Integer.MAX_VALUE;
			output.second.second=Integer.MIN_VALUE;
			return output;
		}
		
		Pair< Boolean, Pair<Integer, Integer>> leftOutput= isBST2(root.left);
		Pair< Boolean, Pair<Integer, Integer>> rightOutput= isBST2(root.right);
		
		int min=Math.min(root.data, Math.min(leftOutput.second.first, rightOutput.second.first));
		int max=Math.max(root.data, Math.max(leftOutput.second.second, rightOutput.second.second));
		
		boolean isBST= (root.data>leftOutput.second.second)&& (root.data<=rightOutput.second.first)&& leftOutput.first && rightOutput.first;
		
		Pair<Boolean, Pair<Integer, Integer>> output= new Pair<Boolean, Pair<Integer, Integer>>();
		output.first=isBST;
		output.second= new Pair<Integer, Integer>();
		output.second.first=min;
		output.second.second=max;
		return output;
	}
	
// Checking whether a tree is BST or not.  O( n^2 )
/* Here, the problem is we are making two calls to the left and right one by one, first one to get the maximum and minimum out of them and the second one to check whether both of them are BST or not. */
	
	public static int minimum(BinaryTreeNode<Integer> root) {
		if(root==null) {
			return Integer.MAX_VALUE;
		}
		
		return Math.min(root.data, Math.min(minimum(root.left), minimum(root.right)));
	}
	
	public static int maximum(BinaryTreeNode<Integer> root) {
		if(root==null) {
			return Integer.MIN_VALUE;
		}
		
		return Math.max(root.data, Math.max(maximum(root.left), maximum(root.right)));
	}
	
	public static boolean isBST( BinaryTreeNode<Integer> root) {
		
		if(root==null) {
			return true;
		}
		
		int leftMax=maximum(root.left);
		int rightMin=minimum(root.right);
		
		if(root.data<=leftMax) {
			return false;
		}
		
		if(root.data>rightMin) {
			return false;
		}
		
		boolean isLeftBST= isBST(root.left);
		boolean isRightBST= isBST(root.right);
		
		return isLeftBST && isRightBST;
	}
	
// Printing the nodes of a Binary tree that are at k distance apart from a target node.
	
	public static void nodesAtDistanceK(BinaryTreeNode<Integer> root, int node, int k) {
	    //Your code goes here
		ArrayList<Integer> result = new ArrayList<>();
        findNodesAtDistanceK(root, node, k, result);
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
	}
	
	private static int findNodesAtDistanceK(BinaryTreeNode<Integer> root, int node, int k, ArrayList<Integer> result) {
        if (root == null) {
            return -1;
        }

        if (root.data == node) {
            printNodesAtDepthK(root, k, result);
            return 0;
        }

        int leftDistance = findNodesAtDistanceK(root.left, node, k, result);
        if (leftDistance != -1) {
            if (leftDistance + 1 == k) {
                result.add(root.data);
            } else {
                printNodesAtDepthK(root.right, k - leftDistance - 2, result);
            }
            return leftDistance + 1;
        }

        int rightDistance = findNodesAtDistanceK(root.right, node, k, result);
        if (rightDistance != -1) {
            if (rightDistance + 1 == k) {
                result.add(root.data);
            } else {
                printNodesAtDepthK(root.left, k - rightDistance - 2, result);
            }
            return rightDistance + 1;
        }

        return -1;
    }
	
	private static void printNodesAtDepthK(BinaryTreeNode<Integer> root, int k, ArrayList<Integer> result) {
        if (root == null || k < 0) {
            return;
        }
        if (k == 0) {
            result.add(root.data);
            return;
        }
        printNodesAtDepthK(root.left, k - 1, result);
        printNodesAtDepthK(root.right, k - 1, result);
    }
	
// Creating a tree from its in-order and pre-order array.
	
	public static BinaryTreeNode<Integer> buildTreeHelper(int[] in, int[] pre, int inStart, int inEnd, int preStart, int preEnd){
		
		if(inStart>inEnd) {
			return null;
		}
		
		int rootData= pre[preStart];
		BinaryTreeNode<Integer> root= new BinaryTreeNode<Integer>(rootData);
		
		int rootIndex=-1;
		
		for(int i= inStart; i<=inEnd; i++) {
			if(in[i]==rootData) {
				rootIndex=i;
				break;
			}
		}
		
		if(rootIndex==-1) {
			return null;
		}
		
		int leftInStart=inStart;
		int leftInEnd=rootIndex-1;
		int leftPreStart=preStart+1;
		int leftPreEnd= leftInEnd-leftInStart+leftPreStart;
		int rightInStart=rootIndex+1;
		int rightInEnd=inEnd;
		int rightPreStart=leftPreEnd+1;
		int rightPreEnd=preEnd;
		
		root.left=buildTreeHelper(in, pre, leftInStart, leftInEnd, leftPreStart, leftPreEnd);
		root.right=buildTreeHelper(in, pre, rightInStart, rightInEnd, rightPreStart, rightPreEnd);
		
		return root;
	}
	 
	public static BinaryTreeNode<Integer> buildTree(int[] in, int[] pre){
		
		return buildTreeHelper(in, pre, 0, in.length-1, 0, pre.length-1);
	}
	
// Printing the tree post-order
	
	public static void printPostOrder(BinaryTreeNode<Integer> root) {
		
		if(root==null) {
			return;
		}
		
		printPreOrder(root.left);
		printPreOrder(root.right);
		System.out.print(root.data+" ");
	}
	
// Printing the tree in pre-order
	
	public static void printPreOrder(BinaryTreeNode<Integer> root) {
		
		if(root==null) {
			return;
		}
		
		System.out.print(root.data+" ");
		printPreOrder(root.left);
		printPreOrder(root.right);
	}
	
// Printing the tree in in-order
	
	public static void printInOrder(BinaryTreeNode<Integer> root) {
		
		if(root==null) {
			return;
		}
		
		printInOrder(root.left);
		System.out.print(root.data+" ");
		printInOrder(root.right);
	}
	
// Finding diameter of a tree : O( n * height of the tree )
	
	public static int diameter(BinaryTreeNode<Integer> root) {
				
		if(root==null) {
			return 0;
		}
		
		int option1= height(root.left)+height(root.right);
		int option2= diameter(root.left);
		int option3= diameter(root.right);
		
		return Math.max(Math.max(option1, option2), option3);
	}
	
// Finding a diameter ( improved version ) : O( n )
	public static Pair<Integer, Integer> heightDiameter(BinaryTreeNode<Integer> root){
		
		if(root==null) {
			Pair<Integer, Integer> output = new Pair<>();
			output.first=0;
			output.second=0;
			return output;
		}
		
		Pair<Integer, Integer> lo= heightDiameter(root.left);
		Pair<Integer, Integer> ro= heightDiameter(root.right);
		
		int height= 1+Math.max(lo.first, ro.first);
		
		int option1= lo.first+ro.first;
		int option2= lo.second;
		int option3= ro.second;
		
		int diameter= Math.max(option1, Math.max(option2, option3));
		 
		Pair<Integer, Integer> output= new Pair<>();
		output.first= height;
		output.second=diameter;
		
		return output;
	}
	
// Height of a tree
	
	public static int height(BinaryTreeNode<Integer> root) {
		//Your code goes here

		if(root==null){
			return 0;
		}

		int smallAnsLeft= height(root.left);
		int smallAnsRight=height(root.right);

		if(smallAnsLeft>smallAnsRight){
			return 1+smallAnsLeft;
		}
		else{
			return 1+smallAnsRight;
		}
	}
	
// Whether a tree is balanced or not
	
	public static boolean isBalanced(BinaryTreeNode<Integer> root) {
		if(root==null) {
			return true;
		}
		
		int leftHeight=height(root.left);
		int rightHeight=height(root.right);
		
		if(Math.abs(leftHeight-rightHeight)>1) {
			return false;
		}
		
		boolean isLeftBalanced=isBalanced(root.left);
		boolean isRightBalanced=isBalanced(root.right);
		
		return isLeftBalanced && isRightBalanced;
	}
	
// Improved version of whether a tree is balanced or not.  O( n )
	
/* In this we are actually doing constant work at each node, just checking for base case and evaluating isBal and height. Hence our time complexity improved drastically from O( n^2 ) to O( n ). */
	
	public static BalancedTreeReturn isBalancedBetter(BinaryTreeNode<Integer> root) {
		
		if(root==null) {
			int height=0;
			boolean isBal=true;
			
			BalancedTreeReturn ans= new BalancedTreeReturn();
			ans.height=height;
			ans.isBalanced= isBal;
			return ans;
			
		}
		
		BalancedTreeReturn leftOutput= isBalancedBetter(root.left);
		BalancedTreeReturn rightOutput= isBalancedBetter(root.right);
		boolean isBal=true;
		int height=1+ Math.max(leftOutput.height, rightOutput.height);
		
		if(Math.abs(leftOutput.height-rightOutput.height)>1){
			isBal=false;
		}
		
		if(!leftOutput.isBalanced || !rightOutput.isBalanced) {
			isBal=false;
		}
		
		BalancedTreeReturn ans= new BalancedTreeReturn();
		ans.height=height;
		ans.isBalanced= isBal;
		
		return ans;
	}

// Printing the tree
	
	public static void printTree(BinaryTreeNode<Integer> root) {
		
		if(root==null) {
			return;
		}
		
		String toBePrinted=root.data+"";
		
		if(root.left!=null) {
			toBePrinted+="L:"+root.left.data+",";
		}
		
		if(root.right!=null) {
			toBePrinted+="R:"+root.right.data;
		}
		
		System.out.println(toBePrinted);
		printTree(root.left);
		printTree(root.right);
	}

// Program to take input
	
	public static BinaryTreeNode<Integer> takeInput(Scanner s){
		
		System.out.println("Enter root data");
		int rootData= s.nextInt();
		
		if(rootData==-1) {
			return null;
		}
		
		BinaryTreeNode<Integer> root= new BinaryTreeNode<>(rootData);
		
		root.left=takeInput(s);
		root.right=takeInput(s);
		
		return root;
		
	}

// Taking input level wise
	
	public static BinaryTreeNode<Integer> takeInputLevelWise() {
		
		Scanner s= new Scanner(System.in);
		
		QueueUsingLL<BinaryTreeNode<Integer>> pendingNodes= new QueueUsingLL<>();
		
		System.out.println("Enter root data.");
		int rootData= s.nextInt();
		
		if(rootData==-1) {
			return null;
		}
		
		BinaryTreeNode<Integer> root= new BinaryTreeNode<>(rootData);
		pendingNodes.enqueue(root);
		
		while(!pendingNodes.isEmpty()) {
			BinaryTreeNode<Integer> front;
			
			try {
				front= pendingNodes.dequeue();
			} catch (QueueEmptyException e) {
				return null;
			}
			
			System.out.println("Enter left child of "+ front.data);
			int leftChild= s.nextInt();
			if(leftChild!=-1) {
				BinaryTreeNode<Integer> child= new BinaryTreeNode<Integer>(leftChild);
				pendingNodes.enqueue(child);
				front.left=child;
			}
			
			System.out.println("Enter right child of "+ front.data);
			int rightChild= s.nextInt();
			if(rightChild!=-1) {
				BinaryTreeNode<Integer> child= new BinaryTreeNode<Integer>(rightChild);
				pendingNodes.enqueue(child);
				front.right=child;
			}
		}
		
		return root;
	}

// Printing level wise
	
	public static void printLevelWise(BinaryTreeNode<Integer> root) {
		//Your code goes here

		if(root==null){
			return;
		}

		Queue<BinaryTreeNode<Integer>> pendingNodes=new LinkedList<>();
		pendingNodes.add(root);

		while(!pendingNodes.isEmpty()){

			String toBePrinted="";
			BinaryTreeNode<Integer> front= pendingNodes.poll();
			toBePrinted+=front.data+":";

			if(front.left!=null){
				pendingNodes.add(front.left);
				toBePrinted+="L:"+front.left.data+",";
			}else{
				toBePrinted+="L:"+"-1,";
			}

			if(front.right!=null){
				pendingNodes.add(front.right);
				toBePrinted+="R:"+front.right.data;
			}else{
				toBePrinted+="R:"+"-1";
			}

			System.out.println(toBePrinted);
		}
	}
	
// Returning the largest among the nodes : O(n)
	
	public static int largest(BinaryTreeNode<Integer> root) {
		
		if(root==null) {
			return -1;
		}
		
		int largestRight= largest(root.left);
		int largestLeft=largest(root.right);
		
		return Math.max(root.data, Math.max(largestLeft, largestRight));
	}
	
// Removing leaf nodes
	
	public static BinaryTreeNode<Integer> removeLeaves(BinaryTreeNode <Integer> root){
		if(root==null) {
			return null;
		}
		
		if(root.left==null && root.right==null) {
			return null;
		}
		
		root.left=removeLeaves(root.left);
		root.right=(root.right);
		
		return root;
	}
	
// Returning number of leaf nodes
	
	public static int noOfLeafNodes(BinaryTreeNode<Integer> root) {
		
		if(root==null) {
			return 0;
		}
		
		if(root.left==null && root.right==null) {
			return 1;
		}
		
		return noOfLeafNodes(root.left)+noOfLeafNodes(root.right);
	}
// Returning number of nodes : O(n)
	
	public static int countNodes(BinaryTreeNode<Integer> root) {
		if(root==null) {
			return 0;
		}
		
		int ans=1;
		ans+=countNodes(root.left);
		ans+=countNodes(root.right);
		return ans;
	}
	
// Printing elements at depth K
	public static void printAtDepthK( BinaryTreeNode<Integer> root, int k) {
		
		if(k<0 || root==null) {
			return;
		}
		
		if(k==0) {
			System.err.print(root.data+" ");
			return;
		}
		
		printAtDepthK(root.left, k-1);
		printAtDepthK(root.right, k-1);
	}

	public static void main(String[] args) {
		
//		Scanner s= new Scanner(System.in);
		
//		BinaryTreeNode<Integer> root= takeInputLevelWise();
//		printTree(root);
//		System.out.println("No of nodes: "+countNodes(root));
//		System.out.println("Is the tree balanced ? "+isBalancedBetter(root).isBalanced);
//		System.out.println("Diameter of the tree :"+ heightDiameter(root).second);
//		System.out.println("Height of the tree :"+ heightDiameter(root).first);
//		s.close();
//		BinaryTreeNode<Integer> root= new BinaryTreeNode<Integer>(1);
//		
//		BinaryTreeNode<Integer> node1= new BinaryTreeNode<Integer>(2);
//		root.left=node1;
//		
//		BinaryTreeNode<Integer> node2= new BinaryTreeNode<Integer>(3);
//		root.right=node1;
		
//		int[] inOrder= {4,2,5,1,3,7};
//		int[] preOrder= {1,2,4,5,3,7};
//		
//		printTree(buildTree(inOrder, preOrder));
		
		BinarySearchTree bst = new BinarySearchTree();
		
		bst.insertData(10);
		bst.insertData(20);
		bst.insertData(5);
		bst.insertData(15);
		bst.insertData(3);
		bst.insertData(7);
		bst.deleteData(10);
		
		bst.printTree();
	}

}
