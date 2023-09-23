package consulting.gigs;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/*
Esta es una clase para manejar el carrito de compras (agregar productos, obtener productos, guardar/recuperar de SharedPreferences
 */
public class CartManager {
    private static CartManager instance;
    private List<Product> products;
    private Context context;

    private static final String CART_PREFERENCES = "cartPreferences";
    private static final String CART_KEY = "cartKey";

    private CartManager(Context context) {
        this.context = context.getApplicationContext();
        products = new ArrayList<>();
        loadCartFromPreferences();
    }

    public static CartManager getInstance(Context context) {
        if (instance == null) {
            instance = new CartManager(context);
        }
        return instance;
    }

    public void addProduct(Product product) {
        products.add(product);
        saveCartToPreferences();
    }

    private void saveCartToPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String jsonCart = gson.toJson(products);
        editor.putString(CART_KEY, jsonCart);
        editor.apply();
    }

    private void loadCartFromPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFERENCES, Context.MODE_PRIVATE);
        String jsonCart = sharedPreferences.getString(CART_KEY, "");

        if (!jsonCart.isEmpty()) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Product>>() {}.getType();
            products = gson.fromJson(jsonCart, type);
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void removeProduct(Product product) {
        products.remove(product);
        saveCartToPreferences();
    }
}
