import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { DetailPageRoutingModule } from './detail-page-routing.module';
import { DetailPageDetailComponent } from './detail/detail.component';

const COMPONENTS = [
  DetailPageDetailComponent];
const COMPONENTS_NOROUNT = [];

@NgModule({
  imports: [
    SharedModule,
    DetailPageRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class DetailPageModule { }
