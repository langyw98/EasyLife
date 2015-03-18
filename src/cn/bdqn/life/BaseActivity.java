package cn.bdqn.life;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class BaseActivity extends Activity {

	public static Activity me;
	public TextView headtext;
	public ImageButton commBtn;
	public ImageButton shareBtn;
	public ImageButton saveBtn;
	public ImageButton backBtn;
	public Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		me = this;
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
	
	/**
	 * 底部导航按钮
	 */
	public void getButton(){
		 //底部菜单
		  //评论
		commBtn=(ImageButton)findViewById(R.id.comment);
		commBtn.setOnClickListener(onClick);
		  //共享
		shareBtn=(ImageButton)findViewById(R.id.share);
		shareBtn.setOnClickListener(onClick);
		  //收藏
		 saveBtn=(ImageButton)findViewById(R.id.save);
		 saveBtn.setOnClickListener(onClick);
		 //返回
//		 backBtn=(ImageButton)findViewById(R.id.back);
	}
	
	/**
	 * 底部导航菜单onClick事件
	 */
	public android.view.View.OnClickListener onClick = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
		}
	};
}
