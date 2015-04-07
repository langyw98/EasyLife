package cn.bdqn.life.dao;

import java.util.List;

import cn.bdqn.life.entity.Comment;
import cn.bdqn.life.entity.Film;

public interface IFilmDao {
	public void addFilm(Film film);
	public void addFilms(List<Film> films);
	public Film getFilm(int id);
	public List<Film> getFilms(int posStart, int pageLength);
	public List<Comment> getFilmComments(int id, int posStart, int pageLength);
	public int getMaxFilmId();
	
	public void addUpcomingFilm(Film film);
	public void addUpcomingFilms(List<Film> films);
	public Film getUpcomingFilm(int id);
	public List<Film> getUpcomingFilms(int posStart, int pageLength);
	public List<Comment> getUpcomingComments(int id, int posStart, int pageLength);
	public int getMaxUpcomingFilmId();
	
	public void addComment(Comment comment);
}
