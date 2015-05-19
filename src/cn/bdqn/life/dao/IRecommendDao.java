package cn.bdqn.life.dao;

import java.util.List;

import cn.bdqn.life.entity.Exhibition;
import cn.bdqn.life.entity.FilmRecommend;
import cn.bdqn.life.entity.Food;
import cn.bdqn.life.entity.Recommend;
import cn.bdqn.life.entity.Show;

public interface IRecommendDao {
	public List<FilmRecommend> getFilmRecommendList();
	public List<Food> getFoodRecommendList();
	public List<Show> getShowRecommendList();
	public List<Exhibition> getExhibitionRecommendList();
	public void updateRecommendList(List<Recommend> recommendList);
}
