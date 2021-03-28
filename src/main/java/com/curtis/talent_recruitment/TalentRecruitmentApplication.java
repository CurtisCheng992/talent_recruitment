package com.curtis.talent_recruitment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@ServletComponentScan
@MapperScan("com.curtis.talent_recruitment.*.dao")
@SpringBootApplication
public class TalentRecruitmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(TalentRecruitmentApplication.class, args);
    }

    @Bean
    public CorsFilter corsFilter(){
        // 1.添加 CORS 配置信息
        CorsConfiguration config = new CorsConfiguration();
        // 1.1 允许的域,不要写 * ，否则 cookie 就无法使用了
        config.addAllowedOrigin( "http://www.tr.com" );
        config.addAllowedOrigin( "http://manage.tr.com" );
        // 1.2 是否发送Cookie信息
        config.setAllowCredentials( true );
        // 1.3 允许的请求方式
        config.addAllowedMethod( "OPTIONS" );
        config.addAllowedMethod( "HEAD" );
        config.addAllowedMethod( "GET" );
        config.addAllowedMethod( "PUT" );
        config.addAllowedMethod( "POST" );
        config.addAllowedMethod( "DELETE" );
        config.addAllowedMethod( "PATCH" );
        // 1.4 允许的头信息
        config.addAllowedHeader( "*" );

        // 2.添加映射路径，这里拦截一切请求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration( "/**", config );

        // 3.返回新的CorsFilter.
        return new org.springframework.web.filter.CorsFilter( configSource );
    }

    /**
     * 配置 redisTemplate
     */
    @Bean(name = "template")
    public RedisTemplate<String, Object> template(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory( factory );
        // key 采用 String 的序列化方式
        template.setKeySerializer( new StringRedisSerializer() );
        // hash 的 key 也采用 String 的序列化方式
        template.setHashKeySerializer( new StringRedisSerializer() );
        // value 序列化方式采用 jackson
        template.setValueSerializer( new GenericJackson2JsonRedisSerializer() );
        // hash 的 value 序列化方式采用 jackson
        template.setHashValueSerializer( new GenericJackson2JsonRedisSerializer() );
        template.afterPropertiesSet();
        return template;
    }

}
