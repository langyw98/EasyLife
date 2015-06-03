package cn.bdqn.life.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import cn.bdqn.life.R;
import cn.bdqn.life.utils.LifePreferences;

public class WelcomeActivity extends Activity {
	private LifePreferences lifeInstance;
	private String uid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		lifeInstance = LifePreferences.getPreferences();
		lifeInstance.init(getApplicationContext());
		
		uid = lifeInstance.getUID();
		
		//从欢迎界面延时跳转
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				//如果已经登陆，跳转到主界面
				if(!uid.equals("no")){
					intent.setClass(WelcomeActivity.this, cn.bdqn.life.activity.MainActivity.class);
				}//如果未登陆，跳转到登陆界面
				else{
					intent.setClass(WelcomeActivity.this, cn.bdqn.life.activity.LoginActivity.class);
				}
				startActivity(intent);
				finish();
			}
		}, 3000);
	}
}
