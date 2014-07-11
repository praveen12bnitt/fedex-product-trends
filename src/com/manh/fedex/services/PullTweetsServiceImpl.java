package com.manh.fedex.services;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.manh.domain.ProcessedTweets;

@Service(value="pullTweetsService")
public class PullTweetsServiceImpl implements PullTweetsService {

	public ArrayList pullTweets(String itemName) {

		ArrayList tweets = new ArrayList();

		HttpClient httpClient = new DefaultHttpClient();

		try {
			itemName = URLEncoder.encode(itemName, "UTF-8");
			
			String url = "http://cedar.csc.ncsu.edu/~healey/tweet/php/recent.php?q="+itemName;
			
	        HttpPost request = new HttpPost(url);

			//StringEntity params = new StringEntity("q=" + itemName);
			request.addHeader("content-type",
					"application/x-www-form-urlencoded");
		//	request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			String respStr = EntityUtils.toString(entity, "UTF-8");
			respStr = respStr.substring(respStr.indexOf("(") + 1,
					respStr.length() - 1);
			// String[] array = respStr.split(":");
			System.out.println(respStr);
			EntityUtils.consume(entity);

			JSONParser parser = new JSONParser();

			JSONObject jsonObject = (JSONObject) parser.parse(respStr);

			// get an array from the JSON object
			JSONArray lang = (JSONArray) jsonObject.get("tw");

			Iterator i = lang.iterator();
			// take each value from the json array separately
			while (i.hasNext()) {
				JSONObject innerObj = (JSONObject) i.next();
				System.out.println("innerObj"+innerObj.toString());
			//	tweets.add(innerObj.get("text").toString());
				tweets.add(innerObj);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return tweets;

	}
	
	public Integer doSentimentalProcess(String tweets) {
		Hashtable<Integer, Integer> rate = new Hashtable();
		
		HttpClient httpClient = new DefaultHttpClient();
		Integer polarity = null ;	
				try {
			        HttpPost request = new HttpPost("http://www.sentiment140.com/api/bulkClassifyJson?appid=sivanandame.c@gmail.com");
			        
			        JSONObject dataValue = new JSONObject();
					List<JSONObject> jsonArray = new ArrayList<>();
					 
					  JSONObject obj = new JSONObject(); 			
						obj.put("text", tweets);
						jsonArray.add(obj);
					 
					  dataValue.put("data", jsonArray); 
					  StringBuffer tweetText = new StringBuffer(dataValue.toJSONString());
			        
			   /*  //   StringBuffer tweetText = new StringBuffer("{\"data\":[");
			        for (int i=0;i<tweets.size();i++) {
			        	tweetText.append("{\"text\": \""+tweets.get(i)+"\"},");
			        }
			        tweetText.append("]}");*/
			        System.out.println(tweetText.toString());
			        StringEntity params =new StringEntity(tweetText.toString());
			        request.addHeader("content-type", "application/x-www-form-urlencoded");
			        request.setEntity(params);
			        HttpResponse response = httpClient.execute(request);
			        HttpEntity entity = response.getEntity();
			        String respStr = EntityUtils.toString(entity,"UTF-8");
			       // String[] array = respStr.split(":");
			        System.out.println(respStr);
			       // EntityUtils.consume(entity);
			        
			        JSONParser parser=new JSONParser();
			        
			        JSONObject jsonObject = (JSONObject) parser.parse(respStr);
			        
			     // get an array from the JSON object
			     	JSONArray lang= (JSONArray) jsonObject.get("data");
			     	
			     	Iterator i = lang.iterator();
			     	
			     	int totalTweets =0;
			     	

					// take each value from the json array separately
					while (i.hasNext()) {
						JSONObject innerObj = (JSONObject) i.next();
						System.out.println("polarity "+ innerObj.get("polarity") + 
								" Tweet " + innerObj.get("text"));
						 polarity = Integer.parseInt(innerObj.get("polarity").toString());
						if(rate.containsKey(polarity)) {
							System.out.println("polarity already counted "+ polarity);
							rate.put(polarity, rate.get(polarity)+1);
						} else { 
							System.out.println("polarity new  "+ polarity);
							rate.put(polarity, 1);
						}
						
						totalTweets++;
					}
					
					System.out.println(totalTweets);
					
					for (Integer key : rate.keySet()) {
					    System.out.println(key + ":" + rate.get(key));
					}
					
			      
			       
			        // handle response here...
			    }catch (Exception ex) {
			        // handle exception here
			    } finally {
			        httpClient.getConnectionManager().shutdown();
			    }
				return polarity;
	}
	
	public Hashtable<Integer, Integer> doSentimentalProcess(ArrayList tweets) {
		Hashtable<Integer, Integer> rate = new Hashtable();
		
		HttpClient httpClient = new DefaultHttpClient();
				
				try {
			        HttpPost request = new HttpPost("http://www.sentiment140.com/api/bulkClassifyJson?appid=sivanandame.c@gmail.com");
			        
			        JSONObject dataValue = new JSONObject();
					List<JSONObject> jsonArray = new ArrayList<>();
					  for (int i=0;i<tweets.size();i++) {
						  JSONObject obj = new JSONObject(); 			
							obj.put("text", tweets.get(i).toString());
							jsonArray.add(obj);
					  }
					  dataValue.put("data", jsonArray); 
					  StringBuffer tweetText = new StringBuffer(dataValue.toJSONString());
			        
			   /*  //   StringBuffer tweetText = new StringBuffer("{\"data\":[");
			        for (int i=0;i<tweets.size();i++) {
			        	tweetText.append("{\"text\": \""+tweets.get(i)+"\"},");
			        }
			        tweetText.append("]}");*/
			        System.out.println(tweetText.toString());
			        StringEntity params =new StringEntity(tweetText.toString());
			        request.addHeader("content-type", "application/x-www-form-urlencoded");
			        request.setEntity(params);
			        HttpResponse response = httpClient.execute(request);
			        HttpEntity entity = response.getEntity();
			        String respStr = EntityUtils.toString(entity,"UTF-8");
			       // String[] array = respStr.split(":");
			        System.out.println(respStr);
			       // EntityUtils.consume(entity);
			        
			        JSONParser parser=new JSONParser();
			        
			        JSONObject jsonObject = (JSONObject) parser.parse(respStr);
			        
			     // get an array from the JSON object
			     	JSONArray lang= (JSONArray) jsonObject.get("data");
			     	
			     	Iterator i = lang.iterator();
			     	
			     	int totalTweets =0;
			     	

					// take each value from the json array separately
					while (i.hasNext()) {
						JSONObject innerObj = (JSONObject) i.next();
						System.out.println("polarity "+ innerObj.get("polarity") + 
								" Tweet " + innerObj.get("text"));
						Integer polarity = Integer.parseInt(innerObj.get("polarity").toString());
						if(rate.containsKey(polarity)) {
							System.out.println("polarity already counted "+ polarity);
							rate.put(polarity, rate.get(polarity)+1);
						} else { 
							System.out.println("polarity new  "+ polarity);
							rate.put(polarity, 1);
						}
						
						totalTweets++;
					}
					
					System.out.println(totalTweets);
					
					for (Integer key : rate.keySet()) {
					    System.out.println(key + ":" + rate.get(key));
					}
					
			      
			       
			        // handle response here...
			    }catch (Exception ex) {
			        // handle exception here
			    } finally {
			        httpClient.getConnectionManager().shutdown();
			    }
				return rate;
	}
	
	public List<ProcessedTweets> doSentimentalProcess1(Collection<String> tweets) {
		Hashtable<Integer, Integer> rate = new Hashtable();
		
		HttpClient httpClient = new DefaultHttpClient();
				
				try {
			        HttpPost request = new HttpPost("http://www.sentiment140.com/api/bulkClassifyJson?appid=sivanandame.c@gmail.com");
			        
			        JSONObject dataValue = new JSONObject();
			        List<JSONObject> jsonArray = new ArrayList<>();
			        for(String tweetStr : tweets) {
			        	JSONObject obj = new JSONObject(); 			
						obj.put("text", tweetStr);
						jsonArray.add(obj);
			        }
			        dataValue.put("data", jsonArray); 
					StringBuffer tweetText = new StringBuffer(dataValue.toJSONString());
			        System.out.println(tweetText.toString());
			        StringEntity params =new StringEntity(tweetText.toString());
			        request.addHeader("content-type", "application/x-www-form-urlencoded");
			        request.setEntity(params);
			        HttpResponse response = httpClient.execute(request);
			        HttpEntity entity = response.getEntity();
			        String respStr = EntityUtils.toString(entity,"UTF-8");
			       // String[] array = respStr.split(":");
			        System.out.println(respStr);
			       // EntityUtils.consume(entity);
			        
			        JSONParser parser=new JSONParser();
			        
			        JSONObject jsonObject = (JSONObject) parser.parse(respStr);
			        
			     // get an array from the JSON object
			     	JSONArray lang= (JSONArray) jsonObject.get("data");
			     	
			     	Iterator i = lang.iterator();
			     	
			     	int totalTweets =0;
			     	
			     	List<ProcessedTweets> ptweets = new ArrayList<>();
			     	
			     	
					// take each value from the json array separately
					while (i.hasNext()) {
						JSONObject innerObj = (JSONObject) i.next();
						System.out.println("polarity "+ innerObj.get("polarity") + 
								" Tweet " + innerObj.get("text"));
						Integer polarity = Integer.parseInt(innerObj.get("polarity").toString());
						
						ProcessedTweets pTweet  = new ProcessedTweets(innerObj.get("text").toString(), polarity);	
						ptweets.add(pTweet);
//						if(rate.containsKey(polarity)) {
//							System.out.println("polarity already counted "+ polarity);
//							rate.put(polarity, rate.get(polarity)+1);
//						} else { 
//							System.out.println("polarity new  "+ polarity);
//							rate.put(polarity, 1);
//						}
						
						totalTweets++;
					}
					
					System.out.println(totalTweets);
					
					for (Integer key : rate.keySet()) {
					    System.out.println(key + ":" + rate.get(key));
					}
					
			      return ptweets;
			       
			        // handle response here...
			    }catch (Exception ex) {
			        // handle exception here
			    } finally {
			        httpClient.getConnectionManager().shutdown();
			    }
				return null;
	}
}
