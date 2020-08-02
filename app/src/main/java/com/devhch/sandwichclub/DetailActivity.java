package com.devhch.sandwichclub;

/**
 * Created By Mirai Dev.
 * Hamza Chaouki
 * Hamza Shawki
 */

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.devhch.sandwichclub.model.Sandwich;
import com.devhch.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich sandwich;
    private TextView detailAlsoKnownAsLabel, detailIngredientsLabel,
            detailPlaceOfOriginLabel, detailDescriptionLabel;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        detailAlsoKnownAsLabel = findViewById(R.id.detail_also_known_as_label);
        detailIngredientsLabel = findViewById(R.id.detail_ingredients_label);
        detailPlaceOfOriginLabel = findViewById(R.id.detail_place_of_origin_label);
        detailDescriptionLabel = findViewById(R.id.detail_description_label);
        progressbar = findViewById(R.id.progress_circular);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        StringBuilder alsoKnownString = new StringBuilder();
        for (String item : sandwich.getAlsoKnownAs())
            alsoKnownString.append(" ").append(item).append(" , ");
        detailAlsoKnownAsLabel.append(alsoKnownString);

        StringBuilder ingredientsString = new StringBuilder();
        for (String item : sandwich.getIngredients())
            ingredientsString.append(" ").append(item).append(" , ");
        detailIngredientsLabel.append(ingredientsString);

        detailPlaceOfOriginLabel.append(" " + sandwich.getPlaceOfOrigin());
        detailDescriptionLabel.append(" " + sandwich.getDescription());
    }
}