package cn.bdqn.life.fragment;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import cn.bdqn.life.R;
import cn.bdqn.life.activity.MainActivity;
import cn.bdqn.life.net.HttpConnection;
import cn.bdqn.life.net.URLParam;
import cn.bdqn.life.net.URLProtocol;
import cn.bdqn.life.utils.BitmapUtils;
import cn.bdqn.life.utils.FileUtil;
import cn.bdqn.life.utils.LifePreferences;
import cn.bdqn.life.utils.LoadImageUtil;

public class UserInfoFragment extends Fragment {
	public static final int REQUEST_CODE_FETCH_ICON = 1;
	
	public static final int MSG_CONNECT_FAILED = 0;
	public static final int MSG_CHANGE_PWD_SUCCESS = 1;
	public static final int MSG_CHANGE_PWD_FAILED = 2;
	public static final int MSG_CHANGE_NICKNAME_SUCCESS = 3;
	public static final int MSG_CHANGE_NICKNAME_FAILED = 4;
	
	private MainActivity hostActivity;
	private ImageView ivIcon;
	private Button btnExit;
	private Button btnNickname;
	private Button btnPwd;
	private Dialog mDialog;
	
	private Bitmap icon = null;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case MSG_CONNECT_FAILED:
				Toast.makeText(hostActivity, "网络连接失败！", Toast.LENGTH_LONG).show();
				break;
			case MSG_CHANGE_PWD_FAILED:
				Toast.makeText(hostActivity, "密码修改失败！", Toast.LENGTH_LONG).show();
				break;
			case MSG_CHANGE_PWD_SUCCESS:
				Toast.makeText(hostActivity, "密码修改成功！", Toast.LENGTH_LONG).show();
				break;
			case MSG_CHANGE_NICKNAME_FAILED:
				Toast.makeText(hostActivity, "昵称修改失败！", Toast.LENGTH_LONG).show();
				break;
			case MSG_CHANGE_NICKNAME_SUCCESS:
				Toast.makeText(hostActivity, "昵称修改成功！", Toast.LENGTH_LONG).show();
				break;
			}
		};
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_userinfo, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		hostActivity = (MainActivity)getActivity();
		hostActivity.setUserInfoFragment(this);
		ivIcon = (ImageView) getView().findViewById(R.id.iv_icon);
		ivIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);  
				hostActivity.startActivityForResult(intent, REQUEST_CODE_FETCH_ICON);
			}
		});
		String uid = LifePreferences.getPreferences().getUID();
		if(FileUtil.imageExist(uid)){
			String path = FileUtil.IMAGEDIR+ FileUtil.separator + uid + ".life";
			Drawable icon = BitmapDrawable.createFromPath(path);
			ivIcon.setImageDrawable(icon);
		}else{
			LoadImageUtil.loadHeadicon(ivIcon, uid);
		}
		btnNickname = (Button) getView().findViewById(R.id.btn_changenickname);
		btnNickname.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(hostActivity);
				mDialog = builder.setView(View.inflate(hostActivity, R.layout.dialog_change_nickname, null))
									.setTitle("修改昵称")
									.setPositiveButton("确定修改", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											EditText etNickname = (EditText) mDialog.findViewById(R.id.et_nickname);
											
											final String strChangeNickname = etNickname.getText().toString().trim();
											String strNickname = LifePreferences.getPreferences().getNickName();
											if(strChangeNickname == null || strChangeNickname.equals("")){
												Toast.makeText(hostActivity, "昵称不能为空", Toast.LENGTH_LONG).show();
											}else if(strNickname.equals(strChangeNickname)){
												return;
											}else{
												new Thread(){
													public void run() {
														URLParam param = new URLParam(null);
														param.addParam("nickname", strChangeNickname);
														param.addParam("uid", LifePreferences.getPreferences().getUID());
														param.addParam("cmd", URLProtocol.CMD_CHANGE_NICKNAME);
														String jsonStr = HttpConnection.httpGet(URLProtocol.ROOT, param);
														//与服务器连接失败
														if(jsonStr == null){
															handler.sendEmptyMessage(MSG_CONNECT_FAILED);
															return;
														}
														try {
															JSONObject json = new JSONObject(jsonStr);
															int code = json.getInt("code");
															if (code == 0) {
																//保存新密码
																LifePreferences.getPreferences().saveNickName(strChangeNickname);
																
																handler.sendEmptyMessage(MSG_CHANGE_NICKNAME_SUCCESS);
																return;
															}
															handler.sendEmptyMessage(MSG_CHANGE_NICKNAME_FAILED);
														} catch (JSONException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													};
												}.start();
											}
											
										}
									})
									.setNegativeButton("取消", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											
										}
									})
									.create();
				mDialog.show();
				EditText etNickname = (EditText) mDialog.findViewById(R.id.et_nickname);
				etNickname.setText(LifePreferences.getPreferences().getNickName());
			}
		});
		btnExit = (Button) getView().findViewById(R.id.btn_exit);
		btnExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LifePreferences.getPreferences().clearData();
				hostActivity.finish();
			}
		});
		btnPwd = (Button) getView().findViewById(R.id.btn_changepwd);
		btnPwd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(hostActivity);
				mDialog = builder.setView(View.inflate(hostActivity, R.layout.dialog_change_pwd, null))
									.setTitle("修改密码")
									.setPositiveButton("确定修改", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											EditText etOldpwd = (EditText) mDialog.findViewById(R.id.et_oldpwd);
											EditText etNewpwd = (EditText) mDialog.findViewById(R.id.et_newpwd);
											EditText etConfirmpwd = (EditText) mDialog.findViewById(R.id.et_confirmpwd);
											
											final String oldPwd = etOldpwd.getText().toString();
											final String newPwd = etNewpwd.getText().toString();
											final String confirmPwd = etConfirmpwd.getText().toString();
											
											if(newPwd == null || confirmPwd == null || !newPwd.equals(confirmPwd)){
												Toast.makeText(hostActivity, "请重新输入新密码，并保证两次输入相同！", Toast.LENGTH_LONG).show();
												return;
											}else{
												new Thread(){
													public void run() {
														URLParam param = new URLParam(null);
														param.addParam("oldpwd", oldPwd);
														param.addParam("newpwd", newPwd);
														param.addParam("uid", LifePreferences.getPreferences().getUID());
														param.addParam("cmd", URLProtocol.CMD_CHANGE_PWD);
														String jsonStr = HttpConnection.httpGet(URLProtocol.ROOT, param);
														//与服务器连接失败
														if(jsonStr == null){
															handler.sendEmptyMessage(MSG_CONNECT_FAILED);
															return;
														}
														try {
															JSONObject json = new JSONObject(jsonStr);
															int code = json.getInt("code");
															if (code == 0) {
																//保存新密码
																LifePreferences.getPreferences().savePW(newPwd);
																
																handler.sendEmptyMessage(MSG_CHANGE_PWD_SUCCESS);
																return;
															}
															handler.sendEmptyMessage(MSG_CHANGE_PWD_FAILED);
														} catch (JSONException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													};
												}.start();
											}
											
										}
									})
									.setNegativeButton("取消", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											
										}
									})
									.create();
				mDialog.show();
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		Uri uri = data.getData();
		try {
			icon = Images.Media.getBitmap(hostActivity.getContentResolver(), uri);
			icon = BitmapUtils.compressImage(icon);
			icon = BitmapUtils.small(icon, ivIcon.getWidth(), ivIcon.getHeight());
			ivIcon.setImageBitmap(icon);
			new Thread(){
				public void run() {
					String uid = LifePreferences.getPreferences().getUID();
					String filePath = FileUtil.saveBitmap(uid, icon);
					if(filePath != null){
						HttpConnection.uploadImage(URLProtocol.ROOT + "?cmd=" + 
												   URLProtocol.CMD_UPLOAD_HEADICON + 
												   "&" + "uid=" + uid, filePath);
					}
				};
			}.start();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
