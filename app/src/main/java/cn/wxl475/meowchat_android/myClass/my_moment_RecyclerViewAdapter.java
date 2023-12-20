package cn.wxl475.meowchat_android.myClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.wxl475.meowchat_android.R;
import cn.wxl475.meowchat_android.pojo.Moment;
import cn.wxl475.meowchat_android.pojo.MomentAllDetail;
import cn.wxl475.meowchat_android.pojo.User;
import cn.wxl475.meowchat_android.pojo.get_comments_result;
import cn.wxl475.meowchat_android.pojo.like_get_count_result;


//让我们的适配器继承自RecyclerView.Adapter<>，并指定泛型为我们适配器的类名.ViewHolder，
// ViewHolder继承自RecyclerView.ViewHolder，并实现每个继承要实现的方法
public class my_moment_RecyclerViewAdapter extends RecyclerView.Adapter<my_moment_RecyclerViewAdapter.ViewHolder> implements View.OnClickListener {
    private final Context context;
    private final MomentAllDetail momentAllDetail;
    //声明一个这个接口的变量
    private onRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = null;

    //构造函数，主要用于接受数据，方便我们在适配器中对数据操作
    public my_moment_RecyclerViewAdapter(Context context, MomentAllDetail momentAllDetail) {
        this.context = context;
        this.momentAllDetail = momentAllDetail;
    }

    //创建ViewHolder，我们需要在这个方法中给新建一个view对象，再初始化一个ViewHolder对象，将view对象传入
    //然后返回一个ViewHolder对象
    @Override
    public my_moment_RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println("create");
        //创建一个view对象（通过布局填充器将布局文件转化为view对象）
        View view = View.inflate(context, R.layout.waterfall_item, null);
        //初始化一个ViewHolder对象，传入view对象
        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(this);
        //将ViewHolder对象返回出去
        return viewHolder;
    }

    //绑定ViewHolder，我们需要在这个方法中给控件设置数据
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(my_moment_RecyclerViewAdapter.ViewHolder holder, int position) {
        System.out.println("bind");
        get_comments_result getCommentsResult = momentAllDetail.getGetCommentsResults().get(position);
        like_get_count_result likeGetCountResult = momentAllDetail.getLikeGetCountResults().get(position);
        List<Moment> moments = momentAllDetail.getGetMomentPreviewsResult().getMoments();
        Moment moment=moments.get(position);
        User user=moment.getUser();
        //给ImageView控件设置数据
        holder.myImageView.setImageURL(moment.getPhotos().get(0));
        holder.moment_title.setText(moment.getTitle());
        holder.moment_avatar.setImageURL(user.getAvatarUrl());
        holder.moment_nickname.setText(user.getNickname());
        Date date = Date.from(Instant.ofEpochSecond(moment.getCreateAt()));
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        StringBuilder stringBuilder = new StringBuilder();
        if(calendar.get(Calendar.YEAR)!= Year.now().getValue()){
            stringBuilder.append(calendar.get(Calendar.YEAR)).append("-");
        }
        stringBuilder.append(calendar.get(Calendar.MONTH)+1).append("-").append(calendar.get(Calendar.DAY_OF_MONTH));
        holder.moment_date.setText(stringBuilder.toString());

        int comment_count=0,like_count=0;
        if(getCommentsResult.getComments()!=null)comment_count=getCommentsResult.getComments().size();
        if(likeGetCountResult.getCount()!=null)like_count=likeGetCountResult.getCount();
        if(comment_count>0 || like_count>0){
            holder.like_and_comment.setVisibility(View.VISIBLE);
            if(comment_count>0){
                holder.moment_commentnum.setVisibility(View.VISIBLE);
                holder.moment_commentnum.setText(Integer.toString(comment_count)+"条回复");
            }
            if(like_count>0) {
                holder.moment_likenum.setVisibility(View.VISIBLE);
                holder.moment_likenum.setText(Integer.toString(like_count)+"位喵友觉得很赞");
            }
        }
        //给每个itemview添加一个Tag,传递数据
        holder.itemView.setTag(moments.get(position));

    }

    //提供给外部调用的方法 刷新数据
    @SuppressLint("NotifyDataSetChanged")
    public void updateData(MomentAllDetail momentAllDetail1){
        //再此处理获得的数据
        momentAllDetail.getGetMomentPreviewsResult().getMoments().addAll(momentAllDetail1.getGetMomentPreviewsResult().getMoments());
        momentAllDetail.getGetCommentsResults().addAll(momentAllDetail1.getGetCommentsResults());
        momentAllDetail.getLikeGetCountResults().addAll(momentAllDetail1.getLikeGetCountResults());
        //最后记得刷新item
        notifyDataSetChanged();
    }

    //获取item的条目总数
    @Override
    public int getItemCount() {
        //直接返回图片数组的长度即可
        return momentAllDetail.getGetMomentPreviewsResult().getMoments().size();
    }

    //将点击事件转移给外面的调用者
    @Override
    public void onClick(View v) {
        if (mOnRecyclerViewItemClickListener != null) {
            //通过v.getTag()接受数据
            mOnRecyclerViewItemClickListener.onItemClick(v, (Moment) v.getTag());
        }

    }

    //我们自定义的ViewHolder类，继承自RecyclerView.ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final my_imageView myImageView;
        private final my_imageView moment_avatar;
        private final TextView moment_title;
        private final TextView moment_nickname;
        private final TextView moment_date;
        private final LinearLayout like_and_comment;
        private final TextView moment_likenum;
        private final TextView moment_commentnum;


        public ViewHolder(View itemView) {
            super(itemView);

            //通过传过来的view对象，我们来实例化控件
            myImageView = itemView.findViewById(R.id.moment_show_img);
            moment_title=itemView.findViewById(R.id.textView_moment_title);
            moment_avatar=itemView.findViewById(R.id.imageView_avatar);
            moment_nickname=itemView.findViewById(R.id.textView_nickname);
            moment_date=itemView.findViewById(R.id.textView_moment_date);
            like_and_comment=itemView.findViewById(R.id.like_and_comment);
            moment_likenum=itemView.findViewById(R.id.textView_moment_likenum);
            moment_commentnum=itemView.findViewById(R.id.textView_moment_commentnum);
        }
    }

    //自定义一个监听的接口，里面包含itemclick的监听方法，主要用于拿数据，方便外部调用拿数据
    public interface onRecyclerViewItemClickListener {
        void onItemClick(View view, Moment moment);
    }

    //定义一个设置Listener的方法（），作用是暴露给外面的调用者，方便调用
    public void setOnRecyclerViewItemClickListener(onRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        mOnRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}