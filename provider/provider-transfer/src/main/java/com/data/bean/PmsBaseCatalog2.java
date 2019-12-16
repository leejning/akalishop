package com.data.bean;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @param
 * @return
 */
@Data
@Table(name="pms_base_catalog2")
@Entity
public class PmsBaseCatalog2 implements Serializable {
    @Id
    @Column
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer catalog1_id;
}
