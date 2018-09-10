package com.example.homepc.taxanomyapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String baseUrl = "http://api.gbif.org/v1/species?name=";

    private String kingdom, phylum, aclass, order, family, genus, species;

    private ProgressDialog progressDialog;

    private Button button;
    private EditText editText;
    private TextView mKingdom, mPhylum, mClass, mOrder, mFamily, mGenus, mSpecies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        mKingdom = findViewById(R.id.textView);
        mPhylum = findViewById(R.id.textView2);
        mClass = findViewById(R.id.textView3);
        mOrder = findViewById(R.id.textView4);
        mFamily = findViewById(R.id.textView5);
        mGenus = findViewById(R.id.textView6);
        mSpecies = findViewById(R.id.textView7);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editText.getText().toString();
                progressDialog.show();

                //      String url = baseUrl + "Puma concolor";
                String url = baseUrl + name;

                url = url.replace(" ", "%20");

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            InputMethodManager inputManager = (InputMethodManager)
                                    getSystemService(Context.INPUT_METHOD_SERVICE);

                            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);

                            editText.getText().clear();

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray results = jsonObject.getJSONArray("results");

                            if (results.length() < 2) {
                                editText.setError("Enter Correct Scientific Name");
                                editText.requestFocus();
                                progressDialog.dismiss();
                            } else {

                                JSONObject object = results.getJSONObject(0);

                                mKingdom.setVisibility(View.VISIBLE);
                                mPhylum.setVisibility(View.VISIBLE);
                                mClass.setVisibility(View.VISIBLE);
                                mOrder.setVisibility(View.VISIBLE);
                                mFamily.setVisibility(View.VISIBLE);
                                mGenus.setVisibility(View.VISIBLE);
                                mSpecies.setVisibility(View.VISIBLE);

                                progressDialog.dismiss();

                                if (object.has("kingdom")) {
                                    kingdom = object.getString("kingdom");
                                    mKingdom.setText("Kingdom: " + kingdom);
                                } else {
                                    kingdom = "-";
                                    mKingdom.setText("Kingdom: " + kingdom);

                                }

                                if (object.has("phylum")) {
                                    phylum = object.getString("phylum");
                                    mPhylum.setText("Phylum: " + phylum);
                                } else {
                                    phylum = "-";
                                    mPhylum.setText("Phylum: " + phylum);

                                }

                                if (object.has("class")) {
                                    aclass = object.getString("class");
                                    mClass.setText("Class: " + aclass);
                                } else {
                                    aclass = "-";
                                    mClass.setText("Class: " + aclass);

                                }

                                if (object.has("order")) {
                                    order = object.getString("order");
                                    mOrder.setText("Order: " + order);
                                } else {
                                    order = "-";
                                    mOrder.setText("Order: " + order);

                                }

                                if (object.has("family")) {
                                    family = object.getString("family");
                                    mFamily.setText("Family: " + family);
                                } else {
                                    family = "-";
                                    mFamily.setText("Family: " + family);

                                }

                                if (object.has("genus")) {
                                    genus = object.getString("genus");
                                    mGenus.setText("Genus: " + genus);
                                } else {
                                    genus = "-";
                                    mGenus.setText("Genus: " + genus);

                                }

                                if (object.has("species")) {
                                    species = object.getString("species");
                                    mSpecies.setText("Species: " + species);
                                } else {
                                    species = "-";
                                    mSpecies.setText("Species: " + species);

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }
        });
    }
}
