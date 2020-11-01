package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.view.View;

import com.zhpan.bannerview.BaseBannerAdapter;

import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.home_data_model;

public class SliderImageAdapter extends BaseBannerAdapter<home_data_model.LayoutDatum, NetViewHolder> {
    @Override
    protected void onBind(NetViewHolder holder, home_data_model.LayoutDatum data, int position, int pageSize) {
        holder.bindData(data, position, pageSize);
    }

    @Override
    public NetViewHolder createViewHolder(View itemView, int viewType) {
        return new NetViewHolder(itemView);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.layout_of_banner;
    }
}
