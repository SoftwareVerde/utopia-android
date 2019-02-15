package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softwareverde.util.Dialog;
import com.softwareverde.util.StringUtil;
import com.softwareverde.utopia.Util;
import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.NewsParser;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.UtopiaUtil;
import com.softwareverde.utopia.news.NewsEvent;
import com.softwareverde.utopia.ui.HelpOverlay;
import com.softwareverde.utopia.ui.NewsEventIconMap;
import com.softwareverde.utopia.ui.adapter.NewsItemAdapter;

import java.util.Map;

public class NewsFragment extends Fragment {
    public static final String HISTORY_CALLBACK_IDENTIFIER = "HistoryFragmentThroneUpdateCallbackIdentifier";

    private Activity _activity;
    private Session _session;
    private View _view;
    private NewsItemAdapter _adapter;
    private NewsParser _parser = null;
    private HelpOverlay _helpOverlay;

    private enum NewsTab {
        PROVINCE, KINGDOM
    };
    private NewsTab _newsTab;

    private enum DisplayMode {
        SUMMARIZED, RAW
    }
    private DisplayMode _displayMode;

    private Integer _year;
    private Integer _month;

    public NewsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDetach() {
        _session.removeNewsCallback(HISTORY_CALLBACK_IDENTIFIER);
        super.onDetach();
    }



