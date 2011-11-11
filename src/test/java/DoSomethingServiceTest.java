import org.junit.Before;
import org.junit.Test;

import com.brightdome.testutil.ClassUnderTest;
import com.brightdome.testutil.Dependency;
import com.brightdome.testutil.DependencyA;
import com.brightdome.testutil.DependencyB;
import com.brightdome.testutil.DoSomethingService;
import com.brightdome.testutil.OtascoAnnotations;


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
		OtascoAnnotations.init(this);
	}

	@Test
	public void testOtasco() {
		doSomethingService.doSomething();
	}
}