package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    List<CartTable> list;
    RoomDataBase db;
    int qty;
    RefreshList refreshList;
    List<wishListTable> wishList;

    public CartAdapter(Context context, List<CartTable> list) {
        this.context = context;
        this.list = list;
        this.db = RoomDataBase.getInstance(context);
        this.refreshList = (RefreshList) context;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_list, parent, false);
        wishList = db.mainDuo().getWishList();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        CartTable table = list.get(position);
        holder.txtName.setText(table.getName());
        Glide.with(context).load(AppStrings.cat_path + table.getImage()).into(holder.imgLogo);

        // holder.txtDeatils.setText("Weight : "+table.get());
        holder.number_qty.setNumber(table.getQty());
        holder.txtAmount.setText("₹ " + table.getPrice());

        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {


                                                    try {
                                                        //wishlistUpdate
                                                        for (int k = 0; k < wishList.size(); k++) {
                                                            if (wishList.get(k).getProductID() == table.getProductID()) {
                                                                db.mainDuo().deleteByProductIDWishList(table.getProductID());
                                                                break;
                                                            }
                                                        }

                                                    } catch (Exception e) {
                                                        e.getMessage();
                                                    }

                                                    try {
                                                        CartTable table = list.get(position);
                                                        db.mainDuo().deleteItem(table);
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

        holder.number_qty.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                if (newValue != 0) {

                    Log.d("TAG", "onValueChange: " + table.getActualPrice());
                    qty = newValue;
                    float value = Float.parseFloat(table.getActualPrice());
                    float total = newValue * value;
                    String finamlValue = String.valueOf(total);
                    Log.d("TAG", "onValueChange: " + finamlValue);
                    db.mainDuo().update(table.getProductID(), String.valueOf(qty), finamlValue);

                    try {
                        //wishlistUpdate
                        for (int k = 0; k < wishList.size(); k++) {
                            if (wishList.get(k).getProductID() == table.getProductID()) {
                                db.mainDuo().updateWishList(table.getProductID(), String.valueOf(qty), finamlValue);
                                break;
                            }
                        }

                    } catch (Exception e) {
                        e.getMessage();
                    }

                    holder.txtAmount.setText("₹ " + finamlValue);
                    list.clear();
                    list.addAll(db.mainDuo().getCartList());
                    notifyDataSetChanged();
                    refreshList.update();


                } else {
                    try {
                        try {
                            //wishlistUpdate
                            db.mainDuo().updateWishList(table.getProductID(), "0", table.getActualPrice());
                        } catch (Exception e) {
                            e.getMessage();
                        }
                        db.mainDuo().deleteByProductID(table.getProductID());
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.imgLogo);
            txtName = itemView.findViewById(R.id.txtName);
            number_qty = itemView.findViewById(R.id.number_qty);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            imgRemove = itemView.findViewById(R.id.imgRemove);
            txtDeatils = itemView.findViewById(R.id.txtDeatils);
        }
    }

    public interface RefreshList {
        void update();
    }
}
