package com.example.recyclerviewv2.data;

import com.example.recyclerviewv2.presentation.model.Pokemon;
import java.util.List;

public interface PokeCallback {
    public void onSuccess(List<Pokemon> response);
    public void onFailed();
}
