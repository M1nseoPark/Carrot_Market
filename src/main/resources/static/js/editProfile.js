function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('profile-img').src = e.target.result;
//            document.getElementById('profile-img-2').src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
    }
    else {
        document.getElementById('profile-img').src = "";
//        document.getElementById('profile-img-2').src = "";
    }
}