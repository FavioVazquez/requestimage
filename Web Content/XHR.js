/**
 * Created by Kike on 3/6/15.
 */
function download(){
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/XHRImageTest/Home', true);
    xhr.responseType = 'blob';
    var name=window.prompt("Type the name of the image you want");
    xhr.onload = function (e) {
        if (this.status == 200) {
            var blob = this.response;
            var img = new Image();
            console.log(blob);
            img.onload = function (e) {
                console.log('executed');
                console.log(this);
                document.querySelector('canvas').getContext('2d').drawImage(img,0,0);
                window.URL.revokeObjectURL(img.src);
            };
            img.src = window.URL.createObjectURL(blob);
        }
    };

    xhr.send(name);
}
function upload(blob,name){
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/XHRImageTest/Home', true);
    xhr.setRequestHeader("name",name);
    var progressBar = document.querySelector('progress');
    xhr.upload.onprogress = function(e) {
        if (e.lengthComputable) {
            progressBar.value = (e.loaded / e.total) * 100;
            progressBar.textContent = progressBar.value;
        }
    };
    xhr.send(blob);
}


