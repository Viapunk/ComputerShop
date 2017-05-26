package domain;

import domain.Category;
import domain.Comment;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pwilk on 26.05.17.
 */
@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name = "product.all", query = "SELECT p FROM Product p"),
        @NamedQuery(name = "product.id", query = "FROM Product WHERE p.id=:productId"),
        @NamedQuery(name = "product.category", query = "FROM Product WHERE p.productCategory=:productCategory"),
        @NamedQuery(name = "product.price", query = "FROM Product WHERE p.price BETWEEN :minPrice AND :maxPrice"),
        @NamedQuery(name = "product.name", query = "FROM Product WHERE p.productName LIKE :productName")
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String productName;
    private float price;
    private List<Comment> comments = new ArrayList<Comment>();

    private Category productCategory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @XmlTransient
    @OneToMany(mappedBy = "product")
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    public Category getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Category productCategory) {
        this.productCategory = productCategory;
    }
}
