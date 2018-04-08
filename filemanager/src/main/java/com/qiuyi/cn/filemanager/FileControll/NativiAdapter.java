package com.qiuyi.cn.filemanager.FileControll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qiuyi.cn.filemanager.R;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 * 本地适配器
 */
public class NativiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener,View.OnLongClickListener{

    public static final int[] NATIVE_SIZE = new int[6];

    private Context context;
    private List<FileType> fileTypeList;
    private myItemClick itemClick;

    public NativiAdapter(Context context, List<FileType> fileTypeList){
        this.context = context;
        this.fileTypeList = fileTypeList;
    }

    public void setOnItemClick(myItemClick itemClick){
        this.itemClick = itemClick;
    }

    public void changeList(){
        for(int i=0;i<6;i++){
            fileTypeList.get(i).setSize(NATIVE_SIZE[i]);
        }
        this.notifyDataSetChanged();
    }

    public interface myItemClick{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    //下面是点击事件
    @Override
    public void onClick(View view) {
        if(itemClick!=null){
            itemClick.onItemClick(view, (int) view.getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if(itemClick!=null){
            itemClick.onItemLongClick(view, (int) view.getTag());
            return true;
        }
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder myViewholder = null;
        if(viewType == 0){
            view = View.inflate(context, R.layout.pager_native_adapter_file_layout,null);
            myViewholder = new TypeViewHolder(view);
        }else if(viewType == 1){
            view = View.inflate(context, R.layout.pager_native_adapter_title_layout,null);
            myViewholder = new TitleViewHolder(view);
        }else if(viewType == 2){
            view = View.inflate(context, R.layout.pager_native_adapter_block_layout,null);
            myViewholder = new BlockViewHolder(view);
        }

        if(view!=null){
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        return myViewholder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FileType myFileType = fileTypeList.get(position);
        if(myFileType.getShowType() == 0){
            TypeViewHolder tholder = (TypeViewHolder) holder;

            tholder.onBind(myFileType);

            tholder.itemView.setTag(position);
        }else if(myFileType.getShowType() == 1){
            TitleViewHolder lholder = (TitleViewHolder) holder;

            lholder.itemView.setTag(position);
        }else if(myFileType.getShowType() == 2){
            BlockViewHolder bholder = (BlockViewHolder) holder;

            bholder.itemView.setTag(position);
        }
    }


    //1
    @Override
    public int getItemCount() {
        return fileTypeList.size();
    }

    //2
    @Override
    public int getItemViewType(int position) {
        return fileTypeList.get(position).getShowType();
    }



    //方块种类
    class TypeViewHolder extends RecyclerView.ViewHolder{

        ImageView myImgView;
        TextView myFileName,myFIleSize;

        public TypeViewHolder(View itemView) {
            super(itemView);

            myImgView = itemView.findViewById(R.id.imageView);
            myFileName = itemView.findViewById(R.id.fileName);
            myFIleSize = itemView.findViewById(R.id.fileSize);
        }

        public void onBind(FileType type){
            Glide.with(context)
                    .load(type.getImage())
                    .into(myImgView);
            myFileName.setText(type.getName());
            myFIleSize.setText(type.getSize()+"");
        }
    }

    //标题
    class TitleViewHolder extends RecyclerView.ViewHolder{
        public TitleViewHolder(View itemView) {
            super(itemView);
        }
    }

    //小方块
    class BlockViewHolder extends RecyclerView.ViewHolder{
        public BlockViewHolder(View itemView) {
            super(itemView);
        }
    }
}
