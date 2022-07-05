package ice.ccylice.creative.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ccylice")
public class QRcode {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "ordinal")
    public int ordinal;

    @ColumnInfo(name = "type")
    public int type;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "pic_type")
    public String pic_type;

    public QRcode(int id,String text,int ordinal,int type,String title,String pic_type){
        this.id = id;
        this.text = text;
        this.ordinal = ordinal;
        this.type = type;
        this.title = title;
        this.pic_type = pic_type;
    }

    @Ignore
    public QRcode(String text,int ordinal,int type,String title,String pic_type){
        this.text = text;
        this.ordinal = ordinal;
        this.type = type;
        this.title = title;
        this.pic_type = pic_type;
    }
}
