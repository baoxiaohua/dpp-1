import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { DetailFormRoutingModule } from './detail-form-routing.module';
import { DetailFormFormItemComponent } from './form-item/form-item.component';

const COMPONENTS = [
  DetailFormFormItemComponent];
const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    DetailFormRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class DetailFormModule { }
