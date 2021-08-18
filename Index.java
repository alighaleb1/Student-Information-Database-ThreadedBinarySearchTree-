package ThreadedBinarySearchTree;

//Threaded Binary Search Tree
public class Index 
{
	//Root of the Threaded Binary Search tree.
	private IndexRecord top;
	
	public Index() 
	{
		top = null;
	}
	/*Insert Id number, first name, last name(key) in ascending order 
		with  position (where) in main database.*/
	public void insert(IndexRecord newTree)
	{
		IndexRecord prev=null, rover=top; 
		boolean isleft=false; //which side the node is being inserted
		boolean isthemostleft = true; //if most left node the left thread is always false.
		boolean isthemostright = true;// if most right node the right thread is always false.

		//Successor(higher node than the node): predecessor(lower value than the node).
		IndexRecord successor = null ,  predecessor = null; 
		
		//Set node(being inserted) threads automatically to true and later false if it is most right or left. 
		newTree.setIsleftthreaded(true); 
		newTree.setIsrightthreaded(true);
		
		if(top == null)
		{
			top = newTree;
			//if node being inserted at the top threads are always false.
			newTree.setIsleftthreaded(false);
			newTree.setIsrightthreaded(false);
		}
		else
		{	
			//Check which side the node is going.
			while(rover != null)
			{ 
				prev = rover;
				if(rover.getKey().compareTo(newTree.key)<0)
				{	
					isleft = false;
					isthemostleft=false;
					IndexRecord s = getSuccessor(rover); 
					rover = rover.getRight();
					if(s == null || s == rover) //Make sure rover doesn't go down a right thread.
					{
						break;
					}
				}
				else
				{				
					isthemostright = false;
					isleft= true;		
					IndexRecord p = getPredecessor(rover);
					rover = rover.getLeft();
					if( p == null|| p == rover)//Make sure rover doesn't go down a left thread.
					{
						break;
					}
				}
			}
			
			if(isleft) // Insert Node on the left.
			{
				//Insert node
				prev.setLeft(newTree);
				
				//Set correct thread.
				prev.setIsleftthreaded(false);
				if(isthemostleft==true )
				{
					newTree.setIsleftthreaded(false);
				}
				
				//Get successor and predecessor.
				successor = getSuccessor(newTree);
				if(newTree.getIsleftthreaded() == true)
				{
					predecessor = getPredecessor(newTree);
				}
			
				//Set successor and predecessor.
			   if(newTree.getIsrightthreaded()==true)
				{
					newTree.setRight(successor);
				}
			   if(newTree.getIsleftthreaded()==true)
				{
					newTree.setLeft(predecessor);
				}
			}
			else //Insert Node on the right.
			{
				//Insert node
				prev.setRight(newTree);
				
				//Set if node is threaded.
				prev.setIsrightthreaded(false);
				if(isthemostright == true)
				{
					newTree.setIsrightthreaded(false);
				}
				
				//Get predecessor and successor.
				 predecessor = getPredecessor(newTree);
				if(newTree.getIsrightthreaded() == true)
				{
					successor = getSuccessor(newTree);
				}
				
				//Set successor and predecessor.
			   if(newTree.getIsrightthreaded()==true)
				{
					newTree.setRight(successor);
				}
			    if(newTree.getIsleftthreaded()==true)
				{
					newTree.setLeft(predecessor);
				}
			}		
		}
	}
	
	//A method to get the successor of a node in order to thread correctly or correct threads while inserting new nodes.
	private IndexRecord getSuccessor(IndexRecord newNode) 
	{
		IndexRecord rover = top, parent = null;	  
			parent = top;
			while(rover.getKey().compareTo(newNode.key) != 0)
			{	
				if(rover.getKey().compareTo(newNode.key)<0)
				{
					rover = rover.getRight();
				}
				else
				{
					parent  = rover;
					rover = rover.getLeft();
				}
			}
		return parent;
	}
	
	//A method to get the predecessor of a node in order to thread correctly or correct threads while inserting new nodes.
	private IndexRecord getPredecessor(IndexRecord newNode)
	{
		IndexRecord rover = top, parent = null;
			parent = top;
			while(rover.getKey().compareTo(newNode.key) != 0)
			{	
				if(rover.getKey().compareTo(newNode.key)<0)
				{
					parent = rover;
					rover = rover.getRight();
				}
				else
				{
					rover = rover.getLeft();
				}
			}			
		return parent;
	}
	
