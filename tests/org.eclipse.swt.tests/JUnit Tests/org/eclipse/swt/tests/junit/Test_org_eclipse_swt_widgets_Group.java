package org.eclipse.swt.tests.junit;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved
 */

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import junit.framework.*;
import junit.textui.*;

/**
 * Automated Test Suite for class org.eclipse.swt.widgets.Group
 *
 * @see org.eclipse.swt.widgets.Group
 */
public class Test_org_eclipse_swt_widgets_Group extends Test_org_eclipse_swt_widgets_Composite {

Group group;

public Test_org_eclipse_swt_widgets_Group(String name) {
	super(name);
}

public static void main(String[] args) {
	TestRunner.run(suite());
}

protected void setUp() {
	super.setUp();
	group = new Group(shell, 0);
	setWidget(group);
}

protected void tearDown() {
	super.tearDown();
}

public void test_ConstructorLorg_eclipse_swt_widgets_CompositeI() {
	try {
		group = new Group(null, 0);
		fail("No exception thrown for parent == null");
	}
	catch (IllegalArgumentException e) {
	}

	int[] cases = {SWT.SHADOW_IN, SWT.SHADOW_OUT, SWT.SHADOW_ETCHED_IN, SWT.SHADOW_ETCHED_OUT};
	for (int i = 0; i < cases.length; i++)
		group = new Group(shell, cases[i]);
}

public void test_checkSubclass() {
	warnUnimpl("Test test_checkSubclass not written");
}

public void test_computeSizeIIZ() {
	warnUnimpl("Test test_computeSizeIIZ not written");
}

public void test_computeTrimIIII() {
	warnUnimpl("Test test_computeTrimIIII not written");
}

public void test_getClientArea() {
	warnUnimpl("Test test_getClientArea not written");
}

public void test_getText() {
	// tested in test_setTextLjava_lang_String
}

public void test_setTextLjava_lang_String() {
	String[] cases = {"", "some text", "ldkashdoehufweovcnhslvhregojebckreavbkuhxbiufvcyhbifuyewvbiureyd.,cmnesljliewjfchvbwoifivbeworixuieurvbiuvbohflksjeahfcliureafgyciabelitvyrwtlicuyrtliureybcliuyreuceyvbliureybct"};
	for (int i = 0; i < cases.length; i++) {
		group.setText(cases[i]);
		assertTrue("case: " + String.valueOf(i), group.getText().equals(cases[i]));
	};
}

public static Test suite() {
	TestSuite suite = new TestSuite();
	java.util.Vector methodNames = methodNames();
	java.util.Enumeration e = methodNames.elements();
	while (e.hasMoreElements()) {
		suite.addTest(new Test_org_eclipse_swt_widgets_Group((String)e.nextElement()));
	}
	return suite;
}
public static java.util.Vector methodNames() {
	java.util.Vector methodNames = new java.util.Vector();
	methodNames.addElement("test_ConstructorLorg_eclipse_swt_widgets_CompositeI");
	methodNames.addElement("test_checkSubclass");
	methodNames.addElement("test_computeSizeIIZ");
	methodNames.addElement("test_computeTrimIIII");
	methodNames.addElement("test_getClientArea");
	methodNames.addElement("test_getText");
	methodNames.addElement("test_setTextLjava_lang_String");
	methodNames.addAll(Test_org_eclipse_swt_widgets_Composite.methodNames()); // add superclass method names
	return methodNames;
}
protected void runTest() throws Throwable {
	if (getName().equals("test_ConstructorLorg_eclipse_swt_widgets_CompositeI")) test_ConstructorLorg_eclipse_swt_widgets_CompositeI();
	else if (getName().equals("test_checkSubclass")) test_checkSubclass();
	else if (getName().equals("test_computeSizeIIZ")) test_computeSizeIIZ();
	else if (getName().equals("test_computeTrimIIII")) test_computeTrimIIII();
	else if (getName().equals("test_getClientArea")) test_getClientArea();
	else if (getName().equals("test_getText")) test_getText();
	else if (getName().equals("test_setTextLjava_lang_String")) test_setTextLjava_lang_String();
	else super.runTest();
}
}
