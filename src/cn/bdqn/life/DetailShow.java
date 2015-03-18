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
import cn.bdqn.life.adapter.DetailShowAdapter;
import cn.bdqn.life.data.DataManager;
import cn.bdqn.life.data.DataManager.Recommend;
import cn.bdqn.life.data.Show;
import cn.bdqn.life.net.ParseJasonDataService;
import cn.bdqn.life.net.URLProtocol;
import cn.bdqn.life.utils.SaveDataUtils;

/** 演出详情 */
public class DetailShow extends Activity implements OnClickListener {

	public static Handler handler;
	private DetailShowAdapter adapter;
	private int type;
	private int which;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailshow_layout);
		TextView headText = (TextView) findViewById(R.id.headtext);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		Button prevButton = (Button) findViewById(R.id.prev);
		Button nextButton = (Button) findViewById(R.id.next);
		Button commButton = (Button) findViewById(R.id.comment);
		Button shareButton = (Button) findViewById(R.id.share);
		Button saveButton = (Button) findViewById(R.id.save);
		prevButton.setOnClickListener(this);
		nextButton.setOnClickListener(this);
		commButton.setOnClickListener(this);
		shareButton.setOnClickListener(this);
		saveButton.setOnClickListener(this);
		which = bundle.getInt("position");
		type = bundle.getInt("type");
		int doWhat = bundle.getInt("do");
		switch (type) {
		case URLProtocol.CMD_CONCERT_DETAIL: {
			headText.setText(R.string.h_play);
			if (doWhat == CollectionActivity.COLLECTION) {
				adapter = new DetailShowAdapter(this,
						(Show) CollectionActivity.objList.get(which),
						URLProtocol.CMD_CONCERT_DETAIL);
			} else {
				adapter = new DetailShowAdapter(this, which,
						URLProtocol.CMD_CONCERT_DETAIL);
			}

		}
			break;
		case URLProtocol.CMD_MUSIC_DETAIL: {
			headText.setText(R.string.music);
			if (doWhat == CollectionActivity.COLLECTION) {
				adapter = new DetailShowAdapter(this,
						(Show) CollectionActivity.objList.get(which),
						URLProtocol.CMD_MUSIC_DETAIL);
			} else {
				adapter = new DetailShowAdapter(this, which, type);
			}

		}
			break;
		case URLProtocol.CMD_PLAY_DETAIL: {
			headText.setText(R.string.play);
			if (doWhat == CollectionActivity.COLLECTION) {
				adapter = new DetailShowAdapter(this,
						(Show) CollectionActivity.objList.get(which),
						URLProtocol.CMD_PLAY_DETAIL);
			} else {
				adapter = new DetailShowAdapter(this, which, type);
			}

		}
			break;
		case URLProtocol.CMD_PEKINGOPERA_DETAIL: {
			headText.setText(R.string.pekingopera);
			if (doWhat == CollectionActivity.COLLECTION) {
				adapter = new DetailShowAdapter(this,
						(Show) CollectionActivity.objList.get(which),
						URLProtocol.CMD_PEKINGOPERA_DETAIL);
			} else {
				adapter = new DetailShowAdapter(this, which, type);
			}

		}
			break;
		default:
			break;
		}

		ListView showDetailList = (ListView) findViewById(R.id.showdetail_list);
		showDetailList.setAdapter(adapter);
		handler = new Handler() {
			public void handleMessage(Message msg) {
				adapter.notifyDataSetChanged();
			}
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.prev: {
			if (type == URLProtocol.CMD_CONCERT_DETAIL) {
				Message msg = new Message();
				msg.what = URLProtocol.CMD_CONCERT_DETAIL;
				Bundle bundle = new Bundle();
				bundle.putInt("position", which - 1);
				bundle.putInt("cid", DataManager.concerts[which - 1].id);
				msg.setData(bundle);
				ParseJasonDataService.handler.sendMessage(msg);
			} else if (type == URLProtocol.CMD_MUSIC_DETAIL) {
				Message msg = new Message();
				msg.what = URLProtocol.CMD_MUSIC_DETAIL;
				Bundle bundle = new Bundle();
				bundle.putInt("position", which - 1);
				bundle.putInt("mid", DataManager.musics[which - 1].id);
				msg.setData(bundle);
				ParseJasonDataService.handler.sendMessage(msg);
			} else if (type == URLProtocol.CMD_PLAY_DETAIL) {
				Message msg = new Message();
				msg.what = URLProtocol.CMD_PLAY_DETAIL;
				Bundle bundle = new Bundle();
				bundle.putInt("position", which - 1);
				bundle.putInt("pid", DataManager.plays[which - 1].id);
				msg.setData(bundle);
				ParseJasonDataService.handler.sendMessage(msg);
			} else {
				Message msg = new Message();
				msg.what = URLProtocol.CMD_PEKINGOPERA_DETAIL;
				Bundle bundle = new Bundle();
				bundle.putInt("position", which - 1);
				bundle.putInt("pid", DataManager.pos[which - 1].id);
				msg.setData(bundle);
				ParseJasonDataService.handler.sendMessage(msg);
			}
		}
			break;
		case R.id.next: {
			if (type == URLProtocol.CMD_CONCERT_DETAIL) {
				Message msg = new Message();
				msg.what = URLProtocol.CMD_CONCERT_DETAIL;
				Bundle bundle = new Bundle();
				bundle.putInt("position", which + 1);
				bundle.putInt("cid", DataManager.concerts[which + 1].id);
				msg.setData(bundle);
				ParseJasonDataService.handler.sendMessage(msg);
			} else if (type == URLProtocol.CMD_MUSIC_DETAIL) {
				Message msg = new Message();
				msg.what = URLProtocol.CMD_MUSIC_DETAIL;
				Bundle bundle = new Bundle();
				bundle.putInt("position", which + 1);
				bundle.putInt("mid", DataManager.musics[which + 1].id);
				msg.setData(bundle);
				ParseJasonDataService.handler.sendMessage(msg);
			} else if (type == URLProtocol.CMD_PLAY_DETAIL) {
				Message msg = new Message();
				msg.what = URLProtocol.CMD_PLAY_DETAIL;
				Bundle bundle = new Bundle();
				bundle.putInt("position", which + 1);
				bundle.putInt("pid", DataManager.plays[which + 1].id);
				msg.setData(bundle);
				ParseJasonDataService.handler.sendMessage(msg);
			} else {
				Message msg = new Message();
				msg.what = URLProtocol.CMD_PEKINGOPERA_DETAIL;
				Bundle bundle = new Bundle();
				bundle.putInt("position", which + 1);
				bundle.putInt("pid", DataManager.pos[which + 1].id);
				msg.setData(bundle);
				ParseJasonDataService.handler.sendMessage(msg);
			}
		}
			break;
		case R.id.comment: {
			Intent intent = new Intent(this, CommentActivity.class);
			intent.putExtra("position", which);
			switch (type) {
			case URLProtocol.CMD_CONCERT_DETAIL: {
				intent.putExtra("type", URLProtocol.CMD_CONCERT_DETAIL);
				intent.putExtra("mid", DataManager.concerts[which].id);
				startActivity(intent);
			}
				break;
			case URLProtocol.CMD_MUSIC_DETAIL: {
				intent.putExtra("type", URLProtocol.CMD_MUSIC_DETAIL);
				intent.putExtra("mid", DataManager.musics[which].id);
				startActivity(intent);
			}
				break;
			case URLProtocol.CMD_PLAY_DETAIL: {
				intent.putExtra("type", URLProtocol.CMD_MUSIC_DETAIL);
				intent.putExtra("mid", DataManager.plays[which].id);
				startActivity(intent);
			}
				break;
			case URLProtocol.CMD_PEKINGOPERA_DETAIL: {
				intent.putExtra("type", URLProtocol.CMD_PEKINGOPERA_DETAIL);
				intent.putExtra("mid", DataManager.pos[which].id);
				startActivity(intent);
			}
				break;
			default:
				break;
			}
		}
			break;
		case R.id.share: {
			Intent intent = new Intent(this, RecommendActivity.class);
			String name = null;
			switch (type) {
			case URLProtocol.CMD_CONCERT_DETAIL: {
				name = DataManager.concerts[which].name;
			}
				break;
			case URLProtocol.CMD_MUSIC_DETAIL: {
				name = DataManager.musics[which].name;
			}
				break;
			case URLProtocol.CMD_PLAY_DETAIL: {
				name = DataManager.plays[which].name;
			}
				break;
			case URLProtocol.CMD_PEKINGOPERA_DETAIL: {
				name = DataManager.pos[which].name;
			}
				break;
			}
			intent.putExtra("name", name);
			startActivity(intent);
		}
			break;
		case R.id.save: {
			Map<String, Object> map = new HashMap<String, Object>();
			String content = null;
			if (type == URLProtocol.CMD_CONCERT_DETAIL) {
				type = URLProtocol.CMD_CONCERT_DETAIL;
				content = DataManager.concerts[which].toString();
			} else if (type == URLProtocol.CMD_MUSIC_DETAIL) {
				type = URLProtocol.CMD_MUSIC_DETAIL;
				content = DataManager.musics[which].toString();
			} else if (type == URLProtocol.CMD_PLAY_DETAIL) {
				type = URLProtocol.CMD_PLAY_DETAIL;
				content = DataManager.plays[which].toString();
			} else {
				type = URLProtocol.CMD_PEKINGOPERA_DETAIL;
				content = DataManager.pos[which].toString();
			}

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
		default:
			break;
		}
	}
}
