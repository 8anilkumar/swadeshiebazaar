package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.List;

import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.database.CartTable;
import work.newproject.asus.as.swadeshiebazaar.database.RoomDataBase;
import work.newproject.asus.as.swadeshiebazaar.database.wishListTable;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

    Context context;
    List<wishListTable> list;
    List<CartTable> cartlist;
    RoomDataBase db;
    int qty;
    RefreshList refreshList;
    String tagCheck = "1";


    public WishListAdapter(Context context, List<wishListTable> list, RefreshList refreshList) {
        this.context = context;
        this.list = list;
        this.refreshList = refreshList;
        this.db = RoomDataBase.getInstance(context);
    }

    @NonNull
    @Override
    public WishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wish_list, parent, false);
        cartlist = db.mainDuo().getCartList();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.ViewHolder holder, int position) {
        wishListTable table = list.get(position);
        holder.txtName.setText(table.getName());
        holder.number_qty.setNumber(table.getQty());

        if (table.getQty().equalsIgnoreCase("0")) {
            holder.btAdd.setVisibility(View.VISIBLE);
            holder.number_qty.setVisibility(View.INVISIBLE);
            holder.txtAmount.setText("₹ " + table.getActualPrice());

        } else {
            holder.number_qty.setVisibility(View.VISIBLE);
            holder.btAdd.setVisibility(View.INVISIBLE);
            holder.txtAmount.setText("₹ " + table.getPrice());


        }
        Glide.with(context).load(AppStrings.cat_path + table.getImage()).into(holder.imgLogo);


        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    try {
                                                        wishListTable table = list.get(position);
                                                        db.mainDuo().deleteWishList(table);
                                                        int poss = holder.getAdapterPosition();
                                                        list.remove(poss);
                                                        notifyItemRemoved(poss);
                                                        notifyItemRangeChanged(poss, list.size());
                                                        refreshList.update();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
        );

        holder.btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.number_qty.setNumber("1");
                qty = 1;
                float value = Float.parseFloat(table.getActualPrice());
                float total = 1 * value;
                String finamlValue = String.valueOf(total);

                try {

                    for (int i = 0; i < cartlist.size(); i++) {
                        if (cartlist.get(i).getProductID() == table.getProductID()) {
                            Log.d("TAG", "onValueChange: " + table.getActualPrice());
                            db.mainDuo().update(table.getProductID(), String.valueOf(qty), finamlValue);
                            tagCheck = "2";
                            holder.btAdd.setVisibility(View.INVISIBLE);
                            holder.number_qty.setVisibility(View.VISIBLE);
                            break;
                        }
                    }


                    if (tagCheck.equalsIgnoreCase("1")) {
                        CartTable dataBase = new CartTable();
                        dataBase.setName(table.getName());
                        String q = String.valueOf(qty);
                        dataBase.setQty(q);
                        dataBase.setImage(table.getImage());
                        dataBase.setType("qty");
                        dataBase.setPrice(finamlValue);
                        dataBase.setProductID(table.getProductID());
                        Log.d("TAG", "onClick: " + table.getActualPrice());
                        dataBase.setActualPrice(table.getActualPrice());
                        db.mainDuo().insertCartTable(dataBase);
                        holder.btAdd.setVisibility(View.INVISIBLE);
                        holder.number_qty.setVisibility(View.VISIBLE);
                    } else {
                        db.mainDuo().updateWishList(table.getProductID(), String.valueOf(qty), finamlValue);
                        holder.txtAmount.setText("₹ " + finamlValue);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("TAG", "onClick: " + e.getMessage());
                }
            }
        });


        holder.number_qty.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {

                Log.d("TAG", "onValueChange: " + newValue);

                if (newValue != 0) {

                    //wishlist

                    qty = newValue;
                    float value = Float.parseFloat(table.getActualPrice());
                    float total = newValue * value;
                    String finamlValue = String.valueOf(total);

                    Log.d("TAG", "onValueChange: " + table.getProductID());
                    db.mainDuo().updateWishList(table.getProductID(), String.valueOf(newValue), finamlValue);
                    holder.txtAmount.setText("₹ " + finamlValue);

                    /* *//*    list.clear();
                    list.addAll(db.mainDuo().getWishList());
                    *//*    notifyDataSetChanged();
                    refreshList.update();*/
                    //cardlist

                    Log.d("TAG", "onValueChange: " + table.getActualPrice());
                    db.mainDuo().update(table.getProductID(), String.valueOf(qty), finamlValue);


                } else {
                    try {

                        //cart_list
                        db.mainDuo().deleteByProductID(table.getProductID());
                        int poss = holder.getAdapterPosition();
                        // list.remove(poss);


                        //wish_list

                        db.mainDuo().deleteByProductIDWishList(table.getProductID());
                        list.remove(poss);
                        notifyItemRemoved(poss);
                        notifyItemRangeChanged(poss, list.size());
                        refreshList.update();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo, imgRemove;
        TextView txtName, txtAmount, txtDeatils;
        ElegantNumberButton number_qty;
        Button btAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            txtName = itemView.findViewById(R.id.txtName);
            number_qty = itemView.findViewById(R.id.number_qty);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            imgRemove = itemView.findViewById(R.id.imgRemove);
            txtDeatils = itemView.findViewById(R.id.txtDeatils);
            btAdd = itemView.findViewById(R.id.btAdd);
        }
    }

    public interface RefreshList {
        void update();
    }
}
