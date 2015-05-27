package cn.bdqn.life.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LifeDatabaseOpenHelper extends SQLiteOpenHelper {
	private static final String NAME = "db_life.db";
	private static final int VERSION = 3;
	public LifeDatabaseOpenHelper(Context context) {
		super(context, NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/*
		 * 创建Film表：id(编号),name(电影名),type(类型),time(上映时间),player(演员)
		 *         image(图片),descr(电影描述),timelong(电影时长)
		 */
		db.execSQL("CREATE TABLE film ("+
				  "id INTEGER NOT NULL PRIMARY KEY,"+
				  "name VARCHAER,"+
				  "type VARCHAER,"+
				  "time VARCHAER,"+
				  "player VARCHAER,"+
				  "image VARCHAER,"+
				  "descr VARCHAER,"+
				  "timelong VARCHAER)");
		/*
		 * 创建UpcomingFilm表：id(编号),name(电影名),type(类型),time(上映时间),player(演员)
		 *         image(图片),descr(电影描述),timelong(电影时长)
		 */
		db.execSQL("CREATE TABLE upcomingfilm ("+
				  "id INTEGER NOT NULL PRIMARY KEY,"+
				  "name VARCHAER,"+
				  "type VARCHAER,"+
				  "time VARCHAER,"+
				  "player VARCHAER,"+
				  "image VARCHAER,"+
				  "descr VARCHAER,"+
				  "timelong VARCHAER)");
		/*
		 * 创建评论表：id(编号),username(评论人姓名),content(评论内容),time(发表时间),
		 * type(评论类型),tid(评论条目在该类型中的id)
		 * type(评论类型)：1.正在上映的电影评论；2.即将上映的电影评论
		 */
		db.execSQL("CREATE TABLE comment ("+
				  "id INTEGER NOT NULL PRIMARY KEY,"+
				  "username VARCHAER,"+
				  "content VARCHAER,"+
				  "time VARCHAER,"+
				  "type INTEGER,"+
				  "tid INTEGER)");
		/*
		 *创建推荐表：id(编号),type(推荐类型),t_id(推荐条目在此类型中对应的id)
		 *type(推荐类型)：1.正在上映的电影；2.即将上映的电影；3.美食；4.演出；5.展览*/
		db.execSQL("CREATE TABLE recommend (" +
				  "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
				  "type INTEGER,"+
				  "t_id INTEGER)");
		/*
		 *创建推荐表：id(编号),type(推荐类型),t_id(推荐条目在此类型中对应的id)
		 *type(推荐类型)：1.正在上映的电影；2.即将上映的电影；3.美食；4.演出；5.展览*/
		db.execSQL("CREATE TABLE favor (" +
				  "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
				  "uid VARCHAER,"+
				  "type INTEGER,"+
				  "t_id INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		/*
		 * 创建UpcomingFilm表：id(编号),name(电影名),type(类型),time(上映时间),player(演员)
		 *         image(图片),descr(电影描述),timelong(电影时长)
		 */
////		if(oldVersion == 1){
//		db.execSQL("CREATE TABLE upcomingfilm ("+
//				  "id INTEGER NOT NULL PRIMARY KEY,"+
//				  "name VARCHAER,"+
//				  "type VARCHAER,"+
//				  "time VARCHAER,"+
//				  "player VARCHAER,"+
//				  "image VARCHAER,"+
//				  "descr VARCHAER,"+
//				  "timelong VARCHAER)");
////		}
	}

}
