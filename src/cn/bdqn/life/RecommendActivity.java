package cn.bdqn.life;

import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/** 推荐 */
public class RecommendActivity extends Activity implements OnClickListener {

	private EditText numText;
	private EditText contentText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recomment_layout);
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		numText = (EditText) findViewById(R.id.num);
		contentText = (EditText) findViewById(R.id.recomment_content);
		contentText.setText(name);
		Button backButton = (Button) findViewById(R.id.recomment_back);
		Button sendButton = (Button) findViewById(R.id.recomment_send);
		backButton.setOnClickListener(this);
		sendButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.recomment_back:
			this.finish();
			break;
		case R.id.recomment_send:
			String numStr = numText.getText().toString();
			String content = contentText.getText().toString();
			SmsManager smsManager = SmsManager.getDefault();
			PendingIntent sentIntent = PendingIntent.getBroadcast(
					RecommendActivity.this, 0, new Intent(), 0);
			//对长短信进行切割发送
			List<String> msgs = smsManager.divideMessage(content);
			for (String msg : msgs) {
				smsManager.sendTextMessage(numStr, null, msg, sentIntent, null);
			}
			Toast.makeText(RecommendActivity.this, "短信发送完成", Toast.LENGTH_LONG)
					.show();
			break;
		default:
			break;
		}
	}
}
