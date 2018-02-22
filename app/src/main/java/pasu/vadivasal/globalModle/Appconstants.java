package pasu.vadivasal.globalModle;

import android.provider.BaseColumns;

/**
 * Created by Admin on 19-11-2017.
 */

public  class Appconstants {
    public static final String LOGIN_TYPE = "login_type";
    public static final String TourID="tournament_id";
    public static final String WEB_DIALOG_PARAM_MEDIA = "media";
    public static final int TYPE_BULL_OWNER =1 ;
    public static final int TYPE_PLAYER =2;
    public static final int TYPE_PHOTOGRAPHER =3;
    public static final String USER_PROFILE_IMAGE = "profileImageUrl";
    public static final String USER_PROFILE_NAME = "name";
    public static final String USER_PROFILE_GOOGLE_ID = "googleUserID";
    public static final String USER_PROFILE_EMAIL_ID = "email_id";
    public static final String USER_PROFILE_PHONE_NUMBER = "phone_number";
    public static final String FORCE_UPDATE = "ForceUpdate";
    public static final int OPEN_DASHBOARD = 0;
    public static final int OPEN_TRENDING = 1;
    public static final int OPEN_TOURNAMENT = 2;
    public static final int OPEN_NEWS = 3;
    public static final int OPEN_SINGLE_NEWS = 4;
    public static final int OPEN_SINGLE_VIDEO = 5;
    public static final int OPEN_SINGLE_IMAGE = 6;
    public static final int OPEN_SINGLE_TOURNAMENT = 7;
    public static final String ABOUT_APP = "about_app";
    public static final String SHARE_CONTENT = "share_content";

    public static class Countries {
        public static String COUNTRY_CODE = "country_code";
        public static String COUNTRY_ID = "country_id";
        public static String COUNTRY_NAME = "country_name";
        public static String IS_ACTIVE = "is_active";
        public static String MOBILE_MAX_LENGTH = "mobile_max_length";
        public static String NAME = "countries";
    }
    public static class CountryMaster implements BaseColumns {
        public static String C_COUNTRYCODE = "countryCode";
        public static String C_COUNTRYNAME = "countryName";
        public static String C_CREATEDDATE = "createdDate";
        public static String C_ISACTIVE = "isActive";
        public static String C_MOBILEMAXLENGTH = "mobileMaxLength";
        public static String C_MODIFIEDDATE = "modifiedDate";
        public static String C_PK_COUNTRYID = "pk_countryID";
        public static String TABLE = "tbl_CountryMaster";
    }

}
