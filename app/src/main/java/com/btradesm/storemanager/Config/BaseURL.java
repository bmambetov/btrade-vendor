package com.btradesm.storemanager.Config;


public class BaseURL {

    static final String APP_NAME = "StoreManager";
    public static final String PREFS_NAME = "GroceryLoginPrefs";
    public static final String PREFS_NAME2 = "GroceryLoginPrefs2";
    public static final String IS_LOGIN = "isLogin";
    public static final String KEY_NAME = "user_fullname";
    public static final String KEY_EMAIL = "user_email";
    public static final String KEY_ID = "user_id";
    public static final String KEY_MOBILE = "user_phone";
    public static final String KEY_IMAGE = "user_image";
    public static final String KEY_PINCODE = "pincode";
    public static final String KEY_SOCITY_ID = "Socity_id";
    public static final String KEY_SOCITY_NAME = "socity_name";
    public static final String KEY_HOUSE = "house_no";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DELIVERY_BOY_ID = "BOY_ID";
    public static final String KEY_STOCK_PRODUCTS_ID = "STOCK_PRODUCTS_ID";
    public static final String KEY_DELIVERY_BOY_NAME = "BOY_NAME";
    public static final String KEY_ORDER_ID = "ORDER_ID";

    public static String BASE_URL = "http://nextinnovation.pro/store/";

    public static String BASE_URLGOGrocer = "http://nextinnovation.pro/";
    public static String IMG_PROFILE_URL = BASE_URL + "uploads/profile/";
    public static String LOGIN_URL = BASE_URL + "index.php/api/login";
    public static String DELIVERY_BOY = BASE_URLGOGrocer + "index.php/api/delivery_boy";
    public static String FORGOT_URL = BASE_URL + "index.php/api/forgot_password";
    public static String GET_STOCK = "https://gogrocer.app/arb/index.php/api/stock";
    public static String STOCK_UPDATE = "https://gogrocer.app/arb/index.php/api/stock_insert";
    public static String Update_user=BASE_URL+"index.php/api/update_userdata";
    public static String ASSIGN_ORDER = BASE_URLGOGrocer + "index.php/api/assign_order";
    public static String DAshborad_URL = BASE_URL + "index.php/api/dashboard";
    public static String OrderDetail = BASE_URLGOGrocer + "index.php/api/order_details";
    public static String IMG_PRODUCT_URL = BASE_URLGOGrocer + "uploads/products/";


    //Dashboard Items
    public static String ALL_PRODUCTS_URL = BASE_URL + "index.php/api/all_products";
    public static String APP_USER_URL = BASE_URL + "index.php/api/all_users";
    public static String STOCK_LIST = BASE_URL + "index.php/api/get_leftstock";


}
