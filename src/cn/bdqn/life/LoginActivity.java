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

/**��¼*/
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
        
        //Ĭ�ϼ�ס���롢�Զ���¼
        rembPwd.setChecked(LifePreferences.getPreferences().getRemberPW());
        autoLogin.setChecked(LifePreferences.getPreferences().getAutoLogin());
        
        //Ϊ�Զ���¼�ͼ�ס������ӵ���¼�
        rembPwd.setOnCheckedChangeListener(checkListener);
        autoLogin.setOnCheckedChangeListener(checkListener);
       
        //��SharedPreferences��ȡ���û�����������Ϣ����ʾ�ڽ���EditText
        String name = LifePreferences.getPreferences().getName();
        String password = LifePreferences.getPreferences().getPW();
        if(!"".equals(name)){
        	userName.setText(name);
        }
        if(!"".equals(password)){
        	userPwd.setText(password);
        }
        //Ϊ��¼��ȡ����ť���õ���¼�
        loginBtn.setOnClickListener(clickListener);
        cancelBtn.setOnClickListener(clickListener);
    }
    
    //CheckBox����¼�
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
    
    //��ť����¼�
    private OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.login){
				String name = userName.getText().toString();
				String pw = userPwd.getText().toString();
				
				if("".equals(name) || "".equals(pw)){//�û���������Ϊ��
					ScreenUtil.showMsg(LoginActivity.this, getString(R.string.input_null));
				}else{//���ʷ������ύע������
					
					mHandler = new Handler(){//��������
						@Override
						public void handleMessage(Message msg) {
							if(msg.what == URLProtocol.CMD_LOGIN){
								ScreenUtil.hideLoading();
								Bundle bundle = msg.getData();
								int code = bundle.getInt(ParseJasonDataService.HTTP_CODE);
								if(code == 0){//״̬��Ϊ0��ʾ��ȡ��Ϣ��ȷ
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
					//��¼ʱ�ж��뱣����û��������Ƿ�һ�� �����һ�·�������
					String saveName = LifePreferences.getPreferences().getName();
					String savePwd = LifePreferences.getPreferences().getPW();
					
					if(saveName.equals(name) && savePwd.equals(pw)){//�û�����������SharedPreferences����һ��
						String uid = LifePreferences.getPreferences().getUID();
						ScreenUtil.showProgressDialog(LoginActivity.this);//��ʾ������
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