    private void _addSourceIconsToView(final Map<String, NewsEvent.Icon> icons, final Integer linearLayoutId) {
        final LinearLayout linearLayout = ((LinearLayout) _view.findViewById(linearLayoutId));
        linearLayout.removeAllViews();

        for (final String iconName : icons.keySet()) {
            final NewsEvent.Icon iconId = icons.get(iconName);

            final ImageView imageView = new ImageView(_activity);
            imageView.setImageResource(NewsEventIconMap.getResId(iconId));
            linearLayout.addView(imageView);
            imageView.getLayoutParams().height = 50;
            imageView.getLayoutParams().width = 50;
            imageView.requestLayout();

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _helpOverlay.show(iconName, imageView);
                }
            });
        }
    }

    private void _drawData() {
        String year = "00";
        if (_year != null) {
            year = _year.toString();
            if (year.length() == 1) {
                year = "0"+ year;
            }
        }

        String month = UtopiaUtil.getUtopianMonthFromIndex(_month);
        if (month == null || month.length() == 0) {
            month = "??";
        }

        ((TextView) _view.findViewById(R.id.news_month)).setText(month);
        ((TextView) _view.findViewById(R.id.news_year)).setText(year);

        if (_parser != null) {
            ((TextView) _view.findViewById(R.id.news_summary_net_acres)).setText(StringUtil.formatNumberString(_parser.getNetAcres()));
            ((TextView) _view.findViewById(R.id.news_summary_net_peasants)).setText(StringUtil.formatNumberString(_parser.getNetPeasants()));
            ((TextView) _view.findViewById(R.id.news_summary_net_soldiers)).setText(StringUtil.formatNumberString(_parser.getNetSoldiers()));
            ((TextView) _view.findViewById(R.id.news_summary_net_offensive_units)).setText(StringUtil.formatNumberString(_parser.getNetOffensiveUnits()));
            ((TextView) _view.findViewById(R.id.news_summary_net_defensive_units)).setText(StringUtil.formatNumberString(_parser.getNetDefensiveUnits()));
            ((TextView) _view.findViewById(R.id.news_summary_net_elites)).setText(StringUtil.formatNumberString(_parser.getNetElites()));
            ((TextView) _view.findViewById(R.id.news_summary_net_gold)).setText(StringUtil.formatNumberString(_parser.getNetGold()));
            ((TextView) _view.findViewById(R.id.news_summary_net_food)).setText(StringUtil.formatNumberString(_parser.getNetFood()));
            ((TextView) _view.findViewById(R.id.news_summary_net_runes)).setText(StringUtil.formatNumberString(_parser.getNetRunes()));
            // TODO: Net Thieves
            // TODO: Net Horses

            _addSourceIconsToView(_parser.getNetAcresSourceIcons(), R.id.news_summary_net_acres_sources);
            _addSourceIconsToView(_parser.getNetPeasantsSourceIcons(), R.id.news_summary_net_peasants_sources);
            _addSourceIconsToView(_parser.getNetSoldiersSourceIcons(), R.id.news_summary_net_soldiers_sources);
            _addSourceIconsToView(_parser.getNetOffensiveUnitsSourceIcons(), R.id.news_summary_net_offensive_units_sources);
            _addSourceIconsToView(_parser.getNetDefensiveUnitsSourceIcons(), R.id.news_summary_net_defensive_units_sources);
            _addSourceIconsToView(_parser.getNetElitesSourceIcons(), R.id.news_summary_net_elites_sources);
            _addSourceIconsToView(_parser.getNetGoldSourceIcons(), R.id.news_summary_net_gold_sources);
            _addSourceIconsToView(_parser.getNetFoodSourceIcons(), R.id.news_summary_net_food_sources);
            _addSourceIconsToView(_parser.getNetRunesSourceIcons(), R.id.news_summary_net_runes_sources);

            final LinearLayout afflictionIconsLayout = (LinearLayout) _view.findViewById(R.id.news_summary_affliction_icons);
            afflictionIconsLayout.removeAllViews();
            final Integer maxWidth = (afflictionIconsLayout.getWidth() == 0 ? _view.getWidth() : afflictionIconsLayout.getWidth());

            final Integer afflictionIconWidth = AndroidUtil.dpToPixels(55 + 4, _activity);
            Integer bufferContentWidth = 0;
            LinearLayout bufferLayout = new LinearLayout(_activity);
            bufferLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            bufferLayout.setOrientation(LinearLayout.HORIZONTAL);

            final Map<String, NewsEvent.Icon> afflictionIcons = _parser.getAfflictionIcons();
            final Map<String, Integer> afflictionDurations = _parser.getAfflictionDurations();
            for (final String iconName : afflictionIcons.keySet()) {

                final NewsEvent.Icon icon = afflictionIcons.get(iconName);
                final Integer duration = afflictionDurations.get(iconName);

                if (icon == null) {
                    continue;
                }

                if (duration == null) {
                    System.out.println("Duration Null For: "+ iconName);
                }

                if (bufferContentWidth + afflictionIconWidth >= maxWidth) {
                    afflictionIconsLayout.addView(bufferLayout);

                    bufferLayout = new LinearLayout(_activity);
                    bufferLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    bufferLayout.setOrientation(LinearLayout.HORIZONTAL);

                    bufferContentWidth = 0;
                }

                final View afflictionIconLayout = _activity.getLayoutInflater().inflate(R.layout.affliction_icon_layout, bufferLayout, false);
                ((TextView) afflictionIconLayout.findViewById(R.id.affliction_icon_textview)).setText(duration != 0 ? duration +"H" : "");
                ((ImageView) afflictionIconLayout.findViewById(R.id.affliction_icon_imageview)).setImageResource(NewsEventIconMap.getResId(icon));

                afflictionIconLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _helpOverlay.show(iconName, afflictionIconLayout);
                    }
                });

                bufferLayout.addView(afflictionIconLayout);
                bufferContentWidth += afflictionIconWidth;
            }

            afflictionIconsLayout.addView(bufferLayout);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.news, container, false);
        _view = rootView;

        _helpOverlay = new HelpOverlay(_activity, _view);

        final ListView listView = (ListView) _view.findViewById(R.id.news_list);

        _adapter = new NewsItemAdapter(_activity);
        _adapter.setDisplayInReverse(true);
        listView.setAdapter(_adapter);

        Province province = _session.getProvince();
        String royalCommands = province.getRoyalCommands();
        if (royalCommands.length() == 0) {
            _view.findViewById(R.id.news_royal_commands_title).setVisibility(View.GONE);
            _view.findViewById(R.id.news_royal_commands).setVisibility(View.GONE);
        }
        else {
            ((TextView) _view.findViewById(R.id.news_royal_commands)).setText(royalCommands);
        }

        _view.findViewById(R.id.news_tab_province).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _showProvinceNews();
                _updateNewsTabButtons();
            }
        });
        _view.findViewById(R.id.news_tab_kingdom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _showRawFormat();
                _updateNewsTabFormatButtons();

                _showKingdomNews();
                _updateNewsTabButtons();
            }
        });

        _view.findViewById(R.id.news_tab_format_raw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _showRawFormat();
                _updateNewsTabFormatButtons();
            }
        });
        _view.findViewById(R.id.news_tab_format_summary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _showSummaryFormat();
                _updateNewsTabFormatButtons();
            }
        });

        _view.findViewById(R.id.news_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _session.downloadNews(_month+1, _year, null);
            }
        });
        _view.findViewById(R.id.news_previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _session.downloadNews(_month-1, _year, null);
            }
        });

        _drawData();

        _showSummaryFormat();
        _updateNewsTabFormatButtons();

        return rootView;
    }

    private void _updateNewsTabButtons() {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (_newsTab.equals(NewsTab.PROVINCE)) {
                    AndroidUtil.setButtonPressedStyle(_view, R.id.news_tab_province, true);
                    AndroidUtil.setButtonPressedStyle(_view, R.id.news_tab_kingdom, false);
                }
                else {
                    AndroidUtil.setButtonPressedStyle(_view, R.id.news_tab_province, false);
                    AndroidUtil.setButtonPressedStyle(_view, R.id.news_tab_kingdom, true);
                }
            }
        });
    }

    private void _updateNewsTabFormatButtons() {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (_displayMode.equals(DisplayMode.RAW)) {
                    AndroidUtil.setButtonPressedStyle(_view, R.id.news_tab_format_raw, true);
                    AndroidUtil.setButtonPressedStyle(_view, R.id.news_tab_format_summary, false);
                }
                else {
                    AndroidUtil.setButtonPressedStyle(_view, R.id.news_tab_format_raw, false);
                    AndroidUtil.setButtonPressedStyle(_view, R.id.news_tab_format_summary, true);
                }
            }
        });
    }

    private void _showProvinceNews() {
        _newsTab = NewsTab.PROVINCE;

        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _view.findViewById(R.id.news_royal_commands_wrapper).setVisibility(View.VISIBLE);
                _view.findViewById(R.id.news_tab_format_layout).setVisibility(View.VISIBLE);

                _adapter.clear();
                _adapter.addAll(_session.getNews());

                _drawData();
            }
        });
    }

    private void _showKingdomNews() {
        _newsTab = NewsTab.KINGDOM;

        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _view.findViewById(R.id.news_royal_commands_wrapper).setVisibility(View.GONE);
                _view.findViewById(R.id.news_tab_format_layout).setVisibility(View.GONE);

                _adapter.clear();
                _adapter.addAll(_session.getKingdomNews());

                _drawData();
            }
        });
    }

    private void _showRawFormat() {
        _displayMode = DisplayMode.RAW;

        _view.findViewById(R.id.news_list).setVisibility(View.VISIBLE);
        _view.findViewById(R.id.news_summary_container).setVisibility(View.GONE);

        _drawData();
    }

    private void _showSummaryFormat() {
        _displayMode = DisplayMode.SUMMARIZED;

        _view.findViewById(R.id.news_list).setVisibility(View.GONE);
        _view.findViewById(R.id.news_summary_container).setVisibility(View.VISIBLE);

         _drawData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;
        _session = Session.getInstance();
        _newsTab = NewsTab.PROVINCE;
        _displayMode = DisplayMode.SUMMARIZED;

        _session.addNewsCallback(HISTORY_CALLBACK_IDENTIFIER, new Session.DownloadNewsCallback() {
            public void run(Integer month, Integer year) {
                _month = month;
                _year = year;

                if (_month == null || _year == null) {
                    _month = 0;
                    _year = 0;
                }

                _parser = new NewsParser(_session.getProvince(), _session.getNews(), _activity);

                if (_newsTab.equals(NewsTab.PROVINCE)) {
                    _showProvinceNews();
                }
                else if (_newsTab.equals(NewsTab.KINGDOM)) {
                    _showKingdomNews();
                }
            }
        });

        _session.downloadNews(new Session.Callback() {
            @Override
            public void run(Session.SessionResponse response) {
                if (!response.getWasSuccess()) {
                    Dialog.setActivity(_activity);
                    Dialog.alert("Download News", "Error downloading news:\n\n  " + response.getErrorMessage() + "\n", null);
                }
            }
        });

        _year = _session.getCurrentUtopiaYear();
        _month = _session.getCurrentUtopiaMonth();
    }
}