package cn.bdqn.life.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import cn.bdqn.life.R;
import cn.bdqn.life.dao.IFilmDao;
import cn.bdqn.life.dao.impl.FilmImpl;
import cn.bdqn.life.entity.Film;

public class FilmDetailActivity extends Activity {

	private Film film;
	private ListView listView;
	private ListViewAdapter adapter;
	
	private class ListViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
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
			return null;
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filmdetail);
		
		Intent intent = getIntent();
		int id = intent.getIntExtra("id", -1);
		IFilmDao filmdao = new FilmImpl();
		film = filmdao.getFilm(id);
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
