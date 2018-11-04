package top.zhacker.retail.spu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class SpuApplication{

  public static void main(String[] args) {
    SpringApplication.run(SpuApplication.class, args);
  }

}
