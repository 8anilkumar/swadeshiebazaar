package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.fragment.ServicesFragment;
import work.newproject.asus.as.swadeshiebazaar.fragment.SubCatFragment;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.PojoModel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.SubCatModel;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder>{

    Context context;
    List<PojoModel> list;
    ServicesFragment fragment;

    public ServicesAdapter(Context context, List<PojoModel> list, ServicesFragment fragment) {
        this.context = context;
        this.list = list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_list_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PojoModel datum=list.get(position);
        holder.txtCatName.setText(datum.getTitle());
        holder.txtImage.setImageResource(datum.getImages());
        holder.constLayout.setOnClickListener(v -> fragment.getEnq(datum.getTitle()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCatName;
        ImageView txtImage;
        ConstraintLayout constLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCatName=itemView.findViewById(R.id.txtCatName);
            txtImage = itemView.findViewById(R.id.imgIcone);
            constLayout=itemView.findViewById(R.id.constLayout);
        }
    }
}


