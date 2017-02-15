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

        final LinearLayout pass_layout = (LinearLayout) rootView.findViewById(R.id.pass_layout);
        user = (EditText)rootView.findViewById(R.id.login_username);
        pass = (EditText)rootView.findViewById(R.id.login_password);
        oper_switch = (Switch) rootView.findViewById(R.id.operator_switch);
        oper_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(oper_switch.isChecked()){
                    pass_layout.setVisibility(View.VISIBLE);
                    user.setHint("Username or Phone");
                }else {
                    pass_layout.setVisibility(View.GONE);
                    user.setHint("Barcode or Phone");
                }

            }
        });

        ImageButton pass_view = (ImageButton) rootView.findViewById(R.id.show_pass_btn);
        pass_view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        pass.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });
        Button bLogin = (Button) rootView.findViewById(R.id.login_btn);
        bLogin.setOnClickListener(btnHandler);


        Button signup = (Button) rootView.findViewById(R.id.register_btn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent ii = new Intent(HomeFragment.this,MainActivity.class);
                ii.putExtra("operator",false);
                startActivity(ii);*/
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }



    class ButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn:
                    if (user.getText().length() == 0){
                        Toast.makeText(getContext(), "Barcode or Phone", Toast.LENGTH_SHORT).show();
                    }else{
                        username = user.getText().toString();
                        if (oper_switch.isChecked()) {
                            if (pass.getText().length() == 0) {
                                Toast.makeText(getContext(),"Password", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            password = pass.getText().toString();
                            new AttemptLoginForOperator().execute();
                        } else {
                            new AttemptLoginForCustomer().execute();
                        }
                    }
                default:
                    break;
            }
        }
    }

    private class AttemptLoginForOperator extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Logging in.");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            int success;

            try {

                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");

                JSONObject json = jsonParser.getJSONFromUrl(getString(R.string.WEBSITE_URL)+getString(R.string.OPERATOR_LOGIN_URL), params);
                System.out.println(getString(R.string.WEBSITE_URL)+getString(R.string.OPERATOR_LOGIN_URL));
                System.out.println(params);

                success = json.getInt(TAG_SUCCESS);
                System.out.println("TAG SUCCESS : "+ success);
                if (success == 1) {
                    Log.d("Successfully Login!", json.toString());

                   /* Intent ii = new Intent(MainActivity.this,OperatorActivity.class);
                    ii.putExtra("jsonResponse",json.toString());
                    finish();
                    startActivity(ii);*/
                    return "Login ok";
                }else{
                    return "Invalid";

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String message) {

            pDialog.dismiss();
            if (message != null){
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class AttemptLoginForCustomer extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Attempting for login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            int success;

            try {

                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair("username", username));

                Log.d("request!", "starting login for customer");

                JSONObject json = jsonParser.getJSONFromUrl(getString(R.string.WEBSITE_URL)+getString(R.string.CUSTOMER_LOGIN_URL), params);
                System.out.println(getString(R.string.WEBSITE_URL)+getString(R.string.CUSTOMER_LOGIN_URL));
                System.out.println(params);

                success = json.getInt(TAG_SUCCESS);
                System.out.println("TAG SUCCESS : "+ success);
                if (success == 1) {
                    Log.d("Successfully Login!", json.toString());

                    /*Intent ii = new Intent(getContext(),CustomerActivity.class);
                    ii.putExtra("jsonResponse",json.toString());
                    finish();
                    startActivity(ii);*/
                    return "Successfully Login!";
                }else{
                    return "Invalid barcode/phone";

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String message) {

            pDialog.dismiss();
            if (message != null){
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
