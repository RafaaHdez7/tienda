import { Params } from '@angular/router';

export interface PageableFilter extends Params {
  numPag: number;
  pageSize?: number;
  fieldOrder: string;
  filters?: any;
}
