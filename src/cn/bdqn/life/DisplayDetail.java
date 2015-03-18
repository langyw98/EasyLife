package cn.bdqn.life;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bdqn.life.adapter.DisplayDetailAdapter;
import cn.bdqn.life.data.DataManager;
import cn.bdqn.life.data.DataManager.Recommend;
import cn.bdqn.life.net.ParseJasonDataService;
import cn.bdqn.life.net.URLProtocol;
import cn.bdqn.life.utils.SaveDataUtils;
import cn.bdqn.life.utils.ScreenUtil;

public class DisplayDetail extends Activity implements OnClickListener {

	public static Handler handler;
	private DisplayDetailAdapter adapter;
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displaydetail_layout);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		position = bundle.getInt("position", 0);
		int doWhat = bundle.getInt("do");
		Button preButton = (Button) findViewById(R.id.prev);
		preButton.setOnClickListener(this);
		Button nextButton = (Button) findViewById(R.id.next);
		nextButton.setOnClickListener(this);
		Button commButton = (Button) findViewById(R.id.comment);
		commButton.setOnClickListener(this);
		Button shareButton = (Button) findViewById(R.id.share);
		shareButton.setOnClickListener(this);
		Button saveButton = (Button) findViewById(R.id.save);
		saveButton.setOnClickListener(this);
		TextView headText = (TextView) findViewById(R.id.headtext);
		headText.setText(R.string.h_display);
		ListView displayDetailList = (ListView) findViewById(R.id.displaydetail_list);
		if (doWhat == CollectionActivity.COLLECTION) {
			adapter = new DisplayDetailAdapter(this,
					(DataManager.Display) (CollectionActivity.objList.get(position)),
					DataManager.recoms);
		} else {
			adapter = new DisplayDetailAdapter(this,
					DataManager.displays[position], DataManager.recoms);
			if (0 == position) {
				preButton.setClickable(false);
			} else if (position == DataManager.displays.length - 1) {
				nextButton.setClickable(false);
			}
		}

		displayDetailList.setAdapter(adapter);
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				adapter.notifyDataSetChanged();
			}
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.prev: {
			if(null != DataManager.displays){
				ScreenUtil.showProgressDialog(this);
				Message msg = new Message();
				msg.what = URLProtocol.CMD_DISPLAY_DETAIL;
				Bundle bundle = new Bundle();
				bundle.putInt("position", position - 1);
				bundle.putInt("did", DataManager.displays[position - 1].did);
				msg.setData(bundle);
				ParseJasonDataService.handler.sendMessage(msg);
			}
			
		}
			break;
		case R.id.next: {
			if(null != DataManager.displays){
				ScreenUtil.showProgressDialog(this);
				Message msg = new Message();
				msg.what = URLProtocol.CMD_DISPLAY_DETAIL;
				Bundle bundle = new Bundle();
				bundle.putInt("position", position + 1);
				bundle.putInt("did", DataManager.displays[position + 1].did);
				msg.setData(bundle);
				ParseJasonDataService.handler.sendMessage(msg);
			}
		}
			break;
		case R.id.comment: {
			Intent intent = new Intent(DisplayDetail.this,
					CommentActivity.class);
			intent.putExtra("position", position);
			intent.putExtra("type", URLProtocol.CMD_DISPLAY_DETAIL);
			intent.putExtra("mid", DataManager.displays[position].did);
			startActivity(intent);
		}
			break;
		case R.id.save: {
			Map<String, Object> map = new HashMap<String, Object>();
			int type = URLProtocol.CMD_DISPLAY_DETAIL;
			String content = DataManager.displays[position].toString();
			StringBuilder builder = new StringBuilder();
			for (Recommend r : DataManager.recoms) {
				builder.append(r.toString());
			}
			String comment = builder.toString();
			map.put("content", content);
			map.put("type", type);
			map.put("comment", comment);
			SaveDataUtils utils = new SaveDataUtils(this);
			utils.saveData(map);
			Toast.makeText(this, "收藏成功", Toast.LENGTH_LONG).show();
		}
			break;
		// 分享
		case R.id.share: {
			Intent intent = new Intent(this, RecommendActivity.class);
			String name = DataManager.displays[position].name;
			intent.putExtra("name", name);
			startActivity(intent);
		}
			break;
		default:
			break;
		}

	}
}
