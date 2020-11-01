package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.fragment.DashBoardFragment;
import work.newproject.asus.as.swadeshiebazaar.fragment.SellAllProductFragment;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.home_data_model;

public class SubCatLIstAdapter  extends RecyclerView.Adapter<SubCatLIstAdapter.ViewHolder> {

    List<home_data_model.LayoutDatum> list;
    Context context;
    DashBoardFragment fragment;

    public SubCatLIstAdapter(List<home_data_model.LayoutDatum> list, Context context,DashBoardFragment fragment) {
        this.list = list;
        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public SubCatLIstAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_sub_cat, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCatLIstAdapter.ViewHolder holder, int position) {

        home_data_model.LayoutDatum datum=list.get(position);
        holder.tvHarbalProduct.setText(datum.getCatName());

        holder.btFSell.setOnClickListener(v -> fragment.getSeelAllList(datum.getProduct()));
        ProductSubAdapter newProducts = new ProductSubAdapter(datum.getProduct(), context,fragment );
        Collections.shuffle(datum.getProduct());
        holder.rvHarbalProduct.setAdapter(newProducts);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHarbalProduct;
        Button btFSell;
        RecyclerView rvHarbalProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHarbalProduct=itemView.findViewById(R.id.tvHarbalProduct);
            rvHarbalProduct=itemView.findViewById(R.id.rvHarbalProduct);
            btFSell=itemView.findViewById(R.id.btFSell);
        }
    }
}
