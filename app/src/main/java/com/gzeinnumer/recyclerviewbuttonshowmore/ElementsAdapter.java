package com.gzeinnumer.recyclerviewbuttonshowmore;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ElementsAdapter extends RecyclerView.Adapter {

    interface Callbacks {
        public void onClickLoadMore();
    }

    private Callbacks mCallbacks;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private boolean mWithHeader = false;
    private boolean mWithFooter = false;
    private List<Elements> mFeedList;

    public ElementsAdapter(List<Elements> feedList) {
        this.mFeedList = feedList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;

        if (viewType == TYPE_FOOTER) {

            itemView = View.inflate(parent.getContext(), R.layout.row_loadmore, null);
            return new LoadMoreViewHolder(itemView);

        } else {

            itemView = View.inflate(parent.getContext(), R.layout.row_element, null);
            return new ElementsViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof LoadMoreViewHolder) {

            LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder;

            loadMoreViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mCallbacks!=null)
                        mCallbacks.onClickLoadMore();
                }
            });

        } else {
            ElementsViewHolder elementsViewHolder = (ElementsViewHolder) holder;

            Elements elements = mFeedList.get(position);
            elementsViewHolder.icon.setImageResource(elements.getIcon());
            elementsViewHolder.name.setText(elements.getName());
        }

    }

    @Override
    public int getItemCount() {
        int itemCount = mFeedList.size();
        if (mWithHeader)
            itemCount++;
        if (mWithFooter)
            itemCount++;
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (mWithHeader && isPositionHeader(position))
            return TYPE_HEADER;
        if (mWithFooter && isPositionFooter(position))
            return TYPE_FOOTER;
        return TYPE_ITEM;
    }

    public boolean isPositionHeader(int position) {
        return position == 0 && mWithHeader;
    }

    public boolean isPositionFooter(int position) {
        return position == getItemCount() - 1 && mWithFooter;
    }

    public void setWithHeader(boolean value){
        mWithHeader = value;
    }

    public void setWithFooter(boolean value){
        mWithFooter = value;
    }

    public void setCallback(Callbacks callbacks){
        mCallbacks = callbacks;
    }

    public class ElementsViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon;
        private TextView name;

        public ElementsViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }

    public class LoadMoreViewHolder  extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }
    }
}
