import { HttpClient, HttpRequest, HttpHeaders } from '@angular/common/http';

import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { Producto } from '../model/dtos/producto.model';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {


  constructor(private http: HttpClient) { }

  private crudURL = environment.productoURL;

  getViews(): Observable<Producto[]> {
    return this.http.get<Producto[]>(
      this.crudURL + ''
    );
  }

  get(id: string): Observable<Producto> {
    return this.http.get<Producto>(
      this.crudURL +'/' + id
    );
  }

  create(producto: Producto, isEdit: boolean): Observable<Producto> {
    if (isEdit) {
      return this.http.put<Producto>(
        this.crudURL ,
        producto
      );
    }
    else {
      return this.http.post<Producto>(
        this.crudURL + 'create',
        producto
      );
    }
  }

  modify(producto: Producto): Observable<Producto> {
    return this.http.put<Producto>(
      this.crudURL + 'edit',
      producto
    );
  }

  delete(id: string): Observable<number> {
    return this.http.delete<number>(
      this.crudURL + 'remove/' + id,
    );
  }
}
