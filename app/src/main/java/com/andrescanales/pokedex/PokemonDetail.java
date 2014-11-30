package com.andrescanales.pokedex;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class PokemonDetail extends ActionBarActivity {

    private Pokemon mpokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState != null) {
            mpokemon = savedInstanceState.getParcelable("pokemon");
        } else {
            Pokemon pokemon = getIntent().getParcelableExtra("pokemon");
            mpokemon = pokemon;
            Pokemon avatar = getIntent().getParcelableExtra("avatar");
        }

        PokemonDetailFragment fragment = PokemonDetailFragment.newInstance(mpokemon);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.pokemon_detail_container, fragment).commit();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("pokemon", mpokemon);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}