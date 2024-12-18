package org.yearup.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.CategoryDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Category;
import org.yearup.models.Product;
import java.math.BigDecimal;
import java.util.List;
    // add the annotations to make this a REST controller
// add the annotation to make this controller the endpoint for the following url
    // http://localhost:8080/categories
// add annotation to allow cross site origin requests
    @RestController
    @RequestMapping("/categories")
    @CrossOrigin
    public class CategoriesController
    {
        // create an Autowired controller to inject the categoryDao and ProductDao
        @Autowired
        private CategoryDao categoryDao;
        @Autowired
        private ProductDao productDao;
        // add the appropriate annotation for a get action
        @GetMapping
        public @ResponseBody List<Category> getAll()
        {
            // find and return all categories
//        [
//        {
//            categoryId: 1,
////        name: "Name",
////        description: "Descrfiption"
//
//        },
//        {
//            categoryId: 2,
////        name: "Name",
////        description: "Descrfiption"
//
//        }
            //  ]
            return categoryDao.getAllCategories();
        }
        // add the appropriate annotation for a get action
        // http://localhost:8080/categories/1
        @GetMapping("/{id}")
        public Category getById(@PathVariable int id)
        {
            // get the category by id
            //    {
//        categoryId: 1,
//        name: "Name",
//        description: "Descrfiption"
//
//    }
            return categoryDao.getById(id);
        }
        // the url to return all products in category 1 would look like this
        // https://localhost:8080/categories/1/products
        @GetMapping("/{categoryId}/products")
        public List<Product> getProductsById(@PathVariable int categoryId)
        {
            // get a list of product by categoryId
            //        [
//        {
//            categoryId: 1,
////        name: "Name",
////        description: "Beverages"
//
//        },
//        {
//            categoryId: 2,
////        name: "Name",
////        description: "Food"
//
//        }
//        //  ]
//        [
//        {
//            productId: 12,
//            name: "Coke",
//            price: 2,
//            categoryId: 1,
//            description:"Bubbly"
//            color:"Black
//            int stock: 100
//            isFeatured: true,
//            imageUrl :"www.image.jpeg"
//        },
//        {
//            productId: 17,
//                    name: "Water",
//                price: 2,
//                categoryId: 1,
//                description:"Spring",
//            color:"Clear",
//            int stock: 100,
//            isFeatured: true,
//                    imageUrl :"www.image.jpeg"
//        }
//        ]
//
            return productDao.listByCategoryId(categoryId) ;
        }
        // add annotation to call this method for a POST action
        // add annotation to ensure that only an ADMIN can call this function
        public Category addCategory(@RequestBody Category category)
        {
            // insert the category
            return null;
        }
        // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
        // add annotation to ensure that only an ADMIN can call this function
        public void updateCategory(@PathVariable int id, @RequestBody Category category)
        {
            // update the category by id
        }
        // add annotation to call this method for a DELETE action - the url path must include the categoryId
        // add annotation to ensure that only an ADMIN can call this function
        public void deleteCategory(@PathVariable int id)
        {
            // delete the category by id
        }
    }
}
