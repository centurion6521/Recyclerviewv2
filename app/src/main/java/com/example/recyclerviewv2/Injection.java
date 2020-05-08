package com.example.recyclerviewv2;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.recyclerviewv2.data.PokeApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.recyclerviewv2.Constants.BASE_URL;

public class Injection {
    private static Gson gsonInstance;
    private static PokeApi pokeApiInstance;
    private static SharedPreferences sharedPreferencesInstance;

    public static Gson getGson(){
        if(gsonInstance ==null){
            gsonInstance=new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }

    public static PokeApi getPokeApiInstance(){
        if(pokeApiInstance==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
            pokeApiInstance = retrofit.create(PokeApi.class);
        }

        return pokeApiInstance;

    }

    public static SharedPreferences getSharedPreferencesInstance(Context context){
        if(sharedPreferencesInstance ==null){
            sharedPreferencesInstance=context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }
}
