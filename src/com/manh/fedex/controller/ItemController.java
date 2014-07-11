package com.manh.fedex.controller;

import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.manh.domain.Item;
import com.manh.domain.Person;
import com.manh.domain.TrendHistroy;
import com.manh.fedex.services.ItemService;
import com.manh.fedex.services.PullTweetsService;
import com.manh.sample.mongodb.CustomerRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private MongoOperations mongoOperation;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired	
	private PullTweetsService pullTweetsService;
	
	@Autowired	
	private ItemService itemService;
	
	@RequestMapping(method = RequestMethod.GET,value = "/getTrend/{itemName}")
	public List<TrendHistroy> getTrend(@PathVariable String itemName) {
		
		itemName = URLDecoder.decode(itemName);
		Query searchTrendQuery = new Query(Criteria.where("itemName").is(
				itemName));	
		ArrayList<TrendHistroy> trends  = (ArrayList) mongoOperation.find(searchTrendQuery, TrendHistroy.class);
		for(TrendHistroy trend: trends) {
			trend.setNegativeTweetsSize(trend.getNegTweets().size());
			trend.setPositiveTweetsSize(trend.getPositiveTweets().size());
			
		}
		
		return trends;
		
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "/save/{itemName}")
	public Item saveItem(@PathVariable String itemName) {
		
		ArrayList tweets = pullTweetsService.pullTweets(itemName);
		
		Item item = itemService.StoreTweets(itemName, tweets);
		
		/*
		
		Hashtable<Integer, Integer> polarity = pullTweetsService.doSentimentalProcess(tweets);
		item.setPolarity(polarity);
		int trendRank=0;
		Integer negativeValue = null;
		Integer positiveValue = null;
		Integer neturalValue = null;
		if (!polarity.isEmpty()) {
			if (polarity.containsKey(4)) {
				positiveValue = polarity.get(4);
			}
			if (polarity.containsKey(0)) {
				negativeValue = polarity.get(0);
			}
			if (polarity.containsKey(2)) {
				neturalValue = polarity.get(2);
			}
			if (positiveValue != null && negativeValue != null) {
				if (positiveValue > negativeValue)
					trendRank = 4;
				else
					trendRank = 0;

			} else if (positiveValue != null) {
				trendRank = 4;
			} else if (negativeValue != null) {
				trendRank = 0;
			} else
				trendRank = 2;

		}
		item.setTrendRank(trendRank);
		
		trendHistory.setItemName(itemName);
		trendHistory.setTrendRank(trendRank);
		trendHistory.setDate(new Date());
		trendHistory.setPolarity(polarity);
		trendHistory.setTrendRank(trendRank);
		
		mongoOperation.save(trendHistory);
		mongoOperation.save(item);*/
		return item;
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/name/{itemName}")
	public Item getItem(@PathVariable String itemName) {		
		// query to search user
		
		itemName = URLDecoder.decode(itemName);
		Query searchUserQuery = new Query(Criteria.where("itemName").is(
				itemName));		
		Item item = mongoOperation.findOne(searchUserQuery,Item.class);
		if(item != null) {
			System.out.println("3. item : " + item);
			System.out.println(item.getId());
		} else {
			item = saveItem(itemName);
		}
		return item;		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/list")
	public List<Item> processSentimentalAnalysis() {
		
		BasicDBObject allQuery = new BasicDBObject();
		
		BasicDBObject fields = new BasicDBObject();
		fields.put("name", 1);
	 
		List<Item> items = mongoOperation.findAll(Item.class);
		
		for(Item item: items) {
			//updateItem(item);
			item = saveItem(item.getItemName());
		}
			
		return items;
	}
	
	
	@RequestMapping(method = RequestMethod.GET,value = "/update/{itemName}")
	public Item updateItem(Item item) {
	//	Item item = new Item();
		TrendHistroy trendHistory = new TrendHistroy();
		//item.setItemName(itemName);
		ArrayList tweets = pullTweetsService.pullTweets(item.getItemName());
		Hashtable<Integer, Integer> polarity = pullTweetsService.doSentimentalProcess(tweets);
		item.setPolarity(polarity);
		int trendRank=2;
		Integer negativeValue = null;
		Integer positiveValue = null;
		Integer neturalValue = null;
		if (!polarity.isEmpty()) {
			if (polarity.containsKey(4)) {
				positiveValue = polarity.get(4);
			}
			if (polarity.containsKey(0)) {
				negativeValue = polarity.get(0);
			}
			if (polarity.containsKey(2)) {
				neturalValue = polarity.get(2);
			}
			if (positiveValue != null && negativeValue != null) {
				if (positiveValue > negativeValue)
					trendRank = 4;
				else
					trendRank = 0;

			} else if (positiveValue != null) {
				trendRank = 4;
			} else if (negativeValue != null) {
				trendRank = 0;
			} else
				trendRank = 2;

		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		item.setTrendRank(trendRank);
		
		trendHistory.setItemName(item.getItemName());
		trendHistory.setTrendRank(trendRank);
		trendHistory.setDate(dateFormat.format(date));
		trendHistory.setPolarity(polarity);
		trendHistory.setTrendRank(trendRank);
		
		mongoOperation.save(trendHistory);
		mongoOperation.save(item);
		return item;
	}

}
