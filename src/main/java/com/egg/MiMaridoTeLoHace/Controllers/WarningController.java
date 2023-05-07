package com.egg.MiMaridoTeLoHace.Controllers;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WarningController implements ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {

        ModelAndView errorPage = new ModelAndView("error");

        String errorMsg = "";

        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                errorMsg = "Bad Request: El servidor no puede o no procesará la solicitud debido a un error del cliente en la sintaxis de la solicitud.";
                break;
            }
            case 401: {
                errorMsg = "Unauthorized: El cliente no está autenticado y no tiene permiso para acceder a los recursos solicitados.";
                break;
            }
            case 402: {
                errorMsg = "Payment Required: Este código está reservado para futuros usos y no se utiliza actualmente.";
                break;
            }
            case 403: {
                errorMsg = "Forbidden: El servidor se niega a permitir que el cliente acceda al recurso solicitado, aunque esté autenticado.";
                break;
            }
            case 404: {
                errorMsg = "Not Found: El servidor no puede encontrar el recurso solicitado.";
                break;
            }
            case 405: {
                errorMsg = "Method Not Allowed: El método de solicitud utilizado no está permitido para el recurso solicitado.";
                break;
            }
            case 406: {
                errorMsg = "Not Acceptable: El servidor no puede producir una respuesta que sea aceptable para el cliente.";
                break;
            }
            case 407: {
                errorMsg = "Proxy Authentication Required: El cliente primero debe autenticarse con el proxy.";
                break;
            }
            case 408: {
                errorMsg = "Request Timeout: El servidor agotó el tiempo de espera para la solicitud.";
                break;
            }
            case 409: {
                errorMsg = "Conflict: La solicitud no pudo ser completada debido a un conflicto con el estado actual del recurso.";
                break;
            }
            case 410: {
                errorMsg = "Gone: El recurso solicitado ya no está disponible y no se espera que esté disponible nuevamente.";
                break;
            }
            case 411: {
                errorMsg = "Length Required: El servidor requiere que el cliente proporcione la longitud del contenido de la solicitud.";
                break;
            }
            case 412: {
                errorMsg = "Precondition Failed: El servidor no puede cumplir con las condiciones previas a la solicitud del cliente para la solicitud.";
                break;
            }
            case 413: {
                errorMsg = "Payload Too Large: El servidor no puede procesar la solicitud porque la carga útil es demasiado grande.";
                break;
            }
            case 414: {
                errorMsg = "URI Too Long: La URI solicitada es demasiado larga para que el servidor la procese.";
                break;
            }
            case 415: {
                errorMsg = "Unsupported Media Type: El servidor no puede procesar el tipo de medio de la solicitud.";
                break;
            }
            case 416: {
                errorMsg = "Range Not Satisfiable: El rango especificado en la solicitud de encabezado 'Rango' no se puede satisfacer por el servidor.";
                break;
            }
            case 417: {
                errorMsg = "Expectation Failed: El servidor no puede cumplir con los requisitos del campo de encabezado 'Expect' de la solicitud.";
                break;
            }

            case 500: {
                errorMsg = "Internal Server Error: El servidor encontró una condición inesperada que le impidió completar la solicitud.";
                break;
            }
            case 501: {
                errorMsg = "Not Implemented: El servidor no admite la funcionalidad necesaria para completar la solicitud.";
                break;
            }
            case 502: {
                errorMsg = "Bad Gateway: El servidor actuó como puerta de enlace o proxy y recibió una respuesta no válida del servidor ascendente.";
                break;
            }
            case 503: {
                errorMsg = "Service Unavailable: El servidor no está listo para manejar la solicitud. Comúnmente se utiliza para indicar que el servidor está sobrecargado o en mantenimiento.";
                break;
            }
            case 504: {
                errorMsg = "Gateway Timeout: El servidor actuó como puerta de enlace o proxy y no recibió una respuesta oportuna del servidor ascendente.";
                break;
            }
            case 505: {
                errorMsg = "HTTP Version Not Supported: El servidor no es compatible con la versión del protocolo HTTP utilizada en la solicitud.";
                break;
            }
            case 506: {
                errorMsg = "Variant Also Negotiates: El servidor ha detectado una referencia circular al procesar la solicitud.";
                break;
            }
            case 507: {
                errorMsg = "Insufficient Storage: El servidor no puede almacenar el recurso que debe crear para completar la solicitud.";
                break;
            }
            case 508: {
                errorMsg = "Loop Detected: El servidor ha detectado un bucle infinito al procesar la solicitud.";
                break;
            }
            case 510: {
                errorMsg = "Not Extended: El servidor requiere más información para completar la solicitud.";
                break;
            }
            case 511: {
                errorMsg = "Network Authentication Required: El cliente debe autenticarse para obtener acceso a la red.";
                break;
            } default: {
                errorMsg = "Null: La página solicitada no existe";
            }
        }
        errorPage.addObject("codigo", httpErrorCode);
        errorPage.addObject("mensaje", errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }

    public String getErrorPath() {
        return "/warning.html";
    }

}
