package com.example.service;

import org.springframework.stereotype.Service;

import com.example.model.PendudukModel;

@Service
public interface PendudukService
{
    PendudukModel selectPenduduk (String nik);

}