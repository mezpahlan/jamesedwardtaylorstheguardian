package uk.co.mezpahlan.oldtimerag.theguardian.feed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import uk.co.mezpahlan.oldtimerag.R;
import uk.co.mezpahlan.oldtimerag.base.GrayscaleTransformation;
import uk.co.mezpahlan.oldtimerag.data.model.search.Fields;
import uk.co.mezpahlan.oldtimerag.data.model.search.Result;

/**
 * RecyclerViewAdapter for TheGuardian.Feed.
 */
public class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedRecyclerViewAdapter.FeedViewHolder> {

    private List<Result> resultList = new ArrayList<>(0);
    private FeedFragment.ResultClickListener clickListener;

    public void setClickListener(FeedFragment.ResultClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void updateResults(List<Result> resultList) {
        // Remove any items from list1 that aren't in list 2
        this.resultList.removeAll(resultList);
        // Add list 2 to the remaining items from list 1
        this.resultList.addAll(resultList);
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
        final TextView publishedOnView = holder.getPublishedOnView();
        final TextView sectionView = holder.getSectionView();

        final Result item = resultList.get(position);
        final Fields fields = item.getFields();
        final String thumbnailUrl = fields.getThumbnail();
        final String headline = stripHTML(fields.getHeadline());
        final String trailText = stripHTML(fields.getTrailText());
        final String publishedOnText = convertDateFormat(item.getWebPublicationDate());
        final String sectionText = item.getSectionName();

        Picasso.with(thumbnailView.getContext())
                .load(thumbnailUrl)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_logo_guardian)
                .error(R.drawable.ic_logo_guardian)
                .transform(new GrayscaleTransformation(Picasso.with(thumbnailView.getContext())))
                .into(thumbnailView);

        headlineView.setText(headline);
        trailTextView.setText(trailText);
        if (trailText.length() < 1) { trailTextView.setVisibility(View.GONE); }
        publishedOnView.setText(publishedOnText);
        sectionView.setText(sectionText);
    }

    private String stripHTML(String input) {
        return input.replaceAll("\\<[^>]*>","");
    }

    private String convertDateFormat(String webPublicationDate) {
        final String year = webPublicationDate.substring(0, 4);
        final String month = webPublicationDate.substring(5,7);
        final String date = webPublicationDate.substring(8,10);

        return date+"-"+month+"-"+year;
    }

    @Override
    public int getItemCount() {
        if (resultList != null) {
            return resultList.size();
        } else {
            return 0;
        }
    }

    private Result getResultWithPosition(int position) {
        return resultList.get(position);
    }

    class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView thumbnailView;
        private TextView headlineView;
        private TextView trailTextView;
        private TextView publishedOnView;
        private TextView sectionView;

        private FeedFragment.ResultClickListener clickListener;

        FeedViewHolder(View itemView, FeedFragment.ResultClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;

            thumbnailView = (ImageView) itemView.findViewById(R.id.thumbnail_view);
            headlineView = (TextView) itemView.findViewById(R.id.headline_view);
            trailTextView = (TextView) itemView.findViewById(R.id.trail_text_view);
            publishedOnView = (TextView) itemView.findViewById(R.id.published_on_view);
            sectionView = (TextView) itemView.findViewById(R.id.section_view);

            itemView.setOnClickListener(this);
        }

        ImageView getThumbnailView() { return thumbnailView; }
        TextView getHeadlineView() { return headlineView; }
        TextView getTrailTextView() { return trailTextView; }
        TextView getPublishedOnView() { return publishedOnView; }
        TextView getSectionView() { return sectionView; }

        @Override
        public void onClick(View v) {
            final int layoutPosition = getLayoutPosition();
            Result result = getResultWithPosition(layoutPosition);
            clickListener.onResultClick(result);
        }
    }
}