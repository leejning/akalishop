package com.akali.provider.es.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName Product
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
@Document(indexName = "products", type = "docs", shards = 1, replicas = 0)
@Data
public class Product {
    /**
     * spuId
     */
    @Id
    private Long id;
    /**
     * 所有需要被搜索的信息，包含标题，分类，甚至品牌
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String all;
    private String title;
    /**
     * 卖点
     */
    private String subTitle;
    private Long brandId;
    private Long cid1;
    private Long cid2;
    private Long cid3;
    /**
     * 分类全名
     */
    private String categoryFullName;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Field(type = FieldType.Keyword, index = false)
    private String skus;

    /**
     * 属性集合
     * { attrName: attrValue }
     * sku属性 Map<String,List<String>>
     * spu属性 Map<String,String>
     */
    private Map<String, Object> attrs;


}
