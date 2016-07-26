package com.virtucure.practiceforms;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.webkit.CookieManager;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsyncWorkerNetwork extends AsyncTask<Map<String,String>, String, Map<String,String>>
{
	ProgressDialog pd;
	ActionCallback func;
	private static final String COOKIES_HEADER = "Set-Cookie";
	private static final String COOKIE = "Cookie";
	private static final String SECRET_HEADER = "X-ACCESS-TOKEN";
	private CookieManager cookieManager = CookieManager.getInstance();

	public AsyncWorkerNetwork(Context con, ActionCallback callback, String loadingMessage) {
		// TODO Auto-generated constructor stub
		pd = new ProgressDialog(con);
        pd.setMessage(loadingMessage+"...");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        func = callback;
	}
	
	@Override
	protected void onPostExecute(Map<String,String> result) {
	// TODO Auto-generated method stub
		pd.dismiss();
		if(func != null)
		{
			try {
				func.onCallback(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onPostExecute(result);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected Map<String, String> doInBackground(Map<String, String>... params) {

		URL url = null;
		HttpURLConnection conn = null;
		String cookie = null;
		PrintWriter out = null;
		Map<String,String> result = new HashMap<>();

		DataOutputStream dataOutputStream = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;

		try {
			url = new URL(params[0].get("serverUrl"));
			params[0].remove("serverUrl");

			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(50000);
			conn.setConnectTimeout(30000);
			conn.setDoInput(true);

			cookie = cookieManager.getCookie(conn.getURL().toString());
			conn.setRequestProperty(COOKIE, cookie);
			conn.setRequestProperty(SECRET_HEADER, User.secretHeader);

			if(params[0].get("operationType") == "6")
				conn.setRequestMethod("GET");
			else {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}

			if(params[0].get("operationType") == "1"){
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setFixedLengthStreamingMode(
						getPostDataString(params[0]).getBytes().length);
				out = new PrintWriter(conn.getOutputStream());
				out.print(getPostDataString(params[0]));
			}else if (params[0].get("operationType") == "2"){
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("cid", User.cid);
				out = new PrintWriter(conn.getOutputStream());
				out.print(params[0].get("retrieveparams"));
			}else if(params[0].get("operationType") == "3"){
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("cid", params[0].get("cid"));
				conn.setRequestProperty("brandorBranchName", params[0].get("bBname"));
				String inputParams = new Gson().toJson(params[0]);
				JSONObject inputJson = new JSONObject(inputParams);
				inputJson.remove("operationType");
				inputJson.remove("bBname");
				inputJson.remove("cid");
				inputJson.remove("agree");
				out = new PrintWriter(conn.getOutputStream());
				out.print(inputJson);
			}else if(params[0].get("operationType") == "4"){
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("cid", User.cid);
				out = new PrintWriter(conn.getOutputStream());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("regId",params[0].get("regid"));
				jsonObject.put("caseIdStatus","true");
				out.print(jsonObject);
			}else if(params[0].get("operationType") == "5"){
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("cid", User.cid);
				conn.setRequestProperty("userName", User.username);
				conn.setRequestProperty("fullName", User.fullName);
				String inputParams = params[0].get("insertparams");
				//conn.setFixedLengthStreamingMode(inputParams.getBytes("utf-8").length);
				out = new PrintWriter(conn.getOutputStream());
				out.print(inputParams);
			}else if(params[0].get("operationType") == "7"){
				conn.setRequestProperty("Content-Type", "application/json");
				out = new PrintWriter(conn.getOutputStream());
				JSONObject inputJson = new JSONObject();
				inputJson.put("formType", "");
				out.print(inputJson);
			}else if(params[0].get("operationType") == "8"){
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("cid", User.cid);
				conn.setRequestProperty("linkType", User.linkType);
				conn.setRequestProperty("userType", User.userType);
				out = new PrintWriter(conn.getOutputStream());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("caseRecordNo",params[0].get("caserecordno"));
				out.print(jsonObject);
			}else if(params[0].get("operationType") == "9"){
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Accept", "application/json");
				out = new PrintWriter(conn.getOutputStream());
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("healthRegistrationId", params[0].get("healthRegistrationId"));
				out.print(jsonObject);
			}else if(params[0].get("operationType") == "10"){
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("cid", User.cid);
				conn.setRequestProperty("userName", User.username);
				conn.setRequestProperty("fullName", User.fullName);
				conn.setRequestProperty("brandorBranchName", User.brandorBranchName);
				out = new PrintWriter(conn.getOutputStream());
				out.print(params[0].get("insertparams"));
			}else if(params[0].get("operationType") == "11") {
				conn.setRequestProperty("X-ACCESS-TOKEN", User.secretHeader);
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

				File file = new File(params[0].get("inputfile"));
				FileInputStream fileInputStream = new FileInputStream(file);

				dataOutputStream = new DataOutputStream(conn.getOutputStream());
				dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
				dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + file.getName() + "\"" + lineEnd);
				dataOutputStream.writeBytes(lineEnd);

				// create a buffer of  maximum size
				bytesAvailable = fileInputStream.available();

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				while (bytesRead > 0) {

					dataOutputStream.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				}

				// send multipart form data necesssary after file data...
				dataOutputStream.writeBytes(lineEnd);
				dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
			}
			else if(params[0].get("operationType") == "12") {
				conn.setRequestProperty("X-ACCESS-TOKEN", User.secretHeader);
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("cid", User.cid);
				out = new PrintWriter(conn.getOutputStream());
			}
			else if(params[0].get("operationType") == "13") {
				conn.setRequestProperty("X-ACCESS-TOKEN", User.secretHeader);
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("cid", User.cid);
				conn.setRequestProperty("linkType", User.linkType);
				conn.setRequestProperty("userType", User.userType);
				out = new PrintWriter(conn.getOutputStream());
				out.print(params[0].get("retrieveparams"));
			}
			else if(params[0].get("operationType") == "14") {
				conn.setRequestProperty("X-ACCESS-TOKEN", User.secretHeader);
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("cid", User.cid);
				conn.setRequestProperty("brandorBranchName", User.brandorBranchName);
				conn.setRequestProperty("linkType", User.linkType);
				out = new PrintWriter(conn.getOutputStream());
			}

			if(out != null)
				out.close();

			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				if(params[0].get("operationType") == "1"){
					User.secretHeader = conn.getHeaderField(SECRET_HEADER);
				}
				else if(params[0].get("operationType") == "6"){
					User.secretHeader = "";
					//cookie=null;
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
						cookieManager.removeAllCookies(null);
					}
					else cookieManager.removeAllCookie();
				}
				List<String> cookieList = conn.getHeaderFields().get(COOKIES_HEADER);
				if (cookieList != null) {
					for (String cookieTemp : cookieList)
						cookieManager.setCookie(conn.getURL().toString(), cookieTemp);
				}
				BufferedReader reader1 =
						new BufferedReader(new InputStreamReader(conn.getInputStream()));

				StringBuilder sd = new StringBuilder();
				String ch;
				while((ch = reader1.readLine()) != null) {
					sd.append(ch);
				}

				if(sd != null){
					result.put("result", new JSONObject(sd.toString()).get("data").toString());
				}
			}
			else if(responseCode == HttpURLConnection.HTTP_UNAUTHORIZED){
				result.put("error", "Invalid Credentials!!!");
			}
			else if(responseCode == HttpURLConnection.HTTP_BAD_REQUEST && "5"!=params[0].get("operationType")){
				result.put("error", "No Records Found!!!");
			}
			else {
				result.put("error", "Problem while connecting to server!!!");
			}
		}
		catch (Exception e){
			result.put("error", "Problem while connecting to server!!!");
		}
		finally {
			conn.disconnect();
		}

		return result;
	}

	private String getPostDataString(Map<String, String> params) throws UnsupportedEncodingException{
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for(Map.Entry<String, String> entry : params.entrySet()){
			if (first)
				first = false;
			else
				result.append("&");

			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
		}

		return result.toString();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		try
		{
			pd.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		super.onPreExecute();
	}

	/*
	public static ArrayList<NameValuePair> splitQuery(String query) throws UnsupportedEncodingException {
		ArrayList<NameValuePair> query_pairs = new ArrayList<NameValuePair>();
		String[] pairs = query.split("&");
		for (String pair : pairs) {
			int idx = pair.indexOf("=");
			query_pairs.add(new BasicNameValuePair(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
					URLDecoder.decode(pair.substring(idx + 1), "UTF-8")));
		}
		return query_pairs;
	}

	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			 while ((line = reader.readLine()) != null) {
				  sb.append(line + "\n");
			 }
		} catch (IOException e) {
			 e.printStackTrace();
		} finally {
			 try {
				  is.close();
			 } catch (IOException e) {
				  e.printStackTrace();
			 }
		}

		return sb.toString();
	}
	*/

}
