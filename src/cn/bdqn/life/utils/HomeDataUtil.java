package cn.bdqn.life.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

import cn.bdqn.life.R;
import cn.bdqn.life.data.HomeData;

public class HomeDataUtil {
	/**����ҳ��ʾ������*/
	public static List<HomeData> getHomeData(Context context) {
		
		List<HomeData> datas = new ArrayList<HomeData>();
		Resources resources = context.getResources();
		
		HomeData movie = new HomeData();
		movie.setName(resources.getString(R.string.h_movie));
		movie.setPicture(BitmapFactory.decodeResource(resources,R.drawable.watchmovie));
		
		HomeData delicious = new HomeData();
		delicious.setName(resources.getString(R.string.h_eat));
		delicious.setPicture(BitmapFactory.decodeResource(resources, R.drawable.delicious));
		
		HomeData display = new HomeData();
		display.setName(resources.getString(R.string.h_display));
		display.setPicture(BitmapFactory.decodeResource(resources, R.drawable.display));
		
		HomeData play = new HomeData();
		play.setName(resources.getString(R.string.h_play));
		play.setPicture(BitmapFactory.decodeResource(resources, R.drawable.seeshow));
		
		datas.add(movie);
		datas.add(delicious);
		datas.add(display);
		datas.add(play);
		return datas;
	}
}
