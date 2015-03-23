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
import cn.bdqn.life.net.HttpConnection;
import cn.bdqn.life.net.URLParam;
import cn.bdqn.life.net.URLProtocol;

public class RegActivity extends Activity {
	private static final int MSG_CONNECT_FAILED = 0;
	private static final int MSG_ACCOUNT_EXIST = 1;
	private static final int MSG_REGIST_SUCCESS = 2;
	
	private EditText et_account;
	private EditText et_password;
	private EditText et_confirm;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case MSG_CONNECT_FAILED:
			case MSG_ACCOUNT_EXIST:
				Toast.makeText(RegActivity.this, "ע��ʧ��", Toast.LENGTH_LONG).show();
				break;
			case MSG_REGIST_SUCCESS:
				Toast.makeText(RegActivity.this, "ע��ɹ�", Toast.LENGTH_LONG).show();
				//��¼Ϊ��½״̬
				//ע��ɹ�������½����ת��������
				Intent intent = new Intent(RegActivity.this, cn.bdqn.life.activity.MainActivity.class);
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
		setContentView(R.layout.activity_regist);
		init();
	}
	
	private void init(){
		et_account = (EditText) findViewById(R.id.et_account);
		et_password = (EditText) findViewById(R.id.et_password);
		et_confirm = (EditText) findViewById(R.id.et_confirm);
	}
	
	public void BtnOnClick(View view){
		switch(view.getId()){
		case R.id.btn_regist:
			String strAccount = et_account.getText().toString().trim();
			String strPassword = et_password.getText().toString().trim();
			String strConfirm = et_confirm.getText().toString().trim();
			//ע���û���Ϊ��
			if(strAccount == null || strAccount.equals("")){
				Toast.makeText(this, "�û�������Ϊ�գ�����д�û���", Toast.LENGTH_LONG).show();
			}//��������Ϊ��
			else if(strPassword == null || strPassword.equals("")){
				Toast.makeText(this, "���벻��Ϊ�գ�����д����", Toast.LENGTH_LONG).show();
			}//������������벻��ͬ
			else if(strConfirm == null || !strConfirm.equals(strPassword)){
				Toast.makeText(this, "������������벻һ�£���ȷ�ϲ��޸�", Toast.LENGTH_LONG).show();
			}
			//����ע�᷽��
			Regist(strAccount, strPassword);
			break;
		}
	}
	
	private void Regist(final String strAccount, final String strPassword){
		new Thread(){
			public void run() {
				URLParam param = new URLParam(null);
				param.addParam("name", strAccount);
				param.addParam("password", strPassword);
				param.addParam("cmd", URLProtocol.CMD_REGISTER);
				String jsonStr = HttpConnection.httpGet(URLProtocol.ROOT, param);
				//�����������ʧ��
				if(jsonStr == null){
					handler.sendEmptyMessage(MSG_CONNECT_FAILED);
				}
				try {
					JSONObject json = new JSONObject(jsonStr);
					int code = json.getInt("code");
					if (code == 0) {
						String uid = json.getString("uid");
						Log.i("RegActivity","uid is "+ uid);
						handler.sendEmptyMessage(MSG_REGIST_SUCCESS);
						return;
					}
					handler.sendEmptyMessage(MSG_ACCOUNT_EXIST);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}
	

}
