package cn.bdqn.life;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

/*
 * ����������
 */
public class TabMainActivity extends TabActivity {

	public TabHost th;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabmain_layout);
		th = this.getTabHost();
		// ���Home
		TabSpec ts1 = th.newTabSpec("TS_HOME").setIndicator("TS_HOME")
				.setContent(new Intent(this, HomeActivity.class));
		th.addTab(ts1);
		// ���Commend�Ƽ�
		TabSpec ts2 = th.newTabSpec("TS_COMMEND").setIndicator("TS_COMMEND")
				.setContent(new Intent(this, RecommendActivity.class));
		th.addTab(ts2);
		// ����ղ�
		TabSpec ts3 = th.newTabSpec("TS_SAVE").setIndicator("TS_SAVE")
				.setContent(new Intent(this, CollectionActivity.class));
		th.addTab(ts3);
		// ��Ӹ���
		TabSpec ts4 = th.newTabSpec("TS_MORE").setIndicator("TS_MORE")
				.setContent(new Intent(this, MoreActivity.class));
		th.addTab(ts4);
		
		RadioGroup rgtab = (RadioGroup) this.findViewById(R.id.main_radio);
		rgtab.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case R.id.radio_button0:// ��ҳ
					th.setCurrentTabByTag("TS_HOME");
					break;
				case R.id.radio_button1:// �Ƽ�
					th.setCurrentTabByTag("TS_COMMEND");
					break;
				case R.id.radio_button2:// �ղ�
					th.setCurrentTabByTag("TS_SAVE");
					break;
				case R.id.radio_button3:// ����
					th.setCurrentTabByTag("TS_MORE");
					break;
				}
			}
		});
	}
}