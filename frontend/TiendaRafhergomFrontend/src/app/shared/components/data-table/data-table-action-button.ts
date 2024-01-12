export interface DataTableActionButton {
  /**
   * Icono que se aplica al butón
   */
  icon: string;

  /**
   * Mensaje de ayuda (tooltip) que se muestra al pasar por encima del botón
   */
  tooltip: string;

  /**
   * Nombre de la clase CSS que se aplica al botón
   */
  styleClass?: string;

  /**
   * Función que indicará si el botón está procesando la acción (true) o no (false).
   *
   * Evitar realizar mucha lógica dentro de este método ya que será llamado múltiples veces.
   */
  loadingFn?: () => boolean;

  /**
   * Función que indicará si el botón está habilitado (true) o no (false).
   * Como parámetro se indica los registros seleccionados de la tabla
   *
   * Evitar realizar mucha lógica dentro de este método ya que será llamado múltiples veces.
   */
  enableFn: (selected: any) => boolean;

  /**
   * Función que se ejecutará al pulsar el botón. Como parámetro se indica los registros seleccionados de la tabla
   */
  handleActionEventFn: (selected: any) => void;
}
