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
 * Automated Test Suite for class org.eclipse.swt.widgets.CoolBar
 *
 * @see org.eclipse.swt.widgets.CoolBar
 */
public class Test_org_eclipse_swt_widgets_CoolBar extends Test_org_eclipse_swt_widgets_Composite {

public Test_org_eclipse_swt_widgets_CoolBar(String name) {
	super(name);
}

public static void main(String[] args) {
	TestRunner.run(suite());
}

protected void setUp() {
	super.setUp();
	CoolBar coolBar = new CoolBar(shell, 0);
	setWidget(coolBar);
}

protected void tearDown() {
	super.tearDown();
}

public void test_ConstructorLorg_eclipse_swt_widgets_CompositeI() {
	warnUnimpl("Test test_ConstructorLorg_eclipse_swt_widgets_CompositeI not written");
}

public void test_checkSubclass() {
	warnUnimpl("Test test_checkSubclass not written");
}

public void test_computeSizeIIZ(){
	warnUnimpl( "Test test_computeSizeIIZ not written");
}

public void test_getItemI() {
	warnUnimpl("Test test_getItemI not written");
}

public void test_getItemCount() {
	warnUnimpl("Test test_getItemCount not written");
}

public void test_getItems() {
	warnUnimpl("Test test_getItems not written");
}

public void test_indexOfLorg_eclipse_swt_widgets_CoolItem() {
	warnUnimpl("Test test_indexOfLorg_eclipse_swt_widgets_CoolItem not written");
}

public void test_getItemOrder() {
	warnUnimpl("Test test_getItemOrder not written");
}

public void test_getItemSizes() {
	warnUnimpl("Test test_getItemSizes not written");
}

public void test_getWrapIndices() {
	warnUnimpl("Test test_getWrapIndices not written");
}

public void test_setWrapIndices$I() {
	warnUnimpl("Test test_setWrapIndices$I not written");
}

public void test_setItemLayout$I$I$Lorg_eclipse_swt_graphics_Point() {
	warnUnimpl("Test test_setItemLayout$I$I$Lorg_eclipse_swt_graphics_Point not written");
}

public static Test suite() {
	TestSuite suite = new TestSuite();
	java.util.Vector methodNames = methodNames();
	java.util.Enumeration e = methodNames.elements();
	while (e.hasMoreElements()) {
		suite.addTest(new Test_org_eclipse_swt_widgets_CoolBar((String)e.nextElement()));
	}
	return suite;
}
public static java.util.Vector methodNames() {
	java.util.Vector methodNames = new java.util.Vector();
	methodNames.addElement("test_ConstructorLorg_eclipse_swt_widgets_CompositeI");
	methodNames.addElement("test_checkSubclass");
	methodNames.addElement("test_computeSizeIIZ");
	methodNames.addElement("test_getItemI");
	methodNames.addElement("test_getItemCount");
	methodNames.addElement("test_getItems");
	methodNames.addElement("test_indexOfLorg_eclipse_swt_widgets_CoolItem");
	methodNames.addElement("test_getItemOrder");
	methodNames.addElement("test_getItemSizes");
	methodNames.addElement("test_getWrapIndices");
	methodNames.addElement("test_setWrapIndices$I");
	methodNames.addElement("test_setItemLayout$I$I$Lorg_eclipse_swt_graphics_Point");
	methodNames.addAll(Test_org_eclipse_swt_widgets_Composite.methodNames()); // add superclass method names
	return methodNames;
}
protected void runTest() throws Throwable {
	if (getName().equals("test_ConstructorLorg_eclipse_swt_widgets_CompositeI")) test_ConstructorLorg_eclipse_swt_widgets_CompositeI();
	else if (getName().equals("test_checkSubclass")) test_checkSubclass();
	else if (getName().equals("test_computeSizeIIZ")) test_computeSizeIIZ();
	else if (getName().equals("test_getItemI")) test_getItemI();
	else if (getName().equals("test_getItemCount")) test_getItemCount();
	else if (getName().equals("test_getItems")) test_getItems();
	else if (getName().equals("test_indexOfLorg_eclipse_swt_widgets_CoolItem")) test_indexOfLorg_eclipse_swt_widgets_CoolItem();
	else if (getName().equals("test_getItemOrder")) test_getItemOrder();
	else if (getName().equals("test_getItemSizes")) test_getItemSizes();
	else if (getName().equals("test_getWrapIndices")) test_getWrapIndices();
	else if (getName().equals("test_setWrapIndices$I")) test_setWrapIndices$I();
	else if (getName().equals("test_setItemLayout$I$I$Lorg_eclipse_swt_graphics_Point")) test_setItemLayout$I$I$Lorg_eclipse_swt_graphics_Point();
	else super.runTest();
}
}
