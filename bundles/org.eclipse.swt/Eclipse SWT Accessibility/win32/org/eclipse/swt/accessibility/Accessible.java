/*******************************************************************************
 * Copyright (c) 2000, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.accessibility;

import java.util.Vector;
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.internal.win32.*;
import org.eclipse.swt.ole.win32.*;
import org.eclipse.swt.internal.ole.win32.*;

/**
 * Instances of this class provide a bridge between application
 * code and assistive technology clients. Many platforms provide
 * default accessible behavior for most widgets, and this class
 * allows that default behavior to be overridden. Applications
 * can get the default Accessible object for a control by sending
 * it <code>getAccessible</code>, and then add an accessible listener
 * to override simple items like the name and help string, or they
 * can add an accessible control listener to override complex items.
 * As a rule of thumb, an application would only want to use the
 * accessible control listener to implement accessibility for a
 * custom control.
 * 
 * @see Control#getAccessible
 * @see AccessibleListener
 * @see AccessibleEvent
 * @see AccessibleControlListener
 * @see AccessibleControlEvent
 * @see <a href="http://www.eclipse.org/swt/snippets/#accessibility">Accessibility snippets</a>
 * @see <a href="http://www.eclipse.org/swt/">Sample code and further information</a>
 * 
 * @since 2.0
 */
public class Accessible {
	int refCount = 0, enumIndex = 0;
	COMObject objIAccessible, objIEnumVARIANT, objIServiceProvider, objIAccessible2, objIAccessibleAction,
		objIAccessibleApplication, objIAccessibleComponent, objIAccessibleEditableText, objIAccessibleHyperlink,
		objIAccessibleHypertext, objIAccessibleImage, objIAccessibleRelation, objIAccessibleTable2,
		objIAccessibleTableCell, objIAccessibleText, objIAccessibleValue;
	IAccessible iaccessible;
	Vector accessibleListeners = new Vector();
	Vector accessibleControlListeners = new Vector();
	Vector accessibleTextListeners = new Vector ();
	Vector accessibleActionListeners = new Vector();
	Vector accessibleHyperlinkListeners = new Vector();
	Vector accessibleTableListeners = new Vector();
	Vector accessibleTableCellListeners = new Vector();
	Vector accessibleTextExtendedListeners = new Vector();
	Vector accessibleValueListeners = new Vector();
	Vector accessibleScrollListeners = new Vector();
	Vector accessibleAttributeListeners = new Vector();
	Object[] variants;
	Accessible parent;
	Control control;

	/**
	 * Constructs a new instance of this class given its parent.
	 * 
	 * @param parent the Accessible parent
	 * 
	 * @see Control#getAccessible
	 * 
	 * @since 3.6
	 */
	public Accessible(Accessible parent) {
		this.parent = parent;
		//TODO: (platform-specific?) code to add this accessible to the parent's children
	}

	/**
	 * @since 3.5
	 */
	protected Accessible() {
	}

	Accessible(Control control) {
		this.control = control;
		int /*long*/[] ppvObject = new int /*long*/[1];
		/* CreateStdAccessibleObject([in] hwnd, [in] idObject, [in] riidInterface, [out] ppvObject).
		 * AddRef has already been called on ppvObject by the callee and must be released by the caller.
		 */
		int result = (int)/*64*/COM.CreateStdAccessibleObject(control.handle, COM.OBJID_CLIENT, COM.IIDIAccessible, ppvObject);
		/* The object needs to be checked, because if the CreateStdAccessibleObject()
		 * symbol is not found, the return value is S_OK.
		 */
		if (ppvObject[0] == 0) return;
		if (result != COM.S_OK) OLE.error(OLE.ERROR_CANNOT_CREATE_OBJECT, result);
		iaccessible = new IAccessible(ppvObject[0]);

		objIAccessible = new COMObject(new int[] {2,0,0,1,3,5,8,1,1,2,2,2,2,2,2,2,3,2,1,1,2,2,5,3,3,1,2,2}) {
			public int /*long*/ method0(int /*long*/[] args) {return QueryInterface(args[0], args[1]);}
			public int /*long*/ method1(int /*long*/[] args) {return AddRef();}
			public int /*long*/ method2(int /*long*/[] args) {return Release();}
			// method3 GetTypeInfoCount - not implemented
			// method4 GetTypeInfo - not implemented
			// method5 GetIDsOfNames - not implemented
			// method6 Invoke - not implemented
			public int /*long*/ method7(int /*long*/[] args) {return get_accParent(args[0]);}
			public int /*long*/ method8(int /*long*/[] args) {return get_accChildCount(args[0]);}
			public int /*long*/ method9(int /*long*/[] args) {return get_accChild(args[0], args[1]);}
			public int /*long*/ method10(int /*long*/[] args) {return get_accName(args[0], args[1]);}
			public int /*long*/ method11(int /*long*/[] args) {return get_accValue(args[0], args[1]);}
			public int /*long*/ method12(int /*long*/[] args) {return get_accDescription(args[0], args[1]);}
			public int /*long*/ method13(int /*long*/[] args) {return get_accRole(args[0], args[1]);}
			public int /*long*/ method14(int /*long*/[] args) {return get_accState(args[0], args[1]);}
			public int /*long*/ method15(int /*long*/[] args) {return get_accHelp(args[0], args[1]);}
			public int /*long*/ method16(int /*long*/[] args) {return get_accHelpTopic(args[0], args[1], args[2]);}
			public int /*long*/ method17(int /*long*/[] args) {return get_accKeyboardShortcut(args[0], args[1]);}
			public int /*long*/ method18(int /*long*/[] args) {return get_accFocus(args[0]);}
			public int /*long*/ method19(int /*long*/[] args) {return get_accSelection(args[0]);}
			public int /*long*/ method20(int /*long*/[] args) {return get_accDefaultAction(args[0], args[1]);}
			public int /*long*/ method21(int /*long*/[] args) {return accSelect((int)/*64*/args[0], args[1]);}
			public int /*long*/ method22(int /*long*/[] args) {return accLocation(args[0], args[1], args[2], args[3], args[4]);}
			public int /*long*/ method23(int /*long*/[] args) {return accNavigate((int)/*64*/args[0], args[1], args[2]);}
			public int /*long*/ method24(int /*long*/[] args) {return accHitTest((int)/*64*/args[0], (int)/*64*/args[1], args[2]);}
			public int /*long*/ method25(int /*long*/[] args) {return accDoDefaultAction(args[0]);}
			public int /*long*/ method26(int /*long*/[] args) {return put_accName(args[0], args[1]);}
			public int /*long*/ method27(int /*long*/[] args) {return put_accValue(args[0], args[1]);}
		};

		/* If the callback takes a struct parameter (for example, a variant),
		 * then create a custom callback that dereferences the struct and
		 * passes a pointer to the original callback.
		 */
		int /*long*/ ppVtable = objIAccessible.ppVtable;
		int /*long*/[] pVtable = new int /*long*/[1];
		COM.MoveMemory(pVtable, ppVtable, OS.PTR_SIZEOF);
		int /*long*/[] funcs = new int /*long*/[28];
		COM.MoveMemory(funcs, pVtable[0], OS.PTR_SIZEOF * funcs.length);
		funcs[9] = COM.get_accChild_CALLBACK(funcs[9]);
		funcs[10] = COM.get_accName_CALLBACK(funcs[10]);
		funcs[11] = COM.get_accValue_CALLBACK(funcs[11]);
		funcs[12] = COM.get_accDescription_CALLBACK(funcs[12]);
		funcs[13] = COM.get_accRole_CALLBACK(funcs[13]);
		funcs[14] = COM.get_accState_CALLBACK(funcs[14]);
		funcs[15] = COM.get_accHelp_CALLBACK(funcs[15]);
		funcs[16] = COM.get_accHelpTopic_CALLBACK(funcs[16]);
		funcs[17] = COM.get_accKeyboardShortcut_CALLBACK(funcs[17]);
		funcs[20] = COM.get_accDefaultAction_CALLBACK(funcs[20]);
		funcs[21] = COM.accSelect_CALLBACK(funcs[21]);
		funcs[22] = COM.accLocation_CALLBACK(funcs[22]);
		funcs[23] = COM.accNavigate_CALLBACK(funcs[23]);
		funcs[25] = COM.accDoDefaultAction_CALLBACK(funcs[25]);
		funcs[26] = COM.put_accName_CALLBACK(funcs[26]);
		funcs[27] = COM.put_accValue_CALLBACK(funcs[27]);
		COM.MoveMemory(pVtable[0], funcs, OS.PTR_SIZEOF * funcs.length);

		objIEnumVARIANT = new COMObject(new int[] {2,0,0,3,1,0,1}) {
			public int /*long*/ method0(int /*long*/[] args) {return QueryInterface(args[0], args[1]);}
			public int /*long*/ method1(int /*long*/[] args) {return AddRef();}
			public int /*long*/ method2(int /*long*/[] args) {return Release();}
			public int /*long*/ method3(int /*long*/[] args) {return Next((int)/*64*/args[0], args[1], args[2]);}
			public int /*long*/ method4(int /*long*/[] args) {return Skip((int)/*64*/args[0]);}
			public int /*long*/ method5(int /*long*/[] args) {return Reset();}
			public int /*long*/ method6(int /*long*/[] args) {return Clone(args[0]);}
		};
		AddRef();
		
		objIServiceProvider = new COMObject(new int[] {2,0,0,3}) {
		    public int /*long*/ method0(int /*long*/[] args) {return QueryInterface(args[0], args[1]);}
		    public int /*long*/ method1(int /*long*/[] args) {return AddRef();}
		    public int /*long*/ method2(int /*long*/[] args) {return Release();}        
		    public int /*long*/ method3(int /*long*/[] args) {return QueryService(args[0], args[1], args[2]);}
		};

		objIAccessible2 = new COMObject(new int[] {2,0,0,/*TODO: IAccessible*/1,2,3,1,1,3,3,1,1,1,1,3,3,1,1,1,1,1}) {
			public int /*long*/ method0(int /*long*/[] args) {return QueryInterface(args[0], args[1]);}
			public int /*long*/ method1(int /*long*/[] args) {return AddRef();}
			public int /*long*/ method2(int /*long*/[] args) {return Release();}
			// TODO: Add IAccessible methods here
			public int /*long*/ method28(int /*long*/[] args) {return get_nRelations(args[0]);}
			public int /*long*/ method29(int /*long*/[] args) {return get_relation((int)/*64*/args[0], args[1]);}
			public int /*long*/ method30(int /*long*/[] args) {return get_relations((int)/*64*/args[0], args[1], args[2]);}
			public int /*long*/ method31(int /*long*/[] args) {return get_role(args[0]);}
			public int /*long*/ method32(int /*long*/[] args) {return scrollTo(args[0]);}
			public int /*long*/ method33(int /*long*/[] args) {return scrollToPoint(args[0], (int)/*64*/args[1], (int)/*64*/args[2]);}
			public int /*long*/ method34(int /*long*/[] args) {return get_groupPosition(args[0], args[1], args[2]);}
			public int /*long*/ method35(int /*long*/[] args) {return get_states(args[0]);}
			public int /*long*/ method36(int /*long*/[] args) {return get_extendedRole(args[0]);}
			public int /*long*/ method37(int /*long*/[] args) {return get_localizedExtendedRole(args[0]);}
			public int /*long*/ method38(int /*long*/[] args) {return get_nExtendedStates(args[0]);}
			public int /*long*/ method39(int /*long*/[] args) {return get_extendedStates((int)/*64*/args[0], args[1], args[2]);}
			public int /*long*/ method40(int /*long*/[] args) {return get_localizedExtendedStates((int)/*64*/args[0], args[1], args[2]);}
			public int /*long*/ method41(int /*long*/[] args) {return get_uniqueID(args[0]);}
			public int /*long*/ method42(int /*long*/[] args) {return get_windowHandle(args[0]);}
			public int /*long*/ method43(int /*long*/[] args) {return get_indexInParent(args[0]);}
			public int /*long*/ method44(int /*long*/[] args) {return get_locale(args[0]);}
			public int /*long*/ method45(int /*long*/[] args) {return get_attributes(args[0]);}
		};

		objIAccessibleAction = new COMObject(new int[] {2,0,0,1,1,2,4,2,2}) {
			public int /*long*/ method0(int /*long*/[] args) {return QueryInterface(args[0], args[1]);}
			public int /*long*/ method1(int /*long*/[] args) {return AddRef();}
			public int /*long*/ method2(int /*long*/[] args) {return Release();}
			public int /*long*/ method3(int /*long*/[] args) {return get_nActions((int)/*64*/args[0]);}
			public int /*long*/ method4(int /*long*/[] args) {return doAction((int)/*64*/args[0]);}
			public int /*long*/ method5(int /*long*/[] args) {return get_description((int)/*64*/args[0], args[1]);}
			public int /*long*/ method6(int /*long*/[] args) {return get_keyBinding((int)/*64*/args[0], (int)/*64*/args[1], args[2], args[3]);}
			public int /*long*/ method7(int /*long*/[] args) {return get_name((int)/*64*/args[0], args[1]);}
			public int /*long*/ method8(int /*long*/[] args) {return get_localizedName((int)/*64*/args[0], args[1]);}
		};

		objIAccessibleApplication = new COMObject(new int[] {2,0,0,1,1,1,1}) {
			public int /*long*/ method0(int /*long*/[] args) {return QueryInterface(args[0], args[1]);}
			public int /*long*/ method1(int /*long*/[] args) {return AddRef();}
			public int /*long*/ method2(int /*long*/[] args) {return Release();}
			public int /*long*/ method3(int /*long*/[] args) {return get_appName(args[0]);}
			public int /*long*/ method4(int /*long*/[] args) {return get_appVersion(args[0]);}
			public int /*long*/ method5(int /*long*/[] args) {return get_toolkitName(args[0]);}
			public int /*long*/ method6(int /*long*/[] args) {return get_toolkitVersion(args[0]);}
		};

		objIAccessibleComponent = new COMObject(new int[] {2,0,0,2,1,1}) {
			public int /*long*/ method0(int /*long*/[] args) {return QueryInterface(args[0], args[1]);}
			public int /*long*/ method1(int /*long*/[] args) {return AddRef();}
			public int /*long*/ method2(int /*long*/[] args) {return Release();}
			public int /*long*/ method3(int /*long*/[] args) {return get_locationInParent(args[0], args[1]);}
			public int /*long*/ method4(int /*long*/[] args) {return get_foreground(args[0]);}
			public int /*long*/ method5(int /*long*/[] args) {return get_background(args[0]);}
		};

		objIAccessibleEditableText = new COMObject(new int[] {2,0,0,2,2,2,2,1,3,3}) {
			public int /*long*/ method0(int /*long*/[] args) {return QueryInterface(args[0], args[1]);}
			public int /*long*/ method1(int /*long*/[] args) {return AddRef();}
			public int /*long*/ method2(int /*long*/[] args) {return Release();}
			public int /*long*/ method3(int /*long*/[] args) {return copyText((int)/*64*/args[0], (int)/*64*/args[1]);}
			public int /*long*/ method4(int /*long*/[] args) {return deleteText((int)/*64*/args[0], (int)/*64*/args[1]);}
			public int /*long*/ method5(int /*long*/[] args) {return insertText((int)/*64*/args[0], args[1]);}
			public int /*long*/ method6(int /*long*/[] args) {return cutText((int)/*64*/args[0], (int)/*64*/args[1]);}
			public int /*long*/ method7(int /*long*/[] args) {return pasteText((int)/*64*/args[0]);}
			public int /*long*/ method8(int /*long*/[] args) {return replaceText((int)/*64*/args[0], (int)/*64*/args[1], args[2]);}
			public int /*long*/ method9(int /*long*/[] args) {return setAttributes((int)/*64*/args[0], (int)/*64*/args[1], args[2]);}
		};

		objIAccessibleHyperlink = new COMObject(new int[] {2,0,0,/*TODO: IAccessibleAction*/2,2,1,1,1}) {
			public int /*long*/ method0(int /*long*/[] args) {return QueryInterface(args[0], args[1]);}
			public int /*long*/ method1(int /*long*/[] args) {return AddRef();}
			public int /*long*/ method2(int /*long*/[] args) {return Release();}
			// TODO: Add IAccessibleAction methods here
			public int /*long*/ method9(int /*long*/[] args) {return get_anchor((int)/*64*/args[0], args[1]);}
			public int /*long*/ method10(int /*long*/[] args) {return get_anchorTarget((int)/*64*/args[0], args[1]);}
			public int /*long*/ method11(int /*long*/[] args) {return get_startIndex(args[0]);}
			public int /*long*/ method12(int /*long*/[] args) {return get_endIndex(args[0]);}
			public int /*long*/ method13(int /*long*/[] args) {return get_valid(args[0]);}
		};

		objIAccessibleHypertext = new COMObject(new int[] {2,0,0,/*TODO: IAccessibleText*/1,2,2}) {
			public int /*long*/ method0(int /*long*/[] args) {return QueryInterface(args[0], args[1]);}
			public int /*long*/ method1(int /*long*/[] args) {return AddRef();}
			public int /*long*/ method2(int /*long*/[] args) {return Release();}
			// TODO: Add IAccessibleText methods here
			public int /*long*/ method22(int /*long*/[] args) {return get_nHyperlinks(args[0]);}
			public int /*long*/ method23(int /*long*/[] args) {return get_hyperlink((int)/*64*/args[0], args[1]);}
			public int /*long*/ method24(int /*long*/[] args) {return get_hyperlinkIndex((int)/*64*/args[0], args[1]);}
		};

		objIAccessibleImage = new COMObject(new int[] {2,0,0,1,3,2}) {
			public int /*long*/ method0(int /*long*/[] args) {return QueryInterface(args[0], args[1]);}
			public int /*long*/ method1(int /*long*/[] args) {return AddRef();}
			public int /*long*/ method2(int /*long*/[] args) {return Release();}
			public int /*long*/ method3(int /*long*/[] args) {return get_description(args[0]);}
			public int /*long*/ method4(int /*long*/[] args) {return get_imagePosition(args[0], args[1], args[2]);}
			public int /*long*/ method5(int /*long*/[] args) {return get_imageSize(args[0], args[1]);}
		};

		objIAccessibleRelation = new COMObject(new int[] {2,0,0,1,1,1,2,3}) {
			public int /*long*/ method0(int /*long*/[] args) {return QueryInterface(args[0], args[1]);}
			public int /*long*/ method1(int /*long*/[] args) {return AddRef();}
			public int /*long*/ method2(int /*long*/[] args) {return Release();}
			public int /*long*/ method3(int /*long*/[] args) {return get_relationType(args[0]);}
			public int /*long*/ method4(int /*long*/[] args) {return get_localizedRelationType(args[0]);}
			public int /*long*/ method5(int /*long*/[] args) {return get_nTargets(args[0]);}
			public int /*long*/ method6(int /*long*/[] args) {return get_target((int)/*64*/args[0], args[1]);}
			public int /*long*/ method7(int /*long*/[] args) {return get_targets((int)/*64*/args[0], args[1], args[2]);}
		};

		objIAccessibleTable2 = new COMObject(new int[] {2,0,0,3,1,2,1,1,1,1,1,2,2,2,2,1,2,2,1,1,1,1,1}) {
			public int /*long*/ method0(int /*long*/[] args) {return QueryInterface(args[0], args[1]);}
			public int /*long*/ method1(int /*long*/[] args) {return AddRef();}
			public int /*long*/ method2(int /*long*/[] args) {return Release();}
			public int /*long*/ method3(int /*long*/[] args) {return get_cellAt((int)/*64*/args[0], (int)/*64*/args[1], args[2]);}
			public int /*long*/ method4(int /*long*/[] args) {return get_caption(args[0]);}
			public int /*long*/ method5(int /*long*/[] args) {return get_columnDescription((int)/*64*/args[0], args[1]);}
			public int /*long*/ method6(int /*long*/[] args) {return get_nColumns(args[0]);}
			public int /*long*/ method7(int /*long*/[] args) {return get_nRows(args[0]);}
			public int /*long*/ method8(int /*long*/[] args) {return get_nSelectedCells(args[0]);}
			public int /*long*/ method9(int /*long*/[] args) {return get_nSelectedColumns(args[0]);}
			public int /*long*/ method10(int /*long*/[] args) {return get_nSelectedRows(args[0]);}
			public int /*long*/ method11(int /*long*/[] args) {return get_rowDescription((int)/*64*/args[0], args[1]);}
			public int /*long*/ method12(int /*long*/[] args) {return get_selectedCells(args[0], args[1]);}
			public int /*long*/ method13(int /*long*/[] args) {return get_selectedColumns(args[0], args[1]);}
			public int /*long*/ method14(int /*long*/[] args) {return get_selectedRows(args[0], args[1]);}
			public int /*long*/ method15(int /*long*/[] args) {return get_summary(args[0]);}
			public int /*long*/ method16(int /*long*/[] args) {return get_isColumnSelected((int)/*64*/args[0], args[1]);}
			public int /*long*/ method17(int /*long*/[] args) {return get_isRowSelected((int)/*64*/args[0], args[1]);}
			public int /*long*/ method18(int /*long*/[] args) {return selectRow((int)/*64*/args[0]);}
			public int /*long*/ method19(int /*long*/[] args) {return selectColumn((int)/*64*/args[0]);}
			public int /*long*/ method20(int /*long*/[] args) {return unselectRow((int)/*64*/args[0]);}
			public int /*long*/ method21(int /*long*/[] args) {return unselectColumn((int)/*64*/args[0]);}
			public int /*long*/ method22(int /*long*/[] args) {return get_modelChange(args[0]);}
		};

		objIAccessibleTableCell = new COMObject(new int[] {2,0,0,1,2,1,1,2,1,1,5,1}) {
			public int /*long*/ method0(int /*long*/[] args) {return QueryInterface(args[0], args[1]);}
			public int /*long*/ method1(int /*long*/[] args) {return AddRef();}
			public int /*long*/ method2(int /*long*/[] args) {return Release();}
			public int /*long*/ method3(int /*long*/[] args) {return get_columnExtent(args[0]);}
			public int /*long*/ method4(int /*long*/[] args) {return get_columnHeaderCells(args[0], args[1]);}
			public int /*long*/ method5(int /*long*/[] args) {return get_columnIndex(args[0]);}
			public int /*long*/ method6(int /*long*/[] args) {return get_rowExtent(args[0]);}
			public int /*long*/ method7(int /*long*/[] args) {return get_rowHeaderCells(args[0], args[1]);}
			public int /*long*/ method8(int /*long*/[] args) {return get_rowIndex(args[0]);}
			public int /*long*/ method9(int /*long*/[] args) {return get_isSelected(args[0]);}
			public int /*long*/ method10(int /*long*/[] args) {return get_rowColumnExtents(args[0], args[1], args[2], args[3], args[4]);}
			public int /*long*/ method11(int /*long*/[] args) {return get_table(args[0]);}
		};

		objIAccessibleText = new COMObject(new int[] {2,0,0,2,4,1,6,1,4,3,3,5,5,5,1,1,3,1,3,5,1,1}) {
			public int /*long*/ method0(int /*long*/[] args) {return QueryInterface(args[0], args[1]);}
			public int /*long*/ method1(int /*long*/[] args) {return AddRef();}
			public int /*long*/ method2(int /*long*/[] args) {return Release();}
			public int /*long*/ method3(int /*long*/[] args) {return addSelection((int)/*64*/args[0], (int)/*64*/args[1]);}
			public int /*long*/ method4(int /*long*/[] args) {return get_attributes((int)/*64*/args[0], args[1], args[2], args[3]);}
			public int /*long*/ method5(int /*long*/[] args) {return get_caretOffset(args[0]);}
			public int /*long*/ method6(int /*long*/[] args) {return get_characterExtents((int)/*64*/args[0], args[1], args[2], args[3], args[4], args[5]);}
			public int /*long*/ method7(int /*long*/[] args) {return get_nSelections(args[0]);}
			public int /*long*/ method8(int /*long*/[] args) {return get_offsetAtPoint((int)/*64*/args[0], (int)/*64*/args[1], args[2], args[3]);}
			public int /*long*/ method9(int /*long*/[] args) {return get_selection((int)/*64*/args[0], args[1], args[2]);}
			public int /*long*/ method10(int /*long*/[] args) {return get_text((int)/*64*/args[0], (int)/*64*/args[1], args[2]);}
			public int /*long*/ method11(int /*long*/[] args) {return get_textBeforeOffset((int)/*64*/args[0], args[1], args[2], args[3], args[4]);}
			public int /*long*/ method12(int /*long*/[] args) {return get_textAfterOffset((int)/*64*/args[0], args[1], args[2], args[3], args[4]);}
			public int /*long*/ method13(int /*long*/[] args) {return get_textAtOffset((int)/*64*/args[0], args[1], args[2], args[3], args[4]);}
			public int /*long*/ method14(int /*long*/[] args) {return removeSelection((int)/*64*/args[0]);}
			public int /*long*/ method15(int /*long*/[] args) {return setCaretOffset((int)/*64*/args[0]);}
			public int /*long*/ method16(int /*long*/[] args) {return setSelection((int)/*64*/args[0], (int)/*64*/args[1], (int)/*64*/args[2]);}
			public int /*long*/ method17(int /*long*/[] args) {return get_nCharacters(args[0]);}
			public int /*long*/ method18(int /*long*/[] args) {return scrollSubstringTo((int)/*64*/args[0], (int)/*64*/args[1], args[2]);}
			public int /*long*/ method19(int /*long*/[] args) {return scrollSubstringToPoint((int)/*64*/args[0], (int)/*64*/args[1], args[2], (int)/*64*/args[3], (int)/*64*/args[4]);}
			public int /*long*/ method20(int /*long*/[] args) {return get_newText(args[0]);}
			public int /*long*/ method21(int /*long*/[] args) {return get_oldText(args[0]);}
		};

		objIAccessibleValue = new COMObject(new int[] {2,0,0,1,1,1,1}) {
			public int /*long*/ method0(int /*long*/[] args) {return QueryInterface(args[0], args[1]);}
			public int /*long*/ method1(int /*long*/[] args) {return AddRef();}
			public int /*long*/ method2(int /*long*/[] args) {return Release();}
			public int /*long*/ method3(int /*long*/[] args) {return get_currentValue(args[0]);}
			public int /*long*/ method4(int /*long*/[] args) {return setCurrentValue(args[0]);}
			public int /*long*/ method5(int /*long*/[] args) {return get_maximumValue(args[0]);}
			public int /*long*/ method6(int /*long*/[] args) {return get_minimumValue(args[0]);}
		};
	}
	
