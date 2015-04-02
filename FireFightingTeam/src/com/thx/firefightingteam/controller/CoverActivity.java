package com.thx.firefightingteam.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.thx.firefightingteam.R;
import com.thx.firefightingteam.modle.User;
import com.thx.firefightingteam.service.LoginService;
import com.thx.firefightingteam.tools.DatabaseHelper;
import com.thx.firefightingteam.tools.GlobalTools;
import com.xhtoolkit.controller.UnderConstructionFragment;
import com.xhtoolkit.tool.SelectVersionDialogFragment;
import com.xhtoolkit.tool.SelectVersionDialogFragment.SelectVersionDialogListener;

@EActivity(R.layout.cover_layout)
public class CoverActivity extends ActionBarActivity implements
		SelectVersionDialogListener {

	private DatabaseHelper databaseHelper;

	private Fragment underConstructionFragment;

	private DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this,
					DatabaseHelper.class);
		}
		return databaseHelper;
	}

	@AfterViews
	void afterViews() {
		startDemoAlert();
		// 检查是否正式版发布
		if (GlobalTools.isIgnoreDemoMode()) {
			// 正式版本，直接加载
		} else {
			// 本地模拟切换功能 
		
		}

	}

	@SuppressLint("Recycle")
	private void setFragment() {

		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();

		List<Fragment> list = manager.getFragments();
		if (list != null) {
			for (Fragment fragment : list) {
				transaction.remove(fragment);
			}
		}
		underConstructionFragment = new UnderConstructionFragment();
		transaction.add(R.id.converContern, underConstructionFragment);
		transaction.show(underConstructionFragment);
		transaction.commit();
	}

	@Override
	public void onDialogVersionAloneClick(DialogFragment dialog) {
		Intent intent=new Intent(this,HomeActivity.class);
		startActivity(intent);
		
	}

	void startDemoAlert() {
		new SelectVersionDialogFragment()
				.setTitle("提示")
				.setContent("请选择启动版本")
				.setPositiveButton("单机演示版")
				.setNegative("联机版")
				.show(getSupportFragmentManager(),
						"SelectVersionDialogFragment");

	}

	@Override
	public void onDialogVersionOnlineClick(DialogFragment dialog) {
		dialog.dismiss();
	}

	void p(Object o) {

		Log.i("message", o + "");
	}

}
