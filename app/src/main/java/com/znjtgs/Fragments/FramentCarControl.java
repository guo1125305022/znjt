package com.znjtgs.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.znjtgs.Adapter.CarControlAdapter;
import com.znjtgs.R;
import com.znjtgs.entity.BaseCar;
import com.znjtgs.entity.CarControlInfo;
import com.znjtgs.file.FileTools;
import com.znjtgs.request.BaseRequest;
import com.znjtgs.request.RequestCarAction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *
 * @author 郭小浪
 * @version 1.0
 * @since 小车控制
 */
public class FramentCarControl extends BaseFragment implements  BaseRequest.OnResultDataListener, CarControlAdapter.OnListSwitchCheckListener {

    private static final String TAG = "FramentCarControl";
    private RequestCarAction carState;

    public static final String KEY_CONTROL_STATE = "control_state";
    public static  String LOCAD_DATA_PATH ="carControlData.data";
    private ArrayList<CarControlInfo> carControlInfos = new ArrayList<>();
    private CarControlAdapter adapter;
    private ListView listView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LOCAD_DATA_PATH= this.getContext().getFilesDir().toString()+LOCAD_DATA_PATH;
    }

    @Override
    protected int initLayout() {
        return R.layout.my_card_frament_car_control;
    }

    @Override
    protected void initComponent(View root) {
        setTitle("小车控制");
        carState = new RequestCarAction(getActivity());
        carState.setListener(this);
        initLocadData();
        listView= (ListView) root.findViewById(R.id.lv_car_control);
        adapter=new CarControlAdapter(this.getActivity(), carControlInfos);
        adapter.setOnListSwitchCheckListener(this);
        listView.setAdapter(adapter);
    }

    private void initLocadData() {
        carControlInfos.clear();
        String data = FileTools.openFileToString(LOCAD_DATA_PATH);
        try {
            initJsonarrayData(data);
        } catch (JSONException e) {
            Log.i(TAG, "initLocadData: "+e.getMessage());
            for (int i = 0; i < 8; i++) {
                carControlInfos.add(new CarControlInfo(i + 1, false));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initJsonarrayData(String data) throws JSONException {
        JSONArray jsonArray = new JSONArray(data);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int carId = jsonObject.getInt(BaseCar.KEY_CAR_ID);
            boolean state = jsonObject.getBoolean(KEY_CONTROL_STATE);
            carControlInfos.add(new CarControlInfo(carId, state));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        JSONArray jsonArray=new JSONArray();
        for (CarControlInfo info:carControlInfos){
            jsonArray.put(info.toJSONObject());
        }
        FileTools.saveStringToFile(LOCAD_DATA_PATH,jsonArray.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResultData(BaseRequest baseRequest, Object object) {
        Toast.makeText(FramentCarControl.this.getActivity(),
                object.toString(), Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCheck(View v, boolean isUser, boolean isChecked, CarControlInfo controlInfo) {
        carState.setCarId(controlInfo.getCarId());
        carState.setCarAction(isChecked);
        carState.connection();
    }

}
