package cn.bdqn.life.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bdqn.life.R;
import cn.bdqn.life.data.DataManager.WillMovie;
/*
 * 即将上映电影列表适配器
 */
public class WillMovieAdapter extends LArrayBaseAdapter<WillMovie> {
	
	public WillMovieAdapter(Context context, WillMovie[] array) {
		super(context, array);
	}

	@Override
	public View createItemView() {
		
		return inflater.inflate(R.layout.movie_item_layout, null);
	}

	@Override
	public void initItemViewByData(int position, View view, WillMovie data) {
		ImageView photo = (ImageView) view.findViewById(R.id.photo);
		ImageView tag = (ImageView) view.findViewById(R.id.tag);
		TextView name = (TextView) view.findViewById(R.id.name);
		TextView label = (TextView) view.findViewById(R.id.label);
		TextView player = (TextView) view.findViewById(R.id.player);
		TextView time = (TextView) view.findViewById(R.id.time);

		photo.setBackgroundDrawable(data.icon);
		tag.setBackgroundResource(R.drawable.mocorner);
		name.setText(data.name);
		label.setText(context.getResources().getString(R.string.label)+data.type);
		player.setText(context.getResources().getString(R.string.player)+data.player);
		time.setText(context.getResources().getString(R.string.time)+data.time);
		
	}

}
