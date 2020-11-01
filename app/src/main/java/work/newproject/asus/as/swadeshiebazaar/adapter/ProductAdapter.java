package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.List;

import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.database.CartTable;
import work.newproject.asus.as.swadeshiebazaar.database.RoomDataBase;
import work.newproject.asus.as.swadeshiebazaar.fragment.DashBoardFragment;
import work.newproject.asus.as.swadeshiebazaar.fragment.ProductDiscriptionFragment;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.home_data_model;
import work.newproject.asus.as.swadeshiebazaar.utils.AppStrings;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<home_data_model.LayoutDatum> list;
    Context context;
    DashBoardFragment fragment;
    int qty;
    RoomDataBase db;
    List<CartTable> cartlist;


    public ProductAdapter(List<home_data_model.LayoutDatum> list, Context context, DashBoardFragment fragment) {
        this.list = list;
        this.context = context;
        this.fragment = fragment;
        this.db = RoomDataBase.getInstance(context);
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_product, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        home_data_model.LayoutDatum layoutDatum = list.get(position);
        layoutDatum.setWishList(false);
        holder.txtDiscount.setText("₹ "+layoutDatum.getPrice());
        strikeThroughText(holder.txtDiscount);
        holder.txtSave.setText("Off "+layoutDatum.getDiscount());
        holder.txtProoductName.setText(layoutDatum.getName());



        /*if (Integer.parseInt(layoutDatum.getRemainStock()) > 1) {
            holder.txtStock.setVisibility(View.VISIBLE);
            holder.txtStock.setText(context.getString(R.string.available_in_stock));

        }else {
            holder.btAdd.setVisibility(View.INVISIBLE);
            holder.number_picker.setVisibility(View.INVISIBLE);
        }*/


        holder.txtActualAmount.setText("₹ " + layoutDatum.getOfferPrice());
        holder.ivWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!layoutDatum.getWishList()) {
                    holder.ivWishList.setImageResource(R.drawable.ic_baseline_favorite_24);
                    layoutDatum.setWishList(true);
                    fragment.getData(layoutDatum.getName(), Long.parseLong(layoutDatum.getId()),layoutDatum.getOfferPrice(),layoutDatum.getImage());
                } else {
                    layoutDatum.setWishList(false);
                    holder.ivWishList.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                 fragment.remove(Long.parseLong(layoutDatum.getId()));
                }

            }
        });


        holder.btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        /*        holder.btAdd.setVisibility(View.INVISIBLE);
                holder.number_picker.setVisibility(View.VISIBLE);
                holder.number_picker.setNumber("1");
                qty = 1;
                float value = Float.parseFloat(layoutDatum.getOfferPrice());
                float total = 1 * value;
                String finamlValue = String.valueOf(total);
                CartTable dataBase = new CartTable();
                dataBase.setName(layoutDatum.getName());
                String q = String.valueOf(qty);
                dataBase.setQty(q);
                dataBase.setImage(layoutDatum.getImage());
                dataBase.setType("qty");
                dataBase.setPrice(finamlValue);
                dataBase.setProductID(Long.parseLong(layoutDatum.getId()));
                dataBase.setActualPrice(layoutDatum.getOfferPrice());
                db.mainDuo().insertCartTable(dataBase);*/



                cartlist=db.mainDuo().getCartList();
                long id= Long.parseLong(layoutDatum.getId());
                fragment.refresh();
                Log.d("TAG", "onClick: "+"1");
              if (cartlist.isEmpty()){
                  holder.btAdd.setVisibility(View.INVISIBLE);
                  holder.number_picker.setVisibility(View.VISIBLE);
                  holder.number_picker.setNumber("1");

                  qty = 1;
                  float value = Float.parseFloat(layoutDatum.getOfferPrice());
                  float total = 1 * value;
                  String finamlValue = String.valueOf(total);
                  CartTable dataBase = new CartTable();
                  dataBase.setName(layoutDatum.getName());
                  String q = String.valueOf(qty);
                  dataBase.setQty(q);
                  dataBase.setImage(layoutDatum.getImage());
                  dataBase.setType("qty");
                  dataBase.setPrice(finamlValue);
                  dataBase.setProductID(Long.parseLong(layoutDatum.getId()));
                  dataBase.setActualPrice(layoutDatum.getOfferPrice());
                  db.mainDuo().insertCartTable(dataBase);
                  fragment.refresh();
              }else {
                  for (int i=0;i<cartlist.size();i++){

                      if (cartlist.get(i).getProductID() == id){
                          holder.btAdd.setVisibility(View.INVISIBLE);
                          holder.number_picker.setVisibility(View.VISIBLE);
                          holder.number_picker.setNumber("1");
                          qty = 1;
                          float value = Float.parseFloat(layoutDatum.getOfferPrice());
                          float total = 1 * value;
                          String finamlValue = String.valueOf(total);
                          db.mainDuo().update(Long.parseLong(layoutDatum.getId()), String.valueOf(qty), finamlValue);
                          Log.d("TAG", "onClick: "+"2");
                          fragment.refresh();
                          break;
                      }else {

                          Log.d("TAG", "onClick: "+"3");
                          holder.btAdd.setVisibility(View.INVISIBLE);
                          holder.number_picker.setVisibility(View.VISIBLE);
                          holder.number_picker.setNumber("1");

                          qty = 1;
                          float value = Float.parseFloat(layoutDatum.getOfferPrice());
                          float total = 1 * value;
                          String finamlValue = String.valueOf(total);
                          CartTable dataBase = new CartTable();
                          dataBase.setName(layoutDatum.getName());
                          String q = String.valueOf(qty);
                          dataBase.setQty(q);
                          dataBase.setImage(layoutDatum.getImage());
                          dataBase.setType("qty");
                          dataBase.setPrice(finamlValue);
                          dataBase.setProductID(Long.parseLong(layoutDatum.getId()));
                          dataBase.setActualPrice(layoutDatum.getOfferPrice());
                          db.mainDuo().insertCartTable(dataBase);
                          fragment.refresh();
                          break;
                      }
                  }
              }



            }
        });


        holder.number_picker.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                if (newValue == 0) {
                    holder.btAdd.setVisibility(View.VISIBLE);
                    holder.number_picker.setVisibility(View.INVISIBLE);
                }


                if (newValue != 0) {

                    //wishlist

                    qty = newValue;
                    float value = Float.parseFloat(layoutDatum.getOfferPrice());
                    float total = newValue * value;
                    String finamlValue = String.valueOf(total);

                    db.mainDuo().update(Long.parseLong(layoutDatum.getId()), String.valueOf(qty), finamlValue);
                    fragment.refresh();

                } else {
                    try {

                        //cart_list
                        db.mainDuo().deleteByProductID(Long.parseLong(layoutDatum.getId()));
                        int poss = holder.getAdapterPosition();
                        fragment.refresh();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



            }
        });


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("dec", layoutDatum.getShrtDescription());
                args.putString("name", layoutDatum.getName());
                args.putString("productBrand", layoutDatum.getBrandId());
                args.putString("price", layoutDatum.getPrice());
                args.putString("img1",layoutDatum.getImage());
                args.putString("qty",layoutDatum.getStock());
                args.putString("productID",layoutDatum.getId());
                Fragment fragmentt = new ProductDiscriptionFragment();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                fragmentt.setArguments(args);
                transaction.replace(R.id.fragment, fragmentt);
                transaction.addToBackStack("social").commit();
            }
        });
        Glide.with(context).load(AppStrings.cat_path + layoutDatum.getImage()).into(holder.imageView);
    }


    private void strikeThroughText(TextView price) {
        price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtProoductName, txtOffer, txtAmount, txtDiscount, txtActualAmount, txtStock,txtSave;
        Button btAdd;
        ImageView ivWishList;
        ElegantNumberButton number_picker;
        ConstraintLayout constLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivWishList = itemView.findViewById(R.id.ivWishList);
            imageView = itemView.findViewById(R.id.imgImage);
            txtProoductName = itemView.findViewById(R.id.txtProoductName);
            txtOffer = itemView.findViewById(R.id.txtOffer);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtDiscount = itemView.findViewById(R.id.txtDiscount);
            txtActualAmount = itemView.findViewById(R.id.txtActualAmount);
            constLayout=itemView.findViewById(R.id.constLayout);
            txtStock = itemView.findViewById(R.id.txtAmount);
            number_picker = itemView.findViewById(R.id.number_picker);
            btAdd = itemView.findViewById(R.id.btAdd);
            txtSave=itemView.findViewById(R.id.txtSave);
        }
    }
}
