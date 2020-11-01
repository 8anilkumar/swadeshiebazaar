package work.newproject.asus.as.swadeshiebazaar.network;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import work.newproject.asus.as.swadeshiebazaar.ModelClass.LocationModel;
import work.newproject.asus.as.swadeshiebazaar.ModelClass.ProductDataModel;

public interface ChildApi {

    @FormUrlEncoded
    @POST("pchildcat")
    Single<ProductDataModel> getChildProduct(@Field("id") String id, @Field("user_id") String user_id);

    @POST("getlocation")
    Single<LocationModel> getlocation();

}
