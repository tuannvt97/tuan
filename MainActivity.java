package com.example.listview_position_it;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    private EditText ma, ten, hsl;
    private RadioButton nhanvien, quanly, giamdoc;
    private Button Save;
    private ListView lwdanhsach;
    private ArrayList<NhanVien> arrayList;
    private MyArrayAdapter<NhanVien> arrayAdapter=null;
    private int vitri=0;
    private NhanVien nv = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        luu();

        arrayList = new ArrayList<NhanVien>();
        arrayAdapter = new MyArrayAdapter<>(this,R.layout.custom_item_layout, arrayList);
        lwdanhsach.setAdapter(arrayAdapter);

        lwdanhsach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vitri=position;
                senddata();
            }
        });
        registerForContextMenu(lwdanhsach);
        lwdanhsach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                vitri=position;
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_demo, menu);
        Menu item1 = (Menu) menu.findItem(R.id.menuSearch);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSearch:
                search();
                break;
            case R.id.menuSetting:
                setting();
                break;
            case R.id.menuDelete:
                arrayList.remove(nv);
                arrayAdapter.notifyDataSetChanged();
            case R.id.menuExit:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setting() {
        Toast.makeText(this, "what the help", Toast.LENGTH_SHORT).show();
    }
    private void search() {
        Toast.makeText(this, "chua lam gi", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            switch (resultCode){
                case 111:
                     nv = (NhanVien) data.getSerializableExtra("dataResult");
                    arrayList.set(vitri, nv);
                    arrayAdapter.notifyDataSetChanged();
            }
        }
    }
    private void senddata() {
        Intent it = new Intent(MainActivity.this, DetailActivity.class);
        String ma = arrayList.get(vitri).getMa();
        String ten = arrayList.get(vitri).getTen();
        String chucvu = arrayList.get(vitri).getChucvu();
        int heSL = arrayList.get(vitri).getLuong();
        nv = new NhanVien(ma, ten, chucvu, heSL);
        Bundle bundle = new Bundle();
        bundle.putSerializable("doituong",nv);
        it.putExtra("data", bundle);
        startActivityForResult(it, 100);
    }
    private void luu() {
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = ma.getText().toString();
                String name = ten.getText().toString();
                String hesoluong = hsl.getText().toString();
                Integer luong =  0 ;
                String chucvu =null;
                if(nhanvien.isChecked()){
                    chucvu="Employees";
                    luong = 500*Integer.parseInt(hesoluong);
                }
                if(quanly.isChecked()){
                    chucvu="Manager";
                    luong = 800*Integer.parseInt(hesoluong);
                }
                if(giamdoc.isChecked()){
                    chucvu = "CEO";
                    luong = 1500*Integer.parseInt(hesoluong);
                }
                 nv = new NhanVien(code,name,chucvu,luong);
                if(code.equals("") && name.equals("") && hesoluong.equals("")){
                    Toast.makeText(MainActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    arrayList.add(nv);
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    private void anhxa() {
        ma= (EditText) findViewById(R.id.edtma);
        ten= (EditText) findViewById(R.id.edtten);
        hsl= (EditText) findViewById(R.id.edthsl);
        nhanvien= (RadioButton) findViewById(R.id.rdoEmployees);
        quanly= (RadioButton) findViewById(R.id.rdoManager);
        giamdoc = (RadioButton) findViewById(R.id.rdoCeo);
        Save= (Button) findViewById(R.id.btnSave);
        lwdanhsach = (ListView) findViewById(R.id.lw);
    }
}
