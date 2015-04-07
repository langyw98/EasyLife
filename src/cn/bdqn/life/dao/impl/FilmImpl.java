package cn.bdqn.life.dao.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.bdqn.life.MyApplication;
import cn.bdqn.life.dao.ICommentDao;
import cn.bdqn.life.dao.IFilmDao;
import cn.bdqn.life.database.LifeDatabaseOpenHelper;
import cn.bdqn.life.entity.Comment;
import cn.bdqn.life.entity.Film;

public class FilmImpl implements IFilmDao {

	@Override
	public void addFilm(Film film) {
		// TODO Auto-generated method stub
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", film.name);
		values.put("descr", film.desc);
		values.put("image", film.image);
		values.put("player", film.player);
		values.put("time", film.time);
		values.put("timelong", film.timelong);
		values.put("type", film.type);
		values.put("id",film.id);
		db.insert("film", null, values);
		db.close();
	}

	public void addFilms(List<Film> films){
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		for(int i = 0; i < films.size(); i++){
			Film film = films.get(i);
			ContentValues values = new ContentValues();
			values.put("name", film.name);
			values.put("descr", film.desc);
			values.put("image", film.image);
			values.put("player", film.player);
			values.put("time", film.time);
			values.put("timelong", film.timelong);
			values.put("type", film.type);
			values.put("id",film.id);
			db.insert("film", null, values);
		}
		db.close();
	}
	
	@Override
	public void addComment(Comment comment) {
		// TODO Auto-generated method stub
		ICommentDao commentDao = new CommentImpl();
		commentDao.addComment(comment);
	}

