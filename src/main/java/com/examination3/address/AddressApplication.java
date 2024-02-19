package com.examination3.address;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 住所管理システムの起動クラスです。
 */
@SpringBootApplication
public class AddressApplication {

    /**
     * アプリケーションのエントリーポイントです。
     *
     * @param args コマンドライン引数。
     */
    public static void main(String[] args) {
        SpringApplication.run(AddressApplication.class, args);
    }
}
