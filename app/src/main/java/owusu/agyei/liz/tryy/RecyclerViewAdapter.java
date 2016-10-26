package owusu.agyei.liz.tryy;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by RSL-PROD-003 on 10/25/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter
{
    private Context mContext;
    private String[] friendsList;

    public RecyclerViewAdapter(Context context, String[] friendsList)
    {
        mContext = context;
        this.friendsList = friendsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String friend = (String) getItem(position);
        if ( friend != null ) {
            ((ViewHolder)holder).tvText.setText(friend);
        }
    }

    public Object getItem(int position)
    {
        return friendsList != null && friendsList.length > 0 ? friendsList[position] : null;
    }

    @Override
    public int getItemViewType(int position)
    {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount()
    {
        return friendsList != null ? friendsList.length : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvText;

        public ViewHolder(View view)
        {
            super(view);
            tvText = (TextView) view.findViewById(R.id.tv_text);
            tvText.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL);
        }
    }
}