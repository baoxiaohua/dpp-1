import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { ExpandFormRoutingModule } from './expand-form-routing.module';
import { ExpandFormExpandComponent } from './expand/expand.component';

const COMPONENTS = [
  ExpandFormExpandComponent];
const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    ExpandFormRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class ExpandFormModule { }
