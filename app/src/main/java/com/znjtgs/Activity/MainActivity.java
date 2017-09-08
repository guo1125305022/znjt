package com.znjtgs.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.gg.slidingmenu.SlidingMenu;
import com.znjtgs.Adapter.SlidingMenuListAdapter;
import com.znjtgs.Fragments.FragmentEnvorMentStanden;
import com.znjtgs.R;
import com.znjtgs.entity.SlidingMenuListAdapterItem;
import com.znjtgs.server.BusCapServer;
import com.znjtgs.ulits.UserManager;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private FragmentTabHost fragmentTabHost;
    private LayoutInflater layoutInflater;
    private ListView listView;
    private SlidingMenuListAdapter slidingMenuListAdapter;
    private SlidingMenu menu;

    @Override
    protected int initLayouotId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent() {
        layoutInflater = getLayoutInflater();
        startService(new Intent(this, BusCapServer.class));
        initSlidingMenu();
        initMainContent();
    }

    private void initMainContent() {
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.fl_main_content);
        TabHost.TabSpec tabSpec0 = getTabSpec(R.string.text_mian_ui_one);
        fragmentTabHost.addTab(tabSpec0, FragmentEnvorMentStanden.class, null);

    }

    private TabHost.TabSpec getTabSpec(int strId) {
        TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(strId + ":" + R.layout.activity_main_tabhost_title_view);
        tabSpec.setIndicator(getTabHostItemView(R.layout.activity_main_tabhost_title_view, strId));
        return tabSpec;
    }

    private View getTabHostItemView(int layoutId, int strId) {
        View inflate = layoutInflater.inflate(layoutId, null);
        TextView textView = (TextView) inflate;
        textView.setText(strId);
        return inflate;
    }


    private void initSlidingMenu() {

        // configure the SlidingMenu
        menu = new SlidingMenu(this);
        //设置滑动菜单在左边还是右边
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        //TOUCHMODE_FULLSCREEN 全屏模式，在content页面中，滑动，可以打开sliding menu
        //TOUCHMODE_MARGIN 边缘模式，在content页面中，如果想打开slding ,你需要在屏幕边缘滑动才可以打开slding menu
        //TOUCHMODE_NONE 自然是不能通过手势打开啦
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);


        menu.setShadowWidth(300);
        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.main_layout_left_menu);
        listView = (ListView) menu.findViewById(R.id.lv_main_menu_list);

        slidingMenuListAdapter = new SlidingMenuListAdapter(this, getListItems());
        listView.setAdapter(slidingMenuListAdapter);
        listView.setOnItemClickListener(this);
    }

    private ArrayList<SlidingMenuListAdapterItem> getListItems() {
        ArrayList<SlidingMenuListAdapterItem> slidingMenuListAdapterItems = new ArrayList<>();
        slidingMenuListAdapterItems.add(new SlidingMenuListAdapterItem(null,"注销用户"));
        slidingMenuListAdapterItems.add(new SlidingMenuListAdapterItem(MyAccountActivity.class, "我的账号"));
        slidingMenuListAdapterItems.add(new SlidingMenuListAdapterItem(MyCardActivity.class, "我的座驾"));
        slidingMenuListAdapterItems.add(new SlidingMenuListAdapterItem(MyRoadActivity.class, "我的路况"));
        slidingMenuListAdapterItems.add(new SlidingMenuListAdapterItem(ParkQueryActivity.class, "停车查询"));
        slidingMenuListAdapterItems.add(new SlidingMenuListAdapterItem(BusQueryActivity.class, "公交查询"));
        //  slidingMenuListAdapterItems.add(new SlidingMenuListAdapterItem(RoadEnvirActivity.class, "道路查询"));
        slidingMenuListAdapterItems.add(new SlidingMenuListAdapterItem(CarLimitActivity.class, "车辆限行"));
        slidingMenuListAdapterItems.add(new SlidingMenuListAdapterItem(CarRechagerHistoryActivity.class, "充值历史"));
        slidingMenuListAdapterItems.add(new SlidingMenuListAdapterItem(RoadConditionQueryActivity.class, "路况查询"));
        slidingMenuListAdapterItems.add(new SlidingMenuListAdapterItem(CarParkHistoryActivity.class, "停车日志"));
//        slidingMenuListAdapterItems.add(new SlidingMenuListAdapterItem(Activity.class, "创意设计"));
        slidingMenuListAdapterItems.add(new SlidingMenuListAdapterItem(ActivityRedGreeLightManager.class, "红绿灯管理"));
        slidingMenuListAdapterItems.add(new SlidingMenuListAdapterItem(ActivityAllTest.class, "综合测试"));

        return slidingMenuListAdapterItems;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){//用户注销实现
            UserManager.getUserManager().clear();//清空用户信息
            this.finish();//关闭当前activity
            startActivity(new Intent(this,LoginActivity.class));//启动登录界面
            return;
        }
        SlidingMenuListAdapterItem slidingMenuListAdapterItem = slidingMenuListAdapter.getItem(position);
        startActivity(new Intent(this, slidingMenuListAdapterItem.getActivity()));
    }

    private long firstClick = 0;

    @Override
    public void activityBackClick() {
        menu.toggle();
    }

    /**
     * 双击退出实现
     */
    @Override
    public void onBackPressed() {
        long secondClick = System.currentTimeMillis();
        if (secondClick - firstClick > 2000) {
            firstClick = secondClick;
            Toast.makeText(this, "在按一次退出！", Toast.LENGTH_SHORT).show();
            return;
        }
        System.exit(0);
    }
}
