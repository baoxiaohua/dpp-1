import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdvancedFormListComponent } from './list/list.component';

const routes: Routes = [

  { path: 'list', component: AdvancedFormListComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdvancedFormRoutingModule { }
