
$(document).ready(function() {
    // Initialize counter
    let counter = 1;

    // Fetch products from the server and populate the product table
    fetchProducts(counter);
});

function fetchProducts(counter) {
    $.get("/products")
        .done(function(products) {
            // Clear existing table rows
            $("#posProductTableBody").empty();

            // Iterate over products and add rows to the table
            products.forEach(function(product) {
                var row = `
                <tr>
                    <td>${counter++}</td>
                    <td>${product.productName}</td>
                    <td>${product.quantityInStock}</td>
                    <td>${product.sellingPrice}</td>
                    <td>${product.categoryId}</td>
                    <td>
                        <a href="#" class="edit" onclick="addToCart('${product.id}', '${product.productName}', ${product.sellingPrice})">Add</a>
                    </td>
                    <!-- Hidden cell for product ID -->
                    <td style="display: none;">${product.id}</td>
                </tr>`;
                $("#posProductTableBody").append(row);
            });
        })
        .fail(function(xhr, status, error) {
            console.error("Failed to fetch products:", error);
        });
}

// Search Function to Filter Product(s) on the Products Table
// Function to filter products based on search keyword
function filterProducts() {
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("searchInput");
    filter = input.value.toUpperCase();
    table = document.getElementById("posProductTableBody");
    tr = table.getElementsByTagName("tr");

    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[1]; // Change index if product name is in different column
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

// Add event listener to the search input field
document.getElementById("searchInput").addEventListener("keyup", filterProducts);


// Array to store cart items
let cartItems = [];

// Event listener for Add button in product table
$(document).on('click', '.edit', function() {
    let $row = $(this).closest('tr');
    let productId = $row.find('td:last-child').text();
    let productName = $row.find('td:eq(1)').text();
    let price = parseFloat($row.find('td:eq(3)').text());
    let quantity = parseInt($row.find('td:eq(2)').text());

    // Check if item is in stock
    if (quantity === 0) {
        alert(`${productName} is out of stock. Please restock.`);
        return; // Exit function
    }

    // Check if item already exists in cart
    let existingItem = cartItems.find(item => item.productId === productId);
    if (existingItem) {
        // Increment quantity if item already exists
        existingItem.quantity++;
        updateCartItem(existingItem);
    } else {
        // Add new item to cart
        let newItem = {
            productId: productId,
            productName: productName,
            price: price,
            quantity: 1
        };
        cartItems.push(newItem);
        addCartItem(newItem);
    }
});

// Function to add cart item to the table
function addCartItem(item) {

    let $cartBody = $('#cart-item');
    let total = item.price * item.quantity;
    item.totalAmount = parseFloat(total.toFixed(2) * item.quantity);
    let row = `
    <tr data-id="${item.productId}">
        <td>${item.productName}</td>
        <td>${item.price.toFixed(2)}</td>
        <td>
            <div class="quantity product-cart__quantity">
                <input type="button" class="qty-minus bttn bttn-left wh-36" data-id="${item.productId}" value="-">
                <input type="text" value="${item.quantity}" class="qty qh-36 input">
                <input type="button" class="qty-plus bttn bttn-left wh-36" data-id="${item.productId}" value="+">
            </div>
        </td>
        <td class="total subtotal">${total.toFixed(2)}</td>
        <td style="background:none; outline:none; border:none;"><button class="delete action-btn float-end" data-id="${item.productId}"><i class="las la-trash-alt"></i></button></td>
    </tr>`;
    $cartBody.append(row);
    updateTotal();

}


// Function to update product quantities in the UI after transaction
function updateProductQuantities() {
    cartItems.forEach(function(item) {
        let productId = item.productId;
        let newQuantity = item.quantityInStock - item.quantity; // Subtract sold quantity
        $(`#posProductTableBody tr[data-id="${productId}"] td:eq(2)`).text(newQuantity);
    });
}


// Event listener for Delete button
$(document).on('click', '.delete', function() {
    let productId = $(this).data('id');
    $(`#cart-item tr[data-id="${productId}"]`).remove();
    cartItems = cartItems.filter(item => item.productId !== productId);
    updateTotal();
});

// Event listener for the "Clear" button
    $('#clearCartBtn').click(function() {
        // Clear the cartItems array and update the UI
        cartItems = [];
        $('#cart-item').empty(); // Empty the cart item table
        updateTotal(); // Update the total in the UI
    });

/*
// Unbind existing event handler to prevent multiple bindings
$(document).off('click', '.qty-minus, .qty-plus');

// Event listener for Quantity +/- buttons
$(document).on('click', '.qty-minus, .qty-plus', function(event) {
    event.preventDefault();
    event.stopPropagation(); // To prevent event bubbling

    // Retrieve product ID, quantity input field, and current quantity
    let productId = $(this).data('id');
    let $qtyInput = $(this).siblings('.qty');
    let quantity = parseInt($qtyInput.val());

    // Find the corresponding item in the cartItems array
    let item = cartItems.find(item => item.productId === productId);

    // Update quantity based on button clicked
    if ($(this).hasClass('qty-minus')) {
        // Decrease quantity by 1, but not below 0
        quantity = Math.max(quantity - 1, 0);
    } else if ($(this).hasClass('qty-plus')) {
        // Increase quantity by 1, but not beyond available stock
//        quantity = Math.min(quantity + 1, item.quantityInStock);

     if (quantity + 1 > item.quantityInStock) {
                // Alert the user and revert the quantity back to the maximum allowed value
                alert(`Maximum available quantity for ${item.productName} is ${item.quantityInStock}`);
                quantity = item.quantityInStock;
            } else {
                quantity = quantity + 1;
            }
    }

    // Update quantity input field
    $qtyInput.val(quantity);

    // Update cart item
    updateCartItemQuantity(productId, quantity);
});


// Function to update quantity of a cart item
function updateCartItemQuantity(productId, quantity) {
 console.log("Updating cart item quantity - Product ID:", productId, "Quantity:", quantity);

 if (!isNaN(quantity) && Number.isInteger(quantity) && quantity >= 0) {

    // Find the cart item and update its quantity
    let updatedItem = cartItems.find(item => item.productId === productId);
    console.log("Updated item:", updatedItem);

    if (updatedItem) {
        updatedItem.quantity = quantity;

        // Remove item from cart if quantity is 0
        if (quantity === 0) {
            removeCartItem(productId);
        } else {
            updateCartItem(updatedItem);
        }
    }else {
            console.error("Item not found in cart:", productId);
        }
    } else {
        console.error("Invalid quantity value:", quantity);
    }
}
*/


// new code to test quantity updation
$(document).ready(function() {
    // Initialize counter
    let counter = 1;

    // Fetch products from the server and populate the product table
    fetchProducts(counter);

    // Event listener for Quantity +/- buttons using event delegation
    $(document).on('click', '.product-cart__quantity .qty-minus, .product-cart__quantity .qty-plus', function(event) {
        event.preventDefault();
        event.stopPropagation(); // To prevent event bubbling

        // Retrieve product ID, quantity input field, and current quantity
        let $qtyInput = $(this).siblings('.qty');
        let productId = $qtyInput.closest('tr').data('id');
        let quantity = parseInt($qtyInput.val());

        // Find the corresponding item in the cartItems array
        let item = cartItems.find(item => item.productId === productId);

        // Update quantity based on button clicked
        if ($(this).hasClass('qty-minus')) {
            // Decrease quantity by 1, but not below 0
            quantity = Math.max(quantity - 0, 0);
        } else if ($(this).hasClass('qty-plus')) {
            // Increase quantity by 1, but not beyond available stock
            if (quantity + 0 > item.quantityInStock) {
                // Alert the user and revert the quantity back to the maximum allowed value
                alert(`Maximum available quantity for ${item.productName} is ${item.quantityInStock}`);
                return; // Exit the function without updating quantity or cart item
            } else {
                quantity = quantity + 0;
            }
        }

        // Update quantity input field
        $qtyInput.val(quantity);

        // Update cart item
        updateCartItemQuantity(productId, quantity);

        // Remove item from cart if quantity is 0
        if (quantity === 0) {
            removeCartItem(productId);
        }

        if (quantity + 1 > item.quantityInStock) {
            // Alert the user and revert the quantity back to the maximum allowed value
            alert(`Maximum available quantity for ${item.productName} is ${item.quantityInStock}`);
            quantity = item.quantityInStock;
        }
    });
});

// Function to update quantity of a cart item
function updateCartItemQuantity(productId, quantity) {
    // Find the cart item and update its quantity
    let updatedItem = cartItems.find(item => item.productId === productId);
    updatedItem.quantity = quantity;

    // Update the quantity input field and total in the table
    let $qtyInput = $(`#cart-item tr[data-id="${productId}"] .qty`);
    let total = updatedItem.price * updatedItem.quantity;
    $qtyInput.val(updatedItem.quantity);
    $(`#cart-item tr[data-id="${productId}"] .total`).text(total.toFixed(2));

    // Update the order summary
    updateTotal();
}

//test quantity updation end here



// Function to remove cart item from the table
function removeCartItem(productId) {
    let updatedItem = cartItems.find(item => item.productId === productId);
    if (updatedItem.quantity === 0) {
        $(`#cart-item tr[data-id="${productId}"]`).remove();
        cartItems = cartItems.filter(item => item.productId !== productId);
        updateTotal();
    }
}

// Function to update cart item in the table
function updateCartItem(item) {
console.log("Updating cart item:", item);

    let $row = $(`#cart-item tr[data-id="${item.productId}"]`);
    let total = item.price * item.quantity;

    console.log("New total:", total);

if (!isNaN(item.quantity)) {
    $row.find('.qty').val(item.quantity);
    $row.find('.total').text(total.toFixed(2));
    updateTotal();
    }else {
        console.error("Invalid quantity value:", item.quantity);
    }
}

// Function to ensure quantity input field accepts only numeric values
$(document).on('input', '.qty', function() {
    $(this).val($(this).val().replace(/\D/, ''));
    let quantity = parseInt($(this).val());
    let productId = $(this).closest('tr').data('id');
    let updatedItem = cartItems.find(item => item.productId === productId);
    if (updatedItem) {
        updatedItem.quantity = quantity;
        if (quantity === 0) {
            removeCartItem(productId); // Remove item if quantity is 0
        } else {
            updateCartItem(updatedItem);
        }
    }
});

// Function to update the order summary based on cart items
function updateTotal() {
    let subtotal = 0;
    cartItems.forEach(item => {
        subtotal += item.price * item.quantity;
    });

    // Calculate taxes (assuming fixed tax rate for now)
    let taxRate = 0.125; // 12.5%
    let taxes = subtotal * taxRate;

    // Calculate total
    let total = subtotal + taxes;

    // Update the HTML elements with the calculated values
    $('.subtotalTotal span').text(`GHS ${subtotal.toFixed(2)}`);
    $('.taxes span').text(`GHS ${taxes.toFixed(2)}`);
    $('.total-money h5').text(`GHS ${total.toFixed(2)}`);
}

// Call the updateOrderSummary function whenever the cart items are updated
updateTotal();

// Start Script for Proceed to Checkout

// Event listener for the "proceed to checkout" button
$('.checkout').click(function() {
    // Open the checkout modal
    $('#checkOut').modal('show');

    // Populate the modal with items and summary
    populateCheckoutModal();
});

// Function to populate the checkout modal with items and summary
function populateCheckoutModal() {
    // Clear existing content in the modal body
    $('.modal-body').empty();

    // Append items to the modal body
    cartItems.forEach(function(item) {
        let itemHtml = `
        <div>${item.productName} - Quantity: ${item.quantity} - Price: GHS ${item.price.toFixed(2)}</div>`;
        $('.modal-body').append(itemHtml);
    });

    // Append summary to the modal body
    let subtotal = calculateSubtotal();
    //let discount = calculateDiscount();
    let taxes = calculateTaxes();
    //let total = subtotal - discount + taxes;
    let summaryHtml = `
    `;
    $('.modal-body').append(summaryHtml);
}

// Function to calculate taxes based on subtotal
function calculateTaxes(subtotal) {
    // Implement your taxes calculation logic here
    let taxRate = 0.125;
    let taxes = taxRate * subtotal;
    return taxes;
}

// Start Submit Request to Backend

// Event listener for the "finish" button in the checkout modal
$('#completeTransaction').click(function() {

    // If all quantities are within stock limits, proceed with transaction
    submitTransaction();
    // Close the modal
    $('#checkOut').modal('hide');
});

// Define getProductById function to retrieve product details by ID
function getProductById(productId) {
    // Implement logic to fetch product details from the server or local data
    return products.find(product => product.id === productId);
}


function submitTransaction() {
    // Prepare the data to be sent in the request body
    let items = cartItems.map(item => ({
        id: item.productId,
        productName: item.productName,
        quantity: item.quantity,
        sellingPrice: item.price,
        totalAmount: item.totalAmount
    }));

    // Make an AJAX POST request to the backend endpoint
    $.ajax({
        url: "/posTransaction",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(items),
        success: function(response) {
            // Handle successful response from the backend
            console.log("Transaction successful:", response);
            // Clear the cart and update the UI as needed
            $('#cart-item').empty();
            cartItems = [];
            updateTotal();

            // Fetch updated products after successful transaction
            fetchProducts(1);

            // Update product quantities in the UI after transaction
            updateProductQuantities();
        },
        error: function(xhr, status, error) {
            // Handle errors from the backend
                        console.error("Error occurred during transaction:", error);
                        // Check if the error is due to insufficient stock
                        if (xhr.status === 400) {
                            // Alert the user with the error message from the server
                            alert(xhr.responseText);
                        } else {
                            // Display a generic error message to the user
                            alert("An error occurred during the transaction. Please try again later.");
                        }
        }
    });

}

// End Submit Request to Backend



//Start Here Transactions Script



// Function to fetch and display transactions
$(document).ready(function() {
    // Attach click event listener to the button
    $('#posTransactions-tab').click(function() {
        fetchTransactions();
    });

    // Initialize counter
    let counter = 1;

    // Function to fetch and display transactions
    function fetchTransactions() {
        $.ajax({
            url: '/transactions', // Update with your actual backend endpoint URL
            type: 'GET',
            success: function(response) {
                // Clear previous table rows
                $('#transactionsTableBody').empty();

                // Iterate over each transaction and populate the table
                $.each(response, function(index, transaction) {
                    index = index+1;
                    // Create a row for the transaction
                    let row = '<tr class="' + (index % 2 === 0 ? 'even' : 'odd') + '">';
                    row += '<td>' + index++ + '</td>'; // Increment counter for each row
                    row += '<td>' + transaction.id + '</td>';
                    row += '<td>' + getProductNames(transaction.items) + '</td>'; // Populate with product names
                    row += '<td>' + getQuantities(transaction.items) + '</td>'; // Populate with quantities
                    row += '<td>' + getPrices(transaction.items) + '</td>'; // Populate with prices
                    row += '<td>' + transaction.date + '</td>';
                    row += '<td>' + calculateSubTotal(transaction.items) + '</td>'; // Populate with subtotal
                    row += '</tr>';

                    // Append row to the table
                    $('#transactionsTableBody').append(row);
                });
            },
            error: function(xhr, status, error) {
                console.error('Error fetching transactions:', error);
                // Handle error appropriately, e.g., show error message to the user
            }
        });
    }

    // Function to get product names from items array
    function getProductNames(items) {
        var productNames = '';
        $.each(items, function(index, item) {
            productNames += item.productName + '<br>';
        });
        return productNames;
    }

    // Function to get quantities from items array
    function getQuantities(items) {
        var quantities = '';
        $.each(items, function(index, item) {
            quantities += item.quantity + '<br>';
        });
        return quantities;
    }

    // Function to get prices from items array
    function getPrices(items) {
        var prices = '';
        $.each(items, function(index, item) {
            prices += item.sellingPrice + '<br>';
        });
        return prices;
    }

    // Function to calculate subtotal
    function calculateSubTotal(items) {
        var total = 0;
        $.each(items, function(index, item) {
            total += item.quantity * item.sellingPrice;
        });
        return total;
    }

    // Call fetchTransactions() when the page loads
    fetchTransactions();
});




//End Here Transactions Script

// End Script for Proceed to Checkout

