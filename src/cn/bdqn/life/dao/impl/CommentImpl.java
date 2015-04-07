package cn.bdqn.life.dao.impl;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.bdqn.life.MyApplication;
import cn.bdqn.life.dao.ICommentDao;
import cn.bdqn.life.database.LifeDatabaseOpenHelper;
import cn.bdqn.life.entity.Comment;

public class CommentImpl implements ICommentDao {

	@Override
	public List<Comment> getComments(int type, int tid, int posStart,
			int pageLength) {
		LifeDatabaseOpenHelper helper = new LifeDatabaseOpenHelper(
				MyApplication.getInstance());
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] columns = { "id", "username", "type", "time", "content", "tid" };
		String selection = "type = ? AND tid = ?";
		String[] selectionArgs = { "" + type, "" +  tid};
		String orderBy = "id desc";
		String limit = posStart + "," + pageLength;
		Cursor cursor = db.query("comment", columns, selection, selectionArgs,
				null, null, orderBy, limit);
		List<Comment> comments = new ArrayList<Comment>();
		if (cursor != null) {
			while (cursor.moveToNext()) {
				Comment comment = new Comment();
				comment.id = cursor.getInt(cursor.getColumnIndex("id"));
				comment.userName = cursor.getString(cursor
						.getColumnIndex("username"));
				comment.type = cursor.getInt(cursor.getColumnIndex("type"));
				comment.time = cursor.getString(cursor.getColumnIndex("time"));
				comment.content = cursor.getString(cursor
						.getColumnIndex("content"));
				comment.tid = cursor.getInt(cursor.getColumnIndex("tid"));
				comments.add(comment);
			}
			cursor.close();
		}
		db.close();
		return comments;
	}

	@Override
	public void addComment(Comment comment) {
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

}
