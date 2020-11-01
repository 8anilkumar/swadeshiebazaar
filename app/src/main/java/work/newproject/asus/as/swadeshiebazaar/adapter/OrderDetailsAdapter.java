package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.annotation.SuppressLint;
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
import work.newproject.asus.as.swadeshiebazaar.network.model_res.OrderDeatilsMOdel;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {

    List<OrderDeatilsMOdel.Datum> list;
    Context context;


    public OrderDetailsAdapter(List<OrderDeatilsMOdel.Datum> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_deatils, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderDetailsAdapter.ViewHolder holder, int position) {

        OrderDeatilsMOdel.Datum datum = list.get(position);
        holder.txtName.setText(datum.getName());
        holder.txtDeatils.setText(datum.getStatus());
        holder.txtAmount.setText(datum.getPrice());
        holder.txtQty.setText("Qty : "+datum.getQty());
        Glide.with(context).load(AppStrings.cat_path+datum.getImage()).into(holder.imgLogo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo, imgRemove;
        TextView txtName, txtAmount, txtDeatils,txtQty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            txtName = itemView.findViewById(R.id.txtName);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            imgRemove = itemView.findViewById(R.id.imgRemove);
            txtDeatils = itemView.findViewById(R.id.txtDeatils);
            txtQty=itemView.findViewById(R.id.txtQty);
        }
    }
}
