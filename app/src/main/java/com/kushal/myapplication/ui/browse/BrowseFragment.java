package com.kushal.myapplication.ui.browse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BrowseSupportFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.kushal.myapplication.R;
import com.kushal.myapplication.model.browse.BrowseData;
import com.kushal.myapplication.presenter.browse.BrowseDataPresenter;
import com.kushal.myapplication.ui.video.playlist.VideoPlaylistActivity;
import com.kushal.myapplication.ui.video.playlist.VideoPlaylistFragment;
import com.kushal.myapplication.ui.video.single.VideoPlaybackActivity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BrowseFragment extends BrowseSupportFragment {

    public static final String TAG = "BrowseFragment";
    private ArrayObjectAdapter mCategoryRowAdapter;
    private String[] categoryStrArr = {"Single Video", "Playlist", "Single Image", "Multiple Image"};

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUIElements();
        prepareEntranceTransition();
        mCategoryRowAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        setBrowseData();

        setOnItemViewClickedListener(new ItemClickListener());
    }

    private void setUIElements() {
        setBadgeDrawable(
                getActivity().getResources().getDrawable(R.drawable.logo, null));
        setTitle(getString(R.string.browse_title));
        setHeadersState(HEADERS_ENABLED);

        setBrandColor(ContextCompat.getColor(getActivity(), R.color.default_background));
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private List<BrowseData> getBrowseData() {
        List<BrowseData> browseDataList = null;
        try {
            InputStream inputStream = getResources().getAssets().open("sampleData.json");
            byte[] streamArray = new byte[inputStream.available()];
            inputStream.read(streamArray);
            inputStream.close();

            JsonElement jsonElement = new JsonParser().parse(new String(streamArray));
            Type listType = new TypeToken<List<BrowseData>>() {
            }.getType();

            browseDataList = new Gson().fromJson(jsonElement, listType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return browseDataList;
    }

    private void setBrowseData() {
        List<BrowseData> browseDataList = getBrowseData();

        if (browseDataList != null) {
            for (String categoryName : categoryStrArr) {
                ArrayObjectAdapter arrayAdapter = new ArrayObjectAdapter(new BrowseDataPresenter());
                for (BrowseData data : browseDataList) {
                    if (data.getCategoryName().equals(categoryName)) {
                        arrayAdapter.add(data);
                    }
                }
                HeaderItem header = new HeaderItem(categoryName);
                ListRow row = new ListRow(header, arrayAdapter);
                mCategoryRowAdapter.add(row);
            }

            setAdapter(mCategoryRowAdapter);
        }
    }

    private final class ItemClickListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (row.getHeaderItem().getName().equalsIgnoreCase("Playlist")) {
                ArrayObjectAdapter adapter = (ArrayObjectAdapter) ((ListRow) row).getAdapter();
                ArrayList<BrowseData> dataList = new ArrayList<>();
                for (int i = 0; i < adapter.size(); i++) {
                    dataList.add((BrowseData) adapter.get(i));
                }
                Intent intent = new Intent(getActivity(), VideoPlaylistActivity.class);
                intent.putExtra(VideoPlaylistFragment.PLAYLIST_KEY, dataList);
                startActivity(intent);

            } else if (row.getHeaderItem().getName().equalsIgnoreCase("Single Video") && item instanceof BrowseData) {
                Intent intent = new Intent(getActivity(), VideoPlaybackActivity.class);
                intent.putExtra(BrowseActivity.BROWSE_ITEM, (BrowseData) item);
                startActivity(intent);
            } else if (row.getHeaderItem().getName().equalsIgnoreCase("Single Image")) {
                Toast.makeText(getContext(), "Single image clicked", Toast.LENGTH_SHORT).show();
            } else if (row.getHeaderItem().getName().equalsIgnoreCase("Multiple Image")) {
                Toast.makeText(getContext(), "Multiple image clicked", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
