import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { ComputationGroupMgtRoutingModule } from './computation-group-mgt-routing.module';
import { ComputationGroupMgtCurdComponent } from './curd/curd.component';
import { ComputationGroupMgtCurdEditComponent } from './curd/edit/edit.component';
import { ComputationGroupMgtCurdViewComponent } from './curd/view/view.component';

const COMPONENTS = [
  ComputationGroupMgtCurdComponent];
const COMPONENTS_NOROUNT = [
  ComputationGroupMgtCurdEditComponent,
  ComputationGroupMgtCurdViewComponent];

@NgModule({
  imports: [
    SharedModule,
    ComputationGroupMgtRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class ComputationGroupMgtModule { }
