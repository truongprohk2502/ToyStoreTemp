function priceRange() {
    var price1 = document.getElementsByName("price1")[0].value.trim();
    var price2 = document.getElementsByName("price2")[0].value.trim();
    document.getElementsByName("price1")[0].value = price1;
    document.getElementsByName("price2")[0].value = price2;
    var pattern = /^\d*$/;
    if (!pattern.test(price1) || !pattern.test(price2) || (price1 == "" && price2 == "") || (parseInt(price1) > parseInt(price2))) {
        return false;
    }
    return true;
}

function validateSearch() {
    var word = document.getElementsByName("word")[0].value.trim();
    document.getElementsByName("word")[0].value = word;
    if (word == "") {
        return false;
    }
    return true;
}