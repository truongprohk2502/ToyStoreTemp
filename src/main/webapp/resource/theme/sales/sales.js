$(document).ready(function () {
    getHistoryOrders(historyPage);
    getDelivering(deliveringPage);
    getInventory(inventoryPage);
});

var rowsQty = 10;
var deliveringPage = 0;
var inventoryPage = 0;
var historyPage = 0;

function getInventory(page) {

    inventoryPage = page;

    $.ajax({
        type: 'get',
        url: 'http://localhost:8080/inventory/',
        dataType: 'json',
        success: function (data) {

            var strHtml = "";

            strHtml += '<div class="btn-inventory">';
            strHtml += '<button class="input-btn">Create new product</button>';
            strHtml += '</div>';

            strHtml += '<table>';
            strHtml += '<tr>';
            strHtml += '<th>Product Name</th>';
            strHtml += '<th>Manufacture</th>';
            strHtml += '<th>Category</th>';
            strHtml += '<th>Quantity</th>';
            strHtml += '<th>Unit Price</th>';
            strHtml += '<th>Old Price</th>';
            strHtml += '<th class="align-center">Update</th>';
            strHtml += '<th class="align-center">Delete</th>';
            strHtml += '</tr>';

            for (var i = page * rowsQty; i < (page + 1) * rowsQty; ++i) {

                if (data[i] != null) {

                    strHtml += '<tr>';
                    strHtml += '<td><a class="a-text" href="/detail/' + data[i].id + '">' + data[i].name + '</a></td>';
                    strHtml += '<td><a class="a-text" href="/products-brand/' + data[i].brandId + '">' + data[i].brandName + '</a></td>';
                    strHtml += '<td><a class="a-text" href="/products-category/' + data[i].categoryId + '">' + data[i].categoryName + '</a></td>';
                    strHtml += '<td>' + data[i].quantityInStock + '</td>';
                    strHtml += '<td>' + data[i].price + '</td>';
                    strHtml += '<td>' + data[i].oldPrice + '</td>';
                    strHtml += '<td class="align-center"><a class="a-text" onclick="updatToy(this)" id="' + data[i].id + '"><img width="20px" src="/resource/rating/images/edit.png" /></a></td>';
                    strHtml += '<td class="align-center"><a class="a-text" onclick="deleteToy(this)" id="' + data[i].id + '"><img width="20px" src="/resource/rating/images/remove.png" /></a></td>';
                    strHtml += '</tr>';

                }
            }

            strHtml += '</table>';

            var totalPages = Math.ceil(data.length / rowsQty);

            strHtml += '<div class="pagination pagination-small pagination-centered">';
            strHtml += '<ul>';

            if (page != 0) {
                strHtml += '<li><a class="a-text" onclick="getInventory(' + (page-1) + ')">Prev</a></li>';
            }

            for (var i = 0; i < totalPages; i++) {

                if (page == i) {

                    strHtml += '<li class="active"><a class="a-text" onclick="getInventory(' + i + ')">' + (i+1) + '</a></li>';
                } else {

                    strHtml += '<li><a class="a-text" onclick="getInventory(' + i + ')">' + (i+1) + '</a></li>';
                }
            }

            if (page != totalPages - 1) {
                strHtml += '<li><a class="a-text" onclick="getInventory(' + (page+1) + ')">Next</a></li>';
            }

            strHtml += '</ul>';
            strHtml += '</div>';

            $('#inventory').html(strHtml);

        }
    });

}

