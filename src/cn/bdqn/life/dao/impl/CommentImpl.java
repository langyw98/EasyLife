package cn.bdqn.life.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.bdqn.life.MyApplication;
import cn.bdqn.life.dao.ICommentDao;
import cn.bdqn.life.database.LifeDatabaseOpenHelper;
import cn.bdqn.life.entity.Comment;
import cn.bdqn.life.entity.Film;
import cn.bdqn.life.net.HttpConnection;
import cn.bdqn.life.net.URLParam;
import cn.bdqn.life.net.URLProtocol;

public class CommentImpl implements ICommentDao {

	@Override
	public List<Comment> getComments(int type, int tid, int startPos,
			int pageLength) {
		List<Comment> comments = new ArrayList<Comment>();
		URLParam param = new URLParam(null);
		param.addParam("startPos", startPos);
		param.addParam("type", type);
		param.addParam("tid", tid);
		param.addParam("pageLength", pageLength);
		param.addParam("cmd", URLProtocol.CMD_GET_COMMENT);
		String jsonStr = HttpConnection.httpGet(URLProtocol.ROOT, param);
		//与服务器连接失败
		if(jsonStr == null){
			return null;
		}
		try {
			JSONObject json = new JSONObject(jsonStr);
			int code = json.getInt("code");
			//有新增数据返回
			if (code == 0) {
				JSONArray jrray = json.getJSONArray("list");
				int len = jrray.length();
				for (int i = 0; i < len; i++) {
					JSONObject jo = jrray.getJSONObject(i);
					Comment comment = new Comment();
					comment.id = jo.getInt("recid");
					comment.tid = jo.getInt("tid");
					comment.type = jo.getInt("type");
					comment.userName = jo.getString("name");
					comment.time = jo.getString("time");
					comment.content = jo.getString("content");
					comments.add(comment);
				}
				return comments;
			}else if(code == 2){
				return comments;
			}else{
				return null;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
