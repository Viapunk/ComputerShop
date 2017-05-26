package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by pwilk on 26.05.17.
 */
@XmlRootElement
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String author;
    private String content;

    @ManyToOne
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
