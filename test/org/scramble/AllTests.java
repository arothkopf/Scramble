package org.scramble;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scramble.encoding.CodeBasedTransformationListFactoryTest;
import org.scramble.util.color.InvertColorTransformerTest;
import org.scramble.util.color.ProgressiveColorTransformerTest;


	@RunWith(Suite.class)
	@Suite.SuiteClasses( { 
		CodeBasedTransformationListFactoryTest.class, 
		InvertColorTransformerTest.class, 
		ProgressiveColorTransformerTest.class, 
		ScrambleControllerTest.class
		})
	public class AllTests {
	}


