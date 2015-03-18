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
import cn.bdqn.life.adapter.WillMovieAdapter;
import cn.bdqn.life.data.DataManager;
import cn.bdqn.life.net.ParseJasonDataService;
import cn.bdqn.life.net.URLProtocol;
import cn.bdqn.life.utils.ScreenUtil;
/*
 * 正在上映的电影
 */
public class WillMovieActivity extends Activity {

	private ListView list;
	public static Handler mHandler;
	public int mid= 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_layout);
		// 电影列表
		list = (ListView) this.findViewById(R.id.movielist);
		WillMovieAdapter adapter = new WillMovieAdapter(this,DataManager.willmovies);
		list.setAdapter(adapter);
		list.setOnItemClickListener(itemClickListener);
		mHandler = new Handler(){//返回请求
			@Override
			public void handleMessage(Message msg) {
				if(msg.what == URLProtocol.CMD_MOVIE_WILL_DETAIL){//看电影
					ScreenUtil.hideLoading();
					Bundle bundle = msg.getData();
					int code = bundle.getInt("HTTP_CODE");
					int position = bundle.getInt("position");
					if(code == 0){//状态码为0表示获取信息正确
						Intent intent= new Intent(WillMovieActivity.this, DetailMovie.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.putExtra("from", 0);//标记来自即将播放
						intent.putExtra("mid", mid);
						intent.putExtra("position", position);
						startActivity(intent);
					}else{
						ScreenUtil.showMsg(WillMovieActivity.this, getString(R.string.data_error));
					}
				}
			}
		};
	}
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,long arg3) {
			ScreenUtil.showProgressDialog(WillMovieActivity.this);//显示进度条
			int mid = DataManager.willmovies[position].mid;
			Bundle bundle = new Bundle();
			bundle.putInt("mid", mid);
			bundle.putInt("position", position);
			Message msg = new Message();
			msg.what = URLProtocol.CMD_MOVIE_WILL_DETAIL;
			msg.setData(bundle);
			ParseJasonDataService.handler.sendMessage(msg);
		}
	};
}
