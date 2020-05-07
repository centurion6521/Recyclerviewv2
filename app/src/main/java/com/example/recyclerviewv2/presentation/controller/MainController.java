package com.example.recyclerviewv2.presentation.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.recyclerviewv2.Constants;
import com.example.recyclerviewv2.Injection;
import com.example.recyclerviewv2.data.PokeApi;
import com.example.recyclerviewv2.presentation.model.Pokemon;
import com.example.recyclerviewv2.presentation.model.RestPokemonResponse;
import com.example.recyclerviewv2.presentation.view.MainActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.recyclerviewv2.Constants.BASE_URL;

public class MainController {
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private MainActivity view;

    public MainController(MainActivity view,Gson gson, SharedPreferences sharedPreferences) {
        this.view=view;
        this.gson=gson;
        this.sharedPreferences=sharedPreferences;
    }

    public void onStart(){
        /*sharedPreferences =getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        gson = new GsonBuilder()
                .setLenient()
                .create();*/
        List<Pokemon> pokemonList = getDataFromCache();
        if(pokemonList!=null){
            view.showList(pokemonList);
        }else {
            makeApiCall();
        }
    }

    private void makeApiCall(){
        Call<RestPokemonResponse> call = Injection.getPokeApiInstance().getPokemonResponse();
        call.enqueue(new Callback<RestPokemonResponse>() {
            @Override
            public void onResponse(Call<RestPokemonResponse> call, Response<RestPokemonResponse> response) {
                if (response.isSuccessful()&& response.body() != null) {
                    List<Pokemon> pokemonList = response.body().getResults();
                    saveList(pokemonList);
                    view.showList(pokemonList);
                }else{

                    view.showError();
                }
            }

            @Override
            public void onFailure(Call<RestPokemonResponse> call, Throwable t) {
                view.showError();
            }
        });



    }

    private void saveList(List<Pokemon> PokemonList){
        String jsonString = gson.toJson(PokemonList);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_POKEMON_LIST,jsonString)
                .apply();
        //Toast.makeText( this,"List Saved",Toast.LENGTH_SHORT).show();
    }

    private List<Pokemon> getDataFromCache(){
        String jsonPokemon =sharedPreferences.getString(Constants.KEY_POKEMON_LIST,null);
        if(jsonPokemon ==null){
            return null;
        }else {
            Type listType = new TypeToken<List<Pokemon>>() {
            }.getType();
            return gson.fromJson(jsonPokemon, listType);
        }
    }

    public void onItemClick(Pokemon pokemon){
        view.navigateToDetails(pokemon);
    }

    public void onButtonAClick(){

    }

    public void onButtonBClick(){

    }
}
