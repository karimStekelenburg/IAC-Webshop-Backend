package com.ElegantDevelopment.iacWebshop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Product")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private double price;

    @NotBlank
    private int quantity;

    private byte[] image;

    @ManyToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name="product_category",
            joinColumns=
            @JoinColumn(name="product_fk", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="category_fk", referencedColumnName="id")
    )
    private List<Category> categories;

    @OneToMany(mappedBy="product",targetEntity=OrderLine.class,
            fetch=FetchType.EAGER)
    private Collection orderLine;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy="product",targetEntity=Sale.class)
    private Collection sale;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;
}
