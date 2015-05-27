package cn.bdqn.life.dao;

import java.util.List;

import cn.bdqn.life.entity.Exhibition;
import cn.bdqn.life.entity.Favor;
import cn.bdqn.life.entity.FilmFavor;
import cn.bdqn.life.entity.Food;
import cn.bdqn.life.entity.Show;

public interface IFavorDao {
	public void updateFavorList(List<Favor> favorList);
	public void addFavor(Favor favor);
	public void removeFavor(Favor favor);
	public List<FilmFavor> getFilmFavorList(String uid);
	public List<Food> getFoodFavorList(String uid);
	public List<Show> getShowFavorList(String uid);
	public List<Exhibition> getExhibitionFavorList(String uid);
	public boolean isFavor(String uid, int type, int tid);
}
