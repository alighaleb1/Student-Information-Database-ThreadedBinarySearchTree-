package ThreadedBinarySearchTree;

import java.io.*;
import java.util.*;


//Main database class to insert,find,delete,list student information(ID number, First name, Last name).
public class DataBase 
{
	Scanner input = new Scanner(System.in);
	private StudentRecord[] data;  //StudentRecord array contains(id number,first name, last name).
	private Index fname,lname, id; //Objects to use methods from Index class(insert,find,delete,list).
	private int next; // counter for StudentRecord array.
	
	
	/*Default constructor to read in student information(ID number, First name, Last name) from a file
	 into StudentRecord array and IndexRecords class.*/
	public DataBase() 
	{
		next = 0; //count for StudentRecord array starting at zero.
		data = new StudentRecord[100]; //declaring studenRecord array size
		
		//Threaded Binary Search Tree Objects(IndexRecord and index)
		fname = new Index(); 
		lname = new Index();
		id = new Index();
		
		//file location
		File f = new File("DataSet.txt");
		String fn, ln, iDn;
		
		//Read/insert student's information from file(first name, last name, id number).
		try 
		{
			Scanner scan = new Scanner(f);
			while(scan.hasNextLine())
			{
				String name = scan.nextLine();
				String lowercase = name.toLowerCase();
				String[] n = lowercase.split(" ");
				fn = n[0];
				ln = n[1];
				iDn = n[2];
				
				//Makes sure same ID number isn't inserted twice from file.	
				if( id.find(iDn) == -1  )
				{
					data[next]= new StudentRecord(fn,ln,iDn);
					 fname.insert(new IndexRecord(fn,next));
				    lname.insert(new IndexRecord(ln,next));
					id.insert(new IndexRecord(iDn,next));
					next++;
				}
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
	}
		
	/*adds a new student (First name, last name, Id number).
	 Makes sure ID number is never replicated.*/
	public void addIt() 
	{
		System.out.println("\nEnter The following Information"
				+ " In Oder Asked For The New Student!");
		System.out.println("First Name:    "
				+ "Last Name	:	ID Number:");
		
		String fnamE = input.next();
	    String lnamE = input.next();
		String iD =  input.next();
				
		int location = isiDinuse(iD);
		if(location == -1 )
		{
			data[next] = new StudentRecord(fnamE,lnamE,iD);
			fname.insert(new IndexRecord(fnamE,next));
			lname.insert(new IndexRecord(lnamE,next));
			id.insert(new IndexRecord(iD,next));
			next++;
			
		 }
		else if(location > -1)
		{
		    System.out.println("\nID Is Already In Use!");
			System.out.println("Press 3 To Look Up The Record"
					+ " By Using The ID Number.\n");
		 }
		System.out.println();
	}	
		
	//Find a  student information by ID number entered by user.
	public void findIt()
	{
		System.out.print("Please Enter an ID To Look Up: ");
		String iDl = input.next();
								
		int location = id.find(iDl);
					
		if(location > -1)
		{
			System.out.println("\n"+data[location]+"\n");
		}
		else if(location == -1)
		{
			System.out.println("\nID not Found\n");
		}
					
	}
			
	//Makes sure no duplicate ID number gets inserted again.
	public int isiDinuse(String iD)
	{
		return (id.find(iD));
	}
	
	//Delete a student information(Id number,first name, last name) by ID number entered by user.
	public void deleteIt() 
	{
		System.out.print("\nPleasae Enter an ID to Delete: ");
		String iDD = input.next();
		int index = id.find(iDD);
		if(index > -1) 
		{
			id.delete(iDD);
			String firstname = data[index].getFname();
			fname.delete(firstname);
			String lastname = data[index].getLname();
			lname.delete(lastname);
			System.out.println("\nRecord of the student with"
					+ " ID number "+iDD+" has been Deleted.\n");
		}
		else if(index == -1)
		{
			System.out.println("\nID not Found: Record cannot be Deleted.\n");
		}
	}
				
	//Print out Student's information in increasing ID number order(0-9).
	public void ListByIDAscending()
	{
		ascending(id);
	}
				
	//Print out Student's information in increasing first name order(a-z).
	public void ListByFirstAscending()
	{
		ascending(fname);
	}
			    
	//Print out Student's information in increasing last name(a-z). 
	 public void ListByLastAscending()
	 {
		ascending(lname);
	 }
		        
	//Print out Student's information in decreasing ID number(9-0).
	public void ListByIDDescending()
	 {
		descending(id);
	 }
			   
	//Print out Student's information in decreasing first name order(z-a).
	 public void ListByFirstDescending()
	{
		descending(fname);
	 }
			   
	 //Print out Student's information in decreasing last name order(z-a). 
	 public void ListByLastDescending() 
	 {
		descending(lname);
	 }
			   
	//A method from index class to print from StudentRecord array in ascending order.
	 public void ascending(Index option) 
	 {		
		 option.printAscending(data);
	 }
			   
	//A method from index class to print from StudentRecord array in ascending order.
	public void descending(Index option)
	{	
		option.printDescending(data);	  
	}
}