package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import work.newproject.asus.as.swadeshiebazaar.MVpView;
import work.newproject.asus.as.swadeshiebazaar.ModelClass.ModelData;
import work.newproject.asus.as.swadeshiebazaar.ProductSubCatFragment;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.DropDownCatMOdel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.SubCatModel;

public class horizontal_cardview extends RecyclerView.Adapter<horizontal_cardview.ViewHolder> {

    int lastposition=0;
    Context context;
    List<ModelData> list;
    List<DropDownCatMOdel.ChildCat> horizontalRVData;
    ProductSubCatFragment fragment;
    String name;
    MVpView mVpView;

    public horizontal_cardview(Context context, List<DropDownCatMOdel.ChildCat> horizontalRVData, ProductSubCatFragment fragment, MVpView mVpView) {
        this.context = context;
        this.horizontalRVData = horizontalRVData;
        this.fragment = fragment;
        this.mVpView=mVpView;
    }

    @NonNull
    @Override
    public horizontal_cardview.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hori, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }

    @Override
    public void onBindViewHolder(@NonNull horizontal_cardview.ViewHolder holder, int position) {
        DropDownCatMOdel.ChildCat  modell = horizontalRVData.get(position);
        holder.txtCat.setText(modell.getName());
        if (position==lastposition){
            holder.mainConstraintLayout.setBackgroundResource(R.color.colorPrimary);
        }
        else{
            holder.mainConstraintLayout.setBackgroundResource(R.color.white);
        }

        holder.horizontalCart.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (position==0){
                    mVpView.getProduct("All","");
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
                     mVpView.getProduct("",modell.getId());
                    notifyItemChanged(lastposition);
                    lastposition = holder.getAdapterPosition();
                    notifyItemChanged(lastposition);
                    holder.mainConstraintLayout.setBackgroundResource(R.color.colorPrimary);
                    notifyDataSetChanged();
                }
            }
        });
//        if(selected_position==-1){
//            holder.mainConstraintLayout.setBackgroundColor(Color.parseColor("#04a40e"));
//            Toast.makeText(context, "this is toast postion -> "+selected_position, Toast.LENGTH_SHORT).show();
//            //holder.tv1.setTextColor(Color.parseColor("#ffffff"));
//        }
//        else
//        {
//            holder.mainConstraintLayout.setBackgroundColor(Color.parseColor("#ffffff"));
//            Toast.makeText(context, "this is toast postion -> "+selected_position, Toast.LENGTH_SHORT).show();
//
//            //holder.tv1.setTextColor(Color.parseColor("#000000"));
//        }
    }

    @Override
    public int getItemCount() {
        return horizontalRVData.size();
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
