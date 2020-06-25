package me.prasad.myreff;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class PoliticsAdapter extends RecyclerView.Adapter<PoliticsAdapter.DataViewHolder> {
    private boolean bVideoIsBeingTouched = false;
    private Handler mHandler = new Handler();
    public static Context context;
    private FrameLayout customViewContainer;
    private WebChromeClient.CustomViewCallback customViewCallback;
    private View mCustomView;
    public ArrayList<AppData> appData=new ArrayList<AppData>();
    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.politics,parent,false);
        DataViewHolder viewHolder=new DataViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DataViewHolder holder, int position) {
        final AppData currentItem=appData.get(position);

       holder.cardView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scaleanimation));
       holder.title.setText(currentItem.getTitle());
        holder.link.setText(currentItem.getLink());
        holder.date.setText(currentItem.getDate());



    }
    public PoliticsAdapter(ArrayList<AppData> appData, Context context)
    {
        this.appData=appData;
        this.context=context;

    }

    @Override
    public int getItemCount() {
        return appData.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        public VideoView videoView;
        public TextView title,link,date;
        public ImageView imageView;
        public View customViewContainer;
        CardView cardView;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardviewPolitics);
            title=itemView.findViewById(R.id.ptitle);
            link=itemView.findViewById(R.id.plink);
            date=itemView.findViewById(R.id.pdate);


        }
    }


}

