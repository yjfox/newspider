package io.newspider.database;

import io.newspider.crawler.NewSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDataUtil {

	private final static String REDIS_ID_LIST_NAME = "idlist";
	private final static String FILED_TITLE = "title";
	private final static String FILED_URL = "url";
	private final static String FILED_SOURCE = "source";

	private static JedisPool getConnectionPool(String redisNode) {
		return SingletonRedisConnector.getConnectionPool(redisNode);
	}

	private static AtomicLong getUniqID(String redisNode) {
		JedisPool jedisPool = getConnectionPool(redisNode);
		AtomicLong id = new AtomicLong(100000);
		String tempID;
		if ((tempID = jedisPool.getResource().lindex(REDIS_ID_LIST_NAME, -1)) != null) {
			id.set(Long.valueOf(tempID) + 1);
		}
		return id;
	}

	public static void saveToRedis(String redisNode, List<NewSource> news) {
		JedisPool jedisPool = getConnectionPool(redisNode);
		AtomicLong uniqID = getUniqID(redisNode);
		for (NewSource newsOne : news) {
			try (Jedis jedis = jedisPool.getResource()) {
				long hashID = newsOne.getTitle().hashCode();
				if (jedis.setnx(hashID + newsOne.getTitle(), "") == 0) {
					continue;
				}
				jedis.rpush(REDIS_ID_LIST_NAME, String.valueOf(uniqID.get()));
				Map<String, String> titleProperties = new HashMap<String, String>();
				titleProperties.put(FILED_TITLE, newsOne.getTitle());
				titleProperties.put(FILED_URL, newsOne.getUrl());
				titleProperties.put(FILED_SOURCE, newsOne.getSource());
				jedis.hmset(
						"id:" + String.valueOf(String.valueOf(uniqID.get())),
						titleProperties);
			}
			uniqID.incrementAndGet();
		}
	}

	public static List<String> getNewsListFromRedis(String redisNode) {
		JedisPool jedisPool = getConnectionPool(redisNode);
		List<String> articleList = new ArrayList<String>();
		try (Jedis jedis = jedisPool.getResource()) {
			articleList = jedis.lrange(REDIS_ID_LIST_NAME, 0,
					jedis.llen(REDIS_ID_LIST_NAME));
		}
		return articleList;
	}

	public static NewSource getNewsContentFromRedis(String key, String redisNode) {
		NewSource result = new NewSource();
		JedisPool jedisPool = getConnectionPool(redisNode);
		try (Jedis jedis = jedisPool.getResource()) {
			Map<String, String> newsMap = jedis.hgetAll("id:" + key);
			System.out.println("out:"+newsMap);
			result.setSource(newsMap.get(FILED_SOURCE));
			result.setTitle(newsMap.get(FILED_TITLE));
			result.setUrl(newsMap.get(FILED_URL));
		}
		return result;
	}
}
