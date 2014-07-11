package com.manh.fedex.services;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.manh.domain.Item;
import com.manh.domain.ProcessedTweets;
import com.manh.domain.TrendHistroy;
import com.manh.domain.Tweet;

@Service(value="itemService")
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private MongoOperations mongoOperation;
	
	@Autowired	
	private PullTweetsService pullTweetsService;

	public Item StoreTweets(String itemName,   ArrayList tweets) {
		
		HttpClient httpClient = new DefaultHttpClient();
		
		ArrayList<String> tweetIdList = new ArrayList<String>();
		ArrayList<String> tweetTextList = new ArrayList<String>();
		TrendHistroy trendHistory = new TrendHistroy();
		
		List<String> positiveTweets = new ArrayList<>();
		List<String> negTweets = new ArrayList<>();
		List<String> neturalTweets = new ArrayList<>();
		
		boolean processSentimental = false;
		int trendRank=2;
		
		try {
			
			Query searchUserQuery = new Query(Criteria.where("itemName").is(
					itemName));		
			Item existingItem = mongoOperation.findOne(searchUserQuery,Item.class);
			
			if(existingItem==null) {
				existingItem = new Item();
				existingItem.setItemName(itemName);
			}
			
			for (int i=0;i<tweets.size();i++) {
				 JSONObject obj = new JSONObject(); 	
				 obj = (JSONObject) tweets.get(i);
				 String tweetId= (String) obj.get("id_str");
				 String tweetStr= (String) obj.get("text");
				 System.out.println("tweetId"+tweetId);
				 Query searchTweetUserQuery = new Query(Criteria.where("tweetId").is(tweetId));	
					
				 Tweet tweet = mongoOperation.findOne(searchTweetUserQuery,Tweet.class);
				 
				 if(tweet != null) {
						// neg condition
						if(tweet.getPolarity() == 0) {
							negTweets.add(tweet.getTweetId()); 
						} else if (tweet.getPolarity() == 2) {
							neturalTweets.add(tweet.getTweetId());
						} else {
							positiveTweets.add(tweet.getTweetId());
						}
						// Add  Polarity to trend history
					} else {
						tweetIdList.add(tweetId);
						tweetTextList.add(tweetStr);
					}
			}
			
			if(tweetTextList != null && tweetTextList.size()>0) {
				processSentimental = true;
			}
			
			if(processSentimental) {
				
				List<ProcessedTweets> ptweets =  pullTweetsService.doSentimentalProcess1(tweetTextList);
				
				List<Tweet> tweetsToSave = new ArrayList<>();
				
				int ii = 0;
				for(ProcessedTweets pTweet : ptweets) {
					Tweet newTweet = new Tweet();
					newTweet.setTweetId(tweetIdList.get(ii));
					newTweet.setTweet(pTweet.getTweet());
					newTweet.setPolarity(pTweet.getPolarity());
					// TODO save the tweet
					
					// neg condition
					if(newTweet.getPolarity() == 0) {
						negTweets.add(newTweet.getTweetId()); 
					} else if (newTweet.getPolarity() == 2) {
						neturalTweets.add(newTweet.getTweetId());
					} else {
						positiveTweets.add(newTweet.getTweetId());
					} 
					mongoOperation.save(newTweet);
					ii++;
					
				}
			
				if (positiveTweets.size() > negTweets.size())
					trendRank = 4;
				else if (positiveTweets.size() == negTweets.size())
					trendRank = 2;
				else 
					trendRank = 0;
				
				existingItem.setTrendRank(trendRank);
				mongoOperation.save(existingItem);
			
			}
			
			if (positiveTweets.size() > negTweets.size())
				trendRank = 4;
			else if (positiveTweets.size() == negTweets.size())
				trendRank = 2;
			else 
				trendRank = 0;
			
			trendHistory.setItemName(itemName);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			
			trendHistory.setTrendRank(trendRank);	
			
			trendHistory.setDate(dateFormat.format(date));
			trendHistory.setNegTweets(negTweets);
			trendHistory.setPositiveTweets(positiveTweets);
			trendHistory.setNeturalTweets(neturalTweets);
			trendHistory.setTrendRank(trendRank);
			mongoOperation.save(trendHistory);
			
			return	existingItem;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			//httpClient.getConnectionManager().shutdown();
		}
		return null;

	}
}
