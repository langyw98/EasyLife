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
import cn.bdqn.life.DetailMovie;
import cn.bdqn.life.R;
import cn.bdqn.life.data.DataManager;
import cn.bdqn.life.data.DataManager.Movie;
import cn.bdqn.life.data.DataManager.WillMovie;
import cn.bdqn.life.net.URLProtocol;

public class MovieDetialAdapter extends BaseAdapter implements OnClickListener{

	private Context mContext;
	private int which;
	private int type;
	private boolean showInfo;
	private boolean showComm;
	private TextView movieInfo;
	public static final int REFRESH = 200;
	public DataManager.Movie movie;
	public DataManager.WillMovie willMovie;
	private ImageView movieinfobtn;
	private ImageView moviecommentbtn;
	
	public MovieDetialAdapter(Context context, int which, int type) {
		this.mContext = context;
		this.which = which;
		this.type = type;
		if(URLProtocol.CMD_FILM_WILL_DETAIL == type){
			willMovie = DataManager.willmovies[which];
		}else {
			movie = DataManager.movies[which];
		}
	}
	
	public MovieDetialAdapter(Context context, Object obj, int type) {
		this.mContext = context;
		this.type = type;
		if(URLProtocol.CMD_FILM_WILL_DETAIL == type){
			willMovie = (WillMovie) obj;
		}else {
			movie = (Movie) obj;
		}
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(null == DataManager.recoms){
			return 1;
		}
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
		if(0 == position){
			convertView = inflater.inflate(R.layout.moviedetial_head, null);
			TextView commentCount = (TextView) convertView.findViewById(R.id.commentcount);
			if(null != DataManager.recoms){
				commentCount.setText("(" + DataManager.recoms.length + ")");
			}else{
				commentCount.setText("(0)");
			}
			TextView movieName = (TextView) convertView.findViewById(R.id.moviename);
			ImageView moviePhoto = (ImageView) convertView.findViewById(R.id.photo);
			TextView labelText = (TextView) convertView.findViewById(R.id.label);
			TextView playerText = (TextView) convertView.findViewById(R.id.player);
			TextView timeText = (TextView) convertView.findViewById(R.id.time);
			TextView timeLongText = (TextView) convertView.findViewById(R.id.timelong);
			movieInfo = (TextView) convertView.findViewById(R.id.movieinfo);
			movieinfobtn = (ImageView) convertView.findViewById(R.id.movieinfobtn);
			if(showInfo){
				movieinfobtn.setImageResource(R.drawable.sanjiao_s);
			}else{
				movieinfobtn.setImageResource(R.drawable.sanjiao_n);
			}
			movieinfobtn.setOnClickListener(this);
			moviecommentbtn = (ImageView) convertView.findViewById(R.id.moviecommentbtn);
			if(showComm){
				moviecommentbtn.setImageResource(R.drawable.sanjiao_s);
			}else{
				moviecommentbtn.setImageResource(R.drawable.sanjiao_n);
			}
			moviecommentbtn.setOnClickListener(this);
			if(URLProtocol.CMD_FILMDETAIL == type){
				movieName.setText(movie.name);
				moviePhoto.setImageDrawable(movie.icon);
				labelText.setText(movie.type);
				playerText.setText(movie.player);
				timeText.setText(movie.time);
				timeLongText.setText(movie.timelong);
				movieInfo.setText(movie.desc);
			}else if(URLProtocol.CMD_FILM_WILL_DETAIL == type) {
				movieName.setText(willMovie.name);
				moviePhoto.setImageDrawable(willMovie.icon);
				labelText.setText(willMovie.type);
				playerText.setText(willMovie.player);
				timeText.setText(willMovie.time);
				timeLongText.setText(willMovie.timelong);
				movieInfo.setText(willMovie.desc);
			}
			if(showInfo){
				movieInfo.setVisibility(View.VISIBLE);
			}else {
				movieInfo.setVisibility(View.GONE);
			}
		}else{
			convertView = inflater.inflate(R.layout.recommend_item_layout, null);
			TextView reName = (TextView) convertView.findViewById(R.id.recname);
			TextView reTime = (TextView) convertView.findViewById(R.id.rectime);
			TextView reContent = (TextView) convertView.findViewById(R.id.reccontent);
			reName.setText(DataManager.recoms[position - 1].name);
			reTime.setText(DataManager.recoms[position - 1].time);
			reContent.setText(DataManager.recoms[position - 1].content);
			RelativeLayout reLayout = (RelativeLayout) convertView.findViewById(R.id.re_layout);
			
			if(showComm){
				reLayout.setVisibility(View.VISIBLE);
			}else{
				reLayout.setVisibility(View.GONE);
			}
		}
		return convertView;
	}

	@Override
	public void onClick(View v) {
		Message msg = new Message();
		switch (v.getId()) {
		case R.id.movieinfobtn:
			if(showInfo){
				showInfo = false;
			}else{
				showInfo = true;
			}
			break;
		case R.id.moviecommentbtn:
			if(showComm){
				showComm = false;
			}else{
				showComm = true;
			}
			break;
		}
		msg.what = REFRESH;
		DetailMovie.handler.sendMessage(msg);
	}

}
