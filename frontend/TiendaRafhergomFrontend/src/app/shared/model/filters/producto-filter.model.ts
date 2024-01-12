import { PageableFilter } from '..';

export interface ProductoFilter extends PageableFilter {
  categoriaId: number;
}
