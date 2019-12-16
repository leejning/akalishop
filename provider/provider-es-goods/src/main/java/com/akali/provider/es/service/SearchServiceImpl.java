package com.akali.provider.es.service;

import com.akali.common.code.CommonCode;
import com.akali.common.code.SearchCode;
import com.akali.common.data_help.DataJpaPageUtils;
import com.akali.common.dto.goods.base.attribute.AttributionSimpleVO;
import com.akali.common.dto.goods.base.brand.BrandSimpleVO;
import com.akali.common.dto.goods.base.category.CategorySimpleVO;
import com.akali.common.dto.goods.sku.SkuEsVO;
import com.akali.common.dto.search.ProductVO;
import com.akali.common.dto.search.SearchDTO;
import com.akali.common.dto.search.SearchQueryResult;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.akali.common.utils.MapperUtils;
import com.akali.provider.es.bean.Product;
import com.akali.provider.es.dao.ProductDao;
import com.akali.provider.goods.api.AttributionService;
import com.akali.provider.goods.api.BrandService;
import com.akali.provider.goods.api.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName SearchServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/24 0024
 * @Version V1.0
 **/
@Service(version = "1.0.0")
public class SearchServiceImpl implements SearchService {
    @Autowired
    private ElasticsearchTemplate template;
    @Autowired
    private ProductDao productDao;
    @Reference(version = "1.0.0")
    private CategoryService categoryService;
    @Reference(version = "1.0.0")
    private BrandService brandService;

    @Reference(version = "1.0.0")
    private AttributionService attributionService;


    private final static String CATEGORY = "cid3";
    private final static String BRAND = "brandId";
    private final static String CATEGORY_AGG = "category_agg";
    private final static String BRAND_AGG = "brand_agg";
    private final static Integer PAGE_SIZE = 10;

    @Override
    public DubboResponse<ProductVO> queryProductById(Long spuId) {
        Optional<Product> opt = productDao.findById(spuId);
        if (!opt.isPresent()) {
            DubboResponse.FAIL(CommonCode.FAIL);
        }
        ProductVO productVO = new ProductVO();
        Product product = opt.get();
        BeanUtils.copyProperties(product, productVO);
        try {
            List<SkuEsVO> skuEsVOS = MapperUtils.json2list(product.getSkus(), SkuEsVO.class);
            productVO.setSkus(skuEsVOS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DubboResponse.SUCCESS(productVO);
    }

    /**
     * 页面商品搜索
     *
     * @param searchDTO
     * @return
     */
    @Override
    public DubboResponse<SearchQueryResult> searchProduct(SearchDTO searchDTO) {
        DataJpaPageUtils.initPageAndSort(searchDTO);
        searchDTO.setPageSize(PAGE_SIZE);

        String key = searchDTO.getKey();
        // 判断是否有搜索条件，如果没有，直接返回null。不允许搜索全部商品
        if (StringUtils.isBlank(key)) {
            return DubboResponse.FAIL(SearchCode.KEY_CANT_BE_NULL);
        }
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        QueryBuilder basicQuery = buildBasicQueryWithFilter(searchDTO);
        queryBuilder.withQuery(basicQuery);
        //结果字段选择
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "title", "subTitle", "skus"}, null));
        //分页排序
        setPageAndSort(queryBuilder, searchDTO);
        // 对商品分类和品牌进行聚合
        queryBuilder.addAggregation(AggregationBuilders.terms(CATEGORY_AGG).field(CATEGORY));
        queryBuilder.addAggregation(AggregationBuilders.terms(BRAND_AGG).field(BRAND));

        //执行查询
        AggregatedPage<Product> pageInfo = (AggregatedPage<Product>) productDao.search(queryBuilder.build());

        /**
         * 解析查询结果
         */

        // 解析分类和品牌的聚合结果
        List<Long> categoryIds = parsedAggResult(pageInfo.getAggregation(CATEGORY_AGG));
        List<Long> brandIds = parsedAggResult(pageInfo.getAggregation(BRAND_AGG));
        // 查询分类和品牌
        List<CategorySimpleVO> categories = categoryService.queryCategorySimpleByIds(categoryIds);
        List<BrandSimpleVO> brands = brandService.queryBrandSimpleByIds(brandIds);

        /**
         * 根据分类聚合结果进行商品属性值的聚合查询。如 cpu型号，内存大小 等
         * key是属性名 value是属性值得数组
         */
        List<Map<String, List<String>>> attributeMap = buildAttrKeyValueMap(categoryIds, basicQuery);

