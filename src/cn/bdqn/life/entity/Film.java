package cn.bdqn.life.entity;

import android.graphics.drawable.Drawable;

public class Film {
	public int id;
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
		return "Movie [id=" + id + ", name=" + name + ", type=" + type
				+ ", time=" + time + ", player=" + player + ", image="
				+ image + ", desc=" + desc + ", timelong=" + timelong
				+ ", icon=" + icon + "]";
	}
}
