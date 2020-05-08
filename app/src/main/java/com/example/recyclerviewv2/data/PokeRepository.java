package com.example.recyclerviewv2.data;

import android.content.SharedPreferences;
import com.example.recyclerviewv2.Constants;
import com.example.recyclerviewv2.presentation.model.Pokemon;
import com.example.recyclerviewv2.presentation.model.RestPokemonResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PokeRepository {
    private PokeApi pokeapi;
    private SharedPreferences sharedPreferences;
    private final Gson gson;

    public PokeRepository(PokeApi pokeapi,SharedPreferences sharedPreferences, Gson gson) {
        this.pokeapi = pokeapi;
        this.sharedPreferences=sharedPreferences;
        this.gson =gson;
    }

    public void getPokemonResponse(final PokeCallback callback){
        List<Pokemon> list=getDataFromCache();
        if(list!=null){
            callback.onSuccess(list);
        }else{
            pokeapi.getPokemonResponse().enqueue(new Callback<RestPokemonResponse>() {
                @Override
                public void onResponse(Call<RestPokemonResponse> call, Response<RestPokemonResponse> response) {
                    if(response.isSuccessful()&& response.body()!=null){
                        callback.onSuccess(response.body().getResults());
                    }else{
                        callback.onFailed();
                    }
                }

                @Override
                public void onFailure(Call<RestPokemonResponse> call, Throwable t) {
                    callback.onFailed();
                }
            });
        }
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

    public void saveList(List<Pokemon> PokemonList){
        String jsonString = gson.toJson(PokemonList);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_POKEMON_LIST,jsonString)
                .apply();
    }


}
