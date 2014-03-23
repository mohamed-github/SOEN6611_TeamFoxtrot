/**
 * Computes DIT for a selected project
 * 
 * This code is a part of a project for the course SOEN 6611 "Software Measurement"
 * taught by Dr. Nikolaos Tsantalis.
 * 
 * @copyright	March 2014 - Concordia University, Montreal, QC
 * 
 */
package metrics;

import java.util.Set;

import ast.ClassObject;
import ast.SystemObject;
import ast.TypeObject;

public class DIT 
{
	/**
	 * Constructor
	 * @param system The system object
	 */
	public DIT(SystemObject system) 
	{
		//Pass ClassObjects to a set
        Set<ClassObject> classes = system.getClassObjects();
        
        //Print Value of the Depth of Inheritance for each Class
		for(ClassObject classObject : classes) 
		{
			//DIT value calculation from computeDIT
			int ditValue = computeDIT(system, classObject);
			System.out.println("The DIT Value of the " + classObject.getName()  + " is : " + ditValue);
		}
		
	}
	
	/**
	 * Compute and get the DIT
	 * @param system the system object
	 * @param classObject the class object
	 */
	private int computeDIT(SystemObject system, ClassObject classObject) 
	{
	
		//Get SuperClass of the class
		TypeObject to = classObject.getSuperclass();
		
		//Initialize DIT count for Minimum Inheritance
		int ditCount = 0;
		
		//Check if the Class has a superclass and Calculate/return the DIT count
		if(classObject.getSuperclass() != null)
		{
			ditCount++;
			/**
			 * For Debugging
			 * //System.out.println("The Superclass of " + classObject.getName()  + " is : " + to.getClassType());
			 */
			
			ClassObject co;
			co = system.getClassObject(to.getClassType());
			do
			{		
				if(co.getSuperclass() != null)
				{
					ditCount++;
				/**
				 * For Debugging	
				 * //System.out.println("The Superclass of " + co.getName()  + " is : " + co.getSuperclass());
				 */
					
				}
				else
				{
					//System.out.println("There is no superclass of superclass");
					
					break;	//Get out of the operation if reached Root node
					
				}
				
				// Set Objects to Next SuperClass for further Iteration
			    to = co.getSuperclass();
				co = system.getClassObject(to.getClassType());
			}
			while(co.getSuperclass() != null);
			
		}
			else
		{
			
			System.out.println("There is no superclass for  " + classObject.getName());
		}
		
		// Return Value to DIT constructor
		return ditCount;
	}
	

}