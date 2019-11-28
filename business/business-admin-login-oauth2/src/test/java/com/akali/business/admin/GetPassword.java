package com.akali.business.admin;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @ClassName GetPassword
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
public class GetPassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //$2a$10$fI9zYPu1QMSYhVGs3r5/OulaXyFYXexzDtHCeSckSjsTLGuncV/9W
        System.out.println(encoder.encode("123456"));

    }
}
