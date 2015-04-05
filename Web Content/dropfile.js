/**
 * Created by Kike on 4/1/15.
 */
function init() {
    var dropdiv = document.getElementById('dropzone');
    var file = document.getElementById('file');
    styleItUp();
    function styleItUp() {
        dropdiv.style.position = 'absolute';
        dropdiv.style.left = (window.innerWidth / 2) - 200;
        dropdiv.style.top = (window.innerHeight / 3);
        dropdiv.style.borderColor = 'red';
        dropdiv.style.borderRadius = '10px';
        dropdiv.style.borderWidth = '4px';
    }

    dropdiv.addEventListener('dragover', dragOverFile, false);
    dropdiv.addEventListener('dragleave', dragOverFile, false);
    dropdiv.addEventListener('dragenter', dragOverFile, false);
    dropdiv.addEventListener('drop', fileHandler, false);
    file.addEventListener('change', fileHandler, true);
    function dragOverFile(e) {
        if (e.type == 'dragover') {
            dropdiv.style.borderColor = 'steelblue';
            dropdiv.style.borderRadius = '10px';
            dropdiv.style.borderWidth = '4px';
        } else {
            dropdiv.style.borderColor = 'red';
            dropdiv.style.borderRadius = '10px';
            dropdiv.style.borderWidth = '4px';
        }
        e.stopPropagation();
        e.preventDefault();
    }

    function fileHandler(e) {
        e.preventDefault();
        var blobs=[];
        var file = e.type=='drop'? e.dataTransfer.files: e.target.files;
        console.log(file);
        for(var i =0;i<file.length;i++) {
            blobs[i] = new Blob([file[i]], {type: 'image/jpg'});
            var name=file[i].name;
            upload(blobs[i],name);
        }
        console.log(blobs);
    }
}