	//find a student through IndexRecord.
	public int find(String s)
	{
		IndexRecord rover = top;
		if(rover != null)
		{
			//While loop that ends if rover reaches the node that is being searched for.
			while(rover.getKey().compareTo(s) != 0 )
			{
				if(rover.getKey().compareTo(s)<0)
				{
					IndexRecord i = getSuccessor(rover);
					rover = rover.getRight();
					if(i == null || i == rover || rover == null) //Exit loop before going down a right thread.
					{
						rover = null;
						break;
					}
				}
				else
				{
					IndexRecord c = getPredecessor(rover);
					rover = rover.getLeft();
					if( c == null|| c == rover || rover == null)//Exit loop before going down a left thread.
					{
						rover = null;
						break;
					}
				}	
			}
		}
		return(rover == null? -1: rover.getWhere()); // return node location or -1 if node doesn't exist.
	}
	//Delete a student from IndexRecord.
	public boolean delete(String s)
	{
		IndexRecord parent = top, rover = top;
		boolean isleft = false; 
		
		//Check which side node is on.
		while(rover.getKey().compareTo(s)!= 0 )
		{
			parent = rover;
			if(rover.getKey().compareTo(s)<0)
			{
				isleft = false;
				rover = rover.getRight();
			}
			else
			{
				isleft = true;
				rover = rover.getLeft();
			}	
		}
		
		//Delete node with no children
		//If statement for all conditions of a node with no children.
		if(rover.getIsleftthreaded() && rover.getIsrightthreaded() || 
				rover.getLeft() == null && rover.getIsrightthreaded() || 
				rover.getIsleftthreaded() && rover.getRight() == null )
		{	
			if(rover == top)
			{	
				top = null; // If the node is the top node set it to null to delete it.
			}
			else if(isleft)
			{
				//Check if it most left node to set correct thread.
				boolean ismostleft = false; 
				if(rover.getLeft() == null) 
				{
					ismostleft=true;
				}
				//Delete the node and set the new correct thread.
				parent.setLeft(rover.getLeft());
				if(!ismostleft)
				{
					parent.setIsleftthreaded(true);
				}
			}
			else 
			{
				//Check if it the most right node to set correct thread.
				boolean ismostright = false;
				if(rover.getRight() == null)
				{
					ismostright=true;
				}
				//Delete the node and set the new correct thread.
				parent.setRight(rover.getRight());
				if(!ismostright)
				{
					parent.setIsrightthreaded(true);
				}
			}
		}
		//Delete node with one right child
		else if(rover.getLeft() == null || rover.getIsleftthreaded() && !rover.getIsrightthreaded())
		{
			if(rover == top)
			{
				//Replace top node with the right child.
				top = top.getRight(); 
				top.setIsleftthreaded(false);
			}
			//If node on the left with a right child.
			else if(isleft) 
			{
				 //Delete node by setting the right child in the location of the node being deleted.
				IndexRecord pred = rover.getLeft();
				parent.setLeft(rover.getRight());
				IndexRecord checker = parent.getLeft();
				
				//Set the new left of the child.
				if(parent.getLeft().getIsleftthreaded()&&parent.getLeft().getIsrightthreaded()
					||parent.getLeft().getIsleftthreaded()&&!parent.getLeft().getIsrightthreaded())
				{
					checker.setLeft(pred);
				}
				else if(!parent.getLeft().getIsleftthreaded()&&parent.getLeft().getIsrightthreaded()
						||!parent.getLeft().getIsleftthreaded()&&!parent.getLeft().getIsrightthreaded())
				{
					IndexRecord lastleft = parent.getLeft();
					lastleft = getMostleft(lastleft);
					lastleft.setLeft(pred);
				}
			}
			//Node on the right with a right child.
			else 
			{
				//Delete node by setting the right child to the in the location of the node being deleted..
				parent.setRight(rover.getRight());
				//Set the new left of the child.
				if(parent.getRight().getIsleftthreaded() && parent.getRight().getIsrightthreaded()
						||parent.getRight().getIsleftthreaded()&&!parent.getRight().getIsrightthreaded())
				{
					parent.getRight().setLeft(parent);
				}
				else if(!parent.getRight().getIsleftthreaded()&&!parent.getRight().getIsrightthreaded()
						||!parent.getRight().getIsleftthreaded()&&parent.getRight().getIsrightthreaded())
				{
					IndexRecord lastleft = parent.getRight();
					lastleft = getMostleft(lastleft);
					lastleft.setLeft(parent);
				}
			}
		}
		//Delete Node with a left child
		else if(rover.getRight() == null || rover.getIsrightthreaded() && !rover.getIsleftthreaded())
		{
			if(rover == top)
			{
				//Delete top node by setting top to the left child.
				top = top.getLeft();
				top.setIsrightthreaded(false);
			}
			//Node on the left with a left child
			else if(isleft)
			{
				//Delete node by setting the left child in the location of the node being deleted.
				IndexRecord rightthread = rover.getRight();
				parent.setLeft(rover.left);
				//set the new right of the node.
				if(parent.getLeft().getIsrightthreaded()&&parent.getLeft().getIsleftthreaded()
						|| parent.getLeft().getIsrightthreaded()&&!parent.getLeft().getIsleftthreaded()
						)
				{																																		
					parent.getLeft().setRight(parent);
				}
				else if(!parent.getLeft().getIsrightthreaded()&&parent.getLeft().getIsleftthreaded()
						||!parent.getLeft().getIsrightthreaded()&&!parent.getLeft().getIsleftthreaded())
				{
					IndexRecord mostright = parent.getLeft();
					mostright = getMostright(mostright);
					mostright.setRight(rightthread);
				}
				
			}
			else
			{
				///Delete Node by setting the node being deleted to the right child.
				IndexRecord succ = rover.getRight();
				parent.setRight(rover.left);
				IndexRecord checker = parent.getRight();
				//Set the new right of the node..
				if(checker.getIsleftthreaded() && checker.getIsrightthreaded()
						||!checker.getIsleftthreaded()&& checker.getIsrightthreaded())
				{
						parent.getRight().setRight(succ);
				}
				else if(checker.getIsleftthreaded()&&!checker.getIsrightthreaded()
						||!checker.getIsrightthreaded()&&!checker.getIsleftthreaded())
				{
					IndexRecord mostright = parent.getRight();
					mostright = getMostright(mostright);
					mostright.setRight(succ);
				}
			}
		}
		//Delete Node with 2 children.
		else if(!rover.getIsleftthreaded() && !rover.getIsrightthreaded())
		{
			//Get successor and parent of the successor.
			IndexRecord successor = getS(rover,true);
			IndexRecord prev = getS(rover, false);
			if(rover == top)
			{
				// Delete top with 3 nodes only.
				top = successor; 
				
				//Delete top by setting top to successor.
				if(successor.getIsleftthreaded() && successor.getRight() == null)
				{
					top.setIsleftthreaded(false);
					top.setRight(null);
					top.setLeft(parent.getLeft());
					
					//Set the right nodes to left and right of top.
					setRight(top,null,0,1);
				}
				
				//Deleting top with more than 3 nodes
				//If successor is both right left and right threaded.
				else if (successor.getIsleftthreaded() && successor.getIsrightthreaded())
				{
					//Set successor in the place of the node being deleted with correct left and right nodes
					prev = getS(rover, false);
					prev.setIsleftthreaded(true);
					prev.setLeft(top);
					top.setRight(rover.getRight());
					top.setLeft(rover.getLeft());
					
					//Set correct successor for the left side of the node being deleted.
					setRight(rover,top,1,0);
					top.setIsleftthreaded(false);
					top.setIsrightthreaded(false);
				}
				//Successor is left threaded but not right threaded.
				else if(successor.getIsleftthreaded() && !successor.getIsrightthreaded())
				{
					//Get parent of successor.
					prev = getS(rover, false);
					
					////Set successor in the place of the node being deleted with correct left and right nodes.
					if(rover.getRight() == successor)
					{
						top.setLeft(rover.getLeft());
						top.setRight(successor.getRight());
						setRight(rover,top,1,0);
					
						//Set the correct predecessor for the node after successor.
						setLeft(successor,top);
						top.setIsleftthreaded(false);
					}
					else
					{
						//Set successor in the place of the node being deleted with correct left and right nodes
						prev.setLeft(successor.getRight());
						top.setRight(rover.getRight());
						top.setLeft(rover.getLeft());
						
						//Set the correct Successor for the left node of the node being deleted.
						setRight(rover,top,1,0);
						
						//Set the correct predecessor for the right node after the successor of the node being deleted.
						setLeft(successor,top);
						top.setIsleftthreaded(false);
						top.setIsrightthreaded(false);
					}
				}
			}
			else if(isleft)
			{	
				//Set successor to the location of the node being deleted.
				parent.setLeft(successor);
				IndexRecord newNode = parent.getLeft();
				//Successor is the right child of the node being deleted(left threaded and right threaded).
				if(rover.getRight().getIsleftthreaded() && rover.getRight().getIsrightthreaded())
				{
					//Set successor in the place of the node being deleted with correct left and right nodes.
					newNode.setLeft(rover.getLeft());
					newNode.setRight(parent);
					setRight(newNode,null,0,0);
					newNode.setIsleftthreaded(false);
				}
				//Successor is the right child of the node being deleted(left threaded and not right threaded).
				else if(rover.getRight().getIsleftthreaded() && !rover.getRight().getIsrightthreaded())
				{
					//Set successor in the place of the node being deleted with correct left and right nodes.
					newNode.setLeft(rover.getLeft());
					newNode.setRight(successor.getRight());
					
					//Set correct predecessor for right child of the successor.
					setLeft(successor,newNode);
					//Set correct successor for predecessor of the node being deleted.
					setRight(newNode,null,0,0);
					newNode.setIsleftthreaded(false);
				}
				//successor is left threaded or the right child of the node isn't left threaded/
				else if(successor.getIsleftthreaded() || !rover.getRight().getIsleftthreaded())
				{
					childNodeS(successor,prev,newNode,rover);
				}
			}
			else
			{
				//Set Node being deleted to the the successor.
				parent.setRight(successor);
				IndexRecord newNode = parent.getRight();
				if(rover.getRight().getIsleftthreaded() && rover.getRight().getRight() == null)
				{
					//Set successor in the place of the node being deleted with correct left and right nodes
					newNode.setLeft(rover.getLeft());
					newNode.setRight(null);
					setRight(newNode,null,0,0);
					newNode.setIsleftthreaded(false);
				}
				//Right side of node being deleted is left threaded but not right threaded.
				else if(rover.getRight().getIsleftthreaded() && !rover.getRight().getIsrightthreaded())
				{
					//Set successor in the place of the node being deleted with correct left and right nodes
					newNode.setLeft(rover.getLeft());
					newNode.setRight(successor.getRight());
					
					setLeft(successor,newNode);
					setRight(newNode,null,0,0);
					newNode.setIsleftthreaded(false);
				}
				//The right side of the node being deleted is the successor , and successor is left && right threaded.
				else if(rover.getRight() == successor && successor.getIsleftthreaded()
							&& successor.getIsrightthreaded())
				{
					//Set successor in the place of the node being deleted with correct left and right nodes.
					successor.setIsleftthreaded(false);
					successor.setLeft(rover.getLeft());
					
					//Set correct successor for the predecessor of the node that was deleted.
					setRight(rover,successor,1,0);
				}
				//Successor is left threaded or the right child of the node being deleted isn't left threaded(all cases).
				else if(successor.getIsleftthreaded() || !rover.getRight().getIsleftthreaded())
				{
					childNodeS(successor,prev,newNode,rover);
				}
			}
		}
		return true;
	}
	//Successor is left threaded or the right child of the node being deleted isn't left threaded(all cases).
	private void childNodeS(IndexRecord successor, IndexRecord prev, 
			IndexRecord newNode, IndexRecord rover)
	{
		//Set the correct predecessor for the parent.
		if(successor.getIsrightthreaded())
		{
			prev.setIsleftthreaded(true);
			prev.setLeft(newNode);
		}
		else if(!successor.getIsrightthreaded())
		{
			prev.setLeft(successor.getRight());
			setLeft(successor,newNode);
		}
		//Set successor in the place of the node being deleted with correct left and right nodes.
		newNode.setLeft(rover.getLeft());
		newNode.setRight(rover.getRight());
		setRight(newNode,null,0,0);
		newNode.setIsleftthreaded(false);
		newNode.setIsrightthreaded(false);
	}
	
