import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { BasicFormRoutingModule } from './basic-form-routing.module';
import { BasicFormBasicComponent } from './basic/basic.component';
import { BasicFormEditComponent } from './edit/edit.component';

const COMPONENTS = [
  BasicFormBasicComponent];
const COMPONENTS_NOROUNT = [
  BasicFormEditComponent];

@NgModule({
  imports: [
    SharedModule,
    BasicFormRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class BasicFormModule { }
