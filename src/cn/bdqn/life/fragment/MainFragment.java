package cn.bdqn.life.fragment;

import cn.bdqn.life.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;



public class MainFragment extends Fragment {
	private FrameLayout fFilm;
	private FrameLayout fExhibition;
	private FrameLayout fFood;
	private FrameLayout fShow;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_main, null);
	}
	
}