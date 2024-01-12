package com.tiodev.careforu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.tiodev.careforu.Adapter.AdapterPopular;
import com.tiodev.careforu.Model.ResModel;
import com.tiodev.careforu.RoomDB.AppDatabase;
import com.tiodev.careforu.RoomDB.User;
import com.tiodev.careforu.RoomDB.UserDao;

import java.util.ArrayList;
import java.util.List;
public class HomeActivity extends AppCompatActivity {
    ImageView digestice,cold, women, skin, menu;
    RecyclerView rcview_home;
    List<User> dataPopular = new ArrayList<>();
    LottieAnimationView lottie;
    EditText editText;
    List<ResModel> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        digestice = findViewById(R.id.digestice);
        cold = findViewById(R.id.cold);
        women = findViewById(R.id.women);
        skin = findViewById(R.id.skin);
        rcview_home = findViewById(R.id.rcview_popular);
        lottie = findViewById(R.id.lottie);
        editText = findViewById(R.id.editText);
        menu = findViewById(R.id.imageView);

        rcview_home.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        setPopularList();

        digestice.setOnClickListener(v -> start("Digestice","Digestice"));
        cold.setOnClickListener(v -> start("Cold", "Cold Flu"));
        women.setOnClickListener(v -> start("Women", "SheCare"));
       skin.setOnClickListener(v -> start("Skin", "Skin Hair"));

        editText.setOnClickListener(v ->{
            Intent intent = new Intent(HomeActivity.this,SearchActivity.class);
            startActivity(intent);
        });

        menu.setOnClickListener(v -> showBottomSheet());
    }
    public void setPopularList() {

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "db_name").allowMainThreadQueries()
                       .createFromAsset("database/recipe.db")
                       .build();
        UserDao userDao = db.userDao();

        List<User> recipes = userDao.getAll();

        for(int i = 0; i<recipes.size(); i++){
            if(recipes.get(i).getCategory().contains("Popular")){
                dataPopular.add(recipes.get(i));
            }
        }
        // Set popular list to adapter
        AdapterPopular adapter = new AdapterPopular(dataPopular, getApplicationContext());
        rcview_home.setAdapter(adapter);
        // Hide progress animation
        lottie.setVisibility(View.GONE);
    }

    public void start(String p, String tittle){
        Intent intent = new Intent(HomeActivity.this,MainActivity.class);
        intent.putExtra("Category", p);
        intent.putExtra("tittle", tittle);
        startActivity(intent);
    }

    private void showBottomSheet() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet);
        LinearLayout abtDev = dialog.findViewById(R.id.about_dev);
        abtDev.setOnClickListener(v ->{
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.linkedin.com/in/roshani-borkar/"));
            startActivity(intent);
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

}