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

/**注册*/
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
	/**点击事件*/
	private OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.submit){//提交注册
				
				name = userName.getText().toString();
				pw = password.getText().toString();
				
				if("".equals(name) || "".equals(pw)){//用户名或密码为空
					ScreenUtil.showMsg(RegActivity.this, getString(R.string.input_null));
				}else{//访问服务器提交注册请求
					
					mHandler = new Handler(){//返回请求
						@Override
						public void handleMessage(Message msg) {
							if(msg.what == URLProtocol.CMD_REGISTER){//是注册功能
								ScreenUtil.hideLoading();
								Bundle bundle = msg.getData();
								int code = bundle.getInt(ParseJasonDataService.HTTP_CODE);
								if(code == 0){//注册成功
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
					ScreenUtil.showProgressDialog(RegActivity.this);//显示进度条
					Bundle bundle = new Bundle();
					bundle.putString("name", name);
					bundle.putString("password", pw);
					Message msg = new Message();
					msg.what = URLProtocol.CMD_REGISTER;
					msg.setData(bundle);
					ParseJasonDataService.handler.sendMessage(msg);
				}
			}if(v.getId() == R.id.cancel){//取消,问用户确定不注册吗？如果不注册直接到主页
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
