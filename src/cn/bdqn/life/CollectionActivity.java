package cn.bdqn.life;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.bdqn.life.data.DataManager;
import cn.bdqn.life.data.DataManager.Concert;
import cn.bdqn.life.data.DataManager.Display;
import cn.bdqn.life.data.DataManager.Music;
import cn.bdqn.life.data.DataManager.Pekingopera;
import cn.bdqn.life.data.DataManager.Play;
import cn.bdqn.life.net.URLProtocol;
import cn.bdqn.life.utils.FileUtil;
import cn.bdqn.life.utils.SaveDataUtils;

/** 收藏 */
public class CollectionActivity extends BaseActivity implements
		OnItemClickListener {
	public final static int COLLECTION = 300;
	private int type;
	public  List<Map<String, Object>> list;
	public static ArrayList<Object> objList = new ArrayList<Object>();
	private SaveDataUtils utils;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collection_layout);
		TextView headText = (TextView) findViewById(R.id.headtext);
		headText.setText(R.string.save);
		listView = (ListView) findViewById(R.id.savelist);
		listView.setOnItemClickListener(this);
		utils = new SaveDataUtils(this);
	}
	
	@Override
	protected void onResume() {
		list = utils.readData();
		listView.setAdapter(adapter);
		super.onResume();
	};

	BaseAdapter adapter = new BaseAdapter() {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ArrayList<Object> arrayList = new ArrayList<Object>();
			LayoutInflater inflater = LayoutInflater
					.from(getApplicationContext());
			convertView = inflater.inflate(R.layout.collection_content, null);
			TextView name = (TextView) convertView
					.findViewById(R.id.collection_name);
			type = (Integer)list.get(position).get("type");
			if (URLProtocol.CMD_FILMDETAIL == type) {
				String content = (String) list.get(position).get("content");
				String[] s = content.split(",");
				for (String str : s) {
					String[] buffer = str.split("=");
					arrayList.add(buffer[1]);
				}
				DataManager.Movie movie = new DataManager.Movie();
				// 电影id
				movie.mid = Integer.valueOf((String) arrayList.get(0));
				// 电影名称
				movie.name = (String) arrayList.get(1);
				// 电影类型
				movie.type = (String) arrayList.get(2);
				// 上映时间
				movie.time = (String) arrayList.get(3);
				// 主演
				movie.player = (String) arrayList.get(4);
				// 海报图片名称
				movie.image = (String) arrayList.get(5);
				// 电影介绍
				movie.desc = (String) arrayList.get(6);
				// 电影时长
				movie.timelong = (String) arrayList.get(7);
				// 海报图片
				if (FileUtil.imageExist(movie.image)) {
					String path = FileUtil.IMAGEDIR + FileUtil.separator
							+ movie.image + ".life";
					movie.icon = BitmapDrawable.createFromPath(path);
				}
				objList.add(movie);
				name.setText("电影:" + movie.name);
			} else if ((URLProtocol.CMD_FILM_WILL_DETAIL == type)) {
				String content = (String) list.get(position).get("content");
				String[] s = content.split(",");
				for (String str : s) {
					String[] buffer = str.split("=");
					arrayList.add(buffer[1]);
				}
				DataManager.WillMovie wMovie = new DataManager.WillMovie();
				wMovie.mid = Integer.valueOf((String) arrayList.get(0));
				wMovie.name = (String) arrayList.get(1);
				wMovie.type = (String) arrayList.get(2);
				wMovie.time = (String) arrayList.get(3);
				wMovie.player = (String) arrayList.get(4);
				wMovie.image = (String) arrayList.get(5);
				wMovie.desc = (String) arrayList.get(6);
				wMovie.timelong = (String) arrayList.get(7);
				if (FileUtil.imageExist(wMovie.image)) {
					String path = FileUtil.IMAGEDIR + FileUtil.separator
							+ wMovie.image + ".life";
					wMovie.icon = BitmapDrawable.createFromPath(path);
				}
				objList.add(wMovie);
				name.setText("电影:" + wMovie.name);
			} else if (URLProtocol.CMD_DISPLAY_DETAIL == type) {
				String content = (String) list.get(position).get("content");
				String[] s = content.split(",");
				for (String str : s) {
					String[] buffer = str.split("=");
					arrayList.add(buffer[1]);
				}
				DataManager.Display display = new Display();
				display.did = Integer.valueOf((String) arrayList.get(0));
				display.name = (String) arrayList.get(1);
				display.addr = (String) arrayList.get(2);
				display.time = (String) arrayList.get(3);
				display.image = (String) arrayList.get(4);
				display.call = (String) arrayList.get(5);
				display.host = (String) arrayList.get(6);
				display.desc = (String) arrayList.get(7);
				if (FileUtil.imageExist(display.image)) {
					String path = FileUtil.IMAGEDIR + FileUtil.separator
							+ display.image + ".life";
					display.icon = BitmapDrawable.createFromPath(path);
				}
				objList.add(display);
				name.setText("展览:" + display.name);
			} else if (URLProtocol.CMD_CONCERT_DETAIL == type) {
				String content = (String) list.get(position).get("content");
				String[] s = content.split(",");
				for (String str : s) {
					String[] buffer = str.split("=");
					arrayList.add(buffer[1]);
				}
				DataManager.Concert concert = new Concert();
				concert.name = (String) arrayList.get(0);
				concert.time = (String) arrayList.get(1);
				concert.addr = (String) arrayList.get(3);
				concert.id = Integer.valueOf((String) arrayList.get(4));
				concert.image = (String) arrayList.get(5);
				concert.call = (String) arrayList.get(6);
				concert.mapx = (String) arrayList.get(7);
				concert.mapy = (String) arrayList.get(8);
				concert.price = (String) arrayList.get(9);
				concert.decs = (String) arrayList.get(10);
				if (FileUtil.imageExist(concert.image)) {
					String path = FileUtil.IMAGEDIR + FileUtil.separator
							+ concert.image + ".life";
					concert.icon = BitmapDrawable.createFromPath(path);
				}
				objList.add(concert);
				name.setText("演唱会:" + concert.name);
			} else if (URLProtocol.CMD_MUSIC_DETAIL == type) {
				String content = (String) list.get(position).get("content");
				String[] s = content.split(",");
				for (String str : s) {
					String[] buffer = str.split("=");
					arrayList.add(buffer[1]);
				}
				DataManager.Music music = new Music();
				music.name = (String) arrayList.get(0);
				music.time = (String) arrayList.get(1);
				music.addr = (String) arrayList.get(3);
				music.id = Integer.valueOf((String) arrayList.get(4));
				music.image = (String) arrayList.get(5);
				music.call = (String) arrayList.get(6);
				music.mapx = (String) arrayList.get(7);
				music.mapy = (String) arrayList.get(8);
				music.price = (String) arrayList.get(9);
				music.decs = (String) arrayList.get(10);
				if (FileUtil.imageExist(music.image)) {
					String path = FileUtil.IMAGEDIR + FileUtil.separator
							+ music.image + ".life";
					music.icon = BitmapDrawable.createFromPath(path);
				}
				objList.add(music);
				name.setText("音乐会:" + music.name);
			} else if (URLProtocol.CMD_PLAY_DETAIL == type) {
				String content = (String) list.get(position).get("content");
				String[] s = content.split(",");
				for (String str : s) {
					String[] buffer = str.split("=");
					arrayList.add(buffer[1]);
				}
				DataManager.Play play = new Play();
				play.name = (String) arrayList.get(0);
				play.time = (String) arrayList.get(1);
				play.addr = (String) arrayList.get(3);
				play.id = Integer.valueOf((String) arrayList.get(4));
				play.image = (String) arrayList.get(5);
				play.call = (String) arrayList.get(6);
				play.mapx = (String) arrayList.get(7);
				play.mapy = (String) arrayList.get(8);
				play.price = (String) arrayList.get(9);
				play.decs = (String) arrayList.get(10);
				if (FileUtil.imageExist(play.image)) {
					String path = FileUtil.IMAGEDIR + FileUtil.separator
							+ play.image + ".life";
					play.icon = BitmapDrawable.createFromPath(path);
				}
				objList.add(play);
				name.setText("演唱会:" + play.name);
			} else if (URLProtocol.CMD_PEKINGOPERA_DETAIL == type) {
				String content = (String)list.get(position).get("content");
				String[] s = content.split(",");
				for (String str : s) {
					String[] buffer = str.split("=");
					arrayList.add(buffer[1]);
				}
				DataManager.Pekingopera pko = new Pekingopera();
				pko.name = (String) arrayList.get(0);
				pko.time = (String) arrayList.get(1);
				pko.addr = (String) arrayList.get(3);
				pko.id = Integer.valueOf((String) arrayList.get(4));
				pko.image = (String) arrayList.get(5);
				pko.call = (String) arrayList.get(6);
				pko.mapx = (String) arrayList.get(7);
				pko.mapy = (String) arrayList.get(8);
				pko.price = (String) arrayList.get(9);
				pko.decs = (String) arrayList.get(10);
				if (FileUtil.imageExist(pko.image)) {
					String path = FileUtil.IMAGEDIR + FileUtil.separator
							+ pko.image + ".life";
					pko.icon = BitmapDrawable.createFromPath(path);
				}
				objList.add(pko);
				name.setText("演唱会:" + pko.name);
			}
			return convertView;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public int getCount() {
			return list.size();
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Bundle bundle = new Bundle();
		Intent intent = null;
		int type = (Integer) list.get(position).get("type");
		bundle.putInt("position", position);
		bundle.putInt("type", type);
		bundle.putInt("do", COLLECTION);
		readComm(position);
		if (type == URLProtocol.CMD_FILM_WILL_DETAIL
				|| type == URLProtocol.CMD_FILMDETAIL) {
			intent = new Intent(this, DetailMovie.class);
		} else if (type == URLProtocol.CMD_DISPLAY_DETAIL) {
			intent = new Intent(this, DisplayDetail.class);
		} else if (type == URLProtocol.CMD_CONCERT_DETAIL
				|| type == URLProtocol.CMD_MUSIC_DETAIL
				|| type == URLProtocol.CMD_PLAY_DETAIL
				|| type == URLProtocol.CMD_PEKINGOPERA_DETAIL) {
			intent = new Intent(this, DetailShow.class);
		}

		intent.putExtras(bundle);
		startActivity(intent);

	}

	private void readComm(int position) {
		String comment = (String) list.get(position).get("comment");
		if (null != comment && !"".equals(comment.trim())) {
			ArrayList<DataManager.Recommend> recList = new ArrayList<DataManager.Recommend>();
			DataManager.Recommend recommend = null;
			String[] s = comment.split("]");
			for (String str : s) {
				recommend = new DataManager.Recommend();
				String[] buffer = str.split(",");
				ArrayList<String> list = new ArrayList<String>();
				for (String strBuffer : buffer) {
					String[] result = strBuffer.split("=");
					if (result.length == 2) {
						list.add(result[1]);
					} else {
						list.add(" ");
					}
				}
				recommend.name = list.get(0);
				recommend.time = list.get(1);
				recommend.content = list.get(2);
				recList.add(recommend);
			}
			int len = recList.size();
			DataManager.recoms = new DataManager.Recommend[len];
			for (int i = 0; i < len; i++) {
				DataManager.recoms[i] = recList.get(i);
			}
		} else {
			DataManager.recoms = new DataManager.Recommend[0];
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			// 退出
			showExitDialog(this);
		}
		return true;
	}
}
