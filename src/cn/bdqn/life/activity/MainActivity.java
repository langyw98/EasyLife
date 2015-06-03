package cn.bdqn.life.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import cn.bdqn.life.R;
import cn.bdqn.life.customview.SlidingMenu;
import cn.bdqn.life.dao.IFavorDao;
import cn.bdqn.life.dao.impl.FavorImpl;
import cn.bdqn.life.entity.Favor;
import cn.bdqn.life.fragment.FavorFragment;
import cn.bdqn.life.fragment.MainFragment;
import cn.bdqn.life.fragment.RecommendFragment;
import cn.bdqn.life.fragment.UserInfoFragment;
import cn.bdqn.life.net.HttpConnection;
import cn.bdqn.life.net.URLParam;
import cn.bdqn.life.net.URLProtocol;
import cn.bdqn.life.utils.LifePreferences;

public class MainActivity extends FragmentActivity{
	
	private static final int TAB_1 = 0;
	private static final int TAB_2 = 1;
	private static final int TAB_3 = 2;
	
	private FragmentManager fgManager;
	
	private LinearLayout lTabMain;
	private LinearLayout lTabRecommend;
	private LinearLayout lTabFavor;
	
	private Fragment mainFragment;
	private Fragment recommendFragment;
	private Fragment favorFragment;
	private UserInfoFragment userInfoFragment;
	
	private SlidingMenu slidingMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
		fgManager = getSupportFragmentManager();
		selectTab(TAB_1);
		syncDataFromServer();
	}
	private void syncDataFromServer(){
		new Thread(){
			public void run() {
				String uid = LifePreferences.getPreferences().getUID();
				IFavorDao favorDao = new FavorImpl();
				URLParam param = new URLParam(null);
				param.addParam("cmd", URLProtocol.CMD_FAVOR);
				param.addParam("uid", uid);
				String jsonStr = HttpConnection.httpGet(URLProtocol.ROOT, param);
				//与服务器连接失败
				if(jsonStr == null){
					return;
				}
				try {
					JSONObject json = new JSONObject(jsonStr);
					int code = json.getInt("code");
					//有新增数据返回
					if (code == 0) {
						JSONArray jrray = json.getJSONArray("list");
						int len = jrray.length();
						List<Favor> favors = new ArrayList<Favor>();
						for (int i = 0; i < len; i++) {
							JSONObject jo = jrray.getJSONObject(i);
							Favor favor = new Favor();
							favor.tid = jo.getInt("tid");
							favor.type = jo.getInt("type");
							favor.uid = uid;
							favors.add(favor);
						}
						favorDao.updateFavorList(favors);
						return;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}
	
	private void selectTab(int pos){
		// 开启一个Fragment事务  
        FragmentTransaction transaction = fgManager.beginTransaction();  
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况  
        hideFragments(transaction);
		switch(pos){
		case TAB_1:
            if (mainFragment == null)  
            {  
                // 如果mainFragment为空，则创建一个并添加到界面上  
            	mainFragment = new MainFragment();  
                transaction.add(R.id.content, mainFragment);  
            } else  
            {  
                // 如果mainFragment不为空，则直接将它显示出来  
                transaction.show(mainFragment);  
            }  
			break;
		case TAB_2:
			if (recommendFragment == null)  
            {  
                // 如果recommendFragment为空，则创建一个并添加到界面上  
				recommendFragment = new RecommendFragment();  
                transaction.add(R.id.content, recommendFragment);  
            } else  
            {  
                // 如果recommendFragment不为空，则直接将它显示出来  
                transaction.show(recommendFragment);  
            } 
			break;
		case TAB_3:
			if (favorFragment == null)  
            {  
                // 如果favorFragment为空，则创建一个并添加到界面上  
				favorFragment = new FavorFragment();  
                transaction.add(R.id.content, favorFragment);  
            } else  
            {  
                // 如果favorFragment不为空，则直接将它显示出来  
                transaction.show(favorFragment);  
            } 
			break;
		}
		transaction.commit();
	}
	
	private void initEvent(){
		lTabMain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectTab(TAB_1);
			}
		});
		lTabRecommend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectTab(TAB_2);
			}
		});		
		lTabFavor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectTab(TAB_3);
			}
		});
		
		
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		
		lTabMain = (LinearLayout) findViewById(R.id.tab_1);
		lTabRecommend = (LinearLayout) findViewById(R.id.tab_2);
		lTabFavor = (LinearLayout) findViewById(R.id.tab_3);	
		
		slidingMenu = (SlidingMenu) findViewById(R.id.slidingmenu);
	}
	
	private void hideFragments(FragmentTransaction transaction) {
		if (mainFragment != null) {
			transaction.hide(mainFragment);
		}
		if (recommendFragment != null) {
			transaction.hide(recommendFragment);
		}
		if (favorFragment != null) {
			transaction.hide(favorFragment);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(slidingMenu.isOpened()){
			slidingMenu.close();
		}else{
			super.onBackPressed();
		}
	}

//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		// TODO Auto-generated method stub
//		if(slidingMenu.isOpened() && ev.getAction() == MotionEvent.ACTION_DOWN){
//			int pos_X = (int) ev.getX();
//			if(pos_X > slidingMenu.getMenuWidth()){
//				return true;
//			}
//		}
//		return super.dispatchTouchEvent(ev);
//	}
//	
	public void setUserInfoFragment(UserInfoFragment userInfoFragment){
		this.userInfoFragment = userInfoFragment;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		userInfoFragment.onActivityResult(requestCode, resultCode, data);
	}
}
