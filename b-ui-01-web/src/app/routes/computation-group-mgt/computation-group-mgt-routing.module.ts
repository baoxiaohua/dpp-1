import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ComputationGroupMgtCurdComponent } from './curd/curd.component';

const routes: Routes = [
  { path: '', redirectTo: 'curd', pathMatch: 'full' },
  { path: 'curd', component: ComputationGroupMgtCurdComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ComputationGroupMgtRoutingModule { }
