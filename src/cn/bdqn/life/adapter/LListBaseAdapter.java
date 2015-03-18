package cn.bdqn.life.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class LListBaseAdapter<T> extends BaseAdapter {
	
	public LayoutInflater inflater;
	public List<T> lists;
	
	public LListBaseAdapter(Context context,List<T> lists){
		this.lists = lists;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null){
			convertView = createItemView();			
		}		
		T data = lists.get(position);
		initItemViewByData( position, convertView, data);
		return convertView;
	}
	/**引入行布局模板*/
	abstract protected View createItemView();
	
	/**为行添加数据*/
	abstract protected void initItemViewByData( int position, View view, T data );
}
