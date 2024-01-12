import { NgModule } from '@angular/core';

import { CovalentCommonModule } from '@covalent/core/common';
import { CovalentDialogsModule } from '@covalent/core/dialogs';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [],
  exports: [CommonModule, CovalentCommonModule, CovalentDialogsModule],
})
export class CovalentModule {}
