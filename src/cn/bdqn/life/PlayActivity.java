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
/*
 * »°¾ç
 */
public class PlayActivity extends Activity implements OnItemClickListener{
	
	public static Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paly_layout);
		ListView playList = (ListView) findViewById(R.id.playlist);
		playList.setOnItemClickListener(this);
		ShowAdapter adapter = new ShowAdapter(this, DataManager.plays);
		playList.setAdapter(adapter);
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				bundle.putInt("type", URLProtocol.CMD_PLAY_DETAIL);
				Intent intent = new Intent(PlayActivity.this, DetailShow.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		};
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int pid = DataManager.plays[position].id;
		Message msg = new Message();
		Bundle bundle = new Bundle();
		bundle.putInt("pid", pid);
		bundle.putInt("position", position);
		msg.setData(bundle);
		msg.what = URLProtocol.CMD_PLAY_DETAIL;
		ParseJasonDataService.handler.sendMessage(msg);
	}
}
