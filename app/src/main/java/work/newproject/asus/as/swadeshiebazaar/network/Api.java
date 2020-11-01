package work.newproject.asus.as.swadeshiebazaar.network;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import work.newproject.asus.as.swadeshiebazaar.ModelClass.CancelOrder;
import work.newproject.asus.as.swadeshiebazaar.ModelClass.ProductDataModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.AddressLIstModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.ChildCatMOdel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.CityListModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.DropDownCatMOdel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.ForgortPasswordModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.KeyWordModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.MyProfileModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.OrderDeatilsMOdel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.OrderModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.OtpVerifyModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.PlaceOrderMOdel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.ProductDiscriptionModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.ProductSubCarModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.SelectAddressMOdel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.SignUpModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.SubCatModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.UpdatePasswordModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.add_address_model;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.home_data_model;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.state_model;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.user_login;

public interface Api {

    @POST("home_data")
    Single<home_data_model> getHomeData();

    @FormUrlEncoded
    @POST("user_signup")
    Single<SignUpModel> userSignUp(@Field("name") String name, @Field("email") String email, @Field("mobile") String mobile, @Field("password") String password);

    @FormUrlEncoded
    @POST("otp_verify")
    Single<OtpVerifyModel> otpVerfy(@Field("mobile") String mobile, @Field("otp") String otp);

    @FormUrlEncoded
    @POST("user_login")
    Single<user_login> userLogin(@Field("mobile") String mobile, @Field("password") String password);

    @FormUrlEncoded
    @POST("product_detail")
    Single<ProductDiscriptionModel> getProductDeatils(@Field("product_id") String product_id);

    /*@FormUrlEncoded
    @POST("add_address")
    Single<add_address> addAddress(@Field("user_id") String user_id, @Field("pincode") String pincode, @Field("house_no") String house_no, @Field("area_colony") String area_colony
            , @Field("city") String city, @Field("state") String state, @Field("landmark") String landmark, @Field("alternate_mobile_number") String alternate_mobile_number, @Field("alternate_name") String alternate_name);
*/

    @POST("get_state")
    Single<state_model> getState();

    @FormUrlEncoded
    @POST("get_city")
    Single<CityListModel> getCityList(@Field("id") String id);

    @FormUrlEncoded
    @POST("add_address")
    Single<add_address_model> addAddress(@Field("user_id") String user_id,@Field("pincode") String pincode,@Field("house_no") String house_no,@Field("area_colony")String area_colony
    ,@Field("city")String city,@Field("state")String state,@Field("landmark")String landmark,@Field("alternate_mobile_number")String alternate_mobile_number,@Field("alternate_name")String alternate_name);

    @FormUrlEncoded
    @POST("user_address_list")
    Single<AddressLIstModel> getAddressList(@Field("user_id") String id);

    @FormUrlEncoded
    @POST("set_shipping_address")
    Single<SelectAddressMOdel> selectAddress(@Field("user_id") String id,@Field("address_id") String address_id,@Field("status") String status);

    @FormUrlEncoded
    @POST("search_by_keyword")
    Single<KeyWordModel> getProductList(@Field("keyword") String keyword, @Field("limit") String limit);

    @FormUrlEncoded
    @POST("category_wise_product")
    Single<ProductSubCarModel> getSubCat(@Field("id") String id,@Field("type") String type);

    @FormUrlEncoded
    @POST("get_sub")
    Single<SubCatModel>getSub(@Field("cat_id") String id);

    @FormUrlEncoded
    @POST("child_cat")
    Single<ChildCatMOdel>getChild(@Field("sub_cat_id") String sub_cat_id);

    @FormUrlEncoded
    @POST("user_info")
    Single<MyProfileModel>getProfile(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("json")
    Single<PlaceOrderMOdel>placeOrder(@Field("user_id") String user_id,@Field("delivery_date")String delivery_date,@Field("delivery_time")String delivery_time
    ,@Field("address_id")String address_id,@Field("wallet")String wallet,@Field("wallet_status")String wallet_status,@Field("payment_id")String payment_id,@Field("json")String json,@Field("type")String type);

    @FormUrlEncoded
    @POST("order_list")
    Single<OrderModel>getOrder(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("particular_order_detail")
    Single<OrderDeatilsMOdel>getOrderDetails(@Field("user_id") String user_id,@Field("order_id")String order_id);

    @FormUrlEncoded
    @POST("forget_password")
    Single<ForgortPasswordModel> forhroPassword(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("update_password")
    Single<UpdatePasswordModel> updatePassword(@Field("mobile") String mobile,@Field("password") String password);

    @FormUrlEncoded
    @POST("dropdown")
    Single<DropDownCatMOdel> getSubAndChild(@Field("id") String id);

    @FormUrlEncoded
    @POST("pchildcat")
    Single<ProductDataModel> getChildProduct(@Field("id") String id);

    @FormUrlEncoded
    @POST("cancelorder")
    Single<CancelOrder> getCancelOrder(@Field("order_id") String id);


}
