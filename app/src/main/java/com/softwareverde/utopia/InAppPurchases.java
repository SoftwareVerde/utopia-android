package com.softwareverde.utopia;

import android.app.Activity;
import android.content.Intent;

import com.android.vending.util.IabHelper;
import com.android.vending.util.IabResult;
import com.android.vending.util.Inventory;
import com.android.vending.util.Purchase;
import com.softwareverde.json.Json;
import com.softwareverde.utopia.ui.dialog.EditOptionDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InAppPurchases {
    private static InAppPurchases _instance;
    public static InAppPurchases getInstance(Activity activity) {
        if (_instance == null) {
            _instance = new InAppPurchases(activity, Settings.getGoogleApiKey());
        }

        return _instance;
    }
    public interface InventoryCallback {
        void run(Boolean itemWasPurchased);
    }
    public interface PurchaseItemCallback {
        void run(Boolean wasSuccessful);
    }

    public static class Items {
        public static final String DONATE_DOLLAR = "utopia_item_40f2796a78ae460b4a402e98c01a9508";
        public static final String DONATE_TWO_DOLLARS = "utopia_item_4242d37dd72ff97e7b771614a64d63ca";
        public static final String DONATE_FIVE_DOLLARS = "utopia_item_3c35d481859b643dc1dc01f6baf12c02";
        public static final String DONATE_TEN_DOLLARS = "utopia_item_9bf10b81fb3bbe70dd0185a98f717dc4";
    }

    public static class InAppPurchaseHelper {
        private InAppPurchases _inAppPurchases;
        private List<String> _pendingPurchaseRequests = new ArrayList<String>(Arrays.asList(
                InAppPurchases.Items.DONATE_DOLLAR, InAppPurchases.Items.DONATE_TWO_DOLLARS, InAppPurchases.Items.DONATE_FIVE_DOLLARS, InAppPurchases.Items.DONATE_TEN_DOLLARS
        ));
        private List<String> _itemsPurchased = new ArrayList<String>();

        private void _iterate(final Runnable callback) {
            if (_pendingPurchaseRequests.isEmpty()) {
                if (callback != null) {
                    callback.run();
                }
                return;
            }

            final String itemId = _pendingPurchaseRequests.remove(0);
            _inAppPurchases.isItemPurchased(itemId, new InAppPurchases.InventoryCallback() {
                @Override
                public void run(Boolean itemWasPurchased) {
                    if (itemWasPurchased) {
                        _itemsPurchased.add(itemId);
                    }

                    _iterate(callback);
                }
            });
        }

        public InAppPurchaseHelper(InAppPurchases inAppPurchases) {
            _inAppPurchases = inAppPurchases;
        }

        public void downloadPurchasedItems(final Runnable callback) {
            _iterate(callback);
        }

        public List<String> getPurchasedItems() { return new ArrayList<String>(_itemsPurchased); }
    }

    public static void promptDonateDialog(final Activity activity, final Runnable onSuccess) {
        final EditOptionDialog editOptionDialog = new EditOptionDialog();
        editOptionDialog.setActivity(activity);
        editOptionDialog.setTitle("Donate Amount");
        editOptionDialog.setContent(null);
        editOptionDialog.setCurrentValue(null);

        final String oneDollarDonation  = "One Dollar";
        final String twoDollarDonation  = "Two Dollars";
        final String fiveDollarDonation = "Five Dollars";
        final String tenDollarDonation  = "Ten Dollars";

        final InAppPurchases inAppPurchases = InAppPurchases.getInstance(activity);

        final List<String> donationOptions = new ArrayList<String>();

        final Runnable promptDonationOptions = new Runnable() {
            @Override
            public void run() {

                editOptionDialog.setOptions(donationOptions);

                editOptionDialog.setCallback(new EditOptionDialog.Callback() {
                    @Override
                    public void run(String newValue) {
                        final String selectedItem;

                        switch (newValue) {
                            case oneDollarDonation: {
                                selectedItem = InAppPurchases.Items.DONATE_DOLLAR;
                            } break;
                            case twoDollarDonation: {
                                selectedItem = InAppPurchases.Items.DONATE_TWO_DOLLARS;
                            } break;
                            case fiveDollarDonation: {
                                selectedItem = InAppPurchases.Items.DONATE_FIVE_DOLLARS;
                            } break;
                            case tenDollarDonation: {
                                selectedItem = InAppPurchases.Items.DONATE_TEN_DOLLARS;
                            } break;
                            default: {
                                selectedItem = "";
                            } break;
                        }

                        inAppPurchases.purchaseItem(selectedItem, new InAppPurchases.PurchaseItemCallback() {
                            @Override
                            public void run(Boolean wasSuccessful) {
                                System.out.println("Purchase Success: "+ wasSuccessful);

                                if (wasSuccessful) {
                                    if (onSuccess != null) {
                                        onSuccess.run();
                                    }
                                }
                                else { }
                            }
                        });
                    }
                });
                editOptionDialog.show(activity.getFragmentManager(), "DONATE_CLICKED");
            }
        };

        // Check for purchased items..
        inAppPurchases.isItemPurchased(InAppPurchases.Items.DONATE_DOLLAR, new InAppPurchases.InventoryCallback() {
            @Override
            public void run(Boolean hasItem) {
                if (! hasItem) {
                    donationOptions.add(oneDollarDonation);
                }

                inAppPurchases.isItemPurchased(InAppPurchases.Items.DONATE_TWO_DOLLARS, new InAppPurchases.InventoryCallback() {
                    @Override
                    public void run(Boolean hasItem) {
                        if (! hasItem) {
                            donationOptions.add(twoDollarDonation);
                        }

                        inAppPurchases.isItemPurchased(InAppPurchases.Items.DONATE_FIVE_DOLLARS, new InAppPurchases.InventoryCallback() {
                            @Override
                            public void run(Boolean hasItem) {
                                if (! hasItem) {
                                    donationOptions.add(fiveDollarDonation);
                                }

                                inAppPurchases.isItemPurchased(InAppPurchases.Items.DONATE_TEN_DOLLARS, new InAppPurchases.InventoryCallback() {
                                    @Override
                                    public void run(Boolean hasItem) {
                                        if (! hasItem) {
                                            donationOptions.add(tenDollarDonation);
                                        }

                                        promptDonationOptions.run();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    private Activity _activity;
    private IabHelper _inAppPurchaseHelper;
    private Inventory _inventory;
    private Boolean _isSetup;
    private String _googleApiKey;

    private void _setup(final Runnable callback) {
        if (_activity == null || _googleApiKey == null) {
            return; // Without calling the callback.
        }

        _inAppPurchaseHelper = new IabHelper(_activity, _googleApiKey);
        try {
            _inAppPurchaseHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) {
                    if (!result.isSuccess()) {
                        System.out.println("Could not setup in-app-billing: " + result);
                    }
                    else {
                        _isSetup = true;
                    }

                    if (callback != null) {
                        callback.run();
                    }
                }
            });
        }
        catch (IllegalStateException e) { }
    }

    private InAppPurchases(Activity activity, String googleApiKey) {
        _isSetup = false;
        _activity = activity;
        _googleApiKey = googleApiKey;
        _setup(null);
    }

    private void _updateInventory(final Runnable callback) {
        final Runnable updateInventory = new Runnable() {
            @Override
            public void run() {
                _inAppPurchaseHelper.queryInventoryAsync(new IabHelper.QueryInventoryFinishedListener() {
                    public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
                        if (! result.isFailure()) {
                            _inventory = inventory;
                        }

                        if (callback != null) {
                            callback.run();
                        }
                    }
                });
            }
        };

        if (! _isSetup) {
            _setup(updateInventory);
        }
        else {
            updateInventory.run();
        }
    }

    private Boolean _isSetup() {
        return (_isSetup && _inAppPurchaseHelper != null);
    }

    public void destroy() {
        if (_inAppPurchaseHelper != null) {
            try {
                _inAppPurchaseHelper.dispose();
            } catch (final Exception exception) { exception.printStackTrace(); }
            _inAppPurchaseHelper = null;
        }
    }

    public void isItemPurchased(final String item, final InventoryCallback callback) {
        final Runnable startIsItemPurchased = new Runnable() {
            @Override
            public void run() {
                Runnable checkInventoryRunnable = new Runnable() {
                    @Override
                    public void run() {
                        Boolean wasPurchased = false;
                        if (_inventory != null && _inventory.hasPurchase(item)) {
                            wasPurchased = true;
                        }

                        if (callback != null) {
                            callback.run(wasPurchased);
                        }
                    }
                };

                if (_inventory == null) {
                    _updateInventory(checkInventoryRunnable);
                }
                else {
                    checkInventoryRunnable.run();
                }
            }
        };

        if (! _isSetup()) {
            _setup(startIsItemPurchased);
        }
        else {
            startIsItemPurchased.run();
        }
    }

    public void purchaseItem(final String item, final PurchaseItemCallback purchaseItemCallback) {
        final Runnable purchaseItem = new Runnable() {
            @Override
            public void run() {
                final String imei = ""; // Disabled

                AndroidUtil.getAndroidId(_activity, new AndroidUtil.AndroidIdCallback() {
                    @Override
                    public void run(final String androidId) {
                        Json purchaseParameters = new Json();
                        purchaseParameters.put("imei", imei);
                        purchaseParameters.put("android_id", androidId);
                        purchaseParameters.put("item_id", item);

                        Integer requestId = 1;

                        IabHelper.OnIabPurchaseFinishedListener purchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
                            public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                                if (result.isFailure() || purchase == null) {
                                    if (purchaseItemCallback != null) {
                                        purchaseItemCallback.run(false);
                                    }

                                    return;
                                }

                                switch (purchase.getSku()) {
                                    case Items.DONATE_DOLLAR: {
                                        System.out.println("Purchased: "+ purchase.getSku());
                                    } break;
                                    case Items.DONATE_TWO_DOLLARS: {
                                        System.out.println("Purchased: "+ purchase.getSku());
                                    } break;
                                    case Items.DONATE_FIVE_DOLLARS: {
                                        System.out.println("Purchased: "+ purchase.getSku());
                                    } break;
                                    case Items.DONATE_TEN_DOLLARS: {
                                        System.out.println("Purchased: "+ purchase.getSku());
                                    } break;
                                    default: {
                                        System.out.println("Unknown SKU: " + purchase.getSku());
                                    }
                                    break;
                                }

                                _updateInventory(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (purchaseItemCallback != null) {
                                            purchaseItemCallback.run(true);
                                        }
                                    }
                                });
                            }
                        };

                        try {
                            _inAppPurchaseHelper.launchPurchaseFlow(_activity, item, requestId, purchaseFinishedListener, purchaseParameters.toString());
                        }
                        catch (IllegalStateException e) {
                            if (purchaseItemCallback != null) {
                                purchaseItemCallback.run(false);
                            }
                        }
                    }
                });
            }
        };

        if (! _isSetup()) {
            _setup(purchaseItem);
        }
        else {
            purchaseItem.run();
        }
    }

    public Boolean handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (! _isSetup()) {
            return false;
        }

        try {
            return _inAppPurchaseHelper.handleActivityResult(requestCode, resultCode, data);
        }
        catch (IllegalStateException e) {
            return false;
        }
    }
}
