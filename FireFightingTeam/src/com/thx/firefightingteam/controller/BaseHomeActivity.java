package com.thx.firefightingteam.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityBase;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivityHelper;
import com.thx.firefightingteam.R;

public abstract class BaseHomeActivity extends ActionBarActivity implements
		SlidingActivityBase {
	SlidingActivityHelper mHelper;
	protected SearchView searchView;
	protected SDKReceiver mReceiver;
	Fragment mContent;

	@Override
	protected  void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getFilesDir().getAbsolutePath(),
				this.getApplicationContext());
		registerRecevice();
		if (null == mHelper)
			mHelper = new SlidingActivityHelper(this);
		mHelper.onCreate(savedInstanceState);
		
	}
	private void registerRecevice() {
		// 注册 SDK 广播监听者
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new SDKReceiver();
		registerReceiver(mReceiver, iFilter);
	}
	class SDKReceiver extends BroadcastReceiver {
		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();
			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				p("key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
			} else if (s
					.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				p("网络出错");
			}
		}
	}

	@Override
	public void setContentView(int layoutResID) {
		
		super.setContentView(layoutResID);
		mHelper.registerAboveContentView(
				getLayoutInflater().inflate(layoutResID, null),
				new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT));
	}

	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v != null)
			return v;
		return mHelper.findViewById(id);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mHelper.onPostCreate(savedInstanceState);
	}

	@Override
	public void setBehindContentView(View view, LayoutParams layoutParams) {
		mHelper.setBehindContentView(view, layoutParams);
	}

	@Override
	public void setBehindContentView(View view) {
		setBehindContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
	}

	@Override
	public void setBehindContentView(int layoutResID) {
		setBehindContentView(getLayoutInflater().inflate(layoutResID, null));
	}

	@Override
	public SlidingMenu getSlidingMenu() {
		return mHelper.getSlidingMenu();
	}

	@Override
	public void toggle() {
		mHelper.toggle();
	}

	@Override
	public void showContent() {
		mHelper.showContent();
	}

	@Override
	public void showMenu() {
		mHelper.showMenu();
	}

	@Override
	public void showSecondaryMenu() {
		mHelper.showSecondaryMenu();
	}

	@Override
	public void setSlidingActionBarEnabled(boolean slidingActionBarEnabled) {
		mHelper.setSlidingActionBarEnabled(slidingActionBarEnabled);
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean b = mHelper.onKeyUp(keyCode, event);
		if (b)return b;
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (null != mContent)
			getSupportFragmentManager().putFragment(outState, "mContent",
					mContent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem menuItem = menu.findItem(R.id.menu_search);
		searchView = new SearchView(this);
		MenuItemCompat.setActionView(menuItem, searchView);
		MenuItemCompat.setShowAsAction(menuItem,
				MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
		menuItem.setTitle("搜索");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.edit_map: {
			return true;
		}
		case R.id.map_change: {

			return true;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	protected void p(Object o) {
		Log.i("message", o + "");
	}

	protected void t(Object o) {
		Toast.makeText(this, o + "", Toast.LENGTH_SHORT).show();
	}

}
