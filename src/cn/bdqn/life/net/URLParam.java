package cn.bdqn.life.net;

public class URLParam {

	public StringBuffer _query = new StringBuffer();
	
	public URLParam(URLParam param){
		if(param != null){
			_query.append(param._query.toString());
		}
	}
	/**添加String类型参数*/
	public void addParam(String name , String value) {
		if(_query.length() != 0){
			_query.append('&');
		}else{
			_query.append('?');
		}
		_query.append(name).append('=').append(URLEncoder.encode(value, "UTF-8"));
	}
	
	/**添加int类型参数*/
	public void addParam(String name , int value){
		if(_query.length() != 0){
			_query.append('&');
		}else{
			_query.append('?');
		}
		_query.append(name).append('=').append(value+"");
	}
	
	/**获得查询字符串*/
	public String getQueryStr(){
		return _query.toString();
	}
}
