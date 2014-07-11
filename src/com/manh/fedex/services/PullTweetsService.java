package com.manh.fedex.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import com.manh.domain.ProcessedTweets;

public interface PullTweetsService {

	public ArrayList pullTweets(String itemName);
	
	public Hashtable<Integer, Integer> doSentimentalProcess(ArrayList tweets) ;
	
	public List<ProcessedTweets> doSentimentalProcess1(Collection<String> tweets) ;
	
	public Integer doSentimentalProcess(String tweets) ;
}
