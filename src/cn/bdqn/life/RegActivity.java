package cn.bdqn.life;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.bdqn.life.data.LifePreferences;
import cn.bdqn.life.net.ParseJasonDataService;
import cn.bdqn.life.net.URLProtocol;
import cn.bdqn.life.utils.DefaultDialog;
import cn.bdqn.life.utils.ScreenUtil;

/**ע��*/
public class RegActivity extends BaseActivity {

	private EditText userName;
	private EditText password;
	private Button cancel;
	private Button submit;
	private String  name;
	private String  pw;
	
	public static Handler mHandler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist_layout);
		ScreenUtil.hideLoading();
		me = this;
		this.setTitle(R.string.regist);
		userName = (EditText) this.findViewById(R.id.userName);
		password = (EditText) this.findViewById(R.id.userPwd);
		cancel = (Button) this.findViewById(R.id.cancel);
		submit = (Button) this.findViewById(R.id.submit);
		
		cancel.setOnClickListener(clickListener);
		submit.setOnClickListener(clickListener);
		
	}
	/**����¼�*/
	private OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.submit){//�ύע��
				
				name = userName.getText().toString();
				pw = password.getText().toString();
				
				if("".equals(name) || "".equals(pw)){//�û���������Ϊ��
					ScreenUtil.showMsg(RegActivity.this, getString(R.string.input_null));
				}else{//���ʷ������ύע������
					
					mHandler = new Handler(){//��������
						@Override
						public void handleMessage(Message msg) {
							if(msg.what == URLProtocol.CMD_REGISTER){//��ע�Ṧ��
								ScreenUtil.hideLoading();
								Bundle bundle = msg.getData();
								int code = bundle.getInt(ParseJasonDataService.HTTP_CODE);
								if(code == 0){//ע��ɹ�
									ScreenUtil.showMsg(RegActivity.this,getString(R.string.regiest_success));
									LifePreferences.getPreferences().saveName(name);
									LifePreferences.getPreferences().savePW(pw);
									Intent intent= new Intent(RegActivity.this, LoginActivity.class);
									intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(intent);
									RegActivity.this.finish();
								}else{
									ScreenUtil.showMsg(RegActivity.this, getString(R.string.regiest_fail));
								}
							}else{
								ScreenUtil.showMsg(RegActivity.this, getString(R.string.data_error));
							}
						}
					};
					ScreenUtil.showProgressDialog(RegActivity.this);//��ʾ������
					Bundle bundle = new Bundle();
					bundle.putString("name", name);
					bundle.putString("password", pw);
					Message msg = new Message();
					msg.what = URLProtocol.CMD_REGISTER;
					msg.setData(bundle);
					ParseJasonDataService.handler.sendMessage(msg);
				}
			}if(v.getId() == R.id.cancel){//ȡ��,���û�ȷ����ע���������ע��ֱ�ӵ���ҳ
				DefaultDialog dialog = new DefaultDialog(RegActivity.this,null,true) {
					
					@Override
					protected void doPositive() {
						Intent intent= new Intent(RegActivity.this, HomeActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						RegActivity.this.finish();
					}
					
					@Override
					protected void doItems(int which) {
						
					}
				};
				dialog.setTitle(R.string.sure_no_regist);
				dialog.show();
			}
			
		}
	};
	
}
