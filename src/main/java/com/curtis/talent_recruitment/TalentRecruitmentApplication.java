package com.curtis.talent_recruitment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@MapperScan("com.curtis.talent_recruitment.*.dao")
@SpringBootApplication
public class TalentRecruitmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(TalentRecruitmentApplication.class, args);
    }

}
