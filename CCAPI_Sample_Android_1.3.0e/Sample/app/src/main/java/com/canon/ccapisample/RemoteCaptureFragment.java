package com.canon.ccapisample;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.WIFI_SERVICE;
import static com.canon.ccapisample.Constants.CCAPI.Field.ACTION;
import static com.canon.ccapisample.Constants.CCAPI.Field.ADDED_CONTENTS;
import static com.canon.ccapisample.Constants.CCAPI.Field.AFAREASELECTION;
import static com.canon.ccapisample.Constants.CCAPI.Field.AFAREASELECTIONMODE;
import static com.canon.ccapisample.Constants.CCAPI.Field.ANTIFLICKERSHOOT;
import static com.canon.ccapisample.Constants.CCAPI.Field.FREQUENCY;
import static com.canon.ccapisample.Constants.CCAPI.Field.HFANTIFLICKERSHOOT;
import static com.canon.ccapisample.Constants.CCAPI.Field.CAMERADISPLAY;
import static com.canon.ccapisample.Constants.CCAPI.Field.HFFLICKETV;
import static com.canon.ccapisample.Constants.CCAPI.Field.LIVEVIEW;
import static com.canon.ccapisample.Constants.CCAPI.Field.LIVEVIEWSIZE;
import static com.canon.ccapisample.Constants.CCAPI.Field.MAX;
import static com.canon.ccapisample.Constants.CCAPI.Field.MESSAGE;
import static com.canon.ccapisample.Constants.CCAPI.Field.MIN;
import static com.canon.ccapisample.Constants.CCAPI.Field.MOVIEMODE;
import static com.canon.ccapisample.Constants.CCAPI.Field.IGNORESHOOTINGMODEDIALMODE;
import static com.canon.ccapisample.Constants.CCAPI.Field.NG;
import static com.canon.ccapisample.Constants.CCAPI.Field.RECBUTTON;
import static com.canon.ccapisample.Constants.CCAPI.Field.RESULT;
import static com.canon.ccapisample.Constants.CCAPI.Field.STATUS;
import static com.canon.ccapisample.Constants.CCAPI.Field.STEP;
import static com.canon.ccapisample.Constants.CCAPI.Field.TV;
import static com.canon.ccapisample.Constants.CCAPI.Field.VALUE;
import static com.canon.ccapisample.Constants.CCAPI.Field.ZOOM;
import static com.canon.ccapisample.Constants.CCAPI.Key.SHOOTING_CONTROL_AF;
import static com.canon.ccapisample.Constants.CCAPI.Key.SHOOTING_CONTROL_DRIVEFOCUS;
import static com.canon.ccapisample.Constants.CCAPI.Key.SHOOTING_CONTROL_MOVIEMODE;
import static com.canon.ccapisample.Constants.CCAPI.Key.SHOOTING_CONTROL_IGNORESHOOTINGMODEDIALMODE;
import static com.canon.ccapisample.Constants.CCAPI.Key.SHOOTING_CONTROL_ZOOM;
import static com.canon.ccapisample.Constants.CCAPI.Key.SHOOTING_LIVEVIEW_ANGLEINFORMATION;
import static com.canon.ccapisample.Constants.CCAPI.Key.SHOOTING_LIVEVIEW_CLICKWB;
import static com.canon.ccapisample.Constants.CCAPI.Key.SHOOTING_LIVEVIEW_RTP;
import static com.canon.ccapisample.Constants.CCAPI.Key.SHOOTING_OPTICALFINDER_AFAREASELECTIONMODE;
import static com.canon.ccapisample.Constants.CCAPI.Key.SHOOTING_SETTINGS_ANTIFLICKERSHOOT;
import static com.canon.ccapisample.Constants.CCAPI.Key.SHOOTING_SETTINGS_HFANTIFLICKERSHOOT;
import static com.canon.ccapisample.Constants.CCAPI.Key.SHOOTING_SETTINGS_HFFLICKERTV;
import static com.canon.ccapisample.Constants.CCAPI.Method.DELETE;
import static com.canon.ccapisample.Constants.CCAPI.Method.GET;
import static com.canon.ccapisample.Constants.CCAPI.Method.PUT;
import static com.canon.ccapisample.Constants.CCAPI.UNIT_MAP;
import static com.canon.ccapisample.Constants.CCAPI.Value.ENABLE;
import static com.canon.ccapisample.Constants.CCAPI.Value.MODE_NOT_SUPPORTED;
import static com.canon.ccapisample.Constants.CCAPI.Value.OFF;
import static com.canon.ccapisample.Constants.CCAPI.Value.ON;
import static com.canon.ccapisample.Constants.RemoteCapture.LV_ARRAY;
import static com.canon.ccapisample.Constants.RemoteCapture.LV_DISPLAY_ON;
import static com.canon.ccapisample.Constants.RemoteCapture.LV_DISPLAY_OFF;
import static com.canon.ccapisample.Constants.RemoteCapture.LV_DISPLAY_KEEP;
import static com.canon.ccapisample.Constants.RemoteCapture.LV_SIZE_MEDIUM;
import static com.canon.ccapisample.Constants.RemoteCapture.LV_SIZE_SMALL;
import static com.canon.ccapisample.Constants.RemoteCapture.LV_SIZE_OFF;
import static com.canon.ccapisample.Constants.RemoteCapture.LV_MIDDLE_ON;
import static com.canon.ccapisample.Constants.RemoteCapture.LV_MIDDLE_OFF;
import static com.canon.ccapisample.Constants.RemoteCapture.LV_MIDDLE_KEEP;
import static com.canon.ccapisample.Constants.RemoteCapture.LV_SMALL_ON;
import static com.canon.ccapisample.Constants.RemoteCapture.LV_SMALL_OFF;
import static com.canon.ccapisample.Constants.RemoteCapture.LV_SMALL_KEEP;
import static com.canon.ccapisample.Constants.RemoteCapture.LV_OFF_ON;
import static com.canon.ccapisample.Constants.RemoteCapture.LV_OFF_OFF;
import static com.canon.ccapisample.Constants.RemoteCapture.LV_OFF_KEEP;
import static com.canon.ccapisample.Constants.Settings.Key.KEY;
import static com.canon.ccapisample.Constants.Settings.Key.NAME;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RemoteCaptureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RemoteCaptureFragment extends Fragment implements WebAPIResultListener, EventListener, View.OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener, CompoundButton.OnCheckedChangeListener {
    private static final String TAG = RemoteCaptureFragment.class.getSimpleName();
    private static final String SETTINGS_INFO = "SettingsInfo";
    private static final String IS_USE_IPV6 = "isUseIPv6";
    private static final String LIST_VIEW_KEY_NAME = "name";
    private static final String LIST_VIEW_KEY_VALUE = "value";
    private static final String RTP_SDP_FILE_NAME = "rtpsessiondesc.sdp";

    private WebAPI mWebAPI;
    private LiveViewThread mLiveViewThread;
    private OpticalFinderThread mOpticalFinderThread;
    private boolean mIsStartRec = false;
    private boolean mIsStartRtp = false;
    private final List<ContentsDataSet> mContentsDataSetList = new ArrayList<>();
    private ContentsAdapter mContentsAdapter;
    private ImageProcessor mImageProcessor;
    private JSONArray mSettingsInfo;
    private boolean mIsUseIPv6;
    private final List<ListViewDataSet> mSettingsDataSetList = new ArrayList<>();
    private CustomSimpleAdapter mAdapter;
    private final List<Map<String, String>> mAdapterDataList = new ArrayList<>();
    private final List<Boolean> mEditableList = new ArrayList<>();
    private final List<Boolean> mGrayOutList = new ArrayList<>();
    private ListViewDataSet mZoomDataSet = null;

    // The flag for determining whether or not the setting of the Live View by the POST request during the period of RemotoCapture startup is completed.
    // Ignore event's notification of the Live View until this flag is true.
    private boolean mIsFirstLiveViewStarted = false;

    private LinearLayout mLiveViewLayout;
    private ImageView mLiveViewImage;
    private TextView mLvSizeText;
    private ImageView mOpticalFinderImage;
    private LinearLayout mControlParentLayout;
    private SeekBar mZoomBar;
    private CheckBox mAFCheckBox;
    private LinearLayout mOpticalFinderLayout;
    private TextView mAfAreaSelectionModeText;
    private TextView mAfAreaSelectionIdText;
    private CheckBox mClickWBCheckBox;
    private Button mShutterButton;
    private Button mMovieButton;
    private Spinner mMovieModeSpinner;
    private Spinner mIgnoreShootingModedialModeSpinner;
    private Spinner mFocusSpinner;
    private LinearLayout mFlickerLessLayout;
    private LinearLayout mFlickerLessSettingsLayout;
    private Switch mAntiFlickerShootSwitch;
    private LinearLayout mHFFlickerLessLayout;
    private LinearLayout mHFFlickerLessSettingsLayout;
    private Switch mAntiHFFlickerShootSwitch;
    private CheckBox mHFFlickerApplyCheckBox;
    private TextView mHFFlickerTvText;
    private Spinner mLiveViewSpinner;
    private RadioGroup mLiveViewMethodRadioGroup;
    private RadioGroup mLiveViewKindRadioGroup;
    private LinearLayout mSettingsParentLayout;
    private LinearLayout mLvInfoParentLayout;
    private ExpandableListView mLvInfoListView;
    private ImageView mHistogramImageView;
    private LinearLayout mAddContentsParentLayout;
    private TextView mContentsCountText;
    private LinearLayout mContentsLayout;

    public RemoteCaptureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RemoteCaptureFragment.
     */
    public static RemoteCaptureFragment newInstance(String settings, boolean isUseIPv6) {
        RemoteCaptureFragment fragment = new RemoteCaptureFragment();
        Bundle args = new Bundle();
        args.putString(SETTINGS_INFO, settings);
        args.putBoolean(IS_USE_IPV6, isUseIPv6);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebAPI = WebAPI.getInstance();
        mImageProcessor = new ImageProcessor();
        mIsUseIPv6 = getArguments().getBoolean(IS_USE_IPV6);

        try {
            mSettingsInfo = new JSONArray(getArguments().getString(SETTINGS_INFO));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_remote_capture, container, false);

        // OnClickListener
        mShutterButton = view.findViewById(R.id.ShutterButton);
        mShutterButton.setOnClickListener(this);
        mShutterButton.setTextColor(Color.BLACK);
        mMovieButton = view.findViewById(R.id.MovieButton);
        mMovieButton.setTextColor(Color.RED);
        mMovieButton.setOnClickListener(this);
        view.findViewById(R.id.MovieModeButton).setOnClickListener(this);
        view.findViewById(R.id.IgnoreShootingModedialModeButton).setOnClickListener(this);
        view.findViewById(R.id.ShutterReleaseButton).setOnClickListener(this);
        view.findViewById(R.id.ShutterHalfPressButton).setOnClickListener(this);
        view.findViewById(R.id.ShutterFullPressButton).setOnClickListener(this);
        view.findViewById(R.id.FocusButton).setOnClickListener(this);
        view.findViewById(R.id.LiveViewSettingButton).setOnClickListener(this);
        view.findViewById(R.id.DoAFStartButton).setOnClickListener(this);
        view.findViewById(R.id.DoAFStopButton).setOnClickListener(this);
        view.findViewById(R.id.FlickerDetectionStartButton).setOnClickListener(this);
        view.findViewById(R.id.HFFlickerDetectionStartButton).setOnClickListener(this);
        view.findViewById(R.id.HFFlickerDetectionCancelButton).setOnClickListener(this);
        view.findViewById(R.id.HFFlickerTvDecrease1Button).setOnClickListener(this);
        view.findViewById(R.id.HFFlickerTvDecrease2Button).setOnClickListener(this);
        view.findViewById(R.id.HFFlickerTvIncrease1Button).setOnClickListener(this);
        view.findViewById(R.id.HFFlickerTvIncrease2Button).setOnClickListener(this);
        view.findViewById(R.id.RtpStartButton).setOnClickListener(this);
        view.findViewById(R.id.RtpStopButton).setOnClickListener(this);
        view.findViewById(R.id.AngleinformationStartButton).setOnClickListener(this);
        view.findViewById(R.id.AngleinformationStopButton).setOnClickListener(this);
        view.findViewById(R.id.ShowControlButton).setOnClickListener(this);
        view.findViewById(R.id.ShowSettingsButton).setOnClickListener(this);
        view.findViewById(R.id.ShowLvInfoButton).setOnClickListener(this);
        view.findViewById(R.id.ShowAddContentsButton).setOnClickListener(this);

        // OnCheckedChangeListener
        mLiveViewMethodRadioGroup = view.findViewById(R.id.LiveViewMethodRadio);
        mLiveViewMethodRadioGroup.setOnCheckedChangeListener(this);
        mLiveViewKindRadioGroup = view.findViewById(R.id.LiveViewKindRadio);
        mLiveViewKindRadioGroup.setOnCheckedChangeListener(this);

        // OnSeekBarChangeListener
        mZoomBar = view.findViewById(R.id.ZoomBar);
        mZoomBar.setOnSeekBarChangeListener(this);
        mZoomBar.setEnabled(false);

        // Live View
        mLiveViewLayout = view.findViewById(R.id.LiveViewLayout);
        mLiveViewImage = view.findViewById(R.id.LiveViewImage);
        mLiveViewImage.setOnTouchListener(this);
        mLvSizeText = view.findViewById(R.id.LvSizeText);
        mClickWBCheckBox = view.findViewById(R.id.ClickWBCheckBox);

        //Optical Finder
        mOpticalFinderLayout = view.findViewById(R.id.OpticalFinderLayout);
        Switch opticalFinderSwitch = view.findViewById(R.id.OpticalFinderSwitch);
        opticalFinderSwitch.setOnCheckedChangeListener(this);
        mOpticalFinderImage = view.findViewById(R.id.OpticalFinderImage);
        mOpticalFinderImage.setOnTouchListener(this);
        mAfAreaSelectionModeText = view.findViewById(R.id.AfAreaSelectionModeText);
        mAfAreaSelectionIdText = view.findViewById(R.id.AfAreaSelectionIdText);

        // Shooting Control
        mControlParentLayout = view.findViewById(R.id.ControlParentLayout);
        mAFCheckBox = view.findViewById(R.id.AFCheckBox);
        mMovieModeSpinner = view.findViewById(R.id.MovieModeSpinner);
        mMovieModeSpinner.setEnabled(false);
        mIgnoreShootingModedialModeSpinner = view.findViewById(R.id.IgnoreShootingModedialModeSpinner);
        mIgnoreShootingModedialModeSpinner.setEnabled(false);
        mFocusSpinner = view.findViewById(R.id.FocusSpinner);
        mFlickerLessLayout = view.findViewById(R.id.FlickerLessLayout);
        mFlickerLessSettingsLayout = view.findViewById(R.id.FlickerLessSettingsLayout);
        mAntiFlickerShootSwitch = view.findViewById(R.id.AntiFlickerShootSwitch);
        mAntiFlickerShootSwitch.setOnCheckedChangeListener(this);
        mHFFlickerLessLayout = view.findViewById(R.id.HFFlickerLessLayout);
        mHFFlickerLessSettingsLayout = view.findViewById(R.id.HFFlickerLessSettingsLayout);
        mAntiHFFlickerShootSwitch = view.findViewById(R.id.AntiHFFlickerShootSwitch);
        mAntiHFFlickerShootSwitch.setOnCheckedChangeListener(this);
        mHFFlickerApplyCheckBox = view.findViewById(R.id.HFFlickerDetectionChecked);
        mHFFlickerTvText = view.findViewById(R.id.HFFlickerTvText);
        mLiveViewSpinner = view.findViewById(R.id.LiveViewSpinner);
        mLiveViewSpinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, LV_ARRAY));

        //If the device does not support the API, hide the UI.
        APIDataSet api = mWebAPI.getAPIData(SHOOTING_CONTROL_ZOOM);
        if(api == null){
            view.findViewById(R.id.ZoomLayout).setVisibility(View.GONE);
        }

        api = mWebAPI.getAPIData(SHOOTING_LIVEVIEW_CLICKWB);
        if(api == null){
            view.findViewById(R.id.ClickWBLayout).setVisibility(View.GONE);
        }

        api = mWebAPI.getAPIData(SHOOTING_OPTICALFINDER_AFAREASELECTIONMODE);
        if(api == null){
            view.findViewById(R.id.OpticalFinderSwitchLayout).setVisibility(View.GONE);
        }

        api = mWebAPI.getAPIData(SHOOTING_CONTROL_MOVIEMODE);
        if(api == null){
            view.findViewById(R.id.MovieModeLayout).setVisibility(View.GONE);
        }

        api = mWebAPI.getAPIData(SHOOTING_CONTROL_IGNORESHOOTINGMODEDIALMODE);
        if(api == null){
            view.findViewById(R.id.IgnoreShootingModedialModeLayout).setVisibility(View.GONE);
        }

        api = mWebAPI.getAPIData(SHOOTING_CONTROL_DRIVEFOCUS);
        if(api == null){
            view.findViewById(R.id.DriveFocusLayout).setVisibility(View.GONE);
        }

        api = mWebAPI.getAPIData(SHOOTING_CONTROL_AF);
        if(api == null){
            view.findViewById(R.id.DoAFLayout).setVisibility(View.GONE);
        }

        api = mWebAPI.getAPIData(SHOOTING_SETTINGS_ANTIFLICKERSHOOT);
        if(api == null){
            mFlickerLessLayout.setVisibility(View.GONE);
        }

        api = mWebAPI.getAPIData(SHOOTING_SETTINGS_HFANTIFLICKERSHOOT);
        if(api == null){
            mHFFlickerLessLayout.setVisibility(View.GONE);
        }

        api = mWebAPI.getAPIData(SHOOTING_LIVEVIEW_RTP);
        if(api == null){
            view.findViewById(R.id.RtpLayout).setVisibility(View.GONE);
        }

        api = mWebAPI.getAPIData(SHOOTING_LIVEVIEW_ANGLEINFORMATION);
        if(api == null){
            view.findViewById(R.id.AngleinformationLayout).setVisibility(View.GONE);
        }

        // Shooting Settings
        ListView paramListView = view.findViewById(R.id.ParameterListView);
        mAdapter = new CustomSimpleAdapter(
                getActivity(),
                mAdapterDataList,
                R.layout.list_view_small_item_layout,
                new String[]{LIST_VIEW_KEY_NAME, LIST_VIEW_KEY_VALUE},
                new int[]{android.R.id.text1, android.R.id.text2},
                mEditableList,
                mGrayOutList);
        paramListView.setAdapter(mAdapter);
        paramListView.setOnItemClickListener(this);
        mSettingsParentLayout = view.findViewById(R.id.SettingsParentLayout);

        // LiveView Info
        mLvInfoListView = view.findViewById(R.id.LvInfoListView);
        mLvInfoParentLayout = view.findViewById(R.id.LvInfoParentLayout);
        mHistogramImageView = view.findViewById(R.id.HistogramImageView);

        // Add Contents
        mAddContentsParentLayout = view.findViewById(R.id.AddContentsParentLayout);
        mContentsCountText = view.findViewById(R.id.ContentsCountText);
        mContentsLayout = view.findViewById(R.id.AddContentsLayout);
        createThumbnailList(getActivity());

        startRemoteCapture();

        return view;
    }

    private void startRemoteCapture(){

        setLiveView((String) mLiveViewSpinner.getSelectedItem());

        startLiveViewThread(getLiveViewMethod(), getLiveViewKind());

        mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.GET_SHOOTING_CONTROL_ZOOM, null, this));
        mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.GET_SHOOTING_CONTROL_MOVIEMODE, null, this));
        APIDataSet api = mWebAPI.getAPIData(SHOOTING_CONTROL_IGNORESHOOTINGMODEDIALMODE);
        if(api != null) {
            mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.GET_SHOOTING_CONTROL_IGNORESHOOTINGMODEDIALMODE, null, this));
        }
        api = mWebAPI.getAPIData(SHOOTING_SETTINGS_ANTIFLICKERSHOOT);
        if(api != null) {
            mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.GET_SHOOTING_SETTINGS_ANTIFLICKERSHOOT, null, this));
        }
        api = mWebAPI.getAPIData(SHOOTING_SETTINGS_HFANTIFLICKERSHOOT);
        if(api != null) {
            mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.GET_SHOOTING_SETTINGS_HFANTIFLICKERSHOOT, null, this));
        }
        api = mWebAPI.getAPIData(SHOOTING_SETTINGS_HFFLICKERTV);
        if(api != null) {
            mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.GET_SHOOTING_SETTINGS_HFFLICKERTV, null, this));
        }

        getListViewSettings();
    }

    private void stopRemoteCapture(){

        stopLiveViewThread();

        setLiveView(LV_OFF_ON);

        if (mIsStartRec) {
            Bundle args = new Bundle();
            args.putString(Constants.RequestCode.POST_SHOOTING_CONTROL_RECBUTTON.name(), "stop");
            mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_CONTROL_RECBUTTON, args, this));
        }

        if(mIsStartRtp){
            stopRtp();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        stopRemoteCapture();
    }

    @Override
    public void onClick(View v){
        if (v != null) {
            if(v.getId() == R.id.MovieButton){
                mMovieButton.setEnabled(false);
                Bundle args = new Bundle();
                if (!mIsStartRec) {
                    args.putString(Constants.RequestCode.POST_SHOOTING_CONTROL_RECBUTTON.name(), "start");
                }
                else {
                    args.putString(Constants.RequestCode.POST_SHOOTING_CONTROL_RECBUTTON.name(), "stop");
                }
                mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_CONTROL_RECBUTTON, args, this));
            }else if(v.getId() == R.id.MovieModeButton){
                if(mMovieModeSpinner.isEnabled()) {
                    Bundle args = new Bundle();
                    String value = (String) mMovieModeSpinner.getSelectedItem();
                    args.putString(Constants.RequestCode.POST_SHOOTING_CONTROL_MOVIEMODE.name(), value);
                    mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_CONTROL_MOVIEMODE, args, this));
                }
            }else if(v.getId() == R.id.IgnoreShootingModedialModeButton){
                if(mIgnoreShootingModedialModeSpinner.isEnabled()) {
                    Bundle args = new Bundle();
                    String value = (String) mIgnoreShootingModedialModeSpinner.getSelectedItem();
                    args.putString(Constants.RequestCode.POST_SHOOTING_CONTROL_IGNORESHOOTINGMODEDIALMODE.name(), value);
                    mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_CONTROL_IGNORESHOOTINGMODEDIALMODE, args, this));
                }
            }else if(v.getId() == R.id.ShutterButton){
                Bundle args = new Bundle();
                String af = String.valueOf(mAFCheckBox.isChecked());
                args.putString(Constants.RequestCode.POST_SHOOTING_CONTROL_SHUTTERBUTTON.name(), af);

                mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_CONTROL_SHUTTERBUTTON, args, this));
            }else if(v.getId() == R.id.ShutterReleaseButton ||
                v.getId() == R.id.ShutterHalfPressButton ||
                v.getId() == R.id.ShutterFullPressButton) {
                Bundle args = new Bundle();
                String action = "";
                String af = String.valueOf(mAFCheckBox.isChecked());

                if(v.getId() == R.id.ShutterReleaseButton){
                    action = "release";
                }
                else if(v.getId() == R.id.ShutterHalfPressButton){
                    action = "half_press";
                }
                else if(v.getId() == R.id.ShutterFullPressButton){
                    action = "full_press";
                }

                args.putStringArray(Constants.RequestCode.POST_SHOOTING_CONTROL_SHUTTERBUTTON_MANUAL.name(), new String[]{action, af});
                mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_CONTROL_SHUTTERBUTTON_MANUAL, args, this));
            }else if(v.getId() == R.id.FocusButton){
                Bundle args = new Bundle();
                String value = (String) mFocusSpinner.getSelectedItem();
                args.putString(Constants.RequestCode.POST_SHOOTING_CONTROL_DRIVEFOCUS.name(), value);
                mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_CONTROL_DRIVEFOCUS, args, this));
            }else if(v.getId() == R.id.LiveViewSettingButton){
                setLiveView((String) mLiveViewSpinner.getSelectedItem());
            }else if(v.getId() == R.id.DoAFStartButton){
                Bundle args = new Bundle();
                args.putString(Constants.RequestCode.POST_SHOOTING_CONTROL_AF.name(), "start");
                mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_CONTROL_AF, args, this));
            }else if(v.getId() == R.id.DoAFStopButton){
                Bundle args = new Bundle();
                args.putString(Constants.RequestCode.POST_SHOOTING_CONTROL_AF.name(), "stop");
                mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_CONTROL_AF, args, this));
            }else if(v.getId() == R.id.FlickerDetectionStartButton){
                Bundle args = new Bundle();
                args.putString(Constants.RequestCode.POST_SHOOTING_CONTROL_FLICKERDETECTION.name(), "start");
                mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_CONTROL_FLICKERDETECTION, args, this));
            }else if(v.getId() == R.id.HFFlickerDetectionStartButton){
                startHFFlickerDetection();
            }else if(v.getId() == R.id.HFFlickerDetectionCancelButton){
                cancelHFFlickerDetection();
            }else if(v.getId() == R.id.HFFlickerTvDecrease1Button){
                Bundle args = new Bundle();
                args.putString(Constants.RequestCode.POST_SHOOTING_CONTROL_HFFLICKERTV.name(), "decrement_small");
                mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_CONTROL_HFFLICKERTV, args, this));
            }else if(v.getId() == R.id.HFFlickerTvDecrease2Button){
                Bundle args = new Bundle();
                args.putString(Constants.RequestCode.POST_SHOOTING_CONTROL_HFFLICKERTV.name(), "decrement_large");
                mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_CONTROL_HFFLICKERTV, args, this));
            }else if(v.getId() == R.id.HFFlickerTvIncrease1Button){
                Bundle args = new Bundle();
                args.putString(Constants.RequestCode.POST_SHOOTING_CONTROL_HFFLICKERTV.name(), "increment_small");
                mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_CONTROL_HFFLICKERTV, args, this));
            }else if(v.getId() == R.id.HFFlickerTvIncrease2Button){
                Bundle args = new Bundle();
                args.putString(Constants.RequestCode.POST_SHOOTING_CONTROL_HFFLICKERTV.name(), "increment_large");
                mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_CONTROL_HFFLICKERTV, args, this));
            }else if(v.getId() == R.id.RtpStartButton){
                setLiveViewRadioEnabled(false);
                if(mLiveViewThread != null) {
                    mLiveViewThread.stopThread(new LiveViewThread.Callback() {
                        @Override
                        public void onComplete() {
                            Log.d(TAG, "LiveView stop onComplete");
                            mLiveViewThread = null;
                            startRtp();
                        }
                    });
                }
                else{
                    startRtp();
                }
            }else if(v.getId() == R.id.RtpStopButton){
                stopRtp();
            }else if(v.getId() == R.id.AngleinformationStartButton){
                Bundle args = new Bundle();
                args.putString(Constants.RequestCode.POST_SHOOTING_LIVEVIEW_ANGLEINFORMATION.name(), "start");
                mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_LIVEVIEW_ANGLEINFORMATION, args, this));
            }else if(v.getId() == R.id.AngleinformationStopButton){
                Bundle args = new Bundle();
                args.putString(Constants.RequestCode.POST_SHOOTING_LIVEVIEW_ANGLEINFORMATION.name(), "stop");
                mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_LIVEVIEW_ANGLEINFORMATION, args, this));
            }else if(v.getId() == R.id.ShowControlButton){

                // Change the display items of the shooting control.
                // Clear lists when change the AddContents display to other display.
                if(mAddContentsParentLayout.getVisibility() == View.VISIBLE){
                    mContentsDataSetList.clear();
                    mContentsCountText.setText("0");
                    mContentsAdapter.notifyDataSetChanged();
                }

                mSettingsParentLayout.setVisibility(View.GONE);
                mLvInfoParentLayout.setVisibility(View.GONE);
                mAddContentsParentLayout.setVisibility(View.GONE);

                mControlParentLayout.setVisibility(View.VISIBLE);
            }else if(v.getId() == R.id.ShowSettingsButton){

                // Change the display lists of the shooting control.
                // Clear lists when change the AddContents display to other display.
                if(mAddContentsParentLayout.getVisibility() == View.VISIBLE){
                    mContentsDataSetList.clear();
                    mContentsCountText.setText("0");
                    mContentsAdapter.notifyDataSetChanged();
                }

                mControlParentLayout.setVisibility(View.GONE);
                mLvInfoParentLayout.setVisibility(View.GONE);
                mAddContentsParentLayout.setVisibility(View.GONE);

                mSettingsParentLayout.setVisibility(View.VISIBLE);
            }else if(v.getId() == R.id.ShowLvInfoButton){

                // Change the display lists of Live View information.
                // Clear lists when change the AddContents display to other display.
                if(mAddContentsParentLayout.getVisibility() == View.VISIBLE){
                    mContentsDataSetList.clear();
                    mContentsCountText.setText("0");
                    mContentsAdapter.notifyDataSetChanged();
                }

                mControlParentLayout.setVisibility(View.GONE);
                mSettingsParentLayout.setVisibility(View.GONE);
                mAddContentsParentLayout.setVisibility(View.GONE);

                mLvInfoParentLayout.setVisibility(View.VISIBLE);
            }else if(v.getId() == R.id.ShowAddContentsButton){

                // Change the display lists of the AddContents.
                mControlParentLayout.setVisibility(View.GONE);
                mSettingsParentLayout.setVisibility(View.GONE);
                mLvInfoParentLayout.setVisibility(View.GONE);

                mAddContentsParentLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        Log.d(TAG, "onCheckedChanged");
        if(group.getId() == R.id.LiveViewMethodRadio || group.getId() == R.id.LiveViewKindRadio){

            // Change the display of the Live View.
            setLiveViewRadioEnabled(false);

            changeLiveViewMethod(getLiveViewMethod(), getLiveViewKind());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(TAG, "onCheckedChanged");
        if(buttonView.getId() == R.id.OpticalFinderSwitch){
            if(isChecked) {
                setLiveView(LV_OFF_OFF);
                startOpticalFinderThread();
                mLiveViewLayout.setVisibility(View.GONE);
                mOpticalFinderLayout.setVisibility(View.VISIBLE);
            } else {
                mOpticalFinderLayout.setVisibility(View.GONE);
                mLiveViewLayout.setVisibility(View.VISIBLE);
                stopOpticalFinderThread();
                setLiveView(LV_SMALL_ON);
            }
        }else if(buttonView.getId() == R.id.AntiFlickerShootSwitch){
            if(mAntiFlickerShootSwitch.isEnabled()) {
                mAntiFlickerShootSwitch.setEnabled(false);
                if (isChecked) {
                    Bundle args = new Bundle();
                    args.putString(Constants.RequestCode.PUT_SHOOTING_SETTINGS_ANTIFLICKERSHOOT.name(), "enable");
                    mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.PUT_SHOOTING_SETTINGS_ANTIFLICKERSHOOT, args, this));
                } else {
                    Bundle args = new Bundle();
                    args.putString(Constants.RequestCode.PUT_SHOOTING_SETTINGS_ANTIFLICKERSHOOT.name(), "disable");
                    mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.PUT_SHOOTING_SETTINGS_ANTIFLICKERSHOOT, args, this));
                }
            }
        }else if(buttonView.getId() == R.id.AntiHFFlickerShootSwitch) {
            if (mAntiHFFlickerShootSwitch.isEnabled()) {
                mAntiHFFlickerShootSwitch.setEnabled(false);
                if (isChecked) {
                    Bundle args = new Bundle();
                    args.putString(Constants.RequestCode.PUT_SHOOTING_SETTINGS_HFANTIFLICKERSHOOT.name(), "enable");
                    mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.PUT_SHOOTING_SETTINGS_HFANTIFLICKERSHOOT, args, this));
                } else {
                    Bundle args = new Bundle();
                    args.putString(Constants.RequestCode.PUT_SHOOTING_SETTINGS_HFANTIFLICKERSHOOT.name(), "disable");
                    mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.PUT_SHOOTING_SETTINGS_HFANTIFLICKERSHOOT, args, this));
                }
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        Log.d(TAG, String.format("progress[%d]", seekBar.getProgress()));
        if(mZoomDataSet != null){
            String key = mZoomDataSet.getName();
            Map<String, Map<String, Integer>> rangeMap = mZoomDataSet.getRangeAbility();
            int max = rangeMap.get(key).get(MAX);
            int min = rangeMap.get(key).get(MIN);
            int step = rangeMap.get(key).get(STEP);
            int current = seekBar.getProgress() * step;

            if(current >= min && current <= max) {
                Bundle args = new Bundle();
                args.putInt(Constants.RequestCode.POST_SHOOTING_CONTROL_ZOOM.name(), current);
                mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_CONTROL_ZOOM, args, this));
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // The display of dialog when the setting list of shooting is selected.
        Log.d(TAG, String.format("onItemClick[%d]", position));

        ListViewDataSet dataSet = mSettingsDataSetList.get(position);
        String name = dataSet.getName();
        JSONObject settings = null;

        try {

            // Get information of the item which is selected.
            for (int i = 0; i < mSettingsInfo.length(); i++) {
                if (name.equals(mSettingsInfo.getJSONObject(i).getString(NAME))) {
                    settings = mSettingsInfo.getJSONObject(i);
                    break;
                }
            }

            if (settings != null) {
                APIDataSet api = mWebAPI.getAPIData(settings.getString(KEY));
                if (api != null) {
                    if (api.isPutable() || api.isDeletable()) {
                        ListViewDialogFragment dialog = ListViewDialogFragment.newInstance(this, settings, dataSet);
                        dialog.show(getActivity().getSupportFragmentManager(), name);
                    }
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                // Execute afframeposition or clickwb API.
                if(mLiveViewThread != null) {
                    int positionX = mLiveViewThread.getImagePositionX();
                    int positionY = mLiveViewThread.getImagePositionY();
                    int positionWidth = mLiveViewThread.getImagePositionWidth();
                    int positionHeight = mLiveViewThread.getImagePositionHeight();

                    if(positionWidth != 0 && positionHeight != 0) {
                        int width = mLiveViewImage.getWidth();
                        int height = mLiveViewImage.getHeight();
                        float widthScale = positionWidth / (float) width;
                        float heightScale = positionHeight / (float) height;
                        int x = (int) (event.getX() * widthScale) + positionX;
                        int y = (int) (event.getY() * heightScale) + positionY;

                        Log.d(TAG, String.format("onTouch : TouchPosition[ x=%f y=%f ]", event.getX(), event.getY()));
                        Log.d(TAG, String.format("onTouch : ImageViewSize[ %d x %d ]", width, height));
                        Log.d(TAG, String.format("onTouch : Scale[ %f / %f ]", widthScale, heightScale));
                        Log.d(TAG, String.format("onTouch : x=%d y=%d", x, y));

                        Bundle args = new Bundle();
                        if(mClickWBCheckBox.isChecked()){
                            args.putIntArray(Constants.RequestCode.POST_SHOOTING_LIVEVIEW_CLICKWB.name(), new int[]{x, y});
                            mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_LIVEVIEW_CLICKWB, args, this));
                        }else {
                            args.putIntArray(Constants.RequestCode.PUT_SHOOTING_LIVEVIEW_AFFRAMEPOSITION.name(), new int[]{x, y});
                            mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.PUT_SHOOTING_LIVEVIEW_AFFRAMEPOSITION, args, this));
                        }
                    }
                    else{
                        Log.d(TAG, "onTouch : LiveViewInfo cannot get.");
                    }
                }else{
                    Log.d(TAG, "onTouch : LiveViewTread is null.");
                }
                break;
            case MotionEvent.ACTION_UP:

                if(mOpticalFinderThread != null){
                    int positionX = mOpticalFinderThread.getImagePositionX();
                    int positionY = mOpticalFinderThread.getImagePositionY();
                    int positionWidth = mOpticalFinderThread.getImagePositionWidth();
                    int positionHeight = mOpticalFinderThread.getImagePositionHeight();

                    int width = mOpticalFinderImage.getWidth();
                    int height = mOpticalFinderImage.getHeight();
                    float widthScale = positionWidth / (float) width;
                    float heightScale = positionHeight / (float) height;
                    int x = (int) (event.getX() * widthScale) + positionX;
                    int y = (int) (event.getY() * heightScale) + positionY;

                    Log.d(TAG, String.format("onTouch : TouchPosition[ x=%f y=%f ]", event.getX(), event.getY()));
                    Log.d(TAG, String.format("onTouch : ImageViewSize[ %d x %d ]", width, height));
                    Log.d(TAG, String.format("onTouch : Scale[ %f / %f ]", widthScale, heightScale));
                    Log.d(TAG, String.format("onTouch : x=%d y=%d", x, y));

                    Bundle args = new Bundle();
                    String areaID = mOpticalFinderThread.getAfFrameID(x, y, mAfAreaSelectionModeText.getText().toString());
                    if(areaID != null) {
                        args.putString(Constants.RequestCode.PUT_SHOOTING_OPTICALFINDER_AFAREASELECTION.name(), areaID);
                        mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.PUT_SHOOTING_OPTICALFINDER_AFAREASELECTION, args, this));
                    }

                }

                break;
            default:
                break;
        }
        return true;
    }

    /**
     * Callback from execute WebAPI
     * @param result HTTP Request result
     */
    @Override
    public void onWebAPIResult(WebAPIResultDataSet result) {
        Log.d(TAG, String.format("%s onWebAPIResult", result.getRequestCode()));
        Context context = getActivity();

        // Do nothing, if life cycle of the fragment is finished.
        if (context != null) {
            if(result.getRequestCode() == Constants.RequestCode.POST_SHOOTING_CONTROL_RECBUTTON){
                // Enable the rec button when the callback returned from starting/ending of recording.
                mMovieButton.setEnabled(true);
            }
            if (result.isError()) {

                // When RTP has not started.
                if(!mIsStartRtp) {
                    if (result.getRequestCode() == Constants.RequestCode.GET_SHOOTING_LIVEVIEW_RTPSESSIONDESC) {

                        // Restart the Live View when starting of RTP failed.
                        setLiveViewRadioEnabled(true);
                        if (mLiveViewThread == null) {
                            startLiveViewThread(getLiveViewMethod(), getLiveViewKind());
                            setLiveViewRadioEnabled(true);
                        }
                    }
                    else if (result.getRequestCode() == Constants.RequestCode.POST_SHOOTING_LIVEVIEW_RTP) {
                        String action = result.getRequestBodyFromKey(ACTION);
                        if (action != null && action.equals("start")) {

                            // Restart the Live View when starting of RTP failed.
                            setLiveViewRadioEnabled(true);
                            if (mLiveViewThread == null) {
                                startLiveViewThread(getLiveViewMethod(), getLiveViewKind());
                                setLiveViewRadioEnabled(true);
                            }
                        }
                    }
                }

                switch (result.getRequestCode()) {

                    case PUT_SHOOTING_SETTINGS_ANTIFLICKERSHOOT: {
                        mAntiFlickerShootSwitch.setChecked(!mAntiFlickerShootSwitch.isChecked());
                        mAntiFlickerShootSwitch.setEnabled(true);
                        break;
                    }
                    case PUT_SHOOTING_SETTINGS_HFANTIFLICKERSHOOT: {
                        mAntiHFFlickerShootSwitch.setChecked(!mAntiHFFlickerShootSwitch.isChecked());
                        mAntiHFFlickerShootSwitch.setEnabled(true);
                        break;
                    }
                    case POST_SHOOTING_CONTROL_HFFLICKERDETECTION: {
                        mHFFlickerApplyCheckBox.setEnabled(true);
                        break;
                    }
                    default:
                        break;
                }

                if(result.getMethod().equals(GET)
                        && result.getResponseCode() == 503
                        && (result.getResponseBodyFromKey(MESSAGE) != null && result.getResponseBodyFromKey(MESSAGE).equals(MODE_NOT_SUPPORTED))){

                    // Error message is not displayed, because setting values can not be got.
                    Log.d(TAG, String.format("%s %s", result.getRequestCode(), result.getResponseBodyFromKey(MESSAGE)));
                }
                else {
                    Toast.makeText(context, result.getErrorMsg(), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                switch (result.getRequestCode()) {
                    case GET_SHOOTING_CONTROL_MOVIEMODE: {
                        String status = "";

                        try {
                            JSONObject jsonObject = new JSONObject(result.getResponseBody());
                            status = jsonObject.getString(STATUS);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                        updateMovieMode(status);
                        break;
                    }
                    case GET_SHOOTING_CONTROL_IGNORESHOOTINGMODEDIALMODE: {
                        String status = "";

                        try {
                            JSONObject jsonObject = new JSONObject(result.getResponseBody());
                            status = jsonObject.getString(STATUS);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                        updateIgnoreShootingModedialMode(status);
                        break;
                    }
                    case ACT_WEB_API: {
                        // Get names of setting items.
                        String url = result.getUrl();
                        String name;
                        if(url.contains("/shooting/settings/")){
                            String[] split = url.split("/shooting/settings/");
                            name = split[split.length - 1];
                        }
                        else{
                            name = result.getRequestName();
                        }

                        // The callback processing after completion of acquisition of setting values when a Fragment is displayed.
                        if (result.getMethod().equals(GET)) {
                            updateListView(name, result.getResponseBody());
                        }

                        // The callback processing after completion of modification of dialog setting values.
                        else if (result.getMethod().equals(PUT) || result.getMethod().equals(DELETE)){

                            // Reflect the response value in the ListView.
                            ListViewDataSet currentData = null;

                            // Get data of the current list.
                            for(int i = 0; i < mSettingsDataSetList.size(); i++){
                                if (mSettingsDataSetList.get(i).getName().equals(name)){
                                    currentData = mSettingsDataSetList.get(i);
                                    break;
                                }
                            }

                            if (currentData != null){
                                try {

                                    // Generate a ListViewDataSet using the response.
                                    JSONObject jsonObject = new JSONObject(result.getResponseBody());
                                    ListViewDataSet responseData = new ListViewDataSet(name, jsonObject);

                                    // Reflect values of generated data in current data.
                                    currentData.setValue(responseData.getValue());

                                    updateListViewData(currentData);

                                    mAdapter.notifyDataSetChanged();
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        break;
                    }
                    case GET_SHOOTING_CONTROL_ZOOM: {
                        try {
                            mZoomDataSet = new ListViewDataSet(result.getRequestName(), new JSONObject(result.getResponseBody()));
                            updateZoom();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case GET_SHOOTING_SETTINGS_ANTIFLICKERSHOOT: {
                        String value = "";

                        try {
                            JSONObject jsonObject = new JSONObject(result.getResponseBody());
                            value = jsonObject.getString(VALUE);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mAntiFlickerShootSwitch.setEnabled(false);
                        if(value.equals(ENABLE)){
                            mAntiFlickerShootSwitch.setChecked(true);
                            mFlickerLessSettingsLayout.setVisibility(View.VISIBLE);
                        }else{
                            mAntiFlickerShootSwitch.setChecked(false);
                            mFlickerLessSettingsLayout.setVisibility(View.GONE);
                        }
                        mAntiFlickerShootSwitch.setEnabled(true);

                        break;
                    }
                    case PUT_SHOOTING_SETTINGS_ANTIFLICKERSHOOT: {
                        String value = "";

                        try {
                            JSONObject jsonObject = new JSONObject(result.getResponseBody());
                            value = jsonObject.getString(VALUE);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(value.equals(ENABLE)){
                            mFlickerLessSettingsLayout.setVisibility(View.VISIBLE);
                        }else{
                            mFlickerLessSettingsLayout.setVisibility(View.GONE);
                        }
                        mAntiFlickerShootSwitch.setEnabled(true);

                        break;
                    }
                    case GET_SHOOTING_SETTINGS_HFANTIFLICKERSHOOT: {
                        String value = "";

                        try {
                            JSONObject jsonObject = new JSONObject(result.getResponseBody());
                            value = jsonObject.getString(VALUE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        mAntiHFFlickerShootSwitch.setEnabled(false);
                        if (value.equals(ENABLE)) {
                            mAntiHFFlickerShootSwitch.setChecked(true);
                            mHFFlickerLessSettingsLayout.setVisibility(View.VISIBLE);
                        } else {
                            mAntiHFFlickerShootSwitch.setChecked(false);
                            mHFFlickerLessSettingsLayout.setVisibility(View.GONE);
                        }
                        mAntiHFFlickerShootSwitch.setEnabled(true);

                        break;
                    }
                    case PUT_SHOOTING_SETTINGS_HFANTIFLICKERSHOOT: {
                        String value = "";

                        try {
                            JSONObject jsonObject = new JSONObject(result.getResponseBody());
                            value = jsonObject.getString(VALUE);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(value.equals(ENABLE)){
                            mHFFlickerLessSettingsLayout.setVisibility(View.VISIBLE);
                        }else{
                            mHFFlickerLessSettingsLayout.setVisibility(View.GONE);
                        }
                        mAntiHFFlickerShootSwitch.setEnabled(true);

                        break;
                    }
                    case POST_SHOOTING_CONTROL_FLICKERDETECTION: {

                        String value = "";
                        JSONObject jsonObject = null;

                        try {
                            jsonObject = new JSONObject(result.getResponseBody());
                            value = jsonObject.getString(RESULT);

                            // Display the result dialog.
                            Toast.makeText(context, result.getRequestName() + ": " + value, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                    }
                    case POST_SHOOTING_CONTROL_HFFLICKERDETECTION: {

                        String value = "";
                        JSONObject jsonObject = null;
                        value = result.getRequestBodyFromKey(ACTION);

                        if(value.equals("start")) {
                            try {
                                jsonObject = new JSONObject(result.getResponseBody());
                                value = jsonObject.getString(RESULT);

                                if (value.equals(NG)) {
                                    // Display the NG result.
                                    Toast.makeText(context, result.getRequestName() + ": " + value, Toast.LENGTH_SHORT).show();
                                } else if (mHFFlickerApplyCheckBox.isChecked()) {
                                    // Display the result.
                                    Toast.makeText(context, result.getRequestName() + ": " + "applied", Toast.LENGTH_SHORT).show();
                                } else {
                                    Bundle args = new Bundle();
                                    String freq = jsonObject.getString(FREQUENCY);
                                    args.putString(FREQUENCY, freq);
                                    String tv = jsonObject.getString(TV);
                                    args.putString(TV, tv);
                                    // Display the result dialog.
                                    HFFlickerTvSettingDialogFragment dialog = HFFlickerTvSettingDialogFragment.newInstance(this, args);
                                    dialog.show(getActivity().getSupportFragmentManager(), "HF Flicker Tv");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        mHFFlickerApplyCheckBox.setEnabled(true);

                        break;
                    }
                    case GET_SHOOTING_SETTINGS_HFFLICKERTV: {
                        String value = "";

                        try {
                            JSONObject jsonObject = new JSONObject(result.getResponseBody());
                            value = jsonObject.getString(VALUE);
                            mHFFlickerTvText.setText(value);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                    }
                    case POST_SHOOTING_LIVEVIEW: {
                        mIsFirstLiveViewStarted = true;
                        break;
                    }
                    case GET_SHOOTING_LIVEVIEW_RTPSESSIONDESC: {
                        String selfIp = null;
                        /* IPv6 */
                        if(mIsUseIPv6){
                            try {
                                // It depends on the Android model. If it does not work, rewrite it according to the target.
                                NetworkInterface ifc = NetworkInterface.getByName("wlan0");
                                List <InterfaceAddress> addresses = ifc.getInterfaceAddresses();
                                for (InterfaceAddress address: addresses) {
                                    InetAddress ip = address.getAddress();
                                    if( ip instanceof Inet6Address ){
                                        String tmpIp = ip.getHostAddress();
                                        if(tmpIp.contains("%")){
                                            selfIp = tmpIp.substring(0,tmpIp.indexOf("%"));
                                        }else{
                                            selfIp = tmpIp;
                                        }
                                        if(ip.isLinkLocalAddress()) {
                                            continue;
                                        }
                                        break;
                                    }
                                }
                                System.out.println("IPv6 Address: " + selfIp);
                            }catch (IOException e) {
                                e.printStackTrace();
                            }
                        /* IPv4 */
                        }else{
                            WifiManager manager = (WifiManager)context.getApplicationContext().getSystemService(WIFI_SERVICE);
                            WifiInfo info = manager.getConnectionInfo();
                            int addressNum = info.getIpAddress();
                            selfIp = String.format(Locale.US, "%d.%d.%d.%d", (addressNum)&0xff, (addressNum>>8)&0xff, (addressNum>>16)&0xff, (addressNum>>24)&0xff);
                            System.out.println("IPv4 Address: " + selfIp);
                        }

                        if(isExternalStorageWritable()) {

                            File dir = context.getExternalFilesDir(null);
                            File file = new File(dir, RTP_SDP_FILE_NAME);
                            FileOutputStream fileOutputStream = null;
                            try {
                                fileOutputStream = new FileOutputStream(file);
                                fileOutputStream.write(result.getBytesResponseBody());
                                fileOutputStream.close();
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                            finally {
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            Bundle args = new Bundle();
                            args.putStringArray(Constants.RequestCode.POST_SHOOTING_LIVEVIEW_RTP.toString(), new String[]{"start", selfIp});
                            mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_LIVEVIEW_RTP, args, this));
                        }
                        else{
                            Toast.makeText(context, result.getRequestName() + " : Cannot start RTP. \nThe sdp file cannot be write to the external storage.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                    case POST_SHOOTING_LIVEVIEW_RTP: {
                        String action = result.getRequestBodyFromKey(ACTION);
                        if(action != null && action.equals("start")) {
                            //When using IPv6, it can be played only in the application supporting IPv6.
                            mIsStartRtp = true;
                            if (isExternalStorageReadable()) {

                                File file = new File(context.getExternalFilesDir(null), RTP_SDP_FILE_NAME);

                                PackageManager packageManager = context.getPackageManager();
                                Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName(), file);

                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(uri, "video/*");
                                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

                                if (!resolveInfo.isEmpty()) {
                                    context.startActivity(intent);
                                }
                            }
                            else {
                                Toast.makeText(context, result.getRequestName() + " : Cannot start RTP. \nThe sdp file cannot be read from the external storage.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if(action != null && action.equals("stop")){
                            mIsStartRtp = false;
                            setLiveViewRadioEnabled(true);
                            if(mLiveViewThread == null) {
                                startLiveViewThread(getLiveViewMethod(), getLiveViewKind());
                            }
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        }
        else{
            Log.d(TAG, String.format("%s Activity is Null.", result.getRequestCode()));
        }
    }

    @Override
    public void onNotifyEvent(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.length() != 0) {
                Iterator<String> iterator = jsonObject.keys();
                while(iterator.hasNext()) {
                    String key = iterator.next();
                    String param = jsonObject.getString(key);
                    Log.d(TAG, String.format("onNotifyEvent : %s : %s", key, param));

                    switch (key) {
                        case ZOOM:
                            JSONObject zoomObj = new JSONObject(param);
                            mZoomDataSet = new ListViewDataSet(key, zoomObj);
                            updateZoom();
                            break;
                        case MOVIEMODE:
                            JSONObject movieMode = new JSONObject(param);

                            if(!movieMode.isNull(STATUS)) {
                                String status = movieMode.getString(STATUS);
                                updateMovieMode(status);
                            }
                            break;
                        case IGNORESHOOTINGMODEDIALMODE:
                            JSONObject ignoreShootingModedialMode = new JSONObject(param);

                            if(!ignoreShootingModedialMode.isNull(STATUS)) {
                                String status = ignoreShootingModedialMode.getString(STATUS);
                                updateIgnoreShootingModedialMode(status);
                            }
                            break;
                        case ADDED_CONTENTS:
                            JSONArray urlArray = new JSONArray(param);
                            for (int i = 0; i < urlArray.length(); i++) {
                                ContentsDataSet contentsDataSet = new ContentsDataSet(urlArray.getString(i));
                                if (contentsDataSet.getKind() != ContentsDataSet.Kind.DIR) {
                                    mContentsDataSetList.add(contentsDataSet);
                                }
                            }
                            mContentsCountText.setText(String.valueOf(mContentsDataSetList.size()));
                            mContentsAdapter.notifyDataSetChanged();
                            break;
                        case RECBUTTON:
                            JSONObject rec = new JSONObject(param);

                            /* Ver.1.0.0 */
                            if(!rec.isNull(ACTION)) {
                                if ( !mIsStartRec && rec.getString(ACTION).equals("start") ) {
                                    updateRec(true);
                                }
                                else if ( mIsStartRec && rec.getString(ACTION).equals("stop") ) {
                                    updateRec(false);
                                }
                            /* Ver. 1.1.0 or later */
                            }else if(!rec.isNull(STATUS)){
                                if ( !mIsStartRec && rec.getString(STATUS).equals("start") ) {
                                    updateRec(true);
                                }
                                else if ( mIsStartRec && rec.getString(STATUS).equals("stop") ) {
                                    updateRec(false);
                                }
                            }

                            break;
                        case ANTIFLICKERSHOOT:
                            JSONObject antiflickershoot = new JSONObject(param);

                            if(!antiflickershoot.isNull(VALUE)) {
                                String value = antiflickershoot.getString(VALUE);

                                mAntiFlickerShootSwitch.setEnabled(false);
                                if (value.equals(ENABLE)) {
                                    mAntiFlickerShootSwitch.setChecked(true);
                                    mFlickerLessSettingsLayout.setVisibility(View.VISIBLE);
                                } else {
                                    mAntiFlickerShootSwitch.setChecked(false);
                                    mFlickerLessSettingsLayout.setVisibility(View.GONE);
                                }
                                mAntiFlickerShootSwitch.setEnabled(true);
                            }
                            break;
                        case HFANTIFLICKERSHOOT:
                            JSONObject antihfflickershoot = new JSONObject(param);

                            if(!antihfflickershoot.isNull(VALUE)) {
                                String value = antihfflickershoot.getString(VALUE);

                                mAntiHFFlickerShootSwitch.setEnabled(false);
                                if (value.equals(ENABLE)) {
                                    mAntiHFFlickerShootSwitch.setChecked(true);
                                    mHFFlickerLessSettingsLayout.setVisibility(View.VISIBLE);
                                } else {
                                    mAntiHFFlickerShootSwitch.setChecked(false);
                                    mHFFlickerLessSettingsLayout.setVisibility(View.GONE);
                                }
                                mAntiHFFlickerShootSwitch.setEnabled(true);
                            }
                            break;
                        case HFFLICKETV:
                            JSONObject hfflickertv = new JSONObject(param);

                            if(!hfflickertv.isNull(VALUE)) {
                                String value = hfflickertv.getString(VALUE);
                                mHFFlickerTvText.setText(value);
                            }
                            break;
                        case LIVEVIEW:
                            // Ignore events until completion of the Live View setting when the RemoteCapture is displayed.
                            if(mIsFirstLiveViewStarted) {
                                updateLiveViewSetting(param);
                            }
                            break;
                        case AFAREASELECTIONMODE:
                        case AFAREASELECTION:
                            if(mOpticalFinderThread != null) {
                                updateOpticalFinderSetting(param);
                            }
                        default:
                            if(key.contains("_")){
                                // Convert "_" to "/" in order to match item names of the application.
                                // ex: "soundrecording_level" -> "soundrecording/level"
                                key = key.replace("_", "/");
                            }

                            updateListView(key, param);
                            break;
                    }
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setLiveViewRadioEnabled(boolean enable){
        for(int i = 0; i < mLiveViewMethodRadioGroup.getChildCount(); i++){
            mLiveViewMethodRadioGroup.getChildAt(i).setEnabled(enable);
        }

        int methodID = mLiveViewMethodRadioGroup.getCheckedRadioButtonId();

        if (!enable || (methodID == R.id.FlipdetailRadioButton || methodID == R.id.ScrolldetailRadioButton)) {
            for (int i = 0; i < mLiveViewKindRadioGroup.getChildCount(); i++) {
                mLiveViewKindRadioGroup.getChildAt(i).setEnabled(enable);
            }
        }
    }

    private void startLiveViewThread(Constants.LiveViewMethod method, Constants.LiveViewKind kind){
        mLiveViewThread = new LiveViewThread(getContext(), mLiveViewImage, mLvSizeText, mHistogramImageView, mLvInfoListView, method, kind);
        mLiveViewThread.start();
    }

    private void stopLiveViewThread(){
        if(mLiveViewThread != null) {
            mLiveViewThread.stopThread(new LiveViewThread.Callback() {
                @Override
                public void onComplete() {
                    Log.d(TAG, "LiveView stop onComplete");
                }
            });
        }
    }

    private void startOpticalFinderThread(){

        mOpticalFinderThread = new OpticalFinderThread(mOpticalFinderImage, mAfAreaSelectionModeText, mAfAreaSelectionIdText);
        mOpticalFinderThread.start();
    }

    private void stopOpticalFinderThread(){
        if(mOpticalFinderThread != null) {
            mOpticalFinderThread.stopThread(new OpticalFinderThread.Callback() {
                @Override
                public void onComplete() {
                    mOpticalFinderThread = null;
                    Log.d(TAG, "OpticalFinderThread stop onComplete");
                }
            });
        }
    }

    private void updateOpticalFinderSetting(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            String mode = jsonObject.getString(VALUE);
            if(!(mode.contentEquals(mAfAreaSelectionModeText.getText()))) {
                stopOpticalFinderThread();
                startOpticalFinderThread();
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void changeLiveViewMethod(final Constants.LiveViewMethod method, final Constants.LiveViewKind kind){
        if(mLiveViewThread != null) {
            mLiveViewThread.stopThread(new LiveViewThread.Callback() {
                @Override
                public void onComplete() {
                    Log.d(TAG, "LiveView stop onComplete");
                    mLiveViewThread = new LiveViewThread(getContext(), mLiveViewImage, mLvSizeText, mHistogramImageView, mLvInfoListView, method, kind);
                    mLiveViewThread.start();
                    setLiveViewRadioEnabled(true);
                }
            });
        }
    }

    private void getListViewSettings(){
        for(int i = 0; i < mSettingsInfo.length(); i++) {
            Bundle args = new Bundle();
            try {
                APIDataSet api = mWebAPI.getAPIData(mSettingsInfo.getJSONObject(i).getString(KEY));
                if ( api != null ) {

                    createListView(mSettingsInfo.getJSONObject(i).getString(NAME));

                    String[] params = new String[]{GET, api.getUrl(), null};
                    args.putStringArray(Constants.RequestCode.ACT_WEB_API.name(), params);
                    mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.ACT_WEB_API, args, this));
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, String> createAdapterData(ListViewDataSet dataSet){
        Map<String, String> map = new HashMap<>();
        map.put(LIST_VIEW_KEY_NAME, dataSet.getName());
        StringBuilder stringBuilder = new StringBuilder();

        for(String item : dataSet.getItems()){
            String value = dataSet.getValue().get(item);
            String unit = null;

            if(!value.equals("null")) {
                if (UNIT_MAP.containsKey(item)) {
                    unit = UNIT_MAP.get(item);
                }
            }
            else{
                value = "";
            }

            if(dataSet.getItems().size() > 1) {
                stringBuilder.append(item).append(" : ").append(value);
            }
            else{
                stringBuilder.append(value);
            }

            if(unit != null){
                stringBuilder.append(" ").append(unit);
            }

            stringBuilder.append("\n");
        }

        if(stringBuilder.length() > 0) {
            map.put(LIST_VIEW_KEY_VALUE, stringBuilder.substring(0, stringBuilder.length() - 1));
        }
        else{
            map.put(LIST_VIEW_KEY_VALUE, "");
        }

        return map;
    }

    private void addListViewData(ListViewDataSet dataSet){
        Map<String, String> map = createAdapterData(dataSet);
        boolean editable = false;
        try {
            for(int i = 0; i < mSettingsInfo.length(); i++) {
                if (mSettingsInfo.getJSONObject(i).getString(NAME).equals(dataSet.getName())) {
                    JSONObject settings = mSettingsInfo.getJSONObject(i);
                    APIDataSet api = mWebAPI.getAPIData(settings.getString(KEY));
                    if (api != null) {
                        if (api.isPutable()) {
                            editable = true;
                        }
                    }
                    break;
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        mEditableList.add(editable);

        if(editable) {
            mGrayOutList.add(true);
        }
        else{
            mGrayOutList.add(false);
        }
        mAdapterDataList.add(map);
        mSettingsDataSetList.add(dataSet);
    }

    private void updateListViewData(ListViewDataSet dataSet){
        boolean isExist = false;
        int index = 0;

        for(int i = 0; i < mSettingsDataSetList.size(); i++){
            if (mSettingsDataSetList.get(i).getName().equals(dataSet.getName())){
                isExist = true;
                index = i;
                break;
            }
        }

        if (isExist){
            Map<String, String> map = createAdapterData(dataSet);
            mAdapterDataList.set(index, map);
            mSettingsDataSetList.set(index, dataSet);

            if(mEditableList.get(index) && !dataSet.isSettable()){
                mGrayOutList.set(index, true);
            }
            else{
                mGrayOutList.set(index, false);
            }
        }
    }

    private void createListView(String name){
        Log.d(TAG, "createListView: " + name);
        ListViewDataSet listViewDataSet = new ListViewDataSet();
        listViewDataSet.setName(name);
        addListViewData(listViewDataSet);
        mAdapter.notifyDataSetChanged();
    }

    private void updateListView(String name, String responseBody){
        Log.d(TAG, "updateListView: " + name);

        try {
            JSONObject response = new JSONObject(responseBody);
            ListViewDataSet listViewDataSet = new ListViewDataSet(name, response);
            updateListViewData(listViewDataSet);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        mAdapter.notifyDataSetChanged();
    }

    private void createThumbnailList(Context context){
        LinearLayout parentLayout = new LinearLayout(context);
        parentLayout.setOrientation(LinearLayout.VERTICAL);

        GridView gridView = new GridView(context);
        mContentsAdapter = new ContentsAdapter(context, mContentsDataSetList, mImageProcessor);
        gridView.setAdapter(mContentsAdapter);
        gridView.setOnScrollListener(mContentsAdapter);
        gridView.setNumColumns(3);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        registerForContextMenu(gridView);

        parentLayout.addView(gridView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mContentsLayout.addView(parentLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    private Constants.LiveViewMethod getLiveViewMethod(){
        Constants.LiveViewMethod method = Constants.LiveViewMethod.FLIPDETAIL;
        int methodID = mLiveViewMethodRadioGroup.getCheckedRadioButtonId();

        if(methodID == R.id.FlipRadioButton) {
            method = Constants.LiveViewMethod.FLIP;
        }else if(methodID == R.id.FlipdetailRadioButton) {
            method = Constants.LiveViewMethod.FLIPDETAIL;
        }else if(methodID == R.id.ScrollRadioButton) {
            method = Constants.LiveViewMethod.SCROLL;
        }else if(methodID == R.id.ScrolldetailRadioButton) {
            method = Constants.LiveViewMethod.SCROLLDETAIL;
        }

        return method;
    }

    private Constants.LiveViewKind getLiveViewKind(){
        Constants.LiveViewKind kind = Constants.LiveViewKind.IMAGE;
        int kindID = mLiveViewKindRadioGroup.getCheckedRadioButtonId();

        if (kindID == R.id.ImageRadioButton) {
            kind = Constants.LiveViewKind.IMAGE;
        }
        else if (kindID == R.id.ImageAndInfoRadioButton) {
            kind = Constants.LiveViewKind.IMAGE_AND_INFO;
        }
        else if (kindID == R.id.InfoRadioButton) {
            kind = Constants.LiveViewKind.INFO;
        }
        return kind;
    }

    private void updateMovieMode(String status){
        mMovieModeSpinner.setEnabled(true);

        switch (status) {
            case ON:
                mMovieButton.setTextColor(Color.RED);
                mShutterButton.setTextColor(Color.GRAY);
                mMovieModeSpinner.setSelection(0);
                break;
            case OFF:
                mMovieButton.setTextColor(Color.GRAY);
                mShutterButton.setTextColor(Color.BLACK);
                mMovieModeSpinner.setSelection(1);
                break;
            default:
                break;
        }
    }

    private void updateIgnoreShootingModedialMode(String status){
        mIgnoreShootingModedialModeSpinner.setEnabled(true);

        switch (status) {
            case ON:
                mIgnoreShootingModedialModeSpinner.setSelection(0);
                break;
            case OFF:
                mIgnoreShootingModedialModeSpinner.setSelection(1);
                break;
            default:
                break;
        }
    }

    private void updateZoom(){
        if(mZoomDataSet != null) {
            try {
                String key = mZoomDataSet.getName();
                Map<String, String> valueMap = mZoomDataSet.getValue();
                Map<String, Map<String, Integer>> rangeMap = mZoomDataSet.getRangeAbility();
                int current = Integer.parseInt(valueMap.get(key));
                int max = rangeMap.get(key).get(MAX);
                int min = rangeMap.get(key).get(MIN);
                int step = rangeMap.get(key).get(STEP);

                if (min == 0) {
                    mZoomBar.setProgress(current / step);
                    mZoomBar.setMax(max / step);
                    mZoomBar.setEnabled(true);
                }
                else {
                    mZoomDataSet = null;
                }
            }
            catch (NumberFormatException e) {
                e.printStackTrace();
                mZoomDataSet = null;
            }
        }
    }

    private void updateRec(boolean isStart){
        Log.d(TAG, String.format("updateRec: %b", isStart));
        if (isStart) {
            mIsStartRec = true;
            mMovieButton.setText("■");
        }
        else {
            mIsStartRec = false;
            mMovieButton.setText("●Rec");
        }
    }

    private void updateLiveViewSetting(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            String size;
            String display;
            String size_display;

            if(!jsonObject.isNull(LIVEVIEWSIZE) && !jsonObject.isNull(CAMERADISPLAY)){
                size = jsonObject.getString(LIVEVIEWSIZE);
                display = jsonObject.getString(CAMERADISPLAY);
                size_display = size + "/" + display;

                SpinnerAdapter adapter = mLiveViewSpinner.getAdapter();
                int index = 0;
                for (int i = 0; i < adapter.getCount(); i++) {
                    if (adapter.getItem(i).equals(size_display)) {
                        index = i;
                        break;
                    }
                }
                mLiveViewSpinner.setSelection(index);

                // Display a message and stop the Live View when the Live View turns OFF.
                if(size.equals(LV_SIZE_OFF)){
                    Context context = getContext();
                    if(context != null) {
                        Toast.makeText(context, "LiveView stopped.", Toast.LENGTH_SHORT).show();
                    }

                    if(mLiveViewThread != null) {
                        setLiveViewRadioEnabled(false);
                        mLiveViewThread.stopThread(new LiveViewThread.Callback() {
                            @Override
                            public void onComplete() {
                                Log.d(TAG, "LiveView stop onComplete");
                                mLiveViewThread = null;
                            }
                        });
                    }
                }
                else{
                    // Restart a thread when the Live View turns ON.
                    if(mLiveViewThread == null) {
                        startLiveViewThread(getLiveViewMethod(), getLiveViewKind());
                        setLiveViewRadioEnabled(true);
                    }
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setLiveView(String value){
        String size = "";
        String display = "";

        switch (value){
            case LV_OFF_OFF:
                size = LV_SIZE_OFF;
                display = LV_DISPLAY_OFF;
                break;
            case LV_OFF_ON:
                size = LV_SIZE_OFF;
                display = LV_DISPLAY_ON;
                break;
            case LV_OFF_KEEP:
                size = LV_SIZE_OFF;
                display = LV_DISPLAY_KEEP;
                break;
            case LV_SMALL_OFF:
                size = LV_SIZE_SMALL;
                display = LV_DISPLAY_OFF;
                break;
            case LV_SMALL_ON:
                size = LV_SIZE_SMALL;
                display = LV_DISPLAY_ON;
                break;
            case LV_SMALL_KEEP:
                size = LV_SIZE_SMALL;
                display = LV_DISPLAY_KEEP;
                break;
            case LV_MIDDLE_OFF:
                size = LV_SIZE_MEDIUM;
                display = LV_DISPLAY_OFF;
                break;
            case LV_MIDDLE_ON:
                size = LV_SIZE_MEDIUM;
                display = LV_DISPLAY_ON;
                break;
            case LV_MIDDLE_KEEP:
                size = LV_SIZE_MEDIUM;
                display = LV_DISPLAY_KEEP;
                break;
            default:
                break;
        }

        Bundle args = new Bundle();
        args.putStringArray(Constants.RequestCode.POST_SHOOTING_LIVEVIEW.name(), new String[]{size, display});
        mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_LIVEVIEW, args, this));
    }

    private void startRtp(){
        // Get SDP.
        // Start RTP if the getting is successful.
        mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.GET_SHOOTING_LIVEVIEW_RTPSESSIONDESC, null, this));
    }

    private void stopRtp(){
        Bundle args = new Bundle();
        args.putStringArray(Constants.RequestCode.POST_SHOOTING_LIVEVIEW_RTP.toString(), new String[]{"stop", ""});
        mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_LIVEVIEW_RTP, args, this));
    }

    private void startHFFlickerDetection(){
        mHFFlickerApplyCheckBox.setEnabled(false);
        Bundle args = new Bundle();
        String apply = String.valueOf(mHFFlickerApplyCheckBox.isChecked());
        args.putStringArray(Constants.RequestCode.POST_SHOOTING_CONTROL_HFFLICKERDETECTION.name(), new String[]{"start", apply});
        mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_CONTROL_HFFLICKERDETECTION, args, true,this));
    }

    private void cancelHFFlickerDetection(){
        mHFFlickerApplyCheckBox.setEnabled(false);
        Bundle args = new Bundle();
        String apply = "";
        args.putStringArray(Constants.RequestCode.POST_SHOOTING_CONTROL_HFFLICKERDETECTION.name(), new String[]{"cancel", apply});
        mWebAPI.enqueueRequest(new WebAPIQueueDataSet(Constants.RequestCode.POST_SHOOTING_CONTROL_HFFLICKERDETECTION, args, true, this));
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}
