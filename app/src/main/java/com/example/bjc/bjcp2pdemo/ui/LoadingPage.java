package com.example.bjc.bjcp2pdemo.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.bjc.bjcp2pdemo.R;
import com.example.bjc.bjcp2pdemo.util.UIUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


/**
 * Created by 毕静存 on 2016/12/2.
 */

public abstract class LoadingPage extends FrameLayout {
    public static final int STATE_LOADING=0;//正在加载的状态
    public static final int STATE_FAIL=1;// 加载失败
    public static final int STATE_EMPTY=2;// 加载成功但数据为空；
    public static final int STATE_SUCCESS=3;// 加载成功；
    private final Context context;
    public int statecurrent=0;//默认状态是正在加载的状态；
    private View loadView;
    private View errorView;
    private View emptyView;
    private View successView;

    public LoadingPage(Context context) {
        this(context,null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
        showSafePage();
    }

    /**
     * 初始化加载的页面的方法；
     */
    private void init() {
        ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if(loadView==null){
            loadView = UIUtils.xmlToLayout(R.layout.page_loading);
            loadView.setLayoutParams(params);
            addView(loadView);
        }
        if(errorView==null){
            errorView = UIUtils.xmlToLayout(R.layout.page_error);
            errorView.setLayoutParams(params);
            addView(errorView);
        }
        if(emptyView==null){
            emptyView = UIUtils.xmlToLayout(R.layout.page_empty);
            emptyView.setLayoutParams(params);
            addView(emptyView);
        }
        if(successView==null){
            successView = View.inflate(context,layoutId(),null);
            successView.setLayoutParams(params);
            addView(successView);
        }
    }

    /**
     * 保证View的显示在主线程中执行；
     */
    private void showSafePage() {
        UIUtils.runOnUiThread(new Runnable(){
            @Override
            public void run() {
                showStatePage();
            }
        });
    }
    /**
     *  根据状态显示不同的界面；
     */
    private void showStatePage() {
        loadView.setVisibility(statecurrent==STATE_LOADING?View.VISIBLE:View.GONE);
        errorView.setVisibility(statecurrent==STATE_FAIL?View.VISIBLE:View.GONE);
        emptyView.setVisibility(statecurrent==STATE_EMPTY?View.VISIBLE:View.GONE);
        successView.setVisibility(statecurrent==STATE_SUCCESS?View.VISIBLE:View.GONE);
    }
    private AsyncHttpClient httpClient = new AsyncHttpClient();
    private ResultState resultState;
    /**
     * 根据联网的状态，显示界面；
     * @return
     */
    public void show(){
        String url = url();
        if(TextUtils.isEmpty(url)){
            resultState= ResultState.SUCCESS;
            resultState.setContent("");
            loadPage();
            return;
        }
        httpClient.get(url(),new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(String content) {
                Log.e("homefragment",url());
                //1成功了但没有数据；
                if(TextUtils.isEmpty(content)){
                    resultState = ResultState.EMPTY;
                    resultState.state=STATE_EMPTY;
                    resultState.setContent("");
                }else{//2成功了也有数据；
                    resultState= ResultState.SUCCESS;
                    resultState.state=STATE_EMPTY;
                    resultState.setContent(content);
                }
                loadPage();
            }

            @Override
            public void onFailure(Throwable error, String content) {
               //请求数据失败了；
                Log.e("homefragment",error.getMessage()+"");
                resultState= ResultState.ERROR;
                resultState.state=STATE_FAIL;
                resultState.setContent("");
                loadPage();
            }
        });
    }



    /**
     * 根据封装的对象进行页面的显示；
     */
    private void loadPage() {
        switch (resultState){
            case ERROR:
                statecurrent=STATE_FAIL;
                break;
            case EMPTY:
                statecurrent=STATE_EMPTY;
                break;
            case SUCCESS:
                statecurrent=STATE_SUCCESS;
                break;
        }
        showSafePage();
        if(statecurrent==STATE_SUCCESS){
            onSuccess(resultState,successView);
        }

    }
    /**
     * 数据传递给对应的fragment；
     */
    protected abstract void onSuccess(ResultState resultState, View successView);

    /**
     * 请求的参数
     * @return
     */
    protected abstract String parmas();

    /**
     * 请求的路径
     * @return
     */
    protected abstract String url();

    /**
     * 布局的id
     * @return
     */
    public abstract int layoutId();

    /**
     * 通过枚举类来封装对象和对象的状态；
     */
    public enum ResultState{
        ERROR(1),EMPTY(2),SUCCESS(3);

        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
        private String content;
        private int state;
        ResultState(int state){
            this.state=state;
        }
    }
}