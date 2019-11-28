package com.akali.provider.cart.service;


import com.akali.common.code.CartCode;
import com.akali.common.code.CommonCode;
import com.akali.common.data_help.DataJpaPageUtils;
import com.akali.common.data_help.PageAndSortObj;
import com.akali.common.dto.cart.CartAddDTO;
import com.akali.common.dto.cart.CartItemDTO;
import com.akali.common.dto.cart.CartQueryPageDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.akali.provider.cart.bean.OmsCartItem;
import com.akali.provider.cart.bean.OmsCartRecord;
import com.akali.provider.cart.dao.CartItemDao;
import com.akali.provider.cart.dao.CartRecordDao;
import com.akali.provider.cart.query_helper.CartItemQueryHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName CartItemServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/25 0025
 * @Version V1.0
 **/
@Service(version = "1.0.0")
@Slf4j
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemDao cartItemDao;
    @Autowired
    private CartRecordDao cartRecordDao;

    /**
     * 添加商品到购物车
     *
     * @param cartAddDTO
     * @return
     */
    @Override
    public DubboResponse<Void> addProductToCart(CartAddDTO cartAddDTO) {
        Long skuId = cartAddDTO.getSkuId();

        Optional<OmsCartRecord> opt = cartRecordDao.findByMemberId(cartAddDTO.getMemberId());
        OmsCartRecord omsCartRecord = null;
        //判断有没有购物车记录表的数据
        if (opt.isPresent()) {
            omsCartRecord = opt.get();
        } else {
            omsCartRecord = new OmsCartRecord();
            omsCartRecord.setMemberId(cartAddDTO.getMemberId());
        }
        if (omsCartRecord.getCartItemIdRecord() == null) {
            omsCartRecord.setCartItemIdRecord(new HashMap<Long, String>());
            omsCartRecord.setCountRecord(new HashMap<Long, Integer>());
        }
        Map<Long, Integer> countRecord = omsCartRecord.getCountRecord();

        //判断会员购物车记录里面有没有该商品
        if (countRecord.containsKey(skuId)) {
            //存在 更新数量
            Integer oldCount = countRecord.get(skuId);
            Integer newCount = updateCount(countRecord.get(skuId), cartAddDTO.getCount());
            countRecord.put(skuId, newCount);

        } else {
            //新添加
            OmsCartItem omsCartItem = new OmsCartItem();
            BeanUtils.copyProperties(cartAddDTO, omsCartItem);
            cartItemDao.save(omsCartItem);
            omsCartRecord.getCartItemIdRecord().put(skuId, omsCartItem.getId());

            Integer newCount = updateCount(0, cartAddDTO.getCount());
            countRecord.put(skuId, newCount);

        }
        cartRecordDao.save(omsCartRecord);

        log.info(">>>>>>会员号：{}，用户添加商品：{}到购物车成功",
                cartAddDTO.getMemberId(), cartAddDTO.getSpuId() + "-" + skuId);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 修改购物车内商品id为skuId 的数量
     *
     * @param skuId
     * @param count
     * @return
     */
    @Override
    public DubboResponse<Void> modifyCartItemCount(Long memberId, Long skuId, Integer count) {
        Optional<OmsCartRecord> opt = cartRecordDao.findByMemberId(memberId);
        if (!opt.isPresent()) {
            DubboResponse.FAIL(CartCode.SKU_NOT_IN_CART);
        }
        OmsCartRecord omsCartRecord = opt.get();
        Map<Long, Integer> countRecord = omsCartRecord.getCountRecord();
        Integer newCount = updateCount(countRecord.get(skuId), count);
        countRecord.put(skuId, newCount);
        cartRecordDao.save(omsCartRecord);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 获取购物车
     *
     * @param queryPageDTO
     * @return
     */
    @Override
    public DubboResponse<QueryResult<CartItemDTO>> getCart(CartQueryPageDTO queryPageDTO) {
        PageAndSortObj pageAndSortObj = DataJpaPageUtils.initPageAndSort(queryPageDTO);

        CartItemQueryHelper queryHelper = CartItemQueryHelper.builder().memberId(queryPageDTO.getMemberId()).build();

        Query query = queryHelper.getWhere(queryHelper, queryHelper.resultList(CartItemDTO.class));

        Page<OmsCartItem> page = cartItemDao.findAll(query, pageAndSortObj.getPageable(), OmsCartItem.class);

        List<CartItemDTO> data = page.stream().map(CartItemDTO::new).collect(Collectors.toList());

        QueryResult<CartItemDTO> result = QueryResult.create(data, page.getTotalElements());

        return DubboResponse.SUCCESS(result);
    }


    private Integer updateCount(Integer oldCount, Integer count) {
        if (count > 0) {
            oldCount = oldCount + count > 99 ? 99 : oldCount + count;
        } else {
            oldCount = oldCount + count < 1 ? 1 : oldCount + count;
        }
        return oldCount;
    }


}
