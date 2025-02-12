package com.pecheur_lover.pecheurlover.controllers;

import com.pecheur_lover.pecheurlover.daos.OrdersDao;
import com.pecheur_lover.pecheurlover.entities.Orders;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersDao ordersDao;

    public OrdersController(OrdersDao ordersDao) {
        this.ordersDao = ordersDao;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Orders>> getAllOrders(){
        return ResponseEntity.ok(ordersDao.findAll());
    }

    @GetMapping("/{id_order}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id_order){
        return ResponseEntity.ok(ordersDao.findById(id_order));
    }

    @PostMapping("/create")
    public ResponseEntity<Orders> createOrder(@Valid @RequestBody Orders orders){
        Orders createdOrders = ordersDao.save(orders);
        return ResponseEntity.ok(createdOrders);
    }

    @PutMapping("/{id_order}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id_order, @RequestBody Orders orders) {
        Orders updatedOrders = ordersDao.update(id_order, orders);
        return ResponseEntity.ok(updatedOrders);
    }

    @DeleteMapping("/{id_order}")
    public ResponseEntity<Orders> deleteOrder(@PathVariable Long id_order) {
        if(ordersDao.delete(id_order)){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
