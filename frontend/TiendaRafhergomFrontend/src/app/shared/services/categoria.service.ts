import { HttpClient, HttpRequest, HttpHeaders } from '@angular/common/http';

import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs/internal/Observable';
import { Categoria } from '../model';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {


  constructor(private http: HttpClient) { }

  private crudURL = environment.categoriaURL;

  getViews(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(
      this.crudURL + ''
    );
  }

  get(id: string): Observable<Categoria> {
    return this.http.get<Categoria>(
      this.crudURL +'/' + id
    );
  }

  create(categoria: Categoria, isEdit: boolean): Observable<Categoria> {
    if (isEdit) {
      return this.http.put<Categoria>(
        this.crudURL ,
        categoria
      );
    }
    else {
      return this.http.post<Categoria>(
        this.crudURL + 'create',
        categoria
      );
    }
  }

  modify(categoria: Categoria): Observable<Categoria> {
    return this.http.put<Categoria>(
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
