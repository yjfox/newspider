package io.newspider.database;

import redis.clients.jedis.JedisPool;

/*
 * singleton thread-safe, just for practice
 */

public class SingletonRedisConnector {
	private static JedisPool pool;

	private SingletonRedisConnector() {
	}

	public static void destroy() {
		synchronized (SingletonRedisConnector.class) {
			pool.destroy();
			pool = null;
		}
	}

	public static JedisPool getConnectionPool(String node) {
		if (pool == null) {
			synchronized (SingletonRedisConnector.class) {
				if (pool == null) {
					pool = new JedisPool(node);
				}
			}
		}
		return pool;
	}

}
