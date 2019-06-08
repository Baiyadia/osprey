package com.kaiqi.osprey.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author newex-team
 * @data 30/03/2018
 */

@Configuration
@Slf4j
public class REDIS {

    private static final int TIMES = 3;
    @Autowired
    private StringRedisTemplate template;

    public String get(String key) {
        return template.opsForValue().get(key);
    }

//
//    public static String get(String key) {
//        log.info("get begin: {}", key);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] resultBytes = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    resultBytes = connection.get(keyBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("get error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        String result = null;
//        if (resultBytes != null) {
//            result = SafeEncoder.encode(resultBytes);
//        }
//        log.info("get end: {}", result);
//        return result;
//    }
//
//    public static Integer getInt(String key) {
//        log.info("getInt begin: {}", key);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] resultBytes = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    resultBytes = connection.get(keyBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("getInt error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        Integer result = null;
//        if (resultBytes != null) {
//            String resultStr = SafeEncoder.encode(resultBytes);
//            result = Integer.valueOf(resultStr);
//        }
//        log.info("getInt end: {}", result);
//        return result;
//    }
//
//    public static Boolean exists(String key) {
//        log.info("exists begin: {}", key);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        Boolean result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.exists(keyBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("exists error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("exists end: {}", result);
//        return result;
//    }
//
//    public static Long incr(String key) {
//        log.info("incr begin: {}", key);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        Long result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.incr(keyBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("incr error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("incr end: {}", result);
//        return result;
//    }
//
//    public static Long incrBy(String key, long increment) {
//        log.info("incrby begin: {} {}", key, increment);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        Long result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.incrBy(keyBytes, increment);
//                    break;
//                } catch (Throwable e) {
//                    log.error("incr error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("incrby end: {}", result);
//        return result;
//    }
//
//    public static void set(String key, String value) {
//        log.info("set begin: {}, {}", key, value);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] valueBytes = SafeEncoder.encode(value);
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    connection.set(keyBytes, valueBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("set error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("set end");
//    }
//
//    public static void set(String key, int value) {
//        log.info("set begin: {}, {}", key, value);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] valueBytes = SafeEncoder.encode(String.valueOf(value));
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    connection.set(keyBytes, valueBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("set error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("set end");
//    }
//
//    public static void setEx(String key, long time, int value) {
//        log.info("setEx begin: {}, {}, {}", key, time, value);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] valueBytes = SafeEncoder.encode(String.valueOf(value));
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    connection.setEx(keyBytes, time, valueBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("setEx error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("setEx end");
//    }
//
//    public static void setEx(String key, long time, String value) {
//        log.info("setEx begin: {}, {}, {}", key, time, value);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] valueBytes = SafeEncoder.encode(value);
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    connection.setEx(keyBytes, time, valueBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("setEx error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("setEx end");
//    }
//
//    public static boolean setNX(String key, String value) {
//        log.info("setNX begin: {}, {}", key, value);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] valueBytes = SafeEncoder.encode(value);
//        Boolean result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.setNX(keyBytes, valueBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("setNX error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("setNX end: {}", result);
//        return result == null ? false : result;
//    }
//
//    public static Integer getSet(String key, int value) {
//        log.info("getSet begin: {}, {}", key, value);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] valueBytes = SafeEncoder.encode(String.valueOf(value));
//        byte[] resultBytes = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    resultBytes = connection.getSet(keyBytes, valueBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("getSet error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        Integer result = null;
//        if (resultBytes != null) {
//            String resultStr = SafeEncoder.encode(resultBytes);
//            result = Integer.valueOf(resultStr);
//        }
//        log.info("getSet end: {}", result);
//        return result;
//    }
//
//    public static String getSet(String key, String value) {
//        log.info("getSet begin: {}, {}", key, value);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] valueBytes = SafeEncoder.encode(value);
//        byte[] resultBytes = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    resultBytes = connection.getSet(keyBytes, valueBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("getSet error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        String result = null;
//        if (resultBytes != null) {
//            result = SafeEncoder.encode(resultBytes);
//        }
//        log.info("getSet end: {}", result);
//        return result;
//    }
//
//    public static boolean expire(String key, long seconds) {
//        log.info("expire begin: {}, {}", key, seconds);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        Boolean result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.expire(keyBytes, seconds);
//                    break;
//                } catch (Throwable e) {
//                    log.error("expire error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("expire end: {}", result);
//        return result == null ? false : result;
//    }
//
//    /**
//     * 当 key 不存在时，返回 -2
//     * <p/>
//     * 当 key 存在但没有设置剩余生存时间时，返回 -1
//     */
//    public static Long ttl(String key) {
//        log.info("ttl begin: {}", key);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        Long result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.ttl(keyBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("ttl error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("ttl end: {}", result);
//        return result;
//    }
//
//    public static Long del(String key) {
//        log.info("del begin: {}", key);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        Long result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.del(keyBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("del error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("del end: {}", result);
//        return result;
//    }
//
//    public static Long lPush(String key, String value) {
//        log.info("lPush begin: {}, {}", key, value);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] valueBytes = SafeEncoder.encode(value);
//        Long result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.lPush(keyBytes, valueBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("lPush error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("lPush end: {}", result);
//        return result;
//    }
//
//    public static String lPop(String key) {
//        log.info("lPop begin: {}", key);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] resultBytes = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    resultBytes = connection.lPop(keyBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("lPop error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        String result = null;
//        if (resultBytes != null) {
//            result = SafeEncoder.encode(resultBytes);
//        }
//        log.info("lPop end: {}", result);
//        return result;
//    }
//
//    public static String rPop(String key) {
//        log.info("rPop begin: {}", key);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] resultBytes = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    resultBytes = connection.rPop(keyBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("rPop error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        String result = null;
//        if (resultBytes != null) {
//            result = SafeEncoder.encode(resultBytes);
//        }
//        log.info("rPop end: {}", result);
//        return result;
//    }
//
//    public static String rPoplPush(String src, String dst) {
//        log.info("rPoplPush begin, src: {},dst:{}", src, dst);
//
//        byte[] srcBytes = SafeEncoder.encode(src);
//        byte[] dstBytes = SafeEncoder.encode(dst);
//        byte[] resultBytes = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    resultBytes = connection.rPopLPush(srcBytes, dstBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("rPoplPush error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        String result = null;
//        if (resultBytes != null) {
//            result = SafeEncoder.encode(resultBytes);
//        }
//        log.info("rPoplPush end: {}", result);
//        return result;
//    }
//
//    public static List<String> lRange(String key, long start, long end) {
//        log.info("lRange begin: {}", key);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        List<byte[]> resultBytes = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    resultBytes = connection.lRange(keyBytes, start, end);
//                    break;
//                } catch (Throwable e) {
//                    log.error("lRange error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        List<String> result = new LinkedList<>();
//        for (byte[] bytes : resultBytes) {
//            String s = SafeEncoder.encode(bytes);
//            result.add(s);
//        }
//        log.info("lRange end: {}", result.size());
//        return result;
//    }
//
//    public static Long rPush(String key, String value) {
//        log.info("rPush begin: {}, {}", key, value);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] valueBytes = SafeEncoder.encode(value);
//        Long result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.rPush(keyBytes, valueBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("rPush error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("rPush end: {}", result);
//        return result;
//    }
//
//    public static Long lRem(String key, long countParams, String value) {
//        log.info("lRem begin: {}, {}, {}", key, countParams, value);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] valueBytes = SafeEncoder.encode(value);
//        Long result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.lRem(keyBytes, countParams, valueBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("lRem error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("lRem end: {}", result);
//        return result;
//    }
//
//    public static Long lLen(String key) {
//        log.info("lLen begin: {}", key);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        Long result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.lLen(keyBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("lLen error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("lLen end: {}", result);
//        return result;
//    }
//
//    public static void lTrim(String key, long start, long end) {
//        log.info("lTrim begin: {} {} {}", key, start, end);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    connection.lTrim(keyBytes, start, end);
//                    break;
//                } catch (Throwable e) {
//                    log.error("lTrim error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("lTrim end");
//    }
//
//    public static Long sAdd(String key, String value) {
//        log.info("sAdd begin: {}, {}", key, value);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] valueBytes = SafeEncoder.encode(value);
//        Long result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.sAdd(keyBytes, valueBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("sAdd error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("sAdd end: {}", result);
//        return result;
//    }
//
//    public static Long sRem(String key, String value) {
//        log.info("sRem begin: {}, {}", key, value);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] valueBytes = SafeEncoder.encode(value);
//        Long result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.sRem(keyBytes, valueBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("sRem error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("sRem end: {}", result);
//        return result;
//    }
//
//    public static Set<String> sMembers(String key) {
//        log.info("sMembers begin: {}", key);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        Set<byte[]> resultBytes = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    resultBytes = connection.sMembers(keyBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("sMembers error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        Set<String> result = null;
//        if (resultBytes != null) {
//            result = new HashSet<>();
//            for (byte[] r : resultBytes) {
//                if (r != null) {
//                    String rStr = SafeEncoder.encode(r);
//                    result.add(rStr);
//                }
//            }
//        }
//        log.info("sMembers end: {}", result == null ? null : result.size());
//        return result;
//    }
//
//    public static boolean sIsMember(String key, String value) {
//        log.info("sIsMember begin: {}, {}", key, value);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] valueBytes = SafeEncoder.encode(value);
//        Boolean result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.sIsMember(keyBytes, valueBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("sIsMember error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("sIsMember end: {}", result);
//        return result == null ? false : (boolean) result;
//    }
//
//    public static boolean zAdd(String key, double score, String value) {
//        log.info("zAdd begin: {}, {}, {}", key, score, value);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] valueBytes = SafeEncoder.encode(value);
//        Boolean result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.zAdd(keyBytes, score, valueBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("zAdd error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("zAdd end: {}", result);
//        return result == null ? false : (boolean) result;
//    }
//
//    public static Long zRem(String key, String value) {
//        log.info("zRem begin: {}, {}", key, value);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] valueBytes = SafeEncoder.encode(value);
//        Long result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.zRem(keyBytes, valueBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("zRem error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("zRem end: {}", result);
//        return result;
//    }
//
//    public static Long zRemRangeByScore(String key, double min, double max) {
//        log.info("zRemRangeByScore begin: {}, {}, {}", key, min, max);
//        Long result = zRemRangeByScore(key, new RedisZSetCommands.Range().gte(min).lte(max));
//        log.info("zRemRangeByScore end");
//        return result;
//    }
//
//    public static Long zRemRangeByScore(String key, RedisZSetCommands.Range range) {
//        log.info("zRemRangeByScore begin: {}, {}", key, JSON.toJSON(range));
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        Long result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.zRemRangeByScore(keyBytes, range);
//                    break;
//                } catch (Throwable e) {
//                    log.error("zRemRangeByScore error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("zRemRangeByScore end: {}", result);
//        return result == null ? 0 : (long) result;
//    }
//
//    public static Set<String> zRange(String key, long start, long end) {
//        log.info("zRange begin: {}, {}, {}", key, start, end);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        Set<byte[]> resultBytes = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    resultBytes = connection.zRange(keyBytes, start, end);
//                    break;
//                } catch (Throwable e) {
//                    log.error("zRange error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        Set<String> result = null;
//        if (resultBytes != null) {
//            result = new HashSet<>();
//            for (byte[] r : resultBytes) {
//                if (r != null) {
//                    String rStr = SafeEncoder.encode(r);
//                    result.add(rStr);
//                }
//            }
//        }
//        log.info("zRange end: {}", result == null ? null : result.size());
//        return result;
//    }
//
//    public static Set<String> zRangeByScore(String key, double min, double max) {
//        log.info("zRangeByScore begin: {}, {}, {}", key, min, max);
//        Set<String> result = zRangeByScore(key, new RedisZSetCommands.Range().gte(min).lte(max));
//        log.info("zRangeByScore end");
//        return result;
//    }
//
//    public static Set<String> zRangeByScore(String key, RedisZSetCommands.Range range) {
//        log.info("zRangeByScore begin: {}, {}", key, JSON.toJSON(range));
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        Set<byte[]> resultBytes = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    resultBytes = connection.zRangeByScore(keyBytes, range);
//                    break;
//                } catch (Throwable e) {
//                    log.error("zRangeByScore error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        Set<String> result = null;
//        if (resultBytes != null) {
//            result = new HashSet<>();
//            for (byte[] r : resultBytes) {
//                if (r != null) {
//                    String rStr = SafeEncoder.encode(r);
//                    result.add(rStr);
//                }
//            }
//        }
//        log.info("zRangeByScore end: {}", result == null ? null : result.size());
//        return result;
//    }
//
//    public static String hGet(String key, String value) {
//        log.info("hGet begin: {}, {}", key, value);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] valueBytes = SafeEncoder.encode(value);
//        byte[] resultBytes = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    resultBytes = connection.hGet(keyBytes, valueBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("hGet error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        String result = null;
//        if (resultBytes != null) {
//            result = SafeEncoder.encode(resultBytes);
//        }
//        log.info("hGet end: {}", result);
//        return result;
//    }
//
//    public static long hLen(String key) {
//        log.info("hLen begin: {}", key);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        Long result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.hLen(keyBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("hLen error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("hLen end: {}", result);
//        return result == null ? 0 : (long) result;
//    }
//
//    public static Map<String, String> hGetAll(String key) {
//        log.info("hGetAll begin: {}", key);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        Map<String, String> result = null;
//        Map<byte[], byte[]> resultBytes = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    resultBytes = connection.hGetAll(keyBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("hGetAll error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        if (resultBytes != null) {
//            result = new HashMap<>();
//            for (Map.Entry<byte[], byte[]> entry : resultBytes.entrySet()) {
//                byte[] k = entry.getKey();
//                String kStr = null;
//                if (k != null) {
//                    kStr = SafeEncoder.encode(k);
//                }
//                byte[] v = entry.getValue();
//                String vStr = null;
//                if (v != null) {
//                    vStr = SafeEncoder.encode(v);
//                }
//                result.put(kStr, vStr);
//            }
//        }
//        log.info("hGetAll end: {}", result == null ? null : result.size());
//        return result;
//    }
//
//    public static boolean hSet(String key, String field, String value) {
//        log.info("hSet begin: {}, {}, {}", key, field, value);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] fieldBytes = SafeEncoder.encode(field);
//        byte[] valueBytes = SafeEncoder.encode(value);
//        Boolean result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.hSet(keyBytes, fieldBytes, valueBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("hSet error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("hSet end: {}", result);
//        return result == null ? false : (boolean) result;
//    }
//
//    public static long hDel(String key, String field) {
//        log.info("hDel begin: {}, {}", key, field);
//
//        byte[] keyBytes = SafeEncoder.encode(key);
//        byte[] fieldBytes = SafeEncoder.encode(field);
//        Long result = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    result = connection.hDel(keyBytes, fieldBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("hDel error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        log.info("hDel end: {}", result);
//        return result == null ? 0 : (long) result;
//    }
//
//    public static Set<String> keys(String pattern) {
//        log.info("keys begin: {}", pattern);
//
//        byte[] patternBytes = SafeEncoder.encode(pattern);
//        Set<byte[]> resultBytes = null;
//        RedisConnection connection = factory.getConnection();
//        try {
//            int count = 0;
//            while (true) {
//                count++;
//                try {
//                    resultBytes = connection.keys(patternBytes);
//                    break;
//                } catch (Throwable e) {
//                    log.error("keys error: ", e);
//                    if (count > TIMES) {
//                        throw new JedisException(e);
//                    }
//                }
//            }
//        } finally {
//            connection.close();
//        }
//        Set<String> result = null;
//        if (resultBytes != null) {
//            result = new HashSet<>();
//            for (byte[] r : resultBytes) {
//                if (r != null) {
//                    String rStr = SafeEncoder.encode(r);
//                    result.add(rStr);
//                }
//            }
//        }
//        log.info("keys end: {}", result == null ? "null" : result.size());
//        return result;
//    }

}
