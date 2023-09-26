package consulting.gigs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import consulting.gigs.R;
import consulting.gigs.entities.OrderEntity;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private List<OrderEntity> orders = new ArrayList<>();
    private OnOrderClickListener listener;

    public OrdersAdapter(OnOrderClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderEntity order = orders.get(position);

        if (holder.tvOrderTitle != null) {
            holder.tvOrderTitle.setText(String.format(Locale.getDefault(), "Orden %d", position + 1));
        }
        if (holder.tvAddress != null) {
            holder.tvAddress.setText(order.address);
        }
        if (holder.tvTotal != null) {
            holder.tvTotal.setText(String.format(Locale.getDefault(), "%.2f", order.total));
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderTitle, tvAddress, tvTotal;

        public OrderViewHolder(@NonNull View itemView, OnOrderClickListener listener) {
            super(itemView);

            tvOrderTitle = itemView.findViewById(R.id.tvOrderTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvTotal = itemView.findViewById(R.id.tvTotal);

            itemView.setOnClickListener(v -> listener.onOrderClick(getAdapterPosition()));
        }
    }

    public interface OnOrderClickListener {
        void onOrderClick(int position);
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }
}
