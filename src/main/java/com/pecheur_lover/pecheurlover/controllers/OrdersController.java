package com.pecheur_lover.pecheurlover.controllers;

import com.pecheur_lover.pecheurlover.daos.OrdersDao;
import com.pecheur_lover.pecheurlover.entities.Orders;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Contrôleur REST pour la gestion des commandes (Orders)
@RestController
@RequestMapping("/orders")
public class OrdersController {
    // Injection du DAO pour les opérations sur les commandes
    private final OrdersDao ordersDao;

    public OrdersController(OrdersDao ordersDao) {
        this.ordersDao = ordersDao;
    }

    // Récupérer toutes les commandes
    @GetMapping("/all")
    public ResponseEntity<List<Orders>> getAllOrders(){
        return ResponseEntity.ok(ordersDao.findAll());
    }

    // Récupérer une commande par son identifiant de facture
    @GetMapping("/{id_invoice}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id_invoice){
        return ResponseEntity.ok(ordersDao.findById(id_invoice));
    }

    // Récupérer toutes les commandes associées à une facture spécifique
    @GetMapping("/by-invoice/{id_invoice}")
    public ResponseEntity<List<Orders>> getOrdersByInvoice(@PathVariable int id_invoice) {
        List<Orders> orders = ordersDao.findOrdersByInvoiceId(id_invoice);
        return ResponseEntity.ok(orders);
    }

    // Récupérer toutes les commandes associées à un email donné
    @GetMapping("/by-email/{email}")
    public ResponseEntity<List<Orders>> getOrdersByEmail(@PathVariable String email) {
        List<Orders> orders = ordersDao.findOrdersByEmail(email);
        return ResponseEntity.ok(orders);
    }

    // Créer une nouvelle commande (order)
    @PostMapping("/create")
    public ResponseEntity<Orders> createOrder(@Valid @RequestBody Orders orders){
        System.out.println("Réception de l'ordre : " + orders); // Log de la commande reçue
        Orders createdOrders = ordersDao.save(orders);
        return ResponseEntity.ok(createdOrders);
    }

    // Mettre à jour une commande existante par son identifiant de facture
    @PutMapping("/{id_invoice}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id_invoice, @RequestBody Orders orders) {
        Orders updatedOrders = ordersDao.update(id_invoice, orders);
        return ResponseEntity.ok(updatedOrders);
    }

    // Supprimer une commande par son identifiant de facture
    @DeleteMapping("/{id_invoice}")
    public ResponseEntity<Orders> deleteOrder(@PathVariable Long id_invoice) {
        if(ordersDao.delete(id_invoice)){
            return ResponseEntity.noContent().build(); // Succès : pas de contenu à retourner
        } else {
            return ResponseEntity.notFound().build(); // Erreur : commande non trouvée
        }
    }
}
