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
		
		//�ӻ�ӭ������ʱ��ת
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				//����Ѿ���½����ת��������
				if(!uid.equals("no")){
					intent.setClass(WelcomeActivity.this, cn.bdqn.life.activity.MainActivity.class);
				}//���δ��½����ת����½����
				else{
					intent.setClass(WelcomeActivity.this, cn.bdqn.life.activity.LoginActivity.class);
				}
				startActivity(intent);
				finish();
			}
		}, 3000);
	}
}
