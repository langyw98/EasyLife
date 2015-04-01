package cn.bdqn.life.dao.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.bdqn.life.MyApplication;
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
		values.put("desc", film.desc);
		values.put("image", film.image);
		values.put("player", film.player);
		values.put("time", film.time);
		values.put("timelong", film.timelong);
		values.put("type", film.type);
		values.put("id",film.id);
		db.insert("film", null, values);
		db.close();
	}

	@Override
	public void addFilmComment(Comment comment) {
		// TODO Auto-generated method stub
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("id", comment.id);
		values.put("content", comment.content);
		values.put("time", comment.time);
		values.put("username", comment.userName);
		values.put("tid", comment.tid);
		values.put("type", comment.type);
		db.insert("comment", null, values);
		db.close();
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
			film.desc = cursor.getString(cursor.getColumnIndex("desc"));
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
		if (cursor != null && cursor.moveToNext()) {
			while (cursor.moveToNext()) {
				Film film = new Film();
				film.id = cursor.getInt(cursor.getColumnIndex("id"));
				film.name = cursor.getString(cursor.getColumnIndex("name"));
				film.type = cursor.getString(cursor.getColumnIndex("type"));
				film.time = cursor.getString(cursor.getColumnIndex("time"));
				film.player = cursor.getString(cursor.getColumnIndex("player"));
				film.image = cursor.getString(cursor.getColumnIndex("image"));
				film.desc = cursor.getString(cursor.getColumnIndex("desc"));
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
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] columns = {"id","username","type","time","content","tid"};
		String orderBy = "id desc";
		String limit = posStart + "," + pageLength;
		Cursor cursor = db.query("comment", columns, null, null, null, null, orderBy ,limit);
		List<Comment> comments = new ArrayList<Comment>();
		if (cursor != null && cursor.moveToNext()) {
			while (cursor.moveToNext()) {
				Comment comment = new Comment();
				comment.id = cursor.getInt(cursor.getColumnIndex("id"));
				comment.userName = cursor.getString(cursor.getColumnIndex("username"));
				comment.type = cursor.getInt(cursor.getColumnIndex("type"));
				comment.time = cursor.getString(cursor.getColumnIndex("time"));
				comment.content = cursor.getString(cursor.getColumnIndex("content"));
				comment.tid = cursor.getInt(cursor.getColumnIndex("tid"));
				comments.add(comment);
			}
			cursor.close();
		}
		db.close();
		return comments;
	}

}
