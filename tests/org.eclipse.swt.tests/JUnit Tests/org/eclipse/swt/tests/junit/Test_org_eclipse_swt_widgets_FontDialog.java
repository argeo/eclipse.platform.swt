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
 * Automated Test Suite for class org.eclipse.swt.widgets.FontDialog
 *
 * @see org.eclipse.swt.widgets.FontDialog
 */
public class Test_org_eclipse_swt_widgets_FontDialog extends Test_org_eclipse_swt_widgets_Dialog {

FontDialog fontDialog;

public Test_org_eclipse_swt_widgets_FontDialog(String name) {
	super(name);
}

public static void main(String[] args) {
	TestRunner.run(suite());
}

protected void setUp() {
	super.setUp();
	fontDialog = new FontDialog(shell, SWT.NULL);
	setDialog(fontDialog);
}

protected void tearDown() {
}

public void test_ConstructorLorg_eclipse_swt_widgets_Shell() {
	FontDialog fd = new FontDialog(shell);
	try {
		new FontDialog(null);
		fail("No exception thrown for parent == null");
	}
	catch (IllegalArgumentException e) {
	}
}

public void test_ConstructorLorg_eclipse_swt_widgets_ShellI() {
	try {
		new FontDialog(null, SWT.NULL);
		fail("No exception thrown for parent == null");
	}
	catch (IllegalArgumentException e) {
	}
}

public void test_getFontData() {
	// tested in test_setFontDataLorg_eclipse_swt_graphics_FontData
}

public void test_open() {
	warnUnimpl("Test test_open not written");
}

public void test_setFontDataLorg_eclipse_swt_graphics_FontData() {
	FontData fontData = new FontData();

	assertNull(fontDialog.getFontData());	
		
	fontDialog.setFontData(fontData);
	assertEquals(fontDialog.getFontData(), fontData);

	fontDialog.setFontData(null);
	assertNull(fontDialog.getFontData());	
}

public static Test suite() {
	TestSuite suite = new TestSuite();
	java.util.Vector methodNames = methodNames();
	java.util.Enumeration e = methodNames.elements();
	while (e.hasMoreElements()) {
		suite.addTest(new Test_org_eclipse_swt_widgets_FontDialog((String)e.nextElement()));
	}
	return suite;
}
public static java.util.Vector methodNames() {
	java.util.Vector methodNames = new java.util.Vector();
	methodNames.addElement("test_ConstructorLorg_eclipse_swt_widgets_Shell");
	methodNames.addElement("test_ConstructorLorg_eclipse_swt_widgets_ShellI");
	methodNames.addElement("test_getFontData");
	methodNames.addElement("test_open");
	methodNames.addElement("test_setFontDataLorg_eclipse_swt_graphics_FontData");
	methodNames.addAll(Test_org_eclipse_swt_widgets_Dialog.methodNames()); // add superclass method names
	return methodNames;
}
protected void runTest() throws Throwable {
	if (getName().equals("test_ConstructorLorg_eclipse_swt_widgets_Shell")) test_ConstructorLorg_eclipse_swt_widgets_Shell();
	else if (getName().equals("test_ConstructorLorg_eclipse_swt_widgets_ShellI")) test_ConstructorLorg_eclipse_swt_widgets_ShellI();
	else if (getName().equals("test_getFontData")) test_getFontData();
	else if (getName().equals("test_open")) test_open();
	else if (getName().equals("test_setFontDataLorg_eclipse_swt_graphics_FontData")) test_setFontDataLorg_eclipse_swt_graphics_FontData();
	else super.runTest();
}
}
