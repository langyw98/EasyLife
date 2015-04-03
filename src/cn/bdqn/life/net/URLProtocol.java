package cn.bdqn.life.net;

/**与服务器定义的接口协议*/
public class URLProtocol {

	/**服务器URL*/
	public static final String ROOT = "http://liuy-PC:8080/leisurelife/dealcmd";
	
	/**登录*/
	public static final int CMD_LOGIN = 0;
	
	/**注册*/
	public static final int CMD_REGISTER = 1;
	
	/**获取评论*/
	public static final int CMD_GET_REC = 2;
	
	/**发布评论*/
	public static final int CMD_SEND_REC = 3;
	
	/**获取图片*/
	public static final int CMD_GET_IMAGE = 4;
	
	/**获取电影列表*/
	public static final int CMD_FILM = 101;
	
	/**获取电影详情*/
	public static final int CMD_FILMDETAIL = 102;
	
	/**获取演唱会列表*/
	public static final int CMD_CONCERT = 201;
	
	/**获取演唱会详情*/
	public static final int CMD_CONCERT_DETAIL = 202;
	
	/**获取美食列表*/
	public static final int CMD_DELICACIES = 301;
	
	/**获取美食详情*/
	public static final int CMD_DELICACIES_DETAIL = 302;
	
	/**获取展览列表*/
	public static final int CMD_DISPLAY = 401;
	
	/**获取展览详情*/
	public static final int CMD_DISPLAY_DETAIL = 402;
	
	/**获取音乐会列表*/
	public static final int CMD_MUSIC = 501;
	
	/**获取音乐会详情*/
	public static final int CMD_MUSIC_DETAIL = 502;
	
	/**获取京剧列表*/
	public static final int CMD_PEKINGOPERA = 601;
	
	/**获取京剧详情*/
	public static final int CMD_PEKINGOPERA_DETAIL = 602;
	
	/**获取话剧列表*/
	public static final int CMD_PLAY = 701;
	
	/**获取话剧详情*/
	public static final int CMD_PLAY_DETAIL = 702;
	
	/**获取即将上映电影列表*/
	public static final int CMD_FILM_WILL = 801;
	
	/**获取即将上映电影详情*/
	public static final int CMD_FILM_WILL_DETAIL = 802;
}
