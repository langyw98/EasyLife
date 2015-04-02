package cn.bdqn.life.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LifeDatabaseOpenHelper extends SQLiteOpenHelper {
	private static final String NAME = "db_life.db";
	private static final int VERSION = 1;
	public LifeDatabaseOpenHelper(Context context) {
		super(context, NAME, null, VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/*
		 * ����Film����id(���),name(��Ӱ��),type(����),time(��ӳʱ��),player(��Ա)
		 *         image(ͼƬ),descr(��Ӱ����),timelong(��Ӱʱ��)
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
		 * �������۱���id(���),username(����������),content(��������),time(����ʱ��),
		 * type(��������),tid(������Ŀ�ڸ������е�id)
		 */
		db.execSQL("CREATE TABLE comment ("+
				  "id INTEGER NOT NULL PRIMARY KEY,"+
				  "username VARCHAER,"+
				  "content VARCHAER,"+
				  "time VARCHAER,"+
				  "type INTEGER,"+
				  "tid INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}