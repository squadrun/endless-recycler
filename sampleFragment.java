import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squad.run.R;
import com.squad.run.core.BaseFragment;
import com.squad.run.listener.EndlessRecyclerOnScrollListener;


public class sampleFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private sampleAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

    public static sampleFragment newInstance() {
        return new sampleFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.sample_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new sampleAdapter(activity);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view
                .findViewById(R.id.swiperefresh_sample_fragment);
        onCreateSwipeToRefresh(mSwipeRefreshLayout);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                        activity.getResources().getDisplayMetrics()));

        mSwipeRefreshLayout.setRefreshing(true);

        endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                loadData(current_page);
            }
        };

        mRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        loadData(endlessRecyclerOnScrollListener.getCurrentPage());


    }

    void onCreateSwipeToRefresh(SwipeRefreshLayout refreshLayout){
        /** Colors for refresh animation ****/
        refreshLayout.setColorSchemeResources(
                R.color.swiperefreshlyout_color_1,
                R.color.swiperefreshlyout_color_2, R.color.swiperefreshlyout_color_3,
                R.color.swiperefreshlyout_color_4);
        refreshLayout.setOnRefreshListener(this);

    }


    private void loadData(int current_page) {
        // Load Data
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sample_fragment, container, false);
    }

    @Override
    public void success(Object model, Response res, GlobalVariables.SERVICE_MODE mode) {
    
    }

    @Override
    public void failure(GlobalVariables.SERVICE_MODE mode, String message, int messageCode) {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mAdapter.clearOnRefresh();
        endlessRecyclerOnScrollListener.resetListener();
        loadData(endlessRecyclerOnScrollListener.getCurrentPage());
    }

}
