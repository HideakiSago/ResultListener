package jp.hideakisago.resultlistener;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ResultListeners {

    /** onResult の listener 一覧。 */
    private final ArrayList<OnResultListener<?>> mOnResultListeners = new ArrayList<>();

    /**
     * listener を登録します。
     */
    public void registerAll(Object owner) {
        if (owner == null) {
            throw new IllegalArgumentException("args:owner must be non-null");
        }
        try {
            for (Field field : owner.getClass().getDeclaredFields()) {
                if (field.getType().isAssignableFrom(OnResultListener.class)) {
                    field.setAccessible(true);
                    register((OnResultListener<?>) field.get(owner));
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * listener を登録します。
     *
     * @param listener 登録する listener です。
     */
    public void register(OnResultListener<?> listener) {
        if (listener == null) {
            throw new IllegalArgumentException("args:listener must be non-null");
        }
        if (mOnResultListeners.contains(listener)) {
            throw new IllegalArgumentException(
                    "Already registered the same listener. " + listener);
        }

        mOnResultListeners.add(listener);
    }

    /**
     * listener から request code を取得します。
     *
     * @param listener 取得したい request code に対する listener です。
     * @return 引数で指定された listener に対する request code を返します。
     */
    public int requestCodeOf(OnResultListener<?> listener) {
        return mOnResultListeners.indexOf(listener);
    }

    /**
     * requestCode に該当する listener を呼び出します。
     *
     * @param requestCode 結果に対応する request code です。
     * @param resultCode 結果の種類です。
     * @param resultObject 呼び出し先から返却された「結果」です。
     * @param <Result> Result object の型です。
     */
    public <Result> void notifyResult(
            int requestCode, int resultCode, Result resultObject) {
        //noinspection unchecked
        OnResultListener<Result> listener = (OnResultListener<Result>) mOnResultListeners.get(requestCode);
        listener.onResult(resultCode, resultObject);
    }
}
