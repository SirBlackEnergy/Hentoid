package me.devsaki.hentoid.database;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import me.devsaki.hentoid.database.domains.Content;
import me.devsaki.hentoid.util.LogHelper;

/**
 * Created by avluis on 04/12/2016.
 * Grabs content from db with provided query
 */
public class SearchContent {
    private static final String TAG = LogHelper.makeLogTag(SearchContent.class);

    private final HentoidDB db;
    private final String mQuery;
    private final int mPage;
    private final int mQty;
    private final boolean mOrder;
    private volatile State mCurrentState = State.NON_INITIALIZED;
    private List<Content> contentList = new ArrayList<>();

    public SearchContent(final Context context, String query, int page, int qty, boolean order) {
        db = HentoidDB.getInstance(context);
        mQuery = query;
        mPage = page;
        mQty = qty;
        mOrder = order;
    }

    public List<Content> getContent() {
        return contentList;
    }

    public void retrieveResults(final ContentListener listener) {
        LogHelper.d(TAG, "Retrieving results.");

        if (mCurrentState == State.READY) {
            listener.onContentReady(true);
            listener.onContentFailed(false);
            return;
        } else if (mCurrentState == State.FAILED) {
            listener.onContentReady(false);
            listener.onContentFailed(true);
            return;
        }

        mCurrentState = State.INITIALIZING;

        new AsyncTask<Void, Void, State>() {

            @Override
            protected State doInBackground(Void... params) {
                retrieveContent();
                return mCurrentState;
            }

            @Override
            protected void onPostExecute(State current) {
                if (listener != null) {
                    listener.onContentReady(current == State.READY);
                    listener.onContentFailed(current == State.FAILED);
                }
            }
        }.execute();
    }

    private synchronized void retrieveContent() {
        LogHelper.d(TAG, "Retrieving content.");
        try {
            if (mCurrentState == State.INITIALIZING) {
                mCurrentState = State.INITIALIZED;

                contentList = db.selectContentByQuery(mQuery, mPage, mQty, mOrder);
                mCurrentState = State.READY;
            }
        } catch (Exception e) {
            LogHelper.e(TAG, "Could not load data from db: ", e);
        } finally {
            if (mCurrentState != State.READY) {
                // Something bad happened!
                LogHelper.w(TAG, "Failed...");
                mCurrentState = State.FAILED;
            }
        }
    }

    enum State {
        NON_INITIALIZED, INITIALIZING, INITIALIZED, READY, FAILED
    }

    public interface ContentListener {
        void onContentReady(boolean success);

        void onContentFailed(boolean failure);
    }
}
