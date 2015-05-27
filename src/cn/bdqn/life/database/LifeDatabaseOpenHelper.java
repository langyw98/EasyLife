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
		 * ����Film��id(���),name(��Ӱ��),type(����),time(��ӳʱ��),player(��Ա)
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
		 * ����UpcomingFilm��id(���),name(��Ӱ��),type(����),time(��ӳʱ��),player(��Ա)
		 *         image(ͼƬ),descr(��Ӱ����),timelong(��Ӱʱ��)
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
		 * �������۱�id(���),username(����������),content(��������),time(����ʱ��),
		 * type(��������),tid(������Ŀ�ڸ������е�id)
		 * type(��������)��1.������ӳ�ĵ�Ӱ���ۣ�2.������ӳ�ĵ�Ӱ����
		 */
		db.execSQL("CREATE TABLE comment ("+
				  "id INTEGER NOT NULL PRIMARY KEY,"+
				  "username VARCHAER,"+
				  "content VARCHAER,"+
				  "time VARCHAER,"+
				  "type INTEGER,"+
				  "tid INTEGER)");
		/*
		 *�����Ƽ���id(���),type(�Ƽ�����),t_id(�Ƽ���Ŀ�ڴ������ж�Ӧ��id)
		 *type(�Ƽ�����)��1.������ӳ�ĵ�Ӱ��2.������ӳ�ĵ�Ӱ��3.��ʳ��4.�ݳ���5.չ��*/
		db.execSQL("CREATE TABLE recommend (" +
				  "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
				  "type INTEGER,"+
				  "t_id INTEGER)");
		/*
		 *�����Ƽ���id(���),type(�Ƽ�����),t_id(�Ƽ���Ŀ�ڴ������ж�Ӧ��id)
		 *type(�Ƽ�����)��1.������ӳ�ĵ�Ӱ��2.������ӳ�ĵ�Ӱ��3.��ʳ��4.�ݳ���5.չ��*/
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
		 * ����UpcomingFilm��id(���),name(��Ӱ��),type(����),time(��ӳʱ��),player(��Ա)
		 *         image(ͼƬ),descr(��Ӱ����),timelong(��Ӱʱ��)
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
