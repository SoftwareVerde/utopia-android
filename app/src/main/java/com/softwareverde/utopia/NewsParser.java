package com.softwareverde.utopia;

import android.app.Activity;

import com.softwareverde.util.Dialog;
import com.softwareverde.util.Util;
import com.softwareverde.util.WebRequest;
import com.softwareverde.utopia.database.AndroidKeyValueStore;
import com.softwareverde.utopia.news.NewsAttackEvent;
import com.softwareverde.utopia.news.NewsEvent;
import com.softwareverde.utopia.news.NewsMiscEvent;
import com.softwareverde.utopia.news.NewsSpellEvent;
import com.softwareverde.utopia.news.NewsThieveryEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsParser {
    private Activity _activity;
    private AndroidKeyValueStore _keyValueStore;

    private Province _province;
    private List<NewsEvent> _newsEvents;

    private Integer _netAcres = 0;
    private Integer _netFood = 0;
    private Integer _netGold = 0;
    private Integer _netPeasants = 0;
    private Integer _netSoldiers = 0;
    private Integer _netOffensiveUnits = 0;
    private Integer _netDefensiveUnits = 0;
    private Integer _netElites = 0;
    private Integer _netThieves = 0;
    private Integer _netHorses = 0;
    private Integer _netRunes = 0;

    private void _logUnknownNewsEvents(final List<String> unknownEvents) {
        final WebRequest webRequest = new WebRequest();
        webRequest.setUrl(Settings.getUnknownNewsLogUrl());
        webRequest.setType(WebRequest.RequestType.POST);

        for (final String unknownEvent : unknownEvents) {
            webRequest.addPostParam("unknown_events", unknownEvent);
        }

        webRequest.execute(true);
    }

    public NewsParser(final Province province, final List<NewsEvent> newsEvents, final Activity activity) {
        _activity = activity;
        _keyValueStore = new AndroidKeyValueStore(_activity, AndroidKeyValueStore.Stores.NEWS_PARSER);

        _newsEvents = new ArrayList<NewsEvent>();
        _province = province;

        for (final NewsEvent newsEvent : newsEvents) {
            _newsEvents.add(newsEvent);
        }

        _parse();
    }

    private void _calculateNetResource(final NewsEvent parsedNewsEvent) {
        _netAcres += parsedNewsEvent.getNetAcres();
        _netPeasants += parsedNewsEvent.getNetPeasants();
        _netSoldiers += parsedNewsEvent.getNetSoldiers();
        _netOffensiveUnits += parsedNewsEvent.getNetOffensiveUnits();
        _netDefensiveUnits += parsedNewsEvent.getNetDefensiveUnits();
        _netElites += parsedNewsEvent.getNetElites();
        _netGold += parsedNewsEvent.getNetGold();
        _netRunes += parsedNewsEvent.getNetRunes();
        _netFood += parsedNewsEvent.getNetFood();
        _netThieves += parsedNewsEvent.getNetThieves();
        _netHorses += parsedNewsEvent.getNetHorses();
    }

    private void _parse() {
        final List<String> unparsedNewsEvents = new ArrayList<String>();
        final List<NewsEvent> parsedNewsEvents = new ArrayList<NewsEvent>();

        for (final NewsEvent newsEvent : _newsEvents) {
            final NewsAttackEvent newsAttackEvent = NewsAttackEvent.fromNewsEvent(_province, newsEvent);
            final NewsSpellEvent newsSpellEvent = NewsSpellEvent.fromNewsEvent(_province, newsEvent);
            final NewsThieveryEvent newsThieveryEvent = NewsThieveryEvent.fromNewsEvent(_province, newsEvent);
            final NewsMiscEvent newsMiscEvent = NewsMiscEvent.fromNewsEvent(_province, newsEvent);

            final List<NewsEvent> newParsedNewsEvents = new ArrayList<NewsEvent>();

            if (newsAttackEvent != null) {
                newParsedNewsEvents.add(newsAttackEvent);
                _calculateNetResource(newsAttackEvent);
            }

            if (newsSpellEvent != null) {
                newParsedNewsEvents.add(newsSpellEvent);
                _calculateNetResource(newsSpellEvent);
            }

            if (newsThieveryEvent != null) {
                newParsedNewsEvents.add(newsThieveryEvent);
                _calculateNetResource(newsThieveryEvent);
            }

            if (newsMiscEvent != null) {
                newParsedNewsEvents.add(newsMiscEvent);
                _calculateNetResource(newsMiscEvent);
            }

            if (newParsedNewsEvents.isEmpty()) {
                unparsedNewsEvents.add("[U] - "+ newsEvent.getNews());
            }

            if (((int) (Math.random() * 1000)) % 100 < 5) {
                unparsedNewsEvents.add("[R] - "+ newsEvent.getNews());
            }

            parsedNewsEvents.addAll(newParsedNewsEvents);
        }

        _newsEvents = parsedNewsEvents;

        if (! unparsedNewsEvents.isEmpty()) {
            final Boolean firstNewsSubmission = (! _keyValueStore.hasKey("SUBMIT_UNKNOWN_EVENTS"));
            final Boolean submitUnknownEvents = (Util.parseInt(_keyValueStore.getString("SUBMIT_UNKNOWN_EVENTS")) > 0);

            if (firstNewsSubmission) {
                Dialog.setActivity(_activity);
                Dialog.confirm("Send Unknown News Events", "Some news events weren't recognized by the Utopia app.\n\nDo you want to submit them to help make the app better?", new Runnable() {
                    @Override
                    public void run() {
                        _logUnknownNewsEvents(unparsedNewsEvents);
                        _keyValueStore.putString("SUBMIT_UNKNOWN_EVENTS", "1");
                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                        _keyValueStore.putString("SUBMIT_UNKNOWN_EVENTS", "0");
                    }
                });
            }
            else if (submitUnknownEvents) {
                _logUnknownNewsEvents(unparsedNewsEvents);
            }
        }
    }

    private interface NewsEventSourceIconLambda {
        Boolean newsEventIsIconApplicable(NewsEvent newsEvent);
    }
    private Map<String, NewsEvent.Icon> _getSourceIcons(final NewsEventSourceIconLambda lambda) {
        final Map<String, NewsEvent.Icon> sourceIcons = new HashMap<String, NewsEvent.Icon>();

        for (final NewsEvent newsEvent : _newsEvents) {
            final String iconName = newsEvent.getIconName();
            final NewsEvent.Icon sourceIcon = newsEvent.getIcon();

            if (sourceIcon == null && iconName != null) {
                System.out.println("No Icon Found For: "+ iconName);
            }

            if (sourceIcon == null) { continue; }
            if (iconName == null) { continue; }
            if (lambda.newsEventIsIconApplicable(newsEvent)) { continue; }

            if (! sourceIcons.containsKey(iconName)) {
                sourceIcons.put(iconName, sourceIcon);
            }
        }

        return sourceIcons;
    }

    public Integer getNetAcres() { return _netAcres; }
    public Integer getNetPeasants() { return _netPeasants; }
    public Integer getNetSoldiers() { return _netSoldiers; }
    public Integer getNetOffensiveUnits() { return _netOffensiveUnits; }
    public Integer getNetDefensiveUnits() { return _netDefensiveUnits; }
    public Integer getNetElites() { return _netElites; }
    public Integer getNetGold() { return _netGold; }
    public Integer getNetRunes() { return _netRunes; }
    public Integer getNetFood() { return _netFood; }
    public Integer getNetHorses() { return _netHorses; }
    public Integer getNetThieves() { return _netThieves; }

    public Map<String, NewsEvent.Icon> getNetAcresSourceIcons() {
        return _getSourceIcons(new NewsEventSourceIconLambda() {
            @Override
            public Boolean newsEventIsIconApplicable(final NewsEvent newsEvent) {
                return (newsEvent.getNetAcres() == 0);
            }
        });
    }

    public Map<String, NewsEvent.Icon> getNetPeasantsSourceIcons() {
        return _getSourceIcons(new NewsEventSourceIconLambda() {
            @Override
            public Boolean newsEventIsIconApplicable(final NewsEvent newsEvent) {
                return (newsEvent.getNetPeasants() == 0);
            }
        });
    }

    public Map<String, NewsEvent.Icon> getNetSoldiersSourceIcons() {
        return _getSourceIcons(new NewsEventSourceIconLambda() {
            @Override
            public Boolean newsEventIsIconApplicable(final NewsEvent newsEvent) {
                return (newsEvent.getNetSoldiers() == 0);
            }
        });
    }

    public Map<String, NewsEvent.Icon> getNetOffensiveUnitsSourceIcons() {
        return _getSourceIcons(new NewsEventSourceIconLambda() {
            @Override
            public Boolean newsEventIsIconApplicable(final NewsEvent newsEvent) {
                return (newsEvent.getNetOffensiveUnits() == 0);
            }
        });
    }

    public Map<String, NewsEvent.Icon> getNetDefensiveUnitsSourceIcons() {
        return _getSourceIcons(new NewsEventSourceIconLambda() {
            @Override
            public Boolean newsEventIsIconApplicable(final NewsEvent newsEvent) {
                return (newsEvent.getNetDefensiveUnits() == 0);
            }
        });
    }

    public Map<String, NewsEvent.Icon> getNetElitesSourceIcons() {
        return _getSourceIcons(new NewsEventSourceIconLambda() {
            @Override
            public Boolean newsEventIsIconApplicable(final NewsEvent newsEvent) {
                return (newsEvent.getNetElites() == 0);
            }
        });
    }

    public Map<String, NewsEvent.Icon> getNetGoldSourceIcons() {
        return _getSourceIcons(new NewsEventSourceIconLambda() {
            @Override
            public Boolean newsEventIsIconApplicable(final NewsEvent newsEvent) {
                return (newsEvent.getNetGold() == 0);
            }
        });
    }

    public Map<String, NewsEvent.Icon> getNetFoodSourceIcons() {
        return _getSourceIcons(new NewsEventSourceIconLambda() {
            @Override
            public Boolean newsEventIsIconApplicable(final NewsEvent newsEvent) {
                return (newsEvent.getNetFood() == 0);
            }
        });
    }

    public Map<String, NewsEvent.Icon> getNetRunesSourceIcons() {
        return _getSourceIcons(new NewsEventSourceIconLambda() {
            @Override
            public Boolean newsEventIsIconApplicable(final NewsEvent newsEvent) {
                return (newsEvent.getNetRunes() == 0);
            }
        });
    }

    public Map<String, NewsEvent.Icon> getNetHorsesSourceIcons() {
        return _getSourceIcons(new NewsEventSourceIconLambda() {
            @Override
            public Boolean newsEventIsIconApplicable(final NewsEvent newsEvent) {
                return (newsEvent.getNetHorses() == 0);
            }
        });
    }

    public Map<String, NewsEvent.Icon> getNetThievesSourceIcons() {
        return _getSourceIcons(new NewsEventSourceIconLambda() {
            @Override
            public Boolean newsEventIsIconApplicable(final NewsEvent newsEvent) {
                return (newsEvent.getNetThieves() == 0);
            }
        });
    }

    public Map<String, NewsEvent.Icon> getAfflictionIcons() {
        final Map<String, NewsEvent.Icon> afflictionIcons = new HashMap<String, NewsEvent.Icon>();

        for (final NewsEvent newsEvent : _newsEvents) {
            if (newsEvent.isAffliction()) {
                afflictionIcons.put(newsEvent.getIconName(), newsEvent.getIcon());
            }
        }

        return afflictionIcons;
    }

    public Map<String, Integer> getAfflictionDurations() {
        final Map<String, Integer> afflictionDurations = new HashMap<String, Integer>();

        for (final NewsEvent newsEvent : _newsEvents) {
            final Integer duration = newsEvent.getDuration();
            if (newsEvent.isAffliction()) {
                afflictionDurations.put(newsEvent.getIconName(), (duration != null ? duration : 0));
            }
        }

        return afflictionDurations;
    }
}
