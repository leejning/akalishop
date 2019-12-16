package com.data.bean;



import lombok.Data;

import javax.persistence.*;
import java.beans.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * @param
 * @return
 */
@Data
@Table(name="pms_base_catalog1")
@Entity
public class PmsBaseCatalog1 implements Serializable {
    @Id
    private Integer id;
    private String name;
}

