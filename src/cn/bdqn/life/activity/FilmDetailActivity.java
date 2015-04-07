package cn.bdqn.life.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import cn.bdqn.life.dao.IFilmDao;
import cn.bdqn.life.dao.impl.FilmImpl;
import cn.bdqn.life.entity.Film;
import cn.bdqn.life.fragment.FilmListFragment;

public class FilmDetailActivity extends Activity {

	private Film film;
	private ListView listView;
	private ListViewAdapter adapter;
	
	private class ListViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 1;
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
				convertView = View.inflate(FilmDetailActivity.this, R.layout.item_filmlist, null);
				viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
				viewHolder.tvLabel = (TextView) convertView.findViewById(R.id.tv_label);
				viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
				viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.iv_photo);
				viewHolder.ivTag = (ImageView) convertView.findViewById(R.id.iv_tag);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.tvName.setText(film.name);
			viewHolder.tvLabel.setText(film.type);
			viewHolder.tvTime.setText(film.time);			
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filmdetail);
		
		Intent intent = getIntent();
		int id = intent.getIntExtra("id", -1);
		int type = intent.getIntExtra("fragmentType", -1);
		if(id == -1 || type == -1){
			finish();
			Toast.makeText(this, "数据获取失败", Toast.LENGTH_LONG).show();
			return;
		}
		IFilmDao filmdao = new FilmImpl();
		if(type == FilmListFragment.FRAGMENTTYPE_RECENT){
			film = filmdao.getFilm(id);
		}else{
			film = filmdao.getUpcomingFilm(id);
		}
		initView();
	}
	
	private void initView(){
		listView = (ListView) findViewById(R.id.lv_film);
		adapter = new ListViewAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}
