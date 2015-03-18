package cn.bdqn.life;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
/**更多*/
public class MoreActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_layout);
		ListView mListView = (ListView) findViewById(R.id.list);
		TextView headText = (TextView) findViewById(R.id.headtext);
		headText.setText("更多");
		ArrayAdapter< String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, new String[]{"关于", "帮助", "反馈", "退出"});
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View view, int index,
					long id) {
				Intent intent = null;
				switch(index){
				case 0: {
					intent = new Intent(MoreActivity.this,AboutActivity.class);
					startActivity(intent);
				}
					break;
				case 1:{
					intent = new Intent(MoreActivity.this,HelpActivity.class);
					startActivity(intent);
				}
					break;
				case 2:{
					intent = new Intent(MoreActivity.this,QuestionActivity.class);
					startActivity(intent);
				}
					break;
				case 3:{
					showExitDialog(MoreActivity.this);
				}
					break;
				default :
					break;
				}
				
			}
		});
		
	}
	
	public static void showExitDialog(Activity me) {
		AlertDialog dialog = new AlertDialog.Builder(me).create();

		String exitMessage = me.getResources().getString(R.string.sure_quit);
		
		String exitSure = me.getResources().getString(R.string.exit_yes);
		String exitNo = me.getResources().getString(R.string.exit_no);
		dialog.setTitle(R.string.app_name);
		dialog.setMessage(exitMessage);
		dialog.setButton(exitSure, mExitListener);
		dialog.setButton2(exitNo, mExitListener);
		dialog.show();
	}

	private static OnClickListener mExitListener = new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			if (which == -1) {
				 android.os.Process.killProcess(android.os.Process.myPid());	
			} else if (which == -2) {
				dialog.dismiss();
			}
		}
	};
}

