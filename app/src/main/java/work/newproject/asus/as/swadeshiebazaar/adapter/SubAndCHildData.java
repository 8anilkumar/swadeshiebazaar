package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import work.newproject.asus.as.swadeshiebazaar.ProductSubCatFragment;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.DropDownCatMOdel;

public class SubAndCHildData extends RecyclerView.Adapter<SubAndCHildData.RecViewHolder> {

    Context context;
    List<DropDownCatMOdel.Datum> list;
    String flag;

    public SubAndCHildData(Context context, List<DropDownCatMOdel.Datum> list, String flag) {
        this.context = context;
        this.list = list;
        this.flag = flag;
    }

    @NonNull
    @Override
    public SubAndCHildData.RecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new RecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubAndCHildData.RecViewHolder holder, int position) {
        DropDownCatMOdel.Datum movie = list.get(position);
        List<DropDownCatMOdel.ChildCat> childCats = movie.getChildCat();
        // DropDownCatMOdel.ChildCat child = childCats.get(position);
        holder.titleTextView.setText(movie.getSubCat());

        if (!movie.getChildCat().isEmpty()){
            holder.rvChildCat.setVisibility(View.GONE);
            holder.rvChildCat.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            ChilCatAdapter subCatAdapter = new ChilCatAdapter(context, movie.getChildCat());
            holder.rvChildCat.setAdapter(subCatAdapter);
            boolean isExpanded = list.get(position).isExpanded();
            holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        }

        holder.titleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Bundle args = new Bundle();
                    args.putString("catID", movie.getSubId());
                    args.putString("catType","sub");
                    args.putString("flag",flag);
                    args.putString("main_id",movie.getMainId());
                    args.putString("name",movie.getSubCat());
                    //args.putString("mainID",childCats.get(position).getParentId());
                    Fragment fragmentt = new ProductSubCatFragment(childCats);
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
        return list == null ? 0 : list.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "MovieVH";
        ConstraintLayout expandableLayout;
        RecyclerView rvChildCat;
        TextView titleTextView, yearTextView, ratingTextView, plotTextView;

        public RecViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            rvChildCat=itemView.findViewById(R.id.rvChildCat);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
        }
    }
}
