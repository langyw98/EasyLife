package cn.bdqn.life.data;

import android.graphics.drawable.Drawable;


public class DataManager {

	public static String[] cities = {"����","�Ϻ�","����"};
	
	/**������ӳ��Ӱ*/
	public static class Movie{
		public int mid;
		public String name;
		public String type;
		public String time;
		public String player;
		public String image;
		public String desc;
		public String timelong;
		public Drawable icon;
		
		@Override
		public String toString() {
			return "Movie [mid=" + mid + ", name=" + name + ", type=" + type
					+ ", time=" + time + ", player=" + player + ", image="
					+ image + ", desc=" + desc + ", timelong=" + timelong
					+ ", icon=" + icon + "]";
		}
		
	}
	public static Movie[] movies;//������ӳ��Ӱ
	/**������ӳ��Ӱ*/
	public static class WillMovie{
		public int mid;
		public String name;
		public String type;
		public String time;
		public String player;
		public String image;
		public String desc;
		public String timelong;
		public Drawable icon;
		
		@Override
		public String toString() {
			return "WillMovie [mid=" + mid + ", name=" + name + ", type="
					+ type + ", time=" + time + ", player=" + player
					+ ", image=" + image + ", desc=" + desc + ", timelong="
					+ timelong + ", icon=" + icon + "]";
		}
		
	}
	public static WillMovie[] willmovies;//������ӳ��Ӱ
	
	/**����*/
	public static class Play extends Show{
	
	}
	public static Play[] plays;
	
	/**չ��*/
	public static class Display{
		public int did;
		public String name;
		public String addr;
		public String time;
		public String image;
		public String call;
		public String host;
		public String desc;
		public Drawable icon;
		
		@Override
		public String toString() {
			return "Display [did=" + did + ", name=" + name + ", addr=" + addr
					+ ", time=" + time + ", image=" + image + ", call=" + call
					+ ", host=" + host + ", desc=" + desc + ", icon=" + icon
					+ "]";
		}
		
	}
	public static Display[] displays;
	
	/**��ʳ*/
	public static class Delicacies{
		public int did;
		public String label;
		public String name;
		public String addr;
		public int avg;
		public String image;
		public String call;
		public String mapx;
		public String mapy;
		public Drawable icon;
		public HotelDe[] hoteds;
	}
	public static Delicacies[] delicacies;
	
	/**����*/
	public static class Pekingopera extends Show{
	

	}
	public static Pekingopera[] pos;
	
	/**����*/
	public static class Recommend{
		public String name;
		public String time;
		public String content;
		
		@Override
		public String toString() {
			return "Recommend [name=" + name + ", time=" + time + ", content="
					+ content + "]";
		}
	}
	public static Recommend[] recoms;
	
	/**�ݳ���*/
	public static class Concert extends Show{
		
	}
	public static Concert[] concerts;
	
	/**���ֻ�*/
	public static class Music extends Show{
		
	}
	public static Music[] musics;
}
