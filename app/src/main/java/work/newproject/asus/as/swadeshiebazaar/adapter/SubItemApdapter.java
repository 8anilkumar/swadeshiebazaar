package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.internal.Constants;
import work.newproject.asus.as.swadeshiebazaar.MVpView;
import work.newproject.asus.as.swadeshiebazaar.ModelClass.ModelData;
import work.newproject.asus.as.swadeshiebazaar.ProductSubCatFragment;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.DropDownCatMOdel;

public class SubItemApdapter extends RecyclerView.Adapter<SubItemApdapter.ViewHolder> {
    int lastposition=0;
    Context context;
    List<DropDownCatMOdel.Datum> list;
    ProductSubCatFragment fragment;
    String name;
    MVpView mVpView;

    public SubItemApdapter(Context context, List<DropDownCatMOdel.Datum> list, ProductSubCatFragment fragment, String name,MVpView mVpView) {
        this.context = context;
        this.list = list;
        this.fragment = fragment;
        this.name = name;
        this.mVpView=mVpView;
    }

    @NonNull
    @Override
    public SubItemApdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hori, parent, false);
        return new SubItemApdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubItemApdapter.ViewHolder holder, int position) {
        DropDownCatMOdel.Datum datum = list.get(position);
        holder.txtCat.setText(datum.getSubCat());
        if (position==lastposition){
            holder.mainConstraintLayout.setBackgroundResource(R.color.colorPrimary);
        }
        else{
            holder.mainConstraintLayout.setBackgroundResource(R.color.white);
        }

        holder.horizontalCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position==0){
                    mVpView.getSubData("All","");
                    // fragment.getCatData(modell.getName(),modell.getId());
                    if (lastposition >= 0)
                        // fragment.getCatData(modell.getName(),modell.getId());
                    notifyItemChanged(lastposition);
                    lastposition = holder.getAdapterPosition();
                    notifyItemChanged(lastposition);
                    holder.mainConstraintLayout.setBackgroundResource(R.color.colorPrimary);
                }else {
                    if (lastposition >= 0)
                        //  fragment.getCatData(modell.getName(),modell.getId());
                        mVpView.getSubData("",datum.getSubId());
                    notifyItemChanged(lastposition);
                    lastposition = holder.getAdapterPosition();
                    notifyItemChanged(lastposition);
                    holder.mainConstraintLayout.setBackgroundResource(R.color.colorPrimary);
                    notifyDataSetChanged();
                }

               // fragment.getSubData(datum.getSubCat(),datum.getSubId());
            }
        });

//        if (name.equalsIgnoreCase(datum.getName())){
//            holder.txtCat.setText(datum.getName());
//            holder.txtCat.setTextColor(context.getResources().getColor(R.color.colorAccent));
//        }else {
//            holder.txtCat.setText(datum.getName());
//        }
//        holder.itemView.setOnClickListener(v -> fragment.getCatData(datum.getName(),datum.getId()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCat;
        RelativeLayout horizontalCart;
        ConstraintLayout mainConstraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCat=itemView.findViewById(R.id.txtCat);
            horizontalCart = itemView.findViewById(R.id.horizontalCart);
            mainConstraintLayout = itemView.findViewById(R.id.mainConstraintLayout);
        }
    }
}
