package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import work.newproject.asus.as.swadeshiebazaar.AddressListActivity;
import work.newproject.asus.as.swadeshiebazaar.CartActivity;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.AddressLIstModel;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {

    Context context;
    List<AddressLIstModel.Datum> list;
    GetChooseAddress chooseAddress;
    AddressListActivity addressListActivity;

    public AddressListAdapter(Context context, List<AddressLIstModel.Datum> list, AddressListActivity addressListActivity) {
        this.context = context;
        this.list = list;
        this.chooseAddress= (GetChooseAddress) context;
        this.addressListActivity = addressListActivity;
    }

    @NonNull
    @Override
    public AddressListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_lits, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AddressListAdapter.ViewHolder holder, int position) {
        AddressLIstModel.Datum modelClass = list.get(position);
        String pinCode = modelClass.getPincode();
        String state = modelClass.getState();
        final String mobile = modelClass.getAltMobile();
        String city = modelClass.getCity();
        String houseNumber = modelClass.getHouseNo();
        String area = modelClass.getAreaColony();
        String landmark = modelClass.getLandmark();
        holder.txtAddres.setText(houseNumber+" "+landmark+" "+area+" "+pinCode+" "+city+" "+state);
        holder.txtName.setText(modelClass.getAlternateName());
        holder.txtNumber.setText(mobile);

        holder.btSetAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addID = modelClass.getAddressId();
                Intent intent = new Intent(context, CartActivity.class);
                String add = houseNumber+" "+landmark+" "+area+" "+pinCode+" "+city+" "+state;
                context.startActivity(intent);
            }
        });;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAddres, txtNumber;
        Button btSetAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtAddres = itemView.findViewById(R.id.txtAddres);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            btSetAddress = itemView.findViewById(R.id.btSetAddress);
        }
    }
    public interface GetChooseAddress {
        void addres(String addressID);
    }
}
