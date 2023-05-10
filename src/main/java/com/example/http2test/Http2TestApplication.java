package com.example.http2test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Http2TestApplication {

    public static void main(String[] args) {
//      pks#1 -> pks#12로 변환하기 위한 provider 설정
//      해당 설정 없어도 동작함, 버전 업 되면서 지원되는 것 같음
//        Security.addProvider(new PemKeyStoreProvider());
        SpringApplication.run(Http2TestApplication.class, args);
    }
}
