package cn.bdqn.life.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import cn.bdqn.life.R;
import cn.bdqn.life.fragment.FavorFragment;
import cn.bdqn.life.fragment.FilmListFragment;

public class FilmMainActivity extends FragmentActivity {
	private ViewPager viewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragments;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainfilm);
		initView();
		initEvent();
	}
	
	private void initEvent(){
		
	}
	
	private void initView(){
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
