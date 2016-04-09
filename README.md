# ResultListener

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
    private final OnResultListener<Bundle> mOnSettingsActivity = new OnResultListener<Bundle>() {
        @Override
        public void onResult(int resultCode, Bundle bundle) {
            Toast.makeText(MainActivity.this, "onResult", Toast.LENGTH_SHORT).show();
        }
    };

    ... {
        startActivityForResult(new Intent(...), mOnSettingsActivity);
    }
```
