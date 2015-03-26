package cn.bdqn.life.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import cn.bdqn.life.R;
import cn.bdqn.life.fragment.FavorFragment;
import cn.bdqn.life.fragment.MainFragment;
import cn.bdqn.life.fragment.RecommendFragment;
import cn.bdqn.life.fragment.UserInfoFragment;

public class MainActivity extends FragmentActivity {
	
	private ViewPager viewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragments;
	
	private LinearLayout lTabMain;
	private LinearLayout lTabRecommend;
	private LinearLayout lTabFavor;
	private LinearLayout lTabUserInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
	}
	
	private void initEvent(){
		lTabMain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		lTabRecommend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});		
		lTabFavor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		lTabUserInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		
		lTabMain = (LinearLayout) findViewById(R.id.tab_1);
		lTabRecommend = (LinearLayout) findViewById(R.id.tab_2);
		lTabFavor = (LinearLayout) findViewById(R.id.tab_3);
		lTabUserInfo = (LinearLayout) findViewById(R.id.tab_4);
		
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		Fragment mainFragment = new MainFragment();
		Fragment recommendFragment = new RecommendFragment();
		Fragment favorFragment = new FavorFragment();
		Fragment userInfoFragment = new UserInfoFragment();
		
		mFragments = new ArrayList<Fragment>();
		mFragments.add(mainFragment);
		mFragments.add(recommendFragment);
		mFragments.add(favorFragment);
		mFragments.add(userInfoFragment);
		
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mFragments.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return mFragments.get(arg0);
			}
		};
		viewPager.setAdapter(mAdapter);
	}
}