function getHistoryOrders(page) {

    historyPage = page;

    $.ajax({
        type: 'get',
        url: 'http://localhost:8080/history/',
        dataType: 'json',
        success: function (data) {

            var strHtml = "";
            strHtml += '<table>';
            strHtml += '<tr>';
            strHtml += '<th>Product Name</th>';
            strHtml += '<th>Quantity</th>';
            strHtml += '<th>Total Price</th>';
            strHtml += '<th>Order Date</th>';
            strHtml += '<th>Delivered Date</th>';
            strHtml += '<th>Customer Name</th>';
            strHtml += '<th>Status</th>';
            strHtml += '</tr>';

            for (var i = page * rowsQty; i < (page + 1) * rowsQty; ++i) {

                if (data[i] != null) {
                    strHtml += '<tr>';
                    strHtml += '<td><a class="a-text" href="/detail/' + data[i].toyId + '">' + data[i].toyName + '</a></td>';
                    strHtml += '<td>' + data[i].quantity + '</td>';
                    strHtml += '<td>' + data[i].totalPrice + '</td>';
                    strHtml += '<td>' + data[i].orderDate + '</td>';
                    strHtml += '<td>' + data[i].deliveredDate + '</td>';
                    strHtml += '<td><a class="a-text" onclick="customerDetail(this)" id="' + data[i].customerId + '">' + data[i].customerName + '</a></td>';
                    strHtml += '<td>' + data[i].status + '</td>';
                    strHtml += '</tr>';
                }

            }

            strHtml += '</table>';

            var totalPages = Math.ceil(data.length / rowsQty);

            strHtml += '<div class="pagination pagination-small pagination-centered">';
            strHtml += '<ul>';

            if (page != 0) {
                strHtml += '<li><a class="a-text" onclick="getHistoryOrders(' + (page-1) + ')">Prev</a></li>';
            }

            for (var i = 0; i < totalPages; i++) {

                if (page == i) {

                    strHtml += '<li class="active"><a class="a-text" onclick="getHistoryOrders(' + i + ')">' + (i+1) + '</a></li>';
                } else {

                    strHtml += '<li><a class="a-text" onclick="getHistoryOrders(' + i + ')">' + (i+1) + '</a></li>';
                }
            }

            if (page != totalPages - 1) {
                strHtml += '<li><a class="a-text" onclick="getHistoryOrders(' + (page+1) + ')">Next</a></li>';
            }

            strHtml += '</ul>';
            strHtml += '</div>';

            $('#history').html(strHtml);

        }
    });


}

function getDelivering(page) {

    deliveringPage = page;

    $.ajax({
        type: 'get',
        url: 'http://localhost:8080/delivering/',
        dataType: 'json',
        success: function (data) {

            var strHtml = "";
            strHtml += '<table>';
            strHtml += '<tr>';
            strHtml += '<th>Product Name</th>';
            strHtml += '<th>Quantity</th>';
            strHtml += '<th>Total Price</th>';
            strHtml += '<th>Order Date</th>';
            strHtml += '<th>Customer Name</th>';
            strHtml += '<th class="align-center">Remove</th>';
            strHtml += '</tr>';

            var customerInfo = "";
            var removeHtml= "";

            for (var i = page * rowsQty; i < (page + 1) * rowsQty; ++i) {

                if (data[i] != null) {

                    strHtml += '<tr>';
                    strHtml += '<td><a class="a-text" href="/detail/' + data[i].toyId + '">' + data[i].toyName + '</a></td>';
                    strHtml += '<td>' + data[i].quantity + '</td>';
                    strHtml += '<td>' + data[i].totalPrice + '</td>';
                    strHtml += '<td>' + data[i].orderDate + '</td>';
                    strHtml += '<td><a class="a-text" onclick="customerDetail(this)" id="' + data[i].customerId + '">' + data[i].customerName + '</a></td>';
                    strHtml += '<td class="align-center"><a class="a-text" onclick="removeOrder(this)" id="' + data[i].id + '"><img width="20px" src="/resource/rating/images/remove.png" /></a></td>';
                    strHtml += '</tr>';

                    customerInfo += '<div id="customer' + data[i].customerId + '" class="modal">';
                    customerInfo += '<div class="modal-content">';
                    customerInfo += '<span class="modal-title">Customer Information</span>';
                    customerInfo += '<span class="close" onclick="closeCustomer(this)" id="' + data[i].customerId + '">&times;</span>';
                    customerInfo += '<table align="center">';
                    customerInfo += '<tr>';
                    customerInfo += '<td><b>Name</b></td>';
                    customerInfo += '<td>' + data[i].customerName + '</td>';
                    customerInfo += '</tr>';
                    customerInfo += '<tr>';
                    customerInfo += '<td><b>Phone</b></td>';
                    customerInfo += '<td>' + data[i].customerPhone + '</td>';
                    customerInfo += '</tr>';
                    customerInfo += '<tr>';
                    customerInfo += '<td><b>Email</b></td>';
                    customerInfo += '<td>' + data[i].customerEmail + '</td>';
                    customerInfo += '</tr>';
                    customerInfo += '<tr>';
                    customerInfo += '<td><b>Address</b></td>';
                    customerInfo += '<td>' + data[i].customerAddress + '</td>';
                    customerInfo += '</tr>';
                    customerInfo += '</table>';
                    customerInfo += '</div>';
                    customerInfo += '</div>';

                    removeHtml += '<div id="order' + data[i].id + '" class="modal">';
                    removeHtml += '<div class="modal-content">';
                    removeHtml += '<span class="modal-title">Remove Order</span>';
                    removeHtml += '<span class="close" onclick="closeRemove(this)" id="' + data[i].id + '">&times;</span><br>';
                    removeHtml += '<div class="choose">Choose your order status?</div>';
                    removeHtml += '<div>';
                    removeHtml += '<button class="btn btn--pill btn--green" onclick="cancelOrder(this)" id="' + data[i].id + '">Cancel Order</button>';
                    removeHtml += '&nbsp;&nbsp;&nbsp;&nbsp;';
                    removeHtml += '<button class="btn btn--pill btn--green" onclick="deliveredOrder(this)" id="' + data[i].id + '">Delivered Order</button>';
                    removeHtml += '</div>';
                    removeHtml += '</div>';
                    removeHtml += '</div>';
                }


            }

            strHtml += '</table>';

            var totalPages = Math.ceil(data.length / rowsQty);

            strHtml += '<div class="pagination pagination-small pagination-centered">';
            strHtml += '<ul>';

            if (page != 0) {
                strHtml += '<li><a class="a-text" onclick="getDelivering(' + (page-1) + ')">Prev</a></li>';
            }

            for (var i = 0; i < totalPages; i++) {

                if (page == i) {

                    strHtml += '<li class="active"><a class="a-text" onclick="getDelivering(' + i + ')">' + (i+1) + '</a></li>';
                } else {

                    strHtml += '<li><a class="a-text" onclick="getDelivering(' + i + ')">' + (i+1) + '</a></li>';
                }
            }

            if (page != totalPages - 1) {
                strHtml += '<li><a class="a-text" onclick="getDelivering(' + (page+1) + ')">Next</a></li>';
            }

            strHtml += '</ul>';
            strHtml += '</div>';

            $('#customerModal').html(customerInfo);
            $('#removeModal').html(removeHtml);
            $('#delivering').html(strHtml);

        }
    });

}

