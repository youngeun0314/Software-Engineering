package Irumping.IrumOrder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IrumOrderApplication {
	public static void main(String[] args) {
		SpringApplication.run(IrumOrderApplication.class, args);
	}
}
