/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function ingresoDecimales(e)
{
    var keynum = window.event ? window.event.keyCode : e.which;
    if ((keynum == 0) || (keynum == 8) || (keynum == 9) || (keynum == 46))
        return true;

    return /\d/.test(String.fromCharCode(keynum));
}

function ingresoEnteros(e)
{
    var keynum = window.event ? window.event.keyCode : e.which;
    if ((keynum == 0) || (keynum == 8) || (keynum == 9))
        return true;

    return /\d/.test(String.fromCharCode(keynum));
}

function validaCedula(ci)
{
    var tipo = document.getElementById("form:perTipoid").value;
    var cadena = ci.value;
    var total = 0;
    var longitud = cadena.length;
    var longcheck = longitud - 1;
    alert("tipo   "+tipo);
    if (tipo === "CEDULA") {
        if (cadena !== "" && longitud === 10) {
            for (i = 0; i < longcheck; i++) {
                if (i % 2 === 0) {
                    var aux = cadena.charAt(i) * 2;
                    if (aux > 9)
                        aux -= 9;
                    total += aux;
                } else {
                    total += parseInt(cadena.charAt(i)); // parseInt o concatenará en lugar de sumar
                }
            }

            total = total % 10 ? 10 - total % 10 : 0;

            if (cadena.charAt(longitud - 1) == total) {
                PrimeFaces.debug("Validation Correcta");
            } else {
                alert("Cedula Inválida ");
            }

        } else {
            alert("Cedula Inválida ");
        }
    } else {
        PrimeFaces.debug("Validation Correcta");
    }

}

function handleComplete(xhr, status, args) {
    if (args.validationFailed) {

    } else {
        PrimeFaces.debug("Save:" + args.saved);
        PrimeFaces.debug("FirstName: " + args.user.firstname + ", Lastname: " + args.user.lastname);
    }
}

