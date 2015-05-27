package cn.bdqn.life.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;
import cn.bdqn.life.R;
import cn.bdqn.life.activity.FilmDetailActivity;
import cn.bdqn.life.dao.IFavorDao;
import cn.bdqn.life.dao.impl.FavorImpl;
import cn.bdqn.life.data.LifePreferences;
import cn.bdqn.life.entity.Exhibition;
import cn.bdqn.life.entity.Favor;
import cn.bdqn.life.entity.FilmFavor;
import cn.bdqn.life.entity.FilmRecommend;
import cn.bdqn.life.entity.Food;
import cn.bdqn.life.entity.Show;
import cn.bdqn.life.net.HttpConnection;
import cn.bdqn.life.net.URLParam;
import cn.bdqn.life.net.URLProtocol;
import cn.bdqn.life.utils.FileUtil;
import cn.bdqn.life.utils.LoadImageUtil;

public class FavorFragment extends Fragment {
	
	private static final int MSG_CONNECT_FAILED = 0;
	private static final int MSG_GET_FAVOR_FAILED = 1;
	private static final int MSG_GET_FAVOR_SUCCESS = 2;
	private static final int MSG_REFRESH_FAVOR_LIST = 3;
	
	private ListView listView;
	private Activity hostActivity;
	private FavorAdapter adapter;
	
	private List<FilmFavor> filmList = new ArrayList<FilmFavor>();
	private List<Exhibition> exhibitionList = new ArrayList<Exhibition>();
	private List<Food> foodList = new ArrayList<Food>();
	private List<Show> showList = new ArrayList<Show>();
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case MSG_CONNECT_FAILED:
				Toast.makeText(hostActivity, "连接服务器失败", Toast.LENGTH_LONG).show();
				break;
			case MSG_GET_FAVOR_FAILED:
				Toast.makeText(hostActivity, "更新列表失败", Toast.LENGTH_LONG).show();
				break;
			case MSG_GET_FAVOR_SUCCESS:
				break;
			}
			String uid = LifePreferences.getPreferences().getUID();
			IFavorDao favorDao = new FavorImpl();
			filmList = favorDao.getFilmFavorList(uid);
			exhibitionList = favorDao.getExhibitionFavorList(uid);
			foodList = favorDao.getFoodFavorList(uid);
			showList = favorDao.getShowFavorList(uid);
			adapter.notifyDataSetChanged();
		};
	};
	private class FavorAdapter extends BaseAdapter{
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
			}else if(position == 1 + filmSize){
				return "美食推荐";
			}else if(position < 1 + filmSize + foodSize){
				return foodList.get(position - 1 - filmSize);
			}else if(position == 2 + filmSize + foodSize){
				return "演出推荐";
			}else if(position < 2 + filmSize + foodSize + showSize){
				return showList.get(position - 2 - filmSize - foodSize);
			}else if(position == 3 + filmSize + foodSize + showSize){
				return "展览推荐";
			}else if(position < 3 + filmSize + foodSize + showSize + foodSize 
					&& position < 3 + filmSize + foodSize + showSize + foodSize + exhibitionSize){
				return exhibitionList.get(position - 3 - filmSize - foodSize - showSize);
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
						labelItem[0] = View.inflate(getActivity(), R.layout.item_favor_label, null);
					}
					view = labelItem[0];
				}else if(position == 1 + filmSize){
					if(labelItem[1] == null){
						labelItem[1] = View.inflate(getActivity(), R.layout.item_favor_label, null);
					}
					view = labelItem[1];
				}else if(position == 2 + filmSize + foodSize){
					if(labelItem[2] == null){
						labelItem[2] = View.inflate(getActivity(), R.layout.item_favor_label, null);
					}
					view = labelItem[2];
				}else if(position == 3 + filmSize + foodSize + showSize){
					if(labelItem[3] == null){
						labelItem[3] = View.inflate(getActivity(), R.layout.item_favor_label, null);
					}
					view = labelItem[3];
				}
				
				TextView tvLabel = (TextView) view.findViewById(R.id.tv_label);
				tvLabel.setText((String)data);
			}else if(data instanceof FilmFavor){
				view = View.inflate(getActivity(), R.layout.item_filmlist, null);
				ImageView ivPhoto = (ImageView) view.findViewById(R.id.iv_photo);
				TextView tvName = (TextView) view.findViewById(R.id.tv_name);
				TextView tvLabel = (TextView) view.findViewById(R.id.tv_label);
				TextView tvTime = (TextView) view.findViewById(R.id.tv_time);
				
				FilmFavor film = (FilmFavor) data;

				if (film.icon == null) {
					if(FileUtil.imageExist(film.image)){
						String path = FileUtil.IMAGEDIR+ FileUtil.separator+ film.image + ".life";
						film.icon = BitmapDrawable.createFromPath(path);
						ivPhoto.setImageDrawable(film.icon);;
					}else{
						LoadImageUtil.loadImage(ivPhoto, film.image);
					}
				} else {
					ivPhoto.setImageDrawable(film.icon);;
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
		return inflater.inflate(R.layout.fragment_favor, null);
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
		hostActivity = getActivity();
		listView = (ListView) getView().findViewById(R.id.lv_favor);
		adapter = new FavorAdapter();
		listView.setAdapter(adapter);
	}
	
	private void initEvent(){
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Object item = adapter.getItem(position);
				if(item instanceof FilmFavor){
					Intent intent = new Intent(hostActivity, FilmDetailActivity.class);
					FilmFavor film = (FilmFavor)item;
					intent.putExtra("id", film.id);
					if(film.isShowing){
						intent.putExtra("fragmentType", 1);
					}else{
						intent.putExtra("fragmentType", 2);
					}
					hostActivity.startActivity(intent);
				}else if(item instanceof Food){
					
				}else if(item instanceof Exhibition){
					
				}else if(item instanceof Show){
					
				}
			}
		});
	}
	
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		handler.sendEmptyMessage(MSG_REFRESH_FAVOR_LIST);
	}

	private void initData() {
		// TODO Auto-generated method stub
		new Thread(){
			public void run() {
				String uid = LifePreferences.getPreferences().getUID();
				IFavorDao favorDao = new FavorImpl();
				URLParam param = new URLParam(null);
				param.addParam("cmd", URLProtocol.CMD_FAVOR);
				param.addParam("uid", uid);
				String jsonStr = HttpConnection.httpGet(URLProtocol.ROOT, param);
				//与服务器连接失败
				if(jsonStr == null){
					handler.sendEmptyMessage(MSG_CONNECT_FAILED);
					return;
				}
				try {
					JSONObject json = new JSONObject(jsonStr);
					int code = json.getInt("code");
					//有新增数据返回
					if (code == 0) {
						JSONArray jrray = json.getJSONArray("list");
						int len = jrray.length();
						List<Favor> favors = new ArrayList<Favor>();
						for (int i = 0; i < len; i++) {
							JSONObject jo = jrray.getJSONObject(i);
							Favor favor = new Favor();
							favor.tid = jo.getInt("tid");
							favor.type = jo.getInt("type");
							favor.uid = uid;
							favors.add(favor);
						}
						favorDao.updateFavorList(favors);
						handler.sendEmptyMessage(MSG_GET_FAVOR_SUCCESS);
						return;
					}
					handler.sendEmptyMessage(MSG_GET_FAVOR_FAILED);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
	}
	
}
