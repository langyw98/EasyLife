package cn.bdqn.life.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bdqn.life.DetailShow;
import cn.bdqn.life.R;
import cn.bdqn.life.data.DataManager;
import cn.bdqn.life.data.Show;
import cn.bdqn.life.net.URLProtocol;
/*
 * 演出详情适配器
 */
public class DetailShowAdapter extends BaseAdapter implements OnClickListener{

	private Context mContext;
	private int which;
	private int type;
	private boolean showInfo = false;
	private boolean showComm = false;
	private TextView showInfoText;
	private TextView showName;
	private ImageView showPhoto;
	private TextView showTime;
	private TextView showAddr;
	private TextView showPrice;
	private TextView showCommCount;
	private Show show;
	private ImageView showcommbtn ;
	
	public DetailShowAdapter(Context context, int which, int type) {
		this.mContext = context;
		this.which = which;
		this.type = type;
	}
	
	public DetailShowAdapter(Context context, Show show, int type) {
		this.mContext = context;
		this.show = show;
		this.type = type;
	}
	
	@Override
	public int getCount() {
		return DataManager.recoms.length + 1;
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
		// 详情页显示演出时间、票价等信息
		if(0 == position){
			convertView = inflater.inflate(R.layout.showdetails_head, null);
			showName = (TextView) convertView.findViewById(R.id.showdetails_name);
			showPhoto = (ImageView) convertView.findViewById(R.id.showdetails_photo);
			showTime = (TextView) convertView.findViewById(R.id.showdetails_time);
			showAddr = (TextView) convertView.findViewById(R.id.showdetails_addr);
			showPrice = (TextView) convertView.findViewById(R.id.showdetails_price);
			ImageView showfobtn = (ImageView) convertView.findViewById(R.id.showfobtn);
			showfobtn.setOnClickListener(this);
			showcommbtn = (ImageView) convertView.findViewById(R.id.showcommbtn);
			showcommbtn.setOnClickListener(this);
			ImageButton showCallButton = (ImageButton) convertView.findViewById(R.id.showcall);
			showCallButton.setOnClickListener(this);
			showInfoText = (TextView) convertView.findViewById(R.id.showinfo_text);
			showCommCount = (TextView) convertView.findViewById(R.id.showcomm_count);
			switch (type) {
			// 演唱会
			case URLProtocol.CMD_CONCERT_DETAIL:{
				if(null != DataManager.concerts){
					show = DataManager.concerts[which];
				}
				setContent(show);
			}
				break;
			// 音乐详情
			case URLProtocol.CMD_MUSIC_DETAIL:{
				if(null != DataManager.musics){
					show = DataManager.musics[which];
				}
				setContent(show);
			}
			break;
			// 话剧
			case URLProtocol.CMD_PLAY_DETAIL:{
				if(null != DataManager.plays){
					show = DataManager.plays[which];
				}
				setContent(show);
			}
			break;
			// 戏曲
			case URLProtocol.CMD_PEKINGOPERA_DETAIL:{
				if(null != DataManager.pos){
					show = DataManager.pos[which];
				}
				setContent(show);
			}
			break;
			default:
				break;
			}
			// 三角按钮显示判断
			if (showInfo) {
				showInfoText.setVisibility(View.VISIBLE);
				showfobtn.setImageResource(R.drawable.sanjiao_s);
			} else {
				showInfoText.setVisibility(View.GONE);
				showfobtn.setImageResource(R.drawable.sanjiao_n);
			}
			
		}else {// 评论
			convertView = inflater.inflate(R.layout.recommend_item_layout, null);
			RelativeLayout reLayout = (RelativeLayout) convertView.findViewById(R.id.re_layout);
			TextView reName = (TextView) convertView.findViewById(R.id.recname);
			TextView reTime = (TextView) convertView.findViewById(R.id.recname);
			TextView reContent = (TextView) convertView.findViewById(R.id.reccontent);
			reName.setText(DataManager.recoms[position - 1].name);
			reTime.setText(DataManager.recoms[position - 1].time);
			reContent.setText(DataManager.recoms[position - 1].content);
			if(showComm){
				reLayout.setVisibility(View.VISIBLE);
				showcommbtn.setImageResource(R.drawable.sanjiao_s);
			}else{
				reLayout.setVisibility(View.GONE);
				showcommbtn.setImageResource(R.drawable.sanjiao_n);
			}
		}
		return convertView;
	}

	@Override
	public void onClick(View v) {
		Message msg = new Message();
		switch (v.getId()) {
		case R.id.showfobtn:{
			if(showInfo){
				DetailShow.handler.sendMessage(msg);
				showInfo = false;
			}else {
				DetailShow.handler.sendMessage(msg);
				showInfo = true;
			}
		}
			break;
		case R.id.showcommbtn:
			if(showComm){
				showComm = false;
				DetailShow.handler.sendMessage(msg);
			}else{
				showComm = true;
				DetailShow.handler.sendMessage(msg);
			}
			break;
		// 电话
		case R.id.showcall:{
			String tel = null;
			if(URLProtocol.CMD_CONCERT_DETAIL == type){
				tel = DataManager.concerts[which].call;
			}else if(URLProtocol.CMD_MUSIC_DETAIL == type){
				tel = DataManager.musics[which].call;
			}else if(URLProtocol.CMD_PLAY_DETAIL == type){
				tel = DataManager.plays[which].call;
			}else{
				tel = DataManager.pos[which].call;
			}
			
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ tel));
		    mContext.startActivity(intent);
		}
		default:
			break;
		}
	}
	
	private void setContent(Show show){
		showName.setText(show.name);
		showPhoto.setImageDrawable(show.icon);
		showTime.setText("时间: " + show.time);
		showAddr.setText("地址: " + show.addr);
		showPrice.setText("票价: " + show.price);
		showInfoText.setText(show.decs);
		if(null != DataManager.recoms){
			showCommCount.setText("(" + DataManager.recoms.length + ")");
		}else{
			showCommCount.setText("(0)");
		}
		
	}

}
