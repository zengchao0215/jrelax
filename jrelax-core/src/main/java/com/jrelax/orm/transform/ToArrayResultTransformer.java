package com.jrelax.orm.transform;

import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

/**
 * Created by Administrator on 2016/8/18.
 */
public class ToArrayResultTransformer extends AliasedTupleSubsetResultTransformer {
    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Object[] array = new Object[aliases.length];
        for(int i = 0; i < tuple.length; ++i) {
            String alias = aliases[i];
            if(alias != null) {
                array[i] = tuple[i];
            }
        }
        return array;
    }

    @Override
    public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
        return false;
    }
}