function customerDetail(obj) {

    var id = obj.getAttribute("id");
    var cusId = 'customer' + id;

    if (id != null) {
        document.getElementById(cusId).style.display = 'block';
    }

}

function closeCustomer(obj) {

    var id = obj.getAttribute("id");
    var cusId = 'customer' + id;

    if (id != null) {
        document.getElementById(cusId).style.display = 'none';
    }

}

function removeOrder(obj) {

    var id = obj.getAttribute("id");
    var orderId = 'order' + id;

    if (id != null) {
        document.getElementById(orderId).style.display = 'block';
    }

}

function closeRemove(obj) {

    var id = obj.getAttribute("id");
    var orderId = 'order' + id;

    if (id != null) {
        document.getElementById(orderId).style.display = 'none';
    }

}

function cancelOrder(obj) {

    var id = obj.getAttribute("id");

    var statusObj = new Object();
    statusObj.orderId = id;
    statusObj.status = "CANCELLED";

    $.ajax({
        type: "post",
        url: "http://localhost:8080/remove/",
        datatype: 'json',
        contentType : 'application/json; charset=utf-8',
        data: JSON.stringify(statusObj),
        success: function(status){
            getDelivering(deliveringPage);
            getHistoryOrders(historyPage);
        },
        error:function(error){
            alert("error " + error);
        }
    });

}

function deliveredOrder(obj) {

    var id = obj.getAttribute("id");

    var statusObj = new Object();
    statusObj.orderId = id;
    statusObj.status = "DELIVERED";

    $.ajax({
        type: "post",
        url: "http://localhost:8080/remove/",
        datatype: 'json',
        contentType : 'application/json; charset=utf-8',
        data: JSON.stringify(statusObj),
        success: function(status){
            getDelivering(deliveringPage);
            getHistoryOrders(historyPage);
        },
        error:function(error){
            alert("error " + error);
        }
    });

}

function deleteToy(obj) {

    var id = obj.getAttribute("id");

    if (confirm("Are you sure to delete this toy?")) {

        $.ajax({
            type: "delete",
            url: "http://localhost:8080/toy/" + id,
            success: function(status){
                getInventory(inventoryPage);
            },
            error:function(error){
                alert("error " + error);
            }
        });
    }

}