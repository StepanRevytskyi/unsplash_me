package com.aAronQInk.Walls.MVP.contracts;

import com.aAronQInk.Walls.POJO.Collection;

import java.util.List;

public interface CollectionContract {

    interface View extends BaseContract.View {
        void showMoreCollection(List<Collection> collections);
    }

    interface Presenter extends BaseContract.Presenter {
        void onLoadMoreCollection(int type, int page, int per_page);
    }

    interface Repository extends BaseContract.Repository {
        void loadMoreCollection(int type, int page, int per_page);
    }
}
