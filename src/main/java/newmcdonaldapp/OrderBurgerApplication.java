package newmcdonaldapp;
import newmcdonaldapp.config.kafka.KafkaProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableBinding(KafkaProcessor.class)
@EnableFeignClients
public class OrderBurgerApplication {
    protected static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(OrderBurgerApplication.class, args);

        MenuRepository menuRepository = applicationContext.getBean(MenuRepository.class);

        String[] menus = {"MacMorning","ShrimpBurger", "BigMac"};


        int k = 1;
        for(String h : menus){
            Menu menu = new Menu();

            menu.setBurgerName(h);
            menu.setPrice(k*2000);
            menu.setStock(1000);
            k++;

            menuRepository.save(menu);

        }
    }
}
