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
 * “Ù¿÷ª·
 */
public class MusicActivity extends Activity implements OnItemClickListener {
	
	public static Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_layout);
		ListView musicList = (ListView) findViewById(R.id.musiclist);
		ShowAdapter adapter = new ShowAdapter(this, DataManager.musics);
		musicList.setAdapter(adapter);
		musicList.setOnItemClickListener(this);
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				bundle.putInt("type", URLProtocol.CMD_MUSIC_DETAIL);
				Intent intent = new Intent(MusicActivity.this, DetailShow.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		};
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int mid = DataManager.musics[position].id;
		Message msg = new Message();
		Bundle bundle = new Bundle();
		bundle.putInt("mid", mid);
		bundle.putInt("position", position);
		msg.setData(bundle);
		msg.what = URLProtocol.CMD_MUSIC_DETAIL;
		ParseJasonDataService.handler.sendMessage(msg);
	}
}
