package cn.bdqn.life;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import cn.bdqn.life.data.DataManager;
/*
 * 美食详情
 */
public class Delicaciesdetail extends Activity implements OnClickListener  {
	private TextView headText;
	private ViewFlipper flipper;
	private float downX = 0;
	private int indicator = 0;
	 private ImageView[] imgs;
	 private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delicaciesdetail_layout);
		Intent intent = getIntent();
		position = intent.getIntExtra("position", 0);
		headText = (TextView) findViewById(R.id.headtext);
		headText.setText(R.string.delicaciesdetail);
		flipper = (ViewFlipper) findViewById(R.id.viewflipper);
		LinearLayout layout = (LinearLayout) findViewById(R.id.indicator);
		LayoutInflater inflater = LayoutInflater.from(this);
		int len = DataManager.delicacies[position].hoteds.length;
		imgs = new ImageView[len];
		for(int i = 0; i < len; i++){
			View view = inflater.inflate(R.layout.delicaciesdetail_addr, null);
			ImageView imageView = new ImageView(this);
			TextView delicaciesdetailName = (TextView) view.findViewById(R.id.delicaciesdetail_name);
			ImageView delicaciesdetailPhoto = (ImageView) view.findViewById(R.id.delicaciesdetail_photo);
			TextView fooddetailAddr = (TextView) view.findViewById(R.id.fooddetail_addr);
			ImageView foodPhoto = (ImageView) view.findViewById(R.id.food_photo);
			TextView foodName = (TextView) view.findViewById(R.id.foodname);
			TextView foodOp = (TextView) view.findViewById(R.id.foodop);
			TextView foodNp = (TextView) view.findViewById(R.id.foodnp);
			TextView callText = (TextView) view.findViewById(R.id.delicaciescall);
			ImageButton telButton =  (ImageButton) view.findViewById(R.id.delicacies_tel);
			telButton.setOnClickListener(this);
			delicaciesdetailName.setText(DataManager.delicacies[position].name);
			delicaciesdetailPhoto.setImageDrawable(DataManager.delicacies[position].icon);
			fooddetailAddr.setText("地址: " + DataManager.delicacies[position].addr);
			foodPhoto.setImageDrawable(DataManager.delicacies[position].hoteds[i].icon);
			foodName.setText("菜名: " + DataManager.delicacies[position].hoteds[i].name);
			callText.setText("电话: " +  DataManager.delicacies[position].call);
			foodOp.setText("原价: " + DataManager.delicacies[position].hoteds[i].oldprice + "");
			foodNp.setText("现价: " + DataManager.delicacies[position].hoteds[i].nowprice + "");
			flipper.addView(view);
			// 添加指示器
			if(0 == i){
				imageView.setImageResource(R.drawable.select_dot);
			}else {
				imageView.setImageResource(R.drawable.normal_dot);
			}
			if(i != 0){
				imageView.setPadding(20, 0, 0, 0);
			}
			imgs[i] = imageView;
			layout.addView(imageView);
		}
	}
	
	// 滑动事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		 float x = event.getX();
		 int action = event.getAction();
		 switch (action) {
		case MotionEvent.ACTION_DOWN:
			downX = x;
			break;
		case MotionEvent.ACTION_UP:
			if((x - downX) >= 200){
				flipper.showNext();
				imgs[indicator].setImageResource(R.drawable.normal_dot);
				if((indicator = indicator + 1) <imgs.length){
				}else{
					indicator = 0;
				}
				// 指示器变动
				imgs[indicator].setImageResource(R.drawable.select_dot);
			}else if((x - downX) <= -200){
				flipper.showPrevious();
				imgs[indicator].setImageResource(R.drawable.normal_dot);
				if((indicator = indicator - 1) >= 0){
				}else{
					indicator = imgs.length - 1;
				}
				imgs[indicator].setImageResource(R.drawable.select_dot);
			}
			break;
		default:
			break;
		}
		 return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.delicacies_tel:{
			String tel = DataManager.delicacies[position].call;
	        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ tel));
		    this.startActivity(intent);
		}
			break;
		}
		
	}
}
