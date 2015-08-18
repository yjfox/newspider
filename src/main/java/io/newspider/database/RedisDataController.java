package io.newspider.database;

import io.newspider.crawler.NewSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDataController {

	public static void saveToRedis(String redisNode, List<NewSource> news) {
		JedisPool jedisPool = SingletonRedisConnector.getConnection(redisNode);
		for (NewSource newsOne : news) {
			try (Jedis jedis = jedisPool.getResource()) {
				long id = newsOne.getTitle().hashCode();
				if (jedis.exists("id:" + id + newsOne.getTitle())) {
					continue;
				}
				jedis.rpush("idlist", id + newsOne.getTitle());
				Map<String, String> titleProperties = new HashMap<String, String>();
				titleProperties.put("title", newsOne.getTitle());
				titleProperties.put("url", newsOne.getUrl());
				titleProperties.put("source", newsOne.getSource());
				jedis.hmset("id:" + id + newsOne.getTitle(), titleProperties);
			}
		}
		jedisPool.destroy();
	}

	public static List<String> getNewsListFromReds(String redisNode) {
		JedisPool jedisPool = SingletonRedisConnector.getConnection(redisNode);
		List<String> articleList = new ArrayList<String>();
		try (Jedis jedis = jedisPool.getResource()) {
			articleList = jedis.lrange("idlist", 0, jedis.llen("idlist"));
		}
		return articleList;
	}

	public static NewSource getNewsFromRedis(String key, String redisNode) {
		NewSource result = new NewSource();
		JedisPool jedisPool = SingletonRedisConnector.getConnection(redisNode);
		try (Jedis jedis = jedisPool.getResource()) {
			Map<String, String> newsMap = jedis.hgetAll(key);
			result.setSource(newsMap.get("source"));
			result.setTitle(newsMap.get("title"));
			result.setUrl(newsMap.get("url"));
		}
		return result;
	}
}
