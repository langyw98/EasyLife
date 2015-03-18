package cn.bdqn.life.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import cn.bdqn.life.R;
import cn.bdqn.life.data.DataManager.Recommend;

public class RecAdapter extends LArrayBaseAdapter<Recommend> {

	public RecAdapter(Context context, Recommend[] array) {
		super(context, array);
		
	}

	@Override
	public View createItemView() {
		
		return inflater.inflate(R.layout.recommend_item_layout, null);
	}

	@Override
	public void initItemViewByData(int position, View view, Recommend data) {
		TextView nameText = (TextView) view.findViewById(R.id.recname);
		nameText.setText(data.name);
		TextView timeText = (TextView) view.findViewById(R.id.rectime);
		timeText.setText(data.time);
		TextView contentText = (TextView) view.findViewById(R.id.reccontent);
		contentText.setText("  "+data.content);
	}

}
