package cn.bdqn.life.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import cn.bdqn.life.R;
import cn.bdqn.life.fragment.FilmListFragment;

public class FilmMainActivity extends FragmentActivity {
	private final int TAB_INDEX_RECENT = 0;
	private final int TAB_INDEX_UPCOMING = 1;
	
	private ViewPager viewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragments;
	private LinearLayout tab_recent;
	private LinearLayout tab_upcoming;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainfilm);
		initView();
		initEvent();
		selectTab(TAB_INDEX_RECENT);
	}
	
	private void selectTab(int index){
		resetTabState();
		switch(index){
		case TAB_INDEX_RECENT:
			tab_recent.setBackgroundColor(Color.YELLOW);
			break;
		case TAB_INDEX_UPCOMING:
			tab_upcoming.setBackgroundColor(Color.YELLOW);
			break;
		}
	}
	
	private void resetTabState(){
		tab_recent.setBackgroundColor(Color.TRANSPARENT);
		tab_upcoming.setBackgroundColor(Color.TRANSPARENT);
	}
	
	private void initEvent(){
		tab_recent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectTab(0);
				viewPager.setCurrentItem(0);
			}
		});
		
		tab_upcoming.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				selectTab(1);
				viewPager.setCurrentItem(1);
			}
		});
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				selectTab(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void initView(){
		tab_recent = (LinearLayout) findViewById(R.id.tab_recent);
		tab_upcoming = (LinearLayout) findViewById(R.id.tab_upcoming);
		
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		FilmListFragment recentFragment = new FilmListFragment(FilmListFragment.FRAGMENTTYPE_RECENT);
		FilmListFragment upcomingFragment = new FilmListFragment(FilmListFragment.FRAGMENTTYPE_UPCOMING);
		
		mFragments = new ArrayList<Fragment>();
		
		mFragments.add(recentFragment);
		mFragments.add(upcomingFragment);
		
		
		
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
