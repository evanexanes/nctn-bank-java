/*
Program name: module-info.java
Description: Declares the InprolaFinalProject module. The only dependency is
   java.desktop, which provides the Swing dialogs the banking app uses.
Author: Exanes, Evan Prens (two-person team project)
Date Compiled: 10/08/2026
======================================================================
Date           Description                      Programmer name
======================================================================
10/08/2026     Initial Implementation           EPPE
11/09/2026     Added header and comments        EPPE
======================================================================
*/

/** Module descriptor. java.desktop provides Swing, which the JOptionPane menus use. */
module InprolaFinalProject {
	requires java.desktop;
}