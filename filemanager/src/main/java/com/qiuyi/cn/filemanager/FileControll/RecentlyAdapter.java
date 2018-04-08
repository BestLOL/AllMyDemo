package com.qiuyi.cn.filemanager.FileControll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qiuyi.cn.filemanager.R;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 * 最近模块的Adapter
 */
public class RecentlyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<FileInfo> listFile;

    public RecentlyAdapter(Context context, List<FileInfo> listFile){
        this.context = context;
        this.listFile = listFile;
    }

    //3 创建布局
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder mViewHolder = null;
        if(viewType == 0){
            //标题栏
            view = View.inflate(context,R.layout.pager_recently_adapter_title_layout,null);
            mViewHolder = new titleViewHolder(view);
        }else if(viewType == 1){
            //文件展示部分 图片文件
            view = View.inflate(context,R.layout.pager_recently_adapter_image_layout,null);
            mViewHolder = new myImageView(view);
        }else if(viewType == 2){
            //文本文件，native_zar
            view = View.inflate(context,R.layout.pager_recently_adapter_file_layout,null);
            mViewHolder = new myFileView(view);
        }else{
            //间隔
            view = View.inflate(context, R.layout.pager_recently_adapter_line_layout,null);
            mViewHolder = new linePart(view);
        }
        return mViewHolder;
    }

    //4 数据填充判断
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FileInfo info = listFile.get(position);

        //调用getType来判断
        if(getItemViewType(position)==0){
            //标题
            titleViewHolder myholder = (titleViewHolder) holder;
            myholder.onBind(info);
        }
        if(getItemViewType(position)==1){
            //native_img
            myImageView myholder = (myImageView) holder;
            myholder.onBind(info);
        }
        if(getItemViewType(position)==2){
            //文件
            myFileView myholder = (myFileView) holder;
            myholder.onBind(info);
        }
        if(getItemViewType(position)==3){
            //间隔
            linePart myholder = (linePart) holder;
        }
    }

    //1
    @Override
    public int getItemCount() {
        return listFile.size();
    }

    //2
    @Override
    public int getItemViewType(int position) {
        return listFile.get(position).getType();
    }


    //标题栏
    class titleViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgFlag;
        private TextView myTitle;
        private TextView fileDate;

        public titleViewHolder(View itemView) {
            super(itemView);
            imgFlag = itemView.findViewById(R.id.imgFlag);
            myTitle = itemView.findViewById(R.id.mtTitle);
            fileDate = itemView.findViewById(R.id.fileDate);
        }

        public void onBind(FileInfo fileInfo){
            imgFlag.setImageResource(R.drawable.qq);
            //fileInfo.getPath()
/*            Glide.with(context)
                    .load(R.drawable.qq)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgFlag);*/
/*            myTitle.setText(fileInfo.getTitle());
            fileDate.setText(fileInfo.getDate());*/
        }
    }

    //图片存放
    class myImageView extends RecyclerView.ViewHolder{

        private ImageView myImageView;

        public myImageView(View itemView) {
            super(itemView);
            myImageView = itemView.findViewById(R.id.myImageView);
        }

        public void onBind(FileInfo info){
            //info.getPath()
            Glide.with(context)
                    .load(R.drawable.qq)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(myImageView);
        }
    }

    //文件存放
    class myFileView extends RecyclerView.ViewHolder{

        private TextView fileName,fileSize;
        private ImageView imageView;

        public myFileView(View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.fileName);
            fileSize = itemView.findViewById(R.id.fileSize);
            imageView = itemView.findViewById(R.id.imageView);
        }

        public void onBind(FileInfo info){
/*            fileName.setText(info.getName());
            fileSize.setText(info.getSize());*/
            Glide.with(context)
                    .load(R.drawable.qq)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    //间距
    class linePart extends RecyclerView.ViewHolder{

        private LinearLayout myLinearyout;
        public linePart(View itemView) {
            super(itemView);
            myLinearyout = itemView.findViewById(R.id.myLinearyout);
        }
    }

}
