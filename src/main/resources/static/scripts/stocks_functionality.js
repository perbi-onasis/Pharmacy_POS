
// Start Populate Product Table Script ------>
function fetchAndDisplayProducts() {
        $.get("/products", function (products) {
            // Clear existing table rows
            $("#productTableBody").empty();

            // Initialize increment counter
            let idCounter = 0;

            // Iterate through each product and append a row to the table
            $.each(products, function (index, product) {
                idCounter++; //Increment the counter for each product
                let row = "<tr data-product-id='" + product.id + "'>" +
                    "<td>" + idCounter + "</td>" +
                    "<td>" + product.productName + "</td>" +
                    "<td>" + product.costPrice + "</td>" +
                    "<td>" + product.sellingPrice + "</td>" +
                    "<td>" + product.quantityInStock + "</td>" +
                    "<td>" + product.expiryDate + "</td>" +
                    "<td>" + product.categoryId + "</td>" +
                    "<td><ul class='orderDatatable_actions mb-0 d-flex flex-wrap'>" +
                    "<li><a href='#' class='update' data-product-id='" + product.id + "'><i class='uil uil-edit'></i></a></li>" +
                    "<li><a href='#' class='remove'><i class='uil uil-trash-alt'></i></a></li>" +
                    "</ul></td>" +
                    "</tr>";
                $("#productTableBody").append(row);
            });
        });
    }
// End Populate Product Table Script

// Start Fetch Products into table when page load
 $(document).ready(function() {
        fetchAndDisplayProducts();
    });
// End Fetch Products into table when page load

// Function to filter the table based on the product name or category ID
    function filterTable() {
        // Get the input value
        var filterValue = document.getElementById("filterInput").value.toUpperCase();

        // Get the table rows
        var rows = document.getElementById("productTableBody").getElementsByTagName("tr");

        // Loop through all table rows, and hide those that do not match the filter
        for (var i = 0; i < rows.length; i++) {
            var productName = rows[i].getElementsByTagName("td")[1]; // Get the second column (product name)
            var categoryId = rows[i].getElementsByTagName("td")[6]; // Get the seventh column (category ID)

            if (productName || categoryId) {
                var productNameText = productName.textContent || productName.innerText;
                var categoryIdText = categoryId.textContent || categoryId.innerText;

                if (productNameText.toUpperCase().indexOf(filterValue) > -1 || categoryIdText.toUpperCase().indexOf(filterValue) > -1) {
                    rows[i].style.display = "";
                } else {
                    rows[i].style.display = "none";
                }
            }
        }
    }
// End Filter Search



// Add Single Product Script
/*

$("#addSingleProductForm").submit(function(e) {
    e.preventDefault();

    // Serialize form data
    let formData = $(this).serialize();

    // Submit AJAX request to check if product name already exists
    $.get("/checkProductName", { productName: $("#productName").val() })
        .done(function(response) {
            if (response.exists) {
                // Product with the same name already exists, prompt user
                if (confirm("A product with the same name already exists. Click OK to update quantity and expiry date, or Cancel to return to the form.")) {
                    // User clicked OK, update quantity and expiry date
                    formData += "&updateExisting=true";
                } else {
                    // User clicked Cancel, return without adding the product
                    return;
                }
            }

            // Submit AJAX request to add product
            $.post("/saveProduct", formData)
                .done(function(response) {
                    console.log("Product added successfully:", response);
                    // Clear form fields
                    $('#addSingleProductForm')[0].reset();
                    // Refresh the product table after adding the product
                    fetchAndDisplayProducts();
                    // Close the modal
                    $('#addSingleProduct').modal('hide');
                })
                .fail(function(jqXHR, textStatus, errorThrown) {
                    console.error("Error adding product:", errorThrown);
                });
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            console.error("Error checking product name:", errorThrown);
        });
});

$("#addSingleProductForm").submit(function(e) {
    e.preventDefault();

    // Serialize form data
    let formData = $(this).serialize();

    // Submit AJAX request to check if product name already exists
    $.get("/checkProductName", { productName: $("#productName").val() })
        .done(function(response) {
            if (response.exists) {
                // Product with the same name already exists, prompt user
                if (confirm("A product with the same name already exists. Click OK to update quantity and expiry date, or Cancel to return to the form.")) {
                    // Get the product ID
                    $.get("/getProductId", { productName: $("#productName").val() })
                        .done(function(productId) {
                            // Append the product ID to the URL for updating product
                            let updateUrl = "/updateProduct/" + productId;

                            // Submit AJAX request to update the existing product
                            $.ajax({
                                url: updateUrl, // Replace with the actual product ID
                                type: "PUT", // Change the HTTP method to PUT
                                data: formData,
                                success: function(response) {
                                    console.log("Product updated successfully:", response);
                                    // Clear form fields
                                    $('#addSingleProductForm')[0].reset();
                                    // Refresh the product table after updating the product
                                    fetchAndDisplayProducts();
                                    // Close the modal
                                    $('#addSingleProduct').modal('hide');
                                },
                                error: function(jqXHR, textStatus, errorThrown) {
                                    console.error("Error updating product:", errorThrown);
                                }
                            });

                        })
                        .fail(function(jqXHR, textStatus, errorThrown) {
                            console.error("Error getting product ID:", errorThrown);
                        });
                } else {
                    // User clicked Cancel, return without adding the product
                    return;
                }
            } else {
                // Submit AJAX request to add product
                $.post("/saveProduct", formData)
                    .done(function(response) {
                        console.log("Product added successfully:", response);
                        // Clear form fields
                        $('#addSingleProductForm')[0].reset();
                        // Refresh the product table after adding the product
                        fetchAndDisplayProducts();
                        // Close the modal
                        $('#addSingleProduct').modal('hide');
                    })
                    .fail(function(jqXHR, textStatus, errorThrown) {
                        console.error("Error adding product:", errorThrown);
                    });
            }
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            console.error("Error checking product name:", errorThrown);
        });
});
*/

