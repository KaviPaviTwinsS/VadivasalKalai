package pasu.vadivasal.globalModle;

import android.content.ContentValues;
import android.database.Cursor;


import org.json.JSONException;
import org.json.JSONObject;

public class Country {
    private String countryCode;
    private String countryName;
    private String createdDate;
    private int isActive;
    private int mobileMaxLength;
    private String modifiedDate;
    private int pk_CountryId;

    public Country(JSONObject jsonObject) {
        try {
            this.pk_CountryId = jsonObject.getInt(Appconstants.Countries.COUNTRY_ID);
            this.countryName = jsonObject.getString(Appconstants.Countries.COUNTRY_NAME);
            this.mobileMaxLength = jsonObject.getInt(Appconstants.Countries.MOBILE_MAX_LENGTH);
            this.countryCode = jsonObject.getString(Appconstants.Countries.COUNTRY_CODE);
            this.isActive = jsonObject.getInt(Appconstants.Countries.IS_ACTIVE);
        } catch (JSONException e) {
            System.out.println("dhoolerror"+e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public Country(Cursor cursor) {
        setPk_CountryId(cursor.getInt(cursor.getColumnIndex(Appconstants.CountryMaster.C_PK_COUNTRYID)));
        setCountryCode(cursor.getString(cursor.getColumnIndex(Appconstants.CountryMaster.C_COUNTRYCODE)));
        setCountryName(cursor.getString(cursor.getColumnIndex(Appconstants.CountryMaster.C_COUNTRYNAME)));
        setMobileMaxLength(cursor.getInt(cursor.getColumnIndex(Appconstants.CountryMaster.C_MOBILEMAXLENGTH)));
        setIsActive(cursor.getInt(cursor.getColumnIndex(Appconstants.CountryMaster.C_ISACTIVE)));
        setCreatedDate(cursor.getString(cursor.getColumnIndex(Appconstants.CountryMaster.C_CREATEDDATE)));
        setModifiedDate(cursor.getString(cursor.getColumnIndex(Appconstants.CountryMaster.C_MODIFIEDDATE)));
    }

    public int getPk_CountryId() {
        return this.pk_CountryId;
    }

    public void setPk_CountryId(int pk_CountryId) {
        this.pk_CountryId = pk_CountryId;
    }

    public int getIsActive() {
        return this.isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getMobileMaxLength() {
        return this.mobileMaxLength;
    }

    public void setMobileMaxLength(int mobileMaxLength) {
        this.mobileMaxLength = mobileMaxLength;
    }

    public ContentValues getContentValue() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Appconstants.CountryMaster.C_PK_COUNTRYID, Integer.valueOf(getPk_CountryId()));
        contentValues.put(Appconstants.CountryMaster.C_COUNTRYNAME, getCountryName());
        contentValues.put(Appconstants.CountryMaster.C_MOBILEMAXLENGTH, Integer.valueOf(getMobileMaxLength()));
        contentValues.put(Appconstants.CountryMaster.C_COUNTRYCODE, getCountryCode());
        contentValues.put(Appconstants.CountryMaster.C_ISACTIVE, Integer.valueOf(getIsActive()));
        return contentValues;
    }
}
