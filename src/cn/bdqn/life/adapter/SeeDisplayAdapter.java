package cn.bdqn.life.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bdqn.life.R;
import cn.bdqn.life.data.DataManager;
/*
 * 展览列表适配器
 */
public class SeeDisplayAdapter<Display> extends LArrayBaseAdapter<Display> {

	public SeeDisplayAdapter(Context context, Display[] array) {
		super(context, array);
		
	}

	@Override
	public View createItemView() {
		return inflater.inflate(R.layout.display_content, null);
	}

	@Override
	public void initItemViewByData(int position, View view, Display data) {
		TextView displayName =  (TextView) view.findViewById(R.id.display_name);
		ImageView displayImg = (ImageView) view.findViewById(R.id.display_photo);
		TextView displayTime = (TextView) view.findViewById(R.id.display_time);
		TextView displayAddr = (TextView) view.findViewById(R.id.display_addr);
		displayName.setText(DataManager.displays[position].name);
		displayImg.setImageDrawable(DataManager.displays[position].icon);
		displayTime.setText("时间: " + DataManager.displays[position].time);
		displayAddr.setText("地址: " + DataManager.displays[position].addr);
	}

}
