package com.example.covid19tacker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MyCustomAdapter extends ArrayAdapter<CountryModal> {
    private Context context;
    private List<CountryModal> countryModalsLists;
    private List<CountryModal> countryModalsListsFiltered;

    public MyCustomAdapter(Context context, List<CountryModal> countryModalsLists) {
        super(context, R.layout.list_custom_item,countryModalsLists);
        this.context=context;
        this.countryModalsLists=countryModalsLists;
        this.countryModalsListsFiltered=countryModalsLists;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_item,null,true);

        TextView tvCountryName=view.findViewById(R.id.tvCountryName);
        ImageView imageview=view.findViewById(R.id.imageFlag);

        tvCountryName.setText(countryModalsListsFiltered.get(position).getCountry());
        Glide.with(context).load(countryModalsListsFiltered.get(position).getFlag()).into(imageview);
        return view;
    }

    @Override
    public int getCount() {
        return countryModalsListsFiltered.size();
    }



    @Nullable
    @Override
    public CountryModal getItem(int position) {
        return countryModalsListsFiltered.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = countryModalsLists.size();
                    filterResults.values = countryModalsLists;

                }else{
                    List<CountryModal> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(CountryModal itemsModel:countryModalsLists){
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                countryModalsListsFiltered = (List<CountryModal>) results.values;
                AffectedCountries.countryModalList = (List<CountryModal>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }



}
