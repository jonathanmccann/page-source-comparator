package com.test;

import com.comparator.Main;
import org.junit.Test;

import java.io.IOException;

public class ComparatorTest {
	@Test
	public void testComparator() throws IOException {
		Main myMain = new Main();

		myMain.main(PageSourceComparatorTestSuite.urls);
	}
}