package com.akali.common.data_help;

import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * @ClassName PageAndSortObj
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/9/30 0030
 * @Version V1.0
 **/
@Data
public class PageAndSortObj {

    private Sort sort;
    private Pageable pageable;
}
