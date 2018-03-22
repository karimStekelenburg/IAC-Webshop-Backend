package com.ElegantDevelopment.iacWebshop.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(name = "order_table")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="address_fk",referencedColumnName="id", insertable = false, updatable = false)
    private Address deliveryAddress;

    @ManyToOne(optional=false)
    @JoinColumn(name="account_fk", referencedColumnName="id")
    private Account account;

    @OneToMany(mappedBy="order",targetEntity=OrderLine.class,
            fetch=FetchType.EAGER)
    private Collection<OrderLine> orderLines;

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

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public double getAmount(){
        double amount = 0;
        for (OrderLine ol : orderLines){
            amount += ol.getPrice();
        }

        amount = amount*100;
        amount = Math.round(amount);
        amount= amount /100;

        return amount;
    }


}


