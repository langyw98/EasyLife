package cn.bdqn.life.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapCache implements ImageCache {
	private LruCache<String, Bitmap> cache;
	
	public BitmapCache() {
		cache = new LruCache<String, Bitmap>(8 * 1024 * 1024) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight();
			}
		};
	}

	@Override
	public Bitmap getBitmap(String url) {
		return cache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		cache.put(url, bitmap);
		saveMyBitmap(url, bitmap);
	}

	public void saveMyBitmap(String url, Bitmap mBitmap) {
		String name = url.substring(url.lastIndexOf("=")+1);
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			File dir = new File(FileUtil.IMAGEDIR + "/");
			if(!dir.exists()){
				dir.mkdir();
			}
			File f = new File(FileUtil.IMAGEDIR + "/" + name + ".life");
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			FileOutputStream fOut = null;
			try {
				fOut = new FileOutputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			try {
				fOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}