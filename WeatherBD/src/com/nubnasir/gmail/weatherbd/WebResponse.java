package com.nubnasir.gmail.weatherbd;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class WebResponse {

	public static String webConnection(String webUrl) {
		String webResponse = null;
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(webUrl);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				HttpEntity httpEntity = httpResponse.getEntity();
				webResponse = EntityUtils.toString(httpEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return webResponse;

	}
}
