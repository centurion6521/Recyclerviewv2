package com.example.recyclerviewv2.presentation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

//import com.example.recyclerviewv2.Constants;
import com.example.recyclerviewv2.Injection;
import com.example.recyclerviewv2.R;
//import com.example.recyclerviewv2.data.PokeApi;
import com.example.recyclerviewv2.presentation.controller.MainController;
import com.example.recyclerviewv2.presentation.model.Pokemon;
//import com.example.recyclerviewv2.presentation.model.RestPokemonResponse;
import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;

//import java.lang.reflect.Type;
import java.util.List;

//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {



    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new MainController(this,
                Injection.getGson(),
                Injection.getSharedPreferencesInstance(getApplicationContext())
                );
        controller.onStart();
    }


    public void showList(List<Pokemon> pokemonList) {
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
       /* List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }*/
        // define an adapter
        mAdapter = new ListAdapter(pokemonList, new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Pokemon item) {
                controller.onItemClick(item);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }




    public void showError() {
        Toast.makeText( this,"Api Error",Toast.LENGTH_SHORT).show();
    }

    public void navigateToDetails(Pokemon pokemon) {
        Toast.makeText( this,"NAVIGATE",Toast.LENGTH_SHORT).show();
    }
}
