package com.abdallah.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.abdallah.sandwichclub.model.Sandwich;
import com.abdallah.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    private final static String TAG = DetailActivity.class.getSimpleName();

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

        bindViews();

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

        populateUI(sandwich);

    }

    /**
     * Bind the views by calling findViewById for each of them
     */
    private void bindViews() {
        ingredientsImageView = findViewById(R.id.image_iv);
        alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        placeOfOriginTextView = findViewById(R.id.origin_tv);
        descriptionTextView = findViewById(R.id.description_tv);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        setTitle(sandwich.getMainName());

        if (!sandwich.getImage().equals("")){
            Picasso.with(this)
                    .load(sandwich.getImage())
                    .into(ingredientsImageView);
        }
        else {
            ingredientsImageView.setVisibility(View.GONE);
        }

        int akaSize = sandwich.getAlsoKnownAs().size();
        if (akaSize != 0) {
            for (int i = 0; i< akaSize; i++) {
                alsoKnownAsTextView.append(sandwich.getAlsoKnownAs().get(i));
                if (i != akaSize-1) {
                    alsoKnownAsTextView.append("\n");
                }
            }
        }
        else {
            LinearLayout alsoKnownAsLinearLayout = findViewById(R.id.also_known_linear_layout);
            alsoKnownAsLinearLayout.setVisibility(View.GONE);
        }

        int ingredientsSize = sandwich.getIngredients().size();
        if (ingredientsSize != 0) {
            for (int i = 0; i<ingredientsSize; i++) {
                ingredientsTextView.append(sandwich.getIngredients().get(i));
                if (i != ingredientsSize-1) {
                    ingredientsTextView.append(", ");
                }
            }
        }
        else {
            LinearLayout ingredientsLinearLayout = findViewById(R.id.ingredients_linear_layout);
            ingredientsLinearLayout.setVisibility(View.GONE);
        }

        if (!sandwich.getPlaceOfOrigin().equals("")) {
            placeOfOriginTextView.setText(sandwich.getPlaceOfOrigin());
        }
        else {
            LinearLayout originLinearLayout = findViewById(R.id.origin_linear_layout);
            originLinearLayout.setVisibility(View.GONE);
        }

        if (!sandwich.getDescription().equals("")) {
            descriptionTextView.setText(sandwich.getDescription());
        }
        else {
            LinearLayout descriptionLinearLayout = findViewById(R.id.description_linear_layout);
            descriptionLinearLayout.setVisibility(View.GONE);
        }

    }
}
