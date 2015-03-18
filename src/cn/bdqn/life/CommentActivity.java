package cn.bdqn.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cn.bdqn.life.data.LifePreferences;
import cn.bdqn.life.net.ParseJasonDataService;
import cn.bdqn.life.net.URLProtocol;
/**∆¿¬€*/
public class CommentActivity extends Activity implements OnClickListener{
	
	private EditText commentText;
	private int position;
	private int type;
	private String name;
	public static Handler handler;
	private int mid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_layout);
		Intent intent = getIntent();
		position = intent.getIntExtra("position", 0);
		type = intent.getIntExtra("type", 0);
		mid = intent.getIntExtra("mid", 0);
		name = LifePreferences.getPreferences().getName();
		TextView headText = (TextView) findViewById(R.id.headtext);
		headText.setText(R.string.moviecomment);
		Button backButton = (Button) findViewById(R.id.comment_back);
		Button sendButton = (Button) findViewById(R.id.comment_send);
		backButton.setOnClickListener(this);
		sendButton.setOnClickListener(this);
		commentText = (EditText) findViewById(R.id.comment_content);
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				Bundle result = msg.getData();
				int code = result.getInt("code", 1);
				if(0 == code){
					Message m = new Message();
					Bundle bundle = new Bundle();
					bundle.putInt("mid", mid);
					bundle.putInt("position", position);
					m.what = type;
					m.setData(bundle);
					ParseJasonDataService.handler.sendMessage(m);
				}else{
					Toast.makeText(getApplicationContext(), "∑¢±Ì ß∞‹", Toast.LENGTH_LONG).show();
				}
			}
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.comment_back:
			this.finish();
			break;
		case R.id.comment_send:
			String content = commentText.getText().toString();
			Message msg = new Message();
			msg.what = URLProtocol.CMD_SEND_REC;
			Bundle bundle = new Bundle();
			bundle.putInt("position", position);
			bundle.putInt("mid", mid);
			bundle.putInt("type", type);
			bundle.putString("name", name);
			bundle.putString("content", content);
			msg.setData(bundle);
			ParseJasonDataService.handler.sendMessage(msg);
			break;
		default:
			break;
		}
		
	}
}
