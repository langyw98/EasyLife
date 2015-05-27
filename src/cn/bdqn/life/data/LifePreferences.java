package cn.bdqn.life.data;

import cn.bdqn.life.MyApplication;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

public class LifePreferences {

	private  final String NAME="leilife";
	private  final String KEY_UID = "uid";
	private  final String KEY_CITY_ID = "city_id";
	private  final String AUTO_LOGIN = "auto_login";
	private  final String REMBER_PW="rember_pw";
	private  final String USERNAME="username";
	private  final String PASSWORD="password";
	private  final String NICKNAME="nickname";
	
	private static LifePreferences lifeInstance;
	private  SharedPreferences lifePreferences;
	public static String mUID = "no";
	public static int mCity;
	
	private LifePreferences(){};
	
	//创建单例LifePreferences
	public static LifePreferences getPreferences(){
		
		if(lifeInstance == null){
			
			synchronized(LifePreferences.class){
				
				lifeInstance = new LifePreferences();
			}
		}
		return lifeInstance;
		
	}
	/**初始化SharedPreferences*/
	public void init(Context ctx){
		if(lifePreferences == null){
			lifePreferences = ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		}
	}
	/**初始化SharedPreferences*/
	public void init(){
		if(lifePreferences == null){
			lifePreferences = MyApplication.getInstance().getSharedPreferences(NAME, Context.MODE_PRIVATE);
		}
	}
	
	/**保存用户名*/
	public void saveName(String name){
		init();
		Editor editor = lifePreferences.edit();
		String bname = new String(Base64.encode(name.getBytes(), Base64.DEFAULT));
		editor.putString(USERNAME, bname);
		editor.commit();
	}
	/**获取用户名*/
	public String getName(){
		init();
		String bname = lifePreferences.getString(USERNAME, "");
		return new String(Base64.decode(bname, Base64.DEFAULT));
		
	}
	
	/**保存昵称*/
	public void saveNickName(String name){
		init();
		Editor editor = lifePreferences.edit();
		String bname = new String(Base64.encode(name.getBytes(), Base64.DEFAULT));
		editor.putString(NICKNAME, bname);
		editor.commit();
	}
	/**获取昵称*/
	public String getNickName(){
		init();
		String bname = lifePreferences.getString(NICKNAME, new String(Base64.encode("NickName".getBytes(), Base64.DEFAULT)));
		return new String(Base64.decode(bname, Base64.DEFAULT));
		
	}
	
	/**保存用户密码*/
	public void savePW(String pw){
		init();
		Editor editor = lifePreferences.edit();
		String bpw = new String(Base64.encode(pw.getBytes(), Base64.DEFAULT));
		editor.putString(PASSWORD, bpw);
		editor.commit();
	}
	/**获取密码*/
	public String getPW(){
		init();
		String bpw = lifePreferences.getString(PASSWORD, "");
		return new String(Base64.decode(bpw, Base64.DEFAULT));
	}	
	
	/**获取用户id，－1为非注册用户*/
	public String getUID() {
		init();
		return lifePreferences.getString(KEY_UID, "no");
	}

	/**保存用户id*/
	public void setUID(String uid) {
		init();
		Editor editor = lifePreferences.edit();
		editor.putString(KEY_UID,uid);
		editor.commit();
		mUID = uid;
	}

	/**获取城市,0为北京，1为上海，2为深圳*/
	public  int getCityID() {
		init();
		return lifePreferences.getInt(KEY_CITY_ID,0);
	}

	/**保存当前选中城市*/
	public  void setCityID(int cid) {
		init();
		Editor editor = lifePreferences.edit();
		editor.putInt(KEY_CITY_ID, cid);
		editor.commit();
		mCity = cid;
	}
	
	/**设置自动登录*/
	public  void setAutoLogin(boolean autoLogin) {
		init();
		Editor editor = lifePreferences.edit();
		editor.putBoolean(AUTO_LOGIN, autoLogin);
		editor.commit();
	}
	
	/**获取自动登录*/
	public  Boolean getAutoLogin() {
		init();
		return lifePreferences.getBoolean(AUTO_LOGIN, false);
	}
	
	/**设置记住密码*/
	public  void setRemberPW(boolean rember_password) {
		init();
		Editor editor = lifePreferences.edit();
		editor.putBoolean(REMBER_PW, rember_password);
		editor.commit();
	}
	
	/**获取记住密码*/
	public  Boolean getRemberPW() {
		init();
		return lifePreferences.getBoolean(REMBER_PW, true);
	}
}
