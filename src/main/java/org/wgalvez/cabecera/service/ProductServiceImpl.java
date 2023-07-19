package org.wgalvez.cabecera.service;

import org.wgalvez.cabecera.model.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    @Override
    public List<Product> showProducts() {
        return Arrays.asList(
                new Product(1, "Notebook", "Apple", new BigDecimal(175.90)),
                new Product(2, "Table", "Loyal", new BigDecimal(40.00)),
                new Product(3, "Mouse", "Megatron", new BigDecimal(60.50)));
    }

    @Override
    public Optional<Product> getProduct(String name) {
        return showProducts().stream().filter(p -> p.getName().equals(name)).findFirst();
    }
}
