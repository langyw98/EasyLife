package cn.bdqn.life.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.bdqn.life.R;
import cn.bdqn.life.entity.Exhibition;
import cn.bdqn.life.entity.Film;
import cn.bdqn.life.entity.FilmRecommend;
import cn.bdqn.life.entity.Food;
import cn.bdqn.life.entity.Show;
import cn.bdqn.life.utils.FileUtil;

public class RecommendFragment extends Fragment {
	
	private ListView listView;
	private List filmList = new ArrayList<FilmRecommend>();
	private List exhibitionList = new ArrayList<Exhibition>();
	private List foodList = new ArrayList<Food>();
	private List showList = new ArrayList<Show>();
	
	private class RecommendAdapter extends BaseAdapter{
		//推荐列表显示顺序1.电影 2.美食 3.演出 4.展览
		private View[] labelItem = new View[4];
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return filmList.size() + exhibitionList.size() + foodList.size() + showList.size() + 4;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			int filmSize = filmList.size();
			int foodSize = foodList.size();
			int showSize = showList.size();
			int exhibitionSize = exhibitionList.size();
			
			if(position == 0){
				return "电影推荐";
			}else if(position < 1 + filmSize){
				return filmList.get(position - 1);
			}else if(position == 2 + filmSize){
				return "美食推荐";
			}else if(position < 2 + filmSize + foodSize){
				return foodList.get(position - 2 - filmSize);
			}else if(position == 3 + filmSize + foodSize){
				return "演出推荐";
			}else if(position < 3 + filmSize + foodSize + showSize){
				return showList.get(position - 3 - filmSize - foodSize);
			}else if(position == 4 + filmSize + foodSize + showSize){
				return "展览推荐";
			}else if(position < 4 + filmSize + foodSize + showSize + foodSize 
					&& position < 4 + filmSize + foodSize + showSize + foodSize + exhibitionSize){
				return exhibitionList.get(position - 4 - filmSize - foodSize - showSize);
			}
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = null;
			int filmSize = filmList.size();
			int foodSize = foodList.size();
			int showSize = showList.size();
			
			Object data = getItem(position);
			if(data == null){
				
			}else if(data instanceof String){
				if(position == 0){
					if(labelItem[0] == null){
						labelItem[0] = View.inflate(getActivity(), R.layout.item_recommend_label, null);
					}
					view = labelItem[0];
				}else if(position == 1 + filmSize){
					if(labelItem[1] == null){
						labelItem[1] = View.inflate(getActivity(), R.layout.item_recommend_label, null);
					}
					view = labelItem[1];
				}else if(position == 2 + filmSize + foodSize){
					if(labelItem[2] == null){
						labelItem[2] = View.inflate(getActivity(), R.layout.item_recommend_label, null);
					}
					view = labelItem[2];
				}else if(position == 3 + filmSize + foodSize + showSize){
					if(labelItem[3] == null){
						labelItem[3] = View.inflate(getActivity(), R.layout.item_recommend_label, null);
					}
					view = labelItem[3];
				}
				
				TextView tvLabel = (TextView) labelItem[0].findViewById(R.id.tv_label);
				tvLabel.setText((String)data);
			}else if(data instanceof FilmRecommend){
				view = View.inflate(getActivity(), R.layout.item_filmlist, null);
				ImageView ivPhoto = (ImageView) view.findViewById(R.id.iv_photo);
				TextView tvName = (TextView) view.findViewById(R.id.tv_name);
				TextView tvLabel = (TextView) view.findViewById(R.id.tv_label);
				TextView tvTime = (TextView) view.findViewById(R.id.tv_time);
				
				FilmRecommend film = (FilmRecommend) data;
				if (film.icon == null) {
					if(FileUtil.imageExist(film.image)){
						String path = FileUtil.IMAGEDIR+ FileUtil.separator+ film.image + ".life";
						film.icon = BitmapDrawable.createFromPath(path);
						ivPhoto.setBackground(film.icon);;
					}else{
//						ivPhoto.setBackgroundResource(R.id.label);
					}
				} else {
					ivPhoto.setBackground(film.icon);;
				}
				tvName.setText(film.name);
				tvLabel.setText(film.type);
				tvTime.setText(film.time);
			}else if(data instanceof Food){
				
			}else if(data instanceof Show){
				
			}else if(data instanceof Exhibition){
				
			}

			return view;
		}
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_recommend, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		initEvent();
		initData();
	}
	
	private void initView(){
		listView = (ListView) getView().findViewById(R.id.lv_recommend);
	}
	
	private void initEvent(){
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void initData() {
		// TODO Auto-generated method stub
		
	}
}
