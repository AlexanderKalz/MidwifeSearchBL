package de.drkalz.midwifesearchbl;

import android.app.Application;

import com.backendless.BackendlessUser;
import com.backendless.geo.GeoPoint;

import java.util.ArrayList;

import de.drkalz.midwifesearchbl.dataObjects.BlockedTime;
import de.drkalz.midwifesearchbl.dataObjects.UserAddress;

/**
 * Created by Alex on 03.02.16.
 */
public class StartApp extends Application {

    private static StartApp singleInstance = null;
    ArrayList<BlockedTime> blockedTimes = new ArrayList<>();
    private boolean isMidwife;
    private BackendlessUser mCurrentUser;
    private UserAddress mUserAddress;
    private String userID;
    private String mUserEmail;
    private String mUserPassword;
    private String fullUsername;
    private GeoPoint homeGeoPoint;

    public static StartApp getInstance() {
        return singleInstance;
    }

    public ArrayList<BlockedTime> getBlockedTimes() {
        return blockedTimes;
    }

    public void setBlockedTime(BlockedTime blockedTime) {
        this.blockedTimes.add(blockedTime);
    }

    public BlockedTime getBlockedTime(int position) {
        return blockedTimes.get(position);
    }

    public void clearBlockedTimes() {
        this.blockedTimes.clear();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public GeoPoint getHomeGeoPoint() {
        return homeGeoPoint;
    }

    public void setHomeGeoPoint(GeoPoint homeGeoPoint) {
        this.homeGeoPoint = homeGeoPoint;
    }

    public boolean isMidwife() {
        return isMidwife;
    }

    public void setMidwife(boolean midwife) {
        isMidwife = midwife;
    }

    public BackendlessUser getCurrentUser() {
        return mCurrentUser;
    }

    public void setCurrentUser(BackendlessUser currentUser) {
        mCurrentUser = currentUser;
    }

    public UserAddress getUserAddress() {
        return mUserAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        mUserAddress = userAddress;
    }

    public String getUserEmail() {
        return mUserEmail;
    }

    public void setUserEmail(String userEmail) {
        mUserEmail = userEmail;
    }

    public String getUserPassword() {
        return mUserPassword;
    }

    public void setUserPassword(String userPassword) {
        mUserPassword = userPassword;
    }

    public String getFullUsername() {
        this.setFullUsername(fullUsername);
        return fullUsername;
    }

    public void setFullUsername(String fullUsername) {
        if (mUserAddress != null) {
            fullUsername = mUserAddress.getFirstname() + " " + mUserAddress.getLastname();
        } else {
            fullUsername = "not set";
        }
        this.fullUsername = fullUsername;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleInstance = this;
    }
}
