package cn.bdqn.life.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bdqn.life.R;
import cn.bdqn.life.data.DataManager;
import cn.bdqn.life.data.DataManager.Delicacies;
/*
 * ��ʳ����������
 */
public class EatAdapter extends LArrayBaseAdapter<Delicacies> {

	public EatAdapter(Context context, Delicacies[] lists) {
		super(context, lists);
	}

	@Override
	public View createItemView() {
		return inflater.inflate(R.layout.eat_content, null);
	}

	@Override
	public void initItemViewByData(int position, View view, Delicacies data) {
		TextView restaurantText = (TextView) view.findViewById(R.id.restaurant);
		ImageView restaurantPhoto = (ImageView) view.findViewById(R.id.restaurant_photo);
		TextView tagText = (TextView) view.findViewById(R.id.eat_label);
		TextView addressText = (TextView) view.findViewById(R.id.eat_address);
		TextView consumeText = (TextView) view.findViewById(R.id.consume);
		tagText.setText("��ǩ: " + DataManager.delicacies[position].label);
		restaurantPhoto.setImageDrawable(DataManager.delicacies[position].icon);
		addressText.setText("��ַ: " + DataManager.delicacies[position].addr);
		consumeText.setText("�˾�: " + DataManager.delicacies[position].avg + "");
		restaurantText.setText(DataManager.delicacies[position].name);
	}

}
