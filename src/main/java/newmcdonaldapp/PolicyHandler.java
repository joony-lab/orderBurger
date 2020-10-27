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
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryStart_OrderStatusChange(@Payload DeliveryStart deliveryStart){

        if(deliveryStart.isMe()){
            System.out.println("##### listener OrderStatusChange : " + deliveryStart.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCookStarted_OrderStatusFixed(@Payload CookStarted cookStarted){

        if(cookStarted.isMe()){
            System.out.println("##### listener OrderStatusFixed : " + cookStarted.toJson());
        }
    }

}
