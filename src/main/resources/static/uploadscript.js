function submitForm() {
    console.log("submit event");
    var fd = new FormData(document.getElementById("fileinfo"));
    fd.append("label", "WEBUPLOAD");
    $.ajax({
      url: "/fileupload/upload",
      type: "POST",
      data: fd,
      enctype: 'multipart/form-data',
      processData: false,  // tell jQuery not to process the data
      contentType: false   // tell jQuery not to set contentType
    }).done(function( data ) {
        console.log("Output:");
        console.log( data );
        document.getElementById("output").innerHTML += data;
    });
    return false;
}