package cn.bdqn.life.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import cn.bdqn.life.R;



public class MainFragment extends Fragment {
	private FrameLayout fFilm;
	private FrameLayout fExhibition;
	private FrameLayout fFood;
	private FrameLayout fShow;
	private Activity hostActivity;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_main, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		initEvent();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		hostActivity = getActivity();
		fFilm = (FrameLayout) hostActivity.findViewById(R.id.entrance_film);
		fExhibition = (FrameLayout) hostActivity.findViewById(R.id.entrance_exhibition);
		fFood = (FrameLayout) hostActivity.findViewById(R.id.entrance_food);
		fShow = (FrameLayout) hostActivity.findViewById(R.id.entrance_show);
	}
	
	private void initEvent(){
		fFilm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(hostActivity, cn.bdqn.life.activity.FilmMainActivity.class);
				startActivity(intent);
			}
		});
	}
}
