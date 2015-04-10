package cn.bdqn.life.customview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import cn.bdqn.life.R;

public class CommentsListView extends ListView{

	View footer;// 底部布局；
	
	public CommentsListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	public CommentsListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		// TODO Auto-generated constructor stub
	}

	public CommentsListView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 添加底部加载提示布局到listview
	 * 
	 * @param context
	 */
	private void initView(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		footer = inflater.inflate(R.layout.comment_listview_footer, null);
		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
		this.addFooterView(footer);
	}
	
	public void showLoadingFooter(boolean state){
		if(state){
			footer.findViewById(R.id.load_layout).setVisibility(
					View.VISIBLE);
		}else{
			footer.findViewById(R.id.load_layout).setVisibility(
					View.GONE);
		}
	}
}
