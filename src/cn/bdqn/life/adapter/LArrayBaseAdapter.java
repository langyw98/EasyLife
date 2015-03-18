package cn.bdqn.life.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**数组参数Adapter*/
public abstract class LArrayBaseAdapter<T> extends BaseAdapter {
	
	public LayoutInflater inflater;
	public T[] array;
	public Context context;
	
	public LArrayBaseAdapter(Context context,T[] array){
		this.context = context;
		this.array = array;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return array.length;
	}

	@Override
	public Object getItem(int position) {
		return array[position];
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
		T data = array[position];
		initItemViewByData( position, convertView, data);
		return convertView;
	}
	/**引入行布局模板*/
	abstract public View createItemView();
	
	/**为行添加数据*/
	abstract public void initItemViewByData( int position, View view, T data );
}
