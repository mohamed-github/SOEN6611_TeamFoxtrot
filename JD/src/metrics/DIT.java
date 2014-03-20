package metrics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import ast.inheritance.*;

import ast.ClassObject;
import ast.SystemObject;

public class DIT {
	//private Map<String, Integer> ditmap;
	
	public DIT(SystemObject system) 
	{
        Set<ClassObject> classes = system.getClassObjects();
        int count = 0;
		for(ClassObject classObject : classes) {
			//Below statement prints name of the super class for a class.
			System.out.println("\n Super class for " + classObject.getName() + " is " + String.valueOf(classObject.getSuperclass()));
			
		}
		
	}

}