package consulting.gigs.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

// Esta entidad funcionará como una "tabla de unión" entre órdenes y productos.

@Entity(tableName = "order_products",
        foreignKeys = {
                @ForeignKey(entity = OrderEntity.class,
                        parentColumns = "orderId",
                        childColumns = "orderId",
                        onDelete = ForeignKey.CASCADE)
        })
public class ProductOrderEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long orderId;

    public String productName;
    public double productPrice;
    public int productQuantity;
}

