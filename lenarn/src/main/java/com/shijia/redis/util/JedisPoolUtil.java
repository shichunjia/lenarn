package com.shijia.redis.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


public class JedisPoolUtil {
    private  static volatile JedisPool jedisPool;
    private  JedisPoolUtil(){}

    public  static  JedisPool getJedisPool(){
        if (jedisPool==null){
            synchronized (JedisPoolUtil.class){
                if(jedisPool==null){
                    JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
                    jedisPoolConfig.setMaxTotal(100);
                    jedisPoolConfig.setMaxIdle(32);
                    jedisPoolConfig.setMaxWaitMillis(100 * 1000);
                    jedisPoolConfig.setTestOnBorrow(true);
                    jedisPool = new JedisPool(jedisPoolConfig, "192.168.44.128",6379);
                    jedisPool=new JedisPool();
                }

            }
        }
        return  jedisPool;
    }


    public static void  release(JedisPool jedisPool, Jedis jedis){
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
    }

}
