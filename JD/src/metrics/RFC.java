package metrics;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import ast.ClassObject;
import ast.MethodInvocationObject;
import ast.MethodObject;
import ast.SystemObject;

public class RFC {
	
	
	TreeSet<String> M;
	TreeSet<String> R;
	TreeSet<String> RS;
	int RFC;

	public RFC(SystemObject system) {
        Set<ClassObject> classes = system.getClassObjects();
        int rfcValue;
		for(ClassObject classObject : classes) {
			rfcValue = computeRFC(classObject);
			System.out.println("RFC for Class " + classObject.getName() + " is " + rfcValue);
		}
		
	}
	
	private int computeRFC(ClassObject classObject) {
		
		M = new TreeSet<String>();
		R = new TreeSet<String>();
		RS = new TreeSet<String>();
		RFC = 0;
		
		//System.out.println("\n The name of the class is : " + classObject.getName());
		//System.out.println("The number of methods in this class: " + classObject.getNumberOfMethods());
		
		List<MethodObject> methods = classObject.getMethodList();
		
		//Populate M Set and R set
		for(int i=0; i<methods.size(); i++)
		{
			//System.out.println("The methods in this class are : " + methods.get(i).getName() + "The class name is : " + methods.get(i).getClassName());
			String methodsInCurrentClass = methods.get(i).getClassName() + "." + methods.get(i).getName();
			//System.out.println("Methods in Current Class :" + methodsInCurrentClass);
			M.add(methodsInCurrentClass);
			
			List<MethodInvocationObject> methodInvocations = methods.get(i).getMethodInvocations();
			
			for(int j=0; j<methodInvocations.size();j++)
			{
				//System.out.println("The number of Method invocations from this method : " + methodInvocations.get(j).getMethodName() 
					//	+ "The class this belongs to : " + methodInvocations.get(j).getOriginClassName());
				String methodInvocationsInEachMethodOfClass = methodInvocations.get(j).getOriginClassName() + "." + methodInvocations.get(j).getMethodName();
				//System.out.println("Methods Invocations in each method :" + methodInvocationsInEachMethodOfClass);
				R.add(methodInvocationsInEachMethodOfClass);
			}
		}
		
		Iterator<String> iM = M.iterator();
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		while(iM.hasNext())
		{
			String s = iM.next();
			
			sb.append(s);
			sb.append(",");
		}
		sb.append("}");
		//System.out.println("\n The Elements of M set are :" + sb);
		
		Iterator<String> iR = R.iterator();
		StringBuffer sb2 = new StringBuffer();
		sb2.append("{");
		while(iR.hasNext())
		{
			String s = iR.next();
			
			sb2.append(s);
			sb2.append(",");
		}
		sb2.append("}");
		//System.out.println("\n The Elements of R set are :" + sb2);
		
		//calculate RS = M U R
		RS = unionOfSet(M, R);
		
		Iterator<String> iRS = RS.iterator();
		StringBuffer sb3 = new StringBuffer();
		sb3.append("{");
		while(iRS.hasNext())
		{
			String s = iRS.next();
			sb3.append(s);
			sb3.append(",");
			
		}
		sb3.append("}");
		//System.out.println("\n The Elements of RS set are :" + sb3);
		
		
		//return |RS| - its size : This is the RFC value
		return RS.size();
	}
	
	
	private TreeSet<String> unionOfSet(Set<String> A, Set<String> B){
		TreeSet<String> temp = new TreeSet<String>(A);
		temp.addAll(B);
		return temp;
	}
	
}
