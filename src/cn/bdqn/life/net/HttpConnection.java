package cn.bdqn.life.net;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

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
	
	/**
	 * 上传图片
	 * 
	 * @param url
	 *            上传地址
	 * @param filepath
	 *            图片路径
	 * @return
	 */
	public static String uploadImage(String url, String filepath) {
	    File file = new File(filepath);

	    if (!file.exists()) {
	        Log.i("leslie", "file not exists");
	        return null;
	    }

	    HttpClient client = new DefaultHttpClient();
	    HttpPost post = new HttpPost(url);

	    FileBody fileBody = new FileBody(file, "image/png");
	    MultipartEntity entity = new MultipartEntity();
	    // image 是服务端读取文件的 key
	    entity.addPart("image", fileBody);

	    post.setEntity(entity);

	    try {
	        HttpResponse response = client.execute(post);
	        int statusCode = response.getStatusLine().getStatusCode();
	        String result = EntityUtils.toString(response.getEntity(), "utf-8");

	        if (statusCode == 201) {
	            // upload success
	            // do something
	        }

	        return result;
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
}