$("#addSingleProductForm").submit(function(e) {
    e.preventDefault();

    // Serialize form data
    let formData = $(this).serialize();

    // Submit AJAX request to check if product name already exists
    $.get("/checkProductName", { productName: $("#productName").val() })
        .done(function(response) {
            if (response.exists) {
                // Product with the same name already exists, prompt user
                if (confirm("A product with the same name already exists. Click OK to update quantity and expiry date, or Cancel to return to the form.")) {
                    // Get the product ID
                    $.get("/getProductId", { productName: $("#productName").val() })
                        .done(function(productId) {
                            // Append the product ID to the URL for updating product
                            let updateUrl = "/updateProduct/" + productId;

                            // Convert formData object to JSON format
                            let jsonData = {};
                            formData.split('&').forEach(function(keyValue) {
                                let [key, value] = keyValue.split('=');
                                jsonData[key] = decodeURIComponent(value.replace(/\+/g, ' '));
                            });

                            // Submit AJAX request to update the existing product
                            $.ajax({
                                url: updateUrl, // Replace with the actual product ID
                                type: "PUT", // Change the HTTP method to PUT
                                data: JSON.stringify(jsonData),
                                contentType: 'application/json',
                                success: function(response) {
                                    console.log("Product updated successfully:", response);
                                    // Clear form fields
                                    $('#addSingleProductForm')[0].reset();
                                    // Refresh the product table after updating the product
                                    fetchAndDisplayProducts();
                                    // Close the modal
                                    $('#addSingleProduct').modal('hide');
                                },
                                error: function(jqXHR, textStatus, errorThrown) {
                                    console.error("Error updating product:", errorThrown);
                                }
                            });

                        })
                        .fail(function(jqXHR, textStatus, errorThrown) {
                            console.error("Error getting product ID:", errorThrown);
                        });
                } else {
                    // User clicked Cancel, return without adding the product
                    return;
                }
            } else {
                // Submit AJAX request to add product
                $.post("/saveProduct", formData)
                    .done(function(response) {
                        console.log("Product added successfully:", response);
                        // Clear form fields
                        $('#addSingleProductForm')[0].reset();
                        // Refresh the product table after adding the product
                        fetchAndDisplayProducts();
                        // Close the modal
                        $('#addSingleProduct').modal('hide');
                    })
                    .fail(function(jqXHR, textStatus, errorThrown) {
                        console.error("Error adding product:", errorThrown);
                    });
            }
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            console.error("Error checking product name:", errorThrown);
        });
});


// End Add Single Product Script

