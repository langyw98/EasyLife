package cn.bdqn.life.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import cn.bdqn.life.R;

public class WelcomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		//�ӻ�ӭ������ʱ��ת
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				//����Ѿ���½����ת��������
				if(false){
					
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
