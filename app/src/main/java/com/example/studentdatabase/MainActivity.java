package com.example.studentdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText usn,name,phone,adress,sms_usn;
    Button ins,del,upd,view,sms;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usn=findViewById(R.id.usn);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        sms_usn=findViewById(R.id.sms_usn);
        adress=findViewById(R.id.adress);
        ins=findViewById(R.id.insert);
        del=findViewById(R.id.delete);
        upd=findViewById(R.id.update);
        view=findViewById(R.id.view_all);
        sms=findViewById(R.id.sms);
        db=openOrCreateDatabase("STudentDB",MODE_PRIVATE,null);
        db.execSQL("create table if not exists student(usn varchar(10) primary key,name varchar(10),phone char(10),adress varchar(10))");
        ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u=usn.getText().toString();
                String n=name.getText().toString();
                String a=adress.getText().toString();
                String p=phone.getText().toString();
                db.execSQL("insert into student values('"+u+"','"+n+"','"+p+"','"+a+"')");
                Toast.makeText(MainActivity.this, "insertion is successful", Toast.LENGTH_SHORT).show();

            }
        });
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u=usn.getText().toString();
                db.execSQL("delete from student where usn='"+u+"'");
                Toast.makeText(MainActivity.this, "deletion successfu", Toast.LENGTH_SHORT).show();
            }
        });
        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u=usn.getText().toString();
                String p=phone.getText().toString();
                db.execSQL("update student set phone='"+p+"' where usn='"+u+"'");
                Toast.makeText(MainActivity.this, "updation is successful", Toast.LENGTH_SHORT).show();

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String all="";
                Cursor c=db.rawQuery("select * from student",null);
                if(c.getCount()>0)
                {
                    String u=c.getString(0);
                    String n=c.getString(1);
                    String p=c.getString(2);
                    String a=c.getString(3);
                    all+=u+"\n"+n+"\n"+p+"\n"+a;
                }
                else
                {
                    Toast.makeText(MainActivity.this, "no content", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u=usn.getText().toString();
                Cursor c=db.rawQuery("Select * from student where usn=?",new String[]{u+""});
                if(c.getCount()==0)
                {
                    Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    c.moveToFirst();
                    String adr=c.getString(3);
                    String pho=c.getString(2);
                    SmsManager s=SmsManager.getDefault();
                    s.sendTextMessage(pho,null,adr,null,null);
                    Toast.makeText(MainActivity.this, "message is sent", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}