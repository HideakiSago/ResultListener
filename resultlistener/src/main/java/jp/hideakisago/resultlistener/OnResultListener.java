package jp.hideakisago.resultlistener;

public interface OnResultListener<Result> {
    void onResult(int resultCode, Result result);
}
