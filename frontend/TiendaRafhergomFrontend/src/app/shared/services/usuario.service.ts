import { HttpClient, HttpRequest, HttpHeaders } from '@angular/common/http';

import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { Usuario } from '../model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {


  constructor(private http: HttpClient) { }

  private crudURL = environment.usuarioURL;

   // Importa el interceptor aquí
   private addCustomHeader(request: HttpRequest<any>): HttpRequest<any> {
    return request.clone({
      setHeaders: {
        'Mi-Cabecera': 'Valor de mi cabecera'
      }
    });
  }

  getViews(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(
      this.crudURL + ''
    );
  }

  get(id: string): Observable<Usuario> {
    return this.http.get<Usuario>(
      this.crudURL +'/' + id
    );
  }

  create(usuario: Usuario, isEdit: boolean): Observable<Usuario> {
    if (isEdit) {
      return this.http.put<Usuario>(
        this.crudURL ,
        usuario
      );
    }
    else {
      return this.http.post<Usuario>(
        this.crudURL + 'create',
        usuario
      );
    }
  }

  modify(usuario: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(
      this.crudURL + 'edit',
      usuario
    );
  }

  delete(id: string): Observable<number> {
    return this.http.delete<number>(
      this.crudURL + 'remove/' + id,
    );
  }
}
