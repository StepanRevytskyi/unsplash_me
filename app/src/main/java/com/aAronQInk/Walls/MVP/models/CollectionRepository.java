package com.aAronQInk.Walls.MVP.models;

import com.aAronQInk.Walls.MVP.callbacks.CollectionCallback;
import com.aAronQInk.Walls.MVP.contracts.CollectionContract;
import com.aAronQInk.Walls.POJO.Collection;
import com.aAronQInk.Walls.endpoints.UnsplashCollectionAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionRepository implements CollectionContract.Repository {

    private UnsplashCollectionAPI mUnsplashCollectionAPI;
    private CollectionCallback mCallback;

    public CollectionRepository(UnsplashCollectionAPI unsplashCollectionAPI) {
        mUnsplashCollectionAPI = unsplashCollectionAPI;
    }

    public void setCallback(CollectionCallback callback) {
        mCallback = callback;
    }

    /**
     * @param type If type = 0 -> All
     *             If type = 1 -> Curated
     *             If type = 2 -> Featured
     */
    @Override
    public void loadMoreCollection(int type, int page, int per_page) {
        Call<List<Collection>> call = null;
        switch (type) {
            case 0:
                call = mUnsplashCollectionAPI.getAllCollections(page, per_page);
                break;
            case 1:
                call = mUnsplashCollectionAPI.getCuratedCollections(page, per_page);
                break;
            case 2:
                call = mUnsplashCollectionAPI.getFeaturedCollections(page, per_page);
                break;
        }
        load(call);
    }

    private void load(Call<List<Collection>> call) {
        call.enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if (response.code() == 200) {
                    mCallback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                mCallback.onFailure();
            }
        });
    }
}
