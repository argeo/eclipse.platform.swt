package org.eclipse.swt.tests.junit;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved
 */

import org.eclipse.swt.dnd.*;
import junit.framework.*;
import junit.textui.*;

/**
 * Automated Test Suite for class org.eclipse.swt.dnd.ByteArrayTransfer
 *
 * @see org.eclipse.swt.dnd.ByteArrayTransfer
 */
public class Test_org_eclipse_swt_dnd_ByteArrayTransfer extends Test_org_eclipse_swt_dnd_Transfer {

public Test_org_eclipse_swt_dnd_ByteArrayTransfer(String name) {
	super(name);
}


protected void setUp() {
	super.setUp();
}

protected void tearDown() {
	super.tearDown();
}

public void test_Constructor() {
	warnUnimpl("Test test_Constructor not written");
}

public void test_getSupportedTypes() {
	warnUnimpl("Test test_getSupportedTypes not written");
}

public void test_isSupportedTypeLorg_eclipse_swt_dnd_TransferData() {
	warnUnimpl("Test test_isSupportedTypeLorg_eclipse_swt_dnd_TransferData not written");
}

public void test_javaToNativeLjava_lang_ObjectLorg_eclipse_swt_dnd_TransferData() {
	warnUnimpl("Test test_javaToNativeLjava_lang_ObjectLorg_eclipse_swt_dnd_TransferData not written");
}

public void test_nativeToJavaLorg_eclipse_swt_dnd_TransferData() {
	warnUnimpl("Test test_nativeToJavaLorg_eclipse_swt_dnd_TransferData not written");
}

public static java.util.Vector methodNames() {
	java.util.Vector methodNames = new java.util.Vector();
	methodNames.addElement("test_Constructor");
	methodNames.addElement("test_getSupportedTypes");
	methodNames.addElement("test_isSupportedTypeLorg_eclipse_swt_dnd_TransferData");
	methodNames.addElement("test_javaToNativeLjava_lang_ObjectLorg_eclipse_swt_dnd_TransferData");
	methodNames.addElement("test_nativeToJavaLorg_eclipse_swt_dnd_TransferData");
	methodNames.addAll(Test_org_eclipse_swt_dnd_Transfer.methodNames()); // add superclass method names
	return methodNames;
}
protected void runTest() throws Throwable {
	if (getName().equals("test_Constructor")) test_Constructor();
	else if (getName().equals("test_getSupportedTypes")) test_getSupportedTypes();
	else if (getName().equals("test_isSupportedTypeLorg_eclipse_swt_dnd_TransferData")) test_isSupportedTypeLorg_eclipse_swt_dnd_TransferData();
	else if (getName().equals("test_javaToNativeLjava_lang_ObjectLorg_eclipse_swt_dnd_TransferData")) test_javaToNativeLjava_lang_ObjectLorg_eclipse_swt_dnd_TransferData();
	else if (getName().equals("test_nativeToJavaLorg_eclipse_swt_dnd_TransferData")) test_nativeToJavaLorg_eclipse_swt_dnd_TransferData();
	else super.runTest();
}
}
