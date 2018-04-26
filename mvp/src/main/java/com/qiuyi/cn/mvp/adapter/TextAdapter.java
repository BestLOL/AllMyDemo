package com.qiuyi.cn.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiuyi.cn.mvp.R;

import java.util.List;

/**
 * Created by Administrator on 2018/4/16.
 */
public class TextAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private Context context;
    private List<String> stringList;

    private OnItemClickListener myListener;

    @Override
    public void onClick(View view) {
        if(myListener!=null){
            myListener.onItemClick(view, (Integer) view.getTag());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener myListener){
        this.myListener = myListener;
    }


    public TextAdapter(Context context,List<String> stringList){
        this.context = context;
        this.stringList = stringList;
    }

    //3
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item,null);
        view.setOnClickListener(this);
        return new textViewHolder(view);
    }

    //4
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        String name = stringList.get(position);

        textViewHolder tholder = (textViewHolder) holder;
        tholder.onBind(name);

        tholder.itemView.setTag(position);
    }

    //1
    @Override
    public int getItemCount() {
        return stringList.size();
    }
    //2
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class textViewHolder extends RecyclerView.ViewHolder{
        TextView myTV_show;
        public textViewHolder(View itemView) {
            super(itemView);
            myTV_show = itemView.findViewById(R.id.tv_show);
        }

        public void onBind(String name){
            myTV_show.setText(name);
        }
    }
}
