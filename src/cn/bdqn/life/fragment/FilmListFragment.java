package cn.bdqn.life.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bdqn.life.R;
import cn.bdqn.life.dao.IFilmDao;
import cn.bdqn.life.dao.impl.FilmImpl;
import cn.bdqn.life.data.DataManager;
import cn.bdqn.life.entity.Film;
import cn.bdqn.life.net.HttpConnection;
import cn.bdqn.life.net.URLParam;
import cn.bdqn.life.net.URLProtocol;
import cn.bdqn.life.utils.FileUtil;

public class FilmListFragment extends Fragment {
	
	public static final int FRAGMENTTYPE_RECENT = 0;
	public static final int FRAGMENTTYPE_UPCOMING = 1;
	
	private static final int MSG_CONNECT_FAILED = 0;
	private static final int MSG_GET_FILMLIST_FAILED = 1;
	private static final int MSG_GET_FILMLIST_SUCCESS = 2;
	private static final int MSG_GET_FILMLIST_NOUPDATE = 3;
	private static final int MSG_GET_WILLFILMLIST_FAILED = 4;
	private static final int MSG_GET_WILLFILMLIST_SUCCESS = 5;
	private static final int MSG_GET_WILLFILMLIST_NOUPDATE = 6;
	 
	
	private static final int PAGE_LENGTH = 9;
	
	private int fragmentType;
	
	private ListView listView;
	private Activity hostActivity;
	private List<Film> filmList;
	private FilmListAdapter adapter;
	
	
	private class FilmListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return filmList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
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
			ViewHolder viewHolder;
			if(convertView == null){
				viewHolder = new ViewHolder();
				convertView = View.inflate(hostActivity, R.layout.item_filmlist, null);
				viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
				viewHolder.tvLabel = (TextView) convertView.findViewById(R.id.tv_label);
				viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
				viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.iv_photo);
				viewHolder.ivTag = (ImageView) convertView.findViewById(R.id.iv_tag);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			Film film = filmList.get(position);
			viewHolder.tvName.setText(film.name);
			viewHolder.tvLabel.setText(film.type);
			viewHolder.tvTime.setText(film.time);
			if (film.icon == null) {
				if(FileUtil.imageExist(film.image)){
					String path = FileUtil.IMAGEDIR+ FileUtil.separator+ film.image + ".life";
					film.icon = BitmapDrawable.createFromPath(path);
					viewHolder.ivPhoto.setBackground(film.icon);;
				}else{
					viewHolder.ivPhoto.setBackgroundResource(R.id.label);
				}
			} else {
				viewHolder.ivPhoto.setBackground(film.icon);;
			}
			
			if(fragmentType == FRAGMENTTYPE_RECENT){
				viewHolder.ivTag.setBackgroundResource(R.drawable.hot);
			}else{
				viewHolder.ivTag.setBackgroundResource(R.drawable.mocorner);
			}
			return convertView;
		}
		
		private class ViewHolder{
			public TextView tvName;
			public TextView tvLabel;
			public TextView tvTime;
			public ImageView ivPhoto;
			public ImageView ivTag;
		}
	};
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			switch(msg.what){
			case MSG_CONNECT_FAILED:
				Toast.makeText(hostActivity, "���ӷ�����ʧ��", Toast.LENGTH_LONG).show();
				break;
			case MSG_GET_FILMLIST_FAILED:
				Toast.makeText(hostActivity, "�����б�ʧ��", Toast.LENGTH_LONG).show();
				break;
			case MSG_GET_FILMLIST_SUCCESS:
				Toast.makeText(hostActivity, "�����б��ɹ�", Toast.LENGTH_LONG).show();
				break;
			case MSG_GET_FILMLIST_NOUPDATE:
				break;
			case MSG_GET_WILLFILMLIST_FAILED:
				Toast.makeText(hostActivity, "�����б�ʧ��", Toast.LENGTH_LONG).show();
				break;
			case MSG_GET_WILLFILMLIST_SUCCESS:
				Toast.makeText(hostActivity, "�����б��ɹ�", Toast.LENGTH_LONG).show();
				break;
			case MSG_GET_WILLFILMLIST_NOUPDATE:
				break;
			}
			IFilmDao filmDao = new FilmImpl();
			filmList = filmDao.getFilms(0, PAGE_LENGTH);
			adapter.notifyDataSetChanged();
		}
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_filmlist, container, false);
	}
	
	public FilmListFragment(int fragmentType) {
		// TODO Auto-generated constructor stub
		this.fragmentType = fragmentType;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initData();
		initView();
	}
	
	
	private void initView() {
		// TODO Auto-generated method stub
		hostActivity = getActivity();
		listView = (ListView) getView().findViewById(R.id.lv_film);
		adapter = new FilmListAdapter();
		listView.setAdapter(adapter);
	}
	
	private void initData(){
		if(filmList == null){
			filmList = new ArrayList<Film>();
		}
		if(fragmentType == FRAGMENTTYPE_RECENT){
//			filmList.add("fragment recent");
			downloadFilmList();
		}else{
//			filmList.add("fragment upcoming");
		}
	}
	
	private void downloadFilmList(){
		new Thread(){
			public void run() {
				IFilmDao filmDao = new FilmImpl();
				int startPos = filmDao.getMaxFilmId();
				URLParam param = new URLParam(null);
				param.addParam("startPos", startPos);
				param.addParam("cmd", URLProtocol.CMD_FILM);
				String jsonStr = HttpConnection.httpGet(URLProtocol.ROOT, param);
				//�����������ʧ��
				if(jsonStr == null){
					handler.sendEmptyMessage(MSG_CONNECT_FAILED);
					return;
				}
				try {
					JSONObject json = new JSONObject(jsonStr);
					int code = json.getInt("code");
					//���������ݷ���
					if (code == 0) {
						JSONArray jrray = json.getJSONArray("list");
						int len = jrray.length();
						List<Film> films = new ArrayList<Film>();
						for (int i = 0; i < len; i++) {
							JSONObject jo = jrray.getJSONObject(i);
							Film film = new Film();
							film.id = jo.getInt("mid");
							film.name = jo.getString("name");
							film.type = jo.getString("type");
							film.time = jo.getString("time");
							film.player = jo.getString("player");
							film.image = jo.getString("image");
							film.desc = jo.getString("desc");
							film.timelong = jo.getString("tlong");
							films.add(film);
						}
						filmDao.addFimls(films);
						handler.sendEmptyMessage(MSG_GET_FILMLIST_SUCCESS);
						return;
					}else if(code == 2){
						handler.sendEmptyMessage(MSG_GET_FILMLIST_NOUPDATE);
						return;
					}
					handler.sendEmptyMessage(MSG_GET_FILMLIST_FAILED);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
}