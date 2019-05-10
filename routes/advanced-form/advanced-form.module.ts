import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { AdvancedFormRoutingModule } from './advanced-form-routing.module';
import { AdvancedFormListComponent } from './list/list.component';

const COMPONENTS = [
  AdvancedFormListComponent];
const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    AdvancedFormRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class AdvancedFormModule { }
