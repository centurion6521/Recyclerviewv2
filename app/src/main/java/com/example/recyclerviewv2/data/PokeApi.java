package com.example.recyclerviewv2.data;

import com.example.recyclerviewv2.presentation.model.RestPokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeApi {
    @GET("/api/v2/pokemon?limit=964")
    Call<RestPokemonResponse> getPokemonResponse();
}
