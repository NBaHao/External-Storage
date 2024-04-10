package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Lưu dữ liệu vào External Storage

        // Kiểm tra xem External Storage có sẵn không
        if (!isExternalStorageWritable()) {
            // Xử lý nếu External Storage không có sẵn
            return;
        }

        try {
            File file = new File(this.getExternalFilesDir(null), "myFile.txt");
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write("Hello, External Storage!");
            osw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Đọc dữ liệu từ External Storage
        StringBuilder data = new StringBuilder();

        try {
            File file = new File(this.getExternalFilesDir(null), "myFile.txt");
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                data.append(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Hiển thị dữ liệu đã đọc
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }
    public  boolean isExternalStorageWritable() {
        String state = android.os.Environment.getExternalStorageState();
        return android.os.Environment.MEDIA_MOUNTED.equals(state);
    }
}