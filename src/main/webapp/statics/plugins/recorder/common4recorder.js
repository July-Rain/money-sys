var audio_context;
var recorder;

function startUserMedia(stream) {
    var input = audio_context.createMediaStreamSource(stream);
    recorder = new Recorder(input);
}

function startRecording(button) {
    recorder && recorder.record();
    button.disabled = true;
    button.nextElementSibling.disabled = false;
}

function stopRecording(button,vmElement) {
    recorder && recorder.stop();
    button.disabled = true;
    button.previousElementSibling.disabled = false;
    recorder && recorder.exportWAV(function(blob) {
        var formData = new FormData();
        formData.append('voiceFile', blob);
        $.ajax({
            url: baseURL + "mgt/accessory/uploadAndTransWords",
            type: 'post',
            processData: false,
            contentType: false,
            data: formData,
            dataType: 'json',
            success: function (data) {
                var obj = eval(data);
                if (obj.code == 0) {
                    vmElement = data.words;
                }else{
                    alert(obj.msg);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(textStatus + "---" + errorThrown);
            }
        });
    });
    //createDownloadLink();
    recorder.clear();
}

function createDownloadLink() {
    recorder && recorder.exportWAV(function(blob) {
        var url = URL.createObjectURL(blob);
        var li = document.createElement('li');
        var au = document.createElement('audio');
        var hf = document.createElement('a');
        au.controls = true;
        au.src = url;
        hf.href = url;
        hf.download = new Date().toISOString() + '.wav';
        hf.innerHTML = hf.download;
        li.appendChild(au);
        li.appendChild(hf);
        recordingslist.appendChild(li);
    });
}

window.onload = function init() {
    try {
        // webkit shim
        window.AudioContext = window.AudioContext || window.webkitAudioContext;
        navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia;
        window.URL = window.URL || window.webkitURL;

        audio_context = new AudioContext;
    } catch(e) {
        alert('No web audio support in this browser!');
    }

    navigator.getUserMedia({
        audio: true
    }, startUserMedia, function(e) {
        alert('No live audio input: ' + e);
    });
};