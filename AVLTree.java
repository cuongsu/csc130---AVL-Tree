import java.io.*;

public class AVLTree {

	protected AVLNode rootnode; // define root node

	class AVLNode {
		public AVLNode left;
		public AVLNode right;
		public AVLNode root;
		public int value;
		public int balance;

		public AVLNode(int k) {
			left = right = root = null;
			balance = 0;
			value = k;
		}

		public String toString() {
			return "" + value;
		}
	}

	// Insert mehod. k is element to insert into the tree
	public void insert(int k) {
		// create new node
		AVLNode n = new AVLNode(k);
		// recursively call insert mehod
		insertAVL(this.rootnode, n);
	}

	//t : The AVLNode to evaluate for recursive insertion
	public void insertAVL(AVLNode t, AVLNode x) {
		if (t == null) {
			this.rootnode = x;
		} else {
			if (x.value < t.value) {
				if (t.left == null) {
					t.left = x;
					x.root = t;
					// check for violation condition
					reBalance(t);
				} else {
					insertAVL(t.left, x);
				}
			} else if (x.value > t.value) {
				if (t.right == null) {
					t.right = x;
					x.root = t;
					// check for violation condition
					reBalance(t);
				} else {
					insertAVL(t.right, x);
				}
			} else {

			}
		}
	}

	// balance the violating subtree
	public void reBalance(AVLNode avl) {
		setBalance(avl);
		int balance = avl.balance;
		if (balance == -2) {
			if (height(avl.left.left) >= height(avl.left.right)) {
				avl = singleRotateWithRight(avl);
			} else {
				avl = doubleRotateWithRight(avl);
			}
		} else if (balance == 2) {
			if (height(avl.right.right) >= height(avl.right.left)) {
				avl = singleRotateWithLeft(avl);
			} else {
				avl = doubleRotateRightLeft(avl);
			}
		}
		if (avl.root != null) {
			reBalance(avl.root);
		} else {
			this.rootnode = avl;
			System.out.println("AVL Tree is balanced\n\n");
		}
	}

	public AVLNode singleRotateWithLeft(AVLNode t) {
		AVLNode t1 = t.right;
		t1.root = t.root;
		t.right = t1.left;
		if (t.right != null) {
			t.right.root = t;
		}
		t1.left = t;
		t.root = t1;
		if (t1.root != null) {
			if (t1.root.right == t) {
				t1.root.right = t1;
			} else if (t1.root.left == t) {
				t1.root.left = t1;
			}
		}
		setBalance(t);
		setBalance(t1);
		return t1;
	}

	public AVLNode singleRotateWithRight(AVLNode t) {
		AVLNode t1 = t.left;
		t1.root = t.root;
		t.left = t1.right;
		if (t.left != null) {
			t.left.root = t;
		}
		t1.right = t;
		t.root = t1;
		if (t1.root != null) {
			if (t1.root.right == t) {
				t1.root.right = t1;
			} else if (t1.root.left == t) {
				t1.root.left = t1;
			}
		}
		setBalance(t);
		setBalance(t1);
		return t1;
	}

	public AVLNode doubleRotateWithRight(AVLNode k3) {
		k3.left = singleRotateWithLeft(k3.left);
		return singleRotateWithRight(k3);
	}

	public AVLNode doubleRotateRightLeft(AVLNode k3) {
		k3.right = singleRotateWithRight(k3.right);
		return singleRotateWithLeft(k3);
	}

	//height() method, which is used in calculating the balance factor between two subtrees of a parent AVLNode
	private int height(AVLNode avl) {
		if (avl == null) {
			return -1;
		}
		if (avl.left == null && avl.right == null) {
			return 0;
		} else if (avl.left == null) {
			return 1 + height(avl.right);
		} else if (avl.right == null) {
			return 1 + height(avl.left);
		} else {
			return 1 + maximum(height(avl.left), height(avl.right));
		}
	}

	private int maximum(int a, int b) {
		if (a >= b) {
			return a;
		} else {
			return b;
		}
	}

	// check the violation through difference of height of left and right
	private void setBalance(AVLNode avl) {
		avl.balance = height(avl.right) - height(avl.left);
	}

