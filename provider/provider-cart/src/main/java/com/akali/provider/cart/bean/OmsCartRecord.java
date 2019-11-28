package com.akali.provider.cart.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * @ClassName OmsCartRecord
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/27 0027
 * @Version V1.0
 **/
@Data
@ToString
@Document(collection = "oms_cart_record")
public class OmsCartRecord {
    @Id
    private String id;
    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 是否为空
     */
    private Boolean empty;
    /**
     * key是 skuId ，
     * value 是 CartItem的id
     */
    Map<Long, String> cartItemIdRecord;
    /**
     * key是 skuId ，
     * value 是数量
     */
    Map<Long, Integer> countRecord;

}
