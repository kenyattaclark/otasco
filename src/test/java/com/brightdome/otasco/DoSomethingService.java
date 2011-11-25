package com.brightdome.otasco;

public class DoSomethingService {

	private DependencyA dependencyA;
	private DependencyB dependencyB;

	public void doSomething() {
		System.out.println(dependencyA.doSomething() + " and " + dependencyB.doSomething() + " without using setters!");
	}
}