package work.newproject.asus.as.swadeshiebazaar.network.model_res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDiscriptionModel {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public class ProductDetail {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("shrt_description")
        @Expose
        private String shrtDescription;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("stock")
        @Expose
        private String stock;
        @SerializedName("remain_stock")
        @Expose
        private String remainStock;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("handling_fee")
        @Expose
        private String handlingFee;
        @SerializedName("manufacturer_id")
        @Expose
        private Object manufacturerId;
        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("sub_cat")
        @Expose
        private String subCat;
        @SerializedName("child_cat")
        @Expose
        private String childCat;
        @SerializedName("brand_id")
        @Expose
        private String brandId;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("more_image")
        @Expose
        private String moreImage;
        @SerializedName("posted_at")
        @Expose
        private String postedAt;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("featured")
        @Expose
        private String featured;
        @SerializedName("newproduct")
        @Expose
        private String newproduct;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShrtDescription() {
            return shrtDescription;
        }

        public void setShrtDescription(String shrtDescription) {
            this.shrtDescription = shrtDescription;
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

        public String getRemainStock() {
            return remainStock;
        }

        public void setRemainStock(String remainStock) {
            this.remainStock = remainStock;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getHandlingFee() {
            return handlingFee;
        }

        public void setHandlingFee(String handlingFee) {
            this.handlingFee = handlingFee;
        }

        public Object getManufacturerId() {
            return manufacturerId;
        }

        public void setManufacturerId(Object manufacturerId) {
            this.manufacturerId = manufacturerId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getSubCat() {
            return subCat;
        }

        public void setSubCat(String subCat) {
            this.subCat = subCat;
        }

        public String getChildCat() {
            return childCat;
        }

        public void setChildCat(String childCat) {
            this.childCat = childCat;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getMoreImage() {
            return moreImage;
        }

        public void setMoreImage(String moreImage) {
            this.moreImage = moreImage;
        }

        public String getPostedAt() {
            return postedAt;
        }

        public void setPostedAt(String postedAt) {
            this.postedAt = postedAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
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

    }
    public class RelatedProduct {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("shrt_description")
        @Expose
        private String shrtDescription;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("stock")
        @Expose
        private String stock;
        @SerializedName("remain_stock")
        @Expose
        private String remainStock;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("handling_fee")
        @Expose
        private String handlingFee;
        @SerializedName("manufacturer_id")
        @Expose
        private Object manufacturerId;
        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("sub_cat")
        @Expose
        private String subCat;
        @SerializedName("child_cat")
        @Expose
        private Object childCat;
        @SerializedName("brand_id")
        @Expose
        private String brandId;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("more_image")
        @Expose
        private String moreImage;
        @SerializedName("posted_at")
        @Expose
        private String postedAt;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("featured")
        @Expose
        private String featured;
        @SerializedName("newproduct")
        @Expose
        private String newproduct;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShrtDescription() {
            return shrtDescription;
        }

        public void setShrtDescription(String shrtDescription) {
            this.shrtDescription = shrtDescription;
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

        public String getRemainStock() {
            return remainStock;
        }

        public void setRemainStock(String remainStock) {
            this.remainStock = remainStock;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getHandlingFee() {
            return handlingFee;
        }

        public void setHandlingFee(String handlingFee) {
            this.handlingFee = handlingFee;
        }

        public Object getManufacturerId() {
            return manufacturerId;
        }

        public void setManufacturerId(Object manufacturerId) {
            this.manufacturerId = manufacturerId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getSubCat() {
            return subCat;
        }

        public void setSubCat(String subCat) {
            this.subCat = subCat;
        }

        public Object getChildCat() {
            return childCat;
        }

        public void setChildCat(Object childCat) {
            this.childCat = childCat;
        }

        public String getBrandId() {
            return brandId;
        }

        public void setBrandId(String brandId) {
            this.brandId = brandId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getMoreImage() {
            return moreImage;
        }

        public void setMoreImage(String moreImage) {
            this.moreImage = moreImage;
        }

        public String getPostedAt() {
            return postedAt;
        }

        public void setPostedAt(String postedAt) {
            this.postedAt = postedAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
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

    }
    public class Data {

        @SerializedName("product_detail")
        @Expose
        private List<ProductDetail> productDetail = null;
        @SerializedName("related_product")
        @Expose
        private List<RelatedProduct> relatedProduct = null;

        public List<ProductDetail> getProductDetail() {
            return productDetail;
        }

        public void setProductDetail(List<ProductDetail> productDetail) {
            this.productDetail = productDetail;
        }

        public List<RelatedProduct> getRelatedProduct() {
            return relatedProduct;
        }

        public void setRelatedProduct(List<RelatedProduct> relatedProduct) {
            this.relatedProduct = relatedProduct;
        }

    }
}
