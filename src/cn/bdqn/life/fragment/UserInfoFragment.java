package cn.bdqn.life.fragment;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import cn.bdqn.life.R;
import cn.bdqn.life.activity.MainActivity;
import cn.bdqn.life.net.HttpConnection;
import cn.bdqn.life.net.URLProtocol;
import cn.bdqn.life.utils.BitmapUtils;
import cn.bdqn.life.utils.FileUtil;
import cn.bdqn.life.utils.LifePreferences;
import cn.bdqn.life.utils.LoadImageUtil;

public class UserInfoFragment extends Fragment {
	public static final int REQUEST_CODE_FETCH_ICON = 1;
	
	private MainActivity hostActivity;
	private ImageView ivIcon;
	
	private Bitmap icon = null;
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
