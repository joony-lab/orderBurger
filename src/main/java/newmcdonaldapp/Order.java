package newmcdonaldapp;

import javax.persistence.*;

import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long burgerId;
    private String burgerName;
    private int orderedQty;
    private int totalPrice;
    private Long customerId;
    private String customerName;
    private String state = "Created";
    private String branchOffice;


    @PrePersist
    public void onPrePersist(){
//        OrderCanceled orderCanceled = new OrderCanceled();
//        BeanUtils.copyProperties(this, orderCanceled);
//        orderCanceled.publishAfterCommit();
//
//        //Following code causes dependency to external APIs
//        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.
//
//        newmcdonaldapp.external.PayHistory payHistory = new newmcdonaldapp.external.PayHistory();
//        // mappings goes here
//        OrderBurgerApplication.applicationContext.getBean(newmcdonaldapp.external.PayHistoryService.class)
//            .payCancel(payHistory);
//
//
//        Ordered ordered = new Ordered();
//        BeanUtils.copyProperties(this, ordered);
//        ordered.publishAfterCommit();
//
//        //Following code causes dependency to external APIs
//        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.
//
//        //newmcdonaldapp.external.PayHistory payHistory = new newmcdonaldapp.external.PayHistory();
//        // mappings goes here
//        OrderBurgerApplication.applicationContext.getBean(newmcdonaldapp.external.PayHistoryService.class)
//            .payRequest(payHistory);

        if(burgerName == null){
            throw new RuntimeException();
        }

        MenuRepository menuRepo = OrderBurgerApplication.applicationContext.getBean(MenuRepository.class);
        Optional<Menu> menus = menuRepo.findByBurgerName(burgerName);
        Menu selectdMenu = menus.get();

        int price = selectdMenu.getPrice();
        int qty = getOrderedQty();

        if( selectdMenu.getStock() < qty ){
            throw new OrderException("No Available stock");
        }

        //this.setTotalQty(qty*price);
        this.setBurgerName(selectdMenu.getName());
        this.setTotalPrice(qty * price);
    }

    @PostPersist
    public void onPostPersist(){
        newmcdonaldapp.external.PayHistory payRequest = new newmcdonaldapp.external.PayHistory();

        payRequest.setOrderId(getId());
        if( getTotalPrice() != 0 ){
            payRequest.setPrice(getTotalPrice());
        }

        OrderBurgerApplication.applicationContext.getBean(newmcdonaldapp.external.PayHistoryService.class)
                .payRequest(payRequest);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBurgerId() {
        return burgerId;
    }

    public void setBurgerId(Long burgerId) {
        this.burgerId = burgerId;
    }

    public String getBurgerName() {
        return burgerName;
    }

    public void setBurgerName(String burgerName) {
        this.burgerName = burgerName;
    }

    public int getOrderedQty() {
        return orderedQty;
    }

    public void setOrderedQty(int orderedQty) {
        this.orderedQty = orderedQty;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(String branchOffice) {
        this.branchOffice = branchOffice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
