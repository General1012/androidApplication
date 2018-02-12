package com.example.mandelacomp3.mypnp;



        import java.io.Serializable;
        import java.util.Objects;


//@SequenceGenerator(name = LineProduct.LINE_PRODUCT_ID_SEQ, sequenceName = LineProduct.LINE_PRODUCT_ID_SEQ,initialValue = 10,allocationSize = 53)
public class OrderedProduct implements Serializable {
    //private static final long serialVersionUID = 1L;
//    public static final String LINE_PRODUCT_ID_SEQ = "LINE_PRODUCT_ID_SEQ";

//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = LINE_PRODUCT_ID_SEQ)

    private Integer id;
    private int quantity;
    private long productId;
    private String name ;
    private double price;
    private String image ;


    public OrderedProduct(){
    }

    public OrderedProduct( long productId,int quantity, String name, double price, String image) {
        this.quantity = quantity ;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    @Override
    public String toString() {
        return "com.pnp.pnp.entity.Ordered[ id=" + id + " ]";
    }

}
