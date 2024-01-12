import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'] // Importa el archivo de estilos CSS
})
export class HeaderComponent {
  // Utiliza el estándar camelCase para el nombre de la variable
  toggleValue = true;

  @Output() booleanToggle = new EventEmitter<boolean>();

  booleanEventClick() {
    // Si el valor es undefined, asigna true
    if (this.toggleValue === undefined) {
      this.toggleValue = true;
    }

    // Cambia el valor del toggle al hacer clic
    this.toggleValue = !this.toggleValue;

    // Emite el valor al componente padre
    this.booleanToggle.emit(this.toggleValue);
  }
}
