package consulting.gigs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private DeleteOrderCallback deleteOrderCallback;

    public OrdersAdapter(OnOrderClickListener listener, DeleteOrderCallback deleteOrderCallback) {
        this.listener = listener;
        this.deleteOrderCallback = deleteOrderCallback;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view, listener, deleteOrderCallback);
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
        Button btnDelete;  // El botón de eliminar

        public OrderViewHolder(@NonNull View itemView, OnOrderClickListener listener, DeleteOrderCallback deleteOrderCallback) {
            super(itemView);

            tvOrderTitle = itemView.findViewById(R.id.tvOrderTitle);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            itemView.setOnClickListener(v -> listener.onOrderClick(getAdapterPosition()));

            // Configura el escucha para el botón de eliminar
            btnDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    deleteOrderCallback.onDeleteOrder(position);
                }
            });
        }
    }

    public interface OnOrderClickListener {
        void onOrderClick(int position);
    }

    public interface DeleteOrderCallback {
        void onDeleteOrder(int position);
    }

    public List<OrderEntity> getOrders() {
        return orders;
    }
}
