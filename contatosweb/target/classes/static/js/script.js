console.log("Esse é o arquivo de script");

document.getElementById("fecharSidebar").onclick = function () {
    if($(".sidebar").is(":visible")) {
        $(".sidebar").css("display", "none");
        $(".content").css("margin-left", "2%");
    }
}

document.querySelector("#abrirSidebar").onclick = function () {
    if(!$(".sidebar").is(":visible")) {
        $(".sidebar").css("display", "block");
        $(".content").css("margin-left", "20%");
    }
}