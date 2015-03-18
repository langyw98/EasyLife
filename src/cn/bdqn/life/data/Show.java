package cn.bdqn.life.data;

import android.graphics.drawable.Drawable;
/*
 * ÑÝ³öÀà
 */
public abstract class Show {
	public String name;
	public String time;
	public Drawable icon;
	public String addr;
	public int id;
	public String image;
	public String call;
	public String mapx;
	public String mapy;
	public String price;
	public String decs;
	
	@Override
	public String toString() {
		return "Show [name=" + name + ", time=" + time + ", icon=" + icon
				+ ", addr=" + addr + ", id=" + id + ", image=" + image
				+ ", call=" + call + ", mapx=" + mapx + ", mapy=" + mapy
				+ ", price=" + price + ", decs=" + decs + "]";
	}
	
}
