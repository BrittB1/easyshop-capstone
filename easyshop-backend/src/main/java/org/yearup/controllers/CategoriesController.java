package org.yearup.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin
public class CategoriesController {
    // create an Autowired controller to inject the categoryDao and ProductDao
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ProductDao productDao;

    // add the appropriate annotation for a get action
    @GetMapping

    // @ResponseBody tells Spring to convert the returned list into JSON
    public @ResponseBody List<Category> getAll() {
        try {
            List<Category> categories = categoryDao.getAllCategories();

            if (categories == null) {
                return new ArrayList<>();
            }
            return categories;

        } catch (Exception e) {
            System.out.println("Error getting categories: " + e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable int id) {

        return categoryDao.getById(id);
    }

    @GetMapping("/{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId) {

        return productDao.listByCategoryId(categoryId);
    }

    @PostMapping //Tells Spring what method to use when a user sends a POST request
    @PreAuthorize("hasRole('ADMIN')") // Checks to see if user has the ADMIN role before this method can be run

    //Category: is the return type. This method will give back a Category object when it's done
    //@RequestBody Category category: Tells Spring to convert the JSON data that was sent in the post request and make it a Category object.
    public Category addCategory(@RequestBody Category category) {
        // Similar to a safety check; ensures a category has been received at all before something is done with it
        if (category == null)
            throw new IllegalArgumentException("Category object can't be null");
        // insert the category
        return categoryDao.create(category);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    //void: This method doesn't return anything
    //@PathVariable int id : Tells Spring to take the ID from the URL path
    //@RequestBody Category category: Tells Spring to convert the JSON sent in the request into a Category object
    public void updateCategory(@PathVariable int id, @RequestBody Category category) {
        // Consistency check that catches mismatched ID when getting category
        if (category.getCategoryId() != id) {
            throw new IllegalArgumentException("Category ID in path must match the Id in the body");
        }
        // Asks database if there's a category with a particular ID and stores the result in existingCategory
        Category existingCategory = categoryDao.getById(id);

        // Checks to see if category was found. If its null an error is thrown
        if (existingCategory == null) {
            throw new IllegalArgumentException("Category not found with ID: " + id);
        }
        categoryDao.update(id, category);
    }

    // Tells Spring that this method handles DELETE requests. {id} is like an address that indicates what will be deleted
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(@PathVariable int id) {
        Category existingCategory = categoryDao.getById(id);
        if (existingCategory == null) {
            throw new IllegalArgumentException("Category with ID " + id + " not found.");
        }

        List<Product> products = productDao.listByCategoryId(id);
        if (!products.isEmpty()) {
            throw new IllegalArgumentException("Can't delete category that has no products");
        }
        categoryDao.delete(id);
    }
}