package com.example.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import org.elasticsearch.common.unit.Fuzziness;


import java.util.List;
import java.util.function.Supplier;

/**
 * @author Osada
 * @Date
 */
public class Operations {
    public static MultiMatchQuery multiMatchQuery(String key, List<String> fields) {
        MultiMatchQuery.Builder builder = new MultiMatchQuery.Builder();
        builder.fuzziness(Fuzziness.AUTO.asString());
        return  builder.query(key).fields(fields).build();
    }

    public static Supplier<Query> searchQuery (String key, List<String> fields){
         Supplier<Query> supplier = () -> Query.of(q->q.multiMatch(multiMatchQuery(key,fields)));
        return supplier;
    }
}
