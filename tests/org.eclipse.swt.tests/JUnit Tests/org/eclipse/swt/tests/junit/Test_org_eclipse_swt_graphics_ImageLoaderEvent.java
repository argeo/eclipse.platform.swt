package org.eclipse.swt.tests.junit;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved
 */

import org.eclipse.swt.graphics.*;
import junit.framework.*;
import junit.textui.*;

/**
 * Automated Test Suite for class org.eclipse.swt.graphics.ImageLoaderEvent
 *
 * @see org.eclipse.swt.graphics.ImageLoaderEvent
 */
public class Test_org_eclipse_swt_graphics_ImageLoaderEvent extends SwtTestCase {

public Test_org_eclipse_swt_graphics_ImageLoaderEvent(String name) {
	super(name);
}

public static void main(String[] args) {
	TestRunner.run(suite());
}

protected void setUp() {
}

protected void tearDown() {
}

public void test_ConstructorLorg_eclipse_swt_graphics_ImageLoaderLorg_eclipse_swt_graphics_ImageDataIZ() {
	warnUnimpl("Test test_ConstructorLorg_eclipse_swt_graphics_ImageLoaderLorg_eclipse_swt_graphics_ImageDataIZ not written");
}

public void test_toString() {
	warnUnimpl("Test test_toString not written");
}

public static Test suite() {
	TestSuite suite = new TestSuite();
	java.util.Vector methodNames = methodNames();
	java.util.Enumeration e = methodNames.elements();
	while (e.hasMoreElements()) {
		suite.addTest(new Test_org_eclipse_swt_graphics_ImageLoaderEvent((String)e.nextElement()));
	}
	return suite;
}
public static java.util.Vector methodNames() {
	java.util.Vector methodNames = new java.util.Vector();
	methodNames.addElement("test_ConstructorLorg_eclipse_swt_graphics_ImageLoaderLorg_eclipse_swt_graphics_ImageDataIZ");
	methodNames.addElement("test_toString");
	return methodNames;
}
protected void runTest() throws Throwable {
	if (getName().equals("test_ConstructorLorg_eclipse_swt_graphics_ImageLoaderLorg_eclipse_swt_graphics_ImageDataIZ")) test_ConstructorLorg_eclipse_swt_graphics_ImageLoaderLorg_eclipse_swt_graphics_ImageDataIZ();
	else if (getName().equals("test_toString")) test_toString();
}
}
