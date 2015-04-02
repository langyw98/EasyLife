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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bdqn.life.adapter.MovieDetialAdapter;
import cn.bdqn.life.data.DataManager;
import cn.bdqn.life.data.DataManager.Movie;
import cn.bdqn.life.data.DataManager.Recommend;
import cn.bdqn.life.data.DataManager.WillMovie;
import cn.bdqn.life.net.ParseJasonDataService;
import cn.bdqn.life.net.URLProtocol;
import cn.bdqn.life.utils.SaveDataUtils;
import cn.bdqn.life.utils.ScreenUtil;

/** 电影详情 */
public class DetailMovie extends Activity implements OnItemClickListener,
		OnClickListener {

	private int mid = 0;
	private int from = 0;
	public static Handler handler;
	private ListView detailList;
	private int position;
	private MovieDetialAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_detail_layout);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		from = bundle.getInt("from");
		position = bundle.getInt("position");
		int doWhat = bundle.getInt("do");
		
		Button prevButton = (Button) findViewById(R.id.prev);
		prevButton.setOnClickListener(this);

		Button commentButton = (Button) findViewById(R.id.comment);
		commentButton.setOnClickListener(this);
		Button shareButton = (Button) findViewById(R.id.share);
		shareButton.setOnClickListener(this);
		Button saveButton = (Button) findViewById(R.id.save);
		saveButton.setOnClickListener(this);
		Button nextButton = (Button) findViewById(R.id.next);
		nextButton.setOnClickListener(this);
		TextView headText = (TextView) findViewById(R.id.headtext);
		headText.setText(R.string.moviedetail);
		detailList = (ListView) findViewById(R.id.moviedetail_list);
		detailList.setOnItemClickListener(this);
		if(CollectionActivity.COLLECTION == doWhat){
			int position = bundle.getInt("position");
			int type = bundle.getInt("type");
			if(URLProtocol.CMD_FILMDETAIL == type){
				DataManager.Movie movie = (Movie)CollectionActivity.objList.get(position);
				adapter = new MovieDetialAdapter(DetailMovie.this, movie,
						URLProtocol.CMD_FILMDETAIL);
				detailList.setAdapter(adapter);
			}else{
				DataManager.WillMovie movie = (WillMovie)CollectionActivity.objList.get(position);
				adapter = new MovieDetialAdapter(DetailMovie.this, movie,
						URLProtocol.CMD_FILM_WILL_DETAIL);
				detailList.setAdapter(adapter);
			}
		}else if (0 == from) {
			if (position == 0) {
				prevButton.setClickable(false);
			} else if (position == DataManager.movies.length - 1) {
				nextButton.setClickable(false);
			}
			adapter = new MovieDetialAdapter(DetailMovie.this, position,
					URLProtocol.CMD_FILM_WILL_DETAIL);
			detailList.setAdapter(adapter);
		} else {
			if (position == 0) {
				prevButton.setClickable(false);
			} else if (position == DataManager.willmovies.length - 1) {
				nextButton.setClickable(false);
			}
			adapter = new MovieDetialAdapter(DetailMovie.this, position,
					URLProtocol.CMD_FILMDETAIL);
			detailList.setAdapter(adapter);
		}
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MovieDetialAdapter.REFRESH: {
					adapter.notifyDataSetChanged();
				}
					break;
				case CollectionActivity.COLLECTION: {
					Bundle bundle = msg.getData();
					int position = bundle.getInt("position");
					int type = bundle.getInt("type");
					if(URLProtocol.CMD_FILMDETAIL == type){
						DataManager.Movie movie = (Movie)CollectionActivity.objList.get(position);
						adapter = new MovieDetialAdapter(DetailMovie.this, movie,
								URLProtocol.CMD_FILM_WILL_DETAIL);
						detailList.setAdapter(adapter);
					}
					
				}
					break;
				}
			}
		};

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Message msg = new Message();
		if (0 == from) {
			msg.what = URLProtocol.CMD_FILMDETAIL;
		} else {
			msg.what = URLProtocol.CMD_FILM_WILL_DETAIL;
		}
		Bundle bundle = msg.getData();
		bundle.putInt("mid", mid);
		ParseJasonDataService.handler.sendMessage(msg);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.prev: {
			ScreenUtil.showProgressDialog(this);
			if (1 == from) {
				Message msg = new Message();
				msg.what = URLProtocol.CMD_FILMDETAIL;
				Bundle bundle = new Bundle();
				bundle.putInt("position", position - 1);
				bundle.putInt("mid", DataManager.movies[position - 1].mid);
				msg.setData(bundle);
				ParseJasonDataService.handler.sendMessage(msg);
			} else {
				Message msg = new Message();
				msg.what = URLProtocol.CMD_FILM_WILL_DETAIL;
				Bundle bundle = new Bundle();
				bundle.putInt("position", position - 1);
				bundle.putInt("mid", DataManager.willmovies[position - 1].mid);
				msg.setData(bundle);
				ParseJasonDataService.handler.sendMessage(msg);
			}
		}
			break;
		case R.id.next: {
			ScreenUtil.showProgressDialog(this);
			if (1 == from) {
				Message msg = new Message();
				msg.what = URLProtocol.CMD_FILMDETAIL;
				Bundle bundle = new Bundle();
				bundle.putInt("position", position + 1);
				bundle.putInt("mid", DataManager.movies[position + 1].mid);
				msg.setData(bundle);
				ParseJasonDataService.handler.sendMessage(msg);
			} else {
				Message msg = new Message();
				msg.what = URLProtocol.CMD_FILM_WILL_DETAIL;
				Bundle bundle = new Bundle();
				bundle.putInt("position", position + 1);
				bundle.putInt("mid", DataManager.willmovies[position + 1].mid);
				msg.setData(bundle);
				ParseJasonDataService.handler.sendMessage(msg);
			}
		}
			break;
		case R.id.comment: {
			Intent intent = new Intent(DetailMovie.this, CommentActivity.class);
			intent.putExtra("position", position);
			if (0 == from) {
				intent.putExtra("type", URLProtocol.CMD_FILM_WILL);
				intent.putExtra("mid", DataManager.willmovies[position].mid);
			} else {
				intent.putExtra("type", URLProtocol.CMD_FILMDETAIL);
				intent.putExtra("mid", DataManager.movies[position].mid);
			}
			startActivity(intent);
		}
			break;
		case R.id.save: {
			Map<String, Object> map = new HashMap<String, Object>();
			String content = null;
			int type = 0;
			if(0 == from){
				// 即将上映的电影详情类型
				type = URLProtocol.CMD_FILM_WILL_DETAIL;
				 // DataManager中存储即将上映电影详情对象的字符串
				content = DataManager.willmovies[position].toString();
			}else{
				type = URLProtocol.CMD_FILMDETAIL;
				content = DataManager.movies[position].toString();
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
		case R.id.share: {
			Intent intent = new Intent(DetailMovie.this,
					RecommendActivity.class);
			String name;
			if (0 == from) {
				name = DataManager.willmovies[position].name;
			} else {
				name = DataManager.movies[position].name;
			}
			intent.putExtra("name", name);
			startActivity(intent);
		}
			break;
		default:
			break;
		}
	}

}
