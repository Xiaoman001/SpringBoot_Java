package com.example.demo.common.ImageUnit;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 火车票识别接口地址https://api.xf-yun.com/v1/private/s19cfe728；
 * appid、apiSecret、apiKey请到讯飞开放平台控制台获取；
 *图像数据,base64编码后最不得大于4M；
 */
public class ImageUnit {
	public final static Map<String, String> urlMap = new HashMap();
	public final static Map<String, String> resMap = new HashMap();
	static {
		urlMap.put("火车票", "s19cfe728");
		urlMap.put("发票", "s824758f1");
		urlMap.put("出租车", "sb6db0171");
		resMap.put("火车票", "train_ticket");
		resMap.put("发票", "vat_invoice");
		resMap.put("出租车", "taxi_ticket");
	}
	private String requestUrl = "https://api.xf-yun.com/v1/private/";
	private String type;
	private String resType;
	//控制台获取以下信息
	private String appid = "3187fa24";
	private String apiSecret = "NDA2YTY4NjQ2YjA0NmM0NjkzYTg5ODgy";
	private String apiKey = "3dc3985fccd5a75a4604853370695add";
	//火车票图片存放位置
	private String imagePath = "";
	//解析Json
	private static Gson json = new Gson();

	public static String getInfo(String filePath, String type) throws Exception{
		ImageUnit demo = new ImageUnit();
		demo.type = urlMap.get(type);
		demo.resType = resMap.get(type);
		demo.requestUrl += demo.type;
		String res = null;
		demo.imagePath = filePath;
		try {
			String resp = demo.doRequest();
			JsonParse myJsonParse = json.fromJson(resp, JsonParse.class);
			String textBase64Decode=new String(Base64.getDecoder().decode(myJsonParse.payload.result.text), "UTF-8");
			textBase64Decode = textBase64Decode.substring(0,textBase64Decode.length() - 1);
			JSONObject jsonObject = JSON.parseObject(textBase64Decode);
			res = jsonObject.toJSONString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 请求主方法
	 * @return 返回服务结果
	 * @throws Exception 异常
	 */
	public String doRequest() throws Exception {
		URL realUrl = new URL(buildRequetUrl());
		URLConnection connection = realUrl.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
		httpURLConnection.setDoInput(true);
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Content-type","application/json");

		OutputStream out = httpURLConnection.getOutputStream();
		String params = buildParam();
		//System.out.println("params=>"+params);
		out.write(params.getBytes());
		out.flush();
		InputStream is = null;
		try{
			is = httpURLConnection.getInputStream();
		}catch (Exception e){
			is = httpURLConnection.getErrorStream();
			throw new Exception("make request error:"+"code is "+httpURLConnection.getResponseMessage()+readAllBytes(is));
		}
		return readAllBytes(is);
	}

	/**
	 * 处理请求URL
	 * 封装鉴权参数等
	 * @return 处理后的URL
	 */
	public String buildRequetUrl(){
		URL url = null;
		// 替换调schema前缀 ，原因是URL库不支持解析包含ws,wss schema的url
		String  httpRequestUrl = requestUrl.replace("ws://", "http://").replace("wss://","https://" );
		try {
			url = new URL(httpRequestUrl);
			//获取当前日期并格式化
			SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
			format.setTimeZone(TimeZone.getTimeZone("GMT"));
			String date = format.format(new Date());
			String host = url.getHost();
			/*if (url.getPort()!=80 && url.getPort() !=443){
				host = host +":"+String.valueOf(url.getPort());
			}*/
			StringBuilder builder = new StringBuilder("host: ").append(host).append("\n").//
					append("date: ").append(date).append("\n").//
					append("POST ").append(url.getPath()).append(" HTTP/1.1");
			//System.out.println("host原始字段："+builder);
			Charset charset = Charset.forName("UTF-8");
			Mac mac = Mac.getInstance("hmacsha256");
			SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
			mac.init(spec);
			byte[] hexDigits = mac.doFinal(builder.toString().getBytes(charset));
			String sha = Base64.getEncoder().encodeToString(hexDigits);
			//System.out.println("加密后的signature为："+sha);

			String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
			String authBase = Base64.getEncoder().encodeToString(authorization.getBytes(charset));
			//System.out.println("authorization base64加密后:"+authBase);
			return String.format("%s?authorization=%s&host=%s&date=%s", requestUrl, URLEncoder.encode(authBase), URLEncoder.encode(host), URLEncoder.encode(date));

		} catch (Exception e) {
			throw new RuntimeException("assemble requestUrl error:"+e.getMessage());
		}
	}

	/**
	 * 组装请求参数
	 * 直接使用示例参数，
	 * 替换部分值
	 * @return 参数字符串
	 */
	private String  buildParam() throws Exception{
		String param = "{"+
				"    \"header\": {"+
				"        \"app_id\": \""+appid+"\","+
				"        \"status\": 3"+
				"    },"+
				"    \"parameter\": {"+
				"        \""+this.type+"\": {"+
				"            \"template_list\": \""+this.resType+"\","+
				"            \"result\": {"+
				"                \"encoding\": \"utf8\","+
				"                \"compress\": \"raw\","+
				"                \"format\": \"json\""+
				"            }"+
				"        }"+
				"    },"+
				"    \"payload\": {"+
				"        \""+this.type+"_data_1\": {"+
				"            \"encoding\": \"jpg\","+
				"            \"image\": \""+Base64.getEncoder().encodeToString(read(imagePath))+"\","+
				"            \"status\": 3"+
				"        }"+
				"    }"+
				"}";
		return param;
	}

	/**
	 * 读取流数据
	 * @param is 流
	 * @return 字符串
	 * @throws IOException 异常
	 */
	private String readAllBytes(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		return br.lines().collect(Collectors.joining(System.lineSeparator()));
	}
	public static byte[] read(String filePath) throws IOException {
		InputStream in = new FileInputStream(filePath);
		byte[] data = inputStream2ByteArray(in);
		in.close();
		return data;
	}
	private static byte[] inputStream2ByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int n = 0;
		while ((n = in.read(buffer)) != -1) {
			out.write(buffer, 0, n);
		}
		return out.toByteArray();
	}
	//Json解析
	class JsonParse {
		public Header header;
		public Payload payload;
	}
	class Header{
		public int code;
		public String message;
		public String sid;
	}
	class Payload{
		public Result result;
	}
	class Result{
		public String compress;
		public String encoding;
		public String format;
		public String text;
	}
}