	@Override
	public Film getFilm(int id) {
		// TODO Auto-generated method stub
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] columns = {"id","name","type","time","player","image","descr","timelong"};
		String selection = "id=?";
		String[] selectionArgs = {""+id};
		Cursor cursor = db.query("film", columns, selection, selectionArgs, null, null, null);
		Film film = null;
		if(cursor != null && cursor.moveToNext()){
			film = new Film();
			film.id = cursor.getInt(cursor.getColumnIndex("id"));
			film.name = cursor.getString(cursor.getColumnIndex("name"));
			film.type = cursor.getString(cursor.getColumnIndex("type"));
			film.time = cursor.getString(cursor.getColumnIndex("time"));
			film.player = cursor.getString(cursor.getColumnIndex("player"));
			film.image = cursor.getString(cursor.getColumnIndex("image"));
			film.desc = cursor.getString(cursor.getColumnIndex("descr"));
			film.timelong = cursor.getString(cursor.getColumnIndex("timelong"));
			cursor.close();
		}
		db.close();
		return film;
	}

	@Override
	public List<Film> getFilms(int posStart, int pageLength) {
		// TODO Auto-generated method stub
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] columns = {"id","name","type","time","player","image","descr","timelong"};
		String orderBy = "id desc";
		String limit = posStart + "," + pageLength;
		Cursor cursor = db.query("film", columns, null, null, null, null, orderBy ,limit);
		List<Film> films = new ArrayList<Film>();
		if (cursor != null) {
			while (cursor.moveToNext()) {
				Film film = new Film();
				film.id = cursor.getInt(cursor.getColumnIndex("id"));
				film.name = cursor.getString(cursor.getColumnIndex("name"));
				film.type = cursor.getString(cursor.getColumnIndex("type"));
				film.time = cursor.getString(cursor.getColumnIndex("time"));
				film.player = cursor.getString(cursor.getColumnIndex("player"));
				film.image = cursor.getString(cursor.getColumnIndex("image"));
				film.desc = cursor.getString(cursor.getColumnIndex("descr"));
				film.timelong = cursor.getString(cursor.getColumnIndex("timelong"));
				films.add(film);
			}
			cursor.close();
		}
		db.close();
		return films;
	}

	@Override
	public List<Comment> getFilmComments(int id, int posStart, int pageLength) {
		// TODO Auto-generated method stub
		ICommentDao commentDao = new CommentImpl();
		List<Comment> comments = commentDao.getComments(1, id, posStart, pageLength);
		return comments;
	}

	@Override
	public int getMaxFilmId() {
		// TODO Auto-generated method stub
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT MAX(id) FROM film", null);
		int result = -1;
		if(cursor != null && cursor.moveToNext()){
			result = cursor.getInt(0);
			cursor.close();
		}
		db.close();
		return result;
	}

	public void addUpcomingFilm(Film film){
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", film.name);
		values.put("descr", film.desc);
		values.put("image", film.image);
		values.put("player", film.player);
		values.put("time", film.time);
		values.put("timelong", film.timelong);
		values.put("type", film.type);
		values.put("id",film.id);
		db.insert("upcomingfilm", null, values);
		db.close();
	}
	public void addUpcomingFilms(List<Film> films){
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		for(int i = 0; i < films.size(); i++){
			Film film = films.get(i);
			ContentValues values = new ContentValues();
			values.put("name", film.name);
			values.put("descr", film.desc);
			values.put("image", film.image);
			values.put("player", film.player);
			values.put("time", film.time);
			values.put("timelong", film.timelong);
			values.put("type", film.type);
			values.put("id",film.id);
			db.insert("upcomingfilm", null, values);
		}
		db.close();
	}

	public Film getUpcomingFilm(int id) {
		// TODO Auto-generated method stub
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(
				MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] columns = { "id", "name", "type", "time", "player", "image",
				"descr", "timelong" };
		String selection = "id=?";
		String[] selectionArgs = { "" + id };
		Cursor cursor = db.query("upcomingfilm", columns, selection, selectionArgs,
				null, null, null);
		Film film = null;
		if (cursor != null && cursor.moveToNext()) {
			film = new Film();
			film.id = cursor.getInt(cursor.getColumnIndex("id"));
			film.name = cursor.getString(cursor.getColumnIndex("name"));
			film.type = cursor.getString(cursor.getColumnIndex("type"));
			film.time = cursor.getString(cursor.getColumnIndex("time"));
			film.player = cursor.getString(cursor.getColumnIndex("player"));
			film.image = cursor.getString(cursor.getColumnIndex("image"));
			film.desc = cursor.getString(cursor.getColumnIndex("descr"));
			film.timelong = cursor.getString(cursor.getColumnIndex("timelong"));
			cursor.close();
		}
		db.close();
		return film;
	}
	
	public List<Film> getUpcomingFilms(int posStart, int pageLength){
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] columns = {"id","name","type","time","player","image","descr","timelong"};
		String orderBy = "id desc";
		String limit = posStart + "," + pageLength;
		Cursor cursor = db.query("upcomingfilm", columns, null, null, null, null, orderBy ,limit);
		List<Film> films = new ArrayList<Film>();
		if (cursor != null) {
			while (cursor.moveToNext()) {
				Film film = new Film();
				film.id = cursor.getInt(cursor.getColumnIndex("id"));
				film.name = cursor.getString(cursor.getColumnIndex("name"));
				film.type = cursor.getString(cursor.getColumnIndex("type"));
				film.time = cursor.getString(cursor.getColumnIndex("time"));
				film.player = cursor.getString(cursor.getColumnIndex("player"));
				film.image = cursor.getString(cursor.getColumnIndex("image"));
				film.desc = cursor.getString(cursor.getColumnIndex("descr"));
				film.timelong = cursor.getString(cursor.getColumnIndex("timelong"));
				films.add(film);
			}
			cursor.close();
		}
		db.close();
		return films;
	}
	
	public List<Comment> getUpcomingComments(int id, int posStart, int pageLength){
		// TODO Auto-generated method stub
		ICommentDao commentDao = new CommentImpl();
		List<Comment> comments = commentDao.getComments(2, id, posStart, pageLength);
		return comments;
	}
	
	public int getMaxUpcomingFilmId(){
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT MAX(id) FROM upcomingfilm", null);
		int result = -1;
		if(cursor != null && cursor.moveToNext()){
			result = cursor.getInt(0);
			cursor.close();
		}
		db.close();
		return result;
	}
}
