package cn.bdqn.life.dao.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.bdqn.life.MyApplication;
import cn.bdqn.life.dao.IRecommendDao;
import cn.bdqn.life.database.LifeDatabaseOpenHelper;
import cn.bdqn.life.entity.Exhibition;
import cn.bdqn.life.entity.FilmRecommend;
import cn.bdqn.life.entity.Food;
import cn.bdqn.life.entity.Recommend;
import cn.bdqn.life.entity.Show;

public class RecommendImpl implements IRecommendDao {

	@Override
	public List<FilmRecommend> getFilmRecommendList() {
		// TODO Auto-generated method stub
		List<FilmRecommend> filmList = new ArrayList<FilmRecommend>();
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		
		String sql = "SELECT film.[id],"
						  + "film.[name],"
						  + "film.[descr],"
						  + "film.[image],"
						  + "film.[player],"
						  + "film.[time],"
						  + "film.[timelong],"
						  + "film.[type] "
					      + "FROM film,recommend WHERE (recommend.[type] = '1') AND "
											        + "(recommend.[t_id] = film.[id]);";
		Cursor cursor = db.rawQuery(sql,new String[]{});
		
		while (cursor != null && cursor.moveToNext()) {
			FilmRecommend film = new FilmRecommend();
			film.id = cursor.getInt(cursor.getColumnIndex("id"));
			film.name = cursor.getString(cursor.getColumnIndex("name"));
			film.type = cursor.getString(cursor.getColumnIndex("type"));
			film.time = cursor.getString(cursor.getColumnIndex("time"));
			film.player = cursor.getString(cursor.getColumnIndex("player"));
			film.image = cursor.getString(cursor.getColumnIndex("image"));
			film.desc = cursor.getString(cursor.getColumnIndex("descr"));
			film.timelong = cursor.getString(cursor.getColumnIndex("timelong"));
			film.isShowing = true;
			
			filmList.add(film);
		}
		cursor.close();
		
		sql = "SELECT film.[id],"
				   + "film.[name],"
				   + "film.[descr],"
				   + "film.[image],"
				   + "film.[player],"
				   + "film.[time],"
				   + "film.[timelong],"
				   + "film.[type] "
			       + "FROM film,recommend WHERE (recommend.[type] = '2') AND "
									         + "(recommend.[t_id] = film.[id]);";
		cursor = db.rawQuery(sql,new String[]{});

		while (cursor != null && cursor.moveToNext()) {
			FilmRecommend film = new FilmRecommend();
			film.id = cursor.getInt(cursor.getColumnIndex("id"));
			film.name = cursor.getString(cursor.getColumnIndex("name"));
			film.type = cursor.getString(cursor.getColumnIndex("type"));
			film.time = cursor.getString(cursor.getColumnIndex("time"));
			film.player = cursor.getString(cursor.getColumnIndex("player"));
			film.image = cursor.getString(cursor.getColumnIndex("image"));
			film.desc = cursor.getString(cursor.getColumnIndex("descr"));
			film.timelong = cursor.getString(cursor.getColumnIndex("timelong"));
			film.isShowing = false;
			
			filmList.add(film);
		}
		cursor.close();
		db.close();
		return filmList;
	}

	@Override
	public List<Food> getFoodRecommendList() {
		// TODO Auto-generated method stub
		List<Food> foodRecommendList = new ArrayList<Food>();
		return foodRecommendList;
	}

	@Override
	public List<Show> getShowRecommendList() {
		// TODO Auto-generated method stub
		List<Show> showRecommendList = new ArrayList<Show>();
		return showRecommendList;
	}

	@Override
	public List<Exhibition> getExhibitionRecommendList() {
		// TODO Auto-generated method stub
		List<Exhibition> exhibitionRecommendList = new ArrayList<Exhibition>();
		return exhibitionRecommendList;
	}

	@Override
	public void updateRecommendList(List<Recommend> recommendList) {
		// TODO Auto-generated method stub
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		
		db.execSQL("TRUNCATE TABLE recommend");
		
		for(int i = 0; i < recommendList.size(); i++){
			Recommend recommend = recommendList.get(i);
			ContentValues v = new ContentValues();
			v.put("t_id", recommend.tid);
			v.put("type", recommend.type);
			db.insert("recommend", null, v);
		}
		
		db.close();
	}

}
