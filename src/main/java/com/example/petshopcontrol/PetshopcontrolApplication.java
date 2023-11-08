package com.example.petshopcontrol;

        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PetshopcontrolApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetshopcontrolApplication.class, args);
        System.out.println(new BCryptPasswordEncoder().encode("senha123"));
    }

}