	/**
	 * Invokes platform specific functionality to allocate a new accessible object.
	 * <p>
	 * <b>IMPORTANT:</b> This method is <em>not</em> part of the public
	 * API for <code>Accessible</code>. It is marked public only so that it
	 * can be shared within the packages provided by SWT. It is not
	 * available on all platforms, and should never be called from
	 * application code.
	 * </p>
	 *
	 * @param control the control to get the accessible object for
	 * @return the platform specific accessible object
	 */
	public static Accessible internal_new_Accessible(Control control) {
		return new Accessible(control);
	}

	/**
	 * Adds the listener to the collection of listeners who will
	 * be notified when an accessible client asks for certain strings,
	 * such as name, description, help, or keyboard shortcut. The
	 * listener is notified by sending it one of the messages defined
	 * in the <code>AccessibleListener</code> interface.
	 *
	 * @param listener the listener that should be notified when the receiver
	 * is asked for a name, description, help, or keyboard shortcut string
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleListener
	 * @see #removeAccessibleListener
	 */
	public void addAccessibleListener(AccessibleListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleListeners.addElement(listener);
	}
	
	/**
	 * Adds the listener to the collection of listeners who will
	 * be notified when an accessible client asks for custom control
	 * specific information. The listener is notified by sending it
	 * one of the messages defined in the <code>AccessibleControlListener</code>
	 * interface.
	 *
	 * @param listener the listener that should be notified when the receiver
	 * is asked for custom control specific information
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleControlListener
	 * @see #removeAccessibleControlListener
	 */
	public void addAccessibleControlListener(AccessibleControlListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleControlListeners.addElement(listener);
	}

	/**
	 * Adds the listener to the collection of listeners who will
	 * be notified when an accessible client asks for custom text control
	 * specific information. The listener is notified by sending it
	 * one of the messages defined in the <code>AccessibleTextListener</code>
	 * interface.
	 *
	 * @param listener the listener that should be notified when the receiver
	 * is asked for custom text control specific information
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleTextListener
	 * @see #removeAccessibleTextListener
	 * 
	 * @since 3.0
	 */
	public void addAccessibleTextListener (AccessibleTextListener listener) {
		checkWidget ();
		if (listener == null) SWT.error (SWT.ERROR_NULL_ARGUMENT);
		accessibleTextListeners.addElement (listener);		
	}
	
	/**
	 * Adds the listener to the collection of listeners that will be
	 * notified when an accessible client asks for any of the properties
	 * defined in the <code>AccessibleAction</code> interface.
	 *
	 * @param listener the listener that should be notified when the receiver
	 * is asked for <code>AccessibleAction</code> interface properties
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleActionListener
	 * @see #removeAccessibleActionListener
	 * 
	 * @since 3.6
	 */
	public void addAccessibleActionListener(AccessibleActionListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleActionListeners.addElement(listener);
	}

	/**
	 * Adds the listener to the collection of listeners that will be
	 * notified when an accessible client asks for any of the properties
	 * defined in the <code>AccessibleHyperlink</code> interface.
	 *
	 * @param listener the listener that should be notified when the receiver
	 * is asked for <code>AccessibleHyperlink</code> interface properties
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleHyperlinkListener
	 * @see #removeAccessibleHyperlinkListener
	 * 
	 * @since 3.6
	 */
	public void addAccessibleHyperlinkListener(AccessibleHyperlinkListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleHyperlinkListeners.addElement(listener);
	}

	/**
	 * Adds the listener to the collection of listeners that will be
	 * notified when an accessible client asks for any of the properties
	 * defined in the <code>AccessibleTable</code> interface.
	 *
	 * @param listener the listener that should be notified when the receiver
	 * is asked for <code>AccessibleTable</code> interface properties
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleTableListener
	 * @see #removeAccessibleTableListener
	 * 
	 * @since 3.6
	 */
	public void addAccessibleTableListener(AccessibleTableListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleTableListeners.addElement(listener);
	}

	/**
	 * Adds the listener to the collection of listeners that will be
	 * notified when an accessible client asks for any of the properties
	 * defined in the <code>AccessibleTableCell</code> interface.
	 *
	 * @param listener the listener that should be notified when the receiver
	 * is asked for <code>AccessibleTableCell</code> interface properties
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleTableCellListener
	 * @see #removeAccessibleTableCellListener
	 * 
	 * @since 3.6
	 */
	public void addAccessibleTableCellListener(AccessibleTableCellListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleTableCellListeners.addElement(listener);
	}

	/**
	 * Adds the listener to the collection of listeners that will be
	 * notified when an accessible client asks for any of the properties
	 * defined in the <code>AccessibleTextExtended</code> interface.
	 *
	 * @param listener the listener that should be notified when the receiver
	 * is asked for <code>AccessibleTextExtended</code> interface properties
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleTextExtendedListener
	 * @see #removeAccessibleTextExtendedListener
	 * 
	 * @since 3.6
	 */
	public void addAccessibleTextExtendedListener(AccessibleTextExtendedListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleTextExtendedListeners.addElement(listener);
	}

	/**
	 * Adds the listener to the collection of listeners that will be
	 * notified when an accessible client asks for any of the properties
	 * defined in the <code>AccessibleValue</code> interface.
	 *
	 * @param listener the listener that should be notified when the receiver
	 * is asked for <code>AccessibleValue</code> interface properties
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleValueListener
	 * @see #removeAccessibleValueListener
	 * 
	 * @since 3.6
	 */
	public void addAccessibleValueListener(AccessibleValueListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleValueListeners.addElement(listener);
	}

	/**
	 * Adds the listener to the collection of listeners that will be
	 * notified when an accessible client asks for any of the properties
	 * defined in the <code>AccessibleScroll</code> interface.
	 *
	 * @param listener the listener that should be notified when the receiver
	 * is asked for <code>AccessibleScroll</code> interface properties
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleScrollListener
	 * @see #removeAccessibleScrollListener
	 * 
	 * @since 3.6
	 */
	public void addAccessibleScrollListener(AccessibleScrollListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleScrollListeners.addElement(listener);
	}

	/**
	 * Adds the listener to the collection of listeners that will be
	 * notified when an accessible client asks for any of the properties
	 * defined in the <code>AccessibleAttribute</code> interface.
	 *
	 * @param listener the listener that should be notified when the receiver
	 * is asked for <code>AccessibleAttribute</code> interface properties
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleAttributeListener
	 * @see #removeAccessibleAttributeListener
	 * 
	 * @since 3.6
	 */
	public void addAccessibleAttributeListener(AccessibleAttributeListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleAttributeListeners.addElement(listener);
	}

	/**
	 * Adds a relation with the specified type and targets
	 * to the receiver's set of relations.
	 * 
	 * @param type a constant beginning with RELATION_* indicating the type of relation
	 * @param targets the accessible(s) that are targets for this relation
	 * 
	 * @since 3.6
	 */
	public void addAccessibleRelation(int type, Accessible targets[]) {
		//TODO: platform-specific? (we will manage the set on Windows)
	}
	
	/**
	 * Returns the control for this Accessible object. 
	 *
	 * @return the receiver's control
	 * @since 3.0
	 */
	public Control getControl() {
		return control;
	}

	/**
	 * Invokes platform specific functionality to dispose an accessible object.
	 * <p>
	 * <b>IMPORTANT:</b> This method is <em>not</em> part of the public
	 * API for <code>Accessible</code>. It is marked public only so that it
	 * can be shared within the packages provided by SWT. It is not
	 * available on all platforms, and should never be called from
	 * application code.
	 * </p>
	 */
	public void internal_dispose_Accessible() {
		if (iaccessible != null) {
			iaccessible.Release();
		}
		iaccessible = null;
		Release();
	}
	
	/**
	 * Invokes platform specific functionality to handle a window message.
	 * <p>
	 * <b>IMPORTANT:</b> This method is <em>not</em> part of the public
	 * API for <code>Accessible</code>. It is marked public only so that it
	 * can be shared within the packages provided by SWT. It is not
	 * available on all platforms, and should never be called from
	 * application code.
	 * </p>
	 */
	public int /*long*/ internal_WM_GETOBJECT (int /*long*/ wParam, int /*long*/ lParam) {
		if (objIAccessible == null) return 0;
		if ((int)/*64*/lParam == COM.OBJID_CLIENT) {
			/* LresultFromObject([in] riid, [in] wParam, [in] pAcc)
			 * The argument pAcc is owned by the caller so reference count does not
			 * need to be incremented.
			 */
			return COM.LresultFromObject(COM.IIDIAccessible, wParam, objIAccessible.getAddress());
		}
		return 0;
	}

	/**
	 * Removes the listener from the collection of listeners who will
	 * be notified when an accessible client asks for certain strings,
	 * such as name, description, help, or keyboard shortcut.
	 *
	 * @param listener the listener that should no longer be notified when the receiver
	 * is asked for a name, description, help, or keyboard shortcut string
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleListener
	 * @see #addAccessibleListener
	 */
	public void removeAccessibleListener(AccessibleListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleListeners.removeElement(listener);
	}

	/**
	 * Removes the listener from the collection of listeners who will
	 * be notified when an accessible client asks for custom control
	 * specific information.
	 *
	 * @param listener the listener that should no longer be notified when the receiver
	 * is asked for custom control specific information
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleControlListener
	 * @see #addAccessibleControlListener
	 */
	public void removeAccessibleControlListener(AccessibleControlListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleControlListeners.removeElement(listener);
	}

	/**
	 * Removes the listener from the collection of listeners who will
	 * be notified when an accessible client asks for custom text control
	 * specific information.
	 *
	 * @param listener the listener that should no longer be notified when the receiver
	 * is asked for custom text control specific information
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleTextListener
	 * @see #addAccessibleTextListener
	 * 
	 * @since 3.0
	 */
	public void removeAccessibleTextListener (AccessibleTextListener listener) {
		checkWidget ();
		if (listener == null) SWT.error (SWT.ERROR_NULL_ARGUMENT);
		accessibleTextListeners.removeElement (listener);
	}

	/**
	 * Removes the listener from the collection of listeners that will be
	 * notified when an accessible client asks for any of the properties
	 * defined in the <code>AccessibleAction</code> interface.
	 *
	 * @param listener the listener that should no longer be notified when the receiver
	 * is asked for <code>AccessibleAction</code> interface properties
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleActionListener
	 * @see #addAccessibleActionListener
	 * 
	 * @since 3.6
	 */
	public void removeAccessibleActionListener(AccessibleActionListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleActionListeners.removeElement(listener);
	}

	/**
	 * Removes the listener from the collection of listeners that will be
	 * notified when an accessible client asks for any of the properties
	 * defined in the <code>AccessibleHyperlink</code> interface.
	 *
	 * @param listener the listener that should no longer be notified when the receiver
	 * is asked for <code>AccessibleHyperlink</code> interface properties
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleHyperlinkListener
	 * @see #addAccessibleHyperlinkListener
	 * 
	 * @since 3.6
	 */
	public void removeAccessibleHyperlinkListener(AccessibleHyperlinkListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleHyperlinkListeners.removeElement(listener);
	}

	/**
	 * Removes the listener from the collection of listeners that will be
	 * notified when an accessible client asks for any of the properties
	 * defined in the <code>AccessibleTable</code> interface.
	 *
	 * @param listener the listener that should no longer be notified when the receiver
	 * is asked for <code>AccessibleTable</code> interface properties
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleTableListener
	 * @see #addAccessibleTableListener
	 * 
	 * @since 3.6
	 */
	public void removeAccessibleTableListener(AccessibleTableListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleTableListeners.removeElement(listener);
	}

	/**
	 * Removes the listener from the collection of listeners that will be
	 * notified when an accessible client asks for any of the properties
	 * defined in the <code>AccessibleTableCell</code> interface.
	 *
	 * @param listener the listener that should no longer be notified when the receiver
	 * is asked for <code>AccessibleTableCell</code> interface properties
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleTableCellListener
	 * @see #addAccessibleTableCellListener
	 * 
	 * @since 3.6
	 */
	public void removeAccessibleTableCellListener(AccessibleTableCellListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleTableCellListeners.removeElement(listener);
	}

