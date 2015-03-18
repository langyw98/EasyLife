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
import cn.bdqn.life.adapter.ShowAdapter;
import cn.bdqn.life.data.DataManager;
import cn.bdqn.life.net.ParseJasonDataService;
import cn.bdqn.life.net.URLProtocol;

public class ConcertActivity extends Activity implements OnItemClickListener {
	private ListView concertView;
	public static Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.concert_layout);
		concertView = (ListView) findViewById(R.id.concertlist);
		ShowAdapter adapter = new ShowAdapter(this, DataManager.concerts);
		concertView.setAdapter(adapter);
		concertView.setOnItemClickListener(this);
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				bundle.putInt("type", URLProtocol.CMD_CONCERT_DETAIL);
				Intent intent = new Intent(ConcertActivity.this, DetailShow.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		};
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Message msg = new Message();
		int cid = DataManager.concerts[position].id;
		Bundle bundle = new Bundle();
		bundle.putInt("cid", cid);
		bundle.putInt("position", position);
		msg.setData(bundle);
		msg.what = URLProtocol.CMD_CONCERT_DETAIL;
		ParseJasonDataService.handler.sendMessage(msg);
	}
}
