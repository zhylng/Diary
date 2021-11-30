package zut.edu.cn.notepad;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBService extends SQLiteOpenHelper {//定义数据库属性
    public static final String Diary = "notes";
    public static final String ID = "_id";
    public static final String TITLE ="title";
    public static final String WRITER ="writer";
    public static final String CONTENT = "content";
    public static final String TIME = "time";
    public DBService(Context context) {
        super(context,"diary.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//创建数据库
        String sql = "CREATE TABLE "+Diary+"( "+ID+
                " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TITLE +" VARCHAR(30) ,"+
                WRITER +" VARCHAR(20) DEFAULT 'administrator',"+
                CONTENT + " TEXT , "+
                TIME + " DATETIME NOT NULL )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//对数据库进行修改

    }

    public Cursor findById(Context context,Long id) {//数据库查询
        SQLiteDatabase db = new DBService(context).getReadableDatabase();
        return db.rawQuery("select * from " + DBService.Diary + " where _id =?", new String[]{id + ""});
    }
}
