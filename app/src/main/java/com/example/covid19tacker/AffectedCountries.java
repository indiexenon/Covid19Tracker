package com.example.covid19tacker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedCountries extends AppCompatActivity {
    EditText etsearch;
    ListView listview;
    SimpleArcLoader simpleArcLoader;


    public static List<CountryModal> countryModalList=new ArrayList<>();
    CountryModal countryModal;
    MyCustomAdapter myCustomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);
        etsearch=findViewById(R.id.etsearch);
        listview=findViewById(R.id.listview);
        simpleArcLoader=findViewById(R.id.loader);

        getSupportActionBar().setTitle("Affected Country");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        fetchData();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),DetailActivity.class).putExtra("position",position));
            }
        });
        etsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myCustomAdapter.getFilter().filter(s);
                myCustomAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {

        String url ="https://corona.lmao.ninja/v2/countries/";

        simpleArcLoader.start();

        StringRequest request= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray=new JSONArray(response);

                            for (int i=1;i<jsonArray.length();i++){

                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                String countryName = jsonObject.getString("country");
                                String cases = jsonObject.getString("cases");
                                String todayscase = jsonObject.getString("todayCases");
                                String death = jsonObject.getString("deaths");
                                String todaysdeath = jsonObject.getString("todayDeaths");
                                String recovered = jsonObject.getString("recovered");
                                String active = jsonObject.getString("active");
                                String critical = jsonObject.getString("critical");
                                String conti = jsonObject.getString("continent");

                                JSONObject object=jsonObject.getJSONObject("countryInfo");
                                String flag=object.getString("flag");

                                countryModal = new CountryModal(flag,countryName,cases,todayscase,death,todaysdeath,recovered,active,critical,conti);
                                countryModalList.add(countryModal);

                            }
                            myCustomAdapter=new MyCustomAdapter(AffectedCountries.this,countryModalList);
                            listview.setAdapter(myCustomAdapter);
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                Toast.makeText(AffectedCountries.this,error.getMessage(),Toast.LENGTH_SHORT).show();


            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);


    }
}