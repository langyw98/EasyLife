package cn.bdqn.life.net;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import cn.bdqn.life.CommentActivity;
import cn.bdqn.life.ConcertActivity;
import cn.bdqn.life.FindEatActivity;
import cn.bdqn.life.HomeActivity;
import cn.bdqn.life.LoadingActivity;
import cn.bdqn.life.LoginActivity;
import cn.bdqn.life.MusicActivity;
import cn.bdqn.life.PkoActivity;
import cn.bdqn.life.PlayActivity;
import cn.bdqn.life.RegActivity;
import cn.bdqn.life.SeeDisplayActivity;
import cn.bdqn.life.WatchMovieActivity;
import cn.bdqn.life.WillMovieActivity;
import cn.bdqn.life.data.DataManager;
import cn.bdqn.life.data.HotelDe;
import cn.bdqn.life.data.LifePreferences;
import cn.bdqn.life.utils.FileUtil;

public class ParseJasonDataService extends Service {

	public static Handler handler;
	public static final int CODE_OK = 0;
	public static final  int COLLECTION = 500;
	public static final String HTTP_CMD = "cmd";// 功能模块
	public static final String HTTP_UID = "uid"; // 用户id
	public static final String HTTP_CODE = "code"; // 是否成功

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
	
	}

	@Override
	public void onStart(Intent intent, int startId) {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
				// 登录功能
				case URLProtocol.CMD_LOGIN: {
					Bundle bundle = msg.getData();
					int login = bundle.getInt("login");
					String uid = bundle.getString("uid");
					URLParam param = new URLParam(null);
					param.addParam("login", login);
					param.addParam("uid", uid);
					Bundle result = doCmd(URLProtocol.CMD_LOGIN, param, null, 0);
					Message m = new Message();
					m.what = URLProtocol.CMD_LOGIN;
					if (result.getInt("login") == 0) {
						LoginActivity.mHandler.sendMessage(m);
					} else if (result.getInt("login") == 1) {
						LoadingActivity.mHandler.sendMessage(m);
					}
				}
					break;
				// 注册功能
				case URLProtocol.CMD_REGISTER: {
					Bundle bundle = msg.getData();
					String name = bundle.getString("name");
					String pw = bundle.getString("password");
					URLParam param = new URLParam(null);
					param.addParam("name", name);
					param.addParam("password", pw);
					Bundle result = doCmd(URLProtocol.CMD_REGISTER, param, null, 0);
					Message m = new Message();
					m.what = URLProtocol.CMD_REGISTER;
					m.setData(result);
					RegActivity.mHandler.sendMessage(m);
				}
					break;
				// 看电影
				case URLProtocol.CMD_MOVIE: {
					URLParam param = new URLParam(null);
					Bundle bundle = doCmd(URLProtocol.CMD_MOVIE, param, null, 0);
					Message m = new Message();
					m.what = URLProtocol.CMD_MOVIE;
					m.setData(bundle);
					HomeActivity.mHandler.sendMessage(m);
				}
					break;
				// 找美食
				case URLProtocol.CMD_DELICACIES: {
					URLParam param = new URLParam(null);
					Bundle bundle = doCmd(URLProtocol.CMD_DELICACIES, param,
							null, 0);
					Message m = new Message();
					m.what = URLProtocol.CMD_DELICACIES;
					m.setData(bundle);
					HomeActivity.mHandler.sendMessage(m);
				}
					break;
				// 看展览
				case URLProtocol.CMD_DISPLAY: {
					URLParam param = new URLParam(null);
					Bundle bundle = doCmd(URLProtocol.CMD_DISPLAY, param, null,
							0);
					Message m = new Message();
					m.what = URLProtocol.CMD_DISPLAY;
					m.setData(bundle);
					HomeActivity.mHandler.sendMessage(m);
				}
					break;
				// 看演出
				case URLProtocol.CMD_CONCERT: {
					URLParam param = new URLParam(null);
					Bundle bundle = doCmd(URLProtocol.CMD_CONCERT, param, null,
							0);
					Message m = new Message();
					m.what = URLProtocol.CMD_CONCERT;
					m.setData(bundle);
					HomeActivity.mHandler.sendMessage(m);
				}
					break;
				// 正在上映电影详情
				case URLProtocol.CMD_MOVIEDETAIL: {
					Bundle bundle = msg.getData();
					int position = bundle.getInt("position");
					int mid = bundle.getInt("mid");
					URLParam param = new URLParam(null);
					param.addParam("mid", mid);
					Bundle result = doCmd(URLProtocol.CMD_MOVIEDETAIL, param,
							null, position);
					Message m = new Message();
					m.what = URLProtocol.CMD_MOVIEDETAIL;
					m.setData(result);
					WatchMovieActivity.mHandler.sendMessage(m);
				}
					break;
				// 即将上映电影详情
				case URLProtocol.CMD_MOVIE_WILL_DETAIL: {
					URLParam param = new URLParam(null);
					Bundle bundle = msg.getData();
					int mid = bundle.getInt("mid");
					int position = bundle.getInt("position");
					param.addParam("mid", mid);
					Bundle result = doCmd(URLProtocol.CMD_MOVIE_WILL_DETAIL,
							param, null, position);
					Message m = new Message();
					m.setData(result);
					m.what = URLProtocol.CMD_MOVIE_WILL_DETAIL;
					WillMovieActivity.mHandler.sendMessage(m);
				}
					break;
				// 美食详情
				case URLProtocol.CMD_DELICACIES_DETAIL: {
					Bundle bundle = msg.getData();
					int did = bundle.getInt("did");
					int position = bundle.getInt("position");
					URLParam param = new URLParam(null);
					param.addParam("did", did);
					Bundle result = doCmd(URLProtocol.CMD_DELICACIES_DETAIL,
							param, null, position);
					Message m = new Message();
					m.setData(result);
					m.what = URLProtocol.CMD_DELICACIES_DETAIL;
					FindEatActivity.handler.sendMessage(m);
				}
					break;
				// 展览详情
				case URLProtocol.CMD_DISPLAY_DETAIL: {
					Bundle bundle = msg.getData();
					int did = bundle.getInt("did");
					int position = bundle.getInt("position");
					URLParam param = new URLParam(null);
					param.addParam("did", did);
					Bundle result = doCmd(URLProtocol.CMD_DISPLAY_DETAIL,
							param, null, position);
					result.putInt("position", position);
					Message m = new Message();
					m.setData(result);
					m.what = URLProtocol.CMD_DISPLAY_DETAIL;
					SeeDisplayActivity.mHandler.sendMessage(m);
				}
					break;
				// 演唱会详情
				case URLProtocol.CMD_CONCERT_DETAIL: {
					Bundle bundle = msg.getData();
					int cid = bundle.getInt("cid");
					int position = bundle.getInt("position");
					URLParam param = new URLParam(null);
					param.addParam("cid", cid);
					Bundle result = doCmd(URLProtocol.CMD_CONCERT_DETAIL,
							param, null, position);
					Message m = new Message();
					m.what = URLProtocol.CMD_CONCERT_DETAIL;
					m.setData(result);
					ConcertActivity.handler.sendMessage(m);
				}
					break;
				// 音乐会详情
				case URLProtocol.CMD_MUSIC_DETAIL: {
					Bundle bundle = msg.getData();
					int mid = bundle.getInt("mid");
					int position = bundle.getInt("position");
					URLParam param = new URLParam(null);
					param.addParam("mid", mid);
					Bundle result = doCmd(URLProtocol.CMD_MUSIC_DETAIL, param,
							null, position);
					Message m = new Message();
					m.what = URLProtocol.CMD_MUSIC_DETAIL;
					m.setData(result);
					MusicActivity.handler.sendMessage(m);
				}
					break;
				// 话剧详情
				case URLProtocol.CMD_PLAY_DETAIL: {
					Bundle bundle = msg.getData();
					int pid = bundle.getInt("pid");
					int position = bundle.getInt("position");
					URLParam param = new URLParam(null);
					param.addParam("pid", pid);
					Bundle result = doCmd(URLProtocol.CMD_PLAY_DETAIL, param,
							null, position);
					Message m = new Message();
					m.what = URLProtocol.CMD_PLAY_DETAIL;
					m.setData(result);
					PlayActivity.handler.sendMessage(m);
				}
					break;
				// 戏曲详情
				case URLProtocol.CMD_PEKINGOPERA_DETAIL: {
					Bundle bundle = msg.getData();
					int pid = bundle.getInt("pid");
					int position = bundle.getInt("position");
					URLParam param = new URLParam(null);
					param.addParam("pid", pid);
					Bundle result = doCmd(URLProtocol.CMD_PEKINGOPERA_DETAIL,
							param, null, position);
					Message m = new Message();
					m.what = URLProtocol.CMD_PEKINGOPERA_DETAIL;
					m.setData(result);
					PkoActivity.handler.sendMessage(m);
				}
					break;
				// 发送评论
				case URLProtocol.CMD_SEND_REC:{
					Bundle bundle = msg.getData();
					int position = bundle.getInt("position");
					int type = bundle.getInt("type");
					String name = bundle.getString("name");
					String content = bundle.getString("content");
					URLParam param = new URLParam(null);
					if(type == URLProtocol.CMD_MOVIEDETAIL){
						param.addParam("type", 1);
						param.addParam("tid", DataManager.movies[position].mid);
					}else if(type == URLProtocol.CMD_MOVIE_WILL_DETAIL){
						param.addParam("type", 8);
						param.addParam("tid", DataManager.willmovies[position].mid);
					}else if(type == URLProtocol.CMD_DISPLAY_DETAIL){
						param.addParam("type", 1);
						param.addParam("tid", DataManager.displays[position].did);
					}else if(type == URLProtocol.CMD_CONCERT_DETAIL){
						param.addParam("type", 1);
						param.addParam("tid", DataManager.concerts[position].id);
					}else if(type == URLProtocol.CMD_MUSIC_DETAIL){
						param.addParam("type", 1);
						param.addParam("tid", DataManager.musics[position].id);
					}else if(type == URLProtocol.CMD_PLAY_DETAIL){
						param.addParam("type", 1);
						param.addParam("tid", DataManager.plays[position].id);
					}else if(type == URLProtocol.CMD_PEKINGOPERA_DETAIL){
						param.addParam("type", 1);
						param.addParam("tid", DataManager.pos[position].id);
					}
					param.addParam("name", name);
					param.addParam("content", content);
					Bundle result = doCmd(URLProtocol.CMD_SEND_REC,
							param, null, 0);
					Message m = new Message();
					m.setData(result);
					CommentActivity.handler.sendMessage(m);
				}
				break;
				
				default:
					break;
				}
			}
		};
	}

	public Bundle doCmd(int cmd, URLParam p, Bundle input, int position) {
		Bundle result = new Bundle();
		result.putInt(HTTP_CMD, cmd);

		if (input != null) {
			result.putAll(input);
		}

		URLParam param = new URLParam(p);
		param.addParam("cmd", cmd);

		String jsonStr = HttpConnection.httpGet(URLProtocol.ROOT, param);
		System.out.println(">>>>>>" + jsonStr);
		jsonStr = jsonStr.replaceAll("(\r\n|\r|\n|\n\r)", " ");

		JSONObject json = null;
		try {
			if (jsonStr == null) {
				result.putInt(HTTP_CODE, -2);
				throw new Exception();
			}

			json = new JSONObject(jsonStr);
			int code = json.getInt("code");
			result.putInt(HTTP_CODE, code);

			int returnCmd = json.getInt("cmd");

			if (returnCmd != cmd) {
				System.out.println("Data Error!!!");
			}
			result.putInt("cmd", cmd);
			System.out.println("code: " + code + " cmd: " + cmd);
			// 向服务器发送请求成功
			if (code == 0) {
				switch (cmd) {
				case URLProtocol.CMD_REGISTER:// 注册
				{
					String uid = json.getString("uid");
					LifePreferences.getPreferences().setUID(uid);
				}
					break;
				case URLProtocol.CMD_LOGIN:// 登录
				{
					int login = json.getInt("login");
					result.putInt("login", login);
				}
					break;
				case URLProtocol.CMD_GET_REC:// 获取评论
				{
					JSONArray jrray = json.getJSONArray("list");
					int len = jrray.length();
					JSONObject jo;
					DataManager.recoms = new DataManager.Recommend[len];
					for (int i = 0; i < len; i++) {
						jo = jrray.getJSONObject(i);
						DataManager.recoms[i] = new DataManager.Recommend();
						DataManager.recoms[i].content = jo.getString("content");
						DataManager.recoms[i].time = jo.getString("time");
						DataManager.recoms[i].name = jo.getString("name");
					}
				}
					break;
				case URLProtocol.CMD_MOVIE:// 看电影
				{
					JSONArray jrray = json.getJSONArray("list");
					int len = jrray.length();
					JSONObject jo;
					DataManager.movies = new DataManager.Movie[len];
					for (int i = 0; i < len; ++i) {
						jo = jrray.getJSONObject(i);
						DataManager.movies[i] = new DataManager.Movie();
						DataManager.movies[i].mid = jo.getInt("mid");
						DataManager.movies[i].name = jo.getString("name");
						DataManager.movies[i].type = jo.getString("type");
						DataManager.movies[i].time = jo.getString("time");
						DataManager.movies[i].player = jo.getString("player");
						DataManager.movies[i].image = jo.getString("image");
						Drawable img = null;

						if (FileUtil.imageExist(DataManager.movies[i].image)) {
							String path = FileUtil.IMAGEDIR
									+ FileUtil.separator
									+ DataManager.movies[i].image + ".life";
							img = BitmapDrawable.createFromPath(path);
						} else {
							try {
								img = HttpConnection
										.getNetimage(DataManager.movies[i].image);

							} catch (Exception e) {
								img = null;
							}
						}
						DataManager.movies[i].icon = img;
					}
					URLParam pam = new URLParam(p);
					doCmd(URLProtocol.CMD_MOVIE_WILL, pam, null, 0);
				}
					break;
				case URLProtocol.CMD_MOVIE_WILL:// 即将上映电影
				{
					JSONArray jrray = json.getJSONArray("list");
					int len = jrray.length();
					JSONObject jo;
					DataManager.willmovies = new DataManager.WillMovie[len];
					for (int i = 0; i < len; ++i) {

						jo = jrray.getJSONObject(i);
						DataManager.willmovies[i] = new DataManager.WillMovie();
						DataManager.willmovies[i].mid = jo.getInt("mid");
						DataManager.willmovies[i].name = jo.getString("name");
						DataManager.willmovies[i].type = jo.getString("type");
						DataManager.willmovies[i].time = jo.getString("time");
						DataManager.willmovies[i].player = jo
								.getString("player");
						DataManager.willmovies[i].image = jo.getString("image");
						Drawable img = null;
						if (FileUtil
								.imageExist(DataManager.willmovies[i].image)) {
							String path = FileUtil.IMAGEDIR
									+ FileUtil.separator
									+ DataManager.willmovies[i].image + ".life";
							img = BitmapDrawable.createFromPath(path);
						} else {
							try {
								img = HttpConnection
										.getNetimage(DataManager.willmovies[i].image);
							} catch (Exception e) {
								img = null;
							}
						}
						DataManager.willmovies[i].icon = img;
					}
				}
					break;
				case URLProtocol.CMD_MOVIEDETAIL:// 电影详情
				{
					int mid = json.getInt("mid");
					result.putInt("position", position);
					DataManager.movies[position].desc = json.getString("desc");
					DataManager.movies[position].timelong = json.getString("tlong");
					URLParam pam = new URLParam(p);
					pam.addParam("type", 1);
					pam.addParam("tid", mid);
					doCmd(URLProtocol.CMD_GET_REC, pam, null, 0);// 获取评论
				}
					break;
				case URLProtocol.CMD_MOVIE_WILL_DETAIL:// 即将上映电影详情
				{
					DataManager.willmovies[position].desc = json
							.getString("desc");
					DataManager.willmovies[position].timelong = json
							.getString("tlong");
					int mid = DataManager.willmovies[position].mid;
					result.putInt("position", position);
					URLParam pam = new URLParam(p);
					pam.addParam("type", 8);
					pam.addParam("tid", mid);
					doCmd(URLProtocol.CMD_GET_REC, pam, null, 0);// 获取评论
				}
					break;
				case URLProtocol.CMD_DELICACIES:// 找美食
				{
					JSONArray jrray = json.getJSONArray("list");
					int len = jrray.length();
					JSONObject jo;
					DataManager.delicacies = new DataManager.Delicacies[len];
					for (int i = 0; i < len; ++i) {

						jo = jrray.getJSONObject(i);
						DataManager.delicacies[i] = new DataManager.Delicacies();
						DataManager.delicacies[i].did = jo.getInt("did");
						DataManager.delicacies[i].label = jo.getString("label");
						DataManager.delicacies[i].addr = jo.getString("addr");
						DataManager.delicacies[i].avg = jo.getInt("avg");
						DataManager.delicacies[i].image = jo.getString("image");
						DataManager.delicacies[i].name = jo.getString("name");
						Drawable img = null;

						if (FileUtil
								.imageExist(DataManager.delicacies[i].image)) {
							String path = FileUtil.IMAGEDIR
									+ FileUtil.separator
									+ DataManager.delicacies[i].image + ".life";
							img = BitmapDrawable.createFromPath(path);
						} else {
							try {
								img = HttpConnection
										.getNetimage(DataManager.delicacies[i].image);
							} catch (Exception e) {
								img = null;
							}
						}
						DataManager.delicacies[i].icon = img;
					}
				}
					break;
				case URLProtocol.CMD_DISPLAY:// 看展览
				{
					JSONArray jrray = json.getJSONArray("list");
					int len = jrray.length();
					JSONObject jo;
					DataManager.displays = new DataManager.Display[len];
					for (int i = 0; i < len; ++i) {

						jo = jrray.getJSONObject(i);
						DataManager.displays[i] = new DataManager.Display();
						DataManager.displays[i].did = jo.getInt("did");
						DataManager.displays[i].name = jo.getString("name");
						DataManager.displays[i].time = jo.getString("time");
						DataManager.displays[i].addr = jo.getString("addr");
						DataManager.displays[i].image = jo.getString("image");
						Drawable img = null;
						if (FileUtil.imageExist(DataManager.displays[i].image)) {
							String path = FileUtil.IMAGEDIR
									+ FileUtil.separator
									+ DataManager.displays[i].image + ".life";
							img = BitmapDrawable.createFromPath(path);
						} else {
							try {
								img = HttpConnection
										.getNetimage(DataManager.displays[i].image);
							} catch (Exception e) {
								img = null;
							}
						}
						DataManager.displays[i].icon = img;
					}
				}
					break;
				// 演唱会
				case URLProtocol.CMD_CONCERT:
				{
					JSONArray jrray = json.getJSONArray("list");
					int len = jrray.length();
					DataManager.concerts = new DataManager.Concert[len];
					JSONObject jo;
					for (int i = 0; i < len; ++i) {

						jo = jrray.getJSONObject(i);
						DataManager.concerts[i] = new DataManager.Concert();
						DataManager.concerts[i].id = jo.getInt("cid");
						DataManager.concerts[i].name = jo.getString("name");
						DataManager.concerts[i].time = jo.getString("time");
						DataManager.concerts[i].addr = jo.getString("addr");
						DataManager.concerts[i].image = jo.getString("image");
						Drawable img = null;
						if (FileUtil.imageExist(DataManager.concerts[i].image)) {
							String path = FileUtil.IMAGEDIR
									+ FileUtil.separator
									+ DataManager.concerts[i].image + ".life";
							img = BitmapDrawable.createFromPath(path);
						} else {
							try {
								img = HttpConnection
										.getNetimage(DataManager.concerts[i].image);
							} catch (Exception e) {
								img = null;
							}
						}
						DataManager.concerts[i].icon = img;
					}
					URLParam pam = new URLParam(p);
					doCmd(URLProtocol.CMD_MUSIC, pam, null, 0);
				}
					break;
				// 音乐会
				case URLProtocol.CMD_MUSIC: {
					JSONArray jrray = json.getJSONArray("list");
					int len = jrray.length();
					DataManager.musics = new DataManager.Music[len];
					JSONObject jo;
					for (int i = 0; i < len; i++) {
						jo = jrray.getJSONObject(i);
						DataManager.musics[i] = new DataManager.Music();
						DataManager.musics[i].addr = jo.getString("addr");
						DataManager.musics[i].id = jo.getInt("mid");
						DataManager.musics[i].name = jo.getString("name");
						DataManager.musics[i].image = jo.getString("image");
						DataManager.musics[i].time = jo.getString("time");
						Drawable img = null;
						if (FileUtil.imageExist(DataManager.musics[i].image)) {
							String path = FileUtil.IMAGEDIR
									+ FileUtil.separator
									+ DataManager.musics[i].image + ".life";
							img = BitmapDrawable.createFromPath(path);
						} else {
							try {
								img = HttpConnection
										.getNetimage(DataManager.musics[i].image);
							} catch (Exception e) {
								img = null;
							}
						}
						DataManager.musics[i].icon = img;
					}
					URLParam pam = new URLParam(p);
					doCmd(URLProtocol.CMD_PEKINGOPERA, pam, null, 0);
				}
					break;
				// 京剧
				case URLProtocol.CMD_PEKINGOPERA: {
					JSONArray jrray = json.getJSONArray("list");
					int len = jrray.length();
					DataManager.pos = new DataManager.Pekingopera[len];
					JSONObject jo;
					for (int i = 0; i < len; i++) {
						jo = jrray.getJSONObject(i);
						DataManager.pos[i] = new DataManager.Pekingopera();
						DataManager.pos[i].id = jo.getInt("pid");
						DataManager.pos[i].name = jo.getString("name");
						DataManager.pos[i].image = jo.getString("image");
						DataManager.pos[i].time = jo.getString("time");
						DataManager.pos[i].addr = jo.getString("addr");
						Drawable img = null;
						if (FileUtil.imageExist(DataManager.pos[i].image)) {
							String path = FileUtil.IMAGEDIR
									+ FileUtil.separator
									+ DataManager.pos[i].image + ".life";
							img = BitmapDrawable.createFromPath(path);
						} else {
							try {
								img = HttpConnection
										.getNetimage(DataManager.pos[i].image);
							} catch (Exception e) {
								img = null;
							}
						}
						DataManager.pos[i].icon = img;
					}
					URLParam pam = new URLParam(p);
					doCmd(URLProtocol.CMD_PLAY, pam, null, 0);
				}
					break;
				// 话剧
				case URLProtocol.CMD_PLAY: {
					JSONArray jrray = json.getJSONArray("list");
					int len = jrray.length();
					DataManager.plays = new DataManager.Play[len];
					JSONObject jo;
					for (int i = 0; i < len; i++) {
						jo = jrray.getJSONObject(i);
						DataManager.plays[i] = new DataManager.Play();
						DataManager.plays[i].id = jo.getInt("pid");
						DataManager.plays[i].name = jo.getString("name");
						DataManager.plays[i].image = jo.getString("image");
						DataManager.plays[i].time = jo.getString("time");
						DataManager.plays[i].addr = jo.getString("addr");
						Drawable img = null;
						if (FileUtil.imageExist(DataManager.plays[i].image)) {
							String path = FileUtil.IMAGEDIR
									+ FileUtil.separator
									+ DataManager.plays[i].image + ".life";
							img = BitmapDrawable.createFromPath(path);
						} else {
							try {
								img = HttpConnection
										.getNetimage(DataManager.plays[i].image);
							} catch (Exception e) {
								img = null;
							}
						}
						DataManager.plays[i].icon = img;
					}
				}
					break;
				case URLProtocol.CMD_DELICACIES_DETAIL: {
					JSONArray array = json.getJSONArray("list");
					int len = array.length();
					DataManager.delicacies[position].hoteds = new HotelDe[len];
					JSONObject jo;
					for (int i = 0; i < len; i++) {
						jo = array.getJSONObject(i);
						DataManager.delicacies[position].hoteds[i] = new HotelDe();
						DataManager.delicacies[position].hoteds[i].image = jo
								.getString("image");
						DataManager.delicacies[position].hoteds[i].name = jo
								.getString("name");
						DataManager.delicacies[position].hoteds[i].nowprice = jo
								.getInt("nprice");
						DataManager.delicacies[position].hoteds[i].oldprice = jo
								.getInt("oprice");
						Drawable img = null;
						if (FileUtil
								.imageExist(DataManager.delicacies[position].hoteds[i].image)) {
							String path = FileUtil.IMAGEDIR
									+ FileUtil.separator
									+ DataManager.delicacies[position].hoteds[i].image
									+ ".life";
							img = BitmapDrawable.createFromPath(path);
						} else {
							try {
								img = HttpConnection
										.getNetimage(DataManager.delicacies[position].hoteds[i].image);
							} catch (Exception e) {
								img = null;
							}
						}
						DataManager.delicacies[position].hoteds[i].icon = img;
					}
					DataManager.delicacies[position].mapx = json
							.getString("mapx");
					DataManager.delicacies[position].mapy = json
							.getString("mapy");
					DataManager.delicacies[position].call = json
							.getString("call");
					result.putInt("position", position);
				}
					break;
				case URLProtocol.CMD_DISPLAY_DETAIL: {
					DataManager.displays[position].host = json
							.getString("host");
					DataManager.displays[position].call = json
							.getString("call");
					DataManager.displays[position].desc = json
							.getString("desc");
					result.putInt("position", position);
					int did = DataManager.displays[position].did;
					URLParam pam = new URLParam(p);
					pam.addParam("type", 1);
					pam.addParam("tid", did);
					doCmd(URLProtocol.CMD_GET_REC, pam, null, 0);
				}
					break;
				case URLProtocol.CMD_CONCERT_DETAIL: {
					DataManager.concerts[position].call = json
							.getString("call");
					DataManager.concerts[position].mapx = json
							.getString("mapx");
					DataManager.concerts[position].mapy = json
							.getString("mapy");
					DataManager.concerts[position].decs = json
							.getString("desc");
					DataManager.concerts[position].price = json
							.getString("price");
					result.putInt("position", position);
					int cid = DataManager.concerts[position].id;
					URLParam pam = new URLParam(null);
					pam.addParam("type", 1);
					pam.addParam("tid", cid);
					doCmd(URLProtocol.CMD_GET_REC, pam, null, 0);
				}
					break;
				case URLProtocol.CMD_MUSIC_DETAIL: {
					DataManager.musics[position].call = json.getString("call");
					DataManager.musics[position].mapx = json.getString("mapx");
					DataManager.musics[position].mapy = json.getString("mapy");
					DataManager.musics[position].price = json
							.getString("price");
					DataManager.musics[position].decs = json.getString("desc");
					result.putInt("position", position);
					int mid = DataManager.musics[position].id;
					URLParam pam = new URLParam(null);
					pam.addParam("type", 1);
					pam.addParam("tid", mid);
					doCmd(URLProtocol.CMD_GET_REC, pam, null, 0);
				}
					break;
				case URLProtocol.CMD_PLAY_DETAIL: {
					DataManager.plays[position].call = json.getString("call");
					DataManager.plays[position].mapx = json.getString("mapx");
					DataManager.plays[position].mapy = json.getString("mapy");
					DataManager.plays[position].price = json.getString("price");
					DataManager.plays[position].decs = json.getString("desc");
					result.putInt("position", position);
					int pid = DataManager.plays[position].id;
					URLParam pam = new URLParam(null);
					pam.addParam("type", 1);
					pam.addParam("tid", pid);
					doCmd(URLProtocol.CMD_GET_REC, pam, null, 0);
				}
					break;
				case URLProtocol.CMD_PEKINGOPERA_DETAIL: {
					DataManager.pos[position].call = json.getString("call");
					DataManager.pos[position].mapx = json.getString("mapx");
					DataManager.pos[position].mapy = json.getString("mapy");
					DataManager.pos[position].price = json.getString("price");
					DataManager.pos[position].decs = json.getString("desc");
					result.putInt("position", position);
					int pid = DataManager.pos[position].id;
					URLParam pam = new URLParam(null);
					pam.addParam("type", 1);
					pam.addParam("tid", pid);
					doCmd(URLProtocol.CMD_GET_REC, pam, null, 0);
				}
					break;
				case URLProtocol.CMD_SEND_REC:{
					result.putInt("code", code);
				}
				break;
				}
			}else {
				if(cmd == URLProtocol.CMD_GET_REC){
					DataManager.recoms = new DataManager.Recommend[0];
				}
				System.out.println("Server Error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
