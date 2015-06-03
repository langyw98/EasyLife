package cn.bdqn.life.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class FileUtil {

	public static String separator = File.separator;
	public static String IMAGEDIR = getRootDir() + separator + "Easylife"+separator+"image";
	
	/**获取SDCard根目录*/
	public static File getRootDir(){
		if(SDCardExist()){
			return Environment.getExternalStorageDirectory();
		}else{
			return null;
		}
	}
	
	/**判断sdcard是否存在*/
	public static boolean SDCardExist(){
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
	
	/**创建文件夹*/
	public static boolean createFile(String name){
		if(SDCardExist()){
			File file = new File(name);
			if(!file.exists()){
				return file.mkdirs();
			}else if(file.exists()){
				return true;
			}
		}
		return false;
	}
	
	/**判断图片是否存在*/
	public static boolean imageExist(String name){
		File file = new File(IMAGEDIR+separator+name+".life");
		if(file.exists()){
			return true;
		}else{
			return false;
		}
	}
	
	public static String saveBitmap(String name, Bitmap mBitmap){
		if (SDCardExist()) {
			File dir = new File(IMAGEDIR + "/");
			mkdir(dir);
			File f = new File(IMAGEDIR + "/" + name + ".life");
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
			return f.getAbsolutePath();
		}
		return null;
	}
	
	public static void saveBitmapByUrl(String url, Bitmap mBitmap) {
		String name = url.substring(url.lastIndexOf("=")+1);
		saveBitmap(name, mBitmap);
	}
	
	public static void mkdir(File f){
		File parentFile = new File(f.getParent());
		if(!f.exists() && parentFile.exists()){
			f.mkdir();
		}else if(!f.exists() && !parentFile.exists()){
			mkdir(parentFile);
			mkdir(f);
		}
	}
	
}
