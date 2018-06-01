//import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.user.myapplication.CameraPage;
import com.example.user.myapplication.R;

public class WebViewFragment extends Fragment {
    private WebView webView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.webview_fragment_layout,container,false);

        webView=(WebView)view.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
      //  webView.loadUrl(CameraPage.weburl);
        return view;
    }
}
