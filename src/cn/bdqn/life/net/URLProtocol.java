package cn.bdqn.life.net;

import java.net.InetAddress;

/**与服务器定义的接口协议*/
public class URLProtocol {

	/**服务器URL*/
//	public static final String ROOT = "http://liuy-PC:8080/leisurelife/dealcmd";
	public static String IP = null;
	static{
//		IP = getAllHostIPs("liuy-PC.jv.jb-aptech.com.cn");
		IP = "192.168.9.184";
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
	public static final int CMD_GET_COMMENT = 2;
	
	/**发布评论*/
	public static final int CMD_SEND_COMMENT = 3;
	
	/**获取电影列表*/
	public static final int CMD_FILM = 101;
	
	/**获取电影详情*/
	public static final int CMD_FILMDETAIL = 102;
	
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
