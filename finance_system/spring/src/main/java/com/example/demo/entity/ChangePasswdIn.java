package com.example.demo.entity;

import lombok.Data;

@Data
public class ChangePasswdIn {

    private String userName;
    private String originPasswd;
    private String newPasswd;
}
