package com.searchbooks.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Utils {

	/**
	 * api 호출을 위한 메소드 (POST방식)
	 * 
	 * @throws Exception
	 */
	public static String getHttpPOST2String(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
		//
		HttpClient httpclient = new DefaultHttpClient();
		String responseBody = null;
		try {
			HttpPost post = new HttpPost(url);

			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(3000)
					.setConnectTimeout(3000)
					.setConnectionRequestTimeout(3000)
					.build();
			post.setConfig(requestConfig);

			if (params != null && !params.isEmpty()) {

				List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
				for (String key : params.keySet()) {
					parameters.add(new BasicNameValuePair(key, params.get(key)));
				}

				UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
				post.setEntity(reqEntity);
			}

			if (headers != null && !headers.isEmpty()) {

				for (String key : headers.keySet()) {

					post.addHeader(key, headers.get(key));
				}
			}

			HttpResponse response = httpclient.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();

			if(!(statusCode >= 200 && statusCode <= 399)){
				return "Fail";
			}

			responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return responseBody;
	}


	/**
	 * api 호출을 위한 메소드 (GET방식)
	 *
	 * @throws Exception
	 */
	public static String getHttpGET2String(String url, Map<String, String> headers, Map<String, String> params)
			throws Exception {

		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				if (url.lastIndexOf("?") > -1) {
					url = url + "&" + key + "=" + URLEncoder.encode(params.get(key), "UTF-8");
				} else {
					url = url + "?" + key + "=" + URLEncoder.encode(params.get(key), "UTF-8");
				}
			}
		}

		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(3000)
				.setConnectTimeout(3000)
				.setConnectionRequestTimeout(3000)
				.build();

		HttpGet get = new HttpGet(url);
		get.setConfig(requestConfig);

		if (headers != null && !headers.isEmpty()) {

			for (String key : headers.keySet()) {

				get.addHeader(key, headers.get(key));
			}
		}

		HttpClient httpclient = HttpClientBuilder.create().build();
		HttpResponse response = httpclient.execute(get);
		int statusCode = response.getStatusLine().getStatusCode();

		if(!(statusCode >= 200 && statusCode <= 399)){
			return "Fail";
		}

		HttpEntity entity = response.getEntity();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		entity.writeTo(out);

		return new String(out.toByteArray(), Charset.forName("UTF-8"));
	}

	/**
	 * 네이버 api 결과를 카카오 형식에 맞게 변환 시켜주는 메소드
	 *
	 */
	public static ArrayList<Map<String, Object>> modifyNaverKeys(ArrayList<Map<String, Object>>  items){

		for(int i = 0 ; i < items.size() ; i++){
			Map<String, Object> mapItem = items.get(i);
			ArrayList<String> authorList = new ArrayList<>();
			authorList.add(((String) mapItem.get("author")).replaceAll("\\|", ","));
			mapItem.put("authors", authorList);
			mapItem.put("contents", mapItem.get("description"));
			mapItem.put("datetime", mapItem.get("pubdate"));
			mapItem.put("sale_price", mapItem.get("discount"));
			mapItem.put("thumbnail", mapItem.get("image"));
			mapItem.put("url", mapItem.get("link"));
			mapItem.put("isbn", ((String)mapItem.get("isbn")).replaceAll("<b>", "").replaceAll("</b>", ""));

			mapItem.remove("author");
			mapItem.remove("description");
			mapItem.remove("pubdate");
			mapItem.remove("discount");
			mapItem.remove("image");
			mapItem.remove("link");

			items.set(i, mapItem);
		}

		return items;
	}
}
