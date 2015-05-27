package cn.bdqn.life.net;

import java.net.InetAddress;

/**�����������Ľӿ�Э��*/
public class URLProtocol {

	/**������URL*/
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
	
	/**��¼*/
	public static final int CMD_LOGIN = 0;
	
	/**ע��*/
	public static final int CMD_REGISTER = 1;
	
	/**��ȡ����*/
	public static final int CMD_GET_REC = 2;
	public static final int CMD_GET_COMMENT = 2;
	
	/**��������*/
	public static final int CMD_SEND_REC = 3;
	public static final int CMD_SEND_COMMENT = 3;
	
	/**��ȡͼƬ*/
	public static final int CMD_GET_IMAGE = 4;
	
	/**��ȡ��Ӱ�б�*/
	public static final int CMD_FILM = 101;
	
	/**��ȡ��Ӱ����*/
	public static final int CMD_FILMDETAIL = 102;
	
	/**��ȡ�ݳ����б�*/
	public static final int CMD_CONCERT = 201;
	
	/**��ȡ�ݳ�������*/
	public static final int CMD_CONCERT_DETAIL = 202;
	
	/**��ȡ��ʳ�б�*/
	public static final int CMD_DELICACIES = 301;
	
	/**��ȡ��ʳ����*/
	public static final int CMD_DELICACIES_DETAIL = 302;
	
	/**��ȡչ���б�*/
	public static final int CMD_DISPLAY = 401;
	
	/**��ȡչ������*/
	public static final int CMD_DISPLAY_DETAIL = 402;
	
	/**��ȡ���ֻ��б�*/
	public static final int CMD_MUSIC = 501;
	
	/**��ȡ���ֻ�����*/
	public static final int CMD_MUSIC_DETAIL = 502;
	
	/**��ȡ�����б�*/
	public static final int CMD_PEKINGOPERA = 601;
	
	/**��ȡ��������*/
	public static final int CMD_PEKINGOPERA_DETAIL = 602;
	
	/**��ȡ�����б�*/
	public static final int CMD_PLAY = 701;
	
	/**��ȡ��������*/
	public static final int CMD_PLAY_DETAIL = 702;
	
	/**��ȡ������ӳ��Ӱ�б�*/
	public static final int CMD_FILM_WILL = 801;
	
	/**��ȡ������ӳ��Ӱ����*/
	public static final int CMD_FILM_WILL_DETAIL = 802;
	
	/**��ȡ�Ƽ��б�*/
	public static final int CMD_RECOMMEND = 901;
	
	/**��ȡ�ղ��б�*/
	public static final int CMD_FAVOR = 1001;
	
	/**����ղ�*/
	public static final int CMD_ADD_FAVOR = 1002;
	
	/**�Ƴ��ղ�*/
	public static final int CMD_REMOVE_FAVOR = 1003;
	
}
