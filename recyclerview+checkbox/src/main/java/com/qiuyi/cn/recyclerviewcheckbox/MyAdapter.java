package com.qiuyi.cn.recyclerviewcheckbox;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnLongClickListener,View.OnClickListener{

    private Context mContext;
    private ItemOnClickListener myListener;

    private List<String> itemString;
    //存储所有选中位置
    private boolean[] flag;
    ///判断当前checkBox是否显示
    private boolean isShowCheckBox = false;

    public boolean[] getFlag() {
        return flag;
    }

    public void setFlag(boolean[] flag) {
        this.flag = flag;
    }

    public boolean isShowCheckBox() {
        return isShowCheckBox;
    }

    public void setShowCheckBox(boolean showCheckBox) {
        this.isShowCheckBox = showCheckBox;
    }

    //选中全部
    public void selectAll(){
        for(int i=0;i<itemString.size();i++){
            flag[i] = true;
        }
        notifyDataSetChanged();
    }

    //所有都没有选中
    public void noSelect(){
        for(int i=0;i<itemString.size();i++){
            flag[i] = false;
        }
        notifyDataSetChanged();
    }

    public MyAdapter(Context context, List<String> itemString){
        this.mContext = context;
        this.itemString = itemString;
        flag = new boolean[itemString.size()];
    }

    @Override
    public void onClick(View view) {
        if(myListener!=null){
            myListener.onItemClick(view, (Integer) view.getTag());
        }
    }


    @Override
    public boolean onLongClick(View view) {
        if(myListener!=null){
            myListener.onItemLongClick(view, (Integer) view.getTag());
        }
        return false;
    }

    public interface ItemOnClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    public void setItemOnClickListener(ItemOnClickListener listener){
        this.myListener = listener;
    }

    //3
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext,R.layout.itemstring,null);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new itemViewHolder(view);
    }

    //4
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        String myTitle = itemString.get(position);

        itemViewHolder myholder = (itemViewHolder) holder;

        myholder.tv_item.setText(myTitle);

        //checkbox的显示
        if(isShowCheckBox){
            myholder.cb_box.setVisibility(View.VISIBLE);
        }else{
            myholder.cb_box.setVisibility(View.INVISIBLE);
        }
        myholder.cb_box.setOnCheckedChangeListener(null);//先设置一次CheckBox的选中监听器，传入参数null
        myholder.cb_box.setChecked(flag[position]);

        myholder.cb_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                flag[position] = b;
            }
        });

        myholder.itemView.setTag(position);
    }

    //1
    @Override
    public int getItemCount() {
        return itemString.size();
    }

    //2
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }



    class itemViewHolder extends RecyclerView.ViewHolder{
        TextView tv_item;
        CheckBox cb_box;
        public itemViewHolder(View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_item);
            cb_box = itemView.findViewById(R.id.cb_box);
        }
    }

    //页面刷新
    public void ReFresh(){
        flag = new boolean[itemString.size()];
        notifyDataSetChanged();
    }
}
