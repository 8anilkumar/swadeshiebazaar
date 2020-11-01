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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import work.newproject.asus.as.swadeshiebazaar.ProductSubCatFragment;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.fragment.getChildFragment;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.ChildCatMOdel;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.DropDownCatMOdel;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

public class ChilCatAdapter extends RecyclerView.Adapter<ChilCatAdapter.ViewHolder> {
    Context context;
    List<DropDownCatMOdel.ChildCat> list;
    getChildFragment fragment;


    public ChilCatAdapter(Context context, List<DropDownCatMOdel.ChildCat> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ChilCatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_cat, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChilCatAdapter.ViewHolder holder, int position) {
        DropDownCatMOdel.ChildCat datum = list.get(position);
        holder.txtCatName.setText(datum.getName());
        //  holder.constLayout.setOnClickListener(v -> fragment.getID(datum.getId()));
       Glide.with(context).load(AppStrings.imag_path+datum.getIcon()).into(holder.imgIcone);


       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Bundle args = new Bundle();
               args.putString("catID", datum.getId());
               args.putString("catType", "child");
               args.putString("mainID",datum.getParentId());
               args.putString("name",datum.getName());
               Log.d("TAG", "onClick: "+datum.getParentId());
               Fragment fragmentt = new ProductSubCatFragment();
               FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
               FragmentTransaction transaction = manager.beginTransaction();
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
        TextView txtCatName;
        ConstraintLayout constLayout;
        ImageView imgIcone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCatName = itemView.findViewById(R.id.txtCatName);
            constLayout = itemView.findViewById(R.id.constLayout);
            imgIcone=itemView.findViewById(R.id.imgIcone);

        }
    }
}
