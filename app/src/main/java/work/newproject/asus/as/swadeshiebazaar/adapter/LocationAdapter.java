package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import work.newproject.asus.as.swadeshiebazaar.GetLocation;
import work.newproject.asus.as.swadeshiebazaar.MainActivity;
import work.newproject.asus.as.swadeshiebazaar.ModelClass.LocationModel;
import work.newproject.asus.as.swadeshiebazaar.MySharedpreferences.MySharedpreferences;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.auth.AuthActivity;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

import static android.content.Context.MODE_PRIVATE;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> implements Filterable {

    Context context;
    List<LocationModel.LocationData> locationlist;
    List<LocationModel.LocationData> mFilteredList;

    public LocationAdapter(List<LocationModel.LocationData> data, GetLocation fragment) {
        this.locationlist = data;
        this.context = fragment;
        mFilteredList = new ArrayList<>(locationlist);
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.location_list, parent, false);
        return new LocationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        LocationModel.LocationData location = locationlist.get(position);
        holder.txtLocation.setText(location.getName());
        holder.Location_linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("USER_CREDENTIALS", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("USER_SELECT_ADDRESS", location.getName());
                editor.commit();
                if (MySharedpreferences.getInstance().get(context, AppStrings.userID) != null) {
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, AuthActivity.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationlist.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<LocationModel.LocationData> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mFilteredList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (LocationModel.LocationData item : mFilteredList) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            locationlist.clear();
            locationlist.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtLocation;
        LinearLayout Location_linear_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            Location_linear_layout = itemView.findViewById(R.id.Location_linear_layout);
        }
    }
}
