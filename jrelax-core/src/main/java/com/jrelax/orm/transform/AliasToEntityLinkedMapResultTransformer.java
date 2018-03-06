package com.jrelax.orm.transform;

import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by zengc on 2016-05-06.
 * 按照sql语句字段的顺序有序排列
 * 原Hibernate提供的Transformers.ALIAS_TO_ENTITY_MAP为无序
 */
public class AliasToEntityLinkedMapResultTransformer extends AliasedTupleSubsetResultTransformer {
    public static final AliasToEntityLinkedMapResultTransformer INSTANCE = new AliasToEntityLinkedMapResultTransformer();

    private AliasToEntityLinkedMapResultTransformer() {
    }

    public Object transformTuple(Object[] tuple, String[] aliases) {
        HashMap result = new LinkedHashMap(tuple.length);

        for(int i = 0; i < tuple.length; ++i) {
            String alias = aliases[i];
            if(alias != null) {
                result.put(alias, tuple[i]);
            }
        }

        return result;
    }

    public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
        return false;
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
