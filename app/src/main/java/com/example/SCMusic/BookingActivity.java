package com.example.SCMusic;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookingActivity extends Activity {
    private TextView faq;
    private int datePost;
    private int monthPost;
    private CardView searchBtn;
    private LinearLayout shownTable;
    private LinearLayout LayOne;
    private LinearLayout LayTwo;
    private LinearLayout LayThree;
    private TextView time1;
    private TextView time2;
    private TextView time3;
    private TextView st1;
    private String status;
    private Button btnUnBook2;
    private Button btnUnBook3;
    String post;
    String post2;
    String post3;
    private String Biguid;
    private String Biguid2;
    private String Biguid3;
    private TextView st2;
    User u1;
    private String checkUID1;
    private String checkUID2;
    private String checkUID3;
    private TextView st3;
    Button btnUnBook ;
    View visib;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    DatabaseReference db;
    Button btnBook;
    Button btnBookCancle;
    private String m_Text = "";
    FirebaseAuth mAuth;
    private Button bhh;
    private TextView searchR;
    private Spinner dateSpinner;
    private Spinner monthSpinner;
    private Button  searchButton;

    String roomStatus[] = {"Available", "ไม่ว่าง"};
    int image[] = {R.drawable.free, R.drawable.unfree_};

    private ArrayList<String> dateSelect = new ArrayList<String>();
    private ArrayList<String> monthSelect = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_search);
        init();

        createDate();
        faqSee();
        SpinnerAndSearch();
    }
    private void createDate(){
        String dateText;
        for(int i = 1;i<=31;i++){
            dateText = String.valueOf(i);
            dateSelect.add(dateText);
        }
        monthSelect.add("January");
        monthSelect.add("February");
        monthSelect.add("March");
        monthSelect.add("April");
        monthSelect.add("May");
        monthSelect.add("June");
        monthSelect.add("July");
        monthSelect.add("August");
        monthSelect.add("September");
        monthSelect.add("October");
        monthSelect.add("November");
        monthSelect.add("December");
    }
    private void init(){
        btnUnBook2 = (Button) findViewById(R.id.btnUnBook2);
        btnUnBook3 = (Button) findViewById(R.id.btnUnBook3);
        btnUnBook = (Button) findViewById(R.id.btnUnBook);
        dateSpinner = (Spinner) findViewById(R.id.dateShow);
        monthSpinner = (Spinner) findViewById(R.id.monthShow);
        //searchButton = (Button) findViewById(R.id.BtnSearch);
        searchBtn = (CardView) findViewById(R.id.search_btn);
        time1 = (TextView) findViewById(R.id.timeShow1);
        time2 = (TextView) findViewById(R.id.timeShow2);
        time3 = (TextView) findViewById(R.id.timeShow3);
        st1 = (TextView) findViewById(R.id.statusShow1);
        st2 = (TextView) findViewById(R.id.statusShow2);
        st3 = (TextView) findViewById(R.id.statusShow3);
        img1 = (ImageView) findViewById(R.id.imageShow1);
        img2 = (ImageView) findViewById(R.id.imageShow2);
        img3 = (ImageView) findViewById(R.id.imageShow3);
        shownTable = (LinearLayout) findViewById(R.id.LayoutHide);
        LayOne = (LinearLayout) findViewById(R.id.Lay1);
        LayTwo = (LinearLayout) findViewById(R.id.Lay2);
        LayThree = (LinearLayout) findViewById(R.id.Lay3);
        faq = (TextView) findViewById(R.id.faqBt);

    }
    private  void SpinnerAndSearch(){

        ArrayAdapter<String> adapterDate = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, dateSelect);
        dateSpinner.setAdapter(adapterDate);

        ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, monthSelect);
        monthSpinner.setAdapter(adapterMonth);
        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                datePost = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monthPost = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = FirebaseDatabase.getInstance().getReference();
                db.child("Date").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        post = snapshot.child(dateSelect.get(datePost) + " " + monthSelect.get(monthPost)).child("Room1").child("Status").getValue(String.class);
                        post2 = snapshot.child(dateSelect.get(datePost) + " " + monthSelect.get(monthPost)).child("Room2").child("Status").getValue(String.class);
                        post3 = snapshot.child(dateSelect.get(datePost) + " " + monthSelect.get(monthPost)).child("Room3").child("Status").getValue(String.class);
                        String uid1 = snapshot.child(dateSelect.get(datePost) + " " + monthSelect.get(monthPost)).child("Room1").child("Uid").getValue(String.class);
                        String uid2 = snapshot.child(dateSelect.get(datePost) + " " + monthSelect.get(monthPost)).child("Room2").child("Uid").getValue(String.class);
                        String uid3 = snapshot.child(dateSelect.get(datePost) + " " + monthSelect.get(monthPost)).child("Room3").child("Uid").getValue(String.class);
                        //time1.setText(uid1);
                        //time2.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());

                        if (post != null) {
                            post.toUpperCase();
                            if (post.equals(("Full"))) {
                                st1.setText("Status : " + "Unavailable");
                                img1.setImageResource(image[1]);
                                btnUnBook.setVisibility(View.VISIBLE);

                            } else {
                                st1.setText("Status : Available");
                            }
                        }
                        if (post2 != null) {
                            post2.toUpperCase();
                                if (post2.equals(("Full"))) {
                                    st2.setText("Status : " +  "Unavailable");
                                    img2.setImageResource(image[1]);
                                    btnUnBook2.setVisibility(View.VISIBLE);
                                } else {
                                    st2.setText("Status : Available");
                                }

                            }
                        if (post3 != null) {
                            post3.toUpperCase();
                            if (post3.equals(("Full"))) {
                                st3.setText("Status : " +  "Unavailable");
                                img3.setImageResource(image[1]);
                                btnUnBook3.setVisibility(View.VISIBLE);
                            } else {
                                st3.setText("Status : Available");
                            }

                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                bookMenu();

            }

        });
    }
    public void bookMenu(){
        shownTable.setVisibility(View.VISIBLE);

        LayOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                builder.setMessage("คุณจะจองห้องซ้อมใช่หรือไม่?");
                builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                            db.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    //status = snapshot.child("Date").child(dateSelect.get(datePost)+ " " +monthSelect.get(monthPost)).child("Room1").child("Status").getValue(String.class);
                                    checkUID1 = snapshot.child("Date").child(dateSelect.get(datePost)+ " " +monthSelect.get(monthPost)).child("Room1").child("Status").getValue(String.class);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            String currID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            if (!currID.equals((checkUID1))){
                                //Toast.makeText(BookingActivity.this,"Room is Full",Toast.LENGTH_LONG).show();
                            }
                            if (post == null){
                                db = FirebaseDatabase.getInstance().getReference();
                                Toast.makeText(getApplicationContext(), "จองแล้ว", Toast.LENGTH_SHORT).show();
                                db.child("Date").child(dateSelect.get(datePost) + " " + monthSelect.get(monthPost)).child("Room1").child("Status").setValue("Full");
                                db.child("Date").child(dateSelect.get(datePost) + " " + monthSelect.get(monthPost)).child("Room1").child("Uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            }else {
                                Toast.makeText(BookingActivity.this,"Room is Full",Toast.LENGTH_LONG).show();
                            }
                    }
                });
                builder.setNegativeButton("ไม่่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        btnUnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        Biguid = snapshot1.child("Date").child(dateSelect.get(datePost) + " " + monthSelect.get(monthPost)).child("Room1").child("Uid").getValue(String.class);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(),"Somethings went wrong",Toast.LENGTH_LONG).show();
                    }
                });
                String currId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String uid = Biguid;
                if (currId.equals((uid))){
                    db.child("Date").child(dateSelect.get(datePost)+ " " +monthSelect.get(monthPost)).child("Room1").child("Status").setValue(null);
                    db.child("Date").child(dateSelect.get(datePost)+ " " +monthSelect.get(monthPost)).child("Room1").child("Uid").setValue(null);
                    Biguid = "";
                    Toast.makeText(getApplicationContext(),"ยกเลิกสำเร็จ",Toast.LENGTH_LONG).show();
                    btnUnBook.setVisibility(visib.INVISIBLE);

                }else {
                    Toast.makeText(BookingActivity.this,"IDผู้ใช้ไม่ตรงกันไม่สามารถยกเลิกได้",Toast.LENGTH_LONG).show();
                }
            }
        });

        LayTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                builder.setMessage("คุณจะจองห้องซ้อมใช่หรือไม่?");
                builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                checkUID2 = snapshot.child("Date").child(dateSelect.get(datePost)+ " " +monthSelect.get(monthPost)).child("Room2").child("Status").getValue(String.class);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        String currID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        if (!currID.equals((checkUID2))){
                            Toast.makeText(BookingActivity.this,"Room is Full",Toast.LENGTH_LONG).show();
                        }
                        if (post2 == null){
                            db = FirebaseDatabase.getInstance().getReference();
                            Toast.makeText(getApplicationContext(), "จองแล้ว", Toast.LENGTH_SHORT).show();
                            db.child("Date").child(dateSelect.get(datePost) + " " + monthSelect.get(monthPost)).child("Room2").child("Status").setValue("Full");
                            db.child("Date").child(dateSelect.get(datePost) + " " + monthSelect.get(monthPost)).child("Room2").child("Uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        }else {
                            Toast.makeText(BookingActivity.this,"Room is Full",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("ไม่่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }

        });
        btnUnBook2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        Biguid2 = snapshot1.child("Date").child(dateSelect.get(datePost) + " " + monthSelect.get(monthPost)).child("Room2").child("Uid").getValue(String.class);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(),"Somethings went wrong",Toast.LENGTH_LONG).show();
                    }
                });
                String currId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String uid = Biguid2;
                if (currId.equals((uid))){
                    db.child("Date").child(dateSelect.get(datePost)+ " " +monthSelect.get(monthPost)).child("Room2").child("Status").setValue(null);
                    db.child("Date").child(dateSelect.get(datePost)+ " " +monthSelect.get(monthPost)).child("Room2").child("Uid").setValue(null);
                    Biguid2 = "";
                    Toast.makeText(getApplicationContext(),"ยกเลิกสำเร็จ",Toast.LENGTH_LONG).show();
                    btnUnBook2.setVisibility(View.INVISIBLE);

                }else {
                    Toast.makeText(BookingActivity.this,"IDผู้ใช้ไม่ตรงกันไม่สามารถยกเลิกได้",Toast.LENGTH_LONG).show();
                }
            }
        });
        LayThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookingActivity.this);
                builder.setMessage("คุณจะจองห้องซ้อมใช่หรือไม่?");
                builder.setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        db.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                checkUID3 = snapshot.child("Date").child(dateSelect.get(datePost)+ " " +monthSelect.get(monthPost)).child("Room3").child("Status").getValue(String.class);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        String currID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        if (!currID.equals((checkUID3))){
                            Toast.makeText(BookingActivity.this,"Room is Full",Toast.LENGTH_LONG).show();
                        }
                        if (post2 == null){
                            db = FirebaseDatabase.getInstance().getReference();
                            Toast.makeText(getApplicationContext(), "จองแล้ว", Toast.LENGTH_SHORT).show();
                            db.child("Date").child(dateSelect.get(datePost) + " " + monthSelect.get(monthPost)).child("Room3").child("Status").setValue("Full");
                            db.child("Date").child(dateSelect.get(datePost) + " " + monthSelect.get(monthPost)).child("Room3").child("Uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        }else {
                            Toast.makeText(BookingActivity.this,"Room is Full",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("ไม่่", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
        btnUnBook3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        Biguid3 = snapshot1.child("Date").child(dateSelect.get(datePost) + " " + monthSelect.get(monthPost)).child("Room3").child("Uid").getValue(String.class);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(),"Somethings went wrong",Toast.LENGTH_LONG).show();
                    }
                });
                String currId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String uid = Biguid3;
                if (currId.equals((uid))){
                    db.child("Date").child(dateSelect.get(datePost)+ " " +monthSelect.get(monthPost)).child("Room3").child("Status").setValue(null);
                    db.child("Date").child(dateSelect.get(datePost)+ " " +monthSelect.get(monthPost)).child("Room3").child("Uid").setValue(null);
                    Biguid2 = "";
                    Toast.makeText(getApplicationContext(),"ยกเลิกสำเร็จ",Toast.LENGTH_LONG).show();
                    btnUnBook3.setVisibility(View.INVISIBLE);
                }else {
                    Toast.makeText(BookingActivity.this,"IDผู้ใช้ไม่ตรงกันไม่สามารถยกเลิกได้",Toast.LENGTH_LONG).show();
                }
            }
        });



        img1.setImageResource(image[0]);
        img2.setImageResource(image[0]);
        img3.setImageResource(image[0]);
        time1.setText("18.00 - 20.00");
        time2.setText("20.00 - 22.00");
        time3.setText("22.00 - 00.00");
        st1.setText("Status : Available");
        st2.setText("Status : Available");
        st3.setText("Status : Available");
    }
    public void faqSee(){
        faq.setPaintFlags(faq.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingActivity.this,Guide.class);
                startActivity(intent);
            }
        });
    }

}
