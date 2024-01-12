package com.tiodev.careforu;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiodev.careforu.Adapter.Adaptar;
import com.tiodev.careforu.Model.ResModel;
import com.tiodev.careforu.RoomDB.AppDatabase;
import com.tiodev.careforu.RoomDB.User;
import com.tiodev.careforu.RoomDB.UserDao;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    RecyclerView recview;
    boolean connected = false;
    List<ResModel> data;
    List<User> dataFinal = new ArrayList<>();
    ImageView back;
    TextView tittle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find views
        back = findViewById(R.id.imageView2);
        tittle = findViewById(R.id.tittle);
        recview = (RecyclerView)findViewById(R.id.recview);
        // Set layout manager to recyclerView
        recview.setLayoutManager(new LinearLayoutManager(this));
        // Set recipe category title
        tittle.setText(getIntent().getStringExtra("tittle"));
        // Get database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "db_name").allowMainThreadQueries()
                .createFromAsset("database/recipe.db")
                .build();
        UserDao userDao = db.userDao();
        // Get all recipes from database
        List<User> recipes = userDao.getAll();
        // Filter category from recipes
        for(int i = 0; i<recipes.size(); i++){
            if(recipes.get(i).getCategory().contains(getIntent().getStringExtra("Category"))){
                dataFinal.add(recipes.get(i));
            }
        }
        // Set category list to adapter
        Adaptar adapter = new Adaptar(dataFinal, getApplicationContext());
        recview.setAdapter(adapter);
        back.setOnClickListener(v -> finish());
    }
}
