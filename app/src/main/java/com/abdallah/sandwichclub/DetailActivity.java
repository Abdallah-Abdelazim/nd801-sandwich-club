package com.abdallah.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.abdallah.sandwichclub.model.Sandwich;
import com.abdallah.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView alsoKnownAsTextView;
    private TextView ingredientsTextView;
    private TextView placeOfOriginTextView;
    private TextView descriptionTextView;
    private ImageView ingredientsImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsImageView = findViewById(R.id.image_iv);
        alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        placeOfOriginTextView = findViewById(R.id.origin_tv);
        descriptionTextView = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwichesDetails = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwichesDetails[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        setTitle(sandwich.getMainName());

        populateUI(sandwich);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsImageView);

        int akaSize = sandwich.getAlsoKnownAs().size();
        for (int i = 0; i< akaSize; i++) {
            alsoKnownAsTextView.append(sandwich.getAlsoKnownAs().get(i));
            if (i != akaSize-1) {
                alsoKnownAsTextView.append("\n");
            }
        }

        int ingredientsSize = sandwich.getIngredients().size();
        for (int i = 0; i<ingredientsSize; i++) {
            ingredientsTextView.append(sandwich.getIngredients().get(i));
            if (i != ingredientsSize-1) {
                ingredientsTextView.append(", ");
            }
        }

        placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());

        descriptionTextView.setText(sandwich.getDescription());

    }
}
