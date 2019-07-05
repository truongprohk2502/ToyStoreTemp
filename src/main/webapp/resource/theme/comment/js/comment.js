$(document).ready(function () {
    findAll();
});

function findAll() {
    var toyId = document.getElementsByName('toyId')[0].value;

    $.ajax({
        type: 'get',
        url: 'http://localhost:8080/comments/' + toyId,
        dataType: 'json',
        success: function (data) {
            var strHtml = "";
            for (var i = 0; i < data.length; ++i) {
                strHtml += '<span class="account-name">';
                strHtml += '<img src="/resource/rating/images/user.png" width="15px" height="15px">';
                strHtml += '<span>';
                strHtml += '<b>' + data[i].nameUser + '</b>';
                strHtml += '<span> (' + data[i].timeAgo + ')</span>';
                strHtml += '</span>';
                strHtml += '<span class="rate-span">';
                strHtml += '<form>';
                strHtml += '<div class="rate">';
                if (data[i].ratingStar == 5) {
                    strHtml += '<input type="radio" checked disabled id="star5" name="rate" value="5" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star5" name="rate" value="5" />';
                }
                strHtml += '<label for="star5"></label>';
                if (data[i].ratingStar == 4) {
                    strHtml += '<input type="radio" checked disabled id="star4" name="rate" value="4" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star4" name="rate" value="4" />';
                }
                strHtml += '<label for="star4"></label>';
                if (data[i].ratingStar == 3) {
                    strHtml += '<input type="radio" checked disabled id="star3" name="rate" value="3" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star3" name="rate" value="3" />';
                }
                strHtml += '<label for="star3"></label>';
                if (data[i].ratingStar == 2) {
                    strHtml += '<input type="radio" checked disabled id="star2" name="rate" value="2" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star2" name="rate" value="2" />';
                }
                strHtml += '<label for="star2"></label>';
                if (data[i].ratingStar == 1) {
                    strHtml += '<input type="radio" checked disabled id="star1" name="rate" value="1" />';
                } else {
                    strHtml += '<input type="radio" disabled id="star1" name="rate" value="1" />';
                }
                strHtml += '<label for="star1"></label>';
                strHtml += '</div>';
                strHtml += '</form>';
                strHtml += '</span>';
                strHtml += '<div class="container-comments">';
                strHtml += '<div class="dialogbox">';
                strHtml += '<div class="body-comments">';
                strHtml += '<span class="tip tip-up"></span>';
                strHtml += '<div class="message-comments">';
                strHtml += '<span>' + data[i].comment + '</span>';
                strHtml += '</div>';
                strHtml += '</div>';
                strHtml += '</div>';
                strHtml += '</div>';
                strHtml += '</span>';
            }
            $('#m-g-l').html(strHtml);
        }
    });
}

function createComment2() {
    var comment = new Object();
    comment.ratingStar = $("input[name='myrate']:checked").val();
    comment.comment = $("textarea[name='comment']").val();
    comment.toyId = $("input[name='toyId']").val();
    comment.username = $("input[name='username']").val();

    $.ajax({
        type: "post",
        url: "http://localhost:8080/comments/",
        datatype: 'json',
        contentType : 'application/json; charset=utf-8',
        data: JSON.stringify(comment),
        success: function(status){
            findAll();
        },
        error:function(error){
            alert("error " + error);
        }
    });
}