package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.fragment.SubCatFragment;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.SubCatModel;

public class SubCatAdapter  extends RecyclerView.Adapter<SubCatAdapter.ViewHolder> {

    Context context;
    List<SubCatModel.Datum> list;
    SubCatFragment fragment;

    public SubCatAdapter(Context context, List<SubCatModel.Datum> list, SubCatFragment fragment) {
        this.context = context;
        this.list = list;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public SubCatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sub_cat, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCatAdapter.ViewHolder holder, int position) {

        SubCatModel.Datum datum=list.get(position);
        holder.txtCatName.setText(datum.getName());
        holder.constLayout.setOnClickListener(v -> fragment.getChildID(datum.getId()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCatName;
        ConstraintLayout constLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCatName=itemView.findViewById(R.id.txtCatName);
            constLayout=itemView.findViewById(R.id.constLayout);
        }
    }
}
