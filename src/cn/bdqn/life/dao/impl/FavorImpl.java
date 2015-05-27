package cn.bdqn.life.dao.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.bdqn.life.MyApplication;
import cn.bdqn.life.dao.IFavorDao;
import cn.bdqn.life.database.LifeDatabaseOpenHelper;
import cn.bdqn.life.entity.Exhibition;
import cn.bdqn.life.entity.Favor;
import cn.bdqn.life.entity.FilmFavor;
import cn.bdqn.life.entity.FilmRecommend;
import cn.bdqn.life.entity.Food;
import cn.bdqn.life.entity.Recommend;
import cn.bdqn.life.entity.Show;

public class FavorImpl implements IFavorDao {

	@Override
	public void updateFavorList(List<Favor> favorList) {
		// TODO Auto-generated method stub
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		//delete from '表名';   
//		select * from sqlite_sequence;   
//		update sqlite_sequence set seq=0 where name='表名';
		db.execSQL("DROP TABLE favor");
		db.execSQL("CREATE TABLE favor (" +
				  "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
				  "uid VARCHAER,"+
				  "type INTEGER,"+
				  "t_id INTEGER)");
		
		for(int i = 0; i < favorList.size(); i++){
			Favor favor = favorList.get(i);
			ContentValues v = new ContentValues();
			v.put("t_id", favor.tid);
			v.put("type", favor.type);
			v.put("uid", favor.uid);
			db.insert("favor", null, v);
		}
		
		db.close();
	}


	@Override
	public List<FilmFavor> getFilmFavorList(String uid) {
		// TODO Auto-generated method stub
		List<FilmFavor> filmList = new ArrayList<FilmFavor>();
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
					      + "FROM film,favor WHERE (favor.[type] = '1') AND "
											        + "(favor.[t_id] = film.[id]) AND"
											        + "(favor.[uid] = '" + uid + "');";
		Cursor cursor = db.rawQuery(sql,new String[]{});
		
		while (cursor != null && cursor.moveToNext()) {
			FilmFavor film = new FilmFavor();
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
			       + "FROM film,favor WHERE (favor.[type] = '2') AND "
									         + "(favor.[t_id] = film.[id]) AND"
											 + "(favor.[uid] = '" + uid + "');";
		cursor = db.rawQuery(sql,new String[]{});

		while (cursor != null && cursor.moveToNext()) {
			FilmFavor film = new FilmFavor();
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
	public List<Food> getFoodFavorList(String uid) {
		// TODO Auto-generated method stub
		return new ArrayList<Food>();
	}

	@Override
	public List<Show> getShowFavorList(String uid) {
		// TODO Auto-generated method stub
		return new ArrayList<Show>();
	}

	@Override
	public List<Exhibition> getExhibitionFavorList(String uid) {
		// TODO Auto-generated method stub
		return new ArrayList<Exhibition>();
	}

	@Override
	public void addFavor(Favor favor) {
		// TODO Auto-generated method stub
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();

		ContentValues v = new ContentValues();
		v.put("t_id", favor.tid);
		v.put("type", favor.type);
		v.put("uid", favor.uid);
		db.insert("favor", null, v);

		db.close();
	}


	@Override
	public void removeFavor(Favor favor) {
		// TODO Auto-generated method stub
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] args = new String[]{"" + favor.tid, "" + favor.type, "" + favor.uid};
		db.delete("favor", "t_id = ? AND type = ? AND uid = ?", args);
		
		db.close();
	}


	@Override
	public boolean isFavor(String uid, int type, int tid) {
		// TODO Auto-generated method stub
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		
		Cursor cursor = db.query("favor", new String[]{"id"}, "type = ? AND t_id = ? AND uid = ?",new String[]{type+"", tid+"", uid+""}, null, null, null);
		if(cursor != null && cursor.moveToNext()){
			return true;
		}
		return false;
	}

}
