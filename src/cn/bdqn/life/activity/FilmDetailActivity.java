package cn.bdqn.life.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.bdqn.life.R;
import cn.bdqn.life.customview.CommentsListView;
import cn.bdqn.life.dao.ICommentDao;
import cn.bdqn.life.dao.IFilmDao;
import cn.bdqn.life.dao.impl.CommentImpl;
import cn.bdqn.life.dao.impl.FilmImpl;
import cn.bdqn.life.data.LifePreferences;
import cn.bdqn.life.entity.Comment;
import cn.bdqn.life.entity.Film;
import cn.bdqn.life.fragment.FilmListFragment;

public class FilmDetailActivity extends Activity {
	private static final int MSG_GET_COMMENT_FAILED = 1;
	private static final int MSG_GET_COMMENT_SUCCESS = 2;
	private static final int MSG_GET_COMMENT_NOUPDATE = 3;
	
	private static final int MSG_ADD_COMMENT_FAILED = 4;
	private static final int MSG_ADD_COMMENT_SUCCESS = 5;
	
	private static final int PAGELENGTH = 10;
	
	private CommentsListView listView;
	private LinearLayout ll_comment;
	private EditText et_comment;
	private Button btn_comment;
	
	private Film film;
	private ListViewAdapter adapter;
	private List<Comment> comments = new ArrayList<Comment>();
	private List<Comment> loadComments; 
	private Comment addComment;
	
