package com.shijia.test;

import com.shijia.redis.util.JedisPoolUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisTest {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("192.168.44.128",6379);
        //设置 redis 字符串数据
        jedis.set("runoobkey", "www.runoob.com");
        jedis.set("shijia","go on!!!");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));
        System.out.println(jedis.get("shijia"));


    }
}
