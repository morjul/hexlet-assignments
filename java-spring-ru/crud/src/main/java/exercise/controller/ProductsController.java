package exercise.controller;

import java.util.List;
import java.util.Optional;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.mapper.ProductMapper;
import exercise.model.Category;
import exercise.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    // BEGIN

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(path = "")
    public List<ProductDTO> index() {
        var products = productRepository.findAll();
        return products.stream()
                .map(productMapper::map)
                .toList();
    }

    @GetMapping(path = "/{id}")
    public ProductDTO show(@PathVariable long id) {

        var product =  productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " not found"));
        var productDTO = productMapper.map(product);
        return productDTO;
    }

    @PostMapping(path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductCreateDTO productData) {
        var product = productMapper.map(productData);
        Optional<Category> optionalCategory = categoryRepository.findById(productData.getCategoryId());
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.getProducts().add(product);
            product.setCategory(category);
            categoryRepository.save(category);
        } else {
            return ResponseEntity.status(400).body(productMapper.map(product));
        }
        var productDTO = productMapper.map(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }

    // BEGIN
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductDTO> update(@Valid @RequestBody ProductUpdateDTO productData, @PathVariable Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        if (productData.getCategoryId().isPresent()) {
            Optional<Category> optionalCategory = categoryRepository.findById(productData.getCategoryId().get());
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                category.getProducts().add(product);
                product.setCategory(category);
                categoryRepository.save(category);
            } else {
                return ResponseEntity.status(400).body(productMapper.map(product));
            }
        }
        //productMapper.update(productData, product);
        if (productData.getPrice() != null && productData.getPrice().isPresent()) {
            product.setPrice(productData.getPrice().get());
        }
        if (productData.getTitle() != null && productData.getTitle().isPresent()) {
            product.setTitle(productData.getTitle().get());
        }
        productRepository.save(product);
        var productDTO = productMapper.map(product);
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @DeleteMapping("/{id}") // Удаление страницы
    public void destroy(@PathVariable long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not Found"));
        Optional<Category> optionalProduct = categoryRepository.findById(product.getCategory().getId());
        if (optionalProduct.isPresent()) {
            Category category = optionalProduct.get();
            category.getProducts().remove(product);
            product.setCategory(null);
            categoryRepository.save(category);
        }
        productRepository.deleteById(id);
    }
    
    // END
}