	//Get the correct node in order to correct threads after deleting.
	private IndexRecord getMostright(IndexRecord mostright) 
	{
		while(!mostright.getIsrightthreaded())
		{
			mostright = mostright.getRight();
		}
		return (mostright);
	}
	
	//Get the correct node in order to correct threads after deleting.
	private IndexRecord getMostleft(IndexRecord lastleft) 
	{
		while(!lastleft.getIsleftthreaded())
		{
			lastleft = lastleft.getLeft();
		}
		return (lastleft);
	}
	
	//A method with a while loop to print from main DataBase using indexes from IndexRecord..
	public void printAscending( StudentRecord[] s) 
	{
		IndexRecord prev = null, rover = top;
		//Get most left node(least value in the tree)
		while(rover != null) 
		{
			prev = rover;
			rover = rover.getLeft();
		}
		// Print Most left Node 
		if(prev!= null)
		{
			System.out.println(s[prev.getWhere()]); 
		}
		//While loop to print in ascending order.
		while(prev != null)
		{
			if(prev != null)
			{
				prev = getS(prev,true);
			}
			
			if(prev != null)
			{
				System.out.println(s[prev.getWhere()]);
			}
		}	
	}
	
	//Return Nodes in (lowest to highest) order from IndexRecord In order to print them in Ascending order.
	/*This method returns the  parent of the successor of a node with two children by setting nodewithtwokiDS
		to false.*/
	private IndexRecord getS(IndexRecord rover, boolean nodewithtwokidsD) 
	{
		IndexRecord r = rover, prev = null;
		//If statement to make sure the Nodes are returned In order and printed in (Ascending order).
		if(rover != null)
		{
			if(rover.getIsrightthreaded())
			{
				rover = rover.getRight();
			}
			else if(!rover.getIsrightthreaded())
			{
				rover = rover.getRight();
				if(rover != null)
				{
					while(!rover.getIsleftthreaded())
					{
						prev = rover;
						rover = rover.getLeft();
					}
				}
			}
		}
		return (nodewithtwokidsD? rover : prev); 
	}
	
