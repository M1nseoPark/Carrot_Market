function button_event(itemId) {
    if (confirm("게시글을 정말 삭제 하시겠어요?") == true) {    //확인
        const Url = "http://localhost/mypage/sale/edit/"+itemId+"/delete";

        $.ajax({
            url: Url,
            type: "GET",
            success: function(result) {
                console.log(result);
                location.href = "http://localhost/mypage/sale/SELL";
            },
            error: function(error) {
                console.log('Error ${error}');
            }
        });
    }
    else {   //취소
        return;
    }
}