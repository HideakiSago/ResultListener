package jp.hideakisago.resultlistener;

import android.app.Activity;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ResultListenersTest {
    @Test
    public void normal() throws Exception {
        NormalData data = new NormalData();
        ResultListeners target = new ResultListeners();

        target.registerAll(data);
        assertThat(target.getNumListeners(), is(3));
        assertThat(target.requestCodeOf((OnResultListener<?>) data.mObjectListener), is(0));
        assertThat(target.requestCodeOf((OnResultListener<?>) data.mFinalObjectListener), is(0));
        assertThat(target.requestCodeOf(data.mOnResultListener), is(1));
        assertThat(target.requestCodeOf(data.mOnResultListenerAnonymous), is(2));

        MockOnResultListener.initCalled(data.mFinalObjectListener);
        target.notifyResult(0, Activity.RESULT_OK, null);
        assertThat(MockOnResultListener.isCalled(data.mFinalObjectListener), is(true));

        MockOnResultListener.initCalled(data.mOnResultListener);
        target.notifyResult(1, Activity.RESULT_OK, null);
        assertThat(MockOnResultListener.isCalled(data.mOnResultListener), is(true));

        target.notifyResult(2, Activity.RESULT_OK, null);

        OnResultListener l = new OnResultListener<Object>() {
            @Override
            public void onResult(int resultCode, Object o) {
            }
        };
        target.register(l);
        assertThat(target.getNumListeners(), is(4));
        assertThat(target.requestCodeOf(l), is(3));
        target.notifyResult(3, Activity.RESULT_OK, null);

        target.notifyResult(Integer.MAX_VALUE, Activity.RESULT_OK, null);
        target.notifyResult(Integer.MIN_VALUE, Activity.RESULT_OK, null);
        // fragment request code
        target.notifyResult(0x00010000, Activity.RESULT_OK, null);
    }

    @Test
    public void abnormal() throws Exception {
        ResultListeners target = new ResultListeners();

        assertThat(target.requestCodeOf(null), is(-1));
        assertThat(target.requestCodeOf(new MockOnResultListener()), is(-1));

        assertThat(target.getNumListeners(), is(0));

        target.notifyResult(Integer.MAX_VALUE, Activity.RESULT_OK, null);
        target.notifyResult(Integer.MIN_VALUE, Activity.RESULT_OK, null);
        // fragment request code
        target.notifyResult(0x00010000, Activity.RESULT_OK, null);
    }

    static class NormalData {
        private static final int CONSTANT = 1;
        private final int mField = 1;
        private Object mField2 = "";
        private Object mObjectListener = new MockOnResultListener();
        private final Object mFinalObjectListener = mObjectListener;
        private OnResultListener mNull = null;
        private OnResultListener mOnResultListener = new MockOnResultListener();
        private OnResultListener mOnResultListenerAnonymous = new OnResultListener<Object>() {
            @Override
            public void onResult(int resultCode, Object o) {
            }
        };
    }

    static class MockOnResultListener implements OnResultListener<Object> {
        boolean called = false;

        @Override
        public void onResult(int resultCode, Object o) {
            called = true;
        }

        static void initCalled(Object o) {
            ((MockOnResultListener) o).called = false;
        }

        static boolean isCalled(Object o) {
            return ((MockOnResultListener) o).called;
        }
    }
}
