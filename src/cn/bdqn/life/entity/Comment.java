package cn.bdqn.life.entity;

public class Comment {
	public String userName;
	public String time;
	public String content;
	public int id;
	public int type;
	public int tid;
	
	@Override
	public String toString() {
		return "Comment [ id =" + id + ", name=" + userName + ", time=" + time + ", content="
				+ content + ", type=" + type + "tid="+ tid +"]";
	}
}
