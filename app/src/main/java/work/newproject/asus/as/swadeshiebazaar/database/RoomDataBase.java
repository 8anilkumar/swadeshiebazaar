package work.newproject.asus.as.swadeshiebazaar.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CartTable.class,wishListTable.class}, version = 3, exportSchema = false)

public abstract class RoomDataBase extends RoomDatabase {
    private static RoomDataBase roomDB;
    private static String DATABASE_NAME = "database";

    public synchronized static RoomDataBase getInstance(Context context) {
        if (roomDB == null) {
            roomDB = Room.databaseBuilder(context.getApplicationContext(), RoomDataBase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return roomDB;
    }

    public abstract MainDuo mainDuo();
}
