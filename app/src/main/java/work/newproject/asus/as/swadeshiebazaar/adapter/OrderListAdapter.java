package work.newproject.asus.as.swadeshiebazaar.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import work.newproject.asus.as.swadeshiebazaar.InvoiceActivity;
import work.newproject.asus.as.swadeshiebazaar.ModelClass.CancelOrder;
import work.newproject.asus.as.swadeshiebazaar.OrderDeatilsActivity;
import work.newproject.asus.as.swadeshiebazaar.R;
import work.newproject.asus.as.swadeshiebazaar.fragment.MyOrderListFragment;
import work.newproject.asus.as.swadeshiebazaar.network.Api;
import work.newproject.asus.as.swadeshiebazaar.network.ApiClints;
import work.newproject.asus.as.swadeshiebazaar.network.model_res.OrderModel;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder>  {
    Api api = ApiClints.getClient().create(Api.class);
    List<OrderModel.Datum> list;
    Context context;
    MyOrderListFragment fragment;

    public OrderListAdapter(List<OrderModel.Datum> list, Context context, MyOrderListFragment myOrderListFragment) {
        this.list = list;
        this.context = context;
        this.fragment = myOrderListFragment;
    }

    @NonNull
    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list, parent, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderListAdapter.ViewHolder holder, int position) {
        OrderModel.Datum datum = list.get(position);
        holder.constLayout.setVisibility(View.VISIBLE);
        holder.txtOrderId.setText("Order ID : "+datum.getOrderId());
        holder.txtSlotTime.setText(datum.getDate()); 
        holder.txtItemRate.setText("₹" + datum.getTotalPrice());

       /* String status = datum.getStatusDelivery();
        if (status.equals("pending")) {
            holder.txtStatus.setText(status);
            holder.txtStatus.setTextColor(context.getResources().getColor(R.color.white));
            holder.txtStatus.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        } else if (status.equals("cancel")) {
            holder.txtStatus.setText(status);
            holder.txtStatus.setTextColor(context.getResources().getColor(R.color.white));
            holder.txtStatus.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }else if (status.equals("delivery")){
            holder.txtStatus.setText(status);
            holder.txtStatus.setTextColor(context.getResources().getColor(R.color.white));
        }*/

        holder.constLayout.setOnClickListener(v -> goOrderDeatails(datum.getOrderId(),datum.getOrderId()));

        holder.txtStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.getCancelOrder( datum.getOrderId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<CancelOrder>() {
                            @Override
                            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                            }

                            @Override
                            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull CancelOrder cancelOrder) {
                                if (cancelOrder.getStatus().equals("success")) {
                                    fragment.getOrderlist();

                                }
                            }

                            @Override
                            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {


                      }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemRate, txtSlotTime, txtOrderId, txtStatus;
        ConstraintLayout constLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItemRate = itemView.findViewById(R.id.txtItemRate);
            txtSlotTime = itemView.findViewById(R.id.txtSlotTime);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            constLayout=itemView.findViewById(R.id.constLayout);
        }
    }

    private void goOrderDeatails(String id,String showid) {
        Intent intent = new Intent(context, OrderDeatilsActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("showId",showid);
        context.startActivity(intent);
    }

}
