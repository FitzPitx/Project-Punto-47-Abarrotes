package consulting.gigs.adapter;

import android.content.res.Resources;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    private OnQuantityChangeListener quantityChangeListener;

    public interface OnRemoveItemClickListener {
        void onRemoveItemClick(int position);
    }

    public interface OnQuantityChangeListener {
        void onQuantityChange();
    }

    public CartAdapter(List<Product> products, OnRemoveItemClickListener removeListener, OnQuantityChangeListener quantityChangeListener) {
        this.products = products;
        this.removeItemClickListener = removeListener;
        this.quantityChangeListener = quantityChangeListener;
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

        // Calculate the total price based on quantity and set to productPriceTextView
        double totalPrice = product.getPrice() * product.getQuantity();
        holder.productPriceTextView.setText("$" + totalPrice);

        try {
            holder.productImageView.setImageResource(product.getImageResource());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            // Puedes configurar una imagen predeterminada en caso de error
            holder.productImageView.setImageResource(R.drawable.boleto);
        }

        holder.productQuantityEditText.setText(String.valueOf(product.getQuantity()));

        holder.productQuantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int quantity = Integer.parseInt(s.toString());
                    product.setQuantity(quantity);

                    // Update productPriceTextView with the new total price after quantity change
                    double updatedPrice = product.getPrice() * product.getQuantity();
                    holder.productPriceTextView.setText("$" + updatedPrice);

                    if (quantityChangeListener != null) {
                        quantityChangeListener.onQuantityChange();
                    }
                } catch (NumberFormatException e) {
                    // El EditText está vacío o contiene un valor no válido
                }
            }
        });

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
        EditText productQuantityEditText;
        ImageButton removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.cart_item_name);
            productPriceTextView = itemView.findViewById(R.id.cart_item_price);
            productImageView = itemView.findViewById(R.id.cart_item_image);
            productQuantityEditText = itemView.findViewById(R.id.cart_item_quantity_edittext);
            removeButton = itemView.findViewById(R.id.cart_item_remove);
        }
    }
}
