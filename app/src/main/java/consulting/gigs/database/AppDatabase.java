package consulting.gigs.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import consulting.gigs.dao.ShopDao;
import consulting.gigs.entities.OrderEntity;
import consulting.gigs.entities.ProductOrderEntity;

@Database(entities = {OrderEntity.class, ProductOrderEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ShopDao shopDao();
}

