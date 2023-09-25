package consulting.gigs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView productImageView;
    private TextView productNameTextView;
    private TextView productPriceTextView;
    // private TextView productQuantityTextView;
    private Button addToCartButton;
    private Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Inicialización de vistas
        productImageView = findViewById(R.id.detail_product_image);
        productNameTextView = findViewById(R.id.detail_product_name);
        productPriceTextView = findViewById(R.id.detail_product_price);
        // productQuantityTextView = findViewById(R.id.detail_product_quantity);
        addToCartButton = findViewById(R.id.detail_product_add_to_cart);

        // Recuperar producto del Intent
        currentProduct = (Product) getIntent().getSerializableExtra("product");

        // Configura las vistas con datos del producto
        productImageView.setImageResource(currentProduct.getImageResource());
        productNameTextView.setText(currentProduct.getName());
        productPriceTextView.setText("$" + currentProduct.getPrice());
        // productQuantityTextView.setText("Quantity: " + currentProduct.getQuantity());

        // Agregar el producto al carrito cuando se presiona el botón
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartManager.getInstance(ProductDetailActivity.this).addProduct(currentProduct);
                Toast.makeText(ProductDetailActivity.this, "Product added to cart!", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }
}