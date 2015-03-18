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
 * ����Ӱ����
 */
public class TabMovieActivity extends TabActivity {

	private TabHost tabHost;
	private final static String TAB_TAG_MOVIE = "tab_tag_movie";//������ӳ
	private final static String TAB_TAG_WILLMOVIE = "tab_tag_willmovie";//������ӳ
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabmovie_layout);  
		tabHost=this.getTabHost();
        TabView view = null;  
        
        // ������ӳ
        view = new TabView(this, R.drawable.movie_n, R.drawable.movie_s);
        view.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.movie_n));  
        TabSpec MovieSpec=tabHost.newTabSpec("movieing");  
        MovieSpec.setIndicator(view);  
        Intent movieIntent = new Intent(this, WatchMovieActivity.class);  
        MovieSpec.setContent(movieIntent);  
		
        //������ӳ 
        view = new TabView(this, R.drawable.movie_will_n, R.drawable.movie_will_s);  
        view.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.movie_will_n));        
        TabSpec willMovieSpec=tabHost.newTabSpec("willmovie");  
        willMovieSpec.setIndicator(view);  
        Intent willMovie = new Intent(this, WillMovieActivity.class);  
        willMovieSpec.setContent(willMovie);  		 		
			
		//��ÿ��tabspecװ��tabHost����	
        tabHost.addTab(MovieSpec);  
        tabHost.addTab(willMovieSpec); 
        
        //����Ĭ��ѡ���ѡ�
        loadTab(this.getIntent());
	}
	
	/** ʹĳһ���ѡ��״̬*/
	public void loadTab(Intent intent){
		Bundle bundle=intent.getExtras();
		if(bundle != null && bundle.size() > 0) {
			String tagName = bundle.getString("tabName");
			if (TAB_TAG_MOVIE.equals(tagName)) {//������ӳ
				 tabHost.setCurrentTab(0); 							
			} else if (TAB_TAG_WILLMOVIE.equals(tagName)) {//������ӳ
				 tabHost.setCurrentTab(1); 	
			}
		}else{
			 tabHost.setCurrentTab(0); 
		}
	}
	/**����ÿ��ѡ�*/
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
