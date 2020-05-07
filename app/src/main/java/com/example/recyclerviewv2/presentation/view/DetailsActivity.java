package com.example.recyclerviewv2.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recyclerviewv2.Injection;
import com.example.recyclerviewv2.R;
import com.example.recyclerviewv2.presentation.model.Pokemon;

public class DetailsActivity extends AppCompatActivity {
    private TextView textDetails;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_details);

            textDetails=findViewById(R.id.details_txt);
            Intent intent = getIntent();
            String pokemonJson = intent.getStringExtra("PokemonName");
            Pokemon pokemon=Injection.getGson().fromJson(pokemonJson, Pokemon.class);
            showDetails(pokemon);
        }

        private void showDetails(Pokemon pokemon){
            textDetails.setText(pokemon.getName()+"\n"+pokemon.getUrl());
        }
}