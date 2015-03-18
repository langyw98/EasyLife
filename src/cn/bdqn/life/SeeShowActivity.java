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

/** 看演出 */
public class SeeShowActivity extends TabActivity {

	private TabHost tabHost;
	private String TAB_TAG_CONCERT = "tab_tag_concert";
	private String TAB_TAG_MUSIC = "tab_tag_music";
	private String TAB_TAG_PLAY = "tab_tag_play";
	private String TAB_TAG_PEKINGOPERA = "tab_tag_pekingopera";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.seeshow);
		tabHost = getTabHost();
		TabView view = null;

		// 演唱会
		view = new TabView(this, R.drawable.concert_n, R.drawable.concert_s);
		view.setBackgroundDrawable(this.getResources().getDrawable(
				R.drawable.concert_n));
		TabSpec concertSpec = tabHost.newTabSpec("concert");
		concertSpec.setIndicator(view);
		Intent concertIntent = new Intent(this, ConcertActivity.class);
		concertSpec.setContent(concertIntent);

		// 音乐会
		view = new TabView(this, R.drawable.music_n, R.drawable.music_s);
		view.setBackgroundDrawable(this.getResources().getDrawable(
				R.drawable.music_n));
		TabSpec musicSpec = tabHost.newTabSpec("music");
		musicSpec.setIndicator(view);
		Intent musicIntent = new Intent(this, MusicActivity.class);
		musicSpec.setContent(musicIntent);
		
		// 话剧
		view = new TabView(this, R.drawable.play_n, R.drawable.play_s);
		view.setBackgroundDrawable(this.getResources().getDrawable(
				R.drawable.play_n));
		TabSpec playSpec = tabHost.newTabSpec("play");
		playSpec.setIndicator(view);
		Intent playIntent = new Intent(this, PlayActivity.class);
		playSpec.setContent(playIntent);
		
		// 戏曲
		view = new TabView(this, R.drawable.pko_n, R.drawable.pko_s);
		view.setBackgroundDrawable(this.getResources().getDrawable(
				R.drawable.pko_n));
		TabSpec pkoSpec = tabHost.newTabSpec("pko");
		pkoSpec.setIndicator(view);
		Intent pkoIntent = new Intent(this, PkoActivity.class);
		pkoSpec.setContent(pkoIntent);

		// 把每个tabspec装入tabHost容器
		tabHost.addTab(concertSpec);
		tabHost.addTab(musicSpec);
		tabHost.addTab(playSpec);
		tabHost.addTab(pkoSpec);

		// 设置默认选择的选项卡
		loadTab(this.getIntent());
	}

	/** 使某一项处于选中状态 */
	public void loadTab(Intent intent) {
		Bundle bundle = intent.getExtras();
		if (bundle != null && bundle.size() > 0) {
			String tagName = bundle.getString("tabName");
			if (TAB_TAG_CONCERT.equals(tagName)) {
				tabHost.setCurrentTab(0);
			} else if (TAB_TAG_MUSIC.equals(tagName)) {
				tabHost.setCurrentTab(1);
			}else if(TAB_TAG_PLAY.equals(tagName)){
				tabHost.setCurrentTab(2);
			}else if(TAB_TAG_PEKINGOPERA.equals(tagName)){
				tabHost.setCurrentTab(3);
			}
		} else {
			tabHost.setCurrentTab(0);
		}
	}

	private class TabView extends LinearLayout {
		ImageView imageView;

		public TabView(Context c, int drawable, int drawableselec) {
			super(c);
			imageView = new ImageView(c);
			StateListDrawable listDrawable = new StateListDrawable();
			listDrawable.addState(SELECTED_STATE_SET, this.getResources()
					.getDrawable(drawableselec));
			listDrawable.addState(ENABLED_STATE_SET, this.getResources()
					.getDrawable(drawable));
			imageView.setImageDrawable(listDrawable);
			imageView.setBackgroundColor(Color.TRANSPARENT);
			setGravity(Gravity.CENTER);
			addView(imageView);
		}
	}
}
