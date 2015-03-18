package cn.bdqn.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import cn.bdqn.life.data.LifePreferences;
import cn.bdqn.life.net.ParseJasonDataService;
import cn.bdqn.life.net.URLProtocol;
import cn.bdqn.life.utils.DefaultDialog;
import cn.bdqn.life.utils.ScreenUtil;

/**登录*/
public class LoginActivity extends Activity {
    
	private EditText userName;
	private EditText userPwd;
	private CheckBox rembPwd;
	private CheckBox autoLogin;
	private Button cancelBtn;
	private Button loginBtn;
	public static Handler mHandler;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);   
        setContentView(R.layout.login_layout);
        this.setTitle(R.string.login_submit_button);
        userName = (EditText) this.findViewById(R.id.userName);
        userPwd = (EditText) this.findViewById(R.id.userPwd);
        rembPwd = (CheckBox) this.findViewById(R.id.rememberPwd);
        autoLogin = (CheckBox) this.findViewById(R.id.autoLogin);
        cancelBtn = (Button) this.findViewById(R.id.cancel);
        loginBtn = (Button) this.findViewById(R.id.login);
        
        //默认记住密码、自动登录
        rembPwd.setChecked(LifePreferences.getPreferences().getRemberPW());
        autoLogin.setChecked(LifePreferences.getPreferences().getAutoLogin());
        
        //为自动登录和记住密码添加点击事件
        rembPwd.setOnCheckedChangeListener(checkListener);
        autoLogin.setOnCheckedChangeListener(checkListener);
       
        //从SharedPreferences中取出用户名和密码信息，显示在界面EditText
        String name = LifePreferences.getPreferences().getName();
        String password = LifePreferences.getPreferences().getPW();
        if(!"".equals(name)){
        	userName.setText(name);
        }
        if(!"".equals(password)){
        	userPwd.setText(password);
        }
        //为登录和取消按钮设置点击事件
        loginBtn.setOnClickListener(clickListener);
        cancelBtn.setOnClickListener(clickListener);
    }
    
    //CheckBox点击事件
    private OnCheckedChangeListener checkListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton view,boolean isChecked) {
			if(view.getId() == R.id.rememberPwd){
				LifePreferences.getPreferences().setRemberPW(isChecked);
			}
			if(view.getId() == R.id.autoLogin){
				LifePreferences.getPreferences().setAutoLogin(isChecked);
			}
		}
	};
    
    //按钮点击事件
    private OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.login){
				String name = userName.getText().toString();
				String pw = userPwd.getText().toString();
				
				if("".equals(name) || "".equals(pw)){//用户名或密码为空
					ScreenUtil.showMsg(LoginActivity.this, getString(R.string.input_null));
				}else{//访问服务器提交注册请求
					
					mHandler = new Handler(){//返回请求
						@Override
						public void handleMessage(Message msg) {
							if(msg.what == URLProtocol.CMD_LOGIN){
								ScreenUtil.hideLoading();
								Bundle bundle = msg.getData();
								int code = bundle.getInt(ParseJasonDataService.HTTP_CODE);
								if(code == 0){//状态码为0表示获取信息正确
									ScreenUtil.showMsg(LoginActivity.this, getString(R.string.login_success));
									Intent intent= new Intent(LoginActivity.this, TabMainActivity.class);
									intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(intent);
									LoginActivity.this.finish();
								}else{
									ScreenUtil.showMsg(LoginActivity.this, getString(R.string.login_fail));
								}
							}else{
								ScreenUtil.showMsg(LoginActivity.this, getString(R.string.data_error));
							}
						}
					};
					//登录时判断与保存的用户名密码是否一致 ，如果一致发送请求
					String saveName = LifePreferences.getPreferences().getName();
					String savePwd = LifePreferences.getPreferences().getPW();
					
					if(saveName.equals(name) && savePwd.equals(pw)){//用户名，密码与SharedPreferences保存一致
						String uid = LifePreferences.getPreferences().getUID();
						ScreenUtil.showProgressDialog(LoginActivity.this);//显示进度条
						Message msg = new Message();
						msg.what = URLProtocol.CMD_LOGIN;
						Bundle bundle = new Bundle();
						bundle.putInt("login", URLProtocol.CMD_LOGIN);
						bundle.putString("uid", uid);
						msg.setData(bundle);
						ParseJasonDataService.handler.sendMessage(msg);
					}else{
						ScreenUtil.showMsg(LoginActivity.this, getString(R.string.input_error));
					}
				}
			}
			if(v.getId() == R.id.cancel){
				DefaultDialog dialog = new DefaultDialog(LoginActivity.this,null,true) {
					
					@Override
					protected void doPositive() {
						Intent intent= new Intent(LoginActivity.this, TabMainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						LoginActivity.this.finish();
					}
					@Override
					protected void doItems(int which) {
					}
				};
				dialog.setTitle(R.string.sure_no_login);
				dialog.show();
			}
		}
	};
}