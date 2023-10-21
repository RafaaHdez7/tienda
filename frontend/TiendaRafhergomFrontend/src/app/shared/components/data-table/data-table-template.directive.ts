import { Directive, Input, TemplateRef } from '@angular/core';

@Directive({
  selector: '[dataTableTemplate]',
  host: {},
})
export class DataTableTemplateDirective {
  @Input('dataTableTemplate') name: string;

  constructor(public template: TemplateRef<any>) {}
}
