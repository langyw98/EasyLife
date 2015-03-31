package cn.bdqn.life.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import cn.bdqn.life.R;
import cn.bdqn.life.fragment.FavorFragment;
import cn.bdqn.life.fragment.MainFragment;
import cn.bdqn.life.fragment.RecommendFragment;

public class MainActivity extends FragmentActivity {
	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initEvent();
		fgManager = getSupportFragmentManager();
		selectTab(TAB_1);
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
		
	}
	
	 private void hideFragments(FragmentTransaction transaction)  
	    {  
	        if (mainFragment != null)  
	        {  
	            transaction.hide(mainFragment);  
	        }  
	        if (recommendFragment != null)  
	        {  
	            transaction.hide(recommendFragment);  
	        }  
	        if (favorFragment != null)  
	        {  
	            transaction.hide(favorFragment);  
	        }  
	    } 
}
