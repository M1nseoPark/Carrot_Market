function button_event(obj){
    if (confirm("게시글을 정말 삭제 하시겠어요?") == true) {    //확인
        obj.setAttribute("th:href", "@{http://localhost/mypage/sale/edit/{itemId}/delete(itemId=${item.id})}")
    } else {   //취소
        return;
    }
}