package com.slidingmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.slidingmenu.view.SlidingMenu;

/**
 * 
 * @author soufun
 * 
 */
public class MainActivity extends Activity {

	private SlidingMenu mLeftMenu;
	private RelativeLayout rl_slidingmenu_05, rl_slidingmenu_04,
			rl_slidingmenu_03, rl_slidingmenu_02, rl_slidingmenu_01;
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mLeftMenu = (SlidingMenu) findViewById(R.id.slidingmenu);
		textView = (TextView) findViewById(R.id.tv_content);
		rl_slidingmenu_01 = (RelativeLayout) findViewById(R.id.rl_slidingmenu_01);
		rl_slidingmenu_02 = (RelativeLayout) findViewById(R.id.rl_slidingmenu_02);
		rl_slidingmenu_03 = (RelativeLayout) findViewById(R.id.rl_slidingmenu_03);
		rl_slidingmenu_04 = (RelativeLayout) findViewById(R.id.rl_slidingmenu_04);
		rl_slidingmenu_05 = (RelativeLayout) findViewById(R.id.rl_slidingmenu_05);
		rl_slidingmenu_01.setOnClickListener(clickListener);
		rl_slidingmenu_01.setOnClickListener(clickListener);
		rl_slidingmenu_02.setOnClickListener(clickListener);
		rl_slidingmenu_03.setOnClickListener(clickListener);
		rl_slidingmenu_04.setOnClickListener(clickListener);
		rl_slidingmenu_05.setOnClickListener(clickListener);

	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.rl_slidingmenu_01:
				mLeftMenu.closeMenu();
				textView.setText("1");
				break;
			case R.id.rl_slidingmenu_02:
				mLeftMenu.closeMenu();
				textView.setText("2");
				break;
			case R.id.rl_slidingmenu_03:
				mLeftMenu.closeMenu();
				textView.setText("3");
				break;
			case R.id.rl_slidingmenu_04:
				mLeftMenu.closeMenu();
				textView.setText("4");
				break;
			case R.id.rl_slidingmenu_05:
				textView.setText("5");
				mLeftMenu.closeMenu();
				break;

			default:
				break;
			}
		}
	};

	public void toggleMenu(View view) {
		mLeftMenu.toggle();
	}
}
