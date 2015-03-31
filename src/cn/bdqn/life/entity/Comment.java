package cn.bdqn.life.entity;

public class Comment {
	public String name;
	public String time;
	public String content;
	
	@Override
	public String toString() {
		return "Comment [name=" + name + ", time=" + time + ", content="
				+ content + "]";
	}
}
