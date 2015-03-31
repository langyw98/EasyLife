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
		// ����һ��Fragment����  
        FragmentTransaction transaction = fgManager.beginTransaction();  
        // �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����  
        hideFragments(transaction);
		switch(pos){
		case TAB_1:
            if (mainFragment == null)  
            {  
                // ���mainFragmentΪ�գ��򴴽�һ������ӵ�������  
            	mainFragment = new MainFragment();  
                transaction.add(R.id.content, mainFragment);  
            } else  
            {  
                // ���mainFragment��Ϊ�գ���ֱ�ӽ�����ʾ����  
                transaction.show(mainFragment);  
            }  
			break;
		case TAB_2:
			if (recommendFragment == null)  
            {  
                // ���recommendFragmentΪ�գ��򴴽�һ������ӵ�������  
				recommendFragment = new RecommendFragment();  
                transaction.add(R.id.content, recommendFragment);  
            } else  
            {  
                // ���recommendFragment��Ϊ�գ���ֱ�ӽ�����ʾ����  
                transaction.show(recommendFragment);  
            } 
			break;
		case TAB_3:
			if (favorFragment == null)  
            {  
                // ���favorFragmentΪ�գ��򴴽�һ������ӵ�������  
				favorFragment = new FavorFragment();  
                transaction.add(R.id.content, favorFragment);  
            } else  
            {  
                // ���favorFragment��Ϊ�գ���ֱ�ӽ�����ʾ����  
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
