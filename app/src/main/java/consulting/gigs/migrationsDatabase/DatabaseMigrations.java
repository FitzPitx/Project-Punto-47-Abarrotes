package consulting.gigs.migrationsDatabase;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseMigrations {
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE orders ADD COLUMN status INTEGER DEFAULT 1 NOT NULL");
        }
    };

    // Aquí puedes agregar más migraciones en el futuro
}

