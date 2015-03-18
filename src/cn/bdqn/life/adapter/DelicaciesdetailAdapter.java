package cn.bdqn.life.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bdqn.life.R;
import cn.bdqn.life.data.DataManager.Delicacies;

public class DelicaciesdetailAdapter extends BaseAdapter implements OnClickListener {

	private Context mContext;
	private Delicacies delicacies;
	
	public DelicaciesdetailAdapter(Context context, Delicacies delicacies) {
		this.mContext = context;
		this.delicacies = delicacies;
	}

	@Override
	public int getCount() {
		return delicacies.hoteds.length + 1;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		if(0 == position){
			convertView = inflater.inflate(R.layout.delicaciesdetail_addr, null);
			TextView hotelName = (TextView) convertView.findViewById(R.id.delicaciesdetail_name);
			hotelName.setText(delicacies.name);
			ImageView hotelPhoto = (ImageView) convertView.findViewById(R.id.delicaciesdetail_photo);
			hotelPhoto.setImageDrawable(delicacies.icon);
			TextView hotelAddr = (TextView) convertView.findViewById(R.id.fooddetail_addr);
			hotelAddr.setText(delicacies.addr);
			Button telButton = (Button) convertView.findViewById(R.id.delicacies_tel);
			telButton.setOnClickListener(this);
		}else{
			convertView = inflater.inflate(R.layout.delicaciesdetail_food, null);
			ImageView foodPhoto = (ImageView) convertView.findViewById(R.id.food_photo);
			foodPhoto.setImageDrawable(delicacies.hoteds[position - 1].icon);
			TextView foodName = (TextView) convertView.findViewById(R.id.food_name);
			foodName.setText(delicacies.hoteds[position - 1].name);
			TextView foodOprice = (TextView) convertView.findViewById(R.id.food_oprice);
			foodOprice.setText(delicacies.hoteds[position - 1].oldprice + "");
			TextView foodNprice = (TextView) convertView.findViewById(R.id.food_nprice);
			foodNprice.setText(delicacies.hoteds[position - 1].nowprice + "");
		}
		
		return convertView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.delicacies_tel:
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + delicacies.call));
			mContext.startActivity(intent);
			break;

		default:
			break;
		}
	}



}
