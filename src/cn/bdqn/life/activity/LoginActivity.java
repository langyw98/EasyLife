package cn.bdqn.life.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import cn.bdqn.life.R;

public class LoginActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}
	public void BtnOnClick(View view){
		Intent intent = new Intent();
		switch(view.getId()){
		case R.id.btn_login:
			break;
		case R.id.btn_regist:
			intent.setClass(this, cn.bdqn.life.activity.RegActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_problem:
			break;
		}
		
	}
}
