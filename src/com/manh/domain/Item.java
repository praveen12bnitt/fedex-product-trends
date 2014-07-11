package com.manh.domain;

import java.util.Hashtable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "item")
public class Item {

	@Id
	private String id;

	private String itemName;
	private String itemDesc;
	private String itemImage;
	private Hashtable<Integer, Integer> polarity;
	private List<Tweet> tweet;
	private int trendRank;

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

	public String getItemImage() {
		return itemImage;
	}

	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	public int getTrendRank() {
		return trendRank;
	}

	public void setTrendRank(int trendRank) {
		this.trendRank = trendRank;
	}

	public List<Tweet> getTweet() {
		return tweet;
	}

	public void setTweet(List<Tweet> tweet) {
		this.tweet = tweet;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

}
