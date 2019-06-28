package com.example.athleticsessexce881final;

import ir.mirrajabi.searchdialog.core.Searchable;

public class SearchModel implements Searchable {
    private String mTitle;

    public SearchModel(String mTitle){
        mTitle = mTitle;
    }



    @Override
    public String getTitle() {
        return mTitle;
    }


    public SearchModel setTitle(String title) {
        mTitle = title;
        return this;
    }


}
