// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  url_frontend: 'http://localhost:4200/',
  url_backend: 'http://localhost:8088/tfg/',
  _productoURL: 'http://localhost:8088/tfg/api/productos/',
  _usuarioURL: 'http://localhost:8088/tfg/api/usuarios/',
  _categoriaURL: 'http://localhost:8088/tfg/api/categorias/',
  get productoURL() {
    return this._productoURL;
  },
  set productoURL(value) {
    this._productoURL = value;
  },
  get usuarioURL() {
    return this._usuarioURL;
  },
  set usuarioURL(value) {
    this._usuarioURL = value;
  },
  get categoriaURL() {
    return this._categoriaURL;
  },
  set categoriaURL(value) {
    this._categoriaURL = value;
  },
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
