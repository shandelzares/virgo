package com.virgo.common;

import java.util.Random;

public class IdGenerator {

    private static final SnowflakeIdWorker MemberIdWorker = new SnowflakeIdWorker(new Random().nextInt(30));//todo id 冲突

    public static Long nextMemberId() {
        return MemberIdWorker.nextId();
    }
}
