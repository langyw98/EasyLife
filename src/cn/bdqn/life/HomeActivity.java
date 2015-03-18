package cn.bdqn.life;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.bdqn.life.adapter.HomeAdapter;
import cn.bdqn.life.data.HomeData;
import cn.bdqn.life.net.ParseJasonDataService;
import cn.bdqn.life.net.URLProtocol;
import cn.bdqn.life.utils.HomeDataUtil;
import cn.bdqn.life.utils.ScreenUtil;

/** 活动 */
public class HomeActivity extends BaseActivity {

	private ListView listview;
	public static Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout);

		headtext = (TextView) this.findViewById(R.id.headtext);
		headtext.setText(R.string.app_name);

		listview = (ListView) this.findViewById(R.id.homeList);

		List<HomeData> datas = HomeDataUtil.getHomeData(this);
		HomeAdapter adapter = new HomeAdapter(this, datas);

		listview.setAdapter(adapter);
		listview.setOnItemClickListener(itemClickListener);

		mHandler = new Handler() {// 返回请求
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == URLProtocol.CMD_MOVIE) {// 看电影
					ScreenUtil.hideLoading();
					Bundle bundle = msg.getData();
					int code = bundle.getInt("HTTP_CODE");
					if (code == 0) {// 状态码为0表示获取信息正确
						Intent intent = new Intent(HomeActivity.this,
								TabMovieActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					} else {
						ScreenUtil.showMsg(HomeActivity.this,
								getString(R.string.data_error));
					}
				}
				if (msg.what == URLProtocol.CMD_DELICACIES) {// 找美食
					ScreenUtil.hideLoading();
					Bundle bundle = msg.getData();
					int code = bundle.getInt("HTTP_CODE");
					if (code == 0) {// 状态码为0表示获取信息正确
						Intent intent = new Intent(HomeActivity.this,
								FindEatActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					} else {
						ScreenUtil.showMsg(HomeActivity.this,
								getString(R.string.data_error));
					}
				}
				if (msg.what == URLProtocol.CMD_DISPLAY) {// 看展览
					ScreenUtil.hideLoading();
					Bundle bundle = msg.getData();
					int code = bundle.getInt("HTTP_CODE");
					if (code == 0) {// 状态码为0表示获取信息正确
						Intent intent = new Intent(HomeActivity.this,
								SeeDisplayActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					} else {
						ScreenUtil.showMsg(HomeActivity.this,
								getString(R.string.data_error));
					}
				}
				if (msg.what == URLProtocol.CMD_CONCERT) {// 看演出
					ScreenUtil.hideLoading();
					Bundle bundle = msg.getData();
					int code = bundle.getInt("HTTP_CODE");
					if (code == 0) {// 状态码为0表示获取信息正确
						Intent intent = new Intent(HomeActivity.this,
								SeeShowActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					} else {
						ScreenUtil.showMsg(HomeActivity.this,
								getString(R.string.data_error));
					}
				}
			}
		};
	}

	/** ListView行点击事件 */
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {

			switch (position) {
			// 看电影
			case 0: {
				ScreenUtil.showProgressDialog(HomeActivity.this);// 显示进度条
				Message msg = new Message();
				msg.what = URLProtocol.CMD_MOVIE;
				ParseJasonDataService.handler.sendMessage(msg);
			}
				break;
			// 找美食
			case 1: {
				ScreenUtil.showProgressDialog(HomeActivity.this);// 显示进度条
				Message msg = new Message();
				msg.what = URLProtocol.CMD_DELICACIES;
				ParseJasonDataService.handler.sendMessage(msg);
			}

				break;
			// 看展览
			case 2: {
				ScreenUtil.showProgressDialog(HomeActivity.this);// 显示进度条
				Message msg = new Message();
				msg.what = URLProtocol.CMD_DISPLAY;
				ParseJasonDataService.handler.sendMessage(msg);
			}
				break;
			// 看演出
			case 3:{
				ScreenUtil.showProgressDialog(HomeActivity.this);// 显示进度条
				Message msg = new Message();
				msg.what = URLProtocol.CMD_CONCERT;
				ParseJasonDataService.handler.sendMessage(msg);
			}
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (KeyEvent.KEYCODE_BACK == keyCode) {
			// 退出
			showExitDialog(this);
		}
		return true;
	}
	
}
