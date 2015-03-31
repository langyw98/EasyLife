package cn.bdqn.life.dao;

import java.util.List;

import cn.bdqn.life.entity.Comment;
import cn.bdqn.life.entity.Movie;

public interface IMovieDao {
	public void addMovie();
	public void addMovieComment(int mid, Comment comment);
	public Movie getMovie(int mid);
	public List<Movie> getMovies(int posStart, int pageLength);
	public Movie getMovieComment(int mid, int posStart, int pageLength);
	
}