	/**
	 * Removes the listener from the collection of listeners that will be
	 * notified when an accessible client asks for any of the properties
	 * defined in the <code>AccessibleTextExtended</code> interface.
	 *
	 * @param listener the listener that should no longer be notified when the receiver
	 * is asked for <code>AccessibleTextExtended</code> interface properties
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleTextExtendedListener
	 * @see #addAccessibleTextExtendedListener
	 * 
	 * @since 3.6
	 */
	public void removeAccessibleTextExtendedListener(AccessibleTextExtendedListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleTextExtendedListeners.removeElement(listener);
	}

	/**
	 * Removes the listener from the collection of listeners that will be
	 * notified when an accessible client asks for any of the properties
	 * defined in the <code>AccessibleValue</code> interface.
	 *
	 * @param listener the listener that should no longer be notified when the receiver
	 * is asked for <code>AccessibleValue</code> interface properties
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleValueListener
	 * @see #addAccessibleValueListener
	 * 
	 * @since 3.6
	 */
	public void removeAccessibleValueListener(AccessibleValueListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleValueListeners.removeElement(listener);
	}

	/**
	 * Removes the listener from the collection of listeners that will be
	 * notified when an accessible client asks for any of the properties
	 * defined in the <code>AccessibleScroll</code> interface.
	 *
	 * @param listener the listener that should no longer be notified when the receiver
	 * is asked for <code>AccessibleScroll</code> interface properties
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleScrollListener
	 * @see #addAccessibleScrollListener
	 * 
	 * @since 3.6
	 */
	public void removeAccessibleScrollListener(AccessibleScrollListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleScrollListeners.removeElement(listener);
	}

	/**
	 * Removes the listener from the collection of listeners that will be
	 * notified when an accessible client asks for any of the properties
	 * defined in the <code>AccessibleAttribute</code> interface.
	 *
	 * @param listener the listener that should no longer be notified when the receiver
	 * is asked for <code>AccessibleAttribute</code> interface properties
	 *
	 * @exception IllegalArgumentException <ul>
	 *    <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 * </ul>
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @see AccessibleAttributeListener
	 * @see #addAccessibleAttributeListener
	 * 
	 * @since 3.6
	 */
	public void removeAccessibleAttributeListener(AccessibleAttributeListener listener) {
		checkWidget();
		if (listener == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
		accessibleAttributeListeners.removeElement(listener);
	}

	/**
	 * Removes the relation with the specified type and targets
	 * from the receiver's set of relations.
	 * 
	 * @param type a constant beginning with RELATION_* indicating the type of relation
	 * @param targets the accessible(s) that are targets for this relation
	 * 
	 * @since 3.6
	 */
	public void removeAccessibleRelation(int type, Accessible targets[]) {
		//TODO: platform-specific? (we will manage the set on Windows)
	}
	
	/**
	 * Sends a message to accessible clients indicating that something
	 * has changed within a custom control.
	 *
	 * @param event a constant beginning with EVENT_* indicating the message to send
	 * @param childID an identifier specifying a child of the control or the control itself
	 * 
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 * 
	 * @since 3.6
	 */
	public void sendEvent(int event, int childID) {
		checkWidget();
		//TODO: send platform-specific event (i.e. WinEvent with EVENT_OBJECT_* or IA2_EVENT_* on Win, Signal on ATK, Notification on Mac)
	}

	/**
	 * Sends a message to accessible clients that the child selection
	 * within a custom container control has changed.
	 *
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 * 
	 * @since 3.0
	 */
	public void selectionChanged () {
		checkWidget();
		COM.NotifyWinEvent (COM.EVENT_OBJECT_SELECTIONWITHIN, control.handle, COM.OBJID_CLIENT, COM.CHILDID_SELF);
	}

	/**
	 * Sends a message to accessible clients indicating that the focus
	 * has changed within a custom control.
	 *
	 * @param childID an identifier specifying a child of the control
	 * 
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 */
	public void setFocus(int childID) {
		checkWidget();
		COM.NotifyWinEvent (COM.EVENT_OBJECT_FOCUS, control.handle, COM.OBJID_CLIENT, childIDToOs(childID));
	}

	/**
	 * Sends a message to accessible clients that the text
	 * caret has moved within a custom control.
	 *
	 * @param index the new caret index within the control
	 * 
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @since 3.0
	 */
	public void textCaretMoved (int index) {
		checkWidget();
		COM.NotifyWinEvent (COM.EVENT_OBJECT_LOCATIONCHANGE, control.handle, COM.OBJID_CARET, COM.CHILDID_SELF);
	}
	
	/**
	 * Sends a message to accessible clients that the text
	 * within a custom control has changed.
	 *
	 * @param type the type of change, one of <code>ACC.NOTIFY_TEXT_INSERT</code>
	 * or <code>ACC.NOTIFY_TEXT_DELETE</code>
	 * @param startIndex the text index within the control where the insertion or deletion begins
	 * @param length the non-negative length in characters of the insertion or deletion
	 *
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 * 
	 * @see ACC#TEXT_INSERT
	 * @see ACC#TEXT_DELETE
	 * 
	 * @since 3.0
	 */
	public void textChanged (int type, int startIndex, int length) {
		checkWidget();
		COM.NotifyWinEvent (COM.EVENT_OBJECT_VALUECHANGE, control.handle, COM.OBJID_CLIENT, COM.CHILDID_SELF);
	}
	
	/**
	 * Sends a message to accessible clients that the text
	 * selection has changed within a custom control.
	 *
	 * @exception SWTException <ul>
	 *    <li>ERROR_WIDGET_DISPOSED - if the receiver's control has been disposed</li>
	 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver's control</li>
	 * </ul>
	 *
	 * @since 3.0
	 */
	public void textSelectionChanged () {
		checkWidget();
		// not an MSAA event
	}
	
	/* QueryInterface([in] iid, [out] ppvObject)
	 * Ownership of ppvObject transfers from callee to caller so reference count on ppvObject 
	 * must be incremented before returning.  Caller is responsible for releasing ppvObject.
	 */
	int QueryInterface(int /*long*/ iid, int /*long*/ ppvObject) {
		GUID guid = new GUID();
		COM.MoveMemory(guid, iid, GUID.sizeof);

		if (COM.IsEqualGUID(guid, COM.IIDIUnknown)) {
			COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessible.getAddress() }, OS.PTR_SIZEOF);
			AddRef();
			return COM.S_OK;
		}

		if (COM.IsEqualGUID(guid, COM.IIDIDispatch)) {
			COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessible.getAddress() }, OS.PTR_SIZEOF);
			AddRef();
			return COM.S_OK;
		}

		if (COM.IsEqualGUID(guid, COM.IIDIAccessible)) {
			COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessible.getAddress() }, OS.PTR_SIZEOF);
			AddRef();
			return COM.S_OK;
		}

		if (COM.IsEqualGUID(guid, COM.IIDIEnumVARIANT)) {
			COM.MoveMemory(ppvObject, new int /*long*/[] { objIEnumVARIANT.getAddress() }, OS.PTR_SIZEOF);
			AddRef();
			enumIndex = 0;
			return COM.S_OK;
		}
		
		if (COM.IsEqualGUID(guid, COM.IIDIServiceProvider)) {
			COM.MoveMemory(ppvObject, new int /*long*/[] { objIServiceProvider.getAddress() }, OS.PTR_SIZEOF);
			AddRef();
			return COM.S_OK;
		}

		if (COM.IsEqualGUID(guid, COM.IIDIAccessible2)) {
			COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessible2.getAddress() }, OS.PTR_SIZEOF);
			AddRef();
			return COM.S_OK;
		}

		if (iaccessible != null) {
			/* Forward any other GUIDs to the OS proxy. */
			int /*long*/[] ppv = new int /*long*/[1];
			int code = iaccessible.QueryInterface(guid, ppv);
			COM.MoveMemory(ppvObject, ppv, OS.PTR_SIZEOF);
			return code;
		}
		
		return COM.E_NOINTERFACE;
	}

	int AddRef() {
		refCount++;
		return refCount;
	}

	int Release() {
		refCount--;

		if (refCount == 0) {
			if (objIAccessible != null)
				objIAccessible.dispose();
			objIAccessible = null;
						
			if (objIEnumVARIANT != null)
				objIEnumVARIANT.dispose();
			objIEnumVARIANT = null;
		}
		return refCount;
	}

	/* QueryService([in] guidService, [in] riid, [out] ppvObject) */
    int QueryService(int /*long*/ guidService, int /*long*/ riid, int /*long*/ ppvObject) {
        GUID service = new GUID();
        COM.MoveMemory(service, guidService, GUID.sizeof);
        GUID guid = new GUID();
        COM.MoveMemory(guid, riid, GUID.sizeof);

        // TODO: This all needs to be verified & rewritten - see IA2 mailing list for discussion - for now, just always answer everything <grin>
        if (COM.IsEqualGUID(guid, COM.IIDIUnknown)) {
            if (COM.IsEqualGUID(service, COM.IIDIAccessible)) {
    			COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessible.getAddress() }, OS.PTR_SIZEOF);
	            AddRef();
	            return COM.S_OK;
	        }

	        if (COM.IsEqualGUID(service, COM.IIDIAccessible2)) {
    			COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessible2.getAddress() }, OS.PTR_SIZEOF);
	            AddRef();
	            return COM.S_OK;
	        }
        }

        if (COM.IsEqualGUID(service, COM.IIDIAccessible) || COM.IsEqualGUID(service, COM.IIDIAccessible2)) {
            if (COM.IsEqualGUID(guid, COM.IIDIAccessible2)/*&&accessible2Listeners.size() !=0*/) {
    			COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessible2.getAddress() }, OS.PTR_SIZEOF);
            	AddRef();
            	return COM.S_OK;
            }
            
            if (COM.IsEqualGUID(guid, COM.IIDIAccessibleApplication)/*&&accessibleApplicationListeners.size() !=0*/) {
    			COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessibleApplication.getAddress() }, OS.PTR_SIZEOF);
                AddRef();
                return COM.S_OK;
            }
            
            if (COM.IsEqualGUID(guid, COM.IIDIAccessibleComponent)/*&&accessibleComponentListeners.size() !=0*/) {
    			COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessibleComponent.getAddress() }, OS.PTR_SIZEOF);
                AddRef();
                return COM.S_OK;
            }
            
            if (COM.IsEqualGUID(guid, COM.IIDIAccessibleEditableText)/*&&accessibleEditableTextListeners.size() !=0*/) {
    			COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessibleEditableText.getAddress() }, OS.PTR_SIZEOF);
                AddRef();
                return COM.S_OK;
            }
            
            if (COM.IsEqualGUID(guid, COM.IIDIAccessibleHypertext)/*&&accessibleHypertextListeners.size() !=0*/) {
    			COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessibleHypertext.getAddress() }, OS.PTR_SIZEOF);
                AddRef();
                return COM.S_OK;
            }
            
            if (COM.IsEqualGUID(guid, COM.IIDIAccessibleImage)/*&&accessibleImageListeners.size() !=0*/) {
    			COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessibleImage.getAddress() }, OS.PTR_SIZEOF);
                AddRef();
                return COM.S_OK;
            }
            
            if (COM.IsEqualGUID(guid, COM.IIDIAccessibleTable) /*&& accessibleTableListeners.size() != 0*/) {
    			COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessibleTable2.getAddress() }, OS.PTR_SIZEOF);
                AddRef();
                return COM.S_OK;
            }
            
            if (COM.IsEqualGUID(guid, COM.IIDIAccessibleTableCell) /*&& accessibleTableListeners.size() != 0*/) {
    			COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessibleTableCell.getAddress() }, OS.PTR_SIZEOF);
                AddRef();
                return COM.S_OK;
            }
            
            if (COM.IsEqualGUID(guid, COM.IIDIAccessibleText)/*&&accessibleExtendedTextListeners.size() !=0*/) {
    			COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessibleText.getAddress() }, OS.PTR_SIZEOF);
                AddRef();
                return COM.S_OK;
            }
            
            if (COM.IsEqualGUID(guid, COM.IIDIAccessibleValue)/*&&accessibleValueListeners.size() !=0*/) {
    			COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessibleValue.getAddress() }, OS.PTR_SIZEOF);
                AddRef();
                return COM.S_OK;
            }
            
            /* Action, Relation, and Hyperlink are handled in separate objects because
             * these interfaces have a many-to-one relationship with Accessible2 objects. */
