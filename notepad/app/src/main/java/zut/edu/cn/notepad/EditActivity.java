package zut.edu.cn.notepad;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    //定义保存界面按钮
    DBService myDb;
    private Button btnCancel;
    private Button btnSave;
    private EditText titleEditText;
    private EditText writerEditText;
    private EditText contentEditText;
    private TextView timeTextView;

    @Override//进入写日记界面
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_editor);
        //时间
        init();
        if(timeTextView.getText().length()==0)
            timeTextView.setText(getTime());
    }

    private void init() {

        //关联按钮
        myDb = new DBService(this);
        SQLiteDatabase db = myDb.getReadableDatabase();
        titleEditText = findViewById(R.id.et_title);
        writerEditText = findViewById(R.id.et_writer);
        contentEditText = findViewById(R.id.et_content);
        timeTextView = findViewById(R.id.edit_time);
        btnCancel = findViewById(R.id.btn_cancel);
        btnSave = findViewById(R.id.btn_save);

        //按钮点击事件
        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开数据库
                SQLiteDatabase db = myDb.getWritableDatabase();
                ContentValues values = new ContentValues();
                //定义字符串获取输入框的值
                String title= titleEditText.getText().toString();
                String writer= writerEditText.getText().toString();
                String content=contentEditText.getText().toString();
                String time= timeTextView.getText().toString();
                //对输入框内容进行判断
                if("".equals(titleEditText.getText().toString())){
                    Toast.makeText(EditActivity.this,"标题不能为空",Toast.LENGTH_LONG).show();
                    return;
                }
                if("".equals(contentEditText.getText().toString())) {
                    Toast.makeText(EditActivity.this,"内容不能为空",Toast.LENGTH_LONG).show();
                    return;
                }

                if("".equals(writerEditText.getText().toString())){//判断是否输入作者
                    //给数据库赋值
                    values.put(DBService.TITLE,title);
                    values.put(DBService.WRITER,"administrator");
                    values.put(DBService.CONTENT,content);
                    values.put(DBService.TIME,time);
                    db.insert(DBService.Diary,null,values);
                    Toast.makeText(EditActivity.this,"保存成功",Toast.LENGTH_LONG).show();
                }else{
                    values.put(DBService.TITLE,title);
                    values.put(DBService.WRITER,writer);
                    values.put(DBService.CONTENT,content);
                    values.put(DBService.TIME,time);
                    db.insert(DBService.Diary,null,values);
                    Toast.makeText(EditActivity.this,"保存成功",Toast.LENGTH_LONG).show();
                }

                //返回主页面MainActivity
                Intent intent = new Intent(EditActivity.this,MainActivity.class);
                startActivity(intent);
                //关闭数据库
                db.close();
            }
        });
    }

    //获取当前时间
    private String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String str = sdf.format(date);
        return str;
    }

}
