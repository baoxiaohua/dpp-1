import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SysQueryMgtListComponent } from './list/list.component';
import { SysQueryMgtCurdComponent } from './curd/curd.component';

const routes: Routes = [

  { path: 'list', component: SysQueryMgtListComponent },
  { path: 'curd', component: SysQueryMgtCurdComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SysQueryMgtRoutingModule { }
