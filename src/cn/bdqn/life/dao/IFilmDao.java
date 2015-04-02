package cn.bdqn.life.dao;

import java.util.List;

import cn.bdqn.life.entity.Comment;
import cn.bdqn.life.entity.Film;

public interface IFilmDao {
	public void addFilm(Film film);
	public void addFimls(List<Film> films);
	public void addFilmComment(Comment comment);
	public Film getFilm(int id);
	public List<Film> getFilms(int posStart, int pageLength);
	public List<Comment> getFilmComments(int id, int posStart, int pageLength);
	public int getMaxFilmId();
	
}
