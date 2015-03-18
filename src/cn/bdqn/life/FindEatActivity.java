package cn.bdqn.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.bdqn.life.adapter.EatAdapter;
import cn.bdqn.life.data.DataManager;
import cn.bdqn.life.net.ParseJasonDataService;
import cn.bdqn.life.net.URLProtocol;
import cn.bdqn.life.utils.ScreenUtil;

/**’“√¿ ≥*/
public class FindEatActivity extends Activity implements OnItemClickListener{
	
	private TextView titleText;
	private ListView eatList;
	private int did = 0;
	public static Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.find_eat);
		titleText = (TextView) findViewById(R.id.headtext);
		titleText.setText(R.string.h_eat);
		eatList = (ListView) findViewById(R.id.eat_list);
		EatAdapter adapter = new EatAdapter(this, DataManager.delicacies);
		eatList.setAdapter(adapter);
		eatList.setOnItemClickListener(this);
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				ScreenUtil.hideLoading();
				Bundle result = msg.getData();
				int position = result.getInt("position");
				Intent intent = new Intent(FindEatActivity.this, Delicaciesdetail.class);
				intent.putExtra("position", position);
				startActivity(intent);
			}
		};
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		ScreenUtil.showProgressDialog(this);
		did = DataManager.delicacies[position].did;
		Message msg = new Message();
		msg.what = URLProtocol.CMD_DELICACIES_DETAIL;
		Bundle bundle = new Bundle();
		bundle.putInt("did", did);
		bundle.putInt("position", position);
		msg.setData(bundle);
		ParseJasonDataService.handler.sendMessage(msg);
	}
}
