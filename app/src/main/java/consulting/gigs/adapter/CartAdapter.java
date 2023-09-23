package consulting.gigs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import consulting.gigs.Product;
import consulting.gigs.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Product> products;
    private OnRemoveItemClickListener removeItemClickListener;

    public interface OnRemoveItemClickListener {
        void onRemoveItemClick(int position);
    }

    public CartAdapter(List<Product> products, OnRemoveItemClickListener listener) {
        this.products = products;
        this.removeItemClickListener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = products.get(position);

        // Configura las vistas del elemento del carrito con los datos del producto
        holder.productNameTextView.setText(product.getName());
        holder.productPriceTextView.setText("Price: $" + product.getPrice());
        holder.productImageView.setImageResource(product.getImageResource());
        holder.productQuantityTextView.setText("Quantity: " + product.getQuantity());

        // Agrega un listener al botón de eliminar
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llama al método de eliminación del producto
                if (removeItemClickListener != null) {
                    removeItemClickListener.onRemoveItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;
        TextView productPriceTextView;
        ImageView productImageView;
        TextView productQuantityTextView;
        ImageButton removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.cart_item_name);
            productPriceTextView = itemView.findViewById(R.id.cart_item_price);
            productImageView = itemView.findViewById(R.id.cart_item_image);
            productQuantityTextView = itemView.findViewById(R.id.cart_item_quantity);
            removeButton = itemView.findViewById(R.id.cart_item_remove);
        }
    }
}
