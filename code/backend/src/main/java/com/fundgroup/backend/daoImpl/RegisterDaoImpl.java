package com.fundgroup.backend.daoImpl;

import com.fundgroup.backend.dao.RegisterDao;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository
public class RegisterDaoImpl implements RegisterDao {

  @Autowired
  private RedisTemplate<String, String> redisTemplate;


  //此处直接注入会出现\xac\xed\x00\x05t\x00\x06的后缀问题，因此需要序列化
  @Autowired(required = false)
  void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
    RedisSerializer<String> stringSerializer = new StringRedisSerializer();//序列化为String
    Jackson2JsonRedisSerializer<String> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<String>(String.class);//序列化为Json
    redisTemplate.setKeySerializer(stringSerializer);
    redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
    redisTemplate.setHashKeySerializer(stringSerializer);
    redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

    this.redisTemplate=redisTemplate;

    //清空数据库
    //    Set<String> keys=redisTemplate.keys("*");
    //    redisTemplate.delete(keys);
    //    this.redisTemplate = redisTemplate;
  }

  //若redis使用不成功，需要写工具类
  @Override
  public boolean checkAuth(String deviceId, String auth) {
    if (Boolean.FALSE.equals(redisTemplate.hasKey(deviceId))) {
      return false;
    }

    String realAuth = (String) redisTemplate.opsForValue().get(deviceId);
    System.out.println("realAuth=" + realAuth);
    return Objects.equals(realAuth, auth);
  }

  //设置验证码，默认为十分钟过期
  @Override
  public void setAuth(String deviceId, String auth) {
    redisTemplate.opsForValue().set(deviceId, auth);
    redisTemplate.expire(deviceId, 10, TimeUnit.MINUTES);
  }
}
