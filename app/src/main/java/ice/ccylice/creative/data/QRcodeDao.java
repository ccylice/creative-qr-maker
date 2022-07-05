package ice.ccylice.creative.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QRcodeDao {

    @Query("SELECT * FROM ccylice")
    List<QRcode> getAll();

    @Insert
    void insertQrcode(QRcode qRcode);

    @Delete
    void deleteQrcode(QRcode qRcode);




}
