package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhpan.bannerview.BannerViewPager;

import java.util.List;

import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.home_data_model;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<home_data_model.Datum> list;

    private final int CATLIST = 0, REC = 1;


    public HomeAdapter(Context context, List<home_data_model.Datum> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == CATLIST) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_layout_recler, parent, false);
            return new cat_holder(view);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_bnner, parent, false);
            return new home_banner(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == CATLIST) {
            ((cat_holder) holder).setDATA(list.get(position),position);

        } else {
            ((home_banner) holder).setDATA(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public int getItemViewType(int position) {
        int viewType;
        if (list.get(position).getLayoutType().equals("catagory")) {
            viewType = CATLIST;
        } else {
            viewType = REC;
        }
        return viewType;
    }


    public class home_banner extends RecyclerView.ViewHolder {
        BannerViewPager banner_view;

        public home_banner(@NonNull View view) {
            super(view);
            banner_view = view.findViewById(R.id.banner_view);
        }

        private void setDATA(home_data_model.Datum data) {

        }
    }


    public class cat_holder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        public cat_holder(@NonNull View view) {
            super(view);
            recyclerView=view.findViewById(R.id.rvCat);

        }

        private void setDATA(home_data_model.Datum data,int i) {

            Log.d("TAG", "setDATA: "+data.getLayoutData().size());
            Log.d("TAG", "setDATA: "+data.getLayoutData().get(i).getName());

            CattAdapter subCatDetailsAdapter = new CattAdapter(data.getLayoutData(), context);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
            recyclerView.setAdapter(subCatDetailsAdapter);

        }


    }
}