	//A method with a while loop to print from main DataBase using indexes from IndexRecord.
	public void printDescending(StudentRecord[] s)
	{
		IndexRecord prev = null, rover = top;
		//Get most right Node(highest value).
		while(rover != null)
		{
			prev = rover;
			rover = rover.getRight();
		}
		//Print highest value.
		System.out.println(s[prev.getWhere()]);
		//While loop with a method to print in descending order.
		while(prev != null)
		{
			if(prev != null)
			{
				prev = getP(prev);
			}
			if(prev != null)
			{
				System.out.println(s[prev.getWhere()]);
			}
		}
	}
	
	//Return Nodes in(highest to lowest) order from IndexRecord In order to print them in descending order.
	private IndexRecord getP(IndexRecord prev) 
	{
		//If statement to make sure the Nodes are returned In order and printed in (Descending order).
		if(prev != null)
		{
			if(prev.getIsleftthreaded())
			{
				prev = prev.getLeft();
			}
			else if(!prev.getIsleftthreaded())
			{
				prev = prev.getLeft();
				if(prev  != null)
				{
					while(!prev.getIsrightthreaded())
					{
						prev = prev.getRight();
					}
				}
			}
		}
		return prev;
	}
	//Correct left thread for the right node of the node being deleted with 2 children.
	public IndexRecord setLeft(IndexRecord newNode, IndexRecord success)
	{
		if(newNode.getRight().getIsleftthreaded())
		{
			newNode.getRight().setLeft(success);
		}
		else if(!newNode.getRight().getIsleftthreaded())
		{
			IndexRecord mostleft = newNode.getRight();
			mostleft = getMostleft(mostleft);
			mostleft.setLeft(success);
		}
		return newNode;
	}
	//Correct right thread for the left node of the node being deleted with 2 children.
	//Correct parent successor threads.
	public IndexRecord setRight(IndexRecord newNode, IndexRecord pred, int choice , int node)
	{
		if(choice == 0) 
		{
			if(newNode.getLeft().getIsrightthreaded())
			{
				newNode.getLeft().setRight(newNode);
			}
			else if(!newNode.getLeft().getIsrightthreaded())
			{
				IndexRecord mostright = newNode.getLeft();
				if(node == 0)
				{
					mostright = getMostright(mostright);
				}
				else if( node == 1)
				{
					mostright = getMostright(mostright.getRight());
				}
				
				mostright.setRight(newNode);
			}
		}
		else if(choice == 1)
		{
			if(newNode.getLeft().getIsrightthreaded())
			{
				newNode.getLeft().setRight(pred);
			}
			else if(!newNode.getLeft().getIsrightthreaded())
			{
				IndexRecord mostright = pred.getLeft();
				mostright = getMostright(mostright);
				mostright.setRight(pred);
			}
		}
		return newNode;
	}
	
	//Check if Threaded Binary Tree is empty.
	public boolean isEmpty()
	{
		return(top==null?true:false);
	}
}