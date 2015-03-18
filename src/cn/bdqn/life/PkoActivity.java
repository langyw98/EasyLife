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
 *Ï·Çú 
 */
public class PkoActivity extends Activity implements OnItemClickListener {
	
	public static Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pko_layout);
		ListView pkoList = (ListView) findViewById(R.id.pkolist);
		ShowAdapter adapter = new ShowAdapter(this, DataManager.pos);
		pkoList.setAdapter(adapter);
		pkoList.setOnItemClickListener(this);
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				Bundle bundle = msg.getData();
				bundle.putInt("type", URLProtocol.CMD_PEKINGOPERA_DETAIL);
				Intent intent = new Intent(PkoActivity.this, DetailShow.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		};
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int pid = DataManager.pos[position].id;
		Message msg = new Message();
		Bundle bundle = new Bundle();
		bundle.putInt("pid", pid);
		bundle.putInt("position", position);
		msg.setData(bundle);
		msg.what = URLProtocol.CMD_PEKINGOPERA_DETAIL;
		ParseJasonDataService.handler.sendMessage(msg);
	}
}
