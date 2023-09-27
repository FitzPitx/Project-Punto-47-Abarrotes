package consulting.gigs.dao;

// Crea el Data Access Object (DAO) que define las operaciones CRUD para las entidades.

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import consulting.gigs.entities.OrderEntity;
import consulting.gigs.entities.ProductOrderEntity;

@Dao
public interface ShopDao {

    @Insert
    long insertOrder(OrderEntity order);

    @Insert
    void insertOrderProducts(ProductOrderEntity... productOrders);

    @Transaction
    @Query("SELECT * FROM orders WHERE orderId = :orderId")
    LiveData<OrderEntity> loadOrderDetails(long orderId);

    @Query("SELECT * FROM orders")
    LiveData<List<OrderEntity>> loadAllOrders();

    @Query("SELECT * FROM order_products WHERE orderId = :orderId")
    LiveData<List<ProductOrderEntity>> loadProductsForOrder(long orderId);


    // Más métodos si son necesarios
}

