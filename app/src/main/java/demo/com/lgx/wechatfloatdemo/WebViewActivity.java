package demo.com.lgx.wechatfloatdemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.IFloatWindow;
import com.yhao.floatwindow.MoveType;
import com.yhao.floatwindow.Screen;

import demo.com.lgx.wechatfloatdemo.weghit.ScaleCircleImageView;


/**
 * Created by Harry on 2018/8/8.
 * desc:
 */

public class WebViewActivity extends BaseActivity {

    private ProgressBar progressBar;
    //    private String url = "https://blog.csdn.net/HarryWeasley/article/details/51955467";
//    private String url="http://m.ocj.com.cn/tvLive01?visitorID=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIhdmlzaXRvciEtYmFmMmZhZWEtZWMyMS00OTVjLTk5Nj" +
//        "AtMDc2NDk4NzZlYmU5IiwiaXNzIjoib2NqLXN0YXJza3kiLCJleHAiOjE1NDIwMjc0NjYsImlhdCI6MTUzNDI1MTQ2Nn0.bKWiSgvLanAoDDcQ5OhgeYaOqbHkLT5lVZ_Emp4IsHo";
//    private String url="https://ocj-h5.ocj.com.cn/shopllMobile/shopllMobileappIndex.html";
//    private String url="https://ocj-test5.ocj.com.cn/shopllMobile/shopllMobileappIndex.html";
//    private String url="https://gitbook.cn/";
    private String url = "https://www.baidu.com";
    private ScaleCircleImageView frameLayout;
    private WebView webView;
    private FrameLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        frameLayout = findViewById(R.id.parent);
        progressBar = findViewById(R.id.progress);
        webView = findViewById(R.id.web_view);
        parent = findViewById(R.id.frame_layout);
        Button back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void onBackPressed() {

        Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show();
        IFloatWindow old = FloatWindow.get("old");
        if (old == null) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.mipmap.ic_launcher_round);
            FloatWindow
                    .with(getApplicationContext())
                    .setTag("old")
                    .setView(imageView)
//                .setWidth(Screen.width, 0.2f) //设置悬浮控件宽高
//                .setHeight(Screen.width, 0.2f)
                    .setMoveType(MoveType.slide, 0, 0)
                    .setWidth(75)
                    .setFilter(false, WebViewActivity.class)
                    .setHeight(75)
                    .setX(Screen.width, 0.8f)  //设置控件初始位置
                    .setY(parent.getHeight() / 3)
                    .setMoveStyle(300, new AccelerateInterpolator())
                    .setDesktopShow(false)
                    .build();
            View view = parent;
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
//            frameLayout.startAnimation(0, frameLayout.getWidth() - 150, 0, frameLayout.getHeight() / 2, 0, 75, bitmap);
            old = FloatWindow.get("old");
            frameLayout
                    .createAnmiationParam()
                    .setFromLeftX(0)
                    .setToLeftX(old.getmB().xOffset)
                    .setFromRightX(parent.getWidth())
                    .setToRightX(old.getmB().xOffset + old.getmB().mWidth)
                    .setFromTopY(0)
                    .setToTopY(old.getmB().yOffset)
                    .setFromBottomY(parent.getHeight())
                    .setFromRadius(0)
                    .setToRadius(old.getmB().mWidth / 2)
                    .setToBottomY(old.getmB().yOffset + old.getmB().mWidth);
            frameLayout.startAnimation(bitmap, old.getmB().mWidth);
            webView.setVisibility(View.GONE);
            frameLayout.setScaleCircleListener(new ScaleCircleImageView.ScaleCircleListener() {
                @Override
                public void onAnimationEnd() {
                    finish();
                }

            });
        }

    }
}
