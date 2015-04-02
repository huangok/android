package com.thx.firefightingteam.tools;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.SharedPref;


@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface DemoPref {

	@DefaultBoolean(false)
	boolean isLocalDemoMode();
}
