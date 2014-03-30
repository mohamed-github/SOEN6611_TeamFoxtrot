package metrics;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ast.ClassObject;
import ast.FieldInstructionObject;
import ast.FieldObject;
import ast.MethodInvocationObject;
import ast.MethodObject;
import ast.SuperFieldInstructionObject;
import ast.SuperMethodInvocationObject;
import ast.SystemObject;
import ast.TypeObject;

public class CBO {

	public CBO(SystemObject system) {
        Set<ClassObject> classes = system.getClassObjects();
        int cboValue;
		for(ClassObject classObject : classes) {
			cboValue = computeCBO(classObject, system);
			System.out.println("CBO for Class " + classObject.getName() + " is " + cboValue);
		}
	}

	private int computeCBO(ClassObject classObject, SystemObject system) {
	
		List<MethodObject> methods = classObject.getMethodList();
		Set<String> coupledClasses = new HashSet<String>();
		List<String> systemClasses = system.getClassNames();
		
		// For Method Level Access
		for(int i=0; i<methods.size(); i++)
		{
			
			List<MethodInvocationObject> methodInvocations = methods.get(i).getMethodInvocations();
			List<FieldInstructionObject> fieldAccesses = methods.get(i).getFieldInstructions();
			List<SuperMethodInvocationObject> superClassMethodInvocations = methods.get(i).getSuperMethodInvocations();
			List<SuperFieldInstructionObject> superClassFieldAccesses  = methods.get(i).getSuperFieldInstructions();
			
			
			for(int j=0; j<methodInvocations.size();j++)
			{
				String classesCoupledThroughMethods = methodInvocations.get(j).getOriginClassName();
				if(!classesCoupledThroughMethods.equalsIgnoreCase(classObject.getName()) && systemClasses.contains(classesCoupledThroughMethods))
				{
					coupledClasses.add(classesCoupledThroughMethods);
				}
			}
			
			for(int j=0; j<fieldAccesses.size();j++)
			{
				String classesCoupledThroughFields = fieldAccesses.get(j).getOwnerClass();
				if(!classesCoupledThroughFields.equalsIgnoreCase(classObject.getName()) && systemClasses.contains(classesCoupledThroughFields))
				{
					coupledClasses.add(classesCoupledThroughFields);
				}
			}
			
			for(int j=0; j<superClassMethodInvocations.size();j++)
			{
				String classesCoupledThroughSuperClassMethodCalls = superClassMethodInvocations.get(j).getOriginClassName();
				if(!classesCoupledThroughSuperClassMethodCalls.equalsIgnoreCase(classObject.getName()) && systemClasses.contains(classesCoupledThroughSuperClassMethodCalls))
				{
					coupledClasses.add(classesCoupledThroughSuperClassMethodCalls);
				}
			}
			
			for(int j=0; j<superClassFieldAccesses.size();j++)
			{
				String classesCoupledThroughSuperClassFieldAccesses = superClassFieldAccesses.get(j).getOwnerClass();
				if(!classesCoupledThroughSuperClassFieldAccesses.equalsIgnoreCase(classObject.getName()) && systemClasses.contains(classesCoupledThroughSuperClassFieldAccesses))
				{
					coupledClasses.add(classesCoupledThroughSuperClassFieldAccesses);
				}
			}
			
			
			/*
			// for Coupling through Return Type
			methods.get(i).getReturnType().getClass().getName();
			
			// For Coupling through Parameter Type
			methods.get(i).getParameterTypeList().getClass().getName();	*/	
		}
		
		// For Class Level Access
		
		/*List<FieldObject> fieldsAccessedInsideClass = classObject.getFieldList();
		
		//List<TypeObject> parameterTypes = methods.get(i).getParameterTypeList();
		for(int i=0; i<fieldsAccessedInsideClass.size(); i++)
		{
			fieldsAccessedInsideClass.get(i).getClassName();
		}*/
		
		/**
		 * For Debugging
			for(String s:coupledClasses)
			{
			//System.out.println("The Coupled Classes Set for " + classObject.getName() + " is :" + s);
			}
		*/
		
		//Size of the Coupled Classes Set is the CBO Value
		return coupledClasses.size();
	}
}
