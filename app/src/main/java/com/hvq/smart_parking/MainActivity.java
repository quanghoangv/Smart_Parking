package com.hvq.smart_parking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtIdNfc;
    Button btnTimKiem;
    ArrayList<ThongTinXe> xeArrayList;
    public static Database database;
    ListView lvThongTinXe;
    XeAdapter adapter;
    ThongTinXe thongTinXe;

    private TextView tvUserName;
    private ImageView imgAvatar;
    private LinearLayout navQLNV, navQLDS, navDangXuat;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();

        drawerLayout = findViewById(R.id.activity_main_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navQLNV.setVisibility(View.GONE);

        Intent intent = getIntent();
        if(intent.getStringExtra("username").equals("ninh")){
            navQLNV.setVisibility(View.VISIBLE);
        }
        navQLDS.setOnClickListener(this);
        navQLNV.setOnClickListener(this);
        navDangXuat.setOnClickListener(this);

        tvUserName.setText(getIntent().getStringExtra("username"));


        xeArrayList = new ArrayList<>();
        adapter =new XeAdapter(this, R.layout.activity_chi_tiet_xe, xeArrayList);
        lvThongTinXe.setAdapter(adapter);

        database =new Database(this,"SmartParking.sqlite",null,1);
        database.QueryData("CREATE TABLE IF NOT EXISTS ThongTinXe(Id INTEGER PRIMARY KEY AUTOINCREMENT, IdNfc VARCHAR(150), GioVao VARCHAR(250), HinhAnh BLOB, GioRa VARCHAR(250), ThanhTien VARCHAR(250))");

        //get Data
        Cursor cursor = database.GetData("SELECT * FROM ThongTinXe");
        while (cursor.moveToNext()){
            xeArrayList.add(new ThongTinXe(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getBlob(3),
                    cursor.getString(4),
                    cursor.getString(5)
            ));

        }
        adapter.notifyDataSetChanged();

        lvThongTinXe.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteThongTinXe(position);
                xeArrayList.clear();
                adapter.notifyDataSetChanged();
                return false;
            }
        });


    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteThongTinXe(int position){
        int id = xeArrayList.get(position).getId();
        String sql = "DELETE FROM ThongTinXe WHERE Id = " + id;
        database.QueryData(sql);

    }

    private void Anhxa() {
        edtIdNfc = (EditText)findViewById(R.id.edtfindWithID);
        btnTimKiem = (Button)findViewById(R.id.btnTimKiem);
        lvThongTinXe = (ListView)findViewById(R.id.lvThongTinXe);

        navQLNV = findViewById(R.id.nav_qlnv);
        navQLDS = findViewById(R.id.nav_qlds);
        navDangXuat = findViewById(R.id.nav_dangxuat);

        tvUserName = findViewById(R.id.tvUserName);
    }
//    public void onNewIntent(Intent intent) {
//        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
//        intent = new Intent(this, Main2Activity.class);
//        startActivity(intent);
//        Log.d("NfcTag", "bhihiiiiiiiiiiiiiiiiiiiii");
//    }


    //Phần NFC
    private final String[][] techList = new String[][] {
            new String[] {
                    NfcA.class.getName(),
                    NfcB.class.getName(),
                    NfcF.class.getName(),
                    NfcV.class.getName(),
                    IsoDep.class.getName(),
                    MifareClassic.class.getName(),
                    MifareUltralight.class.getName(), Ndef.class.getName()
            }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onResume() {
        super.onResume();
        // creating pending intent:
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // creating intent receiver for NFC events:
        IntentFilter filter = new IntentFilter();
        filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        // enabling foreground dispatch for getting intent from NFC event:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{filter}, this.techList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // disabling foreground dispatch:
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.disableForegroundDispatch(this);
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
//            ((TextView)findViewById(R.id.text)).setText(
//                    "NFC Tag\n" +
//                            ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)));
//        }
//    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String IdNfcSet = ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
            Date currentTime = Calendar.getInstance().getTime();
            String GioVao = currentTime.toString();

            if(IdNfcSet != null){
                Cursor cursor = database.GetData("SELECT * FROM ThongTinXe WHERE IdNfc = '" +IdNfcSet+ "'");
                Log.e("Cursor", cursor.toString());;
                if( cursor != null && cursor.getCount()>0){
                    if (cursor.moveToFirst()) {
                        ThongTinXe thongTinXe = new ThongTinXe(
                                cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getBlob(3),
                                cursor.getString(4),
                                cursor.getString(5)
                        );
                        Intent intent2 = new Intent(this, Main3Activity.class);
                        intent2.putExtra("IdNfc", thongTinXe.getIdNfc());
                        intent2.putExtra("Gio Vao", thongTinXe.getGioVao());
                        intent2.putExtra("Hinh Anh", thongTinXe.getHinh());
                        intent2.putExtra("Gio Ra", currentTime.toString());
                        startActivity(intent2);
                        cursor.close();
                    }
                }else{
                    Intent intent1 = new Intent(this, Main2Activity.class);
                    intent1.putExtra("IdNfc", IdNfcSet);
                    intent1.putExtra("Gio Vao", GioVao);
                    intent1.putExtra("Gio Ra","");
                    startActivity(intent1);
                }
            }
        }
    }

    private String ByteArrayToHexString(byte [] inarray) {
        int i, j, in;
        String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        String out= "";

        for(j = 0 ; j < inarray.length ; ++j)
        {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nav_qlds:
                Toast.makeText(this, "QLDS", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_qlnv:
                Toast.makeText(this, "QLNV", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, Main4Activity.class));

                break;
            case R.id.nav_dangxuat:
//                Toast.makeText(this, "Dang xuat", Toast.LENGTH_SHORT).show();
                SharedPreferences myPrefs = getSharedPreferences("IS_LOGIN", MODE_PRIVATE);
                SharedPreferences.Editor editor = myPrefs.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(MainActivity.this, Activity_Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

        }
    }
}
