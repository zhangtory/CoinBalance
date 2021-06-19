package com.zhangtory.coinbalance;

import com.zhangtory.mybatisplus.component.CodeGenerator;
import com.zhangtory.mybatisplus.component.DatabaseProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by Scott on 2021/6/4
 **/
@SpringBootTest
public class Generator {

    @Autowired
    private DatabaseProperties databaseProperties;

    @Test
    void generatorCreate() throws InterruptedException {
        CodeGenerator codeGenerator = new CodeGenerator(databaseProperties, false,
                "ZhangTory", "com.zhangtory.coinbalance");
        codeGenerator.create("lock_balance");
    }

}
