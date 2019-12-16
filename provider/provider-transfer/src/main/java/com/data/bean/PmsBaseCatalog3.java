package com.data.bean;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @param
 * @return
 */
@Data
@Table(name="pms_base_catalog3")
@Entity
public class PmsBaseCatalog3 implements Serializable {
    @Id
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private Integer catalog2_id;
}
