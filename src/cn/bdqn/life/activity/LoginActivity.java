package cn.bdqn.life.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import cn.bdqn.life.R;
import cn.bdqn.life.data.LifePreferences;
import cn.bdqn.life.net.HttpConnection;
import cn.bdqn.life.net.URLParam;
import cn.bdqn.life.net.URLProtocol;

public class LoginActivity extends Activity {
	private static final int MSG_CONNECT_FAILED = 0;
	private static final int MSG_LOGIN_FAILED = 1;
	private static final int MSG_LOGIN_SUCCESS = 2;
	
	private EditText et_account;
	private EditText et_password;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case MSG_CONNECT_FAILED:
			case MSG_LOGIN_FAILED:
				Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_LONG).show();
				break;
			case MSG_LOGIN_SUCCESS:
				Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
				//登陆成功，跳转到主界面
				Intent intent = new Intent(LoginActivity.this, cn.bdqn.life.activity.MainActivity.class);
				startActivity(intent);
				finish();
				break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
	}
	
	private void init(){
		et_account = (EditText) findViewById(R.id.et_account);
		et_password = (EditText) findViewById(R.id.et_password);
	}
	
	public void BtnOnClick(View view){
		Intent intent = new Intent();
		switch(view.getId()){
		case R.id.btn_login:
			String strAccount = et_account.getText().toString().trim();
			String strPassword = et_password.getText().toString().trim();
			//注册用户名为空
			if(strAccount == null || strAccount.equals("")){
				Toast.makeText(this, "用户名不能为空，请填写用户名", Toast.LENGTH_LONG).show();
			}//输入密码为空
			else if(strPassword == null || strPassword.equals("")){
				Toast.makeText(this, "密码不能为空，请填写密码", Toast.LENGTH_LONG).show();
			}
			Login(strAccount,strPassword);
			break;
		case R.id.btn_regist:
			intent.setClass(this, cn.bdqn.life.activity.RegActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_problem:
			break;
		}
	}
	
	private void Login(final String strAccount, final String strPassword){
		new Thread(){
			public void run() {
				URLParam param = new URLParam(null);
				param.addParam("name", strAccount);
				param.addParam("password", strPassword);
				param.addParam("cmd", URLProtocol.CMD_LOGIN);
				String jsonStr = HttpConnection.httpGet(URLProtocol.ROOT, param);
				//与服务器连接失败
				if(jsonStr == null){
					handler.sendEmptyMessage(MSG_CONNECT_FAILED);
					return;
				}
				try {
					JSONObject json = new JSONObject(jsonStr);
					int code = json.getInt("code");
					if (code == 0) {
						//保存登陆信息
						String uid = json.getString("uid");
						LifePreferences.getPreferences().setUID(uid);
						LifePreferences.getPreferences().saveName(strAccount);
						LifePreferences.getPreferences().savePW(strPassword);
						
						handler.sendEmptyMessage(MSG_LOGIN_SUCCESS);
						return;
					}
					handler.sendEmptyMessage(MSG_LOGIN_FAILED);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}
}
