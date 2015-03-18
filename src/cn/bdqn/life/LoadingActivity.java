package cn.bdqn.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.bdqn.life.data.LifePreferences;
import cn.bdqn.life.net.ParseJasonDataService;
import cn.bdqn.life.net.URLProtocol;
import cn.bdqn.life.utils.FileUtil;
import cn.bdqn.life.utils.ScreenUtil;

public class LoadingActivity extends Activity {

	private LifePreferences lifeInstance;
	private Intent intent;
	public static Handler mHandler;
	private String uid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ScreenUtil.showProgressDialog(this);
		Intent tent = new Intent(this, ParseJasonDataService.class);
		startService(tent);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		lifeInstance = LifePreferences.getPreferences();
		lifeInstance.init(getApplicationContext());
		setContentView(R.layout.loading_layout);
		uid = lifeInstance.getUID();
		// SDCard�ϴ��������ļ���
		if (FileUtil.createFile(FileUtil.IMAGEDIR)) {// �����ļ��ɹ�

			if (uid.equals("no")) {// û��ע�ᣬ��ת��ע�����
				intent = new Intent(this, RegActivity.class);
				startActivity(intent);
				finish();
			} else {
				boolean auto_login = lifeInstance.getAutoLogin();
				if (auto_login) { // �Զ���¼����¼����ת��Home ����
					mHandler = new Handler() {// ��������
						@Override
						public void handleMessage(Message msg) {
							if (msg.what == URLProtocol.CMD_LOGIN) {
								ScreenUtil.hideLoading();
								Bundle bundle = msg.getData();
								int code = bundle.getInt(ParseJasonDataService.HTTP_CODE);
								if (code == 0) {// ״̬��Ϊ0��ʾ��ȡ��Ϣ��ȷ
									ScreenUtil.showMsg(LoadingActivity.this,
											getString(R.string.login_success));
									Intent intent = new Intent(
											LoadingActivity.this,
											TabMainActivity.class);
									intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									startActivity(intent);
									LoadingActivity.this.finish();
								} else {
									ScreenUtil.showMsg(LoadingActivity.this,
											getString(R.string.login_fail));
								}
							} else {
								ScreenUtil.showMsg(LoadingActivity.this,
										getString(R.string.data_error));
							}
						}
					};
					
					Thread thread = new Thread(new Runnable() {
						boolean flag = true;
						@Override
						public void run() {
							while (flag) {
								if(null != ParseJasonDataService.handler){
									Message msg = new Message();
									msg.what = URLProtocol.CMD_LOGIN;
									Bundle bundle = new Bundle();
									bundle.putInt("login", 1);
									bundle.putString("uid", uid);
									msg.setData(bundle);
									ParseJasonDataService.handler
											.sendMessage(msg);
									flag = false;
								}	
							}
						}
					});
					thread.start();
					
				} else {// ���Զ���¼����ת����¼����
					intent = new Intent(LoadingActivity.this,
							LoginActivity.class);
					startActivity(intent);
					LoadingActivity.this.finish();
				}
			}
		} else {
			ScreenUtil.showMsg(LoadingActivity.this,
					getString(R.string.creat_file_fail));
			LoadingActivity.this.finish();
		}
	}
}
