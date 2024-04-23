
   // Start Total Number of Products
   $(document).ready(function() {
    // Fetch total products from "/products" endpoint
    $.get("/products", function(data) {
        // Extract total products count from the response data
        let totalProductsCount = data.length;

        // Update the content of the ".totalProducts" element with the total products count
        $(".totalProducts").text(totalProductsCount);
    });
});
// End Total Number of Products



// Start Total Sales of Products
// Function to fetch total sales volume
function fetchSalesVolume() {
    $.get("/analytics/sales-volume")
        .done(function(transactions) {
            let totalSalesVolume = 0;
            transactions.forEach(function(transaction) {
                transaction.items.forEach(function(item) {
                    let productId = item.id;
                    let quantity = item.quantity;
                    let price = item.price;

                    // Fetch the product from the backend to ensure its existence
                    $.get(`/products/${productId}`)
                        .done(function(product) {
                            if (product) {
                                // Calculate sales volume only if the product exists
                                totalSalesVolume += price * quantity;
                            } else {
                                console.error(`Product with ID ${productId} not found.`);
                            }
                        })
                        .fail(function(xhr, status, error) {
                            console.error("Failed to fetch product:", error);
                        });
                });
            });

            // Once all calculations are done, update the UI or perform further actions
            console.log("Total Sales Volume:", totalSalesVolume);
            // Update the UI or perform further actions with the totalSalesVolume value
        })
        .fail(function(xhr, status, error) {
            console.error("Failed to fetch transactions:", error);
        });
}