// Start Update Product Script
$(document).on('click', '.update', function(e) {
    e.preventDefault();

    // Get the product ID from the table row
    let productId = $(this).closest('tr').data('product-id');

    // Fetch the product data for the given ID
    $.get("/products/" + productId, function(product) {

        // Populate the modal form fields with the fetched product data
        $("#updateProductModal #updateNameOfProduct").val(product.productName);
        $("#updateProductModal #updateProductId").val(product.id);
        $("#updateProductModal #updateCostOfProduct").val(product.costPrice);
        $("#updateProductModal #updateSellingPriceOfProduct").val(product.sellingPrice);
        $("#updateProductModal #updateQtyInStock").val(product.quantityInStock);
        $("#updateProductModal #updateExpiryDate").val(product.expiryDate);
        $("#updateProductModal #updateCategory").val(product.categoryId);

        // Set the product ID in a hidden field within the form
        $("#updateProductModal #updateProductId").val(productId);

        // Show the modal
        $('#updateProductModal').modal('show');

        // Log the product details to the console
        console.log("Product Details:", product);

    });
});

// Form submission for updating product
$("#updateProductForm").submit(function(e) {
    e.preventDefault();

    // Get the product ID
    let productId = $("#updateProductId").val();

    // Serialize form data
    let formData = $(this).serialize();
    console.log(productId);
    // Submit AJAX request to update product
    /*$.post("/updateProduct/" + productId, formData)
        .done(function(response) {
            console.log("Product updated successfully:", response);
            // Refresh the product table after updating the product
            fetchAndDisplayProducts();
            // Close the modal
            $('#updateProductModal').modal('hide');
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            console.error("Error updating product:", errorThrown);

        });*/

    // Convert formData object to JSON format
    let jsonData = {};
    formData.split('&').forEach(function(keyValue) {
        let [key, value] = keyValue.split('=');
        jsonData[key] = decodeURIComponent(value.replace(/\+/g, ' '));
    });

    $.ajax({
            url: "/updateProduct/" + productId,
            type: "PUT", // Change POST to PUT
            data: JSON.stringify(jsonData),
            contentType: 'application/json', // Set content type
            success: function(response) {
                console.log("Product updated successfully:", response);
                // Refresh the product table after updating the product
                fetchAndDisplayProducts();
                // Close the modal
                $('#updateProductModal').modal('hide');
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error("Error updating product:", errorThrown);
            }
        });
});
// End Update Product Script


// Start Add CSV/Batch Script
 $(document).ready(function () {
        // Function to handle file upload
        $('#upload-2').on('change', function () {
            let fileList = $(this)[0].files;
            let uploadedFileList = $('#uploadedFileList');

            // Clear existing file list
            uploadedFileList.empty();

            // Iterate through each file and append it to the list
            $.each(fileList, function (index, file) {
                let fileName = file.name;
                let listItem = '<li>' +
                    '<a href="#" class="file-name"><i class="las la-paperclip"></i> <span class="name-text">' + fileName + '</span></a>' +
                    '<a href="#" class="btn-delete"><i class="la la-trash"></i></a>' +
                    '</li>';
                uploadedFileList.append(listItem);
            });
        });

        // Function to handle file deletion
        $('#uploadedFileList').on('click', '.btn-delete', function (e) {
            e.preventDefault();
            $(this).closest('li').remove();
        });

        // Function to handle form submission
        $('#csvUploadForm').submit(function (e) {
            e.preventDefault();

            let formData = new FormData($(this)[0]);

            // Add your form submission logic here
            $.ajax({
                url: '/batch',
                type: 'POST',
                data: formData,
                contentType: false,
                processData: false,
                success: function (response) {
                    // Handle success response
                    console.log(fetchAndDisplayProducts());
                    console.log(formData);
                    console.log(response);

                    // After adding, fetch and display products
                    fetchAndDisplayProducts();
                },
                error: function (xhr, status, error) {
                    // Handle error response
                    console.error('Error uploading files:', response.message);
                }
            });


        });

        // Handle cancel button click event
        $('#cancelButton').on('click', function() {
            // Clear the file input
            $('#upload-2').val('');
            // Clear the uploaded file list
            $('#uploadedFileList').empty();
        });
    });
// End Add CSV/Batch Script

// Start Delete Product Script
$(document).on('click', '.remove', function(e) {
    e.preventDefault();

    // Get the product ID from the table row
    let ProductId = $(this).closest('tr').data('product-id');

    // Send AJAX request to delete the product
    $.ajax({
        url: "products/" + ProductId,
        type: "DELETE",
        success: function(response) {
            console.log("Product deleted successfully:", response);
            // Remove the corresponding row from the table
            $(this).closest('tr').remove();

           // Refresh the product table after updating the product
           fetchAndDisplayProducts();
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.error("Error deleting product:", errorThrown);
        }
    });
});
// End Delete Product Script



//Point of Sale Page



