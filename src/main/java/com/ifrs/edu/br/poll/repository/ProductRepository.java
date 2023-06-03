package com.ifrs.edu.br.poll.repository;

import com.ifrs.edu.br.poll.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