//            if (COM.IsEqualGUID(guid2, COM.IIDIAccessibleAction) && accessibleActionListeners.size() != 0) {
//            	COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessibleAction.getAddress() }, OS.PTR_SIZEOF);
//                AddRef();
//                return COM.S_OK;
//            }
//            
//            if (COM.IsEqualGUID(guid2, COM.IIDIAccessibleRelation) && accessibleRelationListeners.size() != 0) {
//                COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessibleRelation.getAddress() }, OS.PTR_SIZEOF);
//                AddRef();
//                return COM.S_OK;
//            }
//            
//            if (COM.IsEqualGUID(guid2, COM.IIDIAccessibleHyperlink) && accessibleHyperlinkListeners.size() != 0) {
//                COM.MoveMemory(ppvObject, new int /*long*/[] { objIAccessibleHyperlink.getAddress() }, OS.PTR_SIZEOF);
//                AddRef();
//                return COM.S_OK;
//            }
        }

         if (iaccessible != null) {
            /* Forward any other GUIDs to the OS proxy. */
            int[] ppv = new int[1];
            int result = iaccessible.QueryInterface(COM.IIDIServiceProvider, ppv);
            if (result == COM.S_OK) {
                IServiceProvider iserviceProvider = new IServiceProvider(ppv[0]);
                int[] ppvx = new int[1];
                result = iserviceProvider.QueryService(service, guid, ppvx);
    			COM.MoveMemory(ppvObject, new int /*long*/[] { ppvx[0] }, OS.PTR_SIZEOF);
                return result;
            }
        }
        
        return COM.E_NOINTERFACE;
    }

    /* accDoDefaultAction([in] varChild) */
	int accDoDefaultAction(int /*long*/ varChild) {
		// TODO: Need to support this. Just do the same as for IA2 default doAction
		int code = COM.DISP_E_MEMBERNOTFOUND;
		if (iaccessible != null) {
			/* Currently, we don't expose this as API. Forward to the proxy. */
			code = iaccessible.accDoDefaultAction(varChild);
			if (code == COM.E_INVALIDARG) code = COM.DISP_E_MEMBERNOTFOUND; // proxy doesn't know about app childID
		}
		return code;
	}

	/* accHitTest([in] xLeft, [in] yTop, [out] pvarChild) */
	int accHitTest(int xLeft, int yTop, int /*long*/ pvarChild) {
		int osChild = ACC.CHILDID_NONE;
		if (iaccessible != null) {
			/* Get the default child at point (left, top) from the OS. */
			int code = iaccessible.accHitTest(xLeft, yTop, pvarChild);
			if (accessibleControlListeners.size() == 0) return code;
			if (code == COM.S_OK) {
				VARIANT v = getVARIANT(pvarChild);
				if (v.vt == COM.VT_I4) osChild = osToChildID(v.lVal);
				// TODO: Should also handle v.vt == COM.VT_DISPATCH
			}
		}

		AccessibleControlEvent event = new AccessibleControlEvent(this);
		event.childID = osChild;
		event.x = xLeft;
		event.y = yTop;
		for (int i = 0; i < accessibleControlListeners.size(); i++) {
			AccessibleControlListener listener = (AccessibleControlListener) accessibleControlListeners.elementAt(i);
			listener.getChildAtPoint(event);
		}
		int childID = event.childID;
		if (childID == ACC.CHILDID_NONE) return COM.S_FALSE;
		setVARIANT(pvarChild, COM.VT_I4, childIDToOs(childID));
		return COM.S_OK;
	}
	
	/* accLocation([out] pxLeft, [out] pyTop, [out] pcxWidth, [out] pcyHeight, [in] varChild) */
	int accLocation(int /*long*/ pxLeft, int /*long*/ pyTop, int /*long*/ pcxWidth, int /*long*/ pcyHeight, int /*long*/ varChild) {
		VARIANT v = getVARIANT(varChild);
		if (v.vt != COM.VT_I4) return COM.E_INVALIDARG;
		int osLeft = 0, osTop = 0, osWidth = 0, osHeight = 0;
		if (iaccessible != null) {
			/* Get the default location from the OS. */
			int code = iaccessible.accLocation(pxLeft, pyTop, pcxWidth, pcyHeight, varChild);
			if (code == COM.E_INVALIDARG) code = COM.DISP_E_MEMBERNOTFOUND; // proxy doesn't know about app childID
			if (accessibleControlListeners.size() == 0) return code;
			if (code == COM.S_OK) {
				int[] pLeft = new int[1], pTop = new int[1], pWidth = new int[1], pHeight = new int[1];
				COM.MoveMemory(pLeft, pxLeft, 4);
				COM.MoveMemory(pTop, pyTop, 4);
				COM.MoveMemory(pWidth, pcxWidth, 4);
				COM.MoveMemory(pHeight, pcyHeight, 4);
				osLeft = pLeft[0]; osTop = pTop[0]; osWidth = pWidth[0]; osHeight = pHeight[0];
			}
		}

		AccessibleControlEvent event = new AccessibleControlEvent(this);
		event.childID = osToChildID(v.lVal);
		event.x = osLeft;
		event.y = osTop;
		event.width = osWidth;
		event.height = osHeight;
		for (int i = 0; i < accessibleControlListeners.size(); i++) {
			AccessibleControlListener listener = (AccessibleControlListener) accessibleControlListeners.elementAt(i);
			listener.getLocation(event);
		}
		OS.MoveMemory(pxLeft, new int[] { event.x }, 4);
		OS.MoveMemory(pyTop, new int[] { event.y }, 4);
		OS.MoveMemory(pcxWidth, new int[] { event.width }, 4);
		OS.MoveMemory(pcyHeight, new int[] { event.height }, 4);
		return COM.S_OK;
	}
	
	/* accNavigate([in] navDir, [in]  varStart, [out]  pvarEndUpAt) */
	int accNavigate(int navDir, int /*long*/ varStart, int /*long*/ pvarEndUpAt) {
		/* MSAA: "The accNavigate method is deprecated and should not be used." */
		int code = COM.DISP_E_MEMBERNOTFOUND;
		if (iaccessible != null) {
			/* Since many of the native controls still handle accNavigate,
			 * we will continue to send this through to the proxy. */
			code = iaccessible.accNavigate(navDir, varStart, pvarEndUpAt);
			if (code == COM.E_INVALIDARG) code = COM.DISP_E_MEMBERNOTFOUND; // proxy doesn't know about app childID
		}
		return code;
	}
	
	/* accSelect([in] flagsSelect, [in] varChild) */
	int accSelect(int flagsSelect, int /*long*/ varChild) {
		// TODO: Are we going to support this?
		int code = COM.DISP_E_MEMBERNOTFOUND;
		if (iaccessible != null) {
			/* Currently, we don't expose this as API. Forward to the proxy. */
			code = iaccessible.accSelect(flagsSelect, varChild);
			if (code == COM.E_INVALIDARG) code = COM.DISP_E_MEMBERNOTFOUND; // proxy doesn't know about app childID
		}
		return code;
	}

	/* get_accChild([in] varChild, [out] ppdispChild)
	 * Ownership of ppdispChild transfers from callee to caller so reference count on ppdispChild 
	 * must be incremented before returning.  The caller is responsible for releasing ppdispChild.
	 */
	int get_accChild(int /*long*/ varChild, int /*long*/ ppdispChild) {
		VARIANT v = getVARIANT(varChild);
		if (v.vt != COM.VT_I4) return COM.E_INVALIDARG;
		int code = COM.S_FALSE;
		if (iaccessible != null) {
			/* Get the default child from the OS. */
			code = iaccessible.get_accChild(varChild, ppdispChild);
			if (code == COM.E_INVALIDARG) code = COM.S_FALSE; // proxy doesn't know about app childID
		}

		AccessibleControlEvent event = new AccessibleControlEvent(this);
		event.childID = osToChildID(v.lVal);
		for (int i = 0; i < accessibleControlListeners.size(); i++) {
			AccessibleControlListener listener = (AccessibleControlListener) accessibleControlListeners.elementAt(i);
			listener.getChild(event);
		}
		Accessible accessible = event.accessible;
		if (accessible != null) {
			accessible.AddRef();
			COM.MoveMemory(ppdispChild, new int /*long*/[] { accessible.objIAccessible.getAddress() }, OS.PTR_SIZEOF);
			return COM.S_OK;
		}
		return code;
	}
	
	/* get_accChildCount([out] pcountChildren) */
	int get_accChildCount(int /*long*/ pcountChildren) {
		int osChildCount = 0;
		if (iaccessible != null) {
			/* Get the default child count from the OS. */
			int code = iaccessible.get_accChildCount(pcountChildren);
			if (accessibleControlListeners.size() == 0) return code;
			if (code == COM.S_OK) {
				int[] pChildCount = new int[1];
				COM.MoveMemory(pChildCount, pcountChildren, 4);
				osChildCount = pChildCount[0];
			}
		}

		AccessibleControlEvent event = new AccessibleControlEvent(this);
		event.childID = ACC.CHILDID_SELF;
		event.detail = osChildCount;
		for (int i = 0; i < accessibleControlListeners.size(); i++) {
			AccessibleControlListener listener = (AccessibleControlListener) accessibleControlListeners.elementAt(i);
			listener.getChildCount(event);
		}

		COM.MoveMemory(pcountChildren, new int[] { event.detail }, 4);
		return COM.S_OK;
	}
	
	/* get_accDefaultAction([in] varChild, [out] pszDefaultAction) */
	int get_accDefaultAction(int /*long*/ varChild, int /*long*/ pszDefaultAction) {
		VARIANT v = getVARIANT(varChild);
		if (v.vt != COM.VT_I4) return COM.E_INVALIDARG;
		int code = COM.DISP_E_MEMBERNOTFOUND;
		String osDefaultAction = null;
		if (iaccessible != null) {
			/* Get the default defaultAction from the OS. */
			code = iaccessible.get_accDefaultAction(varChild, pszDefaultAction);
			if (code == COM.E_INVALIDARG) code = COM.S_FALSE; // proxy doesn't know about app childID
			if (accessibleControlListeners.size() == 0) return code;
			if (code == COM.S_OK) {
				int /*long*/[] pDefaultAction = new int /*long*/[1];
				COM.MoveMemory(pDefaultAction, pszDefaultAction, OS.PTR_SIZEOF);
				int size = COM.SysStringByteLen(pDefaultAction[0]);
				if (size > 0) {
					char[] buffer = new char[(size + 1) /2];
					COM.MoveMemory(buffer, pDefaultAction[0], size);
					osDefaultAction = new String(buffer);
				}
			}
		}

		AccessibleControlEvent event = new AccessibleControlEvent(this);
		event.childID = osToChildID(v.lVal);
		event.result = osDefaultAction;
		for (int i = 0; i < accessibleControlListeners.size(); i++) {
			AccessibleControlListener listener = (AccessibleControlListener) accessibleControlListeners.elementAt(i);
			listener.getDefaultAction(event);
		}
		if (event.result == null) return code;
		if (event.result.length() == 0) return COM.S_FALSE;
		setString(pszDefaultAction, event.result);
		return COM.S_OK;
	}
	
	/* get_accDescription([in] varChild, [out] pszDescription) */
	int get_accDescription(int /*long*/ varChild, int /*long*/ pszDescription) {
		/* 
		 * MSAA: "The accDescription property is not supported in the transition to
		 * UI Automation. MSAA servers and applications should not use it."
		 * 
		 * TODO: Description was exposed as SWT API. We will need to either deprecate this (?),
		 * or find a suitable replacement. Also, check description property on other platforms.
		 * Note that the trick to expose tree columns (below) was not supported by screen readers
		 * (verify W-E?), so it should be replaced.
		 */
		VARIANT v = getVARIANT(varChild);
		if (v.vt != COM.VT_I4) return COM.E_INVALIDARG;
		int code = COM.DISP_E_MEMBERNOTFOUND;
		String osDescription = null;
		if (iaccessible != null) {
			/* Get the default description from the OS. */
			code = iaccessible.get_accDescription(varChild, pszDescription);
			if (code == COM.E_INVALIDARG) code = COM.S_FALSE; // proxy doesn't know about app childID
			// TEMPORARY CODE - process tree even if there are no apps listening
			if (accessibleListeners.size() == 0 && !(control instanceof Tree)) return code;
			if (code == COM.S_OK) {
				int /*long*/[] pDescription = new int /*long*/[1];
				COM.MoveMemory(pDescription, pszDescription, OS.PTR_SIZEOF);
				int size = COM.SysStringByteLen(pDescription[0]);
				if (size > 0) {
					char[] buffer = new char[(size + 1) /2];
					COM.MoveMemory(buffer, pDescription[0], size);
					osDescription = new String(buffer);
				}
			}
		}
		
		AccessibleEvent event = new AccessibleEvent(this);
		event.childID = osToChildID(v.lVal);
		event.result = osDescription;
		
		// TEMPORARY CODE
		/* Currently our tree columns are emulated using custom draw,
		 * so we need to create the description using the tree column
		 * header text and tree item text. */
		if (v.lVal != COM.CHILDID_SELF) {
			if (control instanceof Tree) {
				Tree tree = (Tree) control;
				int columnCount = tree.getColumnCount ();
				if (columnCount > 1) {
					int /*long*/ hwnd = control.handle, hItem = 0;
					if (OS.COMCTL32_MAJOR >= 6) {
						hItem = OS.SendMessage (hwnd, OS.TVM_MAPACCIDTOHTREEITEM, v.lVal, 0);
					} else {
						hItem = v.lVal;
					}
					Widget widget = tree.getDisplay ().findWidget (hwnd, hItem);
					event.result = "";
					if (widget != null && widget instanceof TreeItem) {
						TreeItem item = (TreeItem) widget;
						for (int i = 1; i < columnCount; i++) {
							event.result += tree.getColumn(i).getText() + ": " + item.getText(i);
							if (i + 1 < columnCount) event.result += ", ";
						}
					}
				}
			}
		}
		for (int i = 0; i < accessibleListeners.size(); i++) {
			AccessibleListener listener = (AccessibleListener) accessibleListeners.elementAt(i);
			listener.getDescription(event);
		}
		if (event.result == null) return code;
		if (event.result.length() == 0) return COM.S_FALSE;
		setString(pszDescription, event.result);
		return COM.S_OK;
	}
	
	/* get_accFocus([out] pvarChild)
	 * Ownership of pvarChild transfers from callee to caller so reference count on pvarChild 
	 * must be incremented before returning.  The caller is responsible for releasing pvarChild.
	 */
	int get_accFocus(int /*long*/ pvarChild) {
		int osChild = ACC.CHILDID_NONE;
		if (iaccessible != null) {
			/* Get the default focus child from the OS. */
			int code = iaccessible.get_accFocus(pvarChild);
			if (accessibleControlListeners.size() == 0) return code;
			if (code == COM.S_OK) {
				VARIANT v = getVARIANT(pvarChild);
				if (v.vt == COM.VT_I4) osChild = osToChildID(v.lVal);
			}
		}

		AccessibleControlEvent event = new AccessibleControlEvent(this);
		event.childID = osChild;
		for (int i = 0; i < accessibleControlListeners.size(); i++) {
			AccessibleControlListener listener = (AccessibleControlListener) accessibleControlListeners.elementAt(i);
			listener.getFocus(event);
		}
		Accessible accessible = event.accessible;
		if (accessible != null) {
			accessible.AddRef();
			setPtrVARIANT(pvarChild, COM.VT_DISPATCH, accessible.objIAccessible.getAddress());
			return COM.S_OK;
		}
		int childID = event.childID;
		if (childID == ACC.CHILDID_NONE) {
			setVARIANT(pvarChild, COM.VT_EMPTY, 0);
			return COM.S_FALSE;
		}
		if (childID == ACC.CHILDID_SELF) {
			AddRef();
			setPtrVARIANT(pvarChild, COM.VT_DISPATCH, objIAccessible.getAddress());
			return COM.S_OK;
		}
		setVARIANT(pvarChild, COM.VT_I4, childIDToOs(childID));
		return COM.S_OK;
	}
	
	/* get_accHelp([in] varChild, [out] pszHelp) */
	int get_accHelp(int /*long*/ varChild, int /*long*/ pszHelp) {
		VARIANT v = getVARIANT(varChild);
		if (v.vt != COM.VT_I4) return COM.E_INVALIDARG;
		int code = COM.DISP_E_MEMBERNOTFOUND;
		String osHelp = null;
		if (iaccessible != null) {
			/* Get the default help string from the OS. */
			code = iaccessible.get_accHelp(varChild, pszHelp);
			if (code == COM.E_INVALIDARG) code = COM.S_FALSE; // proxy doesn't know about app childID
			if (accessibleListeners.size() == 0) return code;
			if (code == COM.S_OK) {
				int /*long*/[] pHelp = new int /*long*/[1];
				COM.MoveMemory(pHelp, pszHelp, OS.PTR_SIZEOF);
				int size = COM.SysStringByteLen(pHelp[0]);
				if (size > 0) {
					char[] buffer = new char[(size + 1) /2];
					COM.MoveMemory(buffer, pHelp[0], size);
					osHelp = new String(buffer);
				}
			}
		}

		AccessibleEvent event = new AccessibleEvent(this);
		event.childID = osToChildID(v.lVal);
		event.result = osHelp;
		for (int i = 0; i < accessibleListeners.size(); i++) {
			AccessibleListener listener = (AccessibleListener) accessibleListeners.elementAt(i);
			listener.getHelp(event);
		}
		if (event.result == null) return code;
		if (event.result.length() == 0) return COM.S_FALSE;
		setString(pszHelp, event.result);
		return COM.S_OK;
	}
	
	/* get_accHelpTopic([out] pszHelpFile, [in] varChild, [out] pidTopic) */
	int get_accHelpTopic(int /*long*/ pszHelpFile, int /*long*/ varChild, int /*long*/ pidTopic) {
		/* MSAA: "The accHelpTopic property is deprecated and should not be used." */
		int code = COM.DISP_E_MEMBERNOTFOUND;
		if (iaccessible != null) {
			/* Since it is possible that a native control might still handle get_accHelpTopic,
			 * we will continue to send this through to the proxy. */
			code = iaccessible.get_accHelpTopic(pszHelpFile, varChild, pidTopic);
			if (code == COM.E_INVALIDARG) code = COM.DISP_E_MEMBERNOTFOUND; // proxy doesn't know about app childID
		}
		return code;
	}

	/* get_accKeyboardShortcut([in] varChild, [out] pszKeyboardShortcut) */
	int get_accKeyboardShortcut(int /*long*/ varChild, int /*long*/ pszKeyboardShortcut) {
		VARIANT v = getVARIANT(varChild);
		if (v.vt != COM.VT_I4) return COM.E_INVALIDARG;
		int code = COM.DISP_E_MEMBERNOTFOUND;
		String osKeyboardShortcut = null;
		if (iaccessible != null) {
			/* Get the default keyboard shortcut from the OS. */
			code = iaccessible.get_accKeyboardShortcut(varChild, pszKeyboardShortcut);
			if (code == COM.E_INVALIDARG) code = COM.S_FALSE; // proxy doesn't know about app childID
			if (accessibleListeners.size() == 0) return code;
			if (code == COM.S_OK) {
				int /*long*/[] pKeyboardShortcut = new int /*long*/[1];
				COM.MoveMemory(pKeyboardShortcut, pszKeyboardShortcut, OS.PTR_SIZEOF);
				int size = COM.SysStringByteLen(pKeyboardShortcut[0]);
				if (size > 0) {
					char[] buffer = new char[(size + 1) /2];
					COM.MoveMemory(buffer, pKeyboardShortcut[0], size);
					osKeyboardShortcut = new String(buffer);
				}
			}
		}

		AccessibleEvent event = new AccessibleEvent(this);
		event.childID = osToChildID(v.lVal);
		event.result = osKeyboardShortcut;
		for (int i = 0; i < accessibleListeners.size(); i++) {
			AccessibleListener listener = (AccessibleListener) accessibleListeners.elementAt(i);
			listener.getKeyboardShortcut(event);
		}
		if (event.result == null) return code;
		if (event.result.length() == 0) return COM.S_FALSE;
		setString(pszKeyboardShortcut, event.result);
		return COM.S_OK;
	}
	
	/* get_accName([in] varChild, [out] pszName) */
	int get_accName(int /*long*/ varChild, int /*long*/ pszName) {
		VARIANT v = getVARIANT(varChild);
		if (v.vt != COM.VT_I4) return COM.E_INVALIDARG;
		int code = COM.S_FALSE;
		String osName = null;
		if (iaccessible != null) {
			/* Get the default name from the OS. */
			code = iaccessible.get_accName(varChild, pszName);
			if (code == COM.E_INVALIDARG) code = COM.S_FALSE; // proxy doesn't know about app childID
			if (accessibleListeners.size() == 0) return code;
			if (code == COM.S_OK) {
				int /*long*/[] pName = new int /*long*/[1];
				COM.MoveMemory(pName, pszName, OS.PTR_SIZEOF);
				int size = COM.SysStringByteLen(pName[0]);
				if (size > 0) {
					char[] buffer = new char[(size + 1) /2];
					COM.MoveMemory(buffer, pName[0], size);
					osName = new String(buffer);
				}
			}
		}

		AccessibleEvent event = new AccessibleEvent(this);
		event.childID = osToChildID(v.lVal);
		event.result = osName;
		for (int i = 0; i < accessibleListeners.size(); i++) {
			AccessibleListener listener = (AccessibleListener) accessibleListeners.elementAt(i);
			listener.getName(event);
		}
		if (event.result == null) return code;
		if (event.result.length() == 0) return COM.S_FALSE;
		setString(pszName, event.result);
		return COM.S_OK;
	}
	
	/* get_accParent([out] ppdispParent)
	 * Ownership of ppdispParent transfers from callee to caller so reference count on ppdispParent 
	 * must be incremented before returning.  The caller is responsible for releasing ppdispParent.
	 */
	int get_accParent(int /*long*/ ppdispParent) {
		// TODO: Need to support this now. Either return parent from proxy, or accessible's parent if no proxy.
		int code = COM.DISP_E_MEMBERNOTFOUND;
		if (iaccessible != null) {
			/* Currently, we don't expose this as API. Forward to the proxy. */
			code = iaccessible.get_accParent(ppdispParent);
		}
		return code;
	}
	
	/* get_accRole([in] varChild, [out] pvarRole) */
	int get_accRole(int /*long*/ varChild, int /*long*/ pvarRole) {
		VARIANT v = getVARIANT(varChild);
		if (v.vt != COM.VT_I4) return COM.E_INVALIDARG;
		int osRole = COM.ROLE_SYSTEM_CLIENT;
		if (iaccessible != null) {
			/* Get the default role from the OS. */
			int code = iaccessible.get_accRole(varChild, pvarRole);
			if (code == COM.S_OK) {
				VARIANT v2 = getVARIANT(pvarRole);
				if (v2.vt == COM.VT_I4) osRole = osToChildID(v2.lVal);
			}
		}

		AccessibleControlEvent event = new AccessibleControlEvent(this);
		event.childID = osToChildID(v.lVal);
		event.detail = osToRole(osRole);
		// TEMPORARY CODE
		/* Currently our checkbox table and tree are emulated using state mask images,
		 * so we need to specify 'checkbox' role for the items. */
		if (control instanceof Tree || control instanceof Table) {
			if (v.lVal != COM.CHILDID_SELF && (control.getStyle() & SWT.CHECK) != 0) event.detail = ACC.ROLE_CHECKBUTTON;
		}
		for (int i = 0; i < accessibleControlListeners.size(); i++) {
			AccessibleControlListener listener = (AccessibleControlListener) accessibleControlListeners.elementAt(i);
			listener.getRole(event);
		}
		setVARIANT(pvarRole, COM.VT_I4, roleToOs(event.detail));
		return COM.S_OK;
	}
	
	/* get_accSelection([out] pvarChildren)
	 * Ownership of pvarChildren transfers from callee to caller so reference count on pvarChildren 
	 * must be incremented before returning.  The caller is responsible for releasing pvarChildren.
	 */
	int get_accSelection(int /*long*/ pvarChildren) {
		int osChild = ACC.CHILDID_NONE;
		if (iaccessible != null) {
			/* Get the default selection from the OS. */
			int code = iaccessible.get_accSelection(pvarChildren);
			if (accessibleControlListeners.size() == 0) return code;
			if (code == COM.S_OK) {
				VARIANT v = getVARIANT(pvarChildren);
				if (v.vt == COM.VT_I4) {
					osChild = osToChildID(v.lVal);
				} else if (v.vt == COM.VT_UNKNOWN) {
					osChild = ACC.CHILDID_MULTIPLE;
					// TODO: Should get IEnumVARIANT from punkVal field, and enumerate children...
				}
				// TODO: Should also handle (v.vt == COM.VT_DISPATCH)
			}
		}

		AccessibleControlEvent event = new AccessibleControlEvent(this);
		event.childID = osChild;
		for (int i = 0; i < accessibleControlListeners.size(); i++) {
			AccessibleControlListener listener = (AccessibleControlListener) accessibleControlListeners.elementAt(i);
			listener.getSelection(event);
		}
		Accessible accessible = event.accessible;
		if (accessible != null) {
			accessible.AddRef();
			setPtrVARIANT(pvarChildren, COM.VT_DISPATCH, accessible.objIAccessible.getAddress());
			return COM.S_OK;
		}
		int childID = event.childID;
		if (childID == ACC.CHILDID_NONE) {
			setVARIANT(pvarChildren, COM.VT_EMPTY, 0);
			return COM.S_FALSE;
		}
		if (childID == ACC.CHILDID_MULTIPLE) {
			AddRef();
			setPtrVARIANT(pvarChildren, COM.VT_UNKNOWN, objIAccessible.getAddress());
			return COM.S_OK;
		}
		if (childID == ACC.CHILDID_SELF) {
			AddRef();
			setPtrVARIANT(pvarChildren, COM.VT_DISPATCH, objIAccessible.getAddress());
			return COM.S_OK;
		}
		setVARIANT(pvarChildren, COM.VT_I4, childIDToOs(childID));
		return COM.S_OK;
	}
	
	/* get_accState([in] varChild, [out] pvarState) */
	int get_accState(int /*long*/ varChild, int /*long*/ pvarState) {
		VARIANT v = getVARIANT(varChild);
		if (v.vt != COM.VT_I4) return COM.E_INVALIDARG;
		int osState = 0;
		if (iaccessible != null) {
			/* Get the default state from the OS. */
			int code = iaccessible.get_accState(varChild, pvarState);
			if (code == COM.S_OK) {
				VARIANT v2 = getVARIANT(pvarState);
				if (v2.vt == COM.VT_I4) osState = v2.lVal;
			}
		}
		
		boolean grayed = false;
		AccessibleControlEvent event = new AccessibleControlEvent(this);
		event.childID = osToChildID(v.lVal);
		event.detail = osToState(osState);
		// TEMPORARY CODE
		/* Currently our checkbox table and tree are emulated using state mask
		 * images, so we need to determine if the item state is 'checked'. */
		if (v.lVal != COM.CHILDID_SELF) {
			if (control instanceof Tree && (control.getStyle() & SWT.CHECK) != 0) {
				int /*long*/ hwnd = control.handle;
				TVITEM tvItem = new TVITEM ();
				tvItem.mask = OS.TVIF_HANDLE | OS.TVIF_STATE;
				tvItem.stateMask = OS.TVIS_STATEIMAGEMASK;
				if (OS.COMCTL32_MAJOR >= 6) {
					tvItem.hItem = OS.SendMessage (hwnd, OS.TVM_MAPACCIDTOHTREEITEM, v.lVal, 0);
				} else {
					tvItem.hItem = v.lVal;
				}
				int /*long*/ result = OS.SendMessage (hwnd, OS.TVM_GETITEM, 0, tvItem);
				boolean checked = (result != 0) && (((tvItem.state >> 12) & 1) == 0);
				if (checked) event.detail |= ACC.STATE_CHECKED;
				grayed = tvItem.state >> 12 > 2;
			} else if (control instanceof Table && (control.getStyle() & SWT.CHECK) != 0) {
				Table table = (Table) control;
				int index = event.childID;
				if (0 <= index && index < table.getItemCount()) {
					TableItem item = table.getItem(index);
					if (item.getChecked()) event.detail |= ACC.STATE_CHECKED;
					if (item.getGrayed()) grayed = true;
				}
			}
		}
		for (int i = 0; i < accessibleControlListeners.size(); i++) {
			AccessibleControlListener listener = (AccessibleControlListener) accessibleControlListeners.elementAt(i);
			listener.getState(event);
		}
		int state = stateToOs(event.detail);
		if ((state & ACC.STATE_CHECKED) != 0 && grayed) {
			state &= ~ COM.STATE_SYSTEM_CHECKED;
			state |= COM.STATE_SYSTEM_MIXED;
		}
		setVARIANT(pvarState, COM.VT_I4, state);
		return COM.S_OK;
	}
	
	/* get_accValue([in] varChild, [out] pszValue) */
	int get_accValue(int /*long*/ varChild, int /*long*/ pszValue) {
		VARIANT v = getVARIANT(varChild);
		if (v.vt != COM.VT_I4) return COM.E_INVALIDARG;
		int code = COM.DISP_E_MEMBERNOTFOUND;
		String osValue = null;
		if (iaccessible != null) {
			/* Get the default value string from the OS. */
			code = iaccessible.get_accValue(varChild, pszValue);
			if (code == COM.E_INVALIDARG) code = COM.DISP_E_MEMBERNOTFOUND; // proxy doesn't know about app childID
			if (accessibleControlListeners.size() == 0) return code;
			if (code == COM.S_OK) {
				int /*long*/[] pValue = new int /*long*/[1];
				COM.MoveMemory(pValue, pszValue, OS.PTR_SIZEOF);
				int size = COM.SysStringByteLen(pValue[0]);
				if (size > 0) {
					char[] buffer = new char[(size + 1) /2];
					COM.MoveMemory(buffer, pValue[0], size);
					osValue = new String(buffer);
				}
			}
		}

		AccessibleControlEvent event = new AccessibleControlEvent(this);
		event.childID = osToChildID(v.lVal);
		event.result = osValue;
		for (int i = 0; i < accessibleControlListeners.size(); i++) {
			AccessibleControlListener listener = (AccessibleControlListener) accessibleControlListeners.elementAt(i);
			listener.getValue(event);
		}
		if (event.result == null) return code;
		// empty string is a valid value, so do not test for it
		setString(pszValue, event.result);
		return COM.S_OK;
	}
	
	/* put_accName([in] varChild, [in] szName) */
	int put_accName(int /*long*/ varChild, int /*long*/ szName) {
		/* MSAA: "The IAccessible::put_accName method is no longer supported. Servers should return E_NOTIMPL." */
		return COM.E_NOTIMPL;
	}
	
	/* put_accValue([in] varChild, [in] szValue) */
	int put_accValue(int /*long*/ varChild, int /*long*/ szValue) {
		// TODO: Are we going to support this?
		/* MSAA: this method is typically only used for edit controls. */
		int code = COM.DISP_E_MEMBERNOTFOUND;
		if (iaccessible != null) {
			/* Currently, we don't expose this as API. Forward to the proxy. */
			code = iaccessible.put_accValue(varChild, szValue);
			if (code == COM.E_INVALIDARG) code = COM.DISP_E_MEMBERNOTFOUND; // proxy doesn't know about app childID
		}
		return code;
	}

	/* IEnumVARIANT methods: Next, Skip, Reset, Clone */
	/* Retrieve the next celt items in the enumeration sequence. 
	 * If there are fewer than the requested number of elements left
	 * in the sequence, retrieve the remaining elements.
	 * The number of elements actually retrieved is returned in pceltFetched 
	 * (unless the caller passed in NULL for that parameter).
	 */
	 
	/* Next([in] celt, [out] rgvar, [in, out] pceltFetched)
	 * Ownership of rgvar transfers from callee to caller so reference count on rgvar 
	 * must be incremented before returning.  The caller is responsible for releasing rgvar.
	 */
	int Next(int celt, int /*long*/ rgvar, int /*long*/ pceltFetched) {
		/* If there are no listeners, query the proxy for
		 * its IEnumVariant, and get the Next items from it.
		 */
		if (iaccessible != null && accessibleControlListeners.size() == 0) {
			int /*long*/[] ppvObject = new int /*long*/[1];
			int code = iaccessible.QueryInterface(COM.IIDIEnumVARIANT, ppvObject);
			if (code != COM.S_OK) return code;
			IEnumVARIANT ienumvariant = new IEnumVARIANT(ppvObject[0]);
			int[] celtFetched = new int[1];
			code = ienumvariant.Next(celt, rgvar, celtFetched);
			ienumvariant.Release();
			COM.MoveMemory(pceltFetched, celtFetched, 4);
			return code;
		}

		if (rgvar == 0) return COM.E_INVALIDARG;
		if (pceltFetched == 0 && celt != 1) return COM.E_INVALIDARG;
		if (enumIndex == 0) {
			AccessibleControlEvent event = new AccessibleControlEvent(this);
			event.childID = ACC.CHILDID_SELF;
			for (int i = 0; i < accessibleControlListeners.size(); i++) {
				AccessibleControlListener listener = (AccessibleControlListener) accessibleControlListeners.elementAt(i);
				listener.getChildren(event);
			}
			variants = event.children;
		}	
		Object[] nextItems = null;
		if (variants != null && celt >= 1) {
			int endIndex = enumIndex + celt - 1;
			if (endIndex > (variants.length - 1)) endIndex = variants.length - 1;
			if (enumIndex <= endIndex) {
				nextItems = new Object[endIndex - enumIndex + 1];
				for (int i = 0; i < nextItems.length; i++) {
					Object child = variants[enumIndex];
					if (child instanceof Integer) {
						nextItems[i] = new Integer(childIDToOs(((Integer)child).intValue()));
					} else {
						nextItems[i] = child;
					}
					enumIndex++;
				}
			}
		}
		if (nextItems != null) {
			for (int i = 0; i < nextItems.length; i++) {
				Object nextItem = nextItems[i];
				if (nextItem instanceof Integer) {
					int item = ((Integer) nextItem).intValue();
					setVARIANT(rgvar + i * VARIANT.sizeof, COM.VT_I4, item);
				} else {
					Accessible accessible = (Accessible) nextItem;
					accessible.AddRef();
					setPtrVARIANT(rgvar + i * VARIANT.sizeof, COM.VT_DISPATCH, accessible.objIAccessible.getAddress());
				}
			}
			if (pceltFetched != 0)
				COM.MoveMemory(pceltFetched, new int[] {nextItems.length}, 4);
			if (nextItems.length == celt) return COM.S_OK;
		} else {
			if (pceltFetched != 0)
				COM.MoveMemory(pceltFetched, new int[] {0}, 4);
		}
		return COM.S_FALSE;
	}
	
	/* Skip over the specified number of elements in the enumeration sequence. */
	int Skip(int celt) {
		/* If there are no listeners, query the proxy
		 * for its IEnumVariant, and tell it to Skip.
		 */
		if (iaccessible != null && accessibleControlListeners.size() == 0) {
			int /*long*/[] ppvObject = new int /*long*/[1];
			int code = iaccessible.QueryInterface(COM.IIDIEnumVARIANT, ppvObject);
			if (code != COM.S_OK) return code;
			IEnumVARIANT ienumvariant = new IEnumVARIANT(ppvObject[0]);
			code = ienumvariant.Skip(celt);
			ienumvariant.Release();
			return code;
		}

		if (celt < 1 ) return COM.E_INVALIDARG;
		enumIndex += celt;
		if (enumIndex > (variants.length - 1)) {
			enumIndex = variants.length - 1;
			return COM.S_FALSE;
		}
		return COM.S_OK;
	}
	
	/* Reset the enumeration sequence to the beginning. */
	int Reset() {
		/* If there are no listeners, query the proxy
		 * for its IEnumVariant, and tell it to Reset.
		 */
		if (iaccessible != null && accessibleControlListeners.size() == 0) {
			int /*long*/[] ppvObject = new int /*long*/[1];
			int code = (int)/*64*/iaccessible.QueryInterface(COM.IIDIEnumVARIANT, ppvObject);
			if (code != COM.S_OK) return code;
			IEnumVARIANT ienumvariant = new IEnumVARIANT(ppvObject[0]);
			code = ienumvariant.Reset();
			ienumvariant.Release();
			return code;
		}
		
		enumIndex = 0;
		return COM.S_OK;
	}

	/* Clone([out] ppEnum)
	 * Ownership of ppEnum transfers from callee to caller so reference count on ppEnum 
	 * must be incremented before returning.  The caller is responsible for releasing ppEnum.
	 */
	int Clone(int /*long*/ ppEnum) {
		/* If there are no listeners, query the proxy for
		 * its IEnumVariant, and get the Clone from it.
		 */
		if (iaccessible != null && accessibleControlListeners.size() == 0) {
			int /*long*/[] ppvObject = new int /*long*/[1];
			int code = iaccessible.QueryInterface(COM.IIDIEnumVARIANT, ppvObject);
			if (code != COM.S_OK) return code;
			IEnumVARIANT ienumvariant = new IEnumVARIANT(ppvObject[0]);
			int /*long*/ [] pEnum = new int /*long*/ [1];
			code = ienumvariant.Clone(pEnum);
			ienumvariant.Release();
			COM.MoveMemory(ppEnum, pEnum, OS.PTR_SIZEOF);
			return code;
		}

		if (ppEnum == 0) return COM.E_INVALIDARG;
		COM.MoveMemory(ppEnum, new int /*long*/[] { objIEnumVARIANT.getAddress() }, OS.PTR_SIZEOF);
		AddRef();
		return COM.S_OK;
	}
	
	/* get_nRelations([out] pNRelations) */
	int get_nRelations(int /*long*/ pNRelations) {
		// TODO: return relations.length
		//COM.MoveMemory(pNRelations, new int [] { relationCount }, 4);
		return COM.S_OK;
	}

	/* get_relation([in] relationIndex, [out] ppRelation) */
	int get_relation(int relationIndex, int /*long*/ ppRelation) {
		// TODO: return relations@index????? Note: this may be deprecated some day
//		Accessible accessible = relation;
//		if (accessible != null) {
//			accessible.AddRef();
//			setPtrVARIANT(ppRelation, COM.VT_DISPATCH, accessible.objIAccessible.getAddress());
//		}
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed, [out] value is NULL
	}

	/* get_relations([in] maxRelations, [out] ppRelations, [out] pNRelations) */
	int get_relations(int maxRelations, int /*long*/ ppRelations, int /*long*/ pNRelations) {
		// TODO: return relations - NOTE: Relations need to be [private] accessible objects...
//		Accessible accessible = relations;
//		if (accessible != null) {
//			accessible.AddRef();
//			setPtrVARIANT(ppRelations, COM.VT_DISPATCH, accessible.objIAccessible.getAddress());
//		}
//		COM.MoveMemory(pNRelations, new int [] { event.relationCount }, 4);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there are no relations, nRelations is set to 0
	}

	/* get_role([out] pRole) */
	int get_role(int /*long*/ pRole) {
		AccessibleControlEvent event = new AccessibleControlEvent(this);
		event.childID = ACC.CHILDID_SELF;
		for (int i = 0; i < accessibleControlListeners.size(); i++) {
			AccessibleControlListener listener = (AccessibleControlListener) accessibleControlListeners.elementAt(i);
			listener.getRole(event);
		}
		COM.MoveMemory(pRole, new int [] { event.detail }, 4);
		return COM.S_OK;
	}

	/* scrollTo([in] scrollType) */
	int scrollTo(int scrollType) {
		AccessibleScrollEvent event = new AccessibleScrollEvent(this);
		event.type = scrollType;
		for (int i = 0; i < accessibleScrollListeners.size(); i++) {
			AccessibleScrollListener listener = (AccessibleScrollListener) accessibleScrollListeners.elementAt(i);
			listener.scrollTo(event);
		}
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* scrollToPoint([in] coordinateType, [in] x, [in] y) */
	int scrollToPoint(int coordinateType, int x, int y) {
		AccessibleScrollEvent event = new AccessibleScrollEvent(this);
		event.x = x;
		event.y = y;
		for (int i = 0; i < accessibleScrollListeners.size(); i++) {
			AccessibleScrollListener listener = (AccessibleScrollListener) accessibleScrollListeners.elementAt(i);
			listener.scrollToPoint(event);
		}
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* get_groupPosition([out] pGroupLevel, [out] pSimilarItemsInGroup, [out] pPositionInGroup) */
	int get_groupPosition(int /*long*/ pGroupLevel, int /*long*/ pSimilarItemsInGroup, int /*long*/ pPositionInGroup) {
		// TODO: handle where possible - maybe add AccessibleGroup later
//		COM.MoveMemory(pGroupLevel, new int [] { groupLevel }, 4);
//		COM.MoveMemory(pSimilarItemsInGroup, new int [] { similarItemsInGroup }, 4);
//		COM.MoveMemory(pPositionInGroup, new int [] { positionInGroup }, 4);
		return COM.S_OK;
		// TODO: @retval S_OK if at least one value is valid@retval S_FALSE if no values are valid
	}

	/* get_states([out] pStates) */
	int get_states(int /*long*/ pStates) {
		AccessibleControlEvent event = new AccessibleControlEvent(this);
		event.childID = ACC.CHILDID_SELF;
		for (int i = 0; i < accessibleControlListeners.size(); i++) {
			AccessibleControlListener listener = (AccessibleControlListener) accessibleControlListeners.elementAt(i);
			listener.getState(event);
		}
		COM.MoveMemory(pStates, new int [] { event.detail }, 4);
		return COM.S_OK;
	}

	/* get_extendedRole([out] pbstrExtendedRole) */
	int get_extendedRole(int /*long*/ pbstrExtendedRole) {
		// TODO: this feature is not supported.
		setString(pbstrExtendedRole, null);
		return COM.DISP_E_MEMBERNOTFOUND;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL
	}

	/* get_localizedExtendedRole([out] pbstrLocalizedExtendedRole) */
	int get_localizedExtendedRole(int /*long*/ pbstrLocalizedExtendedRole) {
		// TODO: this feature is not supported.
		setString(pbstrLocalizedExtendedRole, null);
		return COM.DISP_E_MEMBERNOTFOUND;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL
	}

	/* get_nExtendedStates([out] pNExtendedStates) */
	int get_nExtendedStates(int /*long*/ pNExtendedStates) {
		// TODO: this feature is not supported.
		COM.MoveMemory(pNExtendedStates, new int [] { 0 }, 4);
		return COM.DISP_E_MEMBERNOTFOUND;
	}

	/* get_extendedStates([in] maxExtendedStates, [out] ppbstrExtendedStates, [out] pNExtendedStates) */
	int get_extendedStates(int maxExtendedStates, int /*long*/ ppbstrExtendedStates, int /*long*/ pNExtendedStates) {
		// TODO: this feature is not supported.
		setString(ppbstrExtendedStates, null);
		COM.MoveMemory(pNExtendedStates, new int [] { 0 }, 4);
		return COM.DISP_E_MEMBERNOTFOUND;
		// TODO: @retval S_FALSE if there are no states, [out] values are NULL and 0 respectively
	}

	/* get_localizedExtendedStates([in] maxLocalizedExtendedStates, [out] ppbstrLocalizedExtendedStates, [out] pNLocalizedExtendedStates) */
	int get_localizedExtendedStates(int maxLocalizedExtendedStates, int /*long*/ ppbstrLocalizedExtendedStates, int /*long*/ pNLocalizedExtendedStates) {
		// TODO: this feature is not supported.
		setString(ppbstrLocalizedExtendedStates, null);
		COM.MoveMemory(pNLocalizedExtendedStates, new int [] { 0 }, 4);
		return COM.DISP_E_MEMBERNOTFOUND;
		// TODO: @retval S_FALSE if there are no states, [out] values are NULL and 0 respectively
	}

	/* get_uniqueID([out] pUniqueID) */
	int get_uniqueID(int /*long*/ pUniqueID) {
		// TODO: just return a hash on the Accessible or something
		//COM.MoveMemory(pUniqueID, new int [] { uniqueID }, 4);
		return COM.S_OK;
	}

	/* get_windowHandle([out] pWindowHandle) */
	int get_windowHandle(int /*long*/ pWindowHandle) {
		// TODO: platform-specific - return HWND on Windows
		//COM.MoveMemory(pWindowHandle, new int /*long*/ [] { windowHandle }, OS.PTR_SIZEOF);
		return COM.S_OK;
	}

	/* get_indexInParent([out] pIndexInParent) */
	int get_indexInParent(int /*long*/ pIndexInParent) {
		// TODO: look up index in AccessibleControl.getChildren
		AccessibleControlEvent event = new AccessibleControlEvent(this);
		event.childID = ACC.CHILDID_SELF;
		for (int i = 0; i < accessibleControlListeners.size(); i++) {
			AccessibleControlListener listener = (AccessibleControlListener) accessibleControlListeners.elementAt(i);
			listener.getChildren(event);
		}
		Object [] children = event.children;
		int indexInParent = 0;
		// TODO: look up index in AccessibleControl.getChildren
		COM.MoveMemory(pIndexInParent, new int [] { indexInParent }, 4);
		return COM.S_OK;
		// TODO: @retval S_FALSE if no parent, [out] value is -1
	}

	/* get_locale([out] pLocale) */
	int get_locale(int /*long*/ pLocale) {
		// TODO: just return current locale - maybe add later in AccessibleLocale
		// Note: need to return an IA2Locale struct: String language, String country, String variant
		//COM.MoveMemory(pLocale, new int [] { locale }, 4);
		return COM.S_OK;
	}

	/* get_attributes([out] pbstrAttributes) */
	int get_attributes(int /*long*/ pbstrAttributes) {
		AccessibleAttributeEvent event = new AccessibleAttributeEvent(this);
		for (int i = 0; i < accessibleAttributeListeners.size(); i++) {
			AccessibleAttributeListener listener = (AccessibleAttributeListener) accessibleAttributeListeners.elementAt(i);
			listener.getAttributes(event);
		}
		String attributes = "";
		// TODO: Create an attributes string from the event data
		if (attributes.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		setString(pbstrAttributes, attributes);
		return COM.S_OK;
		// TODO: @retval S_FALSE returned if there is nothing to return, [out] value is NULL
	}

	/* get_nActions([out] pNActions) */
	int get_nActions(int /*long*/ pNActions) {
		AccessibleActionEvent event = new AccessibleActionEvent(this);
		for (int i = 0; i < accessibleActionListeners.size(); i++) {
			AccessibleActionListener listener = (AccessibleActionListener) accessibleActionListeners.elementAt(i);
			listener.getActionCount(event);
		}
		COM.MoveMemory(pNActions, new int [] { event.count }, 4);
		return COM.S_OK;
	}

	/* doAction([in] actionIndex) */
	int doAction(int actionIndex) {
		AccessibleActionEvent event = new AccessibleActionEvent(this);
		event.index = actionIndex;
		for (int i = 0; i < accessibleActionListeners.size(); i++) {
			AccessibleActionListener listener = (AccessibleActionListener) accessibleActionListeners.elementAt(i);
			listener.doAction(event);
		}
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* get_description([in] actionIndex, [out] pbstrDescription) */
	int get_description(int actionIndex, int /*long*/ pbstrDescription) {
		AccessibleActionEvent event = new AccessibleActionEvent(this);
		event.index = actionIndex;
		for (int i = 0; i < accessibleActionListeners.size(); i++) {
			AccessibleActionListener listener = (AccessibleActionListener) accessibleActionListeners.elementAt(i);
			listener.getDescription(event);
		}
		if (event.string.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		setString(pbstrDescription, event.string);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL@retval E_INVALIDARG if bad [in] passed, [out] value is NULL
	}

	/* get_keyBinding([in] actionIndex, [in] nMaxBindings, [out] ppbstrKeyBindings, [out] pNBindings) */
	int get_keyBinding(int actionIndex, int nMaxBindings, int /*long*/ ppbstrKeyBindings, int /*long*/ pNBindings) {
		AccessibleActionEvent event = new AccessibleActionEvent(this);
		event.index = actionIndex;
		for (int i = 0; i < accessibleActionListeners.size(); i++) {
			AccessibleActionListener listener = (AccessibleActionListener) accessibleActionListeners.elementAt(i);
			listener.getKeyBinding(event);
		}
		if (event.keyBindings.length == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		// TODO: Need to return all of the bindings, not just the first
		setString(ppbstrKeyBindings, event.keyBindings[0]);
		COM.MoveMemory(pNBindings, new int [] { event.count }, 4);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there are no relations, [out] values are NULL and 0 respectively@retval E_INVALIDARG if bad [in] passed, [out] values are NULL and 0 respectively
	}

	/* get_name([in] actionIndex, [out] pbstrName) */
	int get_name(int actionIndex, int /*long*/ pbstrName) {
		AccessibleActionEvent event = new AccessibleActionEvent(this);
		event.index = actionIndex;
		for (int i = 0; i < accessibleActionListeners.size(); i++) {
			AccessibleActionListener listener = (AccessibleActionListener) accessibleActionListeners.elementAt(i);
			listener.getName(event);
		}
		if (event.string.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		setString(pbstrName, event.string);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL@retval E_INVALIDARG if bad [in] passed, [out] value is NULL
	}

	/* get_localizedName([in] actionIndex, [out] pbstrLocalizedName) */
	int get_localizedName(int actionIndex, int /*long*/ pbstrLocalizedName) {
		AccessibleActionEvent event = new AccessibleActionEvent(this);
		event.index = actionIndex;
		for (int i = 0; i < accessibleActionListeners.size(); i++) {
			AccessibleActionListener listener = (AccessibleActionListener) accessibleActionListeners.elementAt(i);
			listener.getLocalizedName(event);
		}
		if (event.string.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		setString(pbstrLocalizedName, event.string);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL@retval E_INVALIDARG if bad [in] passed, [out] value is NULL
	}

	/* get_appName([out] pbstrName) */
	int get_appName(int /*long*/ pbstrName) {
		// TODO: use Display.getAppName (version?) new API in Display
		String appName = "";
		if (appName.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		setString(pbstrName, appName);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL
	}

	/* get_appVersion([out] pbstrVersion) */
	int get_appVersion(int /*long*/ pbstrVersion) {
		// TODO: use Display.getAppName (version?) new API in Display
		String appVersion = "";
		if (appVersion.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		setString(pbstrVersion, appVersion);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL
	}

	/* get_toolkitName([out] pbstrName) */
	int get_toolkitName(int /*long*/ pbstrName) {
		// TODO: use SWT for toolkit name and version
		String toolkitName = "SWT";
		if (toolkitName.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		setString(pbstrName, toolkitName);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL
	}

	/* get_toolkitVersion([out] pbstrVersion) */
	int get_toolkitVersion(int /*long*/ pbstrVersion) {
		// TODO: use SWT for toolkit name and version
		String toolkitVersion = "" + SWT.getVersion();
		if (toolkitVersion.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		setString(pbstrVersion, toolkitVersion);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL
	}

	/* get_locationInParent([out] pX, [out] pY) */
	int get_locationInParent(int /*long*/ pX, int /*long*/ pY) {
		// TODO: support transparently (hard for fake parents - screen vs. parent coords)
		AccessibleControlEvent event = new AccessibleControlEvent(this);
		for (int i = 0; i < accessibleControlListeners.size(); i++) {
			AccessibleControlListener listener = (AccessibleControlListener) accessibleControlListeners.elementAt(i);
			listener.getLocation (event);
		}
		COM.MoveMemory(pX, new int [] { event.x }, 4);
		COM.MoveMemory(pY, new int [] { event.y }, 4);
		return COM.S_OK;
	}

	/* get_foreground([out] pForeground) */
	int get_foreground(int /*long*/ pForeground) {
		// TODO: Do not support foreground for children (support transparently for controls)
		//COM.MoveMemory(pForeground, new int [] { control.getForegroundPixel() }, 4);
		return COM.S_OK;
	}

	/* get_background([out] pBackground) */
	int get_background(int /*long*/ pBackground) {
		// TODO: Do not support background for children (support transparently for controls)
		//COM.MoveMemory(pBackground, new int [] { control.getBackgroundPixel() }, 4);
		return COM.S_OK;
	}

	/* copyText([in] startOffset, [in] endOffset) */
	int copyText(int startOffset, int endOffset) {
		// TODO: Do not provide at this time
		return COM.DISP_E_MEMBERNOTFOUND;
//		AccessibleEditableTextEvent event = new AccessibleEditableTextEvent(this);
//		event.start = startOffset;
//		event.end = endOffset;
//		for (int i = 0; i < accessibleEditableTextListeners.size(); i++) {
//			AccessibleEditableTextListener listener = (AccessibleEditableTextListener) accessibleEditableTextListeners.elementAt(i);
//			listener.copyText(event);
//		}
//		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* deleteText([in] startOffset, [in] endOffset) */
	int deleteText(int startOffset, int endOffset) {
		// TODO: Do not provide at this time
		return COM.DISP_E_MEMBERNOTFOUND;
//		AccessibleEditableTextEvent event = new AccessibleEditableTextEvent(this);
//		event.start = startOffset;
//		event.end = endOffset;
//		for (int i = 0; i < accessibleEditableTextListeners.size(); i++) {
//			AccessibleEditableTextListener listener = (AccessibleEditableTextListener) accessibleEditableTextListeners.elementAt(i);
//			listener.deleteText(event);
//		}
//		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* insertText([in] offset, [in] pbstrText) */
	int insertText(int offset, int /*long*/ pbstrText) {
		// TODO: Do not provide at this time
		return COM.DISP_E_MEMBERNOTFOUND;
//		AccessibleEditableTextEvent event = new AccessibleEditableTextEvent(this);
//		event.index = offset;
//		event.string = pbstrText;
//		for (int i = 0; i < accessibleEditableTextListeners.size(); i++) {
//			AccessibleEditableTextListener listener = (AccessibleEditableTextListener) accessibleEditableTextListeners.elementAt(i);
//			listener.insertText(event);
//		}
//		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* cutText([in] startOffset, [in] endOffset) */
	int cutText(int startOffset, int endOffset) {
		// TODO: Do not provide at this time
		return COM.DISP_E_MEMBERNOTFOUND;
//		AccessibleEditableTextEvent event = new AccessibleEditableTextEvent(this);
//		event.start = startOffset;
//		event.end = endOffset;
//		for (int i = 0; i < accessibleEditableTextListeners.size(); i++) {
//			AccessibleEditableTextListener listener = (AccessibleEditableTextListener) accessibleEditableTextListeners.elementAt(i);
//			listener.cutText(event);
//		}
//		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* pasteText([in] offset) */
	int pasteText(int offset) {
		// TODO: Do not provide at this time
		return COM.DISP_E_MEMBERNOTFOUND;
//		AccessibleEditableTextEvent event = new AccessibleEditableTextEvent(this);
//		event.index = offset;
//		for (int i = 0; i < accessibleEditableTextListeners.size(); i++) {
//			AccessibleEditableTextListener listener = (AccessibleEditableTextListener) accessibleEditableTextListeners.elementAt(i);
//			listener.pasteText(event);
//		}
//		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* replaceText([in] startOffset, [in] endOffset, [in] pbstrText) */
	int replaceText(int startOffset, int endOffset, int /*long*/ pbstrText) {
		// TODO: Do not provide at this time
		return COM.DISP_E_MEMBERNOTFOUND;
//		AccessibleEditableTextEvent event = new AccessibleEditableTextEvent(this);
//		event.start = startOffset;
//		event.end = endOffset;
//		event.string = pbstrText;
//		for (int i = 0; i < accessibleEditableTextListeners.size(); i++) {
//			AccessibleEditableTextListener listener = (AccessibleEditableTextListener) accessibleEditableTextListeners.elementAt(i);
//			listener.replaceText(event);
//		}
//		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* setAttributes([in] startOffset, [in] endOffset, [in] pbstrAttributes) */
	int setAttributes(int startOffset, int endOffset, int /*long*/ pbstrAttributes) {
		// TODO: Do not provide at this time
		return COM.DISP_E_MEMBERNOTFOUND;
//		AccessibleEditableTextEvent event = new AccessibleEditableTextEvent(this);
//		event.start = startOffset;
//		event.end = endOffset;
//		event.attributes = pbstrAttributes;
//		for (int i = 0; i < accessibleEditableTextListeners.size(); i++) {
//			AccessibleEditableTextListener listener = (AccessibleEditableTextListener) accessibleEditableTextListeners.elementAt(i);
//			listener.setAttributes(event);
//		}
//		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* get_anchor([in] index, [out] pAnchor) */
	int get_anchor(int index, int /*long*/ pAnchor) {
		AccessibleHyperlinkEvent event = new AccessibleHyperlinkEvent(this);
		event.index = index;
		for (int i = 0; i < accessibleHyperlinkListeners.size(); i++) {
			AccessibleHyperlinkListener listener = (AccessibleHyperlinkListener) accessibleHyperlinkListeners.elementAt(i);
			listener.getAnchor(event);
		}
		if (event.string.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		// TODO: pAnchor is a VARIANT that can be either a bstr (event.string) or a dispatch (event.accessible)
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL@retval E_INVALIDARG if bad [in] passed, [out] value is NULL
	}

	/* get_anchorTarget([in] index, [out] pAnchorTarget) */
	int get_anchorTarget(int index, int /*long*/ pAnchorTarget) {
		AccessibleHyperlinkEvent event = new AccessibleHyperlinkEvent(this);
		event.index = index;
		for (int i = 0; i < accessibleHyperlinkListeners.size(); i++) {
			AccessibleHyperlinkListener listener = (AccessibleHyperlinkListener) accessibleHyperlinkListeners.elementAt(i);
			listener.getAnchorTarget(event);
		}
		// TODO: pAnchorTarget is a VARIANT that can be either a bstr (event.string) or a dispatch (event.accessible)
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL@retval E_INVALIDARG if bad [in] passed, [out] value is NULL
	}

	/* get_startIndex([out] pIndex) */
	int get_startIndex(int /*long*/ pIndex) {
		AccessibleHyperlinkEvent event = new AccessibleHyperlinkEvent(this);
		for (int i = 0; i < accessibleHyperlinkListeners.size(); i++) {
			AccessibleHyperlinkListener listener = (AccessibleHyperlinkListener) accessibleHyperlinkListeners.elementAt(i);
			listener.getStartIndex(event);
		}
		COM.MoveMemory(pIndex, new int [] { event.index }, 4);
		return COM.S_OK;
	}

	/* get_endIndex([out] pIndex) */
	int get_endIndex(int /*long*/ pIndex) {
		AccessibleHyperlinkEvent event = new AccessibleHyperlinkEvent(this);
		for (int i = 0; i < accessibleHyperlinkListeners.size(); i++) {
			AccessibleHyperlinkListener listener = (AccessibleHyperlinkListener) accessibleHyperlinkListeners.elementAt(i);
			listener.getEndIndex(event);
		}
		COM.MoveMemory(pIndex, new int [] { event.index }, 4);
		return COM.S_OK;
	}

	/* get_valid([out] pValid) */
	int get_valid(int /*long*/ pValid) {
		// TODO: deprecated
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is FALSE
	}

	/* get_nHyperlinks([out] pHyperlinkCount) */
	int get_nHyperlinks(int /*long*/ pHyperlinkCount) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.getHyperlinkCount(event);
		}
		COM.MoveMemory(pHyperlinkCount, new int [] { event.count }, 4);
		return COM.S_OK;
	}

	/* get_hyperlink([in] index, [out] ppHyperlink) */
	int get_hyperlink(int index, int /*long*/ ppHyperlink) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		event.index = index;
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.getHyperlink(event);
		}
		Accessible accessible = event.accessible;
		if (accessible != null) {
			accessible.AddRef();
			setPtrVARIANT(ppHyperlink, COM.VT_DISPATCH, accessible.objIAccessible.getAddress());
		}
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed, [out] value is NULL
	}

	/* get_hyperlinkIndex([in] charIndex, [out] pHyperlinkIndex) */
	int get_hyperlinkIndex(int charIndex, int /*long*/ pHyperlinkIndex) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		event.index = charIndex;
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.getHyperlinkIndex(event);
		}
		COM.MoveMemory(pHyperlinkIndex, new int [] { event.index }, 4);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is -1@retval E_INVALIDARG if bad [in] passed, [out] value is NULL
	}

	/* get_description([out] pbstrDescription) */
	int get_description(int /*long*/ pbstrDescription) {
		// TODO: Does it make sense to just reuse description?
		AccessibleEvent event = new AccessibleEvent(this);
		for (int i = 0; i < accessibleListeners.size(); i++) {
			AccessibleListener listener = (AccessibleListener) accessibleListeners.elementAt(i);
			listener.getDescription(event);
		}
		if (event.result.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		setString(pbstrDescription, event.result);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL
	}

	/* get_imagePosition([in] coordinateType, [out] pX, [out] pY) */
	int get_imagePosition(int coordinateType, int /*long*/ pX, int /*long*/ pY) {
		// TODO: does it make sense to just reuse getLocation?
		AccessibleControlEvent event = new AccessibleControlEvent(this);
		for (int i = 0; i < accessibleControlListeners.size(); i++) {
			AccessibleControlListener listener = (AccessibleControlListener) accessibleControlListeners.elementAt(i);
			listener.getLocation(event);
		}
		// TODO: make sure to return the correct coordinateType
		COM.MoveMemory(pX, new int [] { event.x }, 4);
		COM.MoveMemory(pY, new int [] { event.y }, 4);
		return COM.S_OK;
	}

	/* get_imageSize([out] pHeight, [out] pWidth) */
	int get_imageSize(int /*long*/ pHeight, int /*long*/ pWidth) {
		AccessibleControlEvent event = new AccessibleControlEvent(this);
		for (int i = 0; i < accessibleControlListeners.size(); i++) {
			AccessibleControlListener listener = (AccessibleControlListener) accessibleControlListeners.elementAt(i);
			listener.getLocation(event);
		}
		COM.MoveMemory(pHeight, new int [] { event.height }, 4);
		COM.MoveMemory(pWidth, new int [] { event.width }, 4);
		return COM.S_OK;
	}

	/* get_relationType([out] pbstrRelationType) */
	int get_relationType(int /*long*/ pbstrRelationType) {
		// TODO: Fix this
//		AccessibleRelationEvent event = new AccessibleRelationEvent(this);
//		for (int i = 0; i < accessibleRelationListeners.size(); i++) {
//			AccessibleRelationListener listener = (AccessibleRelationListener) accessibleRelationListeners.elementAt(i);
//			listener.getRelationType(event);
//		}
//		if (event.type.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
//		setString(pbstrRelationType, event.type);
		return COM.S_OK;
	}

	/* get_localizedRelationType([out] pbstrLocalizedRelationType) */
	int get_localizedRelationType(int /*long*/ pbstrLocalizedRelationType) {
		// TODO: Fix this
//		AccessibleRelationEvent event = new AccessibleRelationEvent(this);
//		for (int i = 0; i < accessibleRelationListeners.size(); i++) {
//			AccessibleRelationListener listener = (AccessibleRelationListener) accessibleRelationListeners.elementAt(i);
//			listener.getLocalizedRelationType(event);
//		}
//		if (event.type == null) return code;
//		if (event.type.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
//		setString(pbstrLocalizedRelationType, event.type);
		return COM.S_OK;
	}

	/* get_nTargets([out] pNTargets) */
	int get_nTargets(int /*long*/ pNTargets) {
		// TODO: Fix this
//		AccessibleRelationEvent event = new AccessibleRelationEvent(this);
//		for (int i = 0; i < accessibleRelationListeners.size(); i++) {
//			AccessibleRelationListener listener = (AccessibleRelationListener) accessibleRelationListeners.elementAt(i);
//			listener.getTargetCount(event);
//		}
//		COM.MoveMemory(pNTargets, new int [] { event.targetCount }, 4);
		return COM.S_OK;
	}

	/* get_target([in] targetIndex, [out] ppTarget) */
	int get_target(int targetIndex, int /*long*/ ppTarget) {
		// TODO: Fix this
//		AccessibleRelationEvent event = new AccessibleRelationEvent(this);
//		event.index = targetIndex;
//		for (int i = 0; i < accessibleRelationListeners.size(); i++) {
//			AccessibleRelationListener listener = (AccessibleRelationListener) accessibleRelationListeners.elementAt(i);
//			listener.getTarget(event);
//		}
//		Accessible accessible = event.target;
//		if (accessible != null) {
//			accessible.AddRef();
//			setPtrVARIANT(ppTarget, COM.VT_DISPATCH, accessible.objIAccessible.getAddress());
//		}
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL@retval E_INVALIDARG if bad [in] passed, [out] value is NULL
	}

	/* get_targets([in] maxTargets, [out] ppTargets, [out] pNTargets) */
	int get_targets(int maxTargets, int /*long*/ ppTargets, int /*long*/ pNTargets) {
		// TODO: Fix this
//		AccessibleRelationEvent event = new AccessibleRelationEvent(this);
//		event.maxTargets = maxTargets;
//		for (int i = 0; i < accessibleRelationListeners.size(); i++) {
//			AccessibleRelationListener listener = (AccessibleRelationListener) accessibleRelationListeners.elementAt(i);
//			listener.getTargets(event);
//		}
//		Accessible accessible = event.targets;
//		if (accessible != null) {
//			accessible.AddRef();
//			setPtrVARIANT(ppTargets, COM.VT_DISPATCH, accessible.objIAccessible.getAddress());
//		}
//		COM.MoveMemory(pNTargets, new int [] { event.targetCount }, 4);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, nTargets is set to 0
	}

	/* get_cellAt([in] row, [in] column, [out] ppCell) */
	int get_cellAt(int row, int column, int /*long*/ ppCell) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		event.row = row;
		event.column = column;
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.getCellAt(event);
		}
		Accessible accessible = event.accessible;
		if (accessible != null) {
			accessible.AddRef();
			setPtrVARIANT(ppCell, COM.VT_DISPATCH, accessible.objIAccessible.getAddress());
		}
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed, [out] value is NULL
	}

	/* get_caption([out] ppAccessible) */
	int get_caption(int /*long*/ ppAccessible) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.getCaption(event);
		}
		Accessible accessible = event.accessible;
		if (accessible != null) {
			accessible.AddRef();
			setPtrVARIANT(ppAccessible, COM.VT_DISPATCH, accessible.objIAccessible.getAddress());
		}
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL
	}

	/* get_columnDescription([in] column, [out] pbstrDescription) */
	int get_columnDescription(int column, int /*long*/ pbstrDescription) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		event.column = column;
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.getColumnDescription(event);
		}
		if (event.string.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		setString(pbstrDescription, event.string);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL@retval E_INVALIDARG if bad [in] passed, [out] value is NULL
	}

	/* get_nColumns([out] pColumnCount) */
	int get_nColumns(int /*long*/ pColumnCount) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.getColumnCount(event);
		}
		COM.MoveMemory(pColumnCount, new int [] { event.count }, 4);
		return COM.S_OK;
	}

	/* get_nRows([out] pRowCount) */
	int get_nRows(int /*long*/ pRowCount) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.getRowCount(event);
		}
		COM.MoveMemory(pRowCount, new int [] { event.count }, 4);
		return COM.S_OK;
	}

	/* get_nSelectedCells([out] pCellCount) */
	int get_nSelectedCells(int /*long*/ pCellCount) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.getSelectedCellCount(event);
		}
		COM.MoveMemory(pCellCount, new int [] { event.count }, 4);
		return COM.S_OK;
	}

	/* get_nSelectedColumns([out] pColumnCount) */
	int get_nSelectedColumns(int /*long*/ pColumnCount) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.getSelectedColumnCount(event);
		}
		COM.MoveMemory(pColumnCount, new int [] { event.count }, 4);
		return COM.S_OK;
	}

	/* get_nSelectedRows([out] pRowCount) */
	int get_nSelectedRows(int /*long*/ pRowCount) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.getSelectedRowCount(event);
		}
		COM.MoveMemory(pRowCount, new int [] { event.count }, 4);
		return COM.S_OK;
	}

	/* get_rowDescription([in] row, [out] pbstrDescription) */
	int get_rowDescription(int row, int /*long*/ pbstrDescription) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		event.row = row;
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.getRowDescription(event);
		}
		if (event.string.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		setString(pbstrDescription, event.string);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL@retval E_INVALIDARG if bad [in] passed, [out] value is NULL
	}

	/* get_selectedCells([out] ppCells, [out] pNSelectedCells) */
	int get_selectedCells(int /*long*/ ppCells, int /*long*/ pNSelectedCells) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.getSelectedCells(event);
		}
		// TODO: Handle array, not just first element
		Accessible accessible = event.accessibles[0];
		if (accessible != null) {
			accessible.AddRef();
			setPtrVARIANT(ppCells, COM.VT_DISPATCH, accessible.objIAccessible.getAddress());
		}
		COM.MoveMemory(pNSelectedCells, new int [] { event.count }, 4);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there are none, [out] values are NULL and 0 respectively
	}

	/* get_selectedColumns([out] ppSelectedColumns, [out] pNColumns) */
	int get_selectedColumns(int /*long*/ ppSelectedColumns, int /*long*/ pNColumns) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.getSelectedColumns(event);
		}
		// TODO: return whole array of selected items, not just first
		COM.MoveMemory(ppSelectedColumns, new int [] { event.selected[0] }, 4);
		COM.MoveMemory(pNColumns, new int [] { event.count }, 4);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there are none, [out] values are NULL and 0 respectively
	}

	/* get_selectedRows([out] ppSelectedRows, [out] pNRows) */
	int get_selectedRows(int /*long*/ ppSelectedRows, int /*long*/ pNRows) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.getSelectedRows(event);
		}
		// TODO: return whole array of selected items, not just first
		COM.MoveMemory(ppSelectedRows, new int [] { event.selected[0] }, 4);
		COM.MoveMemory(pNRows, new int [] { event.count }, 4);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there are none, [out] values are NULL and 0 respectively
	}

	/* get_summary([out] ppAccessible) */
	int get_summary(int /*long*/ ppAccessible) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.getSummary(event);
		}
		Accessible accessible = event.accessible;
		if (accessible != null) {
			accessible.AddRef();
			setPtrVARIANT(ppAccessible, COM.VT_DISPATCH, accessible.objIAccessible.getAddress());
		}
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL
	}

	/* get_isColumnSelected([in] column, [out] pIsSelected) */
	int get_isColumnSelected(int column, int /*long*/ pIsSelected) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		event.column = column;
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.getIsColumnSelected(event);
		}
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed, [out] value is FALSE
	}

	/* get_isRowSelected([in] row, [out] pIsSelected) */
	int get_isRowSelected(int row, int /*long*/ pIsSelected) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		event.row = row;
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.getIsRowSelected(event);
		}
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed, [out] value is FALSE
	}

	/* selectRow([in] row) */
	int selectRow(int row) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		event.row = row;
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.selectRow(event);
		}
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* selectColumn([in] column) */
	int selectColumn(int column) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		event.column = column;
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.selectColumn(event);
		}
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* unselectRow([in] row) */
	int unselectRow(int row) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		event.row = row;
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.unselectRow(event);
		}
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* unselectColumn([in] column) */
	int unselectColumn(int column) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		event.column = column;
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.unselectColumn(event);
		}
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* get_modelChange([out] pModelChange) */
	int get_modelChange(int /*long*/ pModelChange) {
		AccessibleTableEvent event = new AccessibleTableEvent(this);
		for (int i = 0; i < accessibleTableListeners.size(); i++) {
			AccessibleTableListener listener = (AccessibleTableListener) accessibleTableListeners.elementAt(i);
			listener.getModelChange(event);
		}
		// TODO: create modelChange struct to return from event data
		//COM.MoveMemory(pModelChange, new int [] { event.modelChange }, 4);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL
	}

	/* get_columnExtent([out] pNColumnsSpanned) */
	int get_columnExtent(int /*long*/ pNColumnsSpanned) {
		AccessibleTableCellEvent event = new AccessibleTableCellEvent(this);
		for (int i = 0; i < accessibleTableCellListeners.size(); i++) {
			AccessibleTableCellListener listener = (AccessibleTableCellListener) accessibleTableCellListeners.elementAt(i);
			listener.getColumnExtent(event);
		}
		COM.MoveMemory(pNColumnsSpanned, new int [] { event.count }, 4);
		return COM.S_OK;
	}

	/* get_columnHeaderCells([out] ppCellAccessibles, [out] pNColumnHeaderCells) */
	int get_columnHeaderCells(int /*long*/ ppCellAccessibles, int /*long*/ pNColumnHeaderCells) {
		AccessibleTableCellEvent event = new AccessibleTableCellEvent(this);
		for (int i = 0; i < accessibleTableCellListeners.size(); i++) {
			AccessibleTableCellListener listener = (AccessibleTableCellListener) accessibleTableCellListeners.elementAt(i);
			listener.getColumnHeaderCells(event);
		}
		// TODO: Handle array, not just first element
		Accessible accessible = event.accessibles[0];
		if (accessible != null) {
			accessible.AddRef();
			setPtrVARIANT(ppCellAccessibles, COM.VT_DISPATCH, accessible.objIAccessible.getAddress());
		}
		COM.MoveMemory(pNColumnHeaderCells, new int [] { event.count }, 4);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is no header, [out] values are NULL and 0 respectively
	}

	/* get_columnIndex([out] pColumnIndex) */
	int get_columnIndex(int /*long*/ pColumnIndex) {
		AccessibleTableCellEvent event = new AccessibleTableCellEvent(this);
		for (int i = 0; i < accessibleTableCellListeners.size(); i++) {
			AccessibleTableCellListener listener = (AccessibleTableCellListener) accessibleTableCellListeners.elementAt(i);
			listener.getColumnIndex(event);
		}
		COM.MoveMemory(pColumnIndex, new int [] { event.index }, 4);
		return COM.S_OK;
	}

	/* get_rowExtent([out] pNRowsSpanned) */
	int get_rowExtent(int /*long*/ pNRowsSpanned) {
		AccessibleTableCellEvent event = new AccessibleTableCellEvent(this);
		for (int i = 0; i < accessibleTableCellListeners.size(); i++) {
			AccessibleTableCellListener listener = (AccessibleTableCellListener) accessibleTableCellListeners.elementAt(i);
			listener.getRowExtent(event);
		}
		COM.MoveMemory(pNRowsSpanned, new int [] { event.count }, 4);
		return COM.S_OK;
	}

	/* get_rowHeaderCells([out] ppCellAccessibles, [out] pNRowHeaderCells) */
	int get_rowHeaderCells(int /*long*/ ppCellAccessibles, int /*long*/ pNRowHeaderCells) {
		AccessibleTableCellEvent event = new AccessibleTableCellEvent(this);
		for (int i = 0; i < accessibleTableCellListeners.size(); i++) {
			AccessibleTableCellListener listener = (AccessibleTableCellListener) accessibleTableCellListeners.elementAt(i);
			listener.getRowHeaderCells(event);
		}
		// TODO: Handle array, not just first element
		Accessible accessible = event.accessibles[0];
		if (accessible != null) {
			accessible.AddRef();
			setPtrVARIANT(ppCellAccessibles, COM.VT_DISPATCH, accessible.objIAccessible.getAddress());
		}
		COM.MoveMemory(pNRowHeaderCells, new int [] { event.count }, 4);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is no header, [out] values are NULL and 0 respectively
	}

	/* get_rowIndex([out] pRowIndex) */
	int get_rowIndex(int /*long*/ pRowIndex) {
		AccessibleTableCellEvent event = new AccessibleTableCellEvent(this);
		for (int i = 0; i < accessibleTableCellListeners.size(); i++) {
			AccessibleTableCellListener listener = (AccessibleTableCellListener) accessibleTableCellListeners.elementAt(i);
			listener.getRowIndex(event);
		}
		COM.MoveMemory(pRowIndex, new int [] { event.index }, 4);
		return COM.S_OK;
	}

	/* get_isSelected([out] pIsSelected) */
	int get_isSelected(int /*long*/ pIsSelected) {
		AccessibleTableCellEvent event = new AccessibleTableCellEvent(this);
		for (int i = 0; i < accessibleTableCellListeners.size(); i++) {
			AccessibleTableCellListener listener = (AccessibleTableCellListener) accessibleTableCellListeners.elementAt(i);
			listener.getIsSelected(event);
		}
		return COM.S_OK;
	}

	/* get_rowColumnExtents([out] pRow, [out] pColumn, [out] pRowExtents, [out] pColumnExtents, [out] pIsSelected) */
	int get_rowColumnExtents(int /*long*/ pRow, int /*long*/ pColumn, int /*long*/ pRowExtents, int /*long*/ pColumnExtents, int /*long*/ pIsSelected) {
		AccessibleTableCellEvent event = new AccessibleTableCellEvent(this);
		for (int i = 0; i < accessibleTableCellListeners.size(); i++) {
			AccessibleTableCellListener listener = (AccessibleTableCellListener) accessibleTableCellListeners.elementAt(i);
			listener.getRowColumnExtents(event);
		}
		COM.MoveMemory(pRow, new int [] { event.row }, 4);
		COM.MoveMemory(pColumn, new int [] { event.column }, 4);
		COM.MoveMemory(pRowExtents, new int [] { event.rowExtents }, 4);
		COM.MoveMemory(pColumnExtents, new int [] { event.columnExtents }, 4);
		return COM.S_OK;
	}

	/* get_table([out] ppTable) */
	int get_table(int /*long*/ ppTable) {
		AccessibleTableCellEvent event = new AccessibleTableCellEvent(this);
		for (int i = 0; i < accessibleTableCellListeners.size(); i++) {
			AccessibleTableCellListener listener = (AccessibleTableCellListener) accessibleTableCellListeners.elementAt(i);
			listener.getTable(event);
		}
		Accessible accessible = event.accessible;
		if (accessible != null) {
			accessible.AddRef();
			setPtrVARIANT(ppTable, COM.VT_DISPATCH, accessible.objIAccessible.getAddress());
		}
		return COM.S_OK;
	}

	/* addSelection([in] startOffset, [in] endOffset) */
	int addSelection(int startOffset, int endOffset) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		event.start = startOffset;
		event.end = endOffset;
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.addSelection(event);
		}
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* get_attributes([in] offset, [out] pStartOffset, [out] pEndOffset, [out] pbstrTextAttributes) */
	int get_attributes(int offset, int /*long*/ pStartOffset, int /*long*/ pEndOffset, int /*long*/ pbstrTextAttributes) {
		AccessibleTextAttributeEvent event = new AccessibleTextAttributeEvent(this);
		event.offset = offset;
		for (int i = 0; i < accessibleAttributeListeners.size(); i++) {
			AccessibleAttributeListener listener = (AccessibleAttributeListener) accessibleAttributeListeners.elementAt(i);
			listener.getTextAttributes(event);
		}
		COM.MoveMemory(pStartOffset, new int [] { event.start }, 4);
		COM.MoveMemory(pEndOffset, new int [] { event.end }, 4);
		String textAttributes = "";
		// TODO: Construct text attributes string from event data
		if (textAttributes.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		setString(pbstrTextAttributes, textAttributes);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] values are 0s and NULL respectively@retval E_INVALIDARG if bad [in] passed, [out] values are 0s and NULL respectively
	}

	/* get_caretOffset([out] pOffset) */
	int get_caretOffset(int /*long*/ pOffset) {
		// TODO: already in old API (in super interface)
		AccessibleTextEvent event = new AccessibleTextEvent(this);
		for (int i = 0; i < accessibleTextListeners.size(); i++) {
			AccessibleTextListener listener = (AccessibleTextListener) accessibleTextListeners.elementAt(i);
			listener.getCaretOffset (event);
		}
		COM.MoveMemory(pOffset, new int [] { event.offset }, 4);
		return COM.S_OK;
		// TODO: @retval S_FALSE if the caret is not currently active on this object, i.e. the
	}

	/* get_characterExtents([in] offset, [in] coordType, [out] pX, [out] pY, [out] pWidth, [out] pHeight) */
	int get_characterExtents(int offset, int coordType, int /*long*/ pX, int /*long*/ pY, int /*long*/ pWidth, int /*long*/ pHeight) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		event.index = offset;
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.getCharacterExtents(event);
		}
		COM.MoveMemory(pX, new int [] { event.x }, 4);
		COM.MoveMemory(pY, new int [] { event.y }, 4);
		COM.MoveMemory(pWidth, new int [] { event.width }, 4);
		COM.MoveMemory(pHeight, new int [] { event.height }, 4);
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed, [out] values are 0s
	}

	/* get_nSelections([out] pNSelections) */
	int get_nSelections(int /*long*/ pNSelections) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.getSelectionCount(event);
		}
		COM.MoveMemory(pNSelections, new int [] { event.count }, 4);
		return COM.S_OK;
	}

	/* get_offsetAtPoint([in] x, [in] y, [in] coordType, [out] pOffset) */
	int get_offsetAtPoint(int x, int y, int coordType, int /*long*/ pOffset) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		event.x = x;
		event.y = y;
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.getOffsetAtPoint(event);
		}
		COM.MoveMemory(pOffset, new int [] { event.index }, 4);
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed, [out] value is 0
	}

	/* get_selection([in] selectionIndex, [out] pStartOffset, [out] pEndOffset) */
	int get_selection(int selectionIndex, int /*long*/ pStartOffset, int /*long*/ pEndOffset) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		event.index = selectionIndex;
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.getSelection(event);
		}
		COM.MoveMemory(pStartOffset, new int [] { event.start }, 4);
		COM.MoveMemory(pEndOffset, new int [] { event.end }, 4);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] values are 0s@retval E_INVALIDARG if bad [in] passed, [out] values are 0s
	}

	/* get_text([in] startOffset, [in] endOffset, [out] pbstrText) */
	int get_text(int startOffset, int endOffset, int /*long*/ pbstrText) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		event.start = startOffset;
		event.end = endOffset;
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.getText(event);
		}
		if (event.string.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		setString(pbstrText, event.string);
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed, [out] value is NULL
	}

	/* get_textBeforeOffset([in] offset, [in] boundaryType, [out] pStartOffset, [out] pEndOffset, [out] pbstrText) */
	int get_textBeforeOffset(int offset, int boundaryType, int /*long*/ pStartOffset, int /*long*/ pEndOffset, int /*long*/ pbstrText) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		event.offset = offset;
		event.type = boundaryType;
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.getTextBeforeOffset(event);
		}
		COM.MoveMemory(pStartOffset, new int [] { event.start }, 4);
		COM.MoveMemory(pEndOffset, new int [] { event.end }, 4);
		if (event.string.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		setString(pbstrText, event.string);
		return COM.S_OK;
		// TODO: @retval S_FALSE if the requested boundary type is not implemented, such as@retval E_INVALIDARG if bad [in] passed, [out] values are 0s and NULL respectively
	}

	/* get_textAfterOffset([in] offset, [in] boundaryType, [out] pStartOffset, [out] pEndOffset, [out] pbstrText) */
	int get_textAfterOffset(int offset, int boundaryType, int /*long*/ pStartOffset, int /*long*/ pEndOffset, int /*long*/ pbstrText) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		event.offset = offset;
		event.type = boundaryType;
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.getTextAfterOffset(event);
		}
		COM.MoveMemory(pStartOffset, new int [] { event.start }, 4);
		COM.MoveMemory(pEndOffset, new int [] { event.end }, 4);
		if (event.string.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		setString(pbstrText, event.string);
		return COM.S_OK;
		// TODO: @retval S_FALSE if the requested boundary type is not implemented, such as@retval E_INVALIDARG if bad [in] passed, [out] values are 0s and NULL respectively
	}

	/* get_textAtOffset([in] offset, [in] boundaryType, [out] pStartOffset, [out] pEndOffset, [out] pbstrText) */
	int get_textAtOffset(int offset, int boundaryType, int /*long*/ pStartOffset, int /*long*/ pEndOffset, int /*long*/ pbstrText) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		event.offset = offset;
		event.type = boundaryType;
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.getTextAtOffset(event);
		}
		COM.MoveMemory(pStartOffset, new int [] { event.start }, 4);
		COM.MoveMemory(pEndOffset, new int [] { event.end }, 4);
		if (event.string.length() == 0) return COM.S_FALSE; // TODO: is S_FALSE ok here?
		setString(pbstrText, event.string);
		return COM.S_OK;
		// TODO: @retval S_FALSE if the requested boundary type is not implemented, such as@retval E_INVALIDARG if bad [in] passed, [out] values are 0s and NULL respectively
	}

	/* removeSelection([in] selectionIndex) */
	int removeSelection(int selectionIndex) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		event.index = selectionIndex;
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.removeSelection(event);
		}
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* setCaretOffset([in] offset) */
	int setCaretOffset(int offset) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		event.index = offset;
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.setCaretOffset(event);
		}
		return COM.S_OK;
		// TODO: @retval E_FAIL if the caret cannot be set@retval E_INVALIDARG if bad [in] passed
	}

	/* setSelection([in] selectionIndex, [in] startOffset, [in] endOffset) */
	int setSelection(int selectionIndex, int startOffset, int endOffset) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		event.index = selectionIndex;
		event.start = startOffset;
		event.end = endOffset;
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.setSelection(event);
		}
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* get_nCharacters([out] pNCharacters) */
	int get_nCharacters(int /*long*/ pNCharacters) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.getCharacterCount(event);
		}
		COM.MoveMemory(pNCharacters, new int [] { event.count }, 4);
		return COM.S_OK;
	}

	/* scrollSubstringTo([in] startIndex, [in] endIndex, [in] scrollType) */
	int scrollSubstringTo(int startIndex, int endIndex, int scrollType) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		event.start = startIndex;
		event.end = endIndex;
		event.type = scrollType;
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.scrollSubstringTo(event);
		}
		return COM.S_OK;
		// TODO: @retval E_INVALIDARG if bad [in] passed
	}

	/* scrollSubstringToPoint([in] startIndex, [in] endIndex, [in] coordinateType, [in] x, [in] y) */
	int scrollSubstringToPoint(int startIndex, int endIndex, int coordinateType, int x, int y) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		event.start = startIndex;
		event.end = endIndex;
		event.x = x;
		event.y = y;
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.scrollSubstringToPoint(event);
		}
		return COM.S_OK;
		// TODO: @retval S_FALSE if the object is already at the specified location.@retval E_INVALIDARG if bad [in] passed
	}

	/* get_newText([out] pNewText) */
	int get_newText(int /*long*/ pNewText) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.getNewText(event);
		}
		// TODO: create a text segment struct using the data in the event
		//COM.MoveMemory(pNewText, new int [] { event.string }, 4);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL
	}

	/* get_oldText([out] pOldText) */
	int get_oldText(int /*long*/ pOldText) {
		AccessibleTextExtendedEvent event = new AccessibleTextExtendedEvent(this);
		for (int i = 0; i < accessibleTextExtendedListeners.size(); i++) {
			AccessibleTextExtendedListener listener = (AccessibleTextExtendedListener) accessibleTextExtendedListeners.elementAt(i);
			listener.getOldText(event);
		}
		// TODO: create a text segment struct using the data in the event
		//COM.MoveMemory(pOldText, new int [] { event.string }, 4);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL
	}

	/* get_currentValue([out] pCurrentValue) */
	int get_currentValue(int /*long*/ pCurrentValue) {
		AccessibleValueEvent event = new AccessibleValueEvent(this);
		for (int i = 0; i < accessibleValueListeners.size(); i++) {
			AccessibleValueListener listener = (AccessibleValueListener) accessibleValueListeners.elementAt(i);
			listener.getCurrentValue(event);
		}
		COM.MoveMemory(pCurrentValue, new int [] { event.value }, 4);
		return COM.S_OK;
		// TODO: @retval S_FALSE if there is nothing to return, [out] value is NULL
	}

	/* setCurrentValue([in] value) */
	int setCurrentValue(int /*long*/ value) {
		AccessibleValueEvent event = new AccessibleValueEvent(this);
		event.value = value;
		for (int i = 0; i < accessibleValueListeners.size(); i++) {
			AccessibleValueListener listener = (AccessibleValueListener) accessibleValueListeners.elementAt(i);
			listener.setCurrentValue(event);
		}
		return COM.S_OK;
	}

	/* get_maximumValue([out] pMaximumValue) */
	int get_maximumValue(int /*long*/ pMaximumValue) {
		AccessibleValueEvent event = new AccessibleValueEvent(this);
		for (int i = 0; i < accessibleValueListeners.size(); i++) {
			AccessibleValueListener listener = (AccessibleValueListener) accessibleValueListeners.elementAt(i);
			listener.getMaximumValue(event);
		}
		COM.MoveMemory(pMaximumValue, new int [] { event.value }, 4);
		return COM.S_OK;
	}

	/* get_minimumValue([out] pMinimumValue) */
	int get_minimumValue(int /*long*/ pMinimumValue) {
		AccessibleValueEvent event = new AccessibleValueEvent(this);
		for (int i = 0; i < accessibleValueListeners.size(); i++) {
			AccessibleValueListener listener = (AccessibleValueListener) accessibleValueListeners.elementAt(i);
			listener.getMinimumValue(event);
		}
		COM.MoveMemory(pMinimumValue, new int [] { event.value }, 4);
		return COM.S_OK;
	}

	int childIDToOs(int childID) {
		if (childID == ACC.CHILDID_SELF) return COM.CHILDID_SELF;
		/*
		* Feature of Windows:
		* In Windows XP, tree item ids are 1-based indices. Previous versions
		* of Windows use the tree item handle for the accessible child ID.
		* For backward compatibility, we still take a handle childID for tree
		* items on XP. All other childIDs are 1-based indices.
		*/
		if (!(control instanceof Tree)) return childID + 1;
		if (OS.COMCTL32_MAJOR < 6) return childID;
		return (int)/*64*/OS.SendMessage (control.handle, OS.TVM_MAPHTREEITEMTOACCID, childID, 0);
	}

	int osToChildID(int osChildID) {
		if (osChildID == COM.CHILDID_SELF) return ACC.CHILDID_SELF;
		/*
		* Feature of Windows:
		* In Windows XP, tree item ids are 1-based indices. Previous versions
		* of Windows use the tree item handle for the accessible child ID.
		* For backward compatibility, we still take a handle childID for tree
		* items on XP. All other childIDs are 1-based indices.
		*/
		if (!(control instanceof Tree)) return osChildID - 1;
		if (OS.COMCTL32_MAJOR < 6) return osChildID;
		return (int)/*64*/OS.SendMessage (control.handle, OS.TVM_MAPACCIDTOHTREEITEM, osChildID, 0);
	}
	
	int stateToOs(int state) {
		int osState = 0;
		if ((state & ACC.STATE_SELECTED) != 0) osState |= COM.STATE_SYSTEM_SELECTED;
		if ((state & ACC.STATE_SELECTABLE) != 0) osState |= COM.STATE_SYSTEM_SELECTABLE;
		if ((state & ACC.STATE_MULTISELECTABLE) != 0) osState |= COM.STATE_SYSTEM_MULTISELECTABLE;
		if ((state & ACC.STATE_FOCUSED) != 0) osState |= COM.STATE_SYSTEM_FOCUSED;
		if ((state & ACC.STATE_FOCUSABLE) != 0) osState |= COM.STATE_SYSTEM_FOCUSABLE;
		if ((state & ACC.STATE_PRESSED) != 0) osState |= COM.STATE_SYSTEM_PRESSED;
		if ((state & ACC.STATE_CHECKED) != 0) osState |= COM.STATE_SYSTEM_CHECKED;
		if ((state & ACC.STATE_EXPANDED) != 0) osState |= COM.STATE_SYSTEM_EXPANDED;
		if ((state & ACC.STATE_COLLAPSED) != 0) osState |= COM.STATE_SYSTEM_COLLAPSED;
		if ((state & ACC.STATE_HOTTRACKED) != 0) osState |= COM.STATE_SYSTEM_HOTTRACKED;
		if ((state & ACC.STATE_BUSY) != 0) osState |= COM.STATE_SYSTEM_BUSY;
		if ((state & ACC.STATE_READONLY) != 0) osState |= COM.STATE_SYSTEM_READONLY;
		if ((state & ACC.STATE_INVISIBLE) != 0) osState |= COM.STATE_SYSTEM_INVISIBLE;
		if ((state & ACC.STATE_OFFSCREEN) != 0) osState |= COM.STATE_SYSTEM_OFFSCREEN;
		if ((state & ACC.STATE_SIZEABLE) != 0) osState |= COM.STATE_SYSTEM_SIZEABLE;
		if ((state & ACC.STATE_LINKED) != 0) osState |= COM.STATE_SYSTEM_LINKED;
		return osState;
	}
	
	int osToState(int osState) {
		int state = ACC.STATE_NORMAL;
		if ((osState & COM.STATE_SYSTEM_SELECTED) != 0) state |= ACC.STATE_SELECTED;
		if ((osState & COM.STATE_SYSTEM_SELECTABLE) != 0) state |= ACC.STATE_SELECTABLE;
		if ((osState & COM.STATE_SYSTEM_MULTISELECTABLE) != 0) state |= ACC.STATE_MULTISELECTABLE;
		if ((osState & COM.STATE_SYSTEM_FOCUSED) != 0) state |= ACC.STATE_FOCUSED;
		if ((osState & COM.STATE_SYSTEM_FOCUSABLE) != 0) state |= ACC.STATE_FOCUSABLE;
		if ((osState & COM.STATE_SYSTEM_PRESSED) != 0) state |= ACC.STATE_PRESSED;
		if ((osState & COM.STATE_SYSTEM_CHECKED) != 0) state |= ACC.STATE_CHECKED;
		if ((osState & COM.STATE_SYSTEM_EXPANDED) != 0) state |= ACC.STATE_EXPANDED;
		if ((osState & COM.STATE_SYSTEM_COLLAPSED) != 0) state |= ACC.STATE_COLLAPSED;
		if ((osState & COM.STATE_SYSTEM_HOTTRACKED) != 0) state |= ACC.STATE_HOTTRACKED;
		if ((osState & COM.STATE_SYSTEM_BUSY) != 0) state |= ACC.STATE_BUSY;
		if ((osState & COM.STATE_SYSTEM_READONLY) != 0) state |= ACC.STATE_READONLY;
		if ((osState & COM.STATE_SYSTEM_INVISIBLE) != 0) state |= ACC.STATE_INVISIBLE;
		if ((osState & COM.STATE_SYSTEM_OFFSCREEN) != 0) state |= ACC.STATE_OFFSCREEN;
		if ((osState & COM.STATE_SYSTEM_SIZEABLE) != 0) state |= ACC.STATE_SIZEABLE;
		if ((osState & COM.STATE_SYSTEM_LINKED) != 0) state |= ACC.STATE_LINKED;
		return state;
	}

	int roleToOs(int role) {
		switch (role) {
			case ACC.ROLE_CLIENT_AREA: return COM.ROLE_SYSTEM_CLIENT;
			case ACC.ROLE_WINDOW: return COM.ROLE_SYSTEM_WINDOW;
			case ACC.ROLE_MENUBAR: return COM.ROLE_SYSTEM_MENUBAR;
			case ACC.ROLE_MENU: return COM.ROLE_SYSTEM_MENUPOPUP;
			case ACC.ROLE_MENUITEM: return COM.ROLE_SYSTEM_MENUITEM;
			case ACC.ROLE_SEPARATOR: return COM.ROLE_SYSTEM_SEPARATOR;
			case ACC.ROLE_TOOLTIP: return COM.ROLE_SYSTEM_TOOLTIP;
			case ACC.ROLE_SCROLLBAR: return COM.ROLE_SYSTEM_SCROLLBAR;
			case ACC.ROLE_DIALOG: return COM.ROLE_SYSTEM_DIALOG;
			case ACC.ROLE_LABEL: return COM.ROLE_SYSTEM_STATICTEXT;
			case ACC.ROLE_PUSHBUTTON: return COM.ROLE_SYSTEM_PUSHBUTTON;
			case ACC.ROLE_CHECKBUTTON: return COM.ROLE_SYSTEM_CHECKBUTTON;
			case ACC.ROLE_RADIOBUTTON: return COM.ROLE_SYSTEM_RADIOBUTTON;
			case ACC.ROLE_SPLITBUTTON: return COM.ROLE_SYSTEM_SPLITBUTTON;
			case ACC.ROLE_COMBOBOX: return COM.ROLE_SYSTEM_COMBOBOX;
			case ACC.ROLE_TEXT: return COM.ROLE_SYSTEM_TEXT;
			case ACC.ROLE_TOOLBAR: return COM.ROLE_SYSTEM_TOOLBAR;
			case ACC.ROLE_LIST: return COM.ROLE_SYSTEM_LIST;
			case ACC.ROLE_LISTITEM: return COM.ROLE_SYSTEM_LISTITEM;
			case ACC.ROLE_TABLE: return COM.ROLE_SYSTEM_TABLE;
			case ACC.ROLE_TABLECELL: return COM.ROLE_SYSTEM_CELL;
			case ACC.ROLE_TABLECOLUMNHEADER: return COM.ROLE_SYSTEM_COLUMNHEADER;
			case ACC.ROLE_TABLEROWHEADER: return COM.ROLE_SYSTEM_ROWHEADER;
			case ACC.ROLE_TREE: return COM.ROLE_SYSTEM_OUTLINE;
			case ACC.ROLE_TREEITEM: return COM.ROLE_SYSTEM_OUTLINEITEM;
			case ACC.ROLE_TABFOLDER: return COM.ROLE_SYSTEM_PAGETABLIST;
			case ACC.ROLE_TABITEM: return COM.ROLE_SYSTEM_PAGETAB;
			case ACC.ROLE_PROGRESSBAR: return COM.ROLE_SYSTEM_PROGRESSBAR;
			case ACC.ROLE_SLIDER: return COM.ROLE_SYSTEM_SLIDER;
			case ACC.ROLE_LINK: return COM.ROLE_SYSTEM_LINK;
		}
		return COM.ROLE_SYSTEM_CLIENT;
	}

	int osToRole(int osRole) {
		switch (osRole) {
			case COM.ROLE_SYSTEM_CLIENT: return ACC.ROLE_CLIENT_AREA;
			case COM.ROLE_SYSTEM_WINDOW: return ACC.ROLE_WINDOW;
			case COM.ROLE_SYSTEM_MENUBAR: return ACC.ROLE_MENUBAR;
			case COM.ROLE_SYSTEM_MENUPOPUP: return ACC.ROLE_MENU;
			case COM.ROLE_SYSTEM_MENUITEM: return ACC.ROLE_MENUITEM;
			case COM.ROLE_SYSTEM_SEPARATOR: return ACC.ROLE_SEPARATOR;
			case COM.ROLE_SYSTEM_TOOLTIP: return ACC.ROLE_TOOLTIP;
			case COM.ROLE_SYSTEM_SCROLLBAR: return ACC.ROLE_SCROLLBAR;
			case COM.ROLE_SYSTEM_DIALOG: return ACC.ROLE_DIALOG;
			case COM.ROLE_SYSTEM_STATICTEXT: return ACC.ROLE_LABEL;
			case COM.ROLE_SYSTEM_PUSHBUTTON: return ACC.ROLE_PUSHBUTTON;
			case COM.ROLE_SYSTEM_CHECKBUTTON: return ACC.ROLE_CHECKBUTTON;
			case COM.ROLE_SYSTEM_RADIOBUTTON: return ACC.ROLE_RADIOBUTTON;
			case COM.ROLE_SYSTEM_SPLITBUTTON: return ACC.ROLE_SPLITBUTTON;
			case COM.ROLE_SYSTEM_COMBOBOX: return ACC.ROLE_COMBOBOX;
			case COM.ROLE_SYSTEM_TEXT: return ACC.ROLE_TEXT;
			case COM.ROLE_SYSTEM_TOOLBAR: return ACC.ROLE_TOOLBAR;
			case COM.ROLE_SYSTEM_LIST: return ACC.ROLE_LIST;
			case COM.ROLE_SYSTEM_LISTITEM: return ACC.ROLE_LISTITEM;
			case COM.ROLE_SYSTEM_TABLE: return ACC.ROLE_TABLE;
			case COM.ROLE_SYSTEM_CELL: return ACC.ROLE_TABLECELL;
			case COM.ROLE_SYSTEM_COLUMNHEADER: return ACC.ROLE_TABLECOLUMNHEADER;
			case COM.ROLE_SYSTEM_ROWHEADER: return ACC.ROLE_TABLEROWHEADER;
			case COM.ROLE_SYSTEM_OUTLINE: return ACC.ROLE_TREE;
			case COM.ROLE_SYSTEM_OUTLINEITEM: return ACC.ROLE_TREEITEM;
			case COM.ROLE_SYSTEM_PAGETABLIST: return ACC.ROLE_TABFOLDER;
			case COM.ROLE_SYSTEM_PAGETAB: return ACC.ROLE_TABITEM;
			case COM.ROLE_SYSTEM_PROGRESSBAR: return ACC.ROLE_PROGRESSBAR;
			case COM.ROLE_SYSTEM_SLIDER: return ACC.ROLE_SLIDER;
			case COM.ROLE_SYSTEM_LINK: return ACC.ROLE_LINK;
		}
		return ACC.ROLE_CLIENT_AREA;
	}

	VARIANT getVARIANT(int /*long*/ variant) {
		VARIANT v = new VARIANT();
		COM.MoveMemory(v, variant, VARIANT.sizeof);
		return v;
	}

	void setVARIANT(int /*long*/ variant, short vt, int lVal) {
		if (vt == COM.VT_I4 || vt == COM.VT_EMPTY) {
			COM.MoveMemory(variant, new short[] { vt }, 2);
			COM.MoveMemory(variant + 8, new int[] { lVal }, 4);
		}
	}

	void setPtrVARIANT(int /*long*/ variant, short vt, int /*long*/ lVal) {
		if (vt == COM.VT_DISPATCH || vt == COM.VT_UNKNOWN) {
			COM.MoveMemory(variant, new short[] { vt }, 2);
			COM.MoveMemory(variant + 8, new int /*long*/[] { lVal }, OS.PTR_SIZEOF);
		}
	}
	
	void setString(int psz, String string) {
		char[] data = (string + "\0").toCharArray();
		int /*long*/ ptr = COM.SysAllocString(data);
		COM.MoveMemory(psz, new int /*long*/ [] { ptr }, OS.PTR_SIZEOF);
	}

	/* checkWidget was copied from Widget, and rewritten to work in this package */
	void checkWidget () {
		if (!isValidThread ()) SWT.error (SWT.ERROR_THREAD_INVALID_ACCESS);
		if (control.isDisposed ()) SWT.error (SWT.ERROR_WIDGET_DISPOSED);
	}

	/* isValidThread was copied from Widget, and rewritten to work in this package */
	boolean isValidThread () {
		return control.getDisplay ().getThread () == Thread.currentThread ();
	}
}
