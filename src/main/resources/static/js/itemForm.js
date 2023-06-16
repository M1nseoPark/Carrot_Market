function bindDomEvent(){
    $(".custom-file-input").on("change", function() {
        var fileName = $(this).val().split("\\").pop();  //이미지 파일명
        var fileExt = fileName.substring(fileName.lastIndexOf(".")+1); // 확장자 추출
        fileExt = fileExt.toLowerCase(); //소문자 변환

        if(fileExt != "jpg" && fileExt != "jpeg" && fileExt != "gif" && fileExt != "png" && fileExt != "bmp"){
            alert("이미지 파일만 등록이 가능합니다.");
            return;
        }

        $(this).siblings(".custom-file-label").html(fileName);
    });
}

function showPopup(multipleFilter) {
    const popup = document.querySelector('#popup');

    if (multipleFilter) {
        popup.classList.add('multiple-filter');
    } else {
        popup.classList.remove('multiple-filter');
    }

    popup.classList.remove('hide');
}

function pickPopup(obj) {
    const popup = document.querySelector('#popup');
    var result = document.getElementById("result");
    var text = obj.innerHTML;

    result.value = text
    popup.classList.add('hide');
}