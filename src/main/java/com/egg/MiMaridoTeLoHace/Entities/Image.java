package com.egg.MiMaridoTeLoHace.Entities;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Data;

@Data
@Entity
public class Image {
    
    @Id
    @GeneratedValue (generator = "uuid")
    @GenericGenerator (name = "uuid", strategy = "uuid2")
    private String id;

    private String mime;
    private String name;

    @Lob
    @Basic (fetch = FetchType.LAZY)
    private byte[] content;
    
}
