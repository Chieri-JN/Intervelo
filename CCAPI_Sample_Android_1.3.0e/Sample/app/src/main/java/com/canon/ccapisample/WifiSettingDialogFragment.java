package com.canon.ccapisample;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.canon.ccapisample.Constants.CCAPI.Field.AUTHENTICATION;
import static com.canon.ccapisample.Constants.CCAPI.Field.CHANNEL;
import static com.canon.ccapisample.Constants.CCAPI.Field.ENCRYPTION;
import static com.canon.ccapisample.Constants.CCAPI.Field.GATEWAY;
import static com.canon.ccapisample.Constants.CCAPI.Field.IPADDRESS;
import static com.canon.ccapisample.Constants.CCAPI.Field.IPADDRESSSET;
import static com.canon.ccapisample.Constants.CCAPI.Field.IPV4_GATEWAY;
import static com.canon.ccapisample.Constants.CCAPI.Field.IPV4_IPADDRESS;
import static com.canon.ccapisample.Constants.CCAPI.Field.IPV4_IPADDRESSSET;
import static com.canon.ccapisample.Constants.CCAPI.Field.IPV4_SUBNETMASK;
import static com.canon.ccapisample.Constants.CCAPI.Field.IPV6_GATEWAY;
import static com.canon.ccapisample.Constants.CCAPI.Field.IPV6_MANUAL_ADDRESS;
import static com.canon.ccapisample.Constants.CCAPI.Field.IPV6_MANUAL_SETTING;
import static com.canon.ccapisample.Constants.CCAPI.Field.IPV6_PREFIXLENGTH;
import static com.canon.ccapisample.Constants.CCAPI.Field.IPV6_USEIPV6;
import static com.canon.ccapisample.Constants.CCAPI.Field.KEYINDEX;
import static com.canon.ccapisample.Constants.CCAPI.Field.METHOD;
import static com.canon.ccapisample.Constants.CCAPI.Field.PASSWORD;
import static com.canon.ccapisample.Constants.CCAPI.Field.SSID;
import static com.canon.ccapisample.Constants.CCAPI.Field.SUBNETMASK;
import static com.canon.ccapisample.Constants.CCAPI.Field.WIFI_SETTINGS;
import static com.canon.ccapisample.Constants.CCAPI.Method.DELETE;
import static com.canon.ccapisample.Constants.CCAPI.Method.GET;
import static com.canon.ccapisample.Constants.CCAPI.Method.PUT;
import static com.canon.ccapisample.Constants.CCAPI.VER100;
import static com.canon.ccapisample.Constants.CCAPI.Value.AES;
import static com.canon.ccapisample.Constants.CCAPI.Value.AUTO;
import static com.canon.ccapisample.Constants.CCAPI.Value.CAMERAAP;
import static com.canon.ccapisample.Constants.CCAPI.Value.DISABLE;
import static com.canon.ccapisample.Constants.CCAPI.Value.ENABLE;
import static com.canon.ccapisample.Constants.CCAPI.Value.INFRASTRUCTURE;
import static com.canon.ccapisample.Constants.CCAPI.Value.MANUAL;
import static com.canon.ccapisample.Constants.CCAPI.Value.NONE;
import static com.canon.ccapisample.Constants.CCAPI.Value.OPEN;
import static com.canon.ccapisample.Constants.CCAPI.Value.SHAREDKEY;
import static com.canon.ccapisample.Constants.CCAPI.Value.TKIPAES;
import static com.canon.ccapisample.Constants.CCAPI.Value.WEP;
import static com.canon.ccapisample.Constants.CCAPI.Value.WPAWPA2PSK;
import static com.canon.ccapisample.Constants.CCAPI.Value.WPAWPA2WPA3PERSONAL;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WifiSettingDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WifiSettingDialogFragment extends DialogFragment implements WebAPIResultListener, RadioGroup.OnCheckedChangeListener {
    private static final String TAG = WifiSettingDialogFragment.class.getSimpleName();
    private static final String API_KEY = "ApiKey";
    private WebAPI mWebAPI;
    private APIDataSet mApiDataSet;

    private final Map<String, Integer> mViewIDMap = new HashMap<String, Integer>() {
        {
            put(SSID, R.id.SsidEdit );
            put(METHOD, R.id.MethodRadio );
            put(CHANNEL, R.id.ChannelSpinner );
            put(AUTHENTICATION, R.id.AuthenticationRadio );
            put(ENCRYPTION, R.id.EncryptionRadio );
            put(KEYINDEX, R.id.KeyindexSpinner );
            put(PASSWORD, R.id.PasswordEdit );
            put(IPADDRESSSET, R.id.IpaddresssetRadio );
            put(IPADDRESS, R.id.IpaddressEdit );
            put(SUBNETMASK, R.id.SubnetmaskEdit );
            put(GATEWAY, R.id.GatewayEdit );
            put(IPV4_IPADDRESSSET, R.id.Ipv4_IpaddresssetRadio );
            put(IPV4_IPADDRESS, R.id.Ipv4_IpaddressEdit );
            put(IPV4_SUBNETMASK, R.id.Ipv4_SubnetmaskEdit );
            put(IPV4_GATEWAY, R.id.Ipv4_GatewayEdit );
            put(IPV6_USEIPV6, R.id.Ipv6_UseIpv6Radio );
            put(IPV6_MANUAL_SETTING, R.id.Ipv6_Manual_SettingRadio );
            put(IPV6_MANUAL_ADDRESS, R.id.Ipv6_Manual_AddressEdit );
            put(IPV6_PREFIXLENGTH, R.id.Ipv6_PrefixLengthEdit );
            put(IPV6_GATEWAY, R.id.Ipv6_GatewayEdit );
        }
    };

    private final Map<String, Integer> mMethodRadioIDMap = new HashMap<String, Integer>() {
        {
            put(INFRASTRUCTURE, R.id.InfrastructureRadioButton );
            put(CAMERAAP, R.id.CameraapRadioButton );
        }
    };

    private final Map<String, Integer> mAuthenticationRadioIDMap = new HashMap<String, Integer>() {
        {
            put(OPEN, R.id.OpenRadioButton );
            put(SHAREDKEY, R.id.SharedKeyRadioButton );
            put(WPAWPA2PSK, R.id.Wpawpa2pskRadioButton );
            put(WPAWPA2WPA3PERSONAL, R.id.Wpawpa2wpa3personalRadioButton );
        }
    };

    private final Map<String, Integer> mEncryptionRadioIDMap = new HashMap<String, Integer>() {
        {
            put(NONE, R.id.NoneRadioButton );
            put(WEP, R.id.WepRadioButton );
            put(TKIPAES, R.id.TkipaesRadioButton );
            put(AES, R.id.AesRadioButton );
        }
    };

    private final Map<String, Integer> mIpaddresssetRadioIDMap = new HashMap<String, Integer>() {
        {
            put(AUTO, R.id.AutoRadioButton );
            put(MANUAL, R.id.ManualRadioButton );
        }
    };

    private final Map<String, Integer> mIpv4_IpaddresssetRadioIDMap = new HashMap<String, Integer>() {
        {
            put(AUTO, R.id.Ipv4_Ipaddressset_AutoRadioButton );
            put(MANUAL, R.id.Ipv4_Ipaddressset_ManualRadioButton );
        }
    };

    private final Map<String, Integer> mIpv6_UseIpv6RadioIDMap = new HashMap<String, Integer>() {
        {
            put(ENABLE, R.id.Ipv6_UseIpv6_EnableRadioButton);
            put(DISABLE, R.id.Ipv6_UseIpv6_DisableRadioButton);
        }
    };

    private final Map<String, Integer> mIpv6_Manual_SettingRadioIDMap = new HashMap<String, Integer>() {
        {
            put(ENABLE, R.id.Ipv6_Manual_Setting_EnableRadioButton );
            put(DISABLE, R.id.Ipv6_Manual_Setting_DisableRadioButton );
        }
    };

    private final Map<String, Map<String, Integer>> mRadioIDMap = new HashMap<String, Map<String, Integer>>(){
        {
            put(METHOD, mMethodRadioIDMap );
            put(AUTHENTICATION, mAuthenticationRadioIDMap );
            put(ENCRYPTION, mEncryptionRadioIDMap );
            put(IPADDRESSSET, mIpaddresssetRadioIDMap );
            put(IPV4_IPADDRESSSET, mIpv4_IpaddresssetRadioIDMap );
            put(IPV6_USEIPV6, mIpv6_UseIpv6RadioIDMap );
            put(IPV6_MANUAL_SETTING, mIpv6_Manual_SettingRadioIDMap );
        }
    };

    public WifiSettingDialogFragment() {
        // Required empty public constructor
    }

    public static WifiSettingDialogFragment newInstance(Fragment target, String key) {
        WifiSettingDialogFragment fragment = new WifiSettingDialogFragment();
        fragment.setTargetFragment(target, 0);
        Bundle args = new Bundle();
        args.putString(API_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle arguments) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        mWebAPI = WebAPI.getInstance();
        String apiKey = getArguments().getString(API_KEY);
        mApiDataSet = mWebAPI.getAPIData(apiKey);

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_wifi_setting, null);
        LinearLayout layout = view.findViewById(R.id.WifiSettings);

        // Get current values.
        if (mApiDataSet != null && mApiDataSet.isGetable()) {
            Bundle args = new Bundle();
            String[] params = new String[]{GET, mApiDataSet.getUrl(), null};
            args.putStringArray(Constants.RequestCode.ACT_WEB_API.name(), params);
            mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.ACT_WEB_API, args, this));
        }

        dialog.setTitle(WIFI_SETTINGS);
        dialog.setView(layout);
        dialog.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                setWifiSetting();
            }
        });
        dialog.setNegativeButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetWifiSetting();
            }
        });

        RadioGroup methodRadio = view.findViewById(R.id.MethodRadio);
        methodRadio.setOnCheckedChangeListener(this);
        RadioGroup authenticationRadio = view.findViewById(R.id.AuthenticationRadio);
        authenticationRadio.setOnCheckedChangeListener(this);
        RadioGroup encryptionRadio = view.findViewById(R.id.EncryptionRadio);
        encryptionRadio.setOnCheckedChangeListener(this);
        RadioGroup ipAddressSetRadio = view.findViewById(R.id.IpaddresssetRadio);
        ipAddressSetRadio.setOnCheckedChangeListener(this);

        return dialog.create();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        Log.d(TAG, "onCheckedChanged");
        Dialog view = getDialog();
        if(view != null) {
            if(group.getId() == R.id.MethodRadio ||
                group.getId() == R.id.AuthenticationRadio ||
                group.getId() == R.id.EncryptionRadio ||
                group.getId() == R.id.IpaddresssetRadio ||
                group.getId() == R.id.Ipv4_IpaddresssetRadio ||
                group.getId() == R.id.Ipv6_UseIpv6Radio ||
                group.getId() == R.id.Ipv6_Manual_SettingRadio){
                // Change Visibility of the View in accordance with the current value.
                setViewVisibility(view);

            }
        }
    }

    /**
     * Callback from execute WebAPI
     * @param result HTTP Request result
     */
    @Override
    public void onWebAPIResult(WebAPIResultDataSet result){
        Log.d(TAG, String.format("%s onWebAPIResult", result.getRequestCode()));
        Context context = getActivity();

        // Do nothing, if life cycle of the fragment is finished.
        if(context != null) {
            if (result.isError()) {
                Toast.makeText(context, result.getErrorMsg(), Toast.LENGTH_SHORT).show();
            }
            else {
                switch (result.getRequestCode()) {
                    case ACT_WEB_API:
                        setValue(result.getResponseBody());
                        break;
                    default:
                        break;
                }
            }
        }
        else{
            Log.d(TAG, String.format("%s Activity is Null.", result.getRequestCode()));
        }
    }

    private void setWifiSetting(){
        JSONObject body = new JSONObject();

        for (String name : mViewIDMap.keySet()) {
            try {
                body.put(name, getValue(name));
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG, body.toString());

        if (mApiDataSet != null && mApiDataSet.isPutable()) {
            Bundle args = new Bundle();
            String[] params = new String[]{PUT, mApiDataSet.getUrl(), body.toString()};
            args.putStringArray(Constants.RequestCode.ACT_WEB_API.name(), params);

            // Execute the API.
            // The onWebAPIResult() of caller Fragment process the execution result, because the dialog is closed.
            mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.ACT_WEB_API, args, new WebAPIResultListener() {
                @Override
                public void onWebAPIResult(WebAPIResultDataSet result) {
                    if (getTargetFragment() instanceof WebAPIResultListener) {
                        ((WebAPIResultListener) getTargetFragment()).onWebAPIResult(result);
                    }
                }
            }));
        }
    }

    private void resetWifiSetting(){

        // Execute the API.
        // The onWebAPIResult() of caller Fragment process the execution result, because the dialog is closed.
        if (mApiDataSet != null && mApiDataSet.isPutable()) {
            Bundle args = new Bundle();
            String[] params = new String[]{DELETE, mApiDataSet.getUrl(), null};
            args.putStringArray(Constants.RequestCode.ACT_WEB_API.name(), params);

            // Execute the API.
            // The onWebAPIResult() of caller Fragment process the execution result, because the dialog is closed.
            mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.ACT_WEB_API, args, new WebAPIResultListener() {
                @Override
                public void onWebAPIResult(WebAPIResultDataSet result) {
                    if (getTargetFragment() instanceof WebAPIResultListener) {
                        ((WebAPIResultListener) getTargetFragment()).onWebAPIResult(result);
                    }
                }
            }));
        }
    }

    private String getValue(String name){
        String value = "";
        Dialog view = getDialog();

        if(view != null) {
            int id = mViewIDMap.get(name);
            View tempView = view.findViewById(id);

            if (tempView.isShown()) {
                switch (name) {
                    case SSID:
                    case PASSWORD:
                    case IPADDRESS:
                    case SUBNETMASK:
                    case GATEWAY:
                    case IPV4_IPADDRESS:
                    case IPV4_SUBNETMASK:
                    case IPV4_GATEWAY:
                    case IPV6_MANUAL_ADDRESS:
                    case IPV6_PREFIXLENGTH:
                    case IPV6_GATEWAY:
                        EditText editText = (EditText) tempView;
                        value = editText.getText().toString();
                        break;

                    case METHOD:
                    case AUTHENTICATION:
                    case ENCRYPTION:
                    case IPADDRESSSET:
                    case IPV4_IPADDRESSSET:
                    case IPV6_USEIPV6:
                    case IPV6_MANUAL_SETTING:
                        RadioGroup radioGroup = (RadioGroup) tempView;
                        int radioID = radioGroup.getCheckedRadioButtonId();

                        for (String key : mRadioIDMap.get(name).keySet()) {
                            if (radioID == mRadioIDMap.get(name).get(key)) {
                                value = key;
                                break;
                            }
                        }
                        break;

                    case CHANNEL:
                    case KEYINDEX:
                        Spinner spinner = (Spinner) tempView;
                        value = spinner.getSelectedItem().toString();
                        break;

                    default:
                        break;
                }
            }
            else {
                value = "";
            }
        }

        return value;
    }

    private void setValue(String responseBody){
        Dialog view = getDialog();

        if(view != null) {
            try {
                JSONObject response = new JSONObject(responseBody);
                Iterator<String> keys = response.keys();

                // Reflect the current value in the View.
                while (keys.hasNext()) {
                    String name = keys.next();
                    String value = response.getString(name);

                    if(mViewIDMap.containsKey(name)){
                        int id = mViewIDMap.get(name);
                        switch (name) {
                            case SSID:
                            case PASSWORD:
                            case IPADDRESS:
                            case SUBNETMASK:
                            case GATEWAY:
                            case IPV4_IPADDRESS:
                            case IPV4_SUBNETMASK:
                            case IPV4_GATEWAY:
                            case IPV6_MANUAL_ADDRESS:
                            case IPV6_PREFIXLENGTH:
                            case IPV6_GATEWAY:
                                EditText editText = view.findViewById(id);
                                editText.setText(value);
                                break;

                            case METHOD:
                            case AUTHENTICATION:
                            case ENCRYPTION:
                            case IPADDRESSSET:
                            case IPV4_IPADDRESSSET:
                            case IPV6_USEIPV6:
                            case IPV6_MANUAL_SETTING:
                                RadioGroup radioGroup = view.findViewById(id);
                                if(mRadioIDMap.containsKey(name)){
                                    for (String key : mRadioIDMap.get(name).keySet()) {
                                        if(key.equals(value)){
                                            radioGroup.check(mRadioIDMap.get(name).get(key));
                                            break;
                                        }
                                    }
                                }
                                break;

                            case CHANNEL:
                            case KEYINDEX:
                                Spinner spinner = view.findViewById(id);
                                for (int i = 0; i < spinner.getCount(); i++){
                                    if (spinner.getItemAtPosition(i).toString().equals(value)){
                                        spinner.setSelection(i);
                                        break;
                                    }
                                }
                                break;

                            default:
                                break;
                        }
                    }
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            // Change Visibility of the View in accordance with the current value.
            setViewVisibility(view);

            // Register the OnCheckedChangeListener after reflection of the current value.
            RadioGroup methodRadio = view.findViewById(R.id.MethodRadio);
            methodRadio.setOnCheckedChangeListener(this);
            RadioGroup authenticationRadio = view.findViewById(R.id.AuthenticationRadio);
            authenticationRadio.setOnCheckedChangeListener(this);
            RadioGroup encryptionRadio = view.findViewById(R.id.EncryptionRadio);
            encryptionRadio.setOnCheckedChangeListener(this);
            RadioGroup ipAddressSetRadio = view.findViewById(R.id.IpaddresssetRadio);
            ipAddressSetRadio.setOnCheckedChangeListener(this);
            RadioGroup ipv4AddressSetRadio = view.findViewById(R.id.Ipv4_IpaddresssetRadio);
            ipv4AddressSetRadio.setOnCheckedChangeListener(this);
            RadioGroup UseIpv6Radio = view.findViewById(R.id.Ipv6_UseIpv6Radio);
            UseIpv6Radio.setOnCheckedChangeListener(this);
            RadioGroup ipv6ManualSettingRadio = view.findViewById(R.id.Ipv6_Manual_SettingRadio);
            ipv6ManualSettingRadio.setOnCheckedChangeListener(this);
        }
    }

    private void setViewVisibility(Dialog view){

        RadioGroup methodRadio = view.findViewById(R.id.MethodRadio);
        RadioGroup authenticationRadio = view.findViewById(R.id.AuthenticationRadio);
        RadioGroup encryptionRadio = view.findViewById(R.id.EncryptionRadio);

        int methodID = methodRadio.getCheckedRadioButtonId();
        int authenticationID = authenticationRadio.getCheckedRadioButtonId();
        int encryptionID = encryptionRadio.getCheckedRadioButtonId();

        // Change the display of channel, authentication, encryption in accordance with the methodID.
        if(methodID == R.id.InfrastructureRadioButton){
            view.findViewById(R.id.ChannelLayout).setVisibility(View.GONE);
            view.findViewById(R.id.AuthenticationLayout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.KeyIndexLayout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.EncryptionLayout).setVisibility(View.VISIBLE);

            // Change the display of the encryption RadioButton in accordance with the authenticationID.
            if(authenticationID == R.id.OpenRadioButton){
                view.findViewById(R.id.NoneRadioButton).setEnabled(true);
                view.findViewById(R.id.WepRadioButton).setEnabled(true);
                view.findViewById(R.id.TkipaesRadioButton).setEnabled(false);
                view.findViewById(R.id.AesRadioButton).setEnabled(false);
            }
            else if(authenticationID == R.id.SharedKeyRadioButton){
                view.findViewById(R.id.NoneRadioButton).setEnabled(false);
                view.findViewById(R.id.WepRadioButton).setEnabled(true);
                view.findViewById(R.id.TkipaesRadioButton).setEnabled(false);
                view.findViewById(R.id.AesRadioButton).setEnabled(false);
            }
            else {
                view.findViewById(R.id.NoneRadioButton).setEnabled(false);
                view.findViewById(R.id.WepRadioButton).setEnabled(false);
                view.findViewById(R.id.TkipaesRadioButton).setEnabled(true);
                view.findViewById(R.id.AesRadioButton).setEnabled(false);
            }
        }
        else{
            view.findViewById(R.id.ChannelLayout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.AuthenticationLayout).setVisibility(View.GONE);
            view.findViewById(R.id.KeyIndexLayout).setVisibility(View.GONE);
            view.findViewById(R.id.EncryptionLayout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.NoneRadioButton).setEnabled(true);
            view.findViewById(R.id.WepRadioButton).setEnabled(false);
            view.findViewById(R.id.TkipaesRadioButton).setEnabled(false);
            view.findViewById(R.id.AesRadioButton).setEnabled(true);
        }

        if(encryptionID != -1 && !view.findViewById(encryptionID).isEnabled()){
            if(view.findViewById(R.id.NoneRadioButton).isEnabled()) {
                ((RadioButton) view.findViewById(R.id.NoneRadioButton)).setChecked(true);
            }
            else if(view.findViewById(R.id.WepRadioButton).isEnabled()) {
                ((RadioButton) view.findViewById(R.id.WepRadioButton)).setChecked(true);
            }
            else if(view.findViewById(R.id.TkipaesRadioButton).isEnabled()){
                ((RadioButton) view.findViewById(R.id.TkipaesRadioButton)).setChecked(true);
            }
            encryptionID = encryptionRadio.getCheckedRadioButtonId();
        }

        // Change the display of keyindex, password in accordance with the encryptionID.
        if (encryptionID == R.id.NoneRadioButton) {
            view.findViewById(R.id.KeyIndexLayout).setVisibility(View.GONE);
            view.findViewById(R.id.PasswordEdit).setEnabled(false);
            view.findViewById(R.id.PasswordLayout).setVisibility(View.GONE);
        }
        else{
            if (encryptionID == R.id.WepRadioButton) {
                view.findViewById(R.id.KeyIndexLayout).setVisibility(View.VISIBLE);
            }
            else{
                view.findViewById(R.id.KeyIndexLayout).setVisibility(View.GONE);
            }
            view.findViewById(R.id.PasswordEdit).setEnabled(true);
            view.findViewById(R.id.PasswordLayout).setVisibility(View.VISIBLE);
        }

        if(isSupportedIpv6()){
            // Hide unwanted layouts
            view.findViewById(R.id.IpaddresssetLayout).setVisibility(View.GONE);
            view.findViewById(R.id.IpaddressLayout).setVisibility(View.GONE);
            view.findViewById(R.id.SubnetmaskLayout).setVisibility(View.GONE);
            view.findViewById(R.id.GatewayLayout).setVisibility(View.GONE);

            RadioGroup ipv4AddressSetRadio = view.findViewById(R.id.Ipv4_IpaddresssetRadio);
            int ipv4AddressSetID = ipv4AddressSetRadio.getCheckedRadioButtonId();

            if(ipv4AddressSetID == R.id.Ipv4_Ipaddressset_AutoRadioButton){
                view.findViewById(R.id.Ipv4_IpaddressEdit).setEnabled(false);
                view.findViewById(R.id.Ipv4_SubnetmaskEdit).setEnabled(false);
                view.findViewById(R.id.Ipv4_GatewayEdit).setEnabled(false);
                view.findViewById(R.id.Ipv4_IpaddressLayout).setVisibility(View.GONE);
                view.findViewById(R.id.Ipv4_SubnetmaskLayout).setVisibility(View.GONE);
                view.findViewById(R.id.Ipv4_GatewayLayout).setVisibility(View.GONE);
            }
            else{
                view.findViewById(R.id.Ipv4_IpaddressEdit).setEnabled(true);
                view.findViewById(R.id.Ipv4_SubnetmaskEdit).setEnabled(true);
                view.findViewById(R.id.Ipv4_GatewayEdit).setEnabled(true);
                view.findViewById(R.id.Ipv4_IpaddressLayout).setVisibility(View.VISIBLE);
                view.findViewById(R.id.Ipv4_SubnetmaskLayout).setVisibility(View.VISIBLE);
                view.findViewById(R.id.Ipv4_GatewayLayout).setVisibility(View.VISIBLE);
            }

            RadioGroup UseIpv6Radio = view.findViewById(R.id.Ipv6_UseIpv6Radio);
            int UseIpv6RID = UseIpv6Radio.getCheckedRadioButtonId();

            if(UseIpv6RID == R.id.Ipv6_UseIpv6_EnableRadioButton){

                view.findViewById(R.id.Ipv6_Manual_SettingLayout).setVisibility(View.VISIBLE);

                RadioGroup Ipv6ManualSetting = view.findViewById(R.id.Ipv6_Manual_SettingRadio);
                int Ipv6ManualSettingRID = Ipv6ManualSetting.getCheckedRadioButtonId();

                if(Ipv6ManualSettingRID == R.id.Ipv6_Manual_Setting_EnableRadioButton) {
                    view.findViewById(R.id.Ipv6_Manual_AddressEdit).setEnabled(true);
                    view.findViewById(R.id.Ipv6_PrefixLengthEdit).setEnabled(true);
                    view.findViewById(R.id.Ipv6_GatewayEdit).setEnabled(true);
                    view.findViewById(R.id.Ipv6_Manual_AddressLayout).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.Ipv6_PrefixLengthLayout).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.Ipv6_GatewayLayout).setVisibility(View.VISIBLE);
                }else{
                    view.findViewById(R.id.Ipv6_Manual_AddressEdit).setEnabled(false);
                    view.findViewById(R.id.Ipv6_PrefixLengthEdit).setEnabled(false);
                    view.findViewById(R.id.Ipv6_GatewayEdit).setEnabled(false);
                    view.findViewById(R.id.Ipv6_Manual_AddressLayout).setVisibility(View.GONE);
                    view.findViewById(R.id.Ipv6_PrefixLengthLayout).setVisibility(View.GONE);
                    view.findViewById(R.id.Ipv6_GatewayLayout).setVisibility(View.GONE);
                }
            }
            else{
                view.findViewById(R.id.Ipv6_Manual_AddressEdit).setEnabled(false);
                view.findViewById(R.id.Ipv6_PrefixLengthEdit).setEnabled(false);
                view.findViewById(R.id.Ipv6_GatewayEdit).setEnabled(false);
                view.findViewById(R.id.Ipv6_Manual_SettingLayout).setVisibility(View.GONE);
                view.findViewById(R.id.Ipv6_Manual_AddressLayout).setVisibility(View.GONE);
                view.findViewById(R.id.Ipv6_PrefixLengthLayout).setVisibility(View.GONE);
                view.findViewById(R.id.Ipv6_GatewayLayout).setVisibility(View.GONE);
            }

        }else{
            // Hide unwanted layouts
            view.findViewById(R.id.Ipv4_IpaddresssetLayout).setVisibility(View.GONE);
            view.findViewById(R.id.Ipv4_IpaddressLayout).setVisibility(View.GONE);
            view.findViewById(R.id.Ipv4_SubnetmaskLayout).setVisibility(View.GONE);
            view.findViewById(R.id.Ipv4_GatewayLayout).setVisibility(View.GONE);
            view.findViewById(R.id.Ipv6_UseIpv6Layout).setVisibility(View.GONE);
            view.findViewById(R.id.Ipv6_Manual_SettingLayout).setVisibility(View.GONE);
            view.findViewById(R.id.Ipv6_Manual_AddressLayout).setVisibility(View.GONE);
            view.findViewById(R.id.Ipv6_PrefixLengthLayout).setVisibility(View.GONE);
            view.findViewById(R.id.Ipv6_GatewayLayout).setVisibility(View.GONE);

            RadioGroup ipAddressSetRadio = view.findViewById(R.id.IpaddresssetRadio);
            int ipAddressSetID = ipAddressSetRadio.getCheckedRadioButtonId();
            // Change the display of ipaddress, subnetmask, gateway in accordance with the ipaddresssetID.
            if(ipAddressSetID == R.id.AutoRadioButton){
                view.findViewById(R.id.IpaddressEdit).setEnabled(false);
                view.findViewById(R.id.SubnetmaskEdit).setEnabled(false);
                view.findViewById(R.id.GatewayEdit).setEnabled(false);
                view.findViewById(R.id.IpaddressLayout).setVisibility(View.GONE);
                view.findViewById(R.id.SubnetmaskLayout).setVisibility(View.GONE);
                view.findViewById(R.id.GatewayLayout).setVisibility(View.GONE);
            }
            else{
                view.findViewById(R.id.IpaddressEdit).setEnabled(true);
                view.findViewById(R.id.SubnetmaskEdit).setEnabled(true);
                view.findViewById(R.id.GatewayEdit).setEnabled(true);
                view.findViewById(R.id.IpaddressLayout).setVisibility(View.VISIBLE);
                view.findViewById(R.id.SubnetmaskLayout).setVisibility(View.VISIBLE);
                view.findViewById(R.id.GatewayLayout).setVisibility(View.VISIBLE);
            }
        }

    }

    private Boolean isSupportedIpv6(){
        if(mApiDataSet.getUrl().contains(VER100)){
            return false;
        }else{
            return true;
        }
    }

}
