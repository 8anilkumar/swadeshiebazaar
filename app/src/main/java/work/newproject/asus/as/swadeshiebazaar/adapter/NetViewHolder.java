package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.zhpan.bannerview.BaseViewHolder;

import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.home_data_model;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

public class NetViewHolder  extends BaseViewHolder<home_data_model.LayoutDatum> {

    public NetViewHolder(@NonNull View itemView) {
        super(itemView);

    }


    @Override
    public void bindData(home_data_model.LayoutDatum data, int position, int pageSize) {
        ImageView imageView = findView(R.id.banner_image);
        Glide.with(imageView).load(AppStrings.imag_path+data.getImage()).into(imageView);
    }
}