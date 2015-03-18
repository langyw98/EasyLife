package cn.bdqn.life.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bdqn.life.R;
import cn.bdqn.life.data.HomeData;

public class HomeAdapter extends LListBaseAdapter<HomeData> {

	private LayoutInflater inflater;
	public HomeAdapter(Context context, List<HomeData> lists) {
		super(context, lists);
		inflater = LayoutInflater.from(context);
	}

	@Override
	protected View createItemView() {
		//获取行布局模板
		return inflater.inflate(R.layout.home_item_layout, null);
	}

	@Override
	protected void initItemViewByData(int position, View view, HomeData data) {
		//为每一行添加数据
		ImageView image = (ImageView) view.findViewById(R.id.picture);
		TextView text = (TextView) view.findViewById(R.id.item);
		
		image.setImageBitmap(data.getPicture());
		text.setText(data.getName());
	}

}
