//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.thx.firefightingteam.tools;

import android.content.Context;
import android.content.SharedPreferences;
import org.androidannotations.api.sharedpreferences.BooleanPrefEditorField;
import org.androidannotations.api.sharedpreferences.BooleanPrefField;
import org.androidannotations.api.sharedpreferences.EditorHelper;
import org.androidannotations.api.sharedpreferences.SharedPreferencesHelper;

public final class DemoPref_
    extends SharedPreferencesHelper
{

    private Context context_;

    public DemoPref_(Context context) {
        super(context.getSharedPreferences("DemoPref", 0));
        this.context_ = context;
    }

    public DemoPref_.DemoPrefEditor_ edit() {
        return new DemoPref_.DemoPrefEditor_(getSharedPreferences());
    }

    public BooleanPrefField isLocalDemoMode() {
        return booleanField("isLocalDemoMode", false);
    }

    public final static class DemoPrefEditor_
        extends EditorHelper<DemoPref_.DemoPrefEditor_>
    {


        DemoPrefEditor_(SharedPreferences sharedPreferences) {
            super(sharedPreferences);
        }

        public BooleanPrefEditorField<DemoPref_.DemoPrefEditor_> isLocalDemoMode() {
            return booleanField("isLocalDemoMode");
        }

    }

}