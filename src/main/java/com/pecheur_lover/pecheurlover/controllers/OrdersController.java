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

    @GetMapping("/{id_invoice}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id_invoice){
        return ResponseEntity.ok(ordersDao.findById(id_invoice));
    }

    @GetMapping("/by-invoice/{id_invoice}")
    public ResponseEntity<List<Orders>> getOrdersByInvoice(@PathVariable int id_invoice) {
        List<Orders> orders = ordersDao.findOrdersByInvoiceId(id_invoice);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<List<Orders>> getOrdersByEmail(@PathVariable String email) {
        List<Orders> orders = ordersDao.findOrdersByEmail(email);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/create")
    public ResponseEntity<Orders> createOrder(@Valid @RequestBody Orders orders){
        System.out.println("Réception de l'ordre : " + orders);
        Orders createdOrders = ordersDao.save(orders);
        return ResponseEntity.ok(createdOrders);
    }

    @PutMapping("/{id_invoice}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id_invoice, @RequestBody Orders orders) {
        Orders updatedOrders = ordersDao.update(id_invoice, orders);
        return ResponseEntity.ok(updatedOrders);
    }

    @DeleteMapping("/{id_invoice}")
    public ResponseEntity<Orders> deleteOrder(@PathVariable Long id_invoice) {
        if(ordersDao.delete(id_invoice)){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
