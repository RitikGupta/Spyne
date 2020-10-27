package androidclass.spyne;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hp on 10/25/2020.
 */

public class RecycleAdapterExploreTab extends RecyclerView.Adapter<RecycleAdapterExploreTab.ViewH> {

    Context c;
    ArrayList<ExploreDetails> exploreDetailsArrayList;

    public RecycleAdapterExploreTab(Context context, ArrayList<ExploreDetails> arrayList) {
        this.c = context;
        this.exploreDetailsArrayList = arrayList;
    }

    public class ViewH extends RecyclerView.ViewHolder {

        TextView tvUsername , tvCompName , tvMsg , tvHashtags ;
        View view;

        public ViewH(View itemView)
        {
            super(itemView);
            tvUsername= (TextView) itemView.findViewById(R.id.tv_username);
            tvCompName= (TextView) itemView.findViewById(R.id.tv_compName);
            tvMsg= (TextView) itemView.findViewById(R.id.tv_msg);
            view=itemView;

        }

    }

    @Override
    public ViewH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View views =inflater.inflate(R.layout.layout_explore_tab_cards,parent,false);
        return new ViewH(views);
    }

    @Override
    public void onBindViewHolder(ViewH holder, int position) {
        final ExploreDetails exploreDetails = exploreDetailsArrayList.get(position);
        holder.tvUsername.setText(exploreDetails.getUsername());
        holder.tvCompName.setText(exploreDetails.getUserCompany());
        holder.tvMsg.setText(exploreDetails.getMsg());


    }



    @Override
    public int getItemCount() {
        return exploreDetailsArrayList.size();
    }
}
