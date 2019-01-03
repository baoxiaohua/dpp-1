import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { ComputationMgtRoutingModule } from './computation-mgt-routing.module';
import { ComputationMgtListComponent } from './list/list.component';
import { ComputationMgtEditComponent } from './edit/edit.component';

const COMPONENTS = [
  ComputationMgtListComponent];
const COMPONENTS_NOROUNT = [
  ComputationMgtEditComponent];

@NgModule({
  imports: [
    SharedModule,
    ComputationMgtRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class ComputationMgtModule { }
