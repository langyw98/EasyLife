package cn.bdqn.life.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

public class ScreenUtil {

	public static ProgressDialog _progressDialog;
	
	/**显示Toast信息*/
	public static void showMsg(Context context ,String  text){
		Toast.makeText(context,text, Toast.LENGTH_SHORT).show();
	}
	
	/**显示ProgressDialog*/
	public static void showProgressDialog(Context ctx){
		_progressDialog = new ProgressDialog(ctx);
		_progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		_progressDialog.setMessage("数据加载中......");
		_progressDialog.setCancelable(false);
		_progressDialog.show();
	}	
	
	/**取消ProgressDialog*/
	public static void hideLoading(){
		if(_progressDialog.isShowing()){
			_progressDialog.cancel();
		}
	}
}
