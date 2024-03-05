# Pharmacy Inventory System
# APIs Endpoints

/***User Authentication APIs****/

# User Fields
1. firstname
2. surname
3. email
4. password
5. phoneNumber
6. pharmacyName
7. pharmacyType
8. location

   
# User Registration
localhost:8080/api/users/register

# User Login
localhost:8080/api/users/login

/***End of User Authentication APIs****/

/*** Stock Management APIs ***/

# Product Fields
1. name
2. costprice
3. sellingPrice
4. quantityInStock
5. expiryDate
6. categoryId

# Save Single Product
localhost:8080/users/api/products/single

# Save Multiple products
localhost:8080/users/api/products/batch

# Request for Product
localhost:8080/users/api/products/{id}

# Request for Products per their Category 
localhost:8080/users/api/products/category/{categoryId}

# Update a Products Details
localhost:8080/users/api/products/updateProduct/{id}

/*** End of Stock Management APIs ***/


/*** Point Of Sale APIs ***/



/*** Point Of Sale APIs ***/
