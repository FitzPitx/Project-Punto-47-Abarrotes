package consulting.gigs.entities;

// Define las entidades y las relaciones.
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class OrderEntity {
    @PrimaryKey(autoGenerate = true)
    public long orderId;

    public String address;
    public double total;
    public int status = 1; // 1 por defecto, 0 cuando se elimina
}
