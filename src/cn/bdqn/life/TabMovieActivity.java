package cn.bdqn.life;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
/*
 * 看电影界面
 */
public class TabMovieActivity extends TabActivity {

	private TabHost tabHost;
	private final static String TAB_TAG_MOVIE = "tab_tag_movie";//正在上映
	private final static String TAB_TAG_WILLMOVIE = "tab_tag_willmovie";//即将上映
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabmovie_layout);  
		tabHost=this.getTabHost();
        TabView view = null;  
        
        // 正在上映
        view = new TabView(this, R.drawable.movie_n, R.drawable.movie_s);
        view.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.movie_n));  
        TabSpec MovieSpec=tabHost.newTabSpec("movieing");  
        MovieSpec.setIndicator(view);  
        Intent movieIntent = new Intent(this, WatchMovieActivity.class);  
        MovieSpec.setContent(movieIntent);  
		
        //即将上映 
        view = new TabView(this, R.drawable.movie_will_n, R.drawable.movie_will_s);  
        view.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.movie_will_n));        
        TabSpec willMovieSpec=tabHost.newTabSpec("willmovie");  
        willMovieSpec.setIndicator(view);  
        Intent willMovie = new Intent(this, WillMovieActivity.class);  
        willMovieSpec.setContent(willMovie);  		 		
			
		//把每个tabspec装入tabHost容器	
        tabHost.addTab(MovieSpec);  
        tabHost.addTab(willMovieSpec); 
        
        //设置默认选择的选项卡
        loadTab(this.getIntent());
	}
	
	/** 使某一项处于选中状态*/
	public void loadTab(Intent intent){
		Bundle bundle=intent.getExtras();
		if(bundle != null && bundle.size() > 0) {
			String tagName = bundle.getString("tabName");
			if (TAB_TAG_MOVIE.equals(tagName)) {//正在上映
				 tabHost.setCurrentTab(0); 							
			} else if (TAB_TAG_WILLMOVIE.equals(tagName)) {//即将上映
				 tabHost.setCurrentTab(1); 	
			}
		}else{
			 tabHost.setCurrentTab(0); 
		}
	}
	/**创建每个选项卡*/
	private  class TabView extends LinearLayout {  
	    ImageView imageView ;  
		public TabView(Context c, int drawable, int drawableselec) {  
		    super(c);  
		    imageView = new ImageView(c);  
		    StateListDrawable listDrawable = new StateListDrawable();  
		    listDrawable.addState(SELECTED_STATE_SET, this.getResources().getDrawable(drawableselec));  
		    listDrawable.addState(ENABLED_STATE_SET, this.getResources().getDrawable(drawable));  
		    imageView.setImageDrawable(listDrawable);  
		    imageView.setBackgroundColor(Color.TRANSPARENT);  
		    setGravity(Gravity.CENTER);
		    addView(imageView);  
		} 
	}
}
