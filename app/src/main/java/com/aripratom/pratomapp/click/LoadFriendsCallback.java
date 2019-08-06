package com.aripratom.pratomapp.click;

import com.aripratom.pratomapp.entity.Friends;

import java.util.ArrayList;

/** 10116323 - Aria Pratomo
 * IF - 8
 * 04 - Agustus 2019 */
public interface LoadFriendsCallback {
    void preExecute();
    void postExecute(ArrayList<Friends> friends);
}
