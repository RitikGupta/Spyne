package androidclass.spyne;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by hp on 10/25/2020.
 */

public class AboutFragment extends Fragment {

    SearchView searchView ;
    RecyclerView rv;
    RecycleAdapterExploreTab adapter;

    ArrayList<ExploreDetails> exploreDetailsList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.exploretab , container , false);

        rv = (RecyclerView) view.findViewById(R.id.rv_explore_tab);


        exploreDetailsList = new ArrayList<>();

        exploreDetailsList.add(new ExploreDetails("Ecommerce", "Boost your ecommerce sales with HD images" ,"Request your product photoshoot with just a click.Spyne will connect you with the right photographer for the assignment." ));
        exploreDetailsList.add(new ExploreDetails("Fashion", "Boost your fashion sales with HD images" ,"Spyne's operation team will successfully share final photos digitally, ensuring seamless, satisfactory, and timely delivery." ));
        exploreDetailsList.add(new ExploreDetails("Real estate", "Real Estate with Quality Images Sell 60% faster" ,"Invest in real estate with hd quality images" ));
        exploreDetailsList.add(new ExploreDetails("Hotels", "Real Estate with Quality Images Sell 60% faster" ,"Search for nearbuy hotels with hd quality images" ));
        exploreDetailsList.add(new ExploreDetails("Food", "Crave your customers with mouth-watering food pictures" ,"Post ads of your delicious food and make your customers drool over it" ));
        exploreDetailsList.add(new ExploreDetails("Jewellery", "Boost your jewellery business with impactful images" ,"Increase your sales with beautiful images." ));


        adapter=new RecycleAdapterExploreTab(getContext() ,exploreDetailsList);
        adapter.notifyDataSetChanged();
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);

        return view;
    }
}
