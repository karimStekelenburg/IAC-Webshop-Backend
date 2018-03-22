package com.ElegantDevelopment.iacWebshop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "product_table")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private double price;

    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="product_category",
            joinColumns=
            @JoinColumn(name="product_fk", referencedColumnName="id"),
            inverseJoinColumns=
            @JoinColumn(name="category_fk", referencedColumnName="id")
    )
    @JsonIgnoreProperties("products")
    private List<Category> categories;

    @OneToMany(mappedBy="product",targetEntity=OrderLine.class, fetch=FetchType.LAZY)
    private Collection orderLine;

    @OneToMany(mappedBy="product",targetEntity=Sale.class, fetch = FetchType.LAZY)
    private Collection sale;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Collection getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(Collection orderLine) {
        this.orderLine = orderLine;
    }

    public Collection getSale() {
        return sale;
    }

    public void setSale(Collection sale) {
        this.sale = sale;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", image=" + Arrays.toString(image) +
                ", categories=" + categories +
                ", orderLine=" + orderLine +
                ", sale=" + sale +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
