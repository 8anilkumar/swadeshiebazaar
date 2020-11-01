package work.newproject.asus.as.swadeshiebazaar.network.model_res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class AddressLIstModel implements Serializable {
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

        @SerializedName("address_id")
        @Expose
        private String addressId;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("house_no")
        @Expose
        private String houseNo;
        @SerializedName("area_colony")
        @Expose
        private String areaColony;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("landmark")
        @Expose
        private String landmark;
        @SerializedName("alt_mobile")
        @Expose
        private String altMobile;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("alternate_name")
        @Expose
        private String alternateName;
        @SerializedName("state_name")
        @Expose
        private String stateName;
        @SerializedName("city_name")
        @Expose
        private String cityName;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("created_on")
        @Expose
        private String createdOn;

        public String getAddressId() {
            return addressId;
        }

        public void setAddressId(String addressId) {
            this.addressId = addressId;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public String getHouseNo() {
            return houseNo;
        }

        public void setHouseNo(String houseNo) {
            this.houseNo = houseNo;
        }

        public String getAreaColony() {
            return areaColony;
        }

        public void setAreaColony(String areaColony) {
            this.areaColony = areaColony;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public String getAltMobile() {
            return altMobile;
        }

        public void setAltMobile(String altMobile) {
            this.altMobile = altMobile;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getAlternateName() {
            return alternateName;
        }

        public void setAlternateName(String alternateName) {
            this.alternateName = alternateName;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedOn() {
            return createdOn;
        }

        public void setCreatedOn(String createdOn) {
            this.createdOn = createdOn;
        }

    }
}
