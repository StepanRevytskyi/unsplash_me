package com.aAronQInk.Walls.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.aAronQInk.Walls.ItemDecorator;
import com.aAronQInk.Walls.MVP.contracts.CollectionContract;
import com.aAronQInk.Walls.MVP.models.CollectionRepository;
import com.aAronQInk.Walls.MVP.presenters.CollectionPresenter;
import com.aAronQInk.Walls.POJO.Collection;
import com.aAronQInk.Walls.R;
import com.aAronQInk.Walls.Retrofit.RetrofitClient;
import com.aAronQInk.Walls.adapters.CollectionAdapter;
import com.aAronQInk.Walls.endpoints.UnsplashCollectionAPI;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CollectionsPhotoFragment extends Fragment implements CollectionContract.View {

    @BindView(R.id.collection_recycler) RecyclerView mRecyclerView;
    @BindView(R.id.collection_progress_bar) ProgressBar mProgressBar;
    @BindView(R.id.collection_normal) View mNormalStateView;
    @BindView(R.id.collection_no_internet) View mNoInternetConnectionView;

    private boolean isLoading = true;
    private int page = 1;
    private int per_page = 10;
    private int type = 0;
    private List<Collection> mCollections = new ArrayList<>();

    private CollectionPresenter mPresenter;

    public static CollectionsPhotoFragment getInstance() {
        CollectionsPhotoFragment fragment = new CollectionsPhotoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_collections_photo, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new CollectionPresenter(this, new CollectionRepository(new RetrofitClient().getRetrofitInstance().create(UnsplashCollectionAPI.class)));

        initRecyclerView();
        mPresenter.onLoadMoreCollection(type, page, per_page);
        return view;
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(new CollectionAdapter(mCollections, new ItemDecorator()));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading && ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findLastVisibleItemPosition() == mRecyclerView.getLayoutManager().getItemCount() - 1) {
                    mPresenter.onLoadMoreCollection(type, page, per_page);
                    isLoading = false;
                }
            }
        });
    }

    @OnClick(R.id.retry_button)
    public void onClickRetry() {
        updateOldQuery(0);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.collections_photo_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.collections_all:
                updateOldQuery(0);
                return true;
            case R.id.collections_curated:
                updateOldQuery(1);
                return true;
            case R.id.collections_featured:
                updateOldQuery(2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateOldQuery(int type) {
        page = 1;
        per_page = 10;
        this.type = type;
        mCollections.clear();
        mRecyclerView.getAdapter().notifyDataSetChanged();

        mPresenter.onLoadFirst();
        mPresenter.onLoadMoreCollection(type, page, per_page);
    }

    @Override
    public void showMoreCollection(List<Collection> collections) {
        mCollections.addAll(collections);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoInternetConection() {
        mNormalStateView.setVisibility(View.GONE);
        mNoInternetConnectionView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoInternetConection() {
        mNoInternetConnectionView.setVisibility(View.GONE);
        mNormalStateView.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateQuery() {
        page = page + 1;
        isLoading = true;
    }
}
