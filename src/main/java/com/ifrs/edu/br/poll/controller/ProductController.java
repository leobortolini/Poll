package com.ifrs.edu.br.poll.controller;

import com.ifrs.edu.br.poll.model.Product;
import com.ifrs.edu.br.poll.queue.QueueSender;
import com.ifrs.edu.br.poll.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;
    private final QueueSender queueSender;


    @Autowired
    public ProductController(ProductService productService, QueueSender queueSender) {
        this.productService = productService;
        this.queueSender = queueSender;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        LOGGER.info("start() - findAll");
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> findById(@PathVariable Long id) {
        LOGGER.info("start() - findById");
        return ResponseEntity.status(HttpStatus.OK).body(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        LOGGER.info("start() - create");
        String text = "test message";

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("ultima", "sim");
        Message message = new Message(text.getBytes(), messageProperties);

        queueSender.send("test-exchange", "routing-key-teste", message);

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }

    @PutMapping
    public ResponseEntity<Product> update(@RequestBody Product product) {
        LOGGER.info("start() - update");
        return ResponseEntity.status(HttpStatus.OK).body(productService.update(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        LOGGER.info("start() - delete");
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
