package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.fragment.SubCatFragment;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.home_data_model;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

public class CattAdapter  extends RecyclerView.Adapter<CattAdapter.ViewHolder>  {

    List<home_data_model.LayoutDatum> list;
    Context context;

    public CattAdapter(List<home_data_model.LayoutDatum> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public CattAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cat_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CattAdapter.ViewHolder holder, int position) {
        home_data_model.LayoutDatum layoutDatum=list.get(position);
        holder.txtCatName.setText(layoutDatum.getName());
       // String plain = String.valueOf(Html.fromHtml());
        Log.d("TAG", "onBindViewHolder: "+AppStrings.imag_path+layoutDatum.getIcon());
       Glide.with(context).load(AppStrings.imag_path+layoutDatum.getIcon().toString().trim()).into(holder.imgLogo);

       holder.imgLogo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Bundle args = new Bundle();
               args.putString("catID",layoutDatum.getId());
               args.putString("flag","2");
               Fragment fragmentt = new SubCatFragment();
               FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
               fragmentt.setArguments(args);
               transaction.replace(R.id.fragment, fragmentt);
               transaction.addToBackStack("social").commit();
           }
       });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;
        TextView txtCatName;
        CardView cardView;
        ConstraintLayout constLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            txtCatName = itemView.findViewById(R.id.txtCatName);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
}
