package com.ElegantDevelopment.iacWebshop.model;


        import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
        import org.hibernate.mapping.Collection;
        import org.springframework.data.annotation.CreatedDate;
        import org.springframework.data.annotation.LastModifiedDate;
        import org.springframework.data.jpa.domain.support.AuditingEntityListener;

        import javax.persistence.*;
        import javax.validation.constraints.NotBlank;
        import java.util.Date;

@Entity
@Table(name = "orderline_table")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private double price;

    @NotBlank
    private int quantity;

    @ManyToOne(optional=false)
    @JoinColumn(name="order_fk", referencedColumnName="id")
    private Order order;

    @ManyToOne(optional=false)
    @JoinColumn(name="product_fk", referencedColumnName="id")
    private Product product;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;
}