	private boolean isIntroductionFold = true;
	private int type;
	private int id;
	private boolean isReachEnd = false;
	private boolean isLoading = false;
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			switch(msg.what){
			case MSG_GET_COMMENT_FAILED:
				Toast.makeText(FilmDetailActivity.this, "获取评论失败", Toast.LENGTH_SHORT).show();
				isLoading = false;
				listView.showLoadingFooter(false);
				break;
			case MSG_GET_COMMENT_SUCCESS:
				comments.addAll(loadComments);
				adapter.notifyDataSetChanged();
				isLoading = false;
				listView.showLoadingFooter(false);
				break;
			case MSG_GET_COMMENT_NOUPDATE:
				isReachEnd = true;
				Toast.makeText(FilmDetailActivity.this, "已经到底啦", Toast.LENGTH_SHORT).show();
				isLoading = false;
				listView.showLoadingFooter(false);
				break;
			case MSG_ADD_COMMENT_FAILED:
				addComment = null;
				Toast.makeText(FilmDetailActivity.this, "评论发送失败", Toast.LENGTH_SHORT).show();
				break;
			case MSG_ADD_COMMENT_SUCCESS:
				comments.add(0, addComment);
				addComment = null;
				adapter.notifyDataSetChanged();
				break;
			}
			
		}
	};
	
	private class ListViewAdapter extends BaseAdapter{

		private View item_profile = null;
		private View item_introduction = null;
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 2 + comments.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(position == 0){
				if(item_profile == null){
					item_profile = View.inflate(FilmDetailActivity.this, R.layout.item_filmdetaillist_profile, null);
					ImageView photo = (ImageView) item_profile.findViewById(R.id.iv_photo);
					TextView name = (TextView) item_profile.findViewById(R.id.tv_name);
					TextView type = (TextView) item_profile.findViewById(R.id.tv_type);
					TextView player = (TextView) item_profile.findViewById(R.id.tv_player);
					TextView time = (TextView) item_profile.findViewById(R.id.tv_time);
					TextView timelong = (TextView) item_profile.findViewById(R.id.tv_timelong);
					
					name.setText(film.name);
					type.setText(film.type);
					player.setText(film.player);
					time.setText(film.time);
					timelong.setText(film.timelong);
					
				}
				return item_profile;
			}else if(position == 1){
				if(item_introduction == null){
					item_introduction = View.inflate(FilmDetailActivity.this, R.layout.item_filmdetaillist_introduction, null);
					TextView introduction = (TextView) item_introduction.findViewById(R.id.tv_introduction);
					ImageView symbol = (ImageView) item_introduction.findViewById(R.id.iv_symbol);
					
					introduction.setText(film.desc);
				}
				return item_introduction;
			}else{
				ViewHolder viewHolder = null;
				if(convertView == null || convertView == item_profile || convertView == item_introduction){
					convertView = View.inflate(FilmDetailActivity.this, R.layout.item_comment, null);
					viewHolder = new ViewHolder();
					viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
					viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
					viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_content);
					
					convertView.setTag(viewHolder);
				}else{
					viewHolder = (ViewHolder) convertView.getTag();
				}
				
				Comment comment = comments.get(position - 2);
				viewHolder.tvName.setText(comment.userName);
				viewHolder.tvTime.setText(comment.time);
				viewHolder.tvContent.setText(comment.content);
			}
			return convertView;
		}
		private class ViewHolder{
			public TextView tvName;
			public TextView tvContent;
			public TextView tvTime;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filmdetail);
		
		Intent intent = getIntent();
		id = intent.getIntExtra("id", -1);
		type = intent.getIntExtra("fragmentType", -1);
		if(id == -1 || type == -1){
			finish();
			Toast.makeText(this, "数据获取失败", Toast.LENGTH_LONG).show();
			return;
		}
		IFilmDao filmdao = new FilmImpl();
		if(type == FilmListFragment.FRAGMENTTYPE_RECENT){
			film = filmdao.getFilm(id);
		}else{
			film = filmdao.getUpcomingFilm(id);
		}
		initView();
		loadComment(-1);
	}
	
	private void loadComment(final int startPos){
		if(isLoading){
			return;
		}
		isLoading = true;
		listView.showLoadingFooter(true);
		new Thread(){
			public void run() {
				ICommentDao commentDao = new CommentImpl();
				loadComments = commentDao.getComments(type, id, startPos, PAGELENGTH);
				if(loadComments == null){
					handler.sendEmptyMessage(MSG_GET_COMMENT_FAILED);
				}else if(loadComments.size() == 0){
					handler.sendEmptyMessage(MSG_GET_COMMENT_NOUPDATE);
				}else{
					handler.sendEmptyMessage(MSG_GET_COMMENT_SUCCESS);
				}
			}
		}.start();
	}
	
	private void initView(){
		ll_comment = (LinearLayout) findViewById(R.id.ll_comment);
		et_comment = (EditText) findViewById(R.id.et_comment);
		btn_comment = (Button) findViewById(R.id.btn_comment);
		listView = (CommentsListView) findViewById(R.id.lv_film);
		
		adapter = new ListViewAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(position == 1){
					TextView introduction = (TextView) view.findViewById(R.id.tv_introduction);
					if(isIntroductionFold){
						introduction.setMaxLines(100);
						isIntroductionFold = false;
					}else{
						introduction.setMaxLines(2);
						isIntroductionFold = true;
					}
				}
			}
		});
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if(!isReachEnd&&totalItemCount - (firstVisibleItem + visibleItemCount) <= 3){
					int lastIndex = comments.size() - 1;
					if(lastIndex >= 0){
						int startPos = comments.get(lastIndex).id;
						loadComment(startPos);
					}
				}
				if(ll_comment == null){
					return;
				}
				if(firstVisibleItem + visibleItemCount >= 3){
					ll_comment.setVisibility(View.VISIBLE);
				}else{
					ll_comment.setVisibility(View.GONE);
				}
				
			}
		});
		

		btn_comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String strComment = et_comment.getText().toString().trim();
				if(strComment.isEmpty()){
					Toast.makeText(FilmDetailActivity.this, "无法发送空评论！", Toast.LENGTH_SHORT).show();
				}else{
					new Thread(){
						public void run() {
							Comment comment = new Comment();
							LifePreferences.getPreferences().init(getApplicationContext());
							comment.userName = LifePreferences.getPreferences().getNickName();
							comment.content = strComment;
							comment.type = type;
							comment.tid = id;
							comment.time = null;
							ICommentDao commentDao = new CommentImpl();
							addComment = commentDao.addComment(comment);
							if(addComment != null){
								handler.sendEmptyMessage(MSG_ADD_COMMENT_SUCCESS);
							}else{
								handler.sendEmptyMessage(MSG_ADD_COMMENT_FAILED);
							}
						};
					}.start();
					et_comment.setText("");
				}
			}
		});
	}
	
}
