package uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian.feed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import uk.co.mezpahlan.jamesedwardtaylorstheguardian.R;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.model.Fields;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.data.model.Result;

/**
 * RecyclerViewAdapter for TheGuardian.Feed.
 */
public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedRecyclerViewAdapter.FeedViewHolder> {

    private List<Result> itemList = new ArrayList<>(0);
    private FeedFragment.ResultClickListener clickListener;

    public void setClickListener(FeedFragment.ResultClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void updateItems(List<Result> itemList) {
        // Remove any items from list1 that aren't in list 2
        this.itemList.removeAll(itemList);
        // Add list 2 to the remaining items from list 1
        this.itemList.addAll(itemList);
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rss_feed, null);
        return new FeedViewHolder(layoutView, clickListener);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {
        final ImageView thumbnailView = holder.getThumbnailView();
        final TextView headlineView = holder.getHeadlineView();
        final TextView trailTextView = holder.getTrailTextView();

        final Result item = itemList.get(position);
        final Fields fields = item.getFields();
        final String thumbnailUrl = fields.getThumbnail();
        final String headline = fields.getHeadline();
        final String trailText = fields.getTrailText();

        Picasso.with(thumbnailView.getContext())
                .load(thumbnailUrl)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_logo_guardian)
                .error(R.drawable.ic_logo_guardian)
                .into(thumbnailView);

        headlineView.setText(headline);
        trailTextView.setText(trailText);
    }

    @Override
    public int getItemCount() {
        if (itemList != null) {
            return itemList.size();
        } else {
            return 0;
        }
    }

    public class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView thumbnailView;
        private TextView headlineView;
        private TextView trailTextView;
        private TextView publishedOnView;
        private TextView sectionView;

        private FeedFragment.ResultClickListener clickListener;

        public FeedViewHolder(View itemView, FeedFragment.ResultClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;

            thumbnailView = (ImageView) itemView.findViewById(R.id.thumbnail_view);
            headlineView = (TextView) itemView.findViewById(R.id.headline_view);
            trailTextView = (TextView) itemView.findViewById(R.id.trail_text_view);
            publishedOnView = (TextView) itemView.findViewById(R.id.published_on_view);
            sectionView = (TextView) itemView.findViewById(R.id.section_view);

            thumbnailView.setOnClickListener(this);
        }

        public ImageView getThumbnailView() { return thumbnailView; }
        public TextView getHeadlineView() { return headlineView; }
        public TextView getTrailTextView() { return trailTextView; }
        public TextView getPublishedOnView() { return publishedOnView; }
        public TextView getSectionView() { return sectionView; }

        @Override
        public void onClick(View v) {
            clickListener.onResultClick(getPosition());
        }
    }
}