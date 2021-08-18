package ThreadedBinarySearchTree;

//Threaded Binary Search Tree.
public class IndexRecord 
{
	public IndexRecord left, right; //Nodes.
	private boolean isleftthreaded; //Left Thread checker.
	private boolean isrightthreaded; // Right Thread checker.
	public String key; //(id number, first name, or last name).
	public int where; //Position in main database.
	
	//Constructor for students info to be entered.
	public IndexRecord(String k, int w)
	{
		this.left = null;
		this.right = null;
		this.key = k;
		this.where = w;
		this.isleftthreaded = true;
		this.isrightthreaded = true;
	}
	
	//Getters and Setters for Nodes of Threaded Binary Search Tree
	
	//Getter to check if Node is left threaded.
	public boolean getIsleftthreaded() 
	{
		return isleftthreaded;
	}
	
	//Setter to set Node as left threaded.
	public void setIsleftthreaded(boolean leftthreaded)
	{
		this.isleftthreaded = leftthreaded;
	}
	
	//Getter to check if Node is right threaded.
	public boolean getIsrightthreaded() {
		return isrightthreaded;
	}
	
	//Setter to set Node as right threaded.
	public void setIsrightthreaded(boolean rightthreaded)
	{
		this.isrightthreaded = rightthreaded;
	}

	//Getter for Left
	public IndexRecord getLeft() 
	{
		return left;
	}
	
	//Setter for Left.
	public void setLeft(IndexRecord l) 
	{
		this.left= l;
	}
	
	//Getter for Right.
	public IndexRecord getRight() 
	{
		return right;
	}
	
	//Setter for Right;
	public void setRight(IndexRecord right) 
	{
		this.right = right;
	}
	
	//Getter to get (first name,last name, and id number).
	public String getKey() 
	{
		return key;
	}
	
	//Getter to get position of a student in main database.
	public int getWhere() 
	{
		return where;
	}

	//Print id number or first name or last name with position in main database.
	public String toString()
	{
		return key+" "+where;
	}
	
	//Compareto method to enter in ascending order for the three strings(id number, first name, last name)
	public int compareTo(IndexRecord ir)
	{
		return key.compareTo(ir.key);
	}
}