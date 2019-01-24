package com.softwareverde.utopia.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.softwareverde.util.Dialog;
import com.softwareverde.utopia.AndroidUtil;
import com.softwareverde.utopia.Chatroom;
import com.softwareverde.utopia.Kingdom;
import com.softwareverde.utopia.PrivateMessage;
import com.softwareverde.utopia.Province;
import com.softwareverde.utopia.ProvinceTagUtil;
import com.softwareverde.utopia.R;
import com.softwareverde.utopia.Session;
import com.softwareverde.utopia.database.AndroidKeyValueStore;
import com.softwareverde.utopia.database.KeyValueStore;
import com.softwareverde.utopia.ui.adapter.ChatroomMessageAdapter;
import com.softwareverde.utopia.ui.adapter.PrivateMessageAdapter;
import com.softwareverde.utopia.ui.dialog.EditOptionDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommunicationFragment extends Fragment {
    private static String _ON_MESSAGE_CHANGE_CALLBACK_IDENTIFIER = "ChatroomFragmentMessageChange";
    private static String _ON_PRIVATE_MESSAGE_CHANGE_CALLBACK_IDENTIFIER = "ChatroomFragmentPrivateMessageChange";

    private LayoutInflater _inflater;
    private FrameLayout _subviewContainer;

    private Activity _activity;
    private Session _session;
    private KeyValueStore _keyValueStore;

    private View _view;
    private ChatroomMessageAdapter _chatroomMessageAdapter;
    private PrivateMessageAdapter _privateMessageAdapter;
    private Chatroom _chatroom;
    private Chatroom.Message _lastMessage = null;

    private View _chatroomSubview = null;
    private View _privateMessagesSubview = null;
    private View _composePrivateMessagesSubview = null;
    private Kingdom.Identifier _selectedKingdomIdentifier;
    private PrivateMessage _privateMessage;

    public enum Tab {
        CHATROOM, PRIVATE_MESSAGES
    }
    private Tab _currentTab = Tab.CHATROOM;

    public CommunicationFragment() { }

    private void _showLoadingScreen() {
        AndroidUtil.showLoadingScreen(_activity);
    }
    private void _hideLoadingScreen() {
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.hideLoadingScreen();
            }
        });
    }

    private void _drawChatroomData() {
        if (_currentTab != Tab.CHATROOM) {
            return;
        }

        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (_lastMessage != null) {
                    _chatroomMessageAdapter.addAll(_chatroom.getMessagesAfter(_lastMessage.getId()));
                }
                else {
                    _chatroomMessageAdapter.addAll(_chatroom.getMessages());
                }
                _lastMessage = _chatroom.getLastMessage();
            }
        });
    }

    private void _drawPrivateMessageData() {
        if (_currentTab != Tab.PRIVATE_MESSAGES) {
            return;
        }

        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _privateMessageAdapter.clear();
                _privateMessageAdapter.addAll(_session.getPrivateMessages());
            }
        });
    }

    private void _drawComposePrivateMessageData() {
        if (_composePrivateMessagesSubview == null) {
            return;
        }

        String provinceName = "SELECT PROVINCE";
        Province receivingProvince = _privateMessage.getReceivingProvince();
        if (receivingProvince != null) {
            provinceName = receivingProvince.getName();
        }

        ((TextView) _composePrivateMessagesSubview.findViewById(R.id.compose_private_message_recipient_name)).setText(provinceName.toUpperCase());

        KingdomFragment.drawKingdomNavigation(_session, _view, _selectedKingdomIdentifier);
    }

    public void setTab(Tab tab) {
        _currentTab = tab;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (! _session.downloadMessagesThreadIsRunning()) {
            _session.startDownloadMessagesThread();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        _inflater = inflater;

        final View rootView = _inflater.inflate(R.layout.communication, container, false);
        _view = rootView;

        _chatroom.setUsername(_session.getProvince().getName());

        _subviewContainer = (FrameLayout) _view.findViewById(R.id.communication_subview_container);

        _view.findViewById(R.id.communication_tab_chatroom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AndroidUtil.setButtonPressedStyle(_view, R.id.communication_tab_chatroom, true);
                        AndroidUtil.setButtonPressedStyle(_view, R.id.communication_tab_private_messages, false);
                    }
                });

                _showChatroom();
            }
        });
        _view.findViewById(R.id.communication_tab_private_messages).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AndroidUtil.setButtonPressedStyle(_view, R.id.communication_tab_chatroom, false);
                        AndroidUtil.setButtonPressedStyle(_view, R.id.communication_tab_private_messages, true);
                    }
                });

                _showPrivateMessages();
            }
        });

        if (_currentTab.equals(Tab.CHATROOM)) {
            AndroidUtil.setButtonPressedStyle(_view, R.id.communication_tab_chatroom, true);
            AndroidUtil.setButtonPressedStyle(_view, R.id.communication_tab_private_messages, false);

            _showChatroom();
        }
        else if (_currentTab.equals(Tab.PRIVATE_MESSAGES)) {
            AndroidUtil.setButtonPressedStyle(_view, R.id.communication_tab_chatroom, false);
            AndroidUtil.setButtonPressedStyle(_view, R.id.communication_tab_private_messages, true);

            _showPrivateMessages();
        }

        _session.downloadPrivateMessages(null);

        return rootView;
    }

    private void _applyTagSuggestion(final String provinceName) {
        final EditText input = (EditText) _chatroomSubview.findViewById(R.id.chatroom_message_input);
        final String inputText = input.getText().toString();

        final String newText = ProvinceTagUtil.applyProvinceTagSuggestion(inputText, provinceName);

        input.setText(newText);
        input.setSelection(newText.indexOf("@"+ provinceName) + provinceName.length() + 2); // 1 for "@", 1 for " "
    }

    private void _showChatroom() {
        if (_chatroomSubview == null) {
            _chatroomSubview = _inflater.inflate(R.layout.chatroom, _subviewContainer, false);

            final EditText input = (EditText) _chatroomSubview.findViewById(R.id.chatroom_message_input);

            final View tagListWrapper = _chatroomSubview.findViewById(R.id.chatroom_message_kingdom_mate_tag_wrapper);
            tagListWrapper.setVisibility(View.GONE);

            input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    if (keyEvent == null) { return false; }

                    if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && !(keyEvent.isCapsLockOn() || keyEvent.isShiftPressed())) {

                        _session.sendChatMessage(input.getText().toString(), new Session.Callback() {
                            @Override
                            public void run(Session.SessionResponse response) {
                                _drawChatroomData();
                            }
                        });

                        input.setText("");
                        return true;
                    }

                    return false;
                }
            });
            input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence inputTextCharSequence, int startIndex, int oldCount, int newCount) {
                    final String inputText = inputTextCharSequence.toString();
                    if (inputText.length() == 0) {
                        tagListWrapper.setVisibility(View.GONE);
                        return;
                    }

                    if (! inputText.contains("@")) {
                        tagListWrapper.setVisibility(View.GONE);
                        return;
                    }

                    _clearTagSuggestionListView();
                    final List<String> provinceTagSuggestions = ProvinceTagUtil.calculateProvinceTagSuggestions(inputText, _session.getKingdom().getProvinces());
                    for (final String provinceSuggestion : provinceTagSuggestions) {
                        _addTagSuggestionItem(provinceSuggestion);
                    }

                    if (provinceTagSuggestions.size() > 0) {
                        tagListWrapper.setVisibility(View.VISIBLE);
                    }
                    else {
                        tagListWrapper.setVisibility(View.GONE);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) { }
            });

            _chatroomSubview.findViewById(R.id.chatroom_message_send_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _session.sendChatMessage(input.getText().toString(), new Session.Callback() {
                        @Override
                        public void run(Session.SessionResponse response) {
                            _drawChatroomData();
                        }
                    });

                    input.setText("");
                }
            });

            ((ListView) _chatroomSubview.findViewById(R.id.chatroom_message_listview)).setAdapter(_chatroomMessageAdapter);
        }

        _currentTab = Tab.CHATROOM;

        _subviewContainer.removeAllViews();
        _subviewContainer.addView(_chatroomSubview);

        _drawChatroomData();
    }

    private void _showPrivateMessages() {
        if (_privateMessagesSubview == null) {
            _privateMessagesSubview = _inflater.inflate(R.layout.private_messages, _subviewContainer, false);

            _privateMessageAdapter = new PrivateMessageAdapter(_activity);
            ListView privateMessagesListview = ((ListView) _privateMessagesSubview.findViewById(R.id.private_messages_listview));
            privateMessagesListview.setAdapter(_privateMessageAdapter);
            privateMessagesListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PrivateMessage privateMessage = _privateMessageAdapter.getItem(position);

                    FragmentManager fragmentManager = ((AppCompatActivity) _activity).getSupportFragmentManager();

                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

                    ReadPrivateMessageFragment readPrivateMessageFragment = new ReadPrivateMessageFragment();
                    readPrivateMessageFragment.setPrivateMessage(privateMessage);
                    transaction.replace(R.id.container, readPrivateMessageFragment);

                    AndroidUtil.closeKeyboard(_activity);
                    transaction.commitAllowingStateLoss();
                }
            });

            _privateMessagesSubview.findViewById(R.id.create_private_message_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _showCreatePrivateMessage();
                }
            });
        }

        AndroidUtil.closeKeyboard(_activity);

        _currentTab = Tab.PRIVATE_MESSAGES;

        _subviewContainer.removeAllViews();
        _subviewContainer.addView(_privateMessagesSubview);

        _drawPrivateMessageData();
    }

    private void _showCreatePrivateMessage() {
        if (_composePrivateMessagesSubview == null) {
            _composePrivateMessagesSubview = _inflater.inflate(R.layout.compose_private_message, _subviewContainer, false);

            _privateMessage = new PrivateMessage();
            _privateMessage.setSendingProvince(_session.getProvince());

            _selectedKingdomIdentifier = _session.getFocusedKingdomIdentifier();
            if (_selectedKingdomIdentifier == null) {
                _selectedKingdomIdentifier = _session.getKingdom().getIdentifier();
            }

            KingdomFragment.bindKingdomNavigation(_activity, _session, _composePrivateMessagesSubview, _selectedKingdomIdentifier, new Runnable() {
                @Override
                public void run() {
                    _drawComposePrivateMessageData();
                }
            });

            _composePrivateMessagesSubview.findViewById(R.id.compose_private_message_recipient_name).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String recipientName = "";
                    if (_privateMessage.getReceivingProvince() != null) {
                        recipientName = _privateMessage.getReceivingProvince().getName();
                    }

                    final List<String> provinceNames = new ArrayList<String>();
                    final Map<String, Province> provinceNameMap = new HashMap<String, Province>();
                    for (Province province : _session.getKingdom(_selectedKingdomIdentifier).getProvinces()) {
                        provinceNames.add(province.getName());
                        provinceNameMap.put(province.getName(), province);
                    }

                    EditOptionDialog editOptionDialog = new EditOptionDialog();
                    editOptionDialog.setActivity(_activity);
                    editOptionDialog.setTitle("Select Province "+ _selectedKingdomIdentifier);
                    editOptionDialog.setCurrentValue(recipientName);
                    editOptionDialog.setContent("Province: ");
                    editOptionDialog.setOptions(provinceNames);

                    editOptionDialog.setCallback(new EditOptionDialog.Callback() {
                        @Override
                        public void run(String setValue) {
                            if (setValue != null && setValue != "") {
                                _privateMessage.setReceivingProvince(provinceNameMap.get(setValue));
                            }

                            _drawComposePrivateMessageData();
                        }
                    });

                    editOptionDialog.show(_activity.getFragmentManager(), "EDIT_OPTION");
                }
            });

            _composePrivateMessagesSubview.findViewById(R.id.compose_private_message_send_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = ((EditText) _composePrivateMessagesSubview.findViewById(R.id.compose_private_message_title)).getText().toString();
                    _privateMessage.setTitle(title);

                    String content = ((EditText) _composePrivateMessagesSubview.findViewById(R.id.compose_private_message_content)).getText().toString();
                    _privateMessage.setContent(content);

                    AndroidUtil.closeKeyboard(_activity);

                    _sendPrivateMessage();
                }
            });

            // Padding Hack: (Bug in Android)
            Integer[] padLeftIds = new Integer[] {
                    R.id.compose_private_message_content, R.id.compose_private_message_title
            };
            for (Integer viewId : padLeftIds) {
                EditText editText = ((EditText) _composePrivateMessagesSubview.findViewById(viewId));
                editText.setPadding(40, editText.getPaddingTop(), editText.getPaddingRight(), editText.getPaddingBottom());
            }
        }

        AndroidUtil.closeKeyboard(_activity);

        _subviewContainer.removeAllViews();
        _subviewContainer.addView(_composePrivateMessagesSubview);
        _composePrivateMessagesSubview.startAnimation(AnimationUtils.loadAnimation(_activity, R.anim.abc_slide_in_bottom));

        _drawComposePrivateMessageData();
    }

    private void _sendPrivateMessage() {
        _showLoadingScreen();

        _session.sendPrivateMessage(_privateMessage, new Session.Callback() {
            @Override
            public void run(Session.SessionResponse response) {
                _hideLoadingScreen();

                if (! response.getWasSuccess()) {
                    Dialog.alert("Send Message Failed", response.getErrorMessage(), null);
                    return;
                }

                Dialog.alert("Message Sent", "Message sent!", new Runnable() {
                    @Override
                    public void run() {
                        _activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                _showPrivateMessages();
                            }
                        });
                    }
                });
            }
        });
    }

    private void _addTagSuggestionItem(final String provinceName) {
        final View tagListWrapper = _chatroomSubview.findViewById(R.id.chatroom_message_kingdom_mate_tag_wrapper);
        final LinearLayout tagList = (LinearLayout) _chatroomSubview.findViewById(R.id.chatroom_message_kingdom_mate_tag_list);

        final View tagItemView = _inflater.inflate(R.layout.kingom_mate_tag_item, null);
        ((TextView) tagItemView.findViewById(R.id.kingdom_mate_tag_item_textview)).setText(provinceName);

        tagList.addView(tagItemView);

        tagItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _applyTagSuggestion(provinceName);
                _clearTagSuggestionListView();
                tagListWrapper.setVisibility(View.GONE);
            }
        });
    }

    private void _clearTagSuggestionListView() {
        final LinearLayout tagList = (LinearLayout) _chatroomSubview.findViewById(R.id.chatroom_message_kingdom_mate_tag_list);
        while (tagList.getChildCount() > 0) {
            tagList.removeViewAt(0);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _activity = activity;
        _session = Session.getInstance();
        _keyValueStore = new AndroidKeyValueStore(_activity, AndroidKeyValueStore.Stores.COMMUNICATION_FRAGMENT);

        _chatroomMessageAdapter = new ChatroomMessageAdapter(_activity);
        _chatroomMessageAdapter.setProvince(_session.getProvince());

        _chatroom = _session.getChatroom();

        if (_keyValueStore.hasKey("LAST_MESSAGE_ID")) {
            final String lastMessageId = _keyValueStore.getString("LAST_MESSAGE_ID");
            _chatroomMessageAdapter.setLastReadMessageId(lastMessageId);
        }

        _drawChatroomData();

        if (! _chatroom.hasMessages()) {
            _showLoadingScreen();
        }

        _session.addMessagesCallback(_ON_MESSAGE_CHANGE_CALLBACK_IDENTIFIER, new Runnable() {
            @Override
            public void run() {
                _drawChatroomData();
                _hideLoadingScreen();

                _keyValueStore.putString("LAST_MESSAGE_ID", _chatroom.getLastMessage().getId());
            }
        });

        _session.addPrivateMessageCallback(_ON_PRIVATE_MESSAGE_CHANGE_CALLBACK_IDENTIFIER, new Runnable() {
            @Override
            public void run() {
                _drawPrivateMessageData();
                _hideLoadingScreen();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();

        _session.removeMessagesCallback(_ON_MESSAGE_CHANGE_CALLBACK_IDENTIFIER);
        _session.removePrivateMessageCallback(_ON_PRIVATE_MESSAGE_CHANGE_CALLBACK_IDENTIFIER);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}