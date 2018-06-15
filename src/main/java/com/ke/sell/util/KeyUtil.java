package com.ke.sell.util;

import java.util.UUID;

/**
 * @Author: Mo
 * @Date: Created in  2018/6/15 13:11
 */
public class KeyUtil {
    /**
     * 生成唯一的主键
     */
    public synchronized static String genUniqueKey() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return (System.currentTimeMillis() + uuid).substring(0, 32);
    }


}
