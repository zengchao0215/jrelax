package com.jrelax.orm.transform;

import com.jrelax.orm.util.HqlSqlParser;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询转换
 * Created by zengchao on 2016/8/14.
 */
public class AliasToEntityKeyMapResultTransformer extends AliasedTupleSubsetResultTransformer {
    private String[] columns = null;
    public AliasToEntityKeyMapResultTransformer(String hql){
        this.columns = new HqlSqlParser(hql).getSelectColumns();
    }
    @Override
    public boolean isTransformedValueATupleElement(String[] strings, int tupleLength) {
        return false;
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Map<String, Object> result = new HashMap<>();
        if(columns.length == aliases.length){
            for(int i = 0; i < columns.length; ++i) {
                String alias = columns[i];
                if(alias != null) {
                    result.put(alias, tuple[i]);
                }
            }
        }else{
            for(int i = 0; i < tuple.length; ++i) {
                String alias = aliases[i];
                if(alias != null) {
                    result.put(alias, tuple[i]);
                }
            }
        }
        return result;
    }
}
