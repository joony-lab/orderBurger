package newmcdonaldapp;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @PrePersist
    public void onPrePersist(){
        OrderCanceled orderCanceled = new OrderCanceled();
        BeanUtils.copyProperties(this, orderCanceled);
        orderCanceled.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        newmcdonaldapp.external.PayHistory payHistory = new newmcdonaldapp.external.PayHistory();
        // mappings goes here
        OrderBurgerApplication.applicationContext.getBean(newmcdonaldapp.external.PayHistoryService.class)
            .payCancel(payHistory);


        Ordered ordered = new Ordered();
        BeanUtils.copyProperties(this, ordered);
        ordered.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        //newmcdonaldapp.external.PayHistory payHistory = new newmcdonaldapp.external.PayHistory();
        // mappings goes here
        OrderBurgerApplication.applicationContext.getBean(newmcdonaldapp.external.PayHistoryService.class)
            .payRequest(payHistory);


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




}
