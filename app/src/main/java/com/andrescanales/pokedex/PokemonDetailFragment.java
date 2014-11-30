package com.andrescanales.pokedex;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PokemonDetailFragment extends Fragment {

    private static final String ARG_NOMBRE = "nombre";
    private static final String ARG_AVATAR = "avatar";
    private String mNombre;
    private String mAvatar;
    private ShareActionProvider mShareActionProvider;

    public static PokemonDetailFragment newInstance(Pokemon pokemon) {
        PokemonDetailFragment fragment = new PokemonDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NOMBRE, pokemon.getNombre());
        args.putString(ARG_AVATAR, pokemon.getUrlImage());
        fragment.setArguments(args);
        return fragment;
    }
    public PokemonDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mNombre = getArguments().getString(ARG_NOMBRE);
            mAvatar = getArguments().getString(ARG_AVATAR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_pokemon_detail,
                container, false);

        TextView nombre = (TextView)rootView.findViewById(R.id.textview_pokemon_nombre);
        nombre.setText(mNombre);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.pokemon_detail, menu);

        MenuItem item = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareIntent(getDefaultShareIntent());
        super.onCreateOptionsMenu(menu, inflater);
        //return true;
    }

    private Intent getDefaultShareIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if (mNombre != null){
            intent.putExtra(Intent.EXTRA_SUBJECT, "¿Quién es ese pokemon?");
            intent.putExtra(Intent.EXTRA_TEXT, mNombre);
        }
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(getActivity());
            return true;
        }else if(id == R.id.action_ver_imagen){
            if(mAvatar != null && mAvatar.length()>0){
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(mAvatar));
                startActivity(intent);
                return true;
            }
        }else if(id == R.id.action_mapa){
            String uri = "geo:0,0?q=MCNAMARA+TERMINAL+ROMULUS+MI+48174";
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(i);
            /*Intent intent = new Intent(Intent.ACTION_CALL,
                    Uri.parse("tel: 25621539"));
            startActivity(intent);*/
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
