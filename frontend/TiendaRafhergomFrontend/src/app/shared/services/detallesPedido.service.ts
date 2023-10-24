import { HttpClient, HttpRequest, HttpHeaders } from '@angular/common/http';

import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { DetallesPedido } from '../model';

@Injectable({
  providedIn: 'root'
})
export class DetallesPedidoService {


  constructor(private http: HttpClient) { }

  private crudURL = environment.detallesPedidosURL;

  getViews(): Observable<DetallesPedido[]> {
    return this.http.get<DetallesPedido[]>(
      this.crudURL + ''
    );
  }

  get(id: string): Observable<DetallesPedido> {
    return this.http.get<DetallesPedido>(
      this.crudURL +'/' + id
    );
  }

  create(categoria: DetallesPedido, isEdit: boolean): Observable<DetallesPedido> {
    if (isEdit) {
      return this.http.put<DetallesPedido>(
        this.crudURL ,
        categoria
      );
    }
    else {
      return this.http.post<DetallesPedido>(
        this.crudURL + 'create',
        categoria
      );
    }
  }

  modify(categoria: DetallesPedido): Observable<DetallesPedido> {
    return this.http.put<DetallesPedido>(
      this.crudURL + 'edit',
      categoria
    );
  }

  delete(id: string): Observable<number> {
    return this.http.delete<number>(
      this.crudURL + 'remove/' + id,
    );
  }
}
