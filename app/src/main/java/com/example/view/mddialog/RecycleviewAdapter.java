package com.example.view.mddialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yqy on 8/00082016/12/8/.
 */
public class RecycleviewAdapter extends RecyclerView.Adapter<MyHolder> {


    private List<String> mData;
    private Context context;


    public RecycleviewAdapter(List<String> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder myHolder = new MyHolder(LayoutInflater.from(context).inflate(R.layout.recycleview_item,parent,false));
        return myHolder;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.textView.setText(mData.get(position));

    }


}
class  MyHolder extends  RecyclerView.ViewHolder{

    TextView textView;
    public MyHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.recycle_text);
    }
}