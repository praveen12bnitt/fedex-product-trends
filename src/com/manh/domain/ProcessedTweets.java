package com.manh.domain;


public class ProcessedTweets {	
	private String tweet;
	private int polarity;
	
	
	public ProcessedTweets(String tweet, int polarity) {
		super();
		this.tweet = tweet;
		this.polarity = polarity;
	}
	public String getTweet() {
		return tweet;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	public int getPolarity() {
		return polarity;
	}
	public void setPolarity(int polarity) {
		this.polarity = polarity;
	}
	
	
}
