package cn.bdqn.life.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import cn.bdqn.life.utils.FileUtil;

public class HttpConnection {

	/**从服务器获取数据*/
	public static String httpGet(String urlStr , URLParam param){
		URL url = null;
		HttpURLConnection connection = null;
		try{
			urlStr += param.getQueryStr();
			System.out.println(">>>>>>>"+urlStr);
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(false);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.connect();
			byte[] data = readData(connection.getInputStream());
			
			String result = new String(data,"gbk");
			System.out.println("<<<<<<<" + result);
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(connection != null){
				connection.disconnect();
			}
		}
		return null;
	}
	
	/**读取输入流*/
	public static byte[] readData(InputStream inStream) throws IOException{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while((len = inStream.read(buffer)) != -1){
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		inStream.close();
		outStream.close();
		return data;
	}
	
	/**获取网路图片*/
	public static Drawable getNetimage(String image){
		String urlStr = URLProtocol.ROOT;
		URLParam param = new URLParam(null);
		param.addParam("cmd", 4);
		param.addParam("image", image);
		urlStr += param.getQueryStr();
		Bitmap bm = BitmapFactory.decodeStream(getHttpStream(urlStr,image));
		if(bm != null){
			return new BitmapDrawable(bm);
		}
		return null;
	}
	
	/**获取网路图片*/
	public static InputStream getHttpStream(String strUrl,String image){
		HttpURLConnection conn = null;
		try{
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5*1000);
			conn.setRequestMethod("GET");
			if(conn.getResponseCode() != 200){
				throw new RuntimeException("请求uri失败");
			}
			InputStream inStream = conn.getInputStream();
			byte[] data = readData(inStream);
			FileUtil.saveFile(image, data);//获取图片后保存图片到SDCard
			
			inStream.close();
		
			return new ByteArrayInputStream(data);
		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null){
				conn.disconnect();
			}
		}
		return null;
		
	}
}
