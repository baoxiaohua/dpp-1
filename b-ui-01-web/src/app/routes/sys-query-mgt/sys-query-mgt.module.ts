import { NgModule } from '@angular/core';
import { SharedModule } from '@shared/shared.module';
import { SysQueryMgtRoutingModule } from './sys-query-mgt-routing.module';
import { SysQueryMgtEditComponent } from './edit/edit.component';
import { SysQueryMgtListComponent } from './list/list.component';
import { SysQueryMgtCurdComponent } from './curd/curd.component';
import { SysQueryMgtCurdEditComponent } from './curd/edit/edit.component';
import { SysQueryMgtCurdViewComponent } from './curd/view/view.component';

const COMPONENTS = [
  SysQueryMgtListComponent,
  SysQueryMgtCurdComponent];
const COMPONENTS_NOROUNT = [
  SysQueryMgtEditComponent,
  SysQueryMgtCurdEditComponent,
  SysQueryMgtCurdViewComponent];

@NgModule({
  imports: [
    SharedModule,
    SysQueryMgtRoutingModule
  ],
  declarations: [
    ...COMPONENTS,
    ...COMPONENTS_NOROUNT
  ],
  entryComponents: COMPONENTS_NOROUNT
})
export class SysQueryMgtModule { }
