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
import work.newproject.asus.as.swadeshiebazaar.network.model_res.DropDownCatMOdel;

public class ChildItemAdapter extends RecyclerView.Adapter<ChildItemAdapter.ViewHolder> {
    List<DropDownCatMOdel.ChildCat> childCat;
    Context context;

    public ChildItemAdapter(List<DropDownCatMOdel.ChildCat> childCat, Context context) {
        this.childCat = childCat;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_cat, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DropDownCatMOdel.ChildCat data = childCat.get(position);
        holder.txtCatName.setText(data.getName());
    }

    @Override
    public int getItemCount() {
        return childCat.size();
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
