package cn.bdqn.life.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.bdqn.life.R;

public class FilmListFragment extends Fragment {
	
	public static final int FRAGMENTTYPE_RECENT = 0;
	public static final int FRAGMENTTYPE_UPCOMING = 1;
	
	private int fragmentType;
	
	private ListView listView;
	private Activity hostActivity;
	private List<String> filmList;
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
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			viewHolder.tvName.setText(filmList.get(position));
			
			return convertView;
		}
		
		private class ViewHolder{
			public TextView tvName;
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
		loadData();
		initView();
	}
	
	
	private void initView() {
		// TODO Auto-generated method stub
		hostActivity = getActivity();
		listView = (ListView) getView().findViewById(R.id.lv_film);
		adapter = new FilmListAdapter();
		listView.setAdapter(adapter);
	}
	
	private void loadData(){
		if(filmList == null){
			filmList = new ArrayList<String>();
		}
		if(fragmentType == FRAGMENTTYPE_RECENT){
			filmList.add("fragment recent");
		}else{
			filmList.add("fragment upcoming");
		}
	}
}
