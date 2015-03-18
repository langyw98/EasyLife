package cn.bdqn.life.adapter;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bdqn.life.DisplayDetail;
import cn.bdqn.life.R;
import cn.bdqn.life.data.DataManager;
import cn.bdqn.life.data.DataManager.Display;
import cn.bdqn.life.data.DataManager.Recommend;

public class DisplayDetailAdapter extends BaseAdapter implements
		OnClickListener {

	private Context mContext;
	private Display display;
	private TextView displayinfoText;
	private boolean showInfo;
	private boolean showComm;
	private RelativeLayout reLayout;
	private Recommend[] recoms;
	private ImageView commImg;

	public DisplayDetailAdapter(Context context, Display display,
			Recommend[] recoms) {
		this.mContext = context;
		this.display = display;
		this.recoms = recoms;
	}

	@Override
	public int getCount() {
		if (null == recoms) {
		}
		return recoms.length + 1;
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
		if (0 == position) {
			convertView = inflater.inflate(R.layout.displaydetails_head, null);
			TextView displayName = (TextView) convertView
					.findViewById(R.id.displaydetails_name);
			displayName.setText(display.name);
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.displaydetails_photo);
			imageView.setImageDrawable(display.icon);
			TextView hostText = (TextView) convertView
					.findViewById(R.id.displaydetails_host);
			hostText.setText(display.host);
			TextView timeText = (TextView) convertView
					.findViewById(R.id.displaydetails_time);
			timeText.setText(display.time);
			TextView telText = (TextView) convertView
					.findViewById(R.id.displaydetails_call);
			telText.setText(display.call);
			ImageView fobtn = (ImageView) convertView
					.findViewById(R.id.displayfobtn);
			fobtn.setOnClickListener(this);
			displayinfoText = (TextView) convertView
					.findViewById(R.id.displayinfo_text);
			displayinfoText.setText(display.desc);

			if (showInfo) {
				displayinfoText.setVisibility(View.VISIBLE);
				fobtn.setImageResource(R.drawable.sanjiao_s);
			} else {
				displayinfoText.setVisibility(View.GONE);
				fobtn.setImageResource(R.drawable.sanjiao_n);
			}
			TextView commCount = (TextView) convertView
					.findViewById(R.id.displaycomm_count);
			if(null != DataManager.recoms){
				commCount.setText("(" + DataManager.recoms.length + ")");
			}else{
				commCount.setText("(0)" );
			}
			commImg = (ImageView) convertView
					.findViewById(R.id.displaycommbtn);
			commImg.setOnClickListener(this);
		} else {
			convertView = inflater
					.inflate(R.layout.recommend_item_layout, null);
			reLayout = (RelativeLayout) convertView
					.findViewById(R.id.re_layout);
			TextView recname = (TextView) convertView
					.findViewById(R.id.recname);
			recname.setText(DataManager.recoms[position - 1].name);
			TextView rectime = (TextView) convertView
					.findViewById(R.id.rectime);
			rectime.setText(DataManager.recoms[position - 1].time);
			TextView reccontent = (TextView) convertView
					.findViewById(R.id.reccontent);
			reccontent.setText(DataManager.recoms[position - 1].content);
			if (showComm) {
				reLayout.setVisibility(View.VISIBLE);
				commImg.setImageResource(R.drawable.sanjiao_s);
			} else {
				reLayout.setVisibility(View.GONE);
				commImg.setImageResource(R.drawable.sanjiao_n);
			}
		}
		return convertView;
	}

	@Override
	public void onClick(View v) {
		Message msg = new Message();
		switch (v.getId()) {
		case R.id.displayfobtn:
			if (showInfo) {
				showInfo = false;
				DisplayDetail.handler.sendMessage(msg);
			} else {
				showInfo = true;
				DisplayDetail.handler.sendMessage(msg);
			}
			break;
		case R.id.displaycommbtn:
			if (showComm) {
				showComm = false;
				DisplayDetail.handler.sendMessage(msg);
			} else {
				showComm = true;
				DisplayDetail.handler.sendMessage(msg);
			}
			break;
		default:
			break;
		}
	}

}
