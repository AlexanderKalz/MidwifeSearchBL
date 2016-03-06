package de.drkalz.midwifesearchbl;

import android.app.Application;

import com.backendless.BackendlessUser;

import de.drkalz.midwifesearchbl.dataObjects.UserAddress;

/**
 * Created by Alex on 03.02.16.
 */
public class StartApp extends Application {

    private static StartApp singleInstance = null;
    private boolean isMidwife;
    private BackendlessUser mCurrentUser;
    private UserAddress mUserAddress;
    private String mUserEmail;
    private String mUserPassword;
    private String fullUsername;

    public static StartApp getInstance() {
        return singleInstance;
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
