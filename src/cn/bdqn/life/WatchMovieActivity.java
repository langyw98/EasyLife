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
import cn.bdqn.life.adapter.MovieAdapter;
import cn.bdqn.life.data.DataManager;
import cn.bdqn.life.net.ParseJasonDataService;
import cn.bdqn.life.net.URLProtocol;
import cn.bdqn.life.utils.ScreenUtil;

/**正在上映的电影*/
public class WatchMovieActivity extends Activity {

	private ListView list;
	public static Handler mHandler;
	public int mid = 0 ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.movie_layout);
		// 电影列表
		list = (ListView) this.findViewById(R.id.movielist);
		MovieAdapter adapter = new MovieAdapter(this,DataManager.movies);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(itemClickListener);
		
		mHandler = new Handler(){//返回请求
			@Override
			public void handleMessage(Message msg) {
				if(msg.what == URLProtocol.CMD_MOVIE_WILL_DETAIL){//看电影
					ScreenUtil.hideLoading();
					Bundle bundle = msg.getData();
					mid = bundle.getInt("mid");
					int position = bundle.getInt("position");
					int code = bundle.getInt(ParseJasonDataService.HTTP_CODE);
					if(code == 0){//状态码为0表示获取信息正确
						Intent intent= new Intent(WatchMovieActivity.this, DetailMovie.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.putExtra("from", 0);//标记来自即将播放
						intent.putExtra("position", position);
						startActivity(intent);
					}else{
						ScreenUtil.showMsg(WatchMovieActivity.this, getString(R.string.data_error));
					}
				}else if(msg.what == URLProtocol.CMD_MOVIEDETAIL){
					ScreenUtil.hideLoading();
					Bundle bundle = msg.getData();
					int position = bundle.getInt("position");
					int code = bundle.getInt(ParseJasonDataService.HTTP_CODE);
					if(0 == code){
						Intent intent= new Intent(WatchMovieActivity.this, DetailMovie.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.putExtra("position", position);
						intent.putExtra("from", 1);//标记来自正在播放
						intent.putExtra("mid",mid);
						startActivity(intent);
					}
				}
			}
		};
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapter, View view, int position,long arg3) {
			ScreenUtil.showProgressDialog(WatchMovieActivity.this);//显示进度条
			Message msg = new Message();
			Bundle bundle = new Bundle();
			mid = DataManager.movies[position].mid;
			bundle.putInt("mid", mid);
			bundle.putInt("position", position);
			msg.setData(bundle);
			msg.what = URLProtocol.CMD_MOVIEDETAIL;
			ParseJasonDataService.handler.sendMessage(msg);
		}
	};
}
