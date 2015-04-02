package com.thx.firefightingteam.controller;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.thx.firefightingteam.R;
import com.thx.firefightingteam.fragment.MapFragment;
import com.thx.firefightingteam.fragment.MenuFragment;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.SearchView.OnQueryTextListener;

public class HomeActivity extends BaseHomeActivity {
	
	private MapFragment mapFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		initActionBar();
		initSliding(savedInstanceState);
	}

	private void initActionBar() {
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String arg0) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String arg0) {
				return false;
			}
		});
	

	}

	

	private void initSliding(Bundle savedInstanceState) {
		if (findViewById(R.id.menu_frame) == null) {
			setBehindContentView(R.layout.menu_frame);
			getSlidingMenu().setSlidingEnabled(true);
			getSlidingMenu()
					.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}
		// set the Above View Fragment
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		}
		if (mContent == null) {
			mapFragment = new MapFragment();
			mContent = mapFragment;
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent, "map_fragment").commit();

		// set the Behind View Fragment
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuFragment()).commit();
		SlidingMenu sm = getSlidingMenu();
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeEnabled(false);
		sm.setBehindScrollScale(0.25f);
		sm.setFadeDegree(0.25f);

		sm.setBackgroundImage(R.drawable.img_frame_background);
		sm.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen * 0.25 + 0.75);
				canvas.scale(scale, scale, -canvas.getWidth() / 2,
						canvas.getHeight() / 2);
			}
		});

		sm.setAboveCanvasTransformer(new SlidingMenu.CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (1 - percentOpen * 0.25);
				canvas.scale(scale, scale, 0, canvas.getHeight() / 2);
			}
		});

	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 取消监听 SDK 广播
		unregisterReceiver(mReceiver);
		p("销毁 了");
	}


}
