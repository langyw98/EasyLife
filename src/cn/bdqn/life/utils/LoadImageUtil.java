package cn.bdqn.life.utils;

import android.widget.ImageView;
import cn.bdqn.life.MyApplication;
import cn.bdqn.life.R;
import cn.bdqn.life.net.URLParam;
import cn.bdqn.life.net.URLProtocol;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;

public class LoadImageUtil {
	public static void loadImage(ImageView imageView, String image) {
		String urlStr = URLProtocol.ROOT;
		URLParam param = new URLParam(null);
		param.addParam("cmd", 4);
		param.addParam("image", image);
		urlStr += param.getQueryStr();
		
		RequestQueue mQueue = Volley.newRequestQueue(MyApplication.getInstance());
		ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
		ImageListener listener = ImageLoader.getImageListener(imageView,
				R.drawable.ic_transparent, R.drawable.ic_transparent);
		imageLoader.get(urlStr, listener);
	}
}
