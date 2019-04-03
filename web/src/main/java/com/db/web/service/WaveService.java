package com.db.web.service;

import com.db.web.entity.Wave;
import org.springframework.stereotype.Service;

import java.util.List;

public interface WaveService {

    List<Wave> getAllWave(String hrbustID);

    void insertWave(String hrbustID, Wave wave);
}