package cn.bdqn.life.dao;

import java.util.List;

import cn.bdqn.life.entity.Comment;

public interface ICommentDao {
	public List<Comment> getComments(int type, int tid, int posStart, int pageLength);
	public void addComment(Comment comment);
}
