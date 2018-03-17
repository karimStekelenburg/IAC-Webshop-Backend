# Webshop

## Frameworks used
- Spring Boot (for easy implementation of API related stuff)
- Hibernate (**O**bject-**R**elational-**M**apping stuff)
- JPA (I don't actually know what it does, but it works.. :---) )

## A few side notes
Because we use an ORM framework (hibernate), the database is not designed like your used to. You're probably used to designing and defining a database structure by firing DDL commands to the SQL instance (e.g. `CREATE DATABASE` & `CREATE TABLE`). This is **not** the case in this project.

Database creation and management is handled by Hibernate. All we have to do is tell Hibernate what our entities should look like and what the relationships are like (one-to-one, one-to-many, etc.) We do this in the **domain** classes of our  application. More about this further down in this document.


## Global application structure
The directory structure of our project looks like this:

![](img/project_structure.png)

As you can see we have a few packages, here is what they do:
- **Model** package
  - this is where the domain classes live and thus where we define the database structure.
- **Repository** package
  - This is where the repository/DAO classes live.
  - As you'll see further down in this document, very little is needed to get this up and running.
- **Controller** package
  - This is where our REST (and SOAP in the near future) endpoints live. Incoming calls are intercepted here and almost all business logic is also done in this package.
- **Exception** package
  - Here we have a few custom exception classes, since we're not allowed to throw standard framework exceptions.
- **Config** package
  - Don't worry about this, maybe I need to add some spring config here later, when we are going to deploy.
- **Util** package
  - Here we have one class (for now) containing some logic for encoding and decoding JWT tokens (again, don't worry about it).

## A walk through, layer by layer
In this section we'll discuss the dataflow from the `controller` (where API calls are intercepted) to the `repository` (where data is retrieved and stored).

We'll use the `Product` and `Category` classes to illustrate the workings.

### The object model (or domain class)
Basically the same as all domain classes you have written so far, except the weird `@` stuff. These are called annotations and they tell Hibernate about our entities. Per annotation I've added a comment explaining what they do.

```java
@Entity  // tells Hibernate this class is an entity (and should be persisted to the database)
@Table(name = "product_table")  // tells Hibernate how it should name the table that is used to store this entity
@EntityListeners(AuditingEntityListener.class)  // I have no fucking clue what this does, but it should be there lol
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)  // tells Hibernate not to include these attributes when serialising to JSON
public class Product implements Serializable {

    @Id  // tells Hibernate this is the primary key
    @GeneratedValue(strategy = GenerationType.AUTO)  // tells Hibernate to generate a value upon insertion.
    private Long id;

    @NotBlank  // tells Hibernate this cannot be null
    private String name;

    private String description;

    @NotBlank  // tells Hibernate this cannot be null
    private double price;

    @Column(columnDefinition = "LONGBLOB")  // tells Hibernate what the column type should be. Normally this is automaticly done for you (e.g. a Java String becomes a VARCHAR). In this case it made a TINYBLOB which is too small to store images in, so we have to force-specify the column type)
    private byte[] image;



    @ManyToMany(fetch = FetchType.EAGER)  // tells Hibernate this attribute actually is a relationship to another entity (and thus db table). As you would assume with a many-to-many relationship, this requires a coupling table.
    @JoinTable(name="product_category", // tells Hibernate how to name the coupling table.
            joinColumns=
            @JoinColumn(name="product_fk", referencedColumnName="id"), // name tells Hibernate how it should call the FK column in the coupling table, referencedColumnName specifies the name of the column it references (pretty much always id)
            inverseJoinColumns=
            @JoinColumn(name="category_fk", referencedColumnName="id") // the same as above, but the other way around.
    )
    private List<Category> categories;

    // tells Hibernate there is a one-to-many relationship with the product table
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
    }
}

```
