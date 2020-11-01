package work.newproject.asus.as.swadeshiebazaar.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wishListTable")
public class wishListTable   implements java.io.Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "shrt_description")
    private String shrt_description;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "stock")
    private String stock;

    @ColumnInfo(name = "remain_stock")
    private String remain_stock;

    @ColumnInfo(name = "discount")
    private String discount;


    @ColumnInfo(name = "handling_fee")
    private String handling_fee;

    @ColumnInfo(name = "manufacturer_id")
    private String manufacturer_id;

    @ColumnInfo(name = "category_id")
    private String category_id;

    @ColumnInfo(name = "sub_cat")
    private String sub_cat;

    @ColumnInfo(name = "child_cat")
    private String child_cat;

    @ColumnInfo(name = "brand_id")
    private String brand_id;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "more_image")
    private String more_image;

    @ColumnInfo(name = "posted_at")
    private String posted_at;

    @ColumnInfo(name = "updated_at")
    private String updated_at;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "featured")
    private String featured;

    @ColumnInfo(name = "newproduct")
    private String newproduct;


    @ColumnInfo(name = "qty")
    private String qty;


    @ColumnInfo(name = "productID")
    private long productID;


    @ColumnInfo(name = "actualPrice")
    private String actualPrice;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShrt_description() {
        return shrt_description;
    }

    public void setShrt_description(String shrt_description) {
        this.shrt_description = shrt_description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getRemain_stock() {
        return remain_stock;
    }

    public void setRemain_stock(String remain_stock) {
        this.remain_stock = remain_stock;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getHandling_fee() {
        return handling_fee;
    }

    public void setHandling_fee(String handling_fee) {
        this.handling_fee = handling_fee;
    }

    public String getManufacturer_id() {
        return manufacturer_id;
    }

    public void setManufacturer_id(String manufacturer_id) {
        this.manufacturer_id = manufacturer_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getSub_cat() {
        return sub_cat;
    }

    public void setSub_cat(String sub_cat) {
        this.sub_cat = sub_cat;
    }

    public String getChild_cat() {
        return child_cat;
    }

    public void setChild_cat(String child_cat) {
        this.child_cat = child_cat;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMore_image() {
        return more_image;
    }

    public void setMore_image(String more_image) {
        this.more_image = more_image;
    }

    public String getPosted_at() {
        return posted_at;
    }

    public void setPosted_at(String posted_at) {
        this.posted_at = posted_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getNewproduct() {
        return newproduct;
    }

    public void setNewproduct(String newproduct) {
        this.newproduct = newproduct;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }
}
