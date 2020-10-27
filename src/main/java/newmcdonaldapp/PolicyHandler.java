package newmcdonaldapp;

import newmcdonaldapp.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    OrderRepository orderRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryStart_OrderStatusChange(@Payload DeliveryStart deliveryStart){

        if(deliveryStart.isMe() && deliveryStart.getOrderId() != null){
            System.out.println("##### Delivery Start ###### : " + deliveryStart.toJson());

            orderRepository.findById(deliveryStart.getOrderId()).ifPresent((order)->{
                order.setState("InDelivery");
                orderRepository.save(order);
            });
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCookStarted_OrderStatusFixed(@Payload CookStarted cookStarted){

        if(cookStarted.isMe() && cookStarted.getOrderId() != null){
            System.out.println("##### Cook Start ###### : " + cookStarted.toJson());

            orderRepository.findById(cookStarted.getOrderId()).ifPresent((order)->{
                order.setState("Cooking");
                orderRepository.save(order);
            });
        }
    }

}
