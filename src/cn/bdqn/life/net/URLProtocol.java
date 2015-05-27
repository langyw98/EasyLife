package cn.bdqn.life.net;

import java.net.InetAddress;

/**与服务器定义的接口协议*/
public class URLProtocol {

	/**服务器URL*/
//	public static final String ROOT = "http://liuy-PC:8080/leisurelife/dealcmd";
	public static String IP = null;
	static{
//		IP = getAllHostIPs("liuy-PC.jv.jb-aptech.com.cn");
		IP = "192.168.9.225";
	};
	public static final String ROOT = "http://" + IP + ":8080/leisurelife/dealcmd";
	
	public static String getAllHostIPs(String hostName){
        String[] ips=null;
        try{
            InetAddress[] addrs=InetAddress.getAllByName(hostName);
            if(null!=addrs){
                ips=new String[addrs.length];
                for(int i=0;i<addrs.length;i++){
                    ips[i]=addrs[i].getHostAddress();
                }
            }
        }catch(Exception e){
            ips=null;
        }
        return ips[0];
    }
	
	/**登录*/
	public static final int CMD_LOGIN = 0;
	
	/**注册*/
	public static final int CMD_REGISTER = 1;
	
	/**获取评论*/
	public static final int CMD_GET_REC = 2;
	public static final int CMD_GET_COMMENT = 2;
	
	/**发布评论*/
	public static final int CMD_SEND_REC = 3;
	public static final int CMD_SEND_COMMENT = 3;
	
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
	
	/**获取推荐列表*/
	public static final int CMD_RECOMMEND = 901;
	
	/**获取收藏列表*/
	public static final int CMD_FAVOR = 1001;
	
	/**添加收藏*/
	public static final int CMD_ADD_FAVOR = 1002;
	
	/**移除收藏*/
	public static final int CMD_REMOVE_FAVOR = 1003;
	
}
