package cn.bdqn.life.adapter;

import cn.bdqn.life.R;
import cn.bdqn.life.data.Show;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * �ݳ��б�������
 */
public class ShowAdapter extends LArrayBaseAdapter<Show> {

	public ShowAdapter(Context context, Show[] array) {
		super(context, array);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View createItemView() {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.showcontent, null);
	}

	@Override
	public void initItemViewByData(int position, View view, Show data) {
		// TODO Auto-generated method stub
		ImageView photoView = (ImageView) view.findViewById(R.id.show_photo);
		TextView nameView = (TextView) view.findViewById(R.id.show_name);
		TextView addrView = (TextView) view.findViewById(R.id.show_addr);
		TextView timeView = (TextView) view.findViewById(R.id.show_time);
		photoView.setImageDrawable(data.icon);
		nameView.setText("����:" + data.name);
		addrView.setText("��ַ: " + data.addr);
		timeView.setText("ʱ��: " + data.time);
	}

}
