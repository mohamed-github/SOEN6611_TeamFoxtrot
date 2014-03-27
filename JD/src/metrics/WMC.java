package metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ast.ClassObject;
import ast.MethodObject;
import ast.SystemObject;
import ast.decomposition.CompositeStatementObject;

public class WMC {

	public WMC(SystemObject system) {
        Set<ClassObject> classes = system.getClassObjects();
        int wmcValue;
		for(ClassObject classObject : classes) {
			wmcValue = computeWMC(classObject);
			System.out.println("\n WMC for Class " + classObject.getName() + " is " + wmcValue);
		}	
	}
	

	private int computeWMC(ClassObject classObject) {
	
		List<MethodObject> methodObjectList = classObject.getMethodList();
		int sumOfCCValueOfAllMethod = 0;
		
		for(int i=0; i < methodObjectList.size(); i++)
		{
			MethodObject methodObj = methodObjectList.get(i);
			
			 int ccValueForEachMethod = computeCyclomaticComplexity(methodObj);
			 
			 System.out.println("The cyclomatic complexity of method " + methodObj.getName() + " in the class " + classObject.getName() + " is : " + ccValueForEachMethod);
			 
			 sumOfCCValueOfAllMethod = sumOfCCValueOfAllMethod + ccValueForEachMethod;
		}
		
		return sumOfCCValueOfAllMethod;
	}
	
	// CC = D + 1; where D is the no of Control Structures in a method
	private int computeCyclomaticComplexity(MethodObject methodObject) {
		
		int D, CC;
		
		CompositeStatementObject compStmtObj = methodObject.getMethodBody().getCompositeStatement();
		
		List<CompositeStatementObject> totalControlStatementList = new ArrayList<CompositeStatementObject>();
	
		List<CompositeStatementObject> ifStmtList = compStmtObj.getIfStatements();
		
		totalControlStatementList.addAll(ifStmtList);
		
		List<CompositeStatementObject> forStmtList = compStmtObj.getForStatements();
		
		totalControlStatementList.addAll(forStmtList);
		
		List<CompositeStatementObject> whileStmtList = compStmtObj.getWhileStatements();
		
		totalControlStatementList.addAll(whileStmtList);
		
		// Add code for Switch case, 
		
		D = totalControlStatementList.size();
		
	    CC = D + 1;
		
		
		
		return CC;
	}
	
}
