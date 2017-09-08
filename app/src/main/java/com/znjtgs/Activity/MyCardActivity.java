package com.znjtgs.Activity;

import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;


import com.znjtgs.Fragments.FramentCarControl;
import com.znjtgs.Fragments.CarRechagerFrament;
import com.znjtgs.Fragments.CarSpeedFrament;
import com.znjtgs.R;

import java.util.ArrayList;

public class MyCardActivity extends BaseActivity implements OnTabChangeListener {

	private FragmentTabHost tabHost;

	@Override
	protected int initLayouotId() {
		return R.layout.my_card_frament_root;
	}

	@Override
	protected void initComponent() {
//		setMainTitle("控制");
		initTabHost();
	}




	private void initTabHost() {

		tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(),
				R.id.fl_main_mycard_view);
		String[] tags = { "控制", "状态", "账户" };

//		setMainTitle(tags[0]);
		View[] views = new View[3];

		ArrayList<Class> list = new ArrayList<Class>();
		list.add(FramentCarControl.class);
		list.add(CarSpeedFrament.class);
		list.add(CarRechagerFrament.class);

		for (int i = 0; i < views.length; i++) {
			views[i] = getTabHostItem(tags[i]);
			TabSpec ts = tabHost.newTabSpec(tags[i]);
			ts.setIndicator(views[i]);
			tabHost.addTab(ts, list.get(i), null);
		}
		tabHost.setOnTabChangedListener(this);
	}

	private View getTabHostItem(String text) {
		View v = getLayoutInflater().inflate(R.layout.my_car_table_item_layout, null);
		TextView tv = (TextView) v
				.findViewById(R.id.my_car_table_item_title_name);
		tv.setText(text);
		return v;
	}

	@Override
	public void onTabChanged(String arg0) {

//		setMainTitle(arg0);
	}

}
