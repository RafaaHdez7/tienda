export interface Ayuda {
  idAyuda: string;
  descripcion: string;
  topeAyuda: number|null;

}


export interface ImporteAyuda {

  idImporteAyuda: string;
  ejercicio: number;
  idAyuda: number;
  importeAsignado: number;
  activo: number;
  fechaInicio: Date;
  fechaFin: Date;
  importeConcedido: number;
  importeRemanente: number;
}

export interface ImporteAyudaView {
  idImporteAyuda: string;
  ejercicio: number;
  idAyuda: number;
  importeAsignado: number;
  activo: number;
  fechaInicio: Date;
  fechaFin: Date;
  importeConcedido: number;
  importeRemanente: number;
  parentImporteAyuda?: ImporteAyudaView;
}

export interface ImporteAyudaSimple {
  idImporteAyuda: string;
  ejercicio: number;
  idAyuda: number;
  importeAsignado: number;
  activo: number;
  fechaInicio: string;
  fechaFin: string;
  importeConcedido: number;
  importeRemanente: number;
}

