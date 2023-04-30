package com.egg.MiMaridoTeLoHace.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorController {

  @ExceptionHandler(Exception.class)
  public ModelAndView handleAllExceptions(Exception ex) {
    ModelAndView model = new ModelAndView("error");
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    // Fede: cree el controller para manejar los errores y su vista en html
    switch (httpStatus.value()) {
      case 400:
        model.addObject("errorTitle", "Error 400");
        model.addObject("errorMsg", "La petición no puede ser procesada.");
        break;
      case 401:
        model.addObject("errorTitle", "Error 401");
        model.addObject("errorMsg", "No autorizado.");
        break;
      case 403:
        model.addObject("errorTitle", "Error 403");
        model.addObject("errorMsg", "Acceso denegado.");
        break;
      case 404:
        model.addObject("errorTitle", "Error 404");
        model.addObject("errorMsg", "La página que busca no se encontró.");
        break;
      case 500:
        model.addObject("errorTitle", "Error 500");
        model.addObject("errorMsg", "Ocurrió un error interno en el servidor.");
        break;
      default:
        model.addObject("errorTitle", "Error");
        model.addObject("errorMsg", "Ocurrió un error inesperado.");
        break;
    }

    return model;
  }
}
