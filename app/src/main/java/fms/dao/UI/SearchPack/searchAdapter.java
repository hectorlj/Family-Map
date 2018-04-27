package fms.dao.UI.SearchPack;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import fms.dao.R;

/**
 * Created by Hector Lopez on 11/25/2017.
 */

public class searchAdapter extends RecyclerView.Adapter<searchViewHolder> {
    private List<searchModel> list = Collections.emptyList();
    private Context context;
    public searchAdapter(List<searchModel> list, Context context){
        this.list = list;
        this.context = context;
    }
    @Override
    public searchViewHolder onCreateViewHolder(@Nullable ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_search, parent, false);
        return new searchViewHolder(v, context);
    }
    @Override
    public int getItemCount(){
        return list.size();
    }
    @Override
    public void onBindViewHolder(searchViewHolder viewHolder, int position){
        searchModel row = list.get(position);
        viewHolder.bind(row);
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recycler){
        super.onAttachedToRecyclerView(recycler);
    }
}
