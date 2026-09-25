package com.devshowcase.api.exception;
import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.util.*;
@ResponseBody @RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<?> validation(MethodArgumentNotValidException e){
  Map<String,String> f=new LinkedHashMap<>(); e.getBindingResult().getFieldErrors().forEach(x->f.put(x.getField(),x.getDefaultMessage()));
  return ResponseEntity.badRequest().body(Map.of("status",400,"error","Validation Error","fields",f));
 }
 @ExceptionHandler(NoSuchElementException.class) ResponseEntity<?> notFound(NoSuchElementException e){return ResponseEntity.status(404).body(Map.of("status",404,"error","Not Found","message",e.getMessage()));}
}
