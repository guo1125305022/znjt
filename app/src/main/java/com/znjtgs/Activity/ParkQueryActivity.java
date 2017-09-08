package com.znjtgs.Activity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.znjtgs.R;
import com.znjtgs.request.BaseRequest;
import com.znjtgs.request.RequestParkCSetRATE;
import com.znjtgs.request.RequestParkCarLoaction;
import com.znjtgs.request.RequestParkCurrRATE;

public class ParkQueryActivity extends BaseActivity implements OnClickListener, BaseRequest.OnResultDataListener {

	private EditText et_set_park_money;// 编辑停车费用率


	private ImageView[] parks = new ImageView[2];

	private TextView tv_query_curr_park_mone;
	private RequestParkCarLoaction carLoaction;
	private RequestParkCurrRATE parkCurrRATE;
	private RequestParkCSetRATE parkCSetRATE;


	@Override
	protected int initLayouotId() {
		return R.layout.park_query_activity_layout;
	}

	@Override
	protected void initComponent() {
		setMainTitle("停车查询");
		carLoaction = new RequestParkCarLoaction(this);
		carLoaction.setListener(this);
		parkCurrRATE=new RequestParkCurrRATE(this);
		parkCurrRATE.setListener(this);
		parkCSetRATE=new RequestParkCSetRATE(this);
		parkCSetRATE.setListener(this);
		findViewById(R.id.btn_set_park_curr_money).setOnClickListener(this);
		findViewById(R.id.btn_query_park_curr_money).setOnClickListener(this);
		findViewById(R.id.btn_query_park_kongxianchewei).setOnClickListener(
				this);
		et_set_park_money = (EditText) findViewById(R.id.et_set_park_money);

		parks[0] = (ImageView) findViewById(R.id.iv_park_loaction_1);
		parks[1] = (ImageView) findViewById(R.id.iv_park_loaction_2);

//		tv_query_park_kongxianchewei = (TextView) findViewById(R.id.tv_query_park_kongxianchewei);
		tv_query_curr_park_mone = (TextView) findViewById(R.id.tv_query_park_curr_money);
//		tv_set_patk_money = (TextView) findViewById(R.id.tv_set_park_curr_money);
	}



	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.btn_query_park_curr_money:// 查询当前停车费用率
				parkCurrRATE.connection();
				break;
			case R.id.btn_set_park_curr_money:// 设置停车费用率
				setParkRate();

				break;
			case R.id.btn_query_park_kongxianchewei:// 查询空闲停车位

				carLoaction.connection();// 查询

				break;
			default:
				break;
		}

	}



	private void setParkRate() {

		int money;
		try {
			money = Integer.parseInt(et_set_park_money.getText().toString());
		} catch (Exception e) {
			Toast.makeText(this, "你设置的停车费率错误，停车费率只能是整数", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		parkCSetRATE.setMoney(money);
		parkCSetRATE.connection();
	}





	@Override
	public void onResultData(BaseRequest baseRequest, Object object) {
		if(baseRequest==carLoaction){
			int[] ints= (int[]) object;
			if(ints==null){
				return;
			}

			for (ImageView iv : parks) {
				iv.setImageResource(R.mipmap.free_location);
			}
			for(int i:ints){
				parks[i-1].setImageResource(R.mipmap.location);
			}

		}
		if(baseRequest==parkCurrRATE){
			tv_query_curr_park_mone.setText(object.toString());
		}
		if(baseRequest==parkCSetRATE){
			Toast.makeText(ParkQueryActivity.this, object.toString(),
					Toast.LENGTH_SHORT).show();
		}
	}
}
