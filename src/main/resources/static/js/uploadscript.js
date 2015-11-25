function submitForm() {
    console.log("submit event");

    $.fn.serializeObject = function()
    {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    var csbMetadataInput = JSON.stringify($('#fileinfo').serializeObject());
    console.log("metadata=" + csbMetadataInput);
    var fd = new FormData(document.getElementById("fileinfo"));
    fd.append("csbMetadataInput", csbMetadataInput);
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
        document.getElementById("output").innerHTML = data;
    });
    return false;
}

