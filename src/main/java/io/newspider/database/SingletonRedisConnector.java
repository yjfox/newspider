package io.newspider.database;

import redis.clients.jedis.JedisPool;

/*
 * singleton thread-safe, just for practice
 */

public class SingletonRedisConnector {
	private static JedisPool pool;

	private SingletonRedisConnector(String node) {
		pool = new JedisPool(node);
	}

	public synchronized void close() {
		if (pool != null) {
			pool = null;
		}
	}

	public static JedisPool getConnection(String node) {
		if (pool == null) {
			synchronized (SingletonRedisConnector.class) {
				if (pool == null) {
					new SingletonRedisConnector(node);
				}
			}
		}
		return pool;
	}

}
