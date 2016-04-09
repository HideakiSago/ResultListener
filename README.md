# ResultListener

Android のイケてない設計の一つである requestCode を listener 化します。

## Download

build.gradle 未対応

## Usage

```
    private final ResultListeners mListeners = new ResultListeners();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListeners.registerAll(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mListeners.notifyResult(requestCode, resultCode, data.getExtras());
    }

    public void startActivityForResult(Intent intent, OnResultListener<?> onResult) {
        super.startActivityForResult(intent, mListeners.requestCodeOf(onResult));
    }
```

```
    private final OnResultListener<Bundle> mOnActivityResult = new OnResultListener<Bundle>() {
        @Override
        public void onResult(int resultCode, Bundle bundle) {
            Toast.makeText(MainActivity.this, "onResult", Toast.LENGTH_SHORT).show();
        }
    };

    ... {
        startActivityForResult(new Intent(...), mOnActivityResult);
    }
```

- **Caution:** Listener はすべて field に保持しなければいけません。 仕組み上、匿名 class は期待するような動きをしません。
- 上記の例では result の型を `Bundle` として扱っていますが、 `Intent` でも構いませんし、うまく設計して POJO にしても構いません。
- Lambda 式を使えるようにし、 listener は method 参照することをおすすめします。