	// Returns tree in INORDER traversal
	private void inOrder(AVLNode node) {
		if (node != null) {
			inOrder(node.left);
			System.out.println(node.value);
			inOrder(node.right);
		}
	}

	void inOrder() {
		inOrder(rootnode);
	}

	// Check for Tree is Empty or not

	boolean isEmpty() {
		return rootnode == null;
	}
    
   private boolean find(AVLNode node, int x) {	
      if(node == null){ 
         return false;
      }else if(node.value>x){
	      return find(node.left,x);
		} else if(node.value<x) {
			return find(node.right,x);
		} else if(node.value==x) {
			return true;
		} else {
         return false;
	   }
   }
      
   public void remove (int x) {
      removeAVL(this.rootnode, x);
   }
   
   public void removeAVL(AVLNode x, int y) {
      if (x==null) {
         return;
      } else {
         if(x.value > y) {
            removeAVL(x.left, y);
         } else if (x.value < y) {
            removeAVL(x.right, y);
         } else if(x.value == y) {
            removeFoundNode(x);
         }
      }
   }
   
   public void removeFoundNode(AVLNode y) {
      AVLNode r;
      if(y.left == null || y.right == null) {
         if(y.root == null) {
            this.rootnode = null;
            y = null;
            return;
         }
         r = y;
      } else {
         r = successor(y);
         y.value = r.value;
      }
      
      AVLNode p;
      if(r.left != null) {
         p = r.left;
      } else {
         p = r.right;
      }
      
      if(p != null) {
         p.root = r.root;
      }
      
      if(r.root == null) {
         this.rootnode = p;
      } else {
         if(r == r.root.left) {
            r.root.left = p;
         } else {
            r.root.right = p;
         }
         reBalance(r.root);
      }
      r = null;
   }
   
   public AVLNode successor(AVLNode x) {
      if(x.right != null) {
         AVLNode r = x.right;
         while(r.left != null) {
            r = r.left;
         }
         return r;
      } else {
         AVLNode p = x.root;
         while(p != null && x == p.right) {
            x = p;
            p = x.root;
         }
         return p;
      }
   }

   
   public static void main(String[] args) {
		AVLTree tree = new AVLTree();
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
		int choice = -1;
		int value;

		while (true) {
			System.out.println("Select an option"); // Everytime user have to select one option
			System.out.println("1-Insert a node"); // At a time user can insert only one node
			System.out.println("2-Find a node"); // At a time user can find only one node
			System.out.println("3-Display tree in INORDER Traversal");
			System.out.println("4-Remove a node");
         System.out.println("0-EXIT");
			try {
				choice = Integer.valueOf(inputReader.readLine()).intValue();
				switch (choice) {
				case 0:
					System.out.println("Exit");
					return;
				case 1:
					System.out.println("Enter a value to insert");
					value = Integer.parseInt(inputReader.readLine());
					tree.insert(value);
					break;
				case 2:
               if (tree.isEmpty()) {
						System.out.println("Sorry....find not possible..AVL Tree is empty!\n\n");
					} else {
						System.out.println("Enter a value to find");
                  value = Integer.parseInt(inputReader.readLine());
                  if(tree.find(tree.rootnode, value) == true) {
                     System.out.println("Node has been found in the tree\n\n");
					   } else {
                     System.out.println("Node NOT found in the tree\n\n");
                  }
               }
					break;
				case 3:
					if (tree.isEmpty()) {
						System.out.println("Sorry....in order traversal not possible..AVL Tree is empty!\n\n");
					} else {
						System.out.println("The inorder Traversal of AVL Tree is:");
						tree.inOrder();
						System.out.println();
					}
					break;
            case 4:
               if(tree.isEmpty()) {
                  System.out.println("Sorry....remove not possible..AVL Tree is empty!\n\n");
               }else{
                 	System.out.println("Enter a value to remove");
                  value = Integer.parseInt(inputReader.readLine());
                  if(tree.find(tree.rootnode, value) == true){
                     tree.remove(value);
                  }else{
                     System.out.println("Sorry....remove not possible..node not contained in tree!\n\n");
                  }
               }
               break;
				default:
					System.out.println("Invalid option. \n Try Again!!!");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				System.out.println("Incorrect choice");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}