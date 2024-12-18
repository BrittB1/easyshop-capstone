package org.yearup.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;

import java.math.BigDecimal;
import java.util.List;

// http://localhost:8080/categories
// add annotation to allow cross site origin requests
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
    public @ResponseBody List<Category> getAll() {

//
//        }
        //  ]
        return categoryDao.getAllCategories();
    }

    // add the appropriate annotation for a get action
    // http://localhost:8080/categories/1
    @GetMapping("/{id}")
    public Category getById(@PathVariable int id) {
        // get the category by id
        //    {
//        categoryId: 1,
//        name: "Name",
//        description: "Description"
//
//    }
        return categoryDao.getById(id);
    }

    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
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

    // TODO add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    // TODO add annotation to ensure that only an ADMIN can call this function
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void updateCategory(@PathVariable int id, @RequestBody Category category) {
        if (category.getCategoryId() != id) {
            throw new IllegalArgumentException("Category ID in path must match the Id in the body");
        }
        // TODO update the category by id
    }

    // TODO add annotation to call this method for a DELETE action - the url path must include the categoryId
    // TODO add annotation to ensure that only an ADMIN can call this function
    public void deleteCategory(@PathVariable int id) {
        // TODO delete the category by id
    }
}