        /**
         * 包装结果集
         */
        List<ProductVO> products = pageInfo.getContent().stream()
                .map(p -> new ProductVO(p,p.getSkus())).collect(Collectors.toList());
        SearchQueryResult result = new SearchQueryResult();
        QueryResult<ProductVO> productQueryResult = QueryResult.create(products,pageInfo.getTotalPages(), pageInfo.getTotalElements());
        productQueryResult.setSortby(searchDTO.getSortby());
        productQueryResult.setDescOrAsc(searchDTO.getDescOrAsc());
        productQueryResult.setPageNo(searchDTO.getPageNo());
        productQueryResult.setPageSize(searchDTO.getPageSize());
        result.setQueryResult(productQueryResult);

        result.setFilter(searchDTO.getFilter());
        result.setCategories(categories);
        result.setBrands(brands);
        result.setAttribute(attributeMap);
        return DubboResponse.SUCCESS(result);
    }

    /**
     * 根据分类构建属性的聚合查询，根据聚合结果查询
     *
     * @param categoryIds
     * @param basicQuery
     * @return
     */
    private List<Map<String, List<String>>> buildAttrKeyValueMap(List<Long> categoryIds, QueryBuilder basicQuery) {
        DubboResponse<List<AttributionSimpleVO>> response = attributionService.querySearchingAttrByCateIds(categoryIds);
        if (!response.isSuccess()) {

        }
        List<AttributionSimpleVO> attrs = response.getData();

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(basicQuery);

        // 聚合规格参数
        attrs.forEach(param ->
                queryBuilder.addAggregation(AggregationBuilders.terms(param.getName())
                        .field("attrs." + param.getName() + ".keyword")));

        AggregatedPage<Product> result = template.queryForPage(queryBuilder.build(), Product.class);

        // 解析聚合结果
        List<Map<String, List<String>>> attrKeyValueList = new ArrayList<>();
        Aggregations aggs = result.getAggregations();
        attrs.forEach(param -> {
            String name = param.getName();
            try {
                StringTerms terms = (StringTerms) aggs.get(name);
                List<String> options = terms.getBuckets().stream().map(StringTerms.Bucket::getKeyAsString)
                        .collect(Collectors.toList());

                Map<String, List<String>> attrKeyValue = new HashMap<>();
                attrKeyValue.put(name, options);
                attrKeyValueList.add(attrKeyValue);
            }catch (Exception e){
                Aggregation aggregation = aggs.get(name);
                System.out.println();
            }
        });


        return attrKeyValueList;
    }

    /**
     * 解析聚合结果。分类的id 或者 品牌的id
     *
     * @param aggregation
     * @return
     */
    private List<Long> parsedAggResult(Aggregation aggregation) {
        LongTerms aggTerms = (LongTerms) aggregation;
        List<Long> ids = aggTerms.getBuckets().stream().map(LongTerms.Bucket::getKeyAsNumber)
                .map(Number::longValue).collect(Collectors.toList());
        return ids;
    }

    /**
     * 构建基础查询条件
     *
     * @param searchDTO
     * @return
     */
    private QueryBuilder buildBasicQueryWithFilter(SearchDTO searchDTO) {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        // 基本查询条件
        queryBuilder.must(QueryBuilders.matchQuery("all", searchDTO.getKey()).operator(Operator.AND));
        // 过滤条件构建器
        BoolQueryBuilder filterQueryBuilder = QueryBuilders.boolQuery();
        // 整理过滤条件
        Map<String, String> filter = searchDTO.getFilter();
        for (Map.Entry<String, String> entry : filter.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // 商品分类和品牌要特殊处理
            if (!CATEGORY.equals(key) && !BRAND.equals(key)) {
                key = "attrs." + key + ".keyword";
            }
            filterQueryBuilder.must(QueryBuilders.termQuery(key, value));
        }
        // 添加过滤条件
        queryBuilder.filter(filterQueryBuilder);
        return queryBuilder;
    }

    /**
     * 设置分页和排序
     *
     * @param queryBuilder
     * @param searchDTO
     */
    private void setPageAndSort(NativeSearchQueryBuilder queryBuilder, SearchDTO searchDTO) {
        // 准备分页参数
        Integer page = searchDTO.getPageNo();
        Integer size = searchDTO.getPageSize();

        // 1、分页
        queryBuilder.withPageable(PageRequest.of(page - 1, size));
        // 2、排序
        String sortBy = searchDTO.getSortby();
        Boolean desc = "desc".equals(searchDTO.getDescOrAsc()) ? true : false;
        if (StringUtils.isNotBlank(sortBy)) {
            // 如果不为空，则进行排序
            queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(desc ? SortOrder.DESC : SortOrder.ASC));
        }
    }
}
