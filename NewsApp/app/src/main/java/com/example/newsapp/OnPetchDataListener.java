package com.example.newsapp;

import com.example.newsapp.Models.NewsHeadLine;

import java.util.List;

public interface OnPetchDataListener {
    void OnPetchData(List<NewsHeadLine> list , String mess);
    void Error(String mess);
}
