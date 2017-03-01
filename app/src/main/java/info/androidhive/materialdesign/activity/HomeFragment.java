package info.androidhive.materialdesign.activity;

/**
 * Created by Admin
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;


public class HomeFragment extends Fragment {
    private EditText user, pass;
    private ProgressDialog pDialog;
    private Switch oper_switch;
    private String username;
    private String password;
    private ButtonHandler btnHandler = new ButtonHandler();
    private final JSONParser jsonParser = new JSONParser();
    private static final String TAG_SUCCESS = "success";
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        WebView twitter_webview = (WebView) rootView.findViewById(R.id.twitter_webview);
        twitter_webview.setWebViewClient(new WebViewClient());
        twitter_webview.getSettings().setJavaScriptEnabled(true);
        //twitter_webview.setWebChromeClient(new WebChromeClient());
        twitter_webview.loadUrl("https://m.twitter.com/search?q=%23survivorGR&lang=el");


        //Button bLogin = (Button) rootView.findViewById(R.id.login_btn);
        //bLogin.setOnClickListener(btnHandler);

        // Inflate the layout for this fragment
        return rootView;
    }



    class ButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                default:
                    break;
            }
        }
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
