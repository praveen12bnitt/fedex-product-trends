package com.manh.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "trendhistroy")
public class TrendHistroy {

	@Id
	private String id;	
	private String itemName;
	private String trend;
	private int trendRank;
	private Hashtable <Integer, Integer> polarity;
	
	List<String> positiveTweets = new ArrayList<>();
	List<String> negTweets = new ArrayList<>();
	List<String> neturalTweets = new ArrayList<>();
	private int positiveTweetsSize;
	private int negativeTweetsSize;
	private int neturalTweetsSize;
	
	private String date;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Hashtable<Integer, Integer> getPolarity() {
		return polarity;
	}
	public void setPolarity(Hashtable<Integer, Integer> polarity) {
		this.polarity = polarity;
	}
	public String getTrend() {
		return trend;
	}
	public void setTrend(String trend) {
		this.trend = trend;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getTrendRank() {
		return trendRank;
	}
	public void setTrendRank(int trendRank) {
		this.trendRank = trendRank;
	}
	public List<String> getPositiveTweets() {
		return positiveTweets;
	}
	public void setPositiveTweets(List<String> positiveTweets) {
		this.positiveTweets = positiveTweets;
	}
	public List<String> getNegTweets() {
		return negTweets;
	}
	public void setNegTweets(List<String> negTweets) {
		this.negTweets = negTweets;
	}
	public List<String> getNeturalTweets() {
		return neturalTweets;
	}
	public void setNeturalTweets(List<String> neturalTweets) {
		this.neturalTweets = neturalTweets;
	}
	public int getPositiveTweetsSize() {
		return positiveTweetsSize;
	}
	public void setPositiveTweetsSize(int positiveTweetsSize) {
		this.positiveTweetsSize = positiveTweetsSize;
	}
	public int getNegativeTweetsSize() {
		return negativeTweetsSize;
	}
	public void setNegativeTweetsSize(int negativeTweetsSize) {
		this.negativeTweetsSize = negativeTweetsSize;
	}
	public int getNeturalTweetsSize() {
		return neturalTweetsSize;
	}
	public void setNeturalTweetsSize(int neturalTweetsSize) {
		this.neturalTweetsSize = neturalTweetsSize;
	}
	
	
}
