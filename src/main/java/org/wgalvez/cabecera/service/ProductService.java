package org.wgalvez.cabecera.service;

import org.wgalvez.cabecera.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> showProducts();
    Optional<Product> getProduct(String name);
}
