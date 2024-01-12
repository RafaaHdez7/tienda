import { HttpClient, HttpRequest, HttpHeaders } from '@angular/common/http';

import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { Pedido } from '../model';

@Injectable({
  providedIn: 'root'
})
export class PedidoService {


  constructor(private http: HttpClient) { }

  private crudURL = environment.pedidosURL;

  getViews(): Observable<Pedido[]> {
    return this.http.get<Pedido[]>(
      this.crudURL + ''
    );
  }

  get(id: string): Observable<Pedido> {
    return this.http.get<Pedido>(
      this.crudURL +'/' + id
    );
  }

  create(categoria: Pedido, isEdit: boolean): Observable<Pedido> {
    if (isEdit) {
      return this.http.put<Pedido>(
        this.crudURL ,
        categoria
      );
    }
    else {
      return this.http.post<Pedido>(
        this.crudURL + 'create',
        categoria
      );
    }
  }

  modify(categoria: Pedido): Observable<Pedido> {
    return this.http.put<Pedido>(
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
