# RoundRobins
Procesos mas importante :

src/principal/RoundRobin.java
 
 
  private  void  ejecutarSFJ ( ArrayList < Proceso >  procesosLista , int  q )        
    En este proceso primero creo las extructuras y variables aux

   Recorro la lista con todos los procesos(procesos general(Nuevo)
   
      creo una lista aux para almacenar los procesos y poder eliminar los ya ordenados
      creo una lista con los procesos cuyo tiempo de llegada sea menor al tiempo de reloj 
      ordeno esta lista con los procesos pertenecientes a la ronda (SFJ)
      elimino los elementos ya ordenados de la aux

     la lista ordenada añado a una cola de procesamientos (procesos listos)

   vacio la lista de ordenados
    
    recorro la cola donde se escuentran los procesos pertenecientes a la ronda (hasta que este vacia o cumplir el tañano INICIAL 
                                                                                muy importante asi cuando se vuelve agregar el proceso no se procesa 2 veces)
      siempre elimino el procesos de esta cola("procesos listos")
      si el q<tiempo de servicio
          lo vuelvo agregar a la cola y le resto el tiempo de servicio 
          r aumenta + q
      sino
        r aumenta +tiempo de servicio
        (no se agrega el proceso nuevamente haciendo que no se mas )
        almaceno el tiempo de retorno del proceso
        se elimina de la lista de todos los procesos(general)
      
      muestro resultados de la ronda
      
 al terminar de recorrer todos los procesos muestro la tabla con los tiempo de retorno y tiempo de espera
