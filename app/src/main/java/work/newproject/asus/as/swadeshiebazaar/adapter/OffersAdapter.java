package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.home_data_model;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder>  {

    List<home_data_model.LayoutDatum> list;
    Context context;

    public OffersAdapter(List<home_data_model.LayoutDatum> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OffersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_offer, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersAdapter.ViewHolder holder, int position) {
        home_data_model.LayoutDatum layoutDatum=list.get(position);
        layoutDatum.setWishList(false);
        holder.tvDiscount.setText(layoutDatum.getDiscount());
        holder.tvOfferName.setText(layoutDatum.getName());
        Glide.with(context).load(AppStrings.imag_path+layoutDatum.getImage()).into(holder.ivOfferImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivOfferImage;
        TextView tvDiscount, tvOfferName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDiscount      = itemView.findViewById(R.id.tvDiscount);
            ivOfferImage     = itemView.findViewById(R.id.imgLogo);
            tvOfferName   = itemView.findViewById(R.id.tvOfferName);
        }
    }
}
