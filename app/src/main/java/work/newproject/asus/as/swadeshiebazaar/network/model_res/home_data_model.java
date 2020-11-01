package work.newproject.asus.as.swadeshiebazaar.network.model_res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class home_data_model implements Serializable {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public class Datum {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("layout_type")
        @Expose
        private String layoutType;
        @SerializedName("layout_data")
        @Expose
        private List<LayoutDatum> layoutData = null;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLayoutType() {
            return layoutType;
        }

        public void setLayoutType(String layoutType) {
            this.layoutType = layoutType;
        }

        public List<LayoutDatum> getLayoutData() {
            return layoutData;
        }

        public void setLayoutData(List<LayoutDatum> layoutData) {
            this.layoutData = layoutData;
        }

    }
    public class LayoutDatum {
        private Boolean isWishList;

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("discount")
        @Expose
        private String discount;
        @SerializedName("catagory_id")
        @Expose
        private String catagoryId;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("catagory_name")
        @Expose
        private String catagoryName;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("parent_id")
        @Expose
        private String parentId;
        @SerializedName("shrt_description")
        @Expose
        private String shrtDescription;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("offer_price")
        @Expose
        private String offerPrice;
        @SerializedName("stock")
        @Expose
        private String stock;
        @SerializedName("remain_stock")
        @Expose
        private String remainStock;
        @SerializedName("handling_fee")
        @Expose
        private Object handlingFee;
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
        @SerializedName("more_image")
        @Expose
        private String moreImage;
        @SerializedName("posted_at")
        @Expose
        private String postedAt;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;
        @SerializedName("featured")
        @Expose
        private String featured;
        @SerializedName("newproduct")
        @Expose
        private String newproduct;
        @SerializedName("coupon_id")
        @Expose
        private String couponId;
        @SerializedName("coupon_code")
        @Expose
        private String couponCode;
        @SerializedName("cat_name")
        @Expose
        private String catName;
        @SerializedName("product")
        @Expose
        private List<Product> product = null;

        public Boolean getWishList() {
            return isWishList;
        }

        public void setWishList(Boolean wishList) {
            isWishList = wishList;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getCatagoryId() {
            return catagoryId;
        }

        public void setCatagoryId(String catagoryId) {
            this.catagoryId = catagoryId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getCatagoryName() {
            return catagoryName;
        }

        public void setCatagoryName(String catagoryName) {
            this.catagoryName = catagoryName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
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

        public String getOfferPrice() {
            return offerPrice;
        }

        public void setOfferPrice(String offerPrice) {
            this.offerPrice = offerPrice;
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

        public Object getHandlingFee() {
            return handlingFee;
        }

        public void setHandlingFee(Object handlingFee) {
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

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public String getCatName() {
            return catName;
        }

        public void setCatName(String catName) {
            this.catName = catName;
        }

        public List<Product> getProduct() {
            return product;
        }

        public void setProduct(List<Product> product) {
            this.product = product;
        }

    }


    public class Product {
        private Boolean isWishList;

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
        @SerializedName("offer_price")
        @Expose
        private String offerPrice;
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


        public Boolean getWishList() {
            return isWishList;
        }

        public void setWishList(Boolean wishList) {
            isWishList = wishList;
        }

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

        public String getOfferPrice() {
            return offerPrice;
        }

        public void setOfferPrice(String offerPrice) {
            this.offerPrice = offerPrice;
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
    }
