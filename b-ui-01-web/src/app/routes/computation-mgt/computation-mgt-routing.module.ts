import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ComputationMgtListComponent } from './list/list.component';

const routes: Routes = [
  { path: 'list', component: ComputationMgtListComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ComputationMgtRoutingModule { }
