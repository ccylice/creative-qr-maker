package ice.ccylice.creative.data;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {QRcode.class}, version = 1,exportSchema = false)
public abstract class Database extends RoomDatabase {
    public static final String DATABASE_NAME = "qr_db";
    private static Database db;
    public static synchronized Database getInstance(Context context){
        if (db == null){
            db = Room.databaseBuilder(context.getApplicationContext(),Database.class,
                    DATABASE_NAME)
                    .build();
        }
        return db;
    }
    public abstract QRcodeDao qRcodeDao();
}
