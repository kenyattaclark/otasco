package org.otasco;
import org.junit.Before;
import org.junit.Test;
import org.otasco.ClassUnderTest;
import org.otasco.Dependency;
import org.otasco.DependencyA;
import org.otasco.DependencyB;
import org.otasco.DoSomethingService;
import org.otasco.Otasco;



public class DoSomethingServiceTest {

	@ClassUnderTest
	private DoSomethingService doSomethingService;

	@Dependency
	private DependencyA dependencyA;

	@Dependency
	private DependencyB dependencyB;

	@Before
	public void init() throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException {
		dependencyA = new DependencyA();
		dependencyB = new DependencyB();
		doSomethingService = new DoSomethingService();
		Otasco.init(this);
	}

	@Test
	public void testOtasco() {
		doSomethingService.doSomething();
	}
}