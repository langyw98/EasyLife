package cn.bdqn.life;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.bdqn.life.adapter.SeeDisplayAdapter;
import cn.bdqn.life.data.DataManager;
import cn.bdqn.life.data.DataManager.Display;
import cn.bdqn.life.net.ParseJasonDataService;
import cn.bdqn.life.net.URLProtocol;
import cn.bdqn.life.utils.ScreenUtil;

/** ø¥’π¿¿ */
public class SeeDisplayActivity extends BaseActivity implements
		OnItemClickListener {
	private TextView headText;
	private ListView displayList;
	public static Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_layout);
		headText = (TextView) findViewById(R.id.headtext);
		displayList = (ListView) findViewById(R.id.display_list);
		headText.setText(R.string.h_display);
		SeeDisplayAdapter<Display> adapter = new SeeDisplayAdapter<DataManager.Display>(
				this, DataManager.displays);
		displayList.setAdapter(adapter);
		displayList.setOnItemClickListener(this);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				ScreenUtil.hideLoading();
				Bundle bundle = msg.getData();
				int code = bundle.getInt(ParseJasonDataService.HTTP_CODE);
				int position = bundle.getInt("position");
				if (0 == code) {
					Intent intent = new Intent(SeeDisplayActivity.this,
							DisplayDetail.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("position", position);
					startActivity(intent);
				}

			}
		};
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ScreenUtil.showProgressDialog(this);
		int did = DataManager.displays[position].did;
		Message msg = new Message();
		msg.what = URLProtocol.CMD_DISPLAY_DETAIL;
		Bundle b = new Bundle();
		b.putInt("position", position);
		b.putInt("did", did);
		msg.setData(b);
		ParseJasonDataService.handler.sendMessage(msg);
	}
